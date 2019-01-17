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

package com.liferay.portal.template.soy.internal.util;

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.UnsafeSanitizedContentOrdainer;

import com.liferay.portal.template.soy.util.SoyHTMLSanitizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = SoyHTMLSanitizer.class)
public class SoyHTMLSanitizerImpl implements SoyHTMLSanitizer {

	@Override
	public Object sanitize(String value) {
		return UnsafeSanitizedContentOrdainer.ordainAsSafe(
			value, SanitizedContent.ContentKind.HTML);
	}

}