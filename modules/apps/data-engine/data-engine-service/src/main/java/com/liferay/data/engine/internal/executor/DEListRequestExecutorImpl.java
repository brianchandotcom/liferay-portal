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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.executor.DEDataDefinitionListRequestExecutor;
import com.liferay.data.engine.executor.DEListRequest;
import com.liferay.data.engine.executor.DEListRequestExecutor;
import com.liferay.data.engine.executor.DEListResponse;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = DEListRequestExecutor.class)
public class DEListRequestExecutorImpl implements DEListRequestExecutor {

	@Override
	public <T extends DEListResponse> T execute(DEListRequest deListRequest)
		throws DEDataDefinitionException {

		return deListRequest.accept(this);
	}

	@Override
	public DEDataDefinitionListResponse executeListRequest(
			DEDataDefinitionListRequest deDataDefinitionListRequest)
		throws DEDataDefinitionException {

		return deDataDefinitionListRequestExecutor.execute(
			deDataDefinitionListRequest);
	}

	@Reference
	protected DEDataDefinitionListRequestExecutor
		deDataDefinitionListRequestExecutor;

}