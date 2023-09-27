/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.social.test;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.social.activity.test.util.BaseSocialActivityInterpreterTestCase;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseMBSocialActivityInterpreterTestCase
	extends BaseSocialActivityInterpreterTestCase {

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return getActivityInterpreter(
			MBPortletKeys.MESSAGE_BOARDS, getClassName());
	}

	protected abstract String getClassName();

}