/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.object.model.ObjectEntryFolder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = InfoItemDetailsProvider.class
)
public class ObjectEntryFolderInfoItemDetailsProvider
	implements InfoItemDetailsProvider<ObjectEntryFolder> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(ObjectEntryFolder.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(
		ObjectEntryFolder objectEntryFolder) {

		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				ObjectEntryFolder.class.getName(),
				objectEntryFolder.getObjectEntryFolderId()));
	}

}