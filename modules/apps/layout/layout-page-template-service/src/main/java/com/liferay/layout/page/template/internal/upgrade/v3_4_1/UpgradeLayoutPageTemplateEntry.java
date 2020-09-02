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
public class UpgradeLayoutPageTemplateEntry extends UpgradeProcess {

	public UpgradeLayoutPageTemplateEntry(Portal portal) {
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateFileEntryClassNameId();
	}

	private void _updateFileEntryClassNameId() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("update LayoutPageTemplateEntry set classNameId = ");
		sb.append(_portal.getClassNameId(FileEntry.class.getName()));
		sb.append(" where classNameId = ");
		sb.append(_portal.getClassNameId(DLFileEntry.class.getName()));
		sb.append(" and type_ = ");
		sb.append(LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		runSQL(sb.toString());
	}

	private final Portal _portal;

}