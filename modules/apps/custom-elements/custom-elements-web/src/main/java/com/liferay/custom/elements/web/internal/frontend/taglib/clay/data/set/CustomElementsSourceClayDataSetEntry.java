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

package com.liferay.custom.elements.web.internal.frontend.taglib.clay.data.set;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementsSourceClayDataSetEntry {

	public CustomElementsSourceClayDataSetEntry(
		CustomElementsSource customElementsSource) {

		_customElementsSource = customElementsSource;
	}

	public long getCustomElementsSourceId() {
		return _customElementsSource.getCustomElementsSourceId();
	}

	public String getHtmlElementName() {
		return _customElementsSource.getHTMLElementName();
	}

	public String getMainURL() {
		String urls = _customElementsSource.getURLs();

		if (Validator.isBlank(urls)) {
			return StringPool.BLANK;
		}

		String[] urlsArray = urls.split(StringPool.NEW_LINE);

		return urlsArray[0];
	}

	public String getName() {
		return _customElementsSource.getName();
	}

	private final CustomElementsSource _customElementsSource;

}