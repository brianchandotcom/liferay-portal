/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = DocumentContributor.class)
public class TrashedModelDocumentContributor
	implements DocumentContributor<TrashedModel> {

	@Override
	public void contribute(
		Document document, BaseModel<TrashedModel> baseModel) {

		if ((baseModel == null) || !(baseModel instanceof TrashedModel)) {
			return;
		}

		TrashedModel trashedModel = (TrashedModel)baseModel;

		if (!trashedModel.isInTrash()) {
			return;
		}

		TrashEntry trashEntry = null;

		try {
			trashEntry = _trashHelper.getTrashEntry(trashedModel);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get trash entry for " + trashedModel,
					portalException);
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
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to locate user " +
								serviceContext.getUserId(),
							portalException);
					}
				}
			}
		}
		else {
			document.addDate(Field.REMOVED_DATE, trashEntry.getCreateDate());
			document.addKeyword(
				Field.REMOVED_BY_USER_NAME, trashEntry.getUserName(), true);

			if (trashedModel.isInTrash() &&
				!_trashHelper.isInTrashExplicitly(trashedModel)) {

				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_NAME, trashEntry.getClassName());
				document.addKeyword(
					Field.ROOT_ENTRY_CLASS_PK, trashEntry.getClassPK());
			}
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			baseModel.getModelClassName());

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
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get trash renderer for " +
						trashEntry.getClassName(),
					portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashedModelDocumentContributor.class);

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private UserLocalService _userLocalService;

}