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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.paths.documenttypes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaAddDocumentTypePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"//iframe[@id='_20_openFileEntryTypeView']",
			"Control Panel Documents and Media Add Document Type Page"
		};
	private static String[] _TOP = { "relative=top", "" };
	private static String[] _HEADER_PAGE_TITLE = {
			"//h1[@class='header-title']/span", "Document Type Page Title"
		};
	private static String[] _HEADER_BACK_BUTTON = {
			"//a[@id='_20_TabsBack']", "Back Button"
		};
	private static String[] _CONTENT_NAME = {
			"//input[@id='_20_name']", "Document Type 'Name' Field"
		};
	private static String[] _CONTENT_DESCRIPTION = {
			"//textarea[@id='_20_description']",
			"Document Type 'Description' Field"
		};
	private static String[] _METADATA_FIELD_TEXT = {
			"//li[@title='Text']/div", "Document Type 'Text' Metadata Field"
		};
	private static String[] _METADATA_CANVAS_BUILDER = {
			"//div[@class='aui-diagram-builder-canvas']/div", "Metadata Builder"
		};
	private static String[] _METADATA_BUILDER_LABEL_1 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[1]",
			"1st Docment Type Metadata Label"
		};
	private static String[] _METADATA_BUILDER_FIELD_1 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[1]/following-sibling::input",
			"1st Docment Type Metadata Field"
		};
	private static String[] _METADATA_BUILDER_EDIT_ICON_1 = {
			"xpath(//div[@class='aui-diagram-builder-canvas']//button[@id='editEvent'])[1]",
			"1st Docment Type Metadata Edit Icon"
		};
	private static String[] _METADATA_BUILDER_COPY_ICON_1 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='duplicateEvent'])[1]",
			"1st Docment Type Metadata Copy Icon"
		};
	private static String[] _METADATA_BUILDER_DELETE_ICON_1 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='deleteEvent'])[1]",
			"1st Docment Type Metadata Delete Icon"
		};
	private static String[] _METADATA_BUILDER_LABEL_2 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[2]",
			"2nd Docment Type Metadata Label"
		};
	private static String[] _METADATA_BUILDER_FIELD_2 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[2]/following-sibling::input",
			"2nd Docment Type Metadata Field"
		};
	private static String[] _METADATA_BUILDER_EDIT_ICON_2 = {
			"xpath(//div[@class='aui-diagram-builder-canvas']//button[@id='editEvent'])[2]",
			"2nd Docment Type Metadata Edit Icon"
		};
	private static String[] _METADATA_BUILDER_COPY_ICON_2 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='duplicateEvent'])[2]",
			"2nd Docment Type Metadata Copy Icon"
		};
	private static String[] _METADATA_BUILDER_DELETE_ICON_2 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='deleteEvent'])[2]",
			"2nd Docment Type Metadata Delete Icon"
		};
	private static String[] _METADATA_BUILDER_LABEL_3 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[3]",
			"3rd Docment Type Metadata Label"
		};
	private static String[] _METADATA_BUILDER_FIELD_3 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[3]/following-sibling::input",
			"3rd Docment Type Metadata Field"
		};
	private static String[] _METADATA_BUILDER_EDIT_ICON_3 = {
			"xpath(//div[@class='aui-diagram-builder-canvas']//button[@id='editEvent'])[3]",
			"3rd Docment Type Metadata Edit Icon"
		};
	private static String[] _METADATA_BUILDER_COPY_ICON_3 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='duplicateEvent'])[3]",
			"3rd Docment Type Metadata Copy Icon"
		};
	private static String[] _METADATA_BUILDER_DELETE_ICON_3 = {
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='deleteEvent'])[3]",
			"3rd Docment Type Metadata Delete Icon"
		};
	private static String[] _BUTTON_SAVE = {
			"//input[@value='Save']", "Document Type 'Save' Button"
		};
	private static String[] _BUTTON_CANCEL = {
			"//input[@value='Cancel']", "Document Type 'Cancel' Button"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("TOP", _TOP);
		_paths.put("HEADER_PAGE_TITLE", _HEADER_PAGE_TITLE);
		_paths.put("HEADER_BACK_BUTTON", _HEADER_BACK_BUTTON);
		_paths.put("CONTENT_NAME", _CONTENT_NAME);
		_paths.put("CONTENT_DESCRIPTION", _CONTENT_DESCRIPTION);
		_paths.put("METADATA_FIELD_TEXT", _METADATA_FIELD_TEXT);
		_paths.put("METADATA_CANVAS_BUILDER", _METADATA_CANVAS_BUILDER);
		_paths.put("METADATA_BUILDER_LABEL_1", _METADATA_BUILDER_LABEL_1);
		_paths.put("METADATA_BUILDER_FIELD_1", _METADATA_BUILDER_FIELD_1);
		_paths.put("METADATA_BUILDER_EDIT_ICON_1", _METADATA_BUILDER_EDIT_ICON_1);
		_paths.put("METADATA_BUILDER_COPY_ICON_1", _METADATA_BUILDER_COPY_ICON_1);
		_paths.put("METADATA_BUILDER_DELETE_ICON_1",
			_METADATA_BUILDER_DELETE_ICON_1);
		_paths.put("METADATA_BUILDER_LABEL_2", _METADATA_BUILDER_LABEL_2);
		_paths.put("METADATA_BUILDER_FIELD_2", _METADATA_BUILDER_FIELD_2);
		_paths.put("METADATA_BUILDER_EDIT_ICON_2", _METADATA_BUILDER_EDIT_ICON_2);
		_paths.put("METADATA_BUILDER_COPY_ICON_2", _METADATA_BUILDER_COPY_ICON_2);
		_paths.put("METADATA_BUILDER_DELETE_ICON_2",
			_METADATA_BUILDER_DELETE_ICON_2);
		_paths.put("METADATA_BUILDER_LABEL_3", _METADATA_BUILDER_LABEL_3);
		_paths.put("METADATA_BUILDER_FIELD_3", _METADATA_BUILDER_FIELD_3);
		_paths.put("METADATA_BUILDER_EDIT_ICON_3", _METADATA_BUILDER_EDIT_ICON_3);
		_paths.put("METADATA_BUILDER_COPY_ICON_3", _METADATA_BUILDER_COPY_ICON_3);
		_paths.put("METADATA_BUILDER_DELETE_ICON_3",
			_METADATA_BUILDER_DELETE_ICON_3);
		_paths.put("BUTTON_SAVE", _BUTTON_SAVE);
		_paths.put("BUTTON_CANCEL", _BUTTON_CANCEL);
	}
}