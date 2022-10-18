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

package com.liferay.fragment.entry.processor.helper;

import aQute.bnd.annotation.ProviderType;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface FragmentEntryProcessorHelper {

	public String formatMappedValue(Object value, Locale locale);

	public String getEditableValue(JSONObject jsonObject, Locale locale);

	public long getFileEntryId(
			long classNameId, long classPK, String fieldName, Locale locale)
		throws PortalException;

	public long getFileEntryId(
		Object displayObject, String fieldName, Locale locale);

	public long getFileEntryId(String className, long classPK);

	public long getFileEntryId(WebImage webImage);

	public Object getMappedCollectionValue(
			Optional<Object> displayObjectOptional, JSONObject jsonObject,
			Locale locale)
		throws PortalException;

	public Object getMappedInfoItemFieldValue(
			JSONObject jsonObject,
			Map<Long, InfoItemFieldValues> infoItemFieldValuesMap,
			Locale locale, String mode, long previewClassPK,
			String previewVersion)
		throws PortalException;

	public Object getMappedInfoItemFieldValue(
		String fieldName,
		InfoItemFieldValuesProvider infoItemFieldValuesProvider, Locale locale,
		Object object);

	public boolean isAssetDisplayPage(String mode);

	public boolean isMapped(JSONObject jsonObject);

	public boolean isMappedCollection(JSONObject jsonObject);

	public boolean isMappedLayout(JSONObject jsonObject);

}