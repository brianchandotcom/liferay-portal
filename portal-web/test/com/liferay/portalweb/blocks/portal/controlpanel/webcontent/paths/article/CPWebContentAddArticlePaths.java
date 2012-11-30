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

package com.liferay.portalweb.blocks.portal.controlpanel.webcontent.paths.article;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentAddArticlePaths {
	public static Map<String, String[]> getPaths() {
		return _paths;
	}

	private static String[] _PAGE_NAME = {
			"", "Control Panel Web Content Add Article Page"
		};
	private static String[] _HEADER_STRUCTURE = {
			"//span[@id='_15_structureNameLabel']", "Structure Name"
		};
	private static String[] _HEADER_EDIT_STRUCTURE = {
			"//a[@id='_15_editStructureLink']", "Edit Structure Icon"
		};
	private static String[] _HEADER_CHANGE_STRUCTURE = {
			"//a[@id='_15_changeStructureButton']", "Change Structure Icon"
		};
	private static String[] _HEADER_TEMPLATE = {
			"//span[@class='template-name-label']", "Template Name"
		};
	private static String[] _HEADER_CHOOSE_TEMPLATE = {
			"//a[@id='_15_selectTemplateLink']", "Choose Template Icon"
		};
	private static String[] _HEADER_CHANGE_LANGUAGE = {
			"//a[@id='_15_changeLanguageId']", "Change Default Language Link"
		};
	private static String[] _HEADER_ADD_TRANSLATION = {
			"//span[@class='lfr-translation-manager-add-menu']",
			"Add Translation"
		};
	private static String[] _TRANSLATION_CHINESE_CHINA = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a",
			"Chinese (China)"
		};
	private static String[] _TRANSLATION_FRENCH_FRANCE = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li[17]/a",
			"French (France)"
		};
	private static String[] _TRANSLATION_JAPANESE_JAPAN = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li[27]/a",
			"Japanese (Japan)"
		};
	private static String[] _TRANSLATION_SPANISH_SPAIN = {
			"//div[@class='lfr-component lfr-menu-list']/ul/li[41]/a",
			"Spanish (Spain)"
		};
	private static String[] _ARTICLE_TITLE = {
			"//input[@id='_15_title_en_US']", "Title Field"
		};
	private static String[] _ARTICLE_CONTENT = {
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']",
			"Content Field"
		};
	private static String[] _SIDEBAR_CONTENT = {
			"//a[@id='_15_contentLink']", "Content Link"
		};
	private static String[] _SIDEBAR_ABSTRACT = {
			"//a[@id='_15_abstractLink']", "Abstract Link"
		};
	private static String[] _SIDEBAR_CATEGORIZATION = {
			"//a[@id='_15_categorizationLink']", "Categorization Link"
		};
	private static String[] _SIDEBAR_SCHEDULE = {
			"//a[@id='_15_scheduleLink']", "Schedule Link"
		};
	private static String[] _SIDEBAR_DISPLAY_PAGE = {
			"//a[@id='_15_displayPageLink']", "Display Page Link"
		};
	private static String[] _SIDEBAR_RELATED_ASSETS = {
			"//a[@id='_15_relatedAssetsLink']", "Related Assets Link"
		};
	private static String[] _SIDEBAR_RELATED_PERMISSIONS = {
			"//a[@id='_15_permissionsLink']", "Permissions Link"
		};
	private static String[] _SIDEBAR_CUSTOM_FIELDS = {
			"//a[@id='_15_customFieldsLink']", "Custom Fields Link"
		};
	private static String[] _SIDEBAR_SAVE_AS_DRAFT = {
			"//input[@value='Save as Draft']", "Save as Draft Button"
		};
	private static String[] _SIDEBAR_PUBLISH = {
			"//input[@value='Publish']", "Publish Button"
		};
	private static String[] _SIDEBAR_CANCEL = {
			"xpath=(//input[@value='Cancel'])[2]", "Cancel Button"
		};
	private static Map<String, String[]> _paths = new HashMap<String, String[]>();

	static {
		_paths.put("PAGE_NAME", _PAGE_NAME);
		_paths.put("HEADER_STRUCTURE", _HEADER_STRUCTURE);
		_paths.put("HEADER_EDIT_STRUCTURE", _HEADER_EDIT_STRUCTURE);
		_paths.put("HEADER_CHANGE_STRUCTURE", _HEADER_CHANGE_STRUCTURE);
		_paths.put("HEADER_TEMPLATE", _HEADER_TEMPLATE);
		_paths.put("HEADER_CHOOSE_TEMPLATE", _HEADER_CHOOSE_TEMPLATE);
		_paths.put("HEADER_CHANGE_LANGUAGE", _HEADER_CHANGE_LANGUAGE);
		_paths.put("HEADER_ADD_TRANSLATION", _HEADER_ADD_TRANSLATION);
		_paths.put("TRANSLATION_CHINESE_CHINA", _TRANSLATION_CHINESE_CHINA);
		_paths.put("TRANSLATION_FRENCH_FRANCE", _TRANSLATION_FRENCH_FRANCE);
		_paths.put("TRANSLATION_JAPANESE_JAPAN", _TRANSLATION_JAPANESE_JAPAN);
		_paths.put("TRANSLATION_SPANISH_SPAIN", _TRANSLATION_SPANISH_SPAIN);
		_paths.put("ARTICLE_TITLE", _ARTICLE_TITLE);
		_paths.put("ARTICLE_CONTENT", _ARTICLE_CONTENT);
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