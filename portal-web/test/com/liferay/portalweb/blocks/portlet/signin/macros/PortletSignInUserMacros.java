/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.blocks.portlet.signin.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.actions.HomeActions;
import com.liferay.portalweb.blocks.portlet.signin.actions.PortletSignInActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletSignInUserMacros extends BaseMacros {
	public PortletSignInUserMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void signIn(String email, String password) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);
		PortletSignInActions portletSignInActions = new PortletSignInActions(selenium);

		homeActions.open("URL_GUEST", null);
		portletSignInActions.type("EMAIL_ADDRESS_FIELD", email);
		portletSignInActions.type("PASSWORD_FIELD", password);
		portletSignInActions.check("REMEMBER_ME_LINK", null);
		portletSignInActions.click("SIGN_IN_LINK", null);

		if (portletSignInActions.isElementPresent("I_AGREE_LINK", "")) {
			portletSignInActions.click("I_AGREE_LINK", null);
		}

		if (portletSignInActions.isElementPresent("ANSWER_FIELD", "")) {
			portletSignInActions.type("ANSWER_FIELD", "test");
			portletSignInActions.click("SAVE_LINK", null);
		}
	}

	public void signOut() throws Exception {
		HomeActions homeActions = new HomeActions(selenium);
		PortletSignInActions portletSignInActions = new PortletSignInActions(selenium);

		homeActions.open("URL_GUEST", null);
		portletSignInActions.click("SIGN_OUT_LINK", null);
	}
}