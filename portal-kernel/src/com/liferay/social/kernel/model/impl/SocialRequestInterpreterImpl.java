/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model.impl;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.model.SocialRequestFeedEntry;
import com.liferay.social.kernel.model.SocialRequestInterpreter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialRequestInterpreterImpl implements SocialRequestInterpreter {

	public SocialRequestInterpreterImpl(
		String portletId, SocialRequestInterpreter requestInterpreter) {

		_portletId = portletId;
		_requestInterpreter = requestInterpreter;

		String[] classNames = requestInterpreter.getClassNames();

		for (String className : classNames) {
			_classNames.add(className);
		}
	}

	@Override
	public String[] getClassNames() {
		return _requestInterpreter.getClassNames();
	}

	public String getPortletId() {
		return _portletId;
	}

	public boolean hasClassName(String className) {
		if (_classNames.contains(className)) {
			return true;
		}

		return false;
	}

	@Override
	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return _requestInterpreter.interpret(request, themeDisplay);
	}

	@Override
	public boolean processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return _requestInterpreter.processConfirmation(request, themeDisplay);
	}

	@Override
	public boolean processRejection(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return _requestInterpreter.processRejection(request, themeDisplay);
	}

	private final Set<String> _classNames = new HashSet<>();
	private final String _portletId;
	private final SocialRequestInterpreter _requestInterpreter;

}