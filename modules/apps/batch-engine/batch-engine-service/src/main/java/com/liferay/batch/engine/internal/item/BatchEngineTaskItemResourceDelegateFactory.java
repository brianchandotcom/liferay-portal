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

package com.liferay.batch.engine.internal.item;

import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.internal.BatchEngineTaskMethodRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskItemResourceDelegateFactory {

	public BatchEngineTaskItemResourceDelegateFactory(
		BatchEngineTaskMethodRegistry batchEngineTaskMethodRegistry,
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_batchEngineTaskMethodRegistry = batchEngineTaskMethodRegistry;
		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	public BatchEngineTaskItemResourceDelegate create(
			BatchEngineTaskOperation batchEngineTaskOperation, String className,
			long companyId, Map<String, Serializable> parameters, long userId,
			String version)
		throws Exception {

		BatchEngineTaskItemResourceDelegateCreator
			batchEngineTaskItemResourceDelegateCreator =
				_batchEngineTaskMethodRegistry.
					getBatchEngineTaskItemResourceDelegateCreator(
						version, batchEngineTaskOperation, className);

		if (batchEngineTaskItemResourceDelegateCreator == null) {
			StringBundler sb = new StringBundler(4);

			sb.append("No resource available for batch engine task operation ");
			sb.append(batchEngineTaskOperation);
			sb.append(" and class name ");
			sb.append(className);

			throw new IllegalStateException(sb.toString());
		}

		return batchEngineTaskItemResourceDelegateCreator.create(
			_companyLocalService.getCompany(companyId), parameters,
			_userLocalService.getUser(userId));
	}

	private final BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;
	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}