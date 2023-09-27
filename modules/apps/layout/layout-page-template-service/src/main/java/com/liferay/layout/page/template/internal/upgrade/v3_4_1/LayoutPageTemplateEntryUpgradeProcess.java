/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_4_1;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateEntryUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateEntryUpgradeProcess(Portal portal) {
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateFileEntryClassNameId();
	}

	private void _updateFileEntryClassNameId() throws Exception {
		runSQL(
			StringBundler.concat(
				"update LayoutPageTemplateEntry set classNameId = ",
				_portal.getClassNameId(FileEntry.class.getName()),
				" where classNameId = ",
				_portal.getClassNameId(DLFileEntry.class.getName()),
				" and type_ = ",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE));
	}

	private final Portal _portal;

}