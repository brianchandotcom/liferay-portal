/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.depot.model.DepotEntry",
	service = InfoItemFieldValuesProvider.class
)
public class DepotEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<DepotEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(DepotEntry depotEntry) {
		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getInfoFieldValues(depotEntry)
			).infoFieldValues(
				_displayPageInfoItemFieldSetProvider.getInfoFieldValues(
					_getInfoItemReference(depotEntry), StringPool.BLANK,
					DepotEntry.class.getSimpleName(), depotEntry,
					_getThemeDisplay())
			).infoItemReference(
				_getInfoItemReference(depotEntry)
			).build();
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(
		DepotEntry depotEntry) {

		return ListUtil.fromArray(
			new InfoFieldValue<>(
				DepotEntryInfoItemFields.createDateInfoField,
				depotEntry.getCreateDate()),
			new InfoFieldValue<>(
				DepotEntryInfoItemFields.modifiedDateInfoField,
				depotEntry.getModifiedDate()),
			new InfoFieldValue<>(
				DepotEntryInfoItemFields.nameInfoField,
				() -> {
					try {
						Group group = depotEntry.getGroup();

						return InfoLocalizedValue.<String>builder(
						).defaultLocale(
							LocaleUtil.fromLanguageId(
								group.getDefaultLanguageId())
						).values(
							group.getNameMap()
						).build();
					}
					catch (PortalException portalException) {
						return ReflectionUtil.throwException(portalException);
					}
				}));
	}

	private InfoItemReference _getInfoItemReference(DepotEntry depotEntry) {
		return new InfoItemReference(
			depotEntry.getModelClassName(),
			new ClassPKInfoItemIdentifier(depotEntry.getDepotEntryId()));
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

}