/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Brian Wing Shun Chan
 */
public interface SocialRequestInterpreter {

	public String[] getClassNames();

	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay);

	public boolean processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay);

	public boolean processRejection(
		SocialRequest request, ThemeDisplay themeDisplay);

}