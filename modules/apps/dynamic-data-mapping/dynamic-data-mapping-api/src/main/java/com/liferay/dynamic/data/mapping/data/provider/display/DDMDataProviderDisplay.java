/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.display;

import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;

import java.util.List;
import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Lino Alves
 */
@ProviderType
public interface DDMDataProviderDisplay {

	public List<DDMDisplayTabItem> getDDMDisplayTabItems();

	public DDMDisplayTabItem getDefaultDDMDisplayTabItem();

	public String getPortletId();

	public String getTitle(Locale locale);

}