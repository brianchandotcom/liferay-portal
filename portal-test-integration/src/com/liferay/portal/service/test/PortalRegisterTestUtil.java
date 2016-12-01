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
import com.liferay.portlet.asset.util.AssetEntryIndexer;
import com.liferay.portlet.documentlibrary.util.DLFileEntryIndexer;
import com.liferay.portlet.documentlibrary.util.DLFolderIndexer;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;
import com.liferay.portlet.trash.util.TrashIndexer;
import com.liferay.portlet.usersadmin.util.ContactIndexer;
import com.liferay.portlet.usersadmin.util.OrganizationIndexer;

/**
 * @author Roberto Díaz
 */
public class PortalRegisterTestUtil {

	protected static void registerIndexers() {
		if (_indexersRegistered) {
			return;
		}

		IndexerRegistryUtil.register(new AssetEntryIndexer());
		IndexerRegistryUtil.register(new ContactIndexer());
		IndexerRegistryUtil.register(new DLFileEntryIndexer());
		IndexerRegistryUtil.register(new DLFolderIndexer());
		IndexerRegistryUtil.register(new MBMessageIndexer());
		IndexerRegistryUtil.register(new OrganizationIndexer());
		IndexerRegistryUtil.register(new TrashIndexer());

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