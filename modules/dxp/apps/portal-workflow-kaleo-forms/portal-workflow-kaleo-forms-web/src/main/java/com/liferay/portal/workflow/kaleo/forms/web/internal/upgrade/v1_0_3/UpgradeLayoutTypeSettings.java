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

package com.liferay.portal.workflow.kaleo.forms.web.internal.upgrade.v1_0_3;

import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Inácio Nery
 */
public class UpgradeLayoutTypeSettings extends BasePortletIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deleteLayoutTypeSettingsColumnKeyWithoutValue();
	}

	private void _deleteLayoutTypeSettingsColumnKeyWithoutValue()
		throws Exception {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			LayoutLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

				Property typeSettingsProperty = PropertyFactoryUtil.forName(
					"typeSettings");

				disjunction.add(
					typeSettingsProperty.like(
						"%" + LayoutTypePortletConstants.COLUMN_PREFIX +
							"%=,%"));
				disjunction.add(
					typeSettingsProperty.like(
						"%" + LayoutTypePortletConstants.NESTED_COLUMN_IDS +
							"%=,%"));

				dynamicQuery.add(disjunction);
			});
		indexableActionableDynamicQuery.setParallel(true);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(Layout layout) -> {
				try {
					UnicodeProperties oldtypeSettingsUnicodeProperties =
						layout.getTypeSettingsProperties();
					UnicodeProperties newTypeSettingsUnicodeProperties =
						_getNewTypeSettingsUnicodeProperties(
							layout.getTypeSettingsProperties());

					if (!oldtypeSettingsUnicodeProperties.equals(
							newTypeSettingsUnicodeProperties)) {

						updateLayout(
							layout.getPlid(),
							newTypeSettingsUnicodeProperties.toString());
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update layout " + layout.getPlid(),
							exception);
					}
				}
			});

		indexableActionableDynamicQuery.performActions();
	}

	private UnicodeProperties _getNewTypeSettingsUnicodeProperties(
		UnicodeProperties oldtypeSettingsUnicodeProperties) {

		UnicodeProperties newtypeSettingsUnicodeProperties =
			(UnicodeProperties)oldtypeSettingsUnicodeProperties.clone();

		for (String key : oldtypeSettingsUnicodeProperties.keySet()) {
			if (StringUtil.startsWith(
					key, LayoutTypePortletConstants.COLUMN_PREFIX) ||
				StringUtil.startsWith(
					key, LayoutTypePortletConstants.NESTED_COLUMN_IDS)) {

				String[] portletIds = StringUtil.split(
					oldtypeSettingsUnicodeProperties.getProperty(key));

				if (ArrayUtil.isEmpty(portletIds) ||
					Validator.isNull(portletIds[0])) {

					newtypeSettingsUnicodeProperties.remove(key);
				}
			}
		}

		return newtypeSettingsUnicodeProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLayoutTypeSettings.class);

}