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
import com.liferay.data.engine.executor.DEDataDefinitionSaveRequestExecutor;
import com.liferay.data.engine.executor.DESaveRequest;
import com.liferay.data.engine.executor.DESaveRequestExecutor;
import com.liferay.data.engine.executor.DESaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DESaveRequestExecutor.class)
public class DESaveRequestExecutorImpl implements DESaveRequestExecutor {

	@Override
	public <T extends DESaveResponse> T execute(DESaveRequest deSaveRequest)
		throws PortalException {

		return deSaveRequest.accept(this);
	}

	@Override
	public DEDataDefinitionSaveResponse executeSaveRequest(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws DEDataDefinitionException {

		return deDataDefinitionSaveRequestExecutor.execute(
			deDataDefinitionSaveRequest);
	}

	@Reference
	protected DEDataDefinitionSaveRequestExecutor
		deDataDefinitionSaveRequestExecutor;

}