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

package com.liferay.portlet.dynamicdatamapping.util.test;

import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManager;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 * @author Rafael Praxedes
 */
public class DDMTemplateTestUtil {

	public static DDMTemplate addTemplate(
			long groupId, long classNameId, long classPK,
			long resourceClassNameId)
		throws Exception {

		return addTemplate(
			groupId, classNameId, classPK, resourceClassNameId,
			TemplateConstants.LANG_TYPE_VM, getSampleTemplateXSL(),
			LocaleUtil.getSiteDefault());
	}

	public static DDMTemplate addTemplate(
			long groupId, long classNameId, long classPK,
			long resourceClassNameId, String language, String script,
			Locale defaultLocale)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(defaultLocale, "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMTemplateManagerUtil.addTemplate(
			TestPropsValues.getUserId(), groupId, classNameId, classPK,
			resourceClassNameId, null, nameMap, null,
			DDMTemplateManager.TEMPLATE_TYPE_DISPLAY, null, language, script,
			false, false, null, null, serviceContext);
	}

	public static String getSampleTemplateXSL() {
		return "$name.getData()";
	}

}