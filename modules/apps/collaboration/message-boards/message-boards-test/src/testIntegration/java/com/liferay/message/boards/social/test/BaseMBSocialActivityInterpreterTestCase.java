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