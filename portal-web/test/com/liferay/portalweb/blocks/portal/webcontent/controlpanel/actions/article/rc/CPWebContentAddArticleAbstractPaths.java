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
public class CPWebContentAddArticleAbstractPaths {
	public static Map<String, String> getPaths() {
		return _paths;
	}

	private static Map<String, String> _paths = new HashMap<String, String>();

	static {
		_paths.put("PAGE_NAME", "");
		_paths.put("ABSTRACT_SUMMARY", "//textarea[@id='_15_description_en_US']");
		_paths.put("ABSTRACT_SMALL_IMAGE_CHECKBOX",
			"//input[@id='_15_smallImageCheckbox']");
		_paths.put("ABSTRACT_SMALL_IMAGE_URL",
			"//input[@id='_15_smallImageURL']");
		_paths.put("ABSTRACT_SMALL_IMAGE", "//input[@id='_15_smallFile']");
		_paths.put("SIDEBAR_CONTENT", "//a[@id='_15_contentLink']");
		_paths.put("SIDEBAR_ABSTRACT", "//a[@id='_15_abstractLink']");
		_paths.put("SIDEBAR_CATEGORIZATION", "//a[@id='_15_categorizationLink']");
		_paths.put("SIDEBAR_SCHEDULE", "//a[@id='_15_scheduleLink']");
		_paths.put("SIDEBAR_DISPLAY_PAGE", "//a[@id='_15_displayPageLink']");
		_paths.put("SIDEBAR_RELATED_ASSETS", "//a[@id='_15_relatedAssetsLink']");
		_paths.put("SIDEBAR_RELATED_PERMISSIONS",
			"//a[@id='_15_permissionsLink']");
		_paths.put("SIDEBAR_CUSTOM_FIELDS", "//a[@id='_15_customFieldsLink']");
		_paths.put("SIDEBAR_SAVE_AS_DRAFT", "//input[@value='Save as Draft']");
		_paths.put("SIDEBAR_PUBLISH", "//input[@value='Publish']");
		_paths.put("SIDEBAR_CANCEL", "xpath=(//input[@value='Cancel'])[2]");
	}
}