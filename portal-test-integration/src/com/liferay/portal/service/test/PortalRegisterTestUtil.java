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

package com.liferay.portal.service.test;

import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.workflow.UserWorkflowHandler;
import com.liferay.portlet.usersadmin.util.ContactIndexer;
import com.liferay.portlet.usersadmin.util.OrganizationIndexer;

/**
 * @author     Roberto Díaz
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class PortalRegisterTestUtil {

	protected static void registerIndexers() {
		if (_indexersRegistered) {
			return;
		}

		IndexerRegistryUtil.register(new ContactIndexer());
		IndexerRegistryUtil.register(new OrganizationIndexer());

		_indexersRegistered = true;
	}

	protected static void registerWorkflowHandlers() {
		if (_workflowHandlersRegistered) {
			return;
		}

		WorkflowHandlerRegistryUtil.register(new UserWorkflowHandler());

		_workflowHandlersRegistered = true;
	}

	private static boolean _indexersRegistered;
	private static boolean _workflowHandlersRegistered;

}