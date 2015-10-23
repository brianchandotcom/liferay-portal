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

package com.liferay.portal.portlet.container.upload.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadHandler;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = MVCActionCommand.class)
public class TestUploadMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		_uploadHandler.upload(actionRequest, actionResponse);

		Assert.assertNull(actionRequest.getAttribute(WebKeys.UPLOAD_EXCEPTION));
	}

	private final UploadHandler _uploadHandler = new TestUploadHandler();

}