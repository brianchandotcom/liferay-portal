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

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		if (!_isContentLayout(layout)) {
			return;
		}

		_reindexLayout(layout);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(layout);

		if (ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess() ||
			(layoutPageTemplateEntry == null)) {

			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> _copyStructure(layoutPageTemplateEntry, layout));
	}

	@Override
	public void onAfterUpdate(Layout layout) throws ModelListenerException {
		if (!_isContentLayout(layout)) {
			return;
		}

		_reindexLayout(layout);
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		if (!_isContentLayout(layout)) {
			return;
		}

		try {
			Indexer indexer = IndexerRegistryUtil.getIndexer(Layout.class);

			indexer.delete(layout);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			throw new ModelListenerException(pe);
		}
	}

	private Void _copyStructure(
			LayoutPageTemplateEntry layoutPageTemplateEntry, Layout layout)
		throws Exception {

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		Layout pagetTemplateLayout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					pagetTemplateLayout.getGroupId(),
					_portal.getClassNameId(Layout.class),
					pagetTemplateLayout.getPlid());

		if (layoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				rebuildLayoutPageTemplateStructure(
					pagetTemplateLayout.getGroupId(),
					_portal.getClassNameId(Layout.class),
					pagetTemplateLayout.getPlid());
		}

		draftLayout = _layoutCopyHelper.copyLayout(
			pagetTemplateLayout, draftLayout);

		_layoutLocalService.updateLayout(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), draftLayout.getTypeSettings());

		List<AssetEntryUsage> assetEntryUsages =
			_assetEntryUsageLocalService.getAssetEntryUsagesByPlid(
				layoutPageTemplateEntry.getPlid());

		ServiceContext serviceContext = new ServiceContext();

		assetEntryUsages.forEach(
			assetEntryUsage -> _assetEntryUsageLocalService.addAssetEntryUsage(
				assetEntryUsage.getGroupId(), assetEntryUsage.getAssetEntryId(),
				assetEntryUsage.getContainerType(),
				assetEntryUsage.getContainerKey(), layout.getPlid(),
				serviceContext));

		return null;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry(Layout layout) {
		long classNameId = _portal.getClassNameId(
			LayoutPageTemplateEntry.class);

		if (layout.getClassNameId() != classNameId) {
			return null;
		}

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntry(layout.getClassPK());
	}

	private boolean _isContentLayout(Layout layout) {
		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) {
			return true;
		}

		return false;
	}

	private void _reindexLayout(Layout layout) {
		Indexer indexer = IndexerRegistryUtil.getIndexer(Layout.class);

		try {
			indexer.reindex(layout);
		}
		catch (SearchException se) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to reindex layout " + layout.getPlid(), se);
			}

			throw new ModelListenerException(se);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutModelListener.class);

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}