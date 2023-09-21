/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.web.internal.upload.ImageBlogsUploadFileEntryHandler;
import com.liferay.blogs.web.internal.upload.ImageBlogsUploadResponseHandler;
import com.liferay.item.selector.ItemSelectorUploadResponseHandler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsFileUploadsConfiguration",
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/blogs/image_editor",
		"mvc.command.name=/blogs/upload_image"
	},
	service = MVCActionCommand.class
)
public class UploadImageMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			BlogsFileUploadsConfiguration.class, properties);

		_imageBlogsUploadResponseHandler = new ImageBlogsUploadResponseHandler(
			_blogsFileUploadsConfiguration, _itemSelectorUploadResponseHandler);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_imageBlogsUploadFileEntryHandler, _imageBlogsUploadResponseHandler,
			actionRequest, actionResponse);
	}

	private volatile BlogsFileUploadsConfiguration
		_blogsFileUploadsConfiguration;

	@Reference
	private ImageBlogsUploadFileEntryHandler _imageBlogsUploadFileEntryHandler;

	private volatile ImageBlogsUploadResponseHandler
		_imageBlogsUploadResponseHandler;

	@Reference
	private ItemSelectorUploadResponseHandler
		_itemSelectorUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}