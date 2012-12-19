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

package com.liferay.portalweb.blocks.portal.signin.page.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.home.page.actions.HomeActions;
import com.liferay.portalweb.blocks.portal.signin.page.actions.home.PortletSignInHomeActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class SignInUserMacros extends BaseMacros {
	public SignInUserMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void signIn(String email, String password) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);
		PortletSignInHomeActions portletSignInHomeActions = new PortletSignInHomeActions(selenium);

		homeActions.open("URL_GUEST", null);
		portletSignInHomeActions.type("EMAIL_ADDRESS_FIELD", email);
		portletSignInHomeActions.type("PASSWORD_FIELD", password);
		portletSignInHomeActions.check("REMEMBER_ME_LINK", null);
		portletSignInHomeActions.click("SIGN_IN_LINK", null);

		if (portletSignInHomeActions.isElementPresent("I_AGREE_LINK", "")) {
			portletSignInHomeActions.click("I_AGREE_LINK", null);
		}

		if (portletSignInHomeActions.isElementPresent("ANSWER_FIELD", "")) {
			portletSignInHomeActions.type("ANSWER_FIELD", "test");
			portletSignInHomeActions.click("SAVE_LINK", null);
		}
	}

	public void signOut() throws Exception {
		HomeActions homeActions = new HomeActions(selenium);
		PortletSignInHomeActions portletSignInHomeActions = new PortletSignInHomeActions(selenium);

		homeActions.open("URL_GUEST", null);
		portletSignInHomeActions.click("SIGN_OUT_LINK", null);
	}
}