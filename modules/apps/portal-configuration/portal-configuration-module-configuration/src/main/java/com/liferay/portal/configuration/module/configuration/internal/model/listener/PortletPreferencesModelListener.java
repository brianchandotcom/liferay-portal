/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration.internal.model.listener;

import com.liferay.asset.kernel.util.NotifiedAssetEntryThreadLocal;
import com.liferay.portal.configuration.module.configuration.internal.ConfigurationOverrideInstance;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class PortletPreferencesModelListener
	extends BaseModelListener<PortletPreferences> {

	@Override
	public void onAfterCreate(PortletPreferences portletPreferences)
		throws ModelListenerException {

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences)
		throws ModelListenerException {

		clearCache(portletPreferences);

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	@Override
	public void onAfterUpdate(
			PortletPreferences originalPortletPreferences,
			PortletPreferences portletPreferences)
		throws ModelListenerException {

		clearCache(portletPreferences);

		updateLayout(portletPreferences);

		_clearConfigurationOverrideInstance(portletPreferences);
	}

	protected void clearCache(PortletPreferences portletPreferences) {
		if (portletPreferences == null) {
			return;
		}

		try {
			long companyId = 0;

			Layout layout = _layoutLocalService.fetchLayout(
				portletPreferences.getPlid());

			if ((layout != null) && !layout.isPrivateLayout()) {
				companyId = layout.getCompanyId();
			}
			else {
				LayoutRevision layoutRevision =
					_layoutRevisionLocalService.fetchLayoutRevision(
						portletPreferences.getPlid());

				if ((layoutRevision != null) &&
					!layoutRevision.isPrivateLayout()) {

					companyId = layoutRevision.getCompanyId();
				}
			}

			if (companyId > 0) {
				CacheUtil.clearCache(companyId);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			CacheUtil.clearCache();
		}
	}

	protected void updateLayout(PortletPreferences portletPreferences) {
		try {
			if ((portletPreferences.getOwnerType() ==
					PortletKeys.PREFS_OWNER_TYPE_GROUP) &&
				(portletPreferences.getOwnerId() > 0)) {

				Group group = _groupLocalService.fetchGroup(
					portletPreferences.getOwnerId());

				if (group == null) {
					return;
				}

				String className = group.getClassName();

				if (!className.equals(LayoutSetPrototype.class.getName())) {
					return;
				}

				LayoutSetPrototype layoutSetPrototype =
					_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
						group.getClassPK());

				if (layoutSetPrototype == null) {
					return;
				}

				layoutSetPrototype.setModifiedDate(new Date());

				_layoutSetPrototypeLocalService.updateLayoutSetPrototype(
					layoutSetPrototype);
			}
			else if ((portletPreferences.getOwnerType() ==
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT) &&
					 (portletPreferences.getPlid() > 0)) {

				Layout layout = _layoutLocalService.fetchLayout(
					portletPreferences.getPlid());

				if ((layout == null) ||
					NotifiedAssetEntryThreadLocal.
						isNotifiedAssetEntryIdsModified()) {

					return;
				}

				if (layout.isDraftLayout()) {
					ServiceContext serviceContext =
						ServiceContextThreadLocal.getServiceContext();

					_layoutLocalService.updateStatus(
						serviceContext.getUserId(), layout.getPlid(),
						WorkflowConstants.STATUS_DRAFT, serviceContext);
				}
				else {
					layout.setModifiedDate(new Date());

					_layoutLocalService.updateLayout(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getLayoutId(), layout.getTypeSettings());
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update the layout's modified date", exception);
		}
	}

	private void _clearConfigurationOverrideInstance(
		PortletPreferences portletPreferences) {

		if ((portletPreferences == null) ||
			(portletPreferences.getPortletId() == null)) {

			return;
		}

		ConfigurationPidMapping configurationPidMapping =
			_getConfigurationPidMapping(portletPreferences.getPortletId());

		if (configurationPidMapping == null) {
			return;
		}

		ConfigurationOverrideInstance.clearConfigurationOverrideInstance(
			configurationPidMapping.getConfigurationBeanClass());
	}

	private ConfigurationPidMapping _getConfigurationPidMapping(
		String configurationId) {

		ConfigurationPidMapping configurationPidMapping =
			_settingsLocatorHelper.getConfigurationPidMapping(configurationId);

		if (configurationPidMapping == null) {
			return null;
		}

		Class<?> clazz = configurationPidMapping.getConfigurationBeanClass();

		if (clazz.getAnnotation(Settings.Config.class) == null) {
			return configurationPidMapping;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesModelListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

}