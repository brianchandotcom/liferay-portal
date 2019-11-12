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

package com.liferay.fragment.entry.processor.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.Map;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper}
 */
@Deprecated
@ProviderType
public interface FragmentEntryProcessorUtil {

	public String getEditableValue(
		JSONObject jsonObject, Locale locale, long[] segmentsExperienceIds);

	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException;

	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues, String mode,
			Locale locale, long previewClassPK, int previewType)
		throws PortalException;

	public boolean isAssetDisplayPage(String mode);

	public boolean isMapped(JSONObject jsonObject);

	public String processTemplate(
			String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException;

}