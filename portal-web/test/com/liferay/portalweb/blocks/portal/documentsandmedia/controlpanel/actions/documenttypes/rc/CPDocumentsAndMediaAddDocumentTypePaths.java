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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documenttypes.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaAddDocumentTypePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "//iframe[@id='_20_openFileEntryTypeView']");
		_paths.put("TOP", "relative=top");
		_paths.put("HEADER_PAGE_TITLE", "//h1[@class='header-title']/span");
		_paths.put("HEADER_BACK_BUTTON", "//a[@id='_20_TabsBack']");
		_paths.put("CONTENT_NAME", "//input[@id='_20_name']");
		_paths.put("CONTENT_DESCRIPTION", "//textarea[@id='_20_description']");
		_paths.put("METADATA_FIELD_TEXT", "//li[@title='Text']/div");
		_paths.put("METADATA_CANVAS_BUILDER",
			"//div[@class='aui-diagram-builder-canvas']/div");
		_paths.put("METADATA_BUILDER_LABEL_1",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[1]");
		_paths.put("METADATA_BUILDER_FIELD_1",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[1]/following-sibling::input");
		_paths.put("METADATA_BUILDER_EDIT_ICON_1",
			"xpath(//div[@class='aui-diagram-builder-canvas']//button[@id='editEvent'])[1]");
		_paths.put("METADATA_BUILDER_COPY_ICON_1",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='duplicateEvent'])[1]");
		_paths.put("METADATA_BUILDER_DELETE_ICON_1",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='deleteEvent'])[1]");
		_paths.put("METADATA_BUILDER_LABEL_2",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[2]");
		_paths.put("METADATA_BUILDER_FIELD_2",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[2]/following-sibling::input");
		_paths.put("METADATA_BUILDER_EDIT_ICON_2",
			"xpath(//div[@class='aui-diagram-builder-canvas']//button[@id='editEvent'])[2]");
		_paths.put("METADATA_BUILDER_COPY_ICON_2",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='duplicateEvent'])[2]");
		_paths.put("METADATA_BUILDER_DELETE_ICON_2",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='deleteEvent'])[2]");
		_paths.put("METADATA_BUILDER_LABEL_3",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[3]");
		_paths.put("METADATA_BUILDER_FIELD_3",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//label)[3]/following-sibling::input");
		_paths.put("METADATA_BUILDER_EDIT_ICON_3",
			"xpath(//div[@class='aui-diagram-builder-canvas']//button[@id='editEvent'])[3]");
		_paths.put("METADATA_BUILDER_COPY_ICON_3",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='duplicateEvent'])[3]");
		_paths.put("METADATA_BUILDER_DELETE_ICON_3",
			"xpath=(//div[@class='aui-diagram-builder-canvas']//button[@id='deleteEvent'])[3]");
		_paths.put("BUTTON_SAVE", "//input[@value='Save']");
		_paths.put("BUTTON_CANCEL", "//input[@value='Cancel']");
	}
}