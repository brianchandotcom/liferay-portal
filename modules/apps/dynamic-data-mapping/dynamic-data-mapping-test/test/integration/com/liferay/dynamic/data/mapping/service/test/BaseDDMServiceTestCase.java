/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class BaseDDMServiceTestCase
	extends com.liferay.portlet.dynamicdatamapping.service.test.BaseDDMServiceTestCase {

	@Override
	protected DDMTemplate addTemplate(
			long classNameId, long classPK, long sourceClassNameId,
			String templateKey, String name, String description, String type,
			String mode, String language, String script)
		throws Exception {

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), group.getGroupId(), classNameId,
			classPK, sourceClassNameId, templateKey, getDefaultLocaleMap(name),
			getDefaultLocaleMap(description), type, mode, language, script,
			false, false, null, null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Override
	protected String getBasePath() {
		return "com/liferay/dynamic/data/mapping/dependencies/";
	}

	private Map<Locale, String> getDefaultLocaleMap(String value) {
		Map<Locale, String> map = new HashMap<>();

		map.put(LocaleUtil.getSiteDefault(), value);

		return map;
	}

}