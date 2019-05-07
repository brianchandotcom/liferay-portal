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

package com.liferay.journal.web.internal.servlet.taglib.clay;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.journal.web.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.journal.web.internal.servlet.taglib.util.JournalDDMTemplateActionDropdownItemsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDDMTemplateVerticalCard extends BaseVerticalCard {

	public JournalDDMTemplateVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, renderRequest, rowChecker);

		_renderResponse = renderResponse;

		_ddmTemplate = (DDMTemplate)baseModel;
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		JournalDDMTemplateActionDropdownItemsProvider
			ddmTemplateActionDropdownItemsProvider =
				new JournalDDMTemplateActionDropdownItemsProvider(
					_ddmTemplate, renderRequest, _renderResponse);

		try {
			return ddmTemplateActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return JournalWebConstants.
			JOURNAL_DDM_TEMPLATE_ELEMENTS_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		try {
			if (!DDMTemplatePermission.contains(
					themeDisplay.getPermissionChecker(), _ddmTemplate,
					ActionKeys.UPDATE)) {

				return StringPool.BLANK;
			}

			PortletURL editDDMTemplateURL = _renderResponse.createRenderURL();

			editDDMTemplateURL.setParameter(
				"mvcPath", "/edit_ddm_template.jsp");
			editDDMTemplateURL.setParameter(
				"redirect", themeDisplay.getURLCurrent());
			editDDMTemplateURL.setParameter(
				"ddmTemplateId", String.valueOf(_ddmTemplate.getTemplateId()));

			return editDDMTemplateURL.toString();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "page-template";
	}

	@Override
	public String getImageSrc() {
		return HtmlUtil.escapeAttribute(
			_ddmTemplate.getTemplateImageURL(themeDisplay));
	}

	@Override
	public String getSubtitle() {
		Date createDate = _ddmTemplate.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			_httpServletRequest,
			System.currentTimeMillis() - createDate.getTime(), true);

		return LanguageUtil.format(
			_httpServletRequest, "modified-x-ago", modifiedDateDescription);
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_ddmTemplate.getName(themeDisplay.getLocale()));
	}

	private final DDMTemplate _ddmTemplate;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}