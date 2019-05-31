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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.constants.CTSettingsKeys;
import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.rest.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.dto.v1_0.SettingsUpdate;
import com.liferay.change.tracking.rest.resource.v1_0.SettingsResource;
import com.liferay.change.tracking.settings.CTSettingsManager;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/settings.properties",
	scope = ServiceScope.PROTOTYPE, service = SettingsResource.class
)
public class SettingsResourceImpl extends BaseSettingsResourceImpl {

	@Override
	public Page<Settings> getSettingsPage(Long companyId, Long userId)
		throws Exception {

		_companyLocalService.getCompany(companyId);

		try {
			User user = _userLocalService.getUser(userId);

			return Page.of(
				Collections.singleton(
					_getUserSettings(companyId, user.getUserId())));
		}
		catch (NoSuchUserException | NullPointerException e) {
			return Page.of(
				Collections.singleton(
					_getSettings(companyId, _user.getLocale())));
		}
	}

	@Override
	public Settings putSettings(
			Long companyId, Long userId, SettingsUpdate settingsUpdate)
		throws Exception {

		_companyLocalService.getCompany(companyId);

		try {
			User user = _userLocalService.getUser(userId);

			_ctSettingsManager.setUserCTSetting(
				user.getUserId(),
				CTSettingsKeys.CHECKOUT_CT_COLLECTION_CONFIRMATION_ENABLED,
				Boolean.toString(
					settingsUpdate.
						getCheckoutCTCollectionConfirmationEnabled()));

			return _getUserSettings(companyId, user.getUserId());
		}
		catch (NoSuchUserException | NullPointerException e) {
			if (_ctEngineManager.isChangeTrackingEnabled(companyId)) {
				if (!settingsUpdate.getChangeTrackingEnabled()) {
					_ctEngineManager.disableChangeTracking(companyId);
				}
			}
			else {
				if (settingsUpdate.getChangeTrackingEnabled()) {
					_ctEngineManager.enableChangeTracking(
						companyId, _user.getUserId());
				}
			}

			return _getSettings(companyId, _user.getLocale());
		}
	}

	private Settings _getSettings(Long companyId, Locale locale) {
		Set<String> supportedContentTypeLanguageKeys = new HashSet<>();
		Set<String> supportedContentTypes = new HashSet<>();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		for (CTDefinition<?, ?> ctDefinition : _ctDefinitions) {
			supportedContentTypeLanguageKeys.add(
				ctDefinition.getContentTypeLanguageKey());

			String contentType = LanguageUtil.get(
				resourceBundle, ctDefinition.getContentTypeLanguageKey());

			supportedContentTypes.add(contentType);
		}

		Settings settings = new Settings();

		settings.setChangeTrackingAllowed(
			_ctEngineManager.isChangeTrackingAllowed(companyId));
		settings.setChangeTrackingEnabled(
			_ctEngineManager.isChangeTrackingEnabled(companyId));
		settings.setCompanyId(companyId);
		settings.setSupportedContentTypeLanguageKeys(
			supportedContentTypeLanguageKeys.toArray(new String[0]));
		settings.setSupportedContentTypes(
			supportedContentTypes.toArray(new String[0]));

		return settings;
	}

	private Settings _getUserSettings(Long companyId, Long userId) {
		Settings settings = new Settings();

		settings.setCheckoutCTCollectionConfirmationEnabled(
			GetterUtil.getBoolean(
				_ctSettingsManager.getUserCTSetting(
					userId,
					CTSettingsKeys.CHECKOUT_CT_COLLECTION_CONFIRMATION_ENABLED,
					"true")));
		settings.setCompanyId(companyId);
		settings.setUserId(userId);

		return settings;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private volatile List<CTDefinition<?, ?>> _ctDefinitions;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTSettingsManager _ctSettingsManager;

	@Context
	private User _user;

	@Reference
	private UserLocalService _userLocalService;

}