/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.display.page.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(service = FriendlyURLResolver.class)
public class ObjectEntryDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_OBJECT_ENTRY;
	}

	@Override
	public String getKey() {
		return ObjectEntry.class.getName();
	}

	@Override
	public boolean isURLSeparatorConfigurable() {
		return true;
	}

	@Override
	protected LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
			String friendlyURL)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-21926")) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		String[] paths = StringUtil.split(
			StringUtil.removeFirst(friendlyURL, getURLSeparator()),
			CharPool.SLASH);

		if (paths.length == 1) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				serviceContext.getCompanyId(), paths[0]);

		if (objectDefinition == null) {
			return super.getLayoutDisplayPageProvider(friendlyURL);
		}

		return layoutDisplayPageProviderRegistry.
			getLayoutDisplayPageProviderByClassName(
				objectDefinition.getClassName());
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}