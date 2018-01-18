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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.web.internal.handler.ImportActionExceptionRequestHandler;
import com.liferay.fragment.web.internal.portlet.util.ImportUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/import_fragment_entries"
	},
	service = MVCActionCommand.class
)
public class ImportFragmentEntriesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		long fragmentCollectionId = ParamUtil.getLong(
			uploadPortletRequest, "fragmentCollectionId");

		try {
			InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file");

			ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
				inputStream);

			_importUtil.importFragmentEntries(
				actionRequest, zipReader, fragmentCollectionId,
				StringPool.BLANK);
		}
		catch (Exception e) {
			_importActionExceptionRequestHandler.handleException(
				actionRequest, actionResponse, e);
		}
	}

	@Reference
	private ImportActionExceptionRequestHandler
		_importActionExceptionRequestHandler;

	@Reference
	private ImportUtil _importUtil;

	@Reference
	private Portal _portal;

}