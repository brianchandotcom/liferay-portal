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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;
import com.liferay.portlet.social.model.SocialRequestInterpreter;

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

		String[] classNames = _requestInterpreter.getClassNames();

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
		else {
			return false;
		}
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

	private final Set<String> _classNames = new HashSet<String>();
	private final String _portletId;
	private final SocialRequestInterpreter _requestInterpreter;

}