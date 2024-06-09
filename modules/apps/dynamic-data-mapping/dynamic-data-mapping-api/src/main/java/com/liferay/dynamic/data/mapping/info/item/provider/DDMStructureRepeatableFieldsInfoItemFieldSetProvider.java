/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.info.item.provider;

import aQute.bnd.annotation.ProviderType;

import com.liferay.info.field.InfoFieldSetEntry;

import java.util.List;

/**
 * @author Víctor Galán
 */
@ProviderType
public interface DDMStructureRepeatableFieldsInfoItemFieldSetProvider {

	public List<InfoFieldSetEntry> getInfoItemFieldSet(long ddmStructureId);

}