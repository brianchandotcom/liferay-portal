/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.info.item.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = InfoItemCapabilitiesProvider.class)
public class AssetCategoryInfoItemCapabilitiesProvider
	implements InfoItemCapabilitiesProvider<AssetCategory> {

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		return ListUtil.fromArray(
			_displayPageInfoItemCapability, _templateInfoItemCapability);
	}

	@Reference(
		target = "(info.item.capability.key=" + DisplayPageInfoItemCapability.KEY + ")"
	)
	private InfoItemCapability _displayPageInfoItemCapability;

	@Reference(
		target = "(info.item.capability.key=" + TemplateInfoItemCapability.KEY + ")"
	)
	private InfoItemCapability _templateInfoItemCapability;

}