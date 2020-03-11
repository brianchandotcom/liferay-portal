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

	public SiteParamConverterProvider(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public Long fromString(String parameter) {
		MultivaluedMap<String, String> multivaluedMap =
			_uriInfo.getPathParameters();

		Long siteId = getGroupId(
			_company.getCompanyId(), multivaluedMap.getFirst("siteId"));

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

	public Long getGroupId(long companyId, String siteId) {
		if (siteId == null) {
			return null;
		}

		Group group = _fetchGroup(companyId, siteId);

		if (group == null) {
			return null;
		}

		if (_isSiteOrDepot(group) || _isSiteOrDepot(group.getLiveGroup())) {
			return group.getGroupId();
		}

		return null;
	}

	@Override
	public String toString(Long parameter) {
		return String.valueOf(parameter);
	}

	private Group _fetchGroup(long companyId, String groupKey) {
		Group group = _groupLocalService.fetchGroup(companyId, groupKey);

		if (group != null) {
			return group;
		}

		return _groupLocalService.fetchGroup(GetterUtil.getLong(groupKey));
	}

	private boolean _hasSiteIdAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			String annotationString = annotation.toString();

			if (annotationString.equals(
					"@javax.ws.rs.PathParam(value=siteId)")) {

				return true;
			}
		}

		return false;
	}

	private boolean _isSiteOrDepot(Group group) {
		if (group == null) {
			return false;
		}

		if (group.isSite() || (group.getType() == GroupConstants.TYPE_DEPOT)) {
			return true;
		}

		return false;
	}

	@Context
	private Company _company;

	private final GroupLocalService _groupLocalService;

	@Context
	private UriInfo _uriInfo;

}