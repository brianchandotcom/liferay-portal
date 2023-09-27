/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.taglib.internal.context;

import com.liferay.portal.kernel.comment.display.context.CommentDisplayContext;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.security.sso.SSOUtil;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCommentDisplayContext
	implements CommentDisplayContext {

	@Override
	public boolean isReplyButtonVisible() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		Group group = themeDisplay.getSiteGroup();

		if (group.isStagingGroup() || group.isStagedRemotely()) {
			return false;
		}

		if (themeDisplay.isSignedIn() ||
			!SSOUtil.isLoginRedirectRequired(themeDisplay.getCompanyId())) {

			return true;
		}

		return false;
	}

	protected abstract ThemeDisplay getThemeDisplay();

}