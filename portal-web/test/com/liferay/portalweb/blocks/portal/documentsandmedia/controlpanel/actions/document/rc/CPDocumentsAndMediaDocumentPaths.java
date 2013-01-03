/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.document.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("BREADCRUMB_1", "//div[@id='breadcrumbs']/ul/li[1]/span/a");
		_paths.put("BREADCRUMB_2", "//div[@id='breadcrumbs']/ul/li[2]/span/a");
		_paths.put("BREADCRUMB_3", "//div[@id='breadcrumbs']/ul/li[3]/span/a");
		_paths.put("HEADER_PORTLET_TITLE", "//span[@class='portlet-title-text']");
		_paths.put("HEADER_PORTLET_INFO", "//div[@id='cpContextPanelTemplate']");
		_paths.put("HEADER_OPTIONS_ICON",
			"//span[@title='Options']/ul/li/strong/a");
		_paths.put("HEADER_PAGE_TITLE", "//h1[@class='header-title']/span");
		_paths.put("HEADER_BACK_BUTTON", "//a[@id='_20_TabsBack']");
		_paths.put("TOOLBAR_DOWNLOAD", "//button/span[.='Download']");
		_paths.put("TOOLBAR_EDIT", "//button/span[.='Edit']");
		_paths.put("TOOLBAR_MOVE", "//button/span[.='Move']");
		_paths.put("TOOLBAR_CANCEL_CHECKOUT",
			"//button/span[.='Cancel Checkout']");
		_paths.put("TOOLBAR_CHECKOUT", "//button/span[.='Checkout']");
		_paths.put("TOOLBAR_CHECKIN", "//button/span[.='Checkin']");
		_paths.put("TOOLBAR_PERMISSIONS", "//button/span[.='Permissions']");
		_paths.put("TOOLBAR_MOVE_TO_THE_RECYCLE_BIN",
			"//button/span[.='Move to the Recycle Bin']");
		_paths.put("DOCUMENT_LOCK_MESSAGE",
			"//div[@class='portlet-msg-lock portlet-msg-success']");
		_paths.put("DOCUMENT_INFO_TITLE", "//h2[@class='document-title']");
		_paths.put("DOCUMENT_INFO_DATE_UPLOADED",
			"//span[@class='user-date']/span/span");
		_paths.put("DOCUMENT_INFO_UPLOADER",
			"//span[@class='user-date']/span/span/a");
		_paths.put("DOCUMENT_INFO_AVERAGE_RATING",
			"//div[contains(@id,'_ratingScoreContent')]/div[contains(.,'Average')]");
		_paths.put("DOCUMENT_INFO_YOUR_RATING",
			"//div[contains(@id,'_ratingStarContent')]/div[contains(.,'Your Rating')]");
		_paths.put("VERSION_INFO_HEADER", "//h3[contains(@class,'version')]");
		_paths.put("VERSION_INFO_UPDATER",
			"//div[contains(@class,'lfr-asset-author')]");
		_paths.put("VERSION_INFO_UPDATE_DATE",
			"//div[contains(@class,'lfr-asset-date')]");
		_paths.put("VERSION_INFO_WORKFLOW_STATUS",
			"//span[@class='workflow-status']");
		_paths.put("DOWNLOAD_LINK",
			"//span[@class='download-document']/span/a/span");
		_paths.put("DOWNLOAD_URL_LINK", "//a[@class='show-url-file']");
		_paths.put("DOWNLOAD_URL_FIELD",
			"//label[.='URL']/following-sibling::input");
		_paths.put("DOWNLOAD_WEBDAV_LINK", "//a[@class='show-webdav-url-file']");
		_paths.put("DOWNLOAD_WEBDAV_FIELD",
			"//label[contains(.,'WebDAV URL')]/following-sibling::input");
		_paths.put("VERSION_HISTORY_COMPARE_VERSIONS",
			"//input[@value='Compare Versions']");
		_paths.put("VERSION_HISTORY_CHECKBOX_1",
			"//tr[3]/td/input[@name='_20_rowIds']");
		_paths.put("VERSION_HISTORY_VERSION_1",
			"//tr[3]/td[contains(@class,'col-version')]");
		_paths.put("VERSION_HISTORY_DATE_1",
			"//tr[3]/td[contains(@class,'col-date')]");
		_paths.put("VERSION_HISTORY_SIZE_1",
			"//tr[3]/td[contains(@class,'col-size')]");
		_paths.put("VERSION_HISTORY_STATUS_1",
			"//tr[3]/td[contains(@class,'col-status')]");
		_paths.put("VERSION_HISTORY_ACTIONS_BUTTON_1",
			"//tr[3]/td/span/ul/li/strong/a");
		_paths.put("VERSION_HISTORY_CHECKBOX_2",
			"//tr[4]/td/input[@name='_20_rowIds']");
		_paths.put("VERSION_HISTORY_VERSION_2",
			"//tr[4]/td[contains(@class,'col-version')]");
		_paths.put("VERSION_HISTORY_DATE_2",
			"//tr[4]/td[contains(@class,'col-date')]");
		_paths.put("VERSION_HISTORY_SIZE_2",
			"//tr[4]/td[contains(@class,'col-size')]");
		_paths.put("VERSION_HISTORY_STATUS_2",
			"//tr[4]/td[contains(@class,'col-status')]");
		_paths.put("VERSION_HISTORY_ACTIONS_BUTTON_2",
			"//tr[4]/td/span/ul/li/strong/a");
		_paths.put("VERSION_HISTORY_CHECKBOX_3",
			"//tr[5]/td/input[@name='_20_rowIds']");
		_paths.put("VERSION_HISTORY_VERSION_3",
			"//tr[5]/td[contains(@class,'col-version')]");
		_paths.put("VERSION_HISTORY_DATE_3",
			"//tr[5]/td[contains(@class,'col-date')]");
		_paths.put("VERSION_HISTORY_SIZE_3",
			"//tr[5]/td[contains(@class,'col-size')]");
		_paths.put("VERSION_HISTORY_STATUS_3",
			"//tr[5]/td[contains(@class,'col-status')]");
		_paths.put("VERSION_HISTORY_ACTIONS_BUTTON_3",
			"//tr[5]/td/span/ul/li/strong/a");
		_paths.put("COMMENT_MESSAGE_SUCCESS",
			"//div[@id='_20_discussion-status-messages']");
		_paths.put("COMMENT_MESSAGE_INFO",
			"//fieldset[contains(@class,'add-comment')]/div");
		_paths.put("COMMENT_LINK_ADD", "//a[contains(@href,'_postReplyBody0')]");
		_paths.put("COMMENT_LINK_SUBSCRIBE",
			"//span[@class='subscribe-link']/a/span[.='Subscribe to Comments']");
		_paths.put("COMMENT_LINK_UNSUBSCRIBE",
			"//span[@class='subscribe-link']/a/span[.='Unsubscribe from Comments']");
		_paths.put("COMMENT_TEXTAREA",
			"//textarea[contains(@id,'_postReplyBody0')]");
		_paths.put("COMMENT_BUTTON_REPLY", "//input[@value='Reply']");
		_paths.put("COMMENT_BUTTON_CANCEL", "//input[@value='Cancel']");
		_paths.put("COMMENT_VIEW_MESSAGE_1",
			"xpath=(//div[@class='lfr-discussion-message'])[1]");
		_paths.put("COMMENT_VIEW_MESSAGE_2",
			"xpath=(//div[@class='lfr-discussion-message'])[2]");
		_paths.put("COMMENT_VIEW_MESSAGE_3",
			"xpath=(//div[@class='lfr-discussion-message'])[3]");
	}
}