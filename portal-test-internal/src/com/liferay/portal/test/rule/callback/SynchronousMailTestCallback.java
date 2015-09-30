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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.test.rule.callback.SynchronousDestinationTestCallback;
import com.liferay.portal.util.test.MailServiceTestUtil;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class SynchronousMailTestCallback
	extends SynchronousDestinationTestCallback {

	public static final SynchronousMailTestCallback INSTANCE =
		new SynchronousMailTestCallback();

	@Override
	public void doAfterClass(Description description, SyncHandler syncHandler)
		throws Exception {

		super.doAfterClass(description, syncHandler);

		MailServiceTestUtil.stop();
	}

	@Override
	public void doAfterMethod(
		Description description, SyncHandler syncHandler, Object target) {

		super.doAfterMethod(description, syncHandler, target);

		MailServiceTestUtil.clearMessages();
	}

	@Override
	public SyncHandler doBeforeClass(Description description) throws Throwable {
		MailServiceTestUtil.start();

		SyncHandler syncHandler = super.doBeforeClass(description);

		syncHandler.replaceDestination(DestinationNames.MAIL);

		return syncHandler;
	}

	private SynchronousMailTestCallback() {
	}

}