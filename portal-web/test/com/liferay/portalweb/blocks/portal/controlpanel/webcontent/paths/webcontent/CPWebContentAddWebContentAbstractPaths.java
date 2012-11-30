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

package com.liferay.portalweb.blocks.portal.controlpanel.webcontent.paths.webcontent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentAddWebContentAbstractPaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Web Content Add Web Content Abstract Page"
		};
	private static String[] _ABSTRACT_SUMMARY = {
			"//textarea[@id='_15_description_en_US']", "Abstract Summary"
		};
	private static String[] _ABSTRACT_SMALL_IMAGE_CHECKBOX = {
			"//input[@id='_15_smallImageCheckbox']", "User Small Image Checkbox"
		};
	private static String[] _ABSTRACT_SMALL_IMAGE_URL = {
			"//input[@id='_15_smallImageURL']", "Small Image URL"
		};
	private static String[] _ABSTRACT_SMALL_IMAGE = {
			"//input[@id='_15_smallFile']", "Small Image File"
		};
	private static String[] _SIDEBAR_CONTENT = {
			"//a[@id='_15_contentLink']", "Content"
		};
	private static String[] _SIDEBAR_ABSTRACT = {
			"//a[@id='_15_abstractLink']", "Abstract"
		};
	private static String[] _SIDEBAR_CATEGORIZATION = {
			"//a[@id='_15_categorizationLink']", "Categorization"
		};
	private static String[] _SIDEBAR_SCHEDULE = {
			"//a[@id='_15_scheduleLink']", "Schedule"
		};
	private static String[] _SIDEBAR_DISPLAY_PAGE = {
			"//a[@id='_15_displayPageLink']", "Display Page"
		};
	private static String[] _SIDEBAR_RELATED_ASSETS = {
			"//a[@id='_15_relatedAssetsLink']", "Related Assets"
		};
	private static String[] _SIDEBAR_RELATED_PERMISSIONS = {
			"//a[@id='_15_permissionsLink']", "Permissions"
		};
	private static String[] _SIDEBAR_CUSTOM_FIELDS = {
			"//a[@id='_15_customFieldsLink']", "Custom Fields"
		};
	private static String[] _SIDEBAR_SAVE_AS_DRAFT = {
			"//input[@value='Save as Draft']", "Save as Draft"
		};
	private static String[] _SIDEBAR_PUBLISH = {
			"//input[@value='Publish']", "Publish"
		};
	private static String[] _SIDEBAR_CANCEL = {
			"xpath=(//input[@value='Cancel'])[2]", "Cancel"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("ABSTRACT_SUMMARY", _ABSTRACT_SUMMARY);
		_paths.put("ABSTRACT_SMALL_IMAGE_CHECKBOX",
			_ABSTRACT_SMALL_IMAGE_CHECKBOX);
		_paths.put("ABSTRACT_SMALL_IMAGE_URL", _ABSTRACT_SMALL_IMAGE_URL);
		_paths.put("ABSTRACT_SMALL_IMAGE", _ABSTRACT_SMALL_IMAGE);
		_paths.put("SIDEBAR_CONTENT", _SIDEBAR_CONTENT);
		_paths.put("SIDEBAR_ABSTRACT", _SIDEBAR_ABSTRACT);
		_paths.put("SIDEBAR_CATEGORIZATION", _SIDEBAR_CATEGORIZATION);
		_paths.put("SIDEBAR_SCHEDULE", _SIDEBAR_SCHEDULE);
		_paths.put("SIDEBAR_DISPLAY_PAGE", _SIDEBAR_DISPLAY_PAGE);
		_paths.put("SIDEBAR_RELATED_ASSETS", _SIDEBAR_RELATED_ASSETS);
		_paths.put("SIDEBAR_RELATED_PERMISSIONS", _SIDEBAR_RELATED_PERMISSIONS);
		_paths.put("SIDEBAR_CUSTOM_FIELDS", _SIDEBAR_CUSTOM_FIELDS);
		_paths.put("SIDEBAR_SAVE_AS_DRAFT", _SIDEBAR_SAVE_AS_DRAFT);
		_paths.put("SIDEBAR_PUBLISH", _SIDEBAR_PUBLISH);
		_paths.put("SIDEBAR_CANCEL", _SIDEBAR_CANCEL);
	}
}