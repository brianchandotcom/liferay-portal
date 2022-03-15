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

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.utils.AnnotationUtils;

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

		StringBundler sb = new StringBundler(4);

		sb.append("Unable to get a valid ");

		if (multivaluedMap.containsKey("assetLibraryId")) {
			sb.append("asset library");
		}
		else {
			sb.append("site");
		}

		sb.append(" with ID ");
		sb.append(parameter);

		throw new NotFoundException(sb.toString());
	}

	@Override
	public <T> ParamConverter<T> getConverter(
		Class<T> clazz, Type type, Annotation[] annotations) {

		if (Long.class.equals(clazz) && _hasSiteIdAnnotation(annotations)) {
			return (ParamConverter<T>)this;
		}

		return null;
	}

	public Long getDepotGroupId(String assetLibraryKey, long companyId) {
		if (assetLibraryKey == null) {
			return null;
		}

		return GroupUtil.getDepotGroupId(
			assetLibraryKey, companyId, _depotEntryLocalService,
			_groupLocalService);
	}

	public Long getGroupId(long companyId, String siteKey) {
		if (siteKey == null) {
			return null;
		}

		return GroupUtil.getGroupId(companyId, siteKey, _groupLocalService);
	}

	@Override
	public String toString(Long parameter) {
		return String.valueOf(parameter);
	}

	private boolean _hasSiteIdAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if ((annotation.annotationType() == PathParam.class) &&
				StringUtils.equalsAny(
					AnnotationUtils.getAnnotationValue(annotation),
					"assetLibraryId", "siteId")) {

				return true;
			}
		}

		return false;
	}

	@Context
	private Company _company;

	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;

	@Context
	private UriInfo _uriInfo;

}