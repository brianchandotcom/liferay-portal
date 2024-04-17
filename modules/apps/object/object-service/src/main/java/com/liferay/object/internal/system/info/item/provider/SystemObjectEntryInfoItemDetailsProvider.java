/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.system.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.system.SystemObjectEntry;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Carolina Barbosa
 */
public class SystemObjectEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<SystemObjectEntry> {

	public SystemObjectEntryInfoItemDetailsProvider(
		String itemClassName, ObjectDefinition objectDefinition) {

		_itemClassName = itemClassName;
		_objectDefinition = objectDefinition;
	}

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(
			_itemClassName,
			InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(
					_objectDefinition.getDefaultLanguageId())
			).values(
				_objectDefinition.getLabelMap()
			).build());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(
		SystemObjectEntry systemObjectEntry) {

		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				_itemClassName, systemObjectEntry.getClassPK()));
	}

	private final String _itemClassName;
	private final ObjectDefinition _objectDefinition;

}