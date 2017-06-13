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

package com.liferay.nested.portlets.web.internal.upgrade.v1_0_0;

import com.liferay.nested.portlets.web.internal.constants.NestedPortletsPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletId extends BaseUpgradePortletId {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updateNestedPortletLayoutRevisionTypeSettings();
		updateNestedPortletLayoutTypeSettings();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"118", NestedPortletsPortletKeys.NESTED_PORTLETS
			}
		};
	}

	protected void updateNestedPortletLayoutRevisionTypeSettings()
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("update LayoutRevision set typeSettings = replace(");
		sb.append("typeSettings, '_118_INSTANCE_', '_");
		sb.append(NestedPortletsPortletKeys.NESTED_PORTLETS);
		sb.append("_INSTANCE_') where typeSettings LIKE '%nested-column-ids%'");

		runSQL(sb.toString());
	}

	protected void updateNestedPortletLayoutTypeSettings() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("update Layout set typeSettings = replace(typeSettings, ");
		sb.append("'_118_INSTANCE_', '_");
		sb.append(NestedPortletsPortletKeys.NESTED_PORTLETS);
		sb.append("_INSTANCE_') where typeSettings LIKE '%nested-column-ids%'");

		runSQL(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletId.class);

}