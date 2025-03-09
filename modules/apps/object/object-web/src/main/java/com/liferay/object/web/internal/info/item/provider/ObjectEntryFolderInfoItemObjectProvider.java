/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = InfoItemObjectProvider.class
)
public class ObjectEntryFolderInfoItemObjectProvider
	implements InfoItemObjectProvider<ObjectEntryFolder> {

	@Override
	public ObjectEntryFolder getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.fetchObjectEntryFolder(
				classPKInfoItemIdentifier.getClassPK());

		if (objectEntryFolder == null) {
			throw new NoSuchInfoItemException(
				"Unable to get object entry folder " +
					classPKInfoItemIdentifier.getClassPK());
		}

		return objectEntryFolder;
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}