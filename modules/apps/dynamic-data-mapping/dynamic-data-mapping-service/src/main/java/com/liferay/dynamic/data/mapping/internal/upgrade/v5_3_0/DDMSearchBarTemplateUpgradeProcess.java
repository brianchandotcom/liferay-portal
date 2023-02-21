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

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Tibor Lipusz
 */
public class DDMSearchBarTemplateUpgradeProcess extends UpgradeProcess {

	public DDMSearchBarTemplateUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateSearchBarTemplates();
	}

	private void _updateSearchBarTemplates() throws Exception {
		long resourceClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portlet.display.template.PortletDisplayTemplate");

		for (Map.Entry<String, String> entry :
				_searchBarTemplateClassNames.entrySet()) {

			long newClassNameId = _classNameLocalService.getClassNameId(
				entry.getValue());
			long oldClassNameId = _classNameLocalService.getClassNameId(
				entry.getKey());

			runSQL(
				StringBundler.concat(
					"update DDMTemplate set classNameId = ", newClassNameId,
					" where classNameId = ", oldClassNameId,
					" and resourceClassNameId = ", resourceClassNameId));
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final Map<String, String> _searchBarTemplateClassNames =
		HashMapBuilder.put(
			"com.liferay.portal.search.web.internal.search.bar.portlet." +
				"display.context.SearchBarPortletDisplayContext",
			"com.liferay.portal.search.web.internal.search.bar.portlet." +
				"SearchBarPortlet"
		).put(
			"com.liferay.portal.search.web.internal.search.bar.portlet." +
				"SearchBarPortletDisplayContext",
			"com.liferay.portal.search.web.internal.search.bar.portlet." +
				"SearchBarPortlet"
		).build();

}