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

package com.liferay.trash.internal.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.trash.kernel.model.TrashEntry;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = DocumentContributor.class)
public class TrashedModelDocumentContributor implements DocumentContributor {

	@Override
	public void contribute(Document document, BaseModel baseModel) {
		if ((baseModel == null) || !(baseModel instanceof TrashedModel)) {
			return;
		}

		TrashedModel trashedModel = (TrashedModel)baseModel;

		if (!trashedModel.isInTrash()) {
			return;
		}

		TrashEntry trashEntry = null;

		try {
			trashEntry = trashedModel.getTrashEntry();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get trash entry for " + trashedModel, pe);
			}
		}

		if (trashEntry == null) {
			document.addDate(Field.REMOVED_DATE, new Date());

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext != null) {
				try {
					User user = _userLocalService.getUser(
						serviceContext.getUserId());

					document.addKeyword(
						Field.REMOVED_BY_USER_NAME, user.getFullName(), true);
				}
				catch (PortalException pe) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to locate user " +
								serviceContext.getUserId(),
							pe);
					}
				}
			}
		}
		else {
			document.addDate(Field.REMOVED_DATE, trashEntry.getCreateDate());
			document.addKeyword(
				Field.REMOVED_BY_USER_NAME, trashEntry.getUserName(), true);

			if (trashedModel.isInTrash() &&
				!trashedModel.isInTrashExplicitly()) {

				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_NAME, trashEntry.getClassName());
				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_PK, trashEntry.getClassPK());
			}
		}

		TrashHandler trashHandler = trashedModel.getTrashHandler();

		try {
			TrashRenderer trashRenderer = null;

			if ((trashHandler != null) && (trashEntry != null)) {
				trashRenderer = trashHandler.getTrashRenderer(
					trashEntry.getClassPK());
			}

			if (trashRenderer != null) {
				document.addKeyword(Field.TYPE, trashRenderer.getType(), true);
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get trash renderer for " +
						trashEntry.getClassName(),
					pe);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashedModelDocumentContributor.class);

	private UserLocalService _userLocalService;

}