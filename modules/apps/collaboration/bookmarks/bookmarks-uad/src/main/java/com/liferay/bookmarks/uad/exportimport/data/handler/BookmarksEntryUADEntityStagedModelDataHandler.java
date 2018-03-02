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

package com.liferay.bookmarks.uad.exportimport.data.handler;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.user.associated.data.entity.UADEntity;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class BookmarksEntryUADEntityStagedModelDataHandler
	extends BaseStagedModelDataHandler<UADEntity> {

	public static final String[] CLASS_NAMES =
		{BookmarksEntryUADEntity.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(UADEntity uadEntity) {
		BookmarksEntry entry = _getBookmarksEntry(uadEntity);

		if (entry != null) {
			return entry.getName();
		}

		return null;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, UADEntity uadEntity)
		throws Exception {

		BookmarksEntry entry = _getBookmarksEntry(uadEntity);

		if (entry == null) {
			return;
		}

		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			_getBookmarksEntryUADEntity(uadEntity);

		Element element = portletDataContext.getExportDataElement(
			bookmarksEntryUADEntity);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(bookmarksEntryUADEntity),
			entry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, UADEntity uadEntity)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	private BookmarksEntry _getBookmarksEntry(UADEntity uadEntity) {
		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			_getBookmarksEntryUADEntity(uadEntity);

		if (bookmarksEntryUADEntity != null) {
			return bookmarksEntryUADEntity.getBookmarksEntry();
		}

		return null;
	}

	private BookmarksEntryUADEntity _getBookmarksEntryUADEntity(
		UADEntity uadEntity) {

		if (uadEntity instanceof BookmarksEntryUADEntity) {
			return (BookmarksEntryUADEntity)uadEntity;
		}

		return null;
	}

}