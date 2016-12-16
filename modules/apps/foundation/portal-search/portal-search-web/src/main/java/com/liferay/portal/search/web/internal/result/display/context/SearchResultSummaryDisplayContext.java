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

package com.liferay.portal.search.web.internal.result.display.context;

import java.io.Serializable;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayContext implements Serializable {

	public long getAssetEntryUserId() {
		return _assetEntryUserId;
	}

	public String getAssetRendererURLDownload() {
		return _assetRendererURLDownload;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getContent() {
		return _content;
	}

	public List<SearchResultFieldDisplayContext>
		getDocumentFormFieldDisplayContexts() {

		return _documentFormFieldDisplayContexts;
	}

	public String getFieldAssetCategoryIds() {
		return _fieldAssetCategoryIds;
	}

	public String getFieldAssetTagNames() {
		return _fieldAssetTagNames;
	}

	public String getHighlightedTitle() {
		return _highlightedTitle;
	}

	public String getLocaleLanguageId() {
		return _localeLanguageId;
	}

	public String getLocaleReminder() {
		return _localeReminder;
	}

	public String getModelResource() {
		return _modelResource;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getTitle() {
		return _title;
	}

	public String getViewURL() {
		return _viewURL;
	}

	public boolean isAssetCategoriesOrTagsVisible() {
		return _assetCategoriesOrTagsVisible;
	}

	public boolean isAssetRendererURLDownloadVisible() {
		return _assetRendererURLDownloadVisible;
	}

	public boolean isContentVisible() {
		return _contentVisible;
	}

	public boolean isDocumentFormVisible() {
		return _documentFormVisible;
	}

	public boolean isLocaleReminderVisible() {
		return _localeReminderVisible;
	}

	public boolean isUserPortraitVisible() {
		return _userPortraitVisible;
	}

	public void setAssetCategoriesOrTagsVisible(
		boolean assetCategoriesOrTagsVisible) {

		_assetCategoriesOrTagsVisible = assetCategoriesOrTagsVisible;
	}

	public void setAssetEntryUserId(long assetEntryUserId) {
		_assetEntryUserId = assetEntryUserId;
	}

	public void setAssetRendererURLDownload(String assetRendererURLDownload) {
		_assetRendererURLDownload = assetRendererURLDownload;
	}

	public void setAssetRendererURLDownloadVisible(
		boolean assetRendererURLDownloadVisible) {

		_assetRendererURLDownloadVisible = assetRendererURLDownloadVisible;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setContentVisible(boolean contentVisible) {
		_contentVisible = contentVisible;
	}

	public void setDocumentFormFieldDisplayContexts(
		List<SearchResultFieldDisplayContext>
			documentFormFieldDisplayContexts) {

		_documentFormFieldDisplayContexts = documentFormFieldDisplayContexts;
	}

	public void setDocumentFormVisible(boolean documentFormVisible) {
		_documentFormVisible = documentFormVisible;
	}

	public void setFieldAssetCategoryIds(String fieldAssetCategoryIds) {
		_fieldAssetCategoryIds = fieldAssetCategoryIds;
	}

	public void setFieldAssetTagNames(String fieldAssetTagNames) {
		_fieldAssetTagNames = fieldAssetTagNames;
	}

	public void setHighlightedTitle(String highlightedTitle) {
		_highlightedTitle = highlightedTitle;
	}

	public void setLocaleLanguageId(String localeLanguageId) {
		_localeLanguageId = localeLanguageId;
	}

	public void setLocaleReminder(String localeReminder) {
		_localeReminder = localeReminder;
	}

	public void setLocaleReminderVisible(boolean localeReminderVisible) {
		_localeReminderVisible = localeReminderVisible;
	}

	public void setModelResource(String modelResource) {
		_modelResource = modelResource;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUserPortraitVisible(boolean userPortraitVisible) {
		_userPortraitVisible = userPortraitVisible;
	}

	public void setViewURL(String viewURL) {
		_viewURL = viewURL;
	}

	private boolean _assetCategoriesOrTagsVisible;
	private long _assetEntryUserId;
	private String _assetRendererURLDownload;
	private boolean _assetRendererURLDownloadVisible;
	private String _className;
	private long _classPK;
	private String _content;
	private boolean _contentVisible;
	private List<SearchResultFieldDisplayContext>
		_documentFormFieldDisplayContexts;
	private boolean _documentFormVisible;
	private String _fieldAssetCategoryIds;
	private String _fieldAssetTagNames;
	private String _highlightedTitle;
	private String _localeLanguageId;
	private String _localeReminder;
	private boolean _localeReminderVisible;
	private String _modelResource;
	private PortletURL _portletURL;
	private String _title;
	private boolean _userPortraitVisible;
	private String _viewURL;

}