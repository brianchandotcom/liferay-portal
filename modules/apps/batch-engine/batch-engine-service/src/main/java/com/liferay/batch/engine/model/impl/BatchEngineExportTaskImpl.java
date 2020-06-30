/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	public BatchEngineExportTaskImpl() {
	}

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