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

package com.liferay.portalweb.blocks.portal.webcontent.controlpanel.actions.article.rc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentEditArticlePaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("TOOLBAR_ID", "//span[@class='workflow-id']");
		_paths.put("TOOLBAR_VERSION", "//span[@class='workflow-version']");
		_paths.put("TOOLBAR_STATUS", "//span[@class='workflow-status']");
		_paths.put("TOOLBAR_PERMISSIONS",
			"//span[@class='aui-toolbar-content']/button[1]");
		_paths.put("TOOLBAR_VIEW_HISTORY",
			"//span[@class='aui-toolbar-content']/button[2]");
		_paths.put("HEADER_STRUCTURE", "//span[@id='_15_structureNameLabel']");
		_paths.put("HEADER_EDIT_STRUCTURE", "//a[@id='_15_editStructureLink']");
		_paths.put("HEADER_CHANGE_STRUCTURE",
			"//a[@id='_15_changeStructureButton']");
		_paths.put("HEADER_TEMPLATE", "//span[@class='template-name-label']");
		_paths.put("HEADER_CHOOSE_TEMPLATE", "//a[@id='_15_selectTemplateLink']");
		_paths.put("HEADER_CHANGE_LANGUAGE", "//a[@id='_15_changeLanguageId']");
		_paths.put("HEADER_ADD_TRANSLATION",
			"//span[@class='lfr-translation-manager-add-menu']");
		_paths.put("TRANSLATION_CHINESE_CHINA",
			"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a");
		_paths.put("TRANSLATION_FRENCH_FRANCE",
			"//div[@class='lfr-component lfr-menu-list']/ul/li[17]/a");
		_paths.put("TRANSLATION_JAPANESE_JAPAN",
			"//div[@class='lfr-component lfr-menu-list']/ul/li[27]/a");
		_paths.put("TRANSLATION_SPANISH_SPAIN",
			"//div[@class='lfr-component lfr-menu-list']/ul/li[41]/a");
		_paths.put("ARTICLE_TITLE", "//input[@id='_15_title_en_US']");
		_paths.put("ARTICLE_CONTENT",
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']");
		_paths.put("SIDEBAR_CONTENT", "//a[@id='_15_contentLink']");
		_paths.put("SIDEBAR_ABSTRACT", "//a[@id='_15_abstractLink']");
		_paths.put("SIDEBAR_CATEGORIZATION", "//a[@id='_15_categorizationLink']");
		_paths.put("SIDEBAR_SCHEDULE", "//a[@id='_15_scheduleLink']");
		_paths.put("SIDEBAR_DISPLAY_PAGE", "//a[@id='_15_displayPageLink']");
		_paths.put("SIDEBAR_RELATED_ASSETS", "//a[@id='_15_relatedAssetsLink']");
		_paths.put("SIDEBAR_CUSTOM_FIELDS", "//a[@id='_15_customFieldsLink']");
		_paths.put("SIDEBAR_SAVE_AS_DRAFT", "//input[@value='Save as Draft']");
		_paths.put("SIDEBAR_PUBLISH", "//input[@value='Publish']");
		_paths.put("SIDEBAR_CANCEL", "xpath=(//input[@value='Cancel'])[2]");
	}
}