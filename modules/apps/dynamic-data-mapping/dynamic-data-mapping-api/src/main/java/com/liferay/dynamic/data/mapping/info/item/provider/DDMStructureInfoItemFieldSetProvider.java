/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.info.item.provider;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@ProviderType
public interface DDMStructureInfoItemFieldSetProvider {

	public InfoFieldSet getInfoItemFieldSet(long ddmStructureId)
		throws NoSuchStructureException;

	public InfoFieldSet getInfoItemFieldSet(
			long ddmStructureId,
			InfoLocalizedValue<String> fieldSetNameInfoLocalizedValue)
		throws NoSuchStructureException;

}