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
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.util.AssetEntryIndexer;
import com.liferay.portlet.directory.asset.UserAssetRendererFactory;
import com.liferay.portlet.directory.workflow.UserWorkflowHandler;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory;
import com.liferay.portlet.documentlibrary.asset.DLFolderAssetRendererFactory;
import com.liferay.portlet.documentlibrary.trash.DLFileEntryTrashHandler;
import com.liferay.portlet.documentlibrary.trash.DLFileShortcutTrashHandler;
import com.liferay.portlet.documentlibrary.trash.DLFolderTrashHandler;
import com.liferay.portlet.documentlibrary.util.DLFileEntryIndexer;
import com.liferay.portlet.documentlibrary.util.DLFolderIndexer;
import com.liferay.portlet.documentlibrary.workflow.DLFileEntryWorkflowHandler;
import com.liferay.portlet.messageboards.asset.MBCategoryAssetRendererFactory;
import com.liferay.portlet.messageboards.asset.MBMessageAssetRendererFactory;
import com.liferay.portlet.messageboards.trash.MBCategoryTrashHandler;
import com.liferay.portlet.messageboards.trash.MBMessageTrashHandler;
import com.liferay.portlet.messageboards.trash.MBThreadTrashHandler;
import com.liferay.portlet.messageboards.util.MBMessageIndexer;
import com.liferay.portlet.messageboards.workflow.MBDiscussionWorkflowHandler;
import com.liferay.portlet.messageboards.workflow.MBMessageWorkflowHandler;
import com.liferay.portlet.trash.util.TrashIndexer;
import com.liferay.portlet.usersadmin.util.ContactIndexer;
import com.liferay.portlet.usersadmin.util.OrganizationIndexer;
import com.liferay.portlet.usersadmin.util.UserIndexer;

/**
 * @author Roberto Díaz
 */
public class PortalRegisterTestUtil {

	protected static void registerAssetRendererFactories() {
		if (_assetRendererFactoriesRegistered) {
			return;
		}

		for (Class<?> clazz : _ASSET_RENDERER_FACTORY_CLASSES) {
			try {
				AssetRendererFactory assetRendererFactory =
					(AssetRendererFactory)clazz.newInstance();

				assetRendererFactory.setClassName(
					assetRendererFactory.getClassName());

				AssetRendererFactoryRegistryUtil.register(assetRendererFactory);
			}
			catch (IllegalAccessException iae) {
				iae.printStackTrace();
			}
			catch (InstantiationException ie) {
				ie.printStackTrace();
			}
		}

		_assetRendererFactoriesRegistered = true;
	}

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
		IndexerRegistryUtil.register(new UserIndexer());

		_indexersRegistered = true;
	}

	protected static void registerTrashHandlers() {
		if (_trashHandlersRegistered) {
			return;
		}

		TrashHandlerRegistryUtil.register(new DLFileEntryTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFileShortcutTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFolderTrashHandler());
		TrashHandlerRegistryUtil.register(new MBCategoryTrashHandler());
		TrashHandlerRegistryUtil.register(new MBMessageTrashHandler());
		TrashHandlerRegistryUtil.register(new MBThreadTrashHandler());

		_trashHandlersRegistered = true;
	}

	protected static void registerWorkflowHandlers() {
		if (_workflowHandlersRegistered) {
			return;
		}

		WorkflowHandlerRegistryUtil.register(new DLFileEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBDiscussionWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBMessageWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new UserWorkflowHandler());

		_workflowHandlersRegistered = true;
	}

	private static final Class<?>[] _ASSET_RENDERER_FACTORY_CLASSES = {
		DLFileEntryAssetRendererFactory.class,
		DLFolderAssetRendererFactory.class,
		MBCategoryAssetRendererFactory.class,
		MBMessageAssetRendererFactory.class, UserAssetRendererFactory.class
	};

	private static boolean _assetRendererFactoriesRegistered;
	private static boolean _indexersRegistered;
	private static boolean _trashHandlersRegistered;
	private static boolean _workflowHandlersRegistered;

}