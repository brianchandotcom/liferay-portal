/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.search.Document;

/**
 * @author Shuyang Zhou
 */
public interface DDMFormValuesIndexer {

	public void index(Document document, DDMFormValues ddmFormValues);

}