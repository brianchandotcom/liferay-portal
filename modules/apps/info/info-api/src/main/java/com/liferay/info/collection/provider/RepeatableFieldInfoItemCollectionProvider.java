/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider;

import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.type.Keyed;
import com.liferay.info.type.Labeled;

/**
 * @author Víctor Galán
 */
public interface RepeatableFieldInfoItemCollectionProvider
	extends InfoCollectionProvider<RepeatableInfoFieldValue>, Keyed, Labeled {
}