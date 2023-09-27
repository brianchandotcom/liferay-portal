/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Tibor Lipusz
 */
public class DDMTemplateUpgradeProcess extends UpgradeProcess {

	public DDMTemplateUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long newClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portal.search.web.internal.search.bar.portlet." +
				"SearchBarPortlet");
		long resourceClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.portlet.display.template.PortletDisplayTemplate");

		for (String oldClassName : _OLD_CLASS_NAMES) {
			long oldClassNameId = _classNameLocalService.getClassNameId(
				oldClassName);

			_deleteOrphanedDefaultSearchBarPortletTemplate(
				oldClassNameId, resourceClassNameId);

			_updateDDMTemplate(
				newClassNameId, oldClassNameId, resourceClassNameId);
		}
	}

	private void _deleteOrphanedDefaultSearchBarPortletTemplate(
			long oldClassNameId, long resourceClassNameId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"delete from DDMTemplate where classNameId = ", oldClassNameId,
				" and resourceClassNameId = ", resourceClassNameId,
				" and templateKey = 'SEARCH-BAR-LEFT-ALIGNED-ICON-FTL'"));
	}

	private void _updateDDMTemplate(
			long newClassNameId, long oldClassNameId, long resourceClassNameId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update DDMTemplate set classNameId = ", newClassNameId,
				" where classNameId = ", oldClassNameId,
				" and resourceClassNameId = ", resourceClassNameId));
	}

	private static final String[] _OLD_CLASS_NAMES = {
		"com.liferay.portal.search.web.internal.search.bar.portlet.display." +
			"context.SearchBarPortletDisplayContext",
		"com.liferay.portal.search.web.internal.search.bar.portlet." +
			"SearchBarPortletDisplayContext"
	};

	private final ClassNameLocalService _classNameLocalService;

}