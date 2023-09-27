/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.field.reader;

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;

import java.util.List;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public interface InfoItemFieldReaderFieldSetProvider {

	public InfoFieldSet getInfoFieldSet(String itemClassName);

	public List<InfoFieldValue<Object>> getInfoFieldValues(
		String className, Object itemObject);

}