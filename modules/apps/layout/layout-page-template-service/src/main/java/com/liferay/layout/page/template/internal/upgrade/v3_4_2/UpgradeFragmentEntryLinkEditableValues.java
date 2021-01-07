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

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntryLinkEditableValues extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select fragmentEntryLinkId,editableValues,rendererKey from " +
					"FragmentEntryLink where rendererKey = " +
						"'BASIC_COMPONENT-separator'");
			PreparedStatement ps2 = connection.prepareStatement(
				"update FragmentEntryLink set editableValues = ? where " +
					"fragmentEntryLinkId = ?");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						rs.getString("editableValues"));

				JSONObject configurationJSONObject =
					editablesJSONObject.getJSONObject(
						"com.liferay.fragment.entry.processor.freemarker." +
							"FreeMarkerFragmentEntryProcessor");

				if (configurationJSONObject == null) {
					continue;
				}

				if (configurationJSONObject.has("verticalSpace")) {
					configurationJSONObject.put(
						"bottomSpacing",
						configurationJSONObject.remove("verticalSpace"));
				}

				ps2.setString(1, editablesJSONObject.toString());
				ps2.setLong(2, rs.getLong("fragmentEntryLinkId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}