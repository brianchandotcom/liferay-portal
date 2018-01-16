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

package com.liferay.portal.sharepoint.dws;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Bruno Farache
 */
public class MemberResponseElement implements ResponseElement {

	public MemberResponseElement(User user, boolean member) {
		_id = user.getScreenName();
		_name = user.getFullName();
		_loginName = user.getScreenName();
		_email = user.getEmailAddress();
		_domainGroup = false;
		_member = member;
		_siteAdmin = false;
	}

	@Override
	public void addElement(Element rootEl) {
		String user = "User";

		if (_member) {
			user = "Member";
		}

		Element userEl = rootEl.addElement(user);

		Element idEl = userEl.addElement("ID");

		idEl.setText(_id);

		Element nameEl = userEl.addElement("Name");

		nameEl.setText(_name);

		Element loginNameEl = userEl.addElement("LoginName");

		loginNameEl.setText(_loginName);

		Element emailEl = userEl.addElement("Email");

		emailEl.setText(_email);

		Element isDomainGroupEl = userEl.addElement("IsDomainGroup");

		isDomainGroupEl.setText(String.valueOf(_domainGroup));

		Element isSiteAdminEl = userEl.addElement("IsSiteAdmin");

		isSiteAdminEl.setText(String.valueOf(_siteAdmin));
	}

	private final boolean _domainGroup;
	private final String _email;
	private final String _id;
	private final String _loginName;
	private final boolean _member;
	private final String _name;
	private final boolean _siteAdmin;

}