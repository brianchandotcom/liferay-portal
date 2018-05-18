/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.asset.categories.web.internal.portlet.action;

import com.liferay.commerce.product.asset.categories.web.internal.upload.AssetCategoryAttachmentsUploadResponseHandler;
import com.liferay.commerce.product.asset.categories.web.internal.upload.TempAssetCategoryAttachmentsUploadFileEntryHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_asset_categories_admin_web_portlet_AssetCategoriesAdminPortlet",
		"mvc.command.name=uploadTempCategoryAttachment"
	},
	service = MVCActionCommand.class
)
public class UploadTempAssetCategoryAttachmentMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_tempAssetCategoryAttachmentsUploadFileEntryHandler,
			_assetCategoryAttachmentsUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private AssetCategoryAttachmentsUploadResponseHandler
		_assetCategoryAttachmentsUploadResponseHandler;

	@Reference
	private TempAssetCategoryAttachmentsUploadFileEntryHandler
		_tempAssetCategoryAttachmentsUploadFileEntryHandler;

	@Reference
	private UploadHandler _uploadHandler;

}