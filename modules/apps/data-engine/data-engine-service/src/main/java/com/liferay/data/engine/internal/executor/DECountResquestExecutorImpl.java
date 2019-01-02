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
import com.liferay.data.engine.executor.DECountRequest;
import com.liferay.data.engine.executor.DECountRequestExecutor;
import com.liferay.data.engine.executor.DECountResponse;
import com.liferay.data.engine.executor.DEDataDefinitionCountRequestExecutor;
import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(immediate = true, service = DECountRequestExecutor.class)
public class DECountResquestExecutorImpl implements DECountRequestExecutor {

	@Override
	public <T extends DECountResponse> T execute(DECountRequest deCountRequest)
		throws PortalException {

		return deCountRequest.accept(this);
	}

	@Override
	public DEDataDefinitionCountResponse executeCountRequest(
			DEDataDefinitionCountRequest deDataDefinitionCountRequest)
		throws DEDataDefinitionException {

		return deDataDefinitionCountRequestExecutor.execute(
			deDataDefinitionCountRequest);
	}

	@Reference
	protected DEDataDefinitionCountRequestExecutor
		deDataDefinitionCountRequestExecutor;

}