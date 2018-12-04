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
import com.liferay.data.engine.executor.DEGetRequest;
import com.liferay.data.engine.executor.DEGetRequestExecutor;
import com.liferay.data.engine.executor.DEGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetRequestExecutor;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DEGetRequestExecutor.class)
public class DEGetRequestExecutorImpl implements DEGetRequestExecutor {

	@Override
	public <T extends DEGetResponse> T execute(DEGetRequest deGetRequest)
		throws PortalException {

		return deGetRequest.accept(this);
	}

	@Override
	public DEDataDefinitionGetResponse executeGetRequest(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws DEDataDefinitionException {

		return deDataDefinitionGetRequestExecutor.execute(
			deDataDefinitionGetRequest);
	}

	@Reference
	protected DEDataDefinitionGetRequestExecutor
		deDataDefinitionGetRequestExecutor;

}