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

package com.liferay.journal.content.web.internal.portlet.listener;

import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletConfigurationListener;
import com.liferay.portal.kernel.portlet.PortletConfigurationListenerException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = PortletConfigurationListener.class
)
public class JournalContentPortletConfigurationListener
	implements PortletConfigurationListener {

	@Override
	public void onUpdateScope(
			String portletId, PortletPreferences portletPreferences)
		throws PortletConfigurationListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Portlet ", portletId, " with portlet preferences ",
					MapUtil.toString(portletPreferences.getMap())));
		}

		try {
			long groupId = GetterUtil.getLong(
				portletPreferences.getValue("groupId", "0"));

			if (groupId == 0) {
				return;
			}

			Group group = _groupLocalService.fetchGroup(groupId);

			if (group == null) {
				return;
			}

			String lfrScopeType = portletPreferences.getValue(
				"lfrScopeType", StringPool.BLANK);

			if (group.isCompany() && Objects.equals("company", lfrScopeType)) {
				return;
			}

			if (group.isLayout() && Objects.equals("layout", lfrScopeType)) {
				return;
			}

			if (!group.isCompany() && !group.isLayout() &&
				Validator.isNull(lfrScopeType)) {

				return;
			}

			portletPreferences.reset("assetEntryId");
			portletPreferences.reset("articleId");
			portletPreferences.reset("ddmTemplateKey");
			portletPreferences.reset("groupId");

			portletPreferences.store();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortletConfigurationListenerException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentPortletConfigurationListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

}