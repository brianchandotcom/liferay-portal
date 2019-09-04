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

package com.liferay.layout.seo.internal;

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Adolfo Pérez
 */
public class LayoutSEOLinkImpl implements LayoutSEOLink {

	public LayoutSEOLinkImpl(
		String href, String hrefLang, Relationship relationship) {

		if (Validator.isNull(href)) {
			throw new IllegalArgumentException("HREF is null");
		}

		if (relationship == null) {
			throw new IllegalArgumentException("Relationship is null");
		}

		_href = href;
		_hrefLang = hrefLang;
		_relationship = relationship;
	}

	public String getHref() {
		return _href;
	}

	public String getHrefLang() {
		return _hrefLang;
	}

	public Relationship getRelationship() {
		return _relationship;
	}

	private final String _href;
	private final String _hrefLang;
	private final Relationship _relationship;

}