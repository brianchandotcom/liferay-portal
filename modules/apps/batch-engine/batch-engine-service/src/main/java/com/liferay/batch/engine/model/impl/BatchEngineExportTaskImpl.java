/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivica Cardic
 */
public class BatchEngineExportTaskImpl extends BatchEngineExportTaskBaseImpl {

	@Override
	public List<String> getFieldNamesList() {
		if (Validator.isNull(getFieldNames())) {
			return Collections.emptyList();
		}

		return StringUtil.split(getFieldNames());
	}

	@Override
	public void setFieldNamesList(List<String> fieldNamesList) {
		setFieldNames(StringUtil.merge(fieldNamesList, StringPool.COMMA));
	}

}