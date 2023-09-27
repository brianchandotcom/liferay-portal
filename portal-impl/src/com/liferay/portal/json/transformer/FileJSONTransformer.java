/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.transformer;

import com.liferay.portal.json.data.FileData;
import com.liferay.portal.kernel.json.JSONContext;

import java.io.File;

/**
 * @author Igor Spasic
 */
public class FileJSONTransformer extends ObjectTransformer {

	@Override
	public void transform(JSONContext jsonContext, Object object) {
		File file = (File)object;

		FileData fileData = new FileData(file);

		super.transform(jsonContext, fileData);
	}

}