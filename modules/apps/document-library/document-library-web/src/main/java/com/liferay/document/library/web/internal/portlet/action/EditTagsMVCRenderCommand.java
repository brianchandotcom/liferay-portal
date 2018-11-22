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

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.web.internal.selection.Selection;
import com.liferay.document.library.web.internal.selection.SelectionParser;
import com.liferay.document.library.web.internal.selection.SelectionParserImpl;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	service = MVCRenderCommand.class
)
public class EditTagsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Selection<DLFileEntry> selection = _selectionParser.parse(
			renderRequest);

		Stream<DLFileEntry> dlFileEntryStream = selection.execute();

		List<Set<String>> tagNameSets = dlFileEntryStream.map(
			dlFileEntry -> SetUtil.fromArray(
				_assetTagLocalService.getTagNames(
					DLFileEntryConstants.getClassName(),
					dlFileEntry.getFileEntryId()))
		).collect(
			Collectors.toList()
		);

		Stream<String> tagNamesStream = _getCommonTagNames(tagNameSets);

		renderRequest.setAttribute(
			"commonTagNames", tagNamesStream.collect(Collectors.joining(",")));

		renderRequest.setAttribute("selection", selection);

		return "/document_library/edit_tags.jsp";
	}

	private Stream<String> _getCommonTagNames(List<Set<String>> tagNameSets) {
		if (tagNameSets.isEmpty()) {
			return Stream.empty();
		}

		Set<String> commonTagNames = tagNameSets.get(0);

		for (Set<String> tagNames : tagNameSets) {
			commonTagNames = SetUtil.intersect(commonTagNames, tagNames);
		}

		return commonTagNames.stream();
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	private final SelectionParser _selectionParser = new SelectionParserImpl();

}