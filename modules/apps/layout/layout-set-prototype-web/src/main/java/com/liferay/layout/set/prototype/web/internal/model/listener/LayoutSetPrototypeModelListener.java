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

package com.liferay.layout.set.prototype.web.internal.model.listener;

import com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeConfiguration;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(service = ModelListener.class)
public class LayoutSetPrototypeModelListener
	extends BaseModelListener<LayoutSetPrototype> {

	@Override
	public void onAfterUpdate(
		LayoutSetPrototype originalLayoutSetPrototype,
		LayoutSetPrototype layoutSetPrototype) {

		if (!isTriggerPropagation()) {
			return;
		}

		UnicodeProperties originalSettingsUnicodeProperties =
			originalLayoutSetPrototype.getSettingsProperties();

		boolean originalReadyForPropagation = GetterUtil.getBoolean(
			originalSettingsUnicodeProperties.getProperty(
				"readyForPropagation"));

		UnicodeProperties settingsUnicodeProperties =
			layoutSetPrototype.getSettingsProperties();

		boolean readyForPropagation = GetterUtil.getBoolean(
			settingsUnicodeProperties.getProperty("readyForPropagation"));

		if (!originalReadyForPropagation && readyForPropagation) {
			List<LayoutSet> layoutSets =
				_layoutSetLocalService.getLayoutSetsByLayoutSetPrototypeUuid(
					layoutSetPrototype.getUuid());

			for (LayoutSet layoutSet : layoutSets) {
				_layoutLocalService.getLayouts(
					layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}
		}
	}

	protected boolean isTriggerPropagation() {
		try {
			LayoutSetPrototypeConfiguration layoutSetPrototypeConfiguration =
				_configurationProvider.getCompanyConfiguration(
					LayoutSetPrototypeConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return layoutSetPrototypeConfiguration.triggerPropagation();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeModelListener.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}