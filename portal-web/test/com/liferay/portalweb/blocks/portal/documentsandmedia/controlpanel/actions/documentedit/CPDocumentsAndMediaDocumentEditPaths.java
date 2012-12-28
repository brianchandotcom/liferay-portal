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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documentedit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentEditPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Documents and Media Add File Entry Page"
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
	private static String[] _CONTENT_INFO = {
			"//div[@class='portlet-msg-info']", "Content Info Message"
		};
	private static String[] _CONTENT_FOLDER = {
			"//a[@id='_20_folderName']", "Selected Folder"
		};
	private static String[] _CONTENT_FILE = {
			"//input[@id='_20_file']", "Document File Selection Field"
		};
	private static String[] _CONTENT_TITLE = {
			"//input[@id='_20_title']", "Document Title Field"
		};
	private static String[] _CONTENT_DESCRIPTION = {
			"//textarea[@id='_20_description']", "Document Description Field"
		};
	private static String[] _BUTTON_CANCEL = {
			"//input[@value='Cancel']", "Cancel Button"
		};
	private static String[] _BUTTON_CANCEL_CHECKIN = {
			"//input[@value='Cancel Checkin']", "Cancel Checkin Button"
		};
	private static String[] _BUTTON_CHECKOUT = {
			"//input[@value='Checkout']", "Checkout Button"
		};
	private static String[] _BUTTON_PUBLISH = {
			"//input[@value='Publish']", "Publish Button"
		};
	private static String[] _BUTTON_SAVE = {
			"//input[@value='Save']", "Save Button"
		};
	private static String[] _BUTTON_SAVE_AND_CHECKIN = {
			"//input[@value='Save and Checkin']", "Save and Checkin Button"
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
		_paths.put("CONTENT_INFO", _CONTENT_INFO);
		_paths.put("CONTENT_FOLDER", _CONTENT_FOLDER);
		_paths.put("CONTENT_FILE", _CONTENT_FILE);
		_paths.put("CONTENT_TITLE", _CONTENT_TITLE);
		_paths.put("CONTENT_DESCRIPTION", _CONTENT_DESCRIPTION);
		_paths.put("BUTTON_CANCEL", _BUTTON_CANCEL);
		_paths.put("BUTTON_CANCEL_CHECKIN", _BUTTON_CANCEL_CHECKIN);
		_paths.put("BUTTON_CHECKOUT", _BUTTON_CHECKOUT);
		_paths.put("BUTTON_PUBLISH", _BUTTON_PUBLISH);
		_paths.put("BUTTON_SAVE", _BUTTON_SAVE);
		_paths.put("BUTTON_SAVE_AND_CHECKIN", _BUTTON_SAVE_AND_CHECKIN);
	}
}