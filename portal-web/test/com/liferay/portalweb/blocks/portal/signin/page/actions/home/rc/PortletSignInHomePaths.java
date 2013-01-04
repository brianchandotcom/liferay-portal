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

package com.liferay.portalweb.blocks.portal.signin.page.actions.home.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletSignInHomePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("TOP", "relative=top");
		_paths.put("PAGE_NAME", "");
		_paths.put("ANSWER_FIELD", "//input[@id='reminderQueryAnswer']");
		_paths.put("ANSWER_TEXT", "//label[@for='reminderQueryAnswer']");
		_paths.put("EMAIL_ADDRESS_FIELD", "//input[@id='_58_login']");
		_paths.put("EMAIL_ADDRESS_TEXT", "//label[@for='_58_login']");
		_paths.put("I_AGREE_LINK", "//input[@value='I Agree']");
		_paths.put("I_DISAGREE_LINK", "//input[@value='I Disagree']");
		_paths.put("NEW_PASSWORD_ENTER_AGAIN_FIELD", "//input[@id='password2']");
		_paths.put("NEW_PASSWORD_FIELD", "//input[@id='password1']");
		_paths.put("PASSWORD_FIELD", "//input[@id='_58_password']");
		_paths.put("PASSWORD_TEXT", "//label[@for='_58_password']");
		_paths.put("QUESTION_FIELD", "//select[@id='reminderQueryQuestion']");
		_paths.put("QUESTION_TEXT", "//label[@for='reminderQueryQuestion']");
		_paths.put("REMEMBER_ME_LINK", "//input[@id='_58_rememberMeCheckbox']");
		_paths.put("REMEMBER_ME_TEXT", "//label[@for='_58_rememberMeCheckbox']");
		_paths.put("SAVE_LINK", "//input[@value='Save']");
		_paths.put("SIGN_IN_LINK", "//input[@value='Sign In']");
		_paths.put("SIGN_OUT_LINK", "//span[@class='sign-out']/a");
	}
}