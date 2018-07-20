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

package com.liferay.fragment.entry.processor.nullable;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=0",
	service = FragmentEntryProcessor.class
)
public class NullableFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryLinkHTML(
		FragmentEntryLink fragmentEntryLink, String html, String mode) {

		return html;
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		if (Validator.isNull(html)) {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(Locale.getDefault());

			String message = LanguageUtil.get(
				resourceBundle, "html-content-must-not-be-empty");

			throw new FragmentEntryContentException(message);
		}
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.fragment.entry.processor.nullable)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}