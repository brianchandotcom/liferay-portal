/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.info.item.capability;

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;

/**
 * @author Eudaldo Alonso
 */
public interface EditPageInfoItemCapability extends InfoItemCapability {

	public static final String KEY =
		"com.liferay.layout.page.template.info.item.capability." +
			"EditPageInfoItemCapability";

	public static final Class<?>[] REQUIRED_INFO_ITEM_SERVICE_CLASSES =
		new Class<?>[] {
			InfoItemCreator.class, InfoItemDetailsProvider.class,
			InfoItemFieldValuesProvider.class, InfoItemFieldValuesUpdater.class,
			InfoItemFormProvider.class
		};

}