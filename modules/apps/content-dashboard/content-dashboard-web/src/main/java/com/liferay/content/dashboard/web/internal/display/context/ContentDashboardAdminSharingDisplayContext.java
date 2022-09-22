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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminSharingDisplayContext {

	public ContentDashboardAdminSharingDisplayContext(
		ContentDashboardItemFactoryTracker contentDashboardItemFactoryTracker,
		HttpServletRequest httpServletRequest,
		InfoSearchClassMapperTracker infoSearchClassMapperTracker) {

		_contentDashboardItemFactoryTracker =
			contentDashboardItemFactoryTracker;
		_httpServletRequest = httpServletRequest;
		_infoSearchClassMapperTracker = infoSearchClassMapperTracker;
	}

	public String getClassName() {
		return _infoSearchClassMapperTracker.getSearchClassName(
			_getClassName());
	}

	public long getClassPK() {
		return ParamUtil.getLong(_httpServletRequest, "classPK");
	}

	public boolean isSharingButtonVisible() throws PortalException {
		ContentDashboardItemFactory<?> contentDashboardItemFactory =
			_contentDashboardItemFactoryTracker.getContentDashboardItemFactory(
				_getClassName());

		if (contentDashboardItemFactory == null) {
			return false;
		}

		ContentDashboardItem<?> contentDashboardItem = _toContentDashboardItem(
			contentDashboardItemFactory, getClassPK());

		if (contentDashboardItem == null) {
			return false;
		}

		ContentDashboardItemAction contentDashboardItemAction =
			_getSharingContentDashboardItemAction(contentDashboardItem);

		if (contentDashboardItemAction.getURL() != null) {
			return true;
		}

		return false;
	}

	public boolean isSharingCollaboratorsVisible() throws PortalException {
		ContentDashboardItemFactory<?> contentDashboardItemFactory =
			_contentDashboardItemFactoryTracker.getContentDashboardItemFactory(
				_getClassName());

		if (contentDashboardItemFactory == null) {
			return false;
		}

		ContentDashboardItem<?> contentDashboardItem = _toContentDashboardItem(
			contentDashboardItemFactory, getClassPK());

		if (contentDashboardItem == null) {
			return false;
		}

		ContentDashboardItemAction contentDashboardItemAction =
			_getSharingCollaboratorsContentDashboardItemAction(
				contentDashboardItem);

		if (contentDashboardItemAction.getURL() != null) {
			return true;
		}

		return false;
	}

	private String _getClassName() {
		return ParamUtil.getString(_httpServletRequest, "className");
	}

	private ContentDashboardItemAction
		_getSharingCollaboratorsContentDashboardItemAction(
			ContentDashboardItem<?> contentDashboardItem) {

		List<ContentDashboardItemAction> contentDashboardItemActions =
			contentDashboardItem.getContentDashboardItemActions(
				_httpServletRequest,
				ContentDashboardItemAction.Type.SHARING_COLLABORATORS);

		if (ListUtil.isNotEmpty(contentDashboardItemActions)) {
			return contentDashboardItemActions.get(0);
		}

		return null;
	}

	private ContentDashboardItemAction _getSharingContentDashboardItemAction(
		ContentDashboardItem<?> contentDashboardItem) {

		List<ContentDashboardItemAction> contentDashboardItemActions =
			contentDashboardItem.getContentDashboardItemActions(
				_httpServletRequest,
				ContentDashboardItemAction.Type.SHARING_BUTTON);

		if (ListUtil.isNotEmpty(contentDashboardItemActions)) {
			return contentDashboardItemActions.get(0);
		}

		return null;
	}

	private ContentDashboardItem<?> _toContentDashboardItem(
		ContentDashboardItemFactory<?> contentDashboardItemFactory,
		long classPK) {

		try {
			return contentDashboardItemFactory.create(
				GetterUtil.getLong(classPK));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardAdminSharingDisplayContext.class);

	private final ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;
	private final HttpServletRequest _httpServletRequest;
	private final InfoSearchClassMapperTracker _infoSearchClassMapperTracker;

}