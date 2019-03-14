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

package com.liferay.layout.content.page.editor.web.internal.listener;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Portal;

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
		if (ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			return;
		}

		if (!_isContentLayout(layout)) {
			return;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(layout);

		if (layoutPageTemplateEntry == null) {
			_reindexLayout(layout);

			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> _copyStructure(layoutPageTemplateEntry, layout));

		_reindexLayout(layout);
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

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				layout.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid());

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

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					_portal.getClassNameId(
						LayoutPageTemplateEntry.class.getName()),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					true);

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutPageTemplateStructure.getData());

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return null;
		}

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				JSONArray newFragmentEntryLinkIdsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length();
					 k++) {

					long fragmentEntryLinkId =
						fragmentEntryLinkIdsJSONArray.getLong(k);

					if (fragmentEntryLinkId <= 0) {
						continue;
					}

					FragmentEntryLink fragmentEntryLink =
						_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
							fragmentEntryLinkId);

					if (fragmentEntryLink == null) {
						continue;
					}

					FragmentEntryLink newFragmentEntryLink =
						_fragmentEntryLinkLocalService.addFragmentEntryLink(
							fragmentEntryLink.getUserId(),
							fragmentEntryLink.getGroupId(),
							fragmentEntryLink.getFragmentEntryLinkId(),
							fragmentEntryLink.getFragmentEntryId(),
							_portal.getClassNameId(Layout.class.getName()),
							draftLayout.getPlid(), fragmentEntryLink.getCss(),
							fragmentEntryLink.getHtml(),
							fragmentEntryLink.getJs(),
							fragmentEntryLink.getEditableValues(),
							fragmentEntryLink.getPosition(), serviceContext);

					newFragmentEntryLinkIdsJSONArray.put(
						newFragmentEntryLink.getFragmentEntryLinkId());
				}

				columnJSONObject.put(
					"fragmentEntryLinkIds", newFragmentEntryLinkIdsJSONArray);
			}
		}

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			layout.getUserId(), layout.getGroupId(),
			_portal.getClassNameId(Layout.class), draftLayout.getPlid(),
			dataJSONObject.toString(), serviceContext);

		Layout pagetTemplateLayout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		_layoutCopyHelper.copyLayout(pagetTemplateLayout, draftLayout);

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
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

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

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}