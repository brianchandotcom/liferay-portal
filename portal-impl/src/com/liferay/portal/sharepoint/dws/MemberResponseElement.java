/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;

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

		Element el = rootEl.addElement(user);

		el.addElement("ID").setText(_id);
		el.addElement("Name").setText(_name);
		el.addElement("LoginName").setText(_loginName);
		el.addElement("Email").setText(_email);
		el.addElement("IsDomainGroup").setText(String.valueOf(_domainGroup));
		el.addElement("IsSiteAdmin").setText(String.valueOf(_siteAdmin));
	}

	private boolean _domainGroup;
	private String _email;
	private String _id;
	private String _loginName;
	private boolean _member;
	private String _name;
	private boolean _siteAdmin;

}