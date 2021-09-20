/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.upgrade.v1_0_2;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Inácio Nery
 */
public class UpgradePortletId extends BasePortletIdUpgradeProcess {

	protected void deletePortletReferences(String portletId) throws Exception {
		runSQL("delete from Portlet where portletId = '" + portletId + "'");

		runSQL(
			"delete from PortletPreferences where portletId = '" + portletId +
				"'");

		runSQL("delete from ResourceAction where name = '" + portletId + "'");

		runSQL(
			"delete from ResourcePermission where name = '" + portletId + "'");

		removePortletIdFromLayouts(portletId);
	}

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		deletePortletReferences("1_WAR_kaleoformsportlet");
	}

	protected String getNewTypeSettings(
		String typeSettings, String oldRootPortletId) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				typeSettings
			).build();

		Set<String> keys = typeSettingsUnicodeProperties.keySet();

		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (StringUtil.startsWith(
					key, LayoutTypePortletConstants.COLUMN_PREFIX) ||
				StringUtil.startsWith(
					key, LayoutTypePortletConstants.NESTED_COLUMN_IDS)) {

				String[] portletIds = StringUtil.split(
					typeSettingsUnicodeProperties.getProperty(key));

				if (!ArrayUtil.contains(portletIds, oldRootPortletId)) {
					continue;
				}

				if (portletIds.length > 1) {
					portletIds = ArrayUtil.remove(portletIds, oldRootPortletId);

					String mergedPortletIds = StringUtil.merge(portletIds);

					typeSettingsUnicodeProperties.setProperty(
						key, mergedPortletIds.concat(StringPool.COMMA));
				}
				else {
					iterator.remove();
				}
			}
		}

		return typeSettingsUnicodeProperties.toString();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{"2_WAR_kaleoformsportlet", KaleoFormsPortletKeys.KALEO_FORMS_ADMIN}
		};
	}

	protected void removePortletIdFromLayouts(String oldRootPortletId)
		throws Exception {

		String sql =
			"select plid, typeSettings from Layout where " +
				getTypeSettingsCriteria(oldRootPortletId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long plid = resultSet.getLong("plid");

				String typeSettings = resultSet.getString("typeSettings");

				String newTypeSettings = getNewTypeSettings(
					typeSettings, oldRootPortletId);

				updateLayout(plid, newTypeSettings);
			}
		}
	}

}