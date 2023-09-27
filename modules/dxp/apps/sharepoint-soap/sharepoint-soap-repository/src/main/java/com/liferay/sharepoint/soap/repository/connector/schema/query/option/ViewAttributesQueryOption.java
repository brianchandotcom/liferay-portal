/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.option;

import com.liferay.portal.kernel.xml.simple.Element;

/**
 * @author Iván Zaera
 */
public class ViewAttributesQueryOption extends BaseQueryOption {

	public ViewAttributesQueryOption(boolean recursive) {
		_recursive = recursive;
	}

	@Override
	protected String getNodeName() {
		return "ViewAttributes";
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		if (_recursive) {
			element.addAttribute("Scope", "RecursiveAll");
		}
	}

	private final boolean _recursive;

}