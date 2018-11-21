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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.social.kernel.model.BaseSocialRequestInterpreter;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.model.SocialRequestFeedEntry;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalRequestInterpreter extends BaseSocialRequestInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialRequestFeedEntry doInterpret(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		return null;
	}

	@Override
	protected boolean doProcessConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		return true;
	}

	private static final String[] _CLASS_NAMES = {
		PortalRequestInterpreter.class.getName()
	};

}