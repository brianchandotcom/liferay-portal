/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_5;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Sam Ziemer
 */
public class DDMTemplateLinkUpgradeProcess extends UpgradeProcess {

	public DDMTemplateLinkUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String compositeClassName = ResourceActionsUtil.getCompositeModelName(
			JournalArticle.class.getName(), DDMTemplate.class.getName());

		runSQL(
			"delete from ddmtemplatelink where classNameId = '" +
				_classNameLocalService.getClassNameId(compositeClassName) +
					"' AND classPK not in (select id_ from journalarticle)");
	}

	private final ClassNameLocalService _classNameLocalService;

}