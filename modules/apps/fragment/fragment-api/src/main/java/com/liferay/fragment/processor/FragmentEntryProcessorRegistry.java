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

package com.liferay.fragment.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Lance Ji
 */
public interface FragmentEntryProcessorRegistry {

	public JSONObject getDefaultEditableValuesJSONObject(String html);

	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink)
		throws PortalException;

	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String mode)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, mode, LocaleUtil.getMostRelevantLocale());
	}

	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String mode, Locale locale)
		throws PortalException;

	public void validateFragmentEntryHTML(String html) throws PortalException;

}