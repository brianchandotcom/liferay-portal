/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = InfoItemFieldValuesProvider.class
)
public class ObjectEntryFolderInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntryFolder> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		ObjectEntryFolder objectEntryFolder) {

		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getInfoFieldValues(objectEntryFolder)
			).infoFieldValues(
				_displayPageInfoItemFieldSetProvider.getInfoFieldValues(
					_getInfoItemReference(objectEntryFolder), StringPool.BLANK,
					ObjectEntryFolder.class.getSimpleName(), objectEntryFolder,
					_getThemeDisplay())
			).infoItemReference(
				_getInfoItemReference(objectEntryFolder)
			).build();
		}
		catch (Exception exception) {
			throw new RuntimeException("Unexpected exception", exception);
		}
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(
		ObjectEntryFolder objectEntryFolder) {

		return ListUtil.fromArray(
			new InfoFieldValue<>(
				ObjectEntryFolderInfoItemFields.createDateInfoField,
				objectEntryFolder.getCreateDate()),
			new InfoFieldValue<>(
				ObjectEntryFolderInfoItemFields.modifiedDateInfoField,
				objectEntryFolder.getModifiedDate()),
			new InfoFieldValue<>(
				ObjectEntryFolderInfoItemFields.nameInfoField,
				objectEntryFolder.getName()));
	}

	private InfoItemReference _getInfoItemReference(
		ObjectEntryFolder objectEntryFolder) {

		return new InfoItemReference(
			objectEntryFolder.getModelClassName(),
			new ClassPKInfoItemIdentifier(
				objectEntryFolder.getObjectEntryFolderId()));
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