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

package com.liferay.blogs.web.internal.display.context;

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.settings.BlogsGroupServiceSettings;
import com.liferay.blogs.web.internal.helper.BlogsItemSelectorHelper;
import com.liferay.blogs.web.internal.util.BlogsEntryUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.ResourceBundle;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class BlogsEditEntryDisplayContext {

	public BlogsEditEntryDisplayContext(
		BlogsEntry blogsEntry, BlogsItemSelectorHelper blogsItemSelectorHelper,
		HttpServletRequest httpServletRequest,
		PortletResponse portletResponse) {

		_blogsEntry = blogsEntry;
		_blogsItemSelectorHelper = blogsItemSelectorHelper;
		_httpServletRequest = httpServletRequest;
		_portletResponse = portletResponse;
	}

	public BlogsEntry getBlogsEntry() {
		return _blogsEntry;
	}

	public String getContent() {
		if (_content != null) {
			return _content;
		}

		_content = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "content");

		return _content;
	}

	public String getCoverImageCaption() {
		if (_coverImageCaption != null) {
			return _coverImageCaption;
		}

		_coverImageCaption = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "coverImageCaption",
			LanguageUtil.get(_httpServletRequest, "caption"));

		return _coverImageCaption;
	}

	public long getCoverImageFileEntryId() {
		if (_coverImageFileEntryId != null) {
			return _coverImageFileEntryId;
		}

		_coverImageFileEntryId = BeanParamUtil.getLong(
			getBlogsEntry(), _httpServletRequest, "coverImageFileEntryId");

		return _coverImageFileEntryId;
	}

	public String getCoverImageItemSelectorEventName() {
		return _portletResponse.getNamespace() + "coverImageSelectedItem";
	}

	public String getCoverImageItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _blogsItemSelectorHelper.getItemSelectorURL(
			requestBackedPortletURLFactory, themeDisplay,
			getCoverImageItemSelectorEventName());
	}

	public String getDescription() {
		if (_description != null) {
			return _description;
		}

		String description = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "description");

		if (!isCustomAbstract()) {
			description = StringUtil.shorten(
				getContent(), PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH);
		}

		_description = description;

		return _description;
	}

	public long getEntryId() {
		if (_entryId != null) {
			return _entryId;
		}

		_entryId = BeanParamUtil.getLong(
			getBlogsEntry(), _httpServletRequest, "entryId");

		return _entryId;
	}

	public String[] getImageExtensions() throws ConfigurationException {
		BlogsFileUploadsConfiguration blogsFileUploadsConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				BlogsFileUploadsConfiguration.class);

		return blogsFileUploadsConfiguration.imageExtensions();
	}

	public long getImageMaxSize() throws ConfigurationException {
		BlogsFileUploadsConfiguration blogsFileUploadsConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				BlogsFileUploadsConfiguration.class);

		return blogsFileUploadsConfiguration.imageMaxSize();
	}

	public String getPageTitle(ResourceBundle resourceBundle) {
		BlogsEntry entry = getBlogsEntry();

		if (entry != null) {
			return BlogsEntryUtil.getDisplayTitle(resourceBundle, entry);
		}

		return LanguageUtil.get(_httpServletRequest, "new-blog-entry");
	}

	public String getPortletResource() {
		return ParamUtil.getString(_httpServletRequest, "portletResource");
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getReferringPortletResource() {
		return ParamUtil.getString(
			_httpServletRequest, "referringPortletResource");
	}

	public long getSmallImageFileEntryId() {
		if (_smallImageFileEntryId != null) {
			return _smallImageFileEntryId;
		}

		_smallImageFileEntryId = BeanParamUtil.getLong(
			getBlogsEntry(), _httpServletRequest, "smallImageFileEntryId");

		return _smallImageFileEntryId;
	}

	public String getSmallImageItemSelectorEventName() {
		return _portletResponse.getNamespace() + "smallImageSelectedItem";
	}

	public String getSmallImageItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _blogsItemSelectorHelper.getItemSelectorURL(
			requestBackedPortletURLFactory, themeDisplay,
			getSmallImageItemSelectorEventName());
	}

	public String getSubtitle() {
		if (_subtitle != null) {
			return _subtitle;
		}

		_subtitle = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "subtitle");

		return _subtitle;
	}

	public String getTitle() {
		if (_title != null) {
			return _title;
		}

		_title = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "title");

		return _title;
	}

	public String getURLTitle() {
		if (_urlTitle != null) {
			return _urlTitle;
		}

		_urlTitle = BeanParamUtil.getString(
			getBlogsEntry(), _httpServletRequest, "urlTitle");

		return _urlTitle;
	}

	public boolean isAllowPingbacks() {
		if (_allowPingbacks != null) {
			return _allowPingbacks;
		}

		if (PropsValues.BLOGS_PINGBACK_ENABLED &&
			BeanParamUtil.getBoolean(
				getBlogsEntry(), _httpServletRequest, "allowPingbacks", true)) {

			_allowPingbacks = true;
		}
		else {
			_allowPingbacks = false;
		}

		return _allowPingbacks;
	}

	public boolean isAllowTrackbacks() {
		if (_allowTrackbacks != null) {
			return _allowTrackbacks;
		}

		if (PropsValues.BLOGS_TRACKBACK_ENABLED &&
			BeanParamUtil.getBoolean(
				getBlogsEntry(), _httpServletRequest, "allowTrackbacks",
				true)) {

			_allowTrackbacks = true;
		}
		else {
			_allowTrackbacks = false;
		}

		return _allowTrackbacks;
	}

	public boolean isCustomAbstract() {
		if (_customAbstract != null) {
			return _customAbstract;
		}

		BlogsEntry entry = getBlogsEntry();

		boolean defaultValue = false;

		if ((entry != null) && Validator.isNotNull(entry.getDescription())) {
			defaultValue = true;
		}

		_customAbstract = ParamUtil.getBoolean(
			_httpServletRequest, "customAbstract", defaultValue);

		return _customAbstract;
	}

	public boolean isEmailEntryUpdatedEnabled() throws PortalException {
		if (_emailEntryUpdatedEnabled != null) {
			return _emailEntryUpdatedEnabled;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BlogsGroupServiceSettings blogsGroupServiceSettings =
			BlogsGroupServiceSettings.getInstance(
				themeDisplay.getScopeGroupId());

		if ((getBlogsEntry() != null) &&
			blogsGroupServiceSettings.isEmailEntryUpdatedEnabled()) {

			_emailEntryUpdatedEnabled = true;
		}
		else {
			_emailEntryUpdatedEnabled = false;
		}

		return _emailEntryUpdatedEnabled;
	}

	private Boolean _allowPingbacks;
	private Boolean _allowTrackbacks;
	private final BlogsEntry _blogsEntry;
	private final BlogsItemSelectorHelper _blogsItemSelectorHelper;
	private String _content;
	private String _coverImageCaption;
	private Long _coverImageFileEntryId;
	private Boolean _customAbstract;
	private String _description;
	private Boolean _emailEntryUpdatedEnabled;
	private Long _entryId;
	private final HttpServletRequest _httpServletRequest;
	private final PortletResponse _portletResponse;
	private String _redirect;
	private Long _smallImageFileEntryId;
	private String _subtitle;
	private String _title;
	private String _urlTitle;

}