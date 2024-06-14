/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.provider;

import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;

/**
 * @author Víctor Galán
 */
public interface RepeatableFieldsInfoItemFormProvider<T>
	extends InfoItemFormProvider<T> {

	public InfoForm getRepeatableFieldsInfoForm(String formVariationKey)
		throws NoSuchFormVariationException;

}