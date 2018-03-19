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

package com.liferay.bookmarks.uad.exporter;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.entity.BookmarksEntryUADEntity;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.exception.UADEntityExporterException;
import com.liferay.user.associated.data.exporter.BaseUADEntityExporter;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY},
	service = UADEntityExporter.class
)
public class BookmarksEntryUADEntityExporter extends BaseUADEntityExporter {

	@Override
	public long count(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		return actionableDynamicQuery.performCount();
	}

	@Override
	public void export(UADEntity uadEntity) throws PortalException {
		BookmarksEntry bookmarksEntry = _getBookmarksEntry(uadEntity);

		String json = getJSON(bookmarksEntry);

		Folder folder = getFolder(
			bookmarksEntry.getCompanyId(), BookmarksPortletKeys.BOOKMARKS,
			_FOLDER_NAME);

		try {
			InputStream is = new UnsyncByteArrayInputStream(
				json.getBytes(StringPool.UTF8));

			PortletFileRepositoryUtil.addPortletFileEntry(
				folder.getGroupId(), bookmarksEntry.getUserId(),
				Group.class.getName(), folder.getGroupId(),
				BookmarksPortletKeys.BOOKMARKS, folder.getFolderId(), is,
				uadEntity.getUADEntityId() + ".json",
				ContentTypes.APPLICATION_JSON, false);
		}
		catch (UnsupportedEncodingException uee) {
			throw new UADEntityExporterException(uee);
		}
	}

	@Override
	public void exportAll(
			final long userId, final PortletDataContext portletDataContext)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BookmarksEntry>() {

				@Override
				public void performAction(BookmarksEntry bookmarksEntry)
					throws PortalException {

					StagedModelDataHandler stagedModelDataHandler =
						getStagedModelDataHandler();

					stagedModelDataHandler.exportStagedModel(
						portletDataContext,
						_getBookmarksEntryUADEntity(userId, bookmarksEntry));
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	protected String getUADEntityName() {
		return BookmarksEntryUADEntity.class.getName();
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(
			_bookmarksEntryLocalService.getActionableDynamicQuery(),
			BookmarksUADConstants.USER_ID_FIELD_NAMES_BOOKMARKS_ENTRY, userId);
	}

	private BookmarksEntry _getBookmarksEntry(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		BookmarksEntryUADEntity bookmarksEntryUADEntity =
			(BookmarksEntryUADEntity)uadEntity;

		return bookmarksEntryUADEntity.getBookmarksEntry();
	}

	private BookmarksEntryUADEntity _getBookmarksEntryUADEntity(
		long userId, BookmarksEntry bookmarksEntry) {

		return new BookmarksEntryUADEntity(
			userId, _getUADEntityId(userId, bookmarksEntry), bookmarksEntry);
	}

	private String _getUADEntityId(long userId, BookmarksEntry bookmarksEntry) {
		return String.valueOf(bookmarksEntry.getEntryId()) + StringPool.POUND +
			String.valueOf(userId);
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof BookmarksEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	private static final String _FOLDER_NAME = "UADExport";

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

	@Reference(
		target = "(model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}