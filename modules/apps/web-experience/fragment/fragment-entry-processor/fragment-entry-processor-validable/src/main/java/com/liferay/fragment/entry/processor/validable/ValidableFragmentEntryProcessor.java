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

package com.liferay.fragment.entry.processor.validable;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"fragment.entry.processor.priority:Integer=1"},
	service = FragmentEntryProcessor.class
)
public class ValidableFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryHTML(String html, JSONObject jsonObject)
		throws PortalException {

		return html;
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		if (!Jsoup.isValid(html, Whitelist.relaxed())) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			String message = LanguageUtil.get(
				resourceBundle, "you-have-entered-invalid-html");

			throw new FragmentEntryContentException(message);
		}
	}

}