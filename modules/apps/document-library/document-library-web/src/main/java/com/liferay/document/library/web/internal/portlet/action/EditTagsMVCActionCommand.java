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
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.web.internal.selection.Selection;
import com.liferay.document.library.web.internal.selection.SelectionParser;
import com.liferay.document.library.web.internal.selection.SelectionParserImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.StringUtil;

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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		Selection<DLFileEntry> selection = _selectionParser.parse(
			actionRequest);

		Stream<DLFileEntry> dlFileEntryStream = selection.execute();

		dlFileEntryStream.map(
			dlFileEntry -> _assetEntryLocalService.fetchEntry(
				DLFileEntryConstants.getClassName(),
				dlFileEntry.getFileEntryId())
		).forEach(
			assetEntry -> _addTags(assetEntry, serviceContext)
		);
	}

	private void _addTag(
		AssetEntry assetEntry, String tagName, ServiceContext serviceContext) {

		try {
			AssetTag assetTag = _assetTagLocalService.fetchTag(
				assetEntry.getGroupId(), StringUtil.toLowerCase(tagName));

			if (assetTag == null) {
				assetTag = _assetTagLocalService.addTag(
					assetEntry.getUserId(), assetEntry.getGroupId(), tagName,
					serviceContext);
			}

			_assetTagLocalService.addAssetEntryAssetTag(
				assetEntry.getEntryId(), assetTag);

			_assetTagLocalService.incrementAssetCount(
				assetTag.getTagId(), assetEntry.getClassNameId());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private void _addTags(
		AssetEntry assetEntry, ServiceContext serviceContext) {

		for (String assetTagName : serviceContext.getAssetTagNames()) {
			_addTag(assetEntry, assetTagName, serviceContext);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	private final SelectionParser<DLFileEntry> _selectionParser =
		new SelectionParserImpl();

}