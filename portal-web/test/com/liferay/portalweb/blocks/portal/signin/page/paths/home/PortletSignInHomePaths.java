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

package com.liferay.portalweb.blocks.portal.signin.page.paths.home;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletSignInHomePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = { "", "Portlet Sign In Home Page" };
	private static String[] _ANSWER_FIELD = {
			"//input[@id='reminderQueryAnswer']", ""
		};
	private static String[] _ANSWER_TEXT = {
			"//label[@for='reminderQueryAnswer']", "Answer"
		};
	private static String[] _EMAIL_ADDRESS_FIELD = {
			"//input[@id='_58_login']", "test@liferay.com"
		};
	private static String[] _EMAIL_ADDRESS_TEXT = {
			"//label[@for='_58_login']", "Email Address"
		};
	private static String[] _I_AGREE_LINK = {
			"//input[@value='I Agree']", "I Agree"
		};
	private static String[] _I_DISAGREE_LINK = {
			"//input[@value='I Disagree']", "I Disagree"
		};
	private static String[] _PASSWORD_FIELD = {
			"//input[@id='_58_password']", "test"
		};
	private static String[] _PASSWORD_TEXT = {
			"//label[@for='_58_password']", "Password"
		};
	private static String[] _QUESTION_FIELD = {
			"//select[@id='reminderQueryQuestion']",
			"What is your father's middle name?"
		};
	private static String[] _QUESTION_TEXT = {
			"//label[@for='reminderQueryQuestion']", "Question"
		};
	private static String[] _REMEMBER_ME_LINK = {
			"//input[@id='_58_rememberMeCheckbox']", ""
		};
	private static String[] _REMEMBER_ME_TEXT = {
			"//label[@for='_58_rememberMeCheckbox']", "Remember Me"
		};
	private static String[] _SAVE_LINK = { "//input[@value='Save']", "Save" };
	private static String[] _SIGN_IN_LINK = {
			"//input[@value='Sign In']", "Sign In"
		};
	private static String[] _SIGN_OUT_LINK = {
			"//span[@class='sign-out']/a", "Sign Out"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("ANSWER_FIELD", _ANSWER_FIELD);
		_paths.put("ANSWER_TEXT", _ANSWER_TEXT);
		_paths.put("EMAIL_ADDRESS_FIELD", _EMAIL_ADDRESS_FIELD);
		_paths.put("EMAIL_ADDRESS_TEXT", _EMAIL_ADDRESS_TEXT);
		_paths.put("I_AGREE_LINK", _I_AGREE_LINK);
		_paths.put("I_DISAGREE_LINK", _I_DISAGREE_LINK);
		_paths.put("PASSWORD_FIELD", _PASSWORD_FIELD);
		_paths.put("PASSWORD_TEXT", _PASSWORD_TEXT);
		_paths.put("QUESTION_FIELD", _QUESTION_FIELD);
		_paths.put("QUESTION_TEXT", _QUESTION_TEXT);
		_paths.put("REMEMBER_ME_LINK", _REMEMBER_ME_LINK);
		_paths.put("REMEMBER_ME_TEXT", _REMEMBER_ME_TEXT);
		_paths.put("SAVE_LINK", _SAVE_LINK);
		_paths.put("SIGN_IN_LINK", _SIGN_IN_LINK);
		_paths.put("SIGN_OUT_LINK", _SIGN_OUT_LINK);
	}
}