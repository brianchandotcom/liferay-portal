/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint.dws;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Bruno Farache
 */
public class RoleResponseElement implements ResponseElement {

	public RoleResponseElement(Role role) {
		_name = role.getName();
		_description = role.getDescription();
		_type = role.getTypeLabel();
	}

	@Override
	public void addElement(Element rootEl) {
		Element el = rootEl.addElement("Role");

		el.addAttribute("Name", _name);
		el.addAttribute("Description", _description);
		el.addAttribute("Type", _type);
	}

	private final String _description;
	private final String _name;
	private final String _type;

}