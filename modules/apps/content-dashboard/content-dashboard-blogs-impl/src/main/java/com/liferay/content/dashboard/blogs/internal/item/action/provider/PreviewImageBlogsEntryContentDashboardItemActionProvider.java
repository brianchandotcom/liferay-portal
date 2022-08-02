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

package com.liferay.content.dashboard.blogs.internal.item.action.provider;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.blogs.internal.item.action.PreviewImageBlogsEntryContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemActionProvider.class)
public class PreviewImageBlogsEntryContentDashboardItemActionProvider
	implements ContentDashboardItemActionProvider<BlogsEntry> {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		if (!isShow(blogsEntry, httpServletRequest)) {
			return null;
		}

		InfoItemFieldValuesProvider<BlogsEntry> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, BlogsEntry.class.getName());

		return new PreviewImageBlogsEntryContentDashboardItemAction(
			blogsEntry, infoItemFieldValuesProvider, _language);
	}

	@Override
	public String getKey() {
		return "preview-image";
	}

	@Override
	public ContentDashboardItemAction.Type getType() {
		return ContentDashboardItemAction.Type.PREVIEW_IMAGE;
	}

	@Override
	public boolean isShow(
		BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

		if (blogsEntry.isDraft() || blogsEntry.isInTrash()) {
			return false;
		}

		InfoItemFieldValuesProvider<BlogsEntry> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, BlogsEntry.class.getName());

		ContentDashboardItemAction contentDashboardItemAction =
			new PreviewImageBlogsEntryContentDashboardItemAction(
				blogsEntry, infoItemFieldValuesProvider, _language);

		if (Validator.isNull(contentDashboardItemAction.getURL())) {
			return false;
		}

		return true;
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

}