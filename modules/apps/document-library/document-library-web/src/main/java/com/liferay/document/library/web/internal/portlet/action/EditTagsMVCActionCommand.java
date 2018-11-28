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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.Selection;
import com.liferay.bulk.selection.SelectionFactory;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_tags"
	},
	service = MVCActionCommand.class
)
public class EditTagsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_editTags(actionRequest);

					return null;
				});

			String successMessage = LanguageUtil.get(
				_portal.getHttpServletRequest(actionRequest), "changes-saved");

			SessionMessages.add(
				actionRequest, "requestProcessed", successMessage);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	private void _editTags(ActionRequest actionRequest) throws PortalException {
		Selection<FileEntry> selection = _selectionFactory.create(
			actionRequest.getParameterMap());

		Stream<FileEntry> fileEntryStream = selection.stream();

		boolean append = ParamUtil.getBoolean(actionRequest, "append");

		Set<String> commonTagNamesSet = SetUtil.fromArray(
			ParamUtil.getStringValues(actionRequest, "commonTagNames"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		Set<String> newTagNamesSet = SetUtil.fromArray(
			serviceContext.getAssetTagNames());

		Set<String> toAddTagNamesSet = new HashSet<>(newTagNamesSet);

		toAddTagNamesSet.removeAll(commonTagNamesSet);

		Set<String> toRemoveTagNamesSet = commonTagNamesSet;

		commonTagNamesSet.removeAll(newTagNamesSet);

		fileEntryStream.forEach(
			fileEntry -> {
				try {
					AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
						DLFileEntryConstants.getClassName(),
						fileEntry.getFileEntryId());

					String[] newTagNames = serviceContext.getAssetTagNames();

					if (append) {
						Set<String> currentTagNamesSet = SetUtil.fromArray(
							assetEntry.getTagNames());

						currentTagNamesSet.removeAll(toRemoveTagNamesSet);
						currentTagNamesSet.addAll(toAddTagNamesSet);

						newTagNames = currentTagNamesSet.toArray(
							new String[currentTagNamesSet.size()]);
					}

					_assetEntryLocalService.updateEntry(
						assetEntry.getUserId(), assetEntry.getGroupId(),
						assetEntry.getClassName(), assetEntry.getClassPK(),
						assetEntry.getCategoryIds(), newTagNames);
				}
				catch (PortalException pe) {
					throw new SystemException(pe);
				}
			});
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private SelectionFactory<FileEntry> _selectionFactory;

}