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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.mail.MailServiceTestUtil;

import javax.portlet.PortletPreferences;

import org.junit.runner.Description;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 * @author Shuyang Zhou
 */
public class SynchronousMailTestRule extends SynchronousDestinationTestRule {

	public static final SynchronousMailTestRule INSTANCE =
		new SynchronousMailTestRule();

	@Override
	public void afterClass(Description description, SyncHandler syncHandler)
		throws Exception {

		_setCompanyAdminEmailFromAddress(
			TestPropsValues.getCompanyId(), _adminEmailFromAddress);

		MailServiceTestUtil.stop();
	}

	@Override
	public void afterMethod(
		Description description, SyncHandler syncHandler, Object target) {

		MailServiceTestUtil.clearMessages();
	}

	@Override
	public SyncHandler beforeClass(Description description) throws Throwable {
		MailServiceTestUtil.start();

		_adminEmailFromAddress = PrefsPropsUtil.getString(
			TestPropsValues.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		_setCompanyAdminEmailFromAddress(
			TestPropsValues.getCompanyId(), "integration-test@liferay.com");

		return null;
	}

	private SynchronousMailTestRule() {
	}

	private void _setCompanyAdminEmailFromAddress(
			long companyId, String adminEmailFromAddress)
		throws Exception {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		preferences.setValue(
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS, adminEmailFromAddress);

		preferences.store();
	}

	private String _adminEmailFromAddress;

}