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

package com.liferay.journal.model.listener;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		try {
			_journalArticleLocalService.deleteLayoutArticleReferences(
				layout.getGroupId(), layout.getUuid());

			_journalContentSearchLocalService.deleteLayoutContentSearches(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {

		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	private volatile JournalArticleLocalService _journalArticleLocalService;
	private volatile JournalContentSearchLocalService
		_journalContentSearchLocalService;

}