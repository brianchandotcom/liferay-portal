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

package com.liferay.info.display.contributor;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface InfoDisplayContributor<T> {

	public String getClassName();

	public List<InfoDisplayField> getClassTypeInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException;

	public List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException;

	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException;

	public Map<String, Object> getInfoDisplayFieldsValues(T t, Locale locale)
		throws PortalException;

	public Object getInfoDisplayFieldValue(T t, String fieldName, Locale locale)
		throws PortalException;

	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(long classPK)
		throws PortalException;

	public InfoDisplayObjectProvider<T> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException;

	public String getInfoURLSeparator();

	public String getLabel(Locale locale);

	public default Map<String, Object> getVersionInfoDisplayFieldsValues(
			T t, long versionClassPK, Locale locale)
		throws PortalException {

		return getInfoDisplayFieldsValues(t, locale);
	}

}