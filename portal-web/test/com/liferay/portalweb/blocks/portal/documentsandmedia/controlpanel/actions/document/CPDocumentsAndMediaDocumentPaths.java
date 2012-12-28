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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Documents and Media Document Page"
		};
	private static String[] _BREADCRUMB_1 = {
			"//div[@id='breadcrumbs']/ul/li[1]/span/a", "1st Breadcrumb"
		};
	private static String[] _BREADCRUMB_2 = {
			"//div[@id='breadcrumbs']/ul/li[2]/span/a", "2nd Breadcrumb"
		};
	private static String[] _BREADCRUMB_3 = {
			"//div[@id='breadcrumbs']/ul/li[3]/span/a", "3rd Breadcrumb"
		};
	private static String[] _HEADER_PORTLET_TITLE = {
			"//span[@class='portlet-title-text']", "Portlet Title Header"
		};
	private static String[] _HEADER_PORTLET_INFO = {
			"//div[@id='cpContextPanelTemplate']", "Portlet Information Header"
		};
	private static String[] _HEADER_OPTIONS_ICON = {
			"//span[@title='Options']/ul/li/strong/a", "Options Icon"
		};
	private static String[] _HEADER_PAGE_TITLE = {
			"//h1[@class='header-title']/span", "Page Title"
		};
	private static String[] _HEADER_BACK_BUTTON = {
			"//a[@id='_20_TabsBack']", "Back Button"
		};
	private static String[] _TOOLBAR_DOWNLOAD = {
			"//button/span[.='Download']", "Document Download Button"
		};
	private static String[] _TOOLBAR_EDIT = {
			"//button/span[.='Edit']", "Document Edit Button"
		};
	private static String[] _TOOLBAR_MOVE = {
			"//button/span[.='Move']", "Document Move Button"
		};
	private static String[] _TOOLBAR_CANCEL_CHECKOUT = {
			"//button/span[.='Cancel Checkout']",
			"Document Cancel Checkout Button"
		};
	private static String[] _TOOLBAR_CHECKOUT = {
			"//button/span[.='Checkout']", "Document Checkout Button"
		};
	private static String[] _TOOLBAR_CHECKIN = {
			"//button/span[.='Checkin']", "Document Checkin Button"
		};
	private static String[] _TOOLBAR_PERMISSIONS = {
			"//button/span[.='Permissions']", "Document Permissions Button"
		};
	private static String[] _TOOLBAR_MOVE_TO_THE_RECYCLE_BIN = {
			"//button/span[.='Move to the Recycle Bin']",
			"Document Move to the Recycle Bin Button"
		};
	private static String[] _DOCUMENT_LOCK_MESSAGE = {
			"//div[@class='portlet-msg-lock portlet-msg-success']",
			"Document Lock Message"
		};
	private static String[] _DOCUMENT_INFO_TITLE = {
			"//h2[@class='document-title']", "Document Title"
		};
	private static String[] _DOCUMENT_INFO_DATE_UPLOADED = {
			"//span[@class='user-date']/span/span", "Document Upload Date"
		};
	private static String[] _DOCUMENT_INFO_UPLOADER = {
			"//span[@class='user-date']/span/span/a", "Document Uploader"
		};
	private static String[] _DOCUMENT_INFO_AVERAGE_RATING = {
			"//div[contains(@id,'_ratingScoreContent')]/div[contains(.,'Average')]",
			"Average Document Rating"
		};
	private static String[] _DOCUMENT_INFO_YOUR_RATING = {
			"//div[contains(@id,'_ratingStarContent')]/div[contains(.,'Your Rating')]",
			"Your Document Rating"
		};
	private static String[] _VERSION_INFO_HEADER = {
			"//h3[contains(@class,'version')]", "Document Version Header"
		};
	private static String[] _VERSION_INFO_UPDATER = {
			"//div[contains(@class,'lfr-asset-author')]",
			"Document's Last Updater"
		};
	private static String[] _VERSION_INFO_UPDATE_DATE = {
			"//div[contains(@class,'lfr-asset-date')]",
			"Document's Last Update Date"
		};
	private static String[] _VERSION_INFO_WORKFLOW_STATUS = {
			"//span[@class='workflow-status']", "Document's Current Status"
		};
	private static String[] _DOWNLOAD_LINK = {
			"//span[@class='download-document']/span/a/span",
			"Document Download Link"
		};
	private static String[] _DOWNLOAD_URL_LINK = {
			"//a[@class='show-url-file']", "Document 'URL' Link"
		};
	private static String[] _DOWNLOAD_URL_FIELD = {
			"//label[.='URL']/following-sibling::input", "Document 'URL' Field"
		};
	private static String[] _DOWNLOAD_WEBDAV_LINK = {
			"//a[@class='show-webdav-url-file']", "Document 'WebDAV URL' Link"
		};
	private static String[] _DOWNLOAD_WEBDAV_FIELD = {
			"//label[contains(.,'WebDAV URL')]/following-sibling::input",
			"Document 'WebDAV URL' Field"
		};
	private static String[] _VERSION_HISTORY_COMPARE_VERSIONS = {
			"//input[@value='Compare Versions']", "Compare Versions Button"
		};
	private static String[] _VERSION_HISTORY_CHECKBOX_1 = {
			"//tr[3]/td/input[@name='_20_rowIds']", "1st Table Checkbox"
		};
	private static String[] _VERSION_HISTORY_VERSION_1 = {
			"//tr[3]/td[contains(@class,'col-version')]",
			"1st Table 'Version' Entry"
		};
	private static String[] _VERSION_HISTORY_DATE_1 = {
			"//tr[3]/td[contains(@class,'col-date')]", "1st Table 'Date' Entry"
		};
	private static String[] _VERSION_HISTORY_SIZE_1 = {
			"//tr[3]/td[contains(@class,'col-size')]", "1st Table 'Size' Entry"
		};
	private static String[] _VERSION_HISTORY_STATUS_1 = {
			"//tr[3]/td[contains(@class,'col-status')]",
			"1st Table 'Status' Entry"
		};
	private static String[] _VERSION_HISTORY_ACTIONS_BUTTON_1 = {
			"//tr[3]/td/span/ul/li/strong/a", "1st Table Actions Button"
		};
	private static String[] _VERSION_HISTORY_CHECKBOX_2 = {
			"//tr[4]/td/input[@name='_20_rowIds']", "2nd Version Checkbox"
		};
	private static String[] _VERSION_HISTORY_VERSION_2 = {
			"//tr[4]/td[contains(@class,'col-version')]",
			"2nd Table 'Version' Entry"
		};
	private static String[] _VERSION_HISTORY_DATE_2 = {
			"//tr[4]/td[contains(@class,'col-date')]", "2nd Table 'Date' Entry"
		};
	private static String[] _VERSION_HISTORY_SIZE_2 = {
			"//tr[4]/td[contains(@class,'col-size')]", "2nd Table 'Size' Entry"
		};
	private static String[] _VERSION_HISTORY_STATUS_2 = {
			"//tr[4]/td[contains(@class,'col-status')]",
			"2nd Table 'Status' Entry"
		};
	private static String[] _VERSION_HISTORY_ACTIONS_BUTTON_2 = {
			"//tr[4]/td/span/ul/li/strong/a", "2nd Table Actions Button"
		};
	private static String[] _VERSION_HISTORY_CHECKBOX_3 = {
			"//tr[5]/td/input[@name='_20_rowIds']", "3rd Table Checkbox"
		};
	private static String[] _VERSION_HISTORY_VERSION_3 = {
			"//tr[5]/td[contains(@class,'col-version')]",
			"3rd Table 'Version' Entry"
		};
	private static String[] _VERSION_HISTORY_DATE_3 = {
			"//tr[5]/td[contains(@class,'col-date')]", "3rd Table 'Date' Entry"
		};
	private static String[] _VERSION_HISTORY_SIZE_3 = {
			"//tr[5]/td[contains(@class,'col-size')]", "3rd Table 'Size' Entry"
		};
	private static String[] _VERSION_HISTORY_STATUS_3 = {
			"//tr[5]/td[contains(@class,'col-status')]",
			"3rd Table 'Status' Entry"
		};
	private static String[] _VERSION_HISTORY_ACTIONS_BUTTON_3 = {
			"//tr[5]/td/span/ul/li/strong/a", "3rd Table Actions Button"
		};
	private static String[] _COMMENT_MESSAGE_SUCCESS = {
			"//div[@id='_20_discussion-status-messages']",
			"Document Comment Success Message"
		};
	private static String[] _COMMENT_MESSAGE_INFO = {
			"//fieldset[contains(@class,'add-comment')]/div",
			"Document Comment Information Message"
		};
	private static String[] _COMMENT_LINK_ADD = {
			"//a[contains(@href,'_postReplyBody0')]", "Add Comment Link"
		};
	private static String[] _COMMENT_LINK_SUBSCRIBE = {
			"//span[@class='subscribe-link']/a/span[.='Subscribe to Comments']",
			"Document's Subscribe to Comments Link"
		};
	private static String[] _COMMENT_LINK_UNSUBSCRIBE = {
			"//span[@class='subscribe-link']/a/span[.='Unsubscribe from Comments']",
			"Document's Unsubscribe from Comments Link"
		};
	private static String[] _COMMENT_TEXTAREA = {
			"//textarea[contains(@id,'_postReplyBody0')]",
			"Document Comment Textarea"
		};
	private static String[] _COMMENT_BUTTON_REPLY = {
			"//input[@value='Reply']", "Document Comment Reply Button"
		};
	private static String[] _COMMENT_BUTTON_CANCEL = {
			"//input[@value='Cancel']", "Document Comment Cancel Button"
		};
	private static String[] _COMMENT_VIEW_MESSAGE_1 = {
			"xpath=(//div[@class='lfr-discussion-message'])[1]",
			"1st Document Comment Message"
		};
	private static String[] _COMMENT_VIEW_MESSAGE_2 = {
			"xpath=(//div[@class='lfr-discussion-message'])[2]",
			"2nd Document Comment Message"
		};
	private static String[] _COMMENT_VIEW_MESSAGE_3 = {
			"xpath=(//div[@class='lfr-discussion-message'])[3]",
			"3rd Document Comment Message"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("BREADCRUMB_1", _BREADCRUMB_1);
		_paths.put("BREADCRUMB_2", _BREADCRUMB_2);
		_paths.put("BREADCRUMB_3", _BREADCRUMB_3);
		_paths.put("HEADER_PORTLET_TITLE", _HEADER_PORTLET_TITLE);
		_paths.put("HEADER_PORTLET_INFO", _HEADER_PORTLET_INFO);
		_paths.put("HEADER_OPTIONS_ICON", _HEADER_OPTIONS_ICON);
		_paths.put("HEADER_PAGE_TITLE", _HEADER_PAGE_TITLE);
		_paths.put("HEADER_BACK_BUTTON", _HEADER_BACK_BUTTON);
		_paths.put("TOOLBAR_DOWNLOAD", _TOOLBAR_DOWNLOAD);
		_paths.put("TOOLBAR_EDIT", _TOOLBAR_EDIT);
		_paths.put("TOOLBAR_MOVE", _TOOLBAR_MOVE);
		_paths.put("TOOLBAR_CANCEL_CHECKOUT", _TOOLBAR_CANCEL_CHECKOUT);
		_paths.put("TOOLBAR_CHECKOUT", _TOOLBAR_CHECKOUT);
		_paths.put("TOOLBAR_CHECKIN", _TOOLBAR_CHECKIN);
		_paths.put("TOOLBAR_PERMISSIONS", _TOOLBAR_PERMISSIONS);
		_paths.put("TOOLBAR_MOVE_TO_THE_RECYCLE_BIN",
			_TOOLBAR_MOVE_TO_THE_RECYCLE_BIN);
		_paths.put("DOCUMENT_LOCK_MESSAGE", _DOCUMENT_LOCK_MESSAGE);
		_paths.put("DOCUMENT_INFO_TITLE", _DOCUMENT_INFO_TITLE);
		_paths.put("DOCUMENT_INFO_DATE_UPLOADED", _DOCUMENT_INFO_DATE_UPLOADED);
		_paths.put("DOCUMENT_INFO_UPLOADER", _DOCUMENT_INFO_UPLOADER);
		_paths.put("DOCUMENT_INFO_AVERAGE_RATING", _DOCUMENT_INFO_AVERAGE_RATING);
		_paths.put("DOCUMENT_INFO_YOUR_RATING", _DOCUMENT_INFO_YOUR_RATING);
		_paths.put("VERSION_INFO_HEADER", _VERSION_INFO_HEADER);
		_paths.put("VERSION_INFO_UPDATER", _VERSION_INFO_UPDATER);
		_paths.put("VERSION_INFO_UPDATE_DATE", _VERSION_INFO_UPDATE_DATE);
		_paths.put("VERSION_INFO_WORKFLOW_STATUS", _VERSION_INFO_WORKFLOW_STATUS);
		_paths.put("DOWNLOAD_LINK", _DOWNLOAD_LINK);
		_paths.put("DOWNLOAD_URL_LINK", _DOWNLOAD_URL_LINK);
		_paths.put("DOWNLOAD_URL_FIELD", _DOWNLOAD_URL_FIELD);
		_paths.put("DOWNLOAD_WEBDAV_LINK", _DOWNLOAD_WEBDAV_LINK);
		_paths.put("DOWNLOAD_WEBDAV_FIELD", _DOWNLOAD_WEBDAV_FIELD);
		_paths.put("VERSION_HISTORY_COMPARE_VERSIONS",
			_VERSION_HISTORY_COMPARE_VERSIONS);
		_paths.put("VERSION_HISTORY_CHECKBOX_1", _VERSION_HISTORY_CHECKBOX_1);
		_paths.put("VERSION_HISTORY_VERSION_1", _VERSION_HISTORY_VERSION_1);
		_paths.put("VERSION_HISTORY_DATE_1", _VERSION_HISTORY_DATE_1);
		_paths.put("VERSION_HISTORY_SIZE_1", _VERSION_HISTORY_SIZE_1);
		_paths.put("VERSION_HISTORY_STATUS_1", _VERSION_HISTORY_STATUS_1);
		_paths.put("VERSION_HISTORY_ACTIONS_BUTTON_1",
			_VERSION_HISTORY_ACTIONS_BUTTON_1);
		_paths.put("VERSION_HISTORY_CHECKBOX_2", _VERSION_HISTORY_CHECKBOX_2);
		_paths.put("VERSION_HISTORY_VERSION_2", _VERSION_HISTORY_VERSION_2);
		_paths.put("VERSION_HISTORY_DATE_2", _VERSION_HISTORY_DATE_2);
		_paths.put("VERSION_HISTORY_SIZE_2", _VERSION_HISTORY_SIZE_2);
		_paths.put("VERSION_HISTORY_STATUS_2", _VERSION_HISTORY_STATUS_2);
		_paths.put("VERSION_HISTORY_ACTIONS_BUTTON_2",
			_VERSION_HISTORY_ACTIONS_BUTTON_2);
		_paths.put("VERSION_HISTORY_CHECKBOX_3", _VERSION_HISTORY_CHECKBOX_3);
		_paths.put("VERSION_HISTORY_VERSION_3", _VERSION_HISTORY_VERSION_3);
		_paths.put("VERSION_HISTORY_DATE_3", _VERSION_HISTORY_DATE_3);
		_paths.put("VERSION_HISTORY_SIZE_3", _VERSION_HISTORY_SIZE_3);
		_paths.put("VERSION_HISTORY_STATUS_3", _VERSION_HISTORY_STATUS_3);
		_paths.put("VERSION_HISTORY_ACTIONS_BUTTON_3",
			_VERSION_HISTORY_ACTIONS_BUTTON_3);
		_paths.put("COMMENT_MESSAGE_SUCCESS", _COMMENT_MESSAGE_SUCCESS);
		_paths.put("COMMENT_MESSAGE_INFO", _COMMENT_MESSAGE_INFO);
		_paths.put("COMMENT_LINK_ADD", _COMMENT_LINK_ADD);
		_paths.put("COMMENT_LINK_SUBSCRIBE", _COMMENT_LINK_SUBSCRIBE);
		_paths.put("COMMENT_LINK_UNSUBSCRIBE", _COMMENT_LINK_UNSUBSCRIBE);
		_paths.put("COMMENT_TEXTAREA", _COMMENT_TEXTAREA);
		_paths.put("COMMENT_BUTTON_REPLY", _COMMENT_BUTTON_REPLY);
		_paths.put("COMMENT_BUTTON_CANCEL", _COMMENT_BUTTON_CANCEL);
		_paths.put("COMMENT_VIEW_MESSAGE_1", _COMMENT_VIEW_MESSAGE_1);
		_paths.put("COMMENT_VIEW_MESSAGE_2", _COMMENT_VIEW_MESSAGE_2);
		_paths.put("COMMENT_VIEW_MESSAGE_3", _COMMENT_VIEW_MESSAGE_3);
	}
}