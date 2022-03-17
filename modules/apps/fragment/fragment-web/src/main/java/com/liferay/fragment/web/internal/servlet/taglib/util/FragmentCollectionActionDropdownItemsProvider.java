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

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.web.internal.display.context.FragmentDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;

import java.util.List;

import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class FragmentCollectionActionDropdownItemsProvider {

	public FragmentCollectionActionDropdownItemsProvider(
		FragmentDisplayContext fragmentDisplayContext,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_fragmentDisplayContext = fragmentDisplayContext;
		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_fragmentDisplayContext::hasUpdatePermission,
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.createRenderURL(
									_renderResponse
								).setMVCRenderCommandName(
									"/fragment/edit_fragment_collection"
								).setRedirect(
									_fragmentDisplayContext.getRedirect()
								).setParameter(
									"fragmentCollectionId",
									_fragmentDisplayContext.
										getFragmentCollectionId()
								).buildString());
							dropdownItem.setIcon("pencil");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							ResourceURL
								exportFragmentCompositionsAndFragmentEntriesURL =
									_renderResponse.createResourceURL();

							exportFragmentCompositionsAndFragmentEntriesURL.
								setParameter(
									"fragmentCollectionId",
									String.valueOf(
										_fragmentDisplayContext.
											getFragmentCollectionId()));
							exportFragmentCompositionsAndFragmentEntriesURL.
								setResourceID(
									"/fragment/export_fragment_collections");

							dropdownItem.setHref(
								exportFragmentCompositionsAndFragmentEntriesURL.
									toString());

							dropdownItem.setIcon("upload");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "export"));
						}
					).add(
						_fragmentDisplayContext::hasUpdatePermission,
						dropdownItem -> {
							dropdownItem.putData(
								"action", "openImportCollectionView");
							dropdownItem.putData(
								"viewImportURL",
								PortletURLBuilder.createRenderURL(
									_renderResponse
								).setMVCRenderCommandName(
									"/fragment/view_import"
								).setParameter(
									"fragmentCollectionId",
									_fragmentDisplayContext.
										getFragmentCollectionId()
								).setWindowState(
									LiferayWindowState.POP_UP
								).buildString());
							dropdownItem.setIcon("import");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "import"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_fragmentDisplayContext::hasDeletePermission,
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteFragmentCollection");
							dropdownItem.putData(
								"deleteFragmentCollectionURL",
								PortletURLBuilder.createActionURL(
									_renderResponse
								).setActionName(
									"/fragment/delete_fragment_collection"
								).setParameter(
									"fragmentCollectionId",
									_fragmentDisplayContext.
										getFragmentCollectionId()
								).buildString());
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private final FragmentDisplayContext _fragmentDisplayContext;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}