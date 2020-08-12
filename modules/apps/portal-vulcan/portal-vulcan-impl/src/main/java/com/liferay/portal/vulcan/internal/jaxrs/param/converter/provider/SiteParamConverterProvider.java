/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.vulcan.internal.jaxrs.param.converter.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * @author Javier Gamarra
 */
@Provider
public class SiteParamConverterProvider
	implements ParamConverter<Long>, ParamConverterProvider {

	public SiteParamConverterProvider(
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService) {

		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	public Long fromString(String parameter) {
		MultivaluedMap<String, String> multivaluedMap =
			_uriInfo.getPathParameters();

		Long siteId = null;

		if (multivaluedMap.containsKey("assetLibraryId")) {
			siteId = getDepotGroupId(
				multivaluedMap.getFirst("assetLibraryId"),
				_company.getCompanyId());
		}
		else {
			siteId = getGroupId(
				_company.getCompanyId(), multivaluedMap.getFirst("siteId"));
		}

		if (siteId != null) {
			return siteId;
		}

		throw new NotFoundException(
			"Unable to get a valid site with ID " + parameter);
	}

	@Override
	public <T> ParamConverter<T> getConverter(
		Class<T> clazz, Type type, Annotation[] annotations) {

		if (Long.class.equals(clazz) && _hasSiteIdAnnotation(annotations)) {
			return (ParamConverter<T>)this;
		}

		return null;
	}

	public Long getDepotGroupId(String assetLibraryId, long companyId) {
		if (assetLibraryId == null) {
			return null;
		}

		return _getDepotGroupId(assetLibraryId, companyId);
	}

	public Long getGroupId(long companyId, String siteId) {
		if (siteId == null) {
			return null;
		}

		return _getGroupId(companyId, siteId);
	}

	@Override
	public String toString(Long parameter) {
		return String.valueOf(parameter);
	}

	private boolean _checkGroup(Group group) {
		if ((group != null) &&
			(_isDepotOrSite(group) || _isDepotOrSite(group.getLiveGroup()))) {

			return true;
		}

		return false;
	}

	private Long _getDepotGroupId(String assetLibraryId, long companyId) {
		Group group = _groupLocalService.fetchGroup(companyId, assetLibraryId);

		if (group == null) {
			try {
				DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
					GetterUtil.getLong(assetLibraryId));

				if (depotEntry == null) {
					return null;
				}

				group = depotEntry.getGroup();
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}

				return null;
			}
		}

		if (_checkGroup(group)) {
			return group.getGroupId();
		}

		return null;
	}

	private Long _getGroupId(long companyId, String groupKey) {
		Group group = _groupLocalService.fetchGroup(companyId, groupKey);

		if (group == null) {
			group = _groupLocalService.fetchGroup(GetterUtil.getLong(groupKey));
		}

		if (_checkGroup(group)) {
			return group.getGroupId();
		}

		return null;
	}

	private boolean _hasSiteIdAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			String annotationString = annotation.toString();

			if (annotationString.equals(
					"@javax.ws.rs.PathParam(value=assetLibraryId)") ||
				annotationString.equals(
					"@javax.ws.rs.PathParam(value=siteId)")) {

				return true;
			}
		}

		return false;
	}

	private boolean _isDepotOrSite(Group group) {
		if ((group.getType() == GroupConstants.TYPE_DEPOT) || group.isSite()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteParamConverterProvider.class);

	@Context
	private Company _company;

	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;

	@Context
	private UriInfo _uriInfo;

}