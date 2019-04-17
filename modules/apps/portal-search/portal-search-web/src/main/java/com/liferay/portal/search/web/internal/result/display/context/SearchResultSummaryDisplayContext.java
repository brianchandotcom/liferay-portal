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

	public String getCreationDateString() {
		return _creationDateString;
	}

	public String getCreatorUserName() {
		return _creatorUserName;
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

	public List<SearchResultFieldDisplayContext> getFieldDisplayContexts() {
		return _fieldDisplayContexts;
	}

	public String getHighlightedTitle() {
		return _highlightedTitle;
	}

	public String getIconId() {
		return _iconId;
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

	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getThumbnailURLString() {
		return _thumbnailURLString;
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

	public boolean isCreationDateVisible() {
		return _creationDateVisible;
	}

	public boolean isCreatorVisible() {
		return _creatorVisible;
	}

	public boolean isDocumentFormVisible() {
		return _documentFormVisible;
	}

	public boolean isFieldsVisible() {
		return _fieldsVisible;
	}

	public boolean isIconVisible() {
		return _iconVisible;
	}

	public boolean isLocaleReminderVisible() {
		return _localeReminderVisible;
	}

	public boolean isModelResourceVisible() {
		return _modelResourceVisible;
	}

	public boolean isTemporarilyUnavailable() {
		return _temporarilyUnavailable;
	}

	public boolean isThumbnailVisible() {
		return _thumbnailVisible;
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

	public void setCreationDateString(String creationDateString) {
		_creationDateString = creationDateString;
	}

	public void setCreationDateVisible(boolean creationDateVisible) {
		_creationDateVisible = creationDateVisible;
	}

	public void setCreatorUserName(String creatorUserName) {
		_creatorUserName = creatorUserName;
	}

	public void setCreatorVisible(boolean writtenByVisible) {
		_creatorVisible = writtenByVisible;
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

	public void setFieldDisplayContexts(
		List<SearchResultFieldDisplayContext> fieldDisplayContexts) {

		_fieldDisplayContexts = fieldDisplayContexts;
	}

	public void setFieldsVisible(boolean fieldsVisible) {
		_fieldsVisible = fieldsVisible;
	}

	public void setHighlightedTitle(String highlightedTitle) {
		_highlightedTitle = highlightedTitle;
	}

	public void setIconId(String iconId) {
		_iconId = iconId;
	}

	public void setIconVisible(boolean iconVisible) {
		_iconVisible = iconVisible;
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

	public void setModelResourceVisible(boolean modelResourceVisible) {
		_modelResourceVisible = modelResourceVisible;
	}

	public void setPathThemeImages(String pathThemeImages) {
		_pathThemeImages = pathThemeImages;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setTemporarilyUnavailable(boolean temporarilyUnavailable) {
		_temporarilyUnavailable = temporarilyUnavailable;
	}

	public void setThumbnailURLString(String thumbnailURLString) {
		_thumbnailURLString = thumbnailURLString;
	}

	public void setThumbnailVisible(boolean thumbnailVisible) {
		_thumbnailVisible = thumbnailVisible;
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
	private String _creationDateString;
	private boolean _creationDateVisible;
	private String _creatorUserName;
	private boolean _creatorVisible;
	private List<SearchResultFieldDisplayContext>
		_documentFormFieldDisplayContexts;
	private boolean _documentFormVisible;
	private String _fieldAssetCategoryIds;
	private String _fieldAssetTagNames;
	private List<SearchResultFieldDisplayContext> _fieldDisplayContexts;
	private boolean _fieldsVisible;
	private String _highlightedTitle;
	private String _iconId;
	private boolean _iconVisible;
	private String _localeLanguageId;
	private String _localeReminder;
	private boolean _localeReminderVisible;
	private String _modelResource;
	private boolean _modelResourceVisible;
	private String _pathThemeImages;
	private PortletURL _portletURL;
	private boolean _temporarilyUnavailable;
	private String _thumbnailURLString;
	private boolean _thumbnailVisible;
	private String _title;
	private boolean _userPortraitVisible;
	private String _viewURL;

}