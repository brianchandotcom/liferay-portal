/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public String getHref() {
		return _href;
	}

	@Override
	public String getHrefLang() {
		return _hrefLang;
	}

	@Override
	public Relationship getRelationship() {
		return _relationship;
	}

	private final String _href;
	private final String _hrefLang;
	private final Relationship _relationship;

}