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

package com.liferay.portal.osgi.web.portlet.container.upload.test;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;

/**
 * @author Manuel de la Peña
 */
public class TestUploadMVCActionCommand extends BaseMVCActionCommand {

	public TestUploadMVCActionCommand(
		TestUploadPortlet testUploadPortlet,
		UniqueFileNameProvider uniqueFileNameProvider) {

		_testUploadHandler = new TestUploadHandler(
			testUploadPortlet, uniqueFileNameProvider);
	}

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		_testUploadHandler.upload(actionRequest, actionResponse);

		Assert.assertNull(actionRequest.getAttribute(WebKeys.UPLOAD_EXCEPTION));
	}

	private final TestUploadHandler _testUploadHandler;

}