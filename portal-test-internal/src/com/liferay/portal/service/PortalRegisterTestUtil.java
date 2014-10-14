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

package com.liferay.portal.service;

import com.liferay.portal.asset.LayoutRevisionAssetRendererFactory;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.assetpublisher.util.AssetIndexer;
import com.liferay.portlet.blogs.asset.BlogsEntryAssetRendererFactory;
import com.liferay.portlet.blogs.trash.BlogsEntryTrashHandler;
import com.liferay.portlet.blogs.util.BlogsIndexer;
import com.liferay.portlet.blogs.workflow.BlogsEntryWorkflowHandler;
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
import com.liferay.portlet.dynamicdatalists.asset.DDLRecordAssetRendererFactory;
import com.liferay.portlet.dynamicdatalists.util.DDLIndexer;
import com.liferay.portlet.dynamicdatalists.workflow.DDLRecordWorkflowHandler;
import com.liferay.portlet.journal.asset.JournalArticleAssetRendererFactory;
import com.liferay.portlet.journal.asset.JournalFolderAssetRendererFactory;
import com.liferay.portlet.journal.trash.JournalArticleTrashHandler;
import com.liferay.portlet.journal.util.JournalArticleIndexer;
import com.liferay.portlet.journal.util.JournalFolderIndexer;
import com.liferay.portlet.journal.workflow.JournalArticleWorkflowHandler;
import com.liferay.portlet.messageboards.asset.MBCategoryAssetRendererFactory;
import com.liferay.portlet.messageboards.asset.MBDiscussionAssetRendererFactory;
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
import com.liferay.portlet.wiki.asset.WikiPageAssetRendererFactory;
import com.liferay.portlet.wiki.trash.WikiNodeTrashHandler;
import com.liferay.portlet.wiki.trash.WikiPageTrashHandler;
import com.liferay.portlet.wiki.util.WikiNodeIndexer;
import com.liferay.portlet.wiki.util.WikiPageIndexer;
import com.liferay.portlet.wiki.workflow.WikiPageWorkflowHandler;

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

		IndexerRegistryUtil.register(new AssetIndexer());
		IndexerRegistryUtil.register(new BlogsIndexer());
		IndexerRegistryUtil.register(new ContactIndexer());
		IndexerRegistryUtil.register(new DDLIndexer());
		IndexerRegistryUtil.register(new DLFileEntryIndexer());
		IndexerRegistryUtil.register(new DLFolderIndexer());
		IndexerRegistryUtil.register(new JournalArticleIndexer());
		IndexerRegistryUtil.register(new JournalFolderIndexer());
		IndexerRegistryUtil.register(new MBMessageIndexer());
		IndexerRegistryUtil.register(new OrganizationIndexer());
		IndexerRegistryUtil.register(new TrashIndexer());
		IndexerRegistryUtil.register(new UserIndexer());
		IndexerRegistryUtil.register(new WikiNodeIndexer());
		IndexerRegistryUtil.register(new WikiPageIndexer());

		_indexersRegistered = true;
	}

	protected static void registerTrashHandlers() {
		if (_trashHandlersRegistered) {
			return;
		}

		TrashHandlerRegistryUtil.register(new BlogsEntryTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFileEntryTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFileShortcutTrashHandler());
		TrashHandlerRegistryUtil.register(new DLFolderTrashHandler());
		TrashHandlerRegistryUtil.register(new JournalArticleTrashHandler());
		TrashHandlerRegistryUtil.register(new MBCategoryTrashHandler());
		TrashHandlerRegistryUtil.register(new MBMessageTrashHandler());
		TrashHandlerRegistryUtil.register(new MBThreadTrashHandler());
		TrashHandlerRegistryUtil.register(new WikiNodeTrashHandler());
		TrashHandlerRegistryUtil.register(new WikiPageTrashHandler());

		_trashHandlersRegistered = true;
	}

	protected static void registerWorkflowHandlers() {
		if (_workflowHandlersRegistered) {
			return;
		}

		WorkflowHandlerRegistryUtil.register(new BlogsEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new DDLRecordWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new DLFileEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(
			new JournalArticleWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBDiscussionWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBMessageWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new UserWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new WikiPageWorkflowHandler());

		_workflowHandlersRegistered = true;
	}

	private static final Class<?>[] _ASSET_RENDERER_FACTORY_CLASSES = {
		BlogsEntryAssetRendererFactory.class,
		DDLRecordAssetRendererFactory.class,
		DLFileEntryAssetRendererFactory.class,
		DLFolderAssetRendererFactory.class,
		JournalArticleAssetRendererFactory.class,
		JournalFolderAssetRendererFactory.class,
		LayoutRevisionAssetRendererFactory.class,
		MBCategoryAssetRendererFactory.class,
		MBDiscussionAssetRendererFactory.class,
		MBMessageAssetRendererFactory.class, UserAssetRendererFactory.class,
		WikiPageAssetRendererFactory.class
	};

	private static boolean _assetRendererFactoriesRegistered;
	private static boolean _indexersRegistered;
	private static boolean _trashHandlersRegistered;
	private static boolean _workflowHandlersRegistered;

}