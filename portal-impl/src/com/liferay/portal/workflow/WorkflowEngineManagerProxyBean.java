/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowEngineManagerProxyBean
	extends BaseProxyBean implements WorkflowEngineManager {

	@Override
	public String getKey() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getOptionalAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getVersion() {
		throw new UnsupportedOperationException();
	}

}