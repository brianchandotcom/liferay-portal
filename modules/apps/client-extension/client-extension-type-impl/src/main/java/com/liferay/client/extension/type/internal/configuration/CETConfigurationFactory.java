/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.configuration;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 */
@Component(
	configurationPid = "com.liferay.client.extension.type.configuration.CETConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class CETConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_properties = properties;

		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, properties,
			companyId -> {
				CETConfiguration cetConfiguration =
					ConfigurableUtil.createConfigurable(
						CETConfiguration.class, properties);

				_cet = _cetManager.addCET(
					cetConfiguration, companyId,
					_getExternalReferenceCode(properties));

				if (Objects.equals(
						_cet.getType(),
						ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

					_addControlPanelThemeCSSClientExtensionEntryRel(companyId);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, _properties,
			companyId -> {
				_cetManager.deleteCET(_cet);

				_clientExtensionEntryRelLocalService.
					deleteClientExtensionEntryRels(
						companyId, _cet.getExternalReferenceCode());
			});

		_properties = null;
	}

	@Modified
	protected void modified(Map<String, Object> properties) throws Exception {
		_properties = properties;

		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, properties,
			companyId -> {
				_cetManager.deleteCET(_cet);

				CETConfiguration cetConfiguration =
					ConfigurableUtil.createConfigurable(
						CETConfiguration.class, properties);

				_cet = _cetManager.addCET(
					cetConfiguration, companyId,
					_getExternalReferenceCode(properties));

				if (Objects.equals(
						_cet.getType(),
						ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

					_clientExtensionEntryRelLocalService.
						deleteClientExtensionEntryRels(
							companyId, _cet.getExternalReferenceCode());

					_addControlPanelThemeCSSClientExtensionEntryRel(companyId);
				}
			});
	}

	private void _addControlPanelThemeCSSClientExtensionEntryRel(Long companyId)
		throws PortalException {

		ThemeCSSCET themeCSSCET = (ThemeCSSCET)_cet;

		if (!Objects.equals(themeCSSCET.getScope(), "controlPanel")) {
			return;
		}

		ClientExtensionEntryRel clientExtensionEntryRel =
			_clientExtensionEntryRelLocalService.
				fetchClientExtensionEntryRelByExternalReferenceCode(
					themeCSSCET.getExternalReferenceCode(), companyId);

		if (clientExtensionEntryRel != null) {
			return;
		}

		Company company = _companyLocalService.getCompany(companyId);

		User guestUser = company.getGuestUser();

		Layout layout = _getControlPanelLayout(companyId);

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			guestUser.getUserId(), layout.getGroupId(),
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			_cet.getExternalReferenceCode(), _cet.getType(), StringPool.BLANK,
			new ServiceContext());

		int count =
			_clientExtensionEntryRelLocalService.
				getClientExtensionEntryRelsCount(
					_portal.getClassNameId(Layout.class), layout.getPlid(),
					ClientExtensionEntryConstants.TYPE_THEME_CSS);

		if (count > 1) {
			_log.error(
				"Only one theme CSS client extension can be applied at a " +
					"time. To avoid conflicts, none of them will be applied.");
		}
	}

	private Layout _getControlPanelLayout(long companyId)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(
			companyId, GroupConstants.CONTROL_PANEL);

		List<Layout> layouts = _layoutLocalService.getLayouts(
			group.getGroupId(), true);

		if (ListUtil.isEmpty(layouts)) {
			throw new NoSuchLayoutException(
				"Unable to get Control Panel layout");
		}

		return layouts.get(0);
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		return "LXC:" +
			ConfigurationFactoryUtil.getExternalReferenceCode(properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CETConfigurationFactory.class);

	private volatile CET _cet;

	@Reference
	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	private volatile Map<String, Object> _properties;

}