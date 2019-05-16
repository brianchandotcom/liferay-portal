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

package com.liferay.batch.engine.fileimport.internal.configuration;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.fileimport.BatchFileImportType;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportJobNameMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchFileImportJobNameMapper.class)
public class BatchFileImportJobNameMapperImpl
	implements BatchFileImportJobNameMapper {

	@Override
	public String getJobName(
		String domainName, String version,
		BatchFileImportType batchFileImportType,
		BatchFileImportOperation batchFileImportOperation) {

		StringBuilder sb = new StringBuilder();

		sb.append(batchFileImportOperation);
		sb.append(StringPool.UNDERLINE);
		sb.append(StringUtil.toUpperCase(domainName));
		sb.append(StringPool.UNDERLINE);
		sb.append(version);
		sb.append(StringPool.UNDERLINE);
		sb.append(batchFileImportType);
		sb.append(StringPool.UNDERLINE);
		sb.append("JOB");

		return sb.toString();
	}

}