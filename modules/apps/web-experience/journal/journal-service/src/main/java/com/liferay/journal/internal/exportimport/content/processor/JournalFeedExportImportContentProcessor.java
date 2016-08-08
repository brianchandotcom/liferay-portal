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

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
@Component(
	property = {
		"content.processor.order=1",
		"model.class.name=com.liferay.journal.model.JournalFeed"
	},
	service = {ExportImportContentProcessor.class}
)
public class JournalFeedExportImportContentProcessor
	implements ExportImportContentProcessor<JournalFeed, String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, JournalFeed feed,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL();

		newGroupFriendlyURL = newGroupFriendlyURL.substring(1);

		String[] friendlyURLParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), StringPool.FORWARD_SLASH);

		String oldGroupFriendlyURL = friendlyURLParts[2];

		if (newGroupFriendlyURL.equals(oldGroupFriendlyURL)) {
			String targetLayoutFriendlyUrl = StringUtil.replaceFirst(
				feed.getTargetLayoutFriendlyUrl(),
				StringPool.SLASH + newGroupFriendlyURL + StringPool.SLASH,
				StringPool.SLASH + _DATA_HANDLER_GROUP_FRIENDLY_URL +
					StringPool.SLASH);

			feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		}

		Group targetLayoutGroup = _groupLocalService.fetchFriendlyURLGroup(
			portletDataContext.getCompanyId(),
			StringPool.SLASH + oldGroupFriendlyURL);

		boolean privateLayout = false;

		if (!PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING.equals(
				StringPool.SLASH + friendlyURLParts[1])) {

			privateLayout = true;
		}

		String targetLayoutFriendlyURL = StringPool.SLASH + friendlyURLParts[3];

		Layout targetLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			targetLayoutGroup.getGroupId(), privateLayout,
			targetLayoutFriendlyURL);

		Element feedElement = portletDataContext.getExportDataElement(feed);

		portletDataContext.addReferenceElement(
			feed, feedElement, targetLayout,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, JournalFeed feed,
			String content)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL();

		newGroupFriendlyURL = newGroupFriendlyURL.substring(1);

		String[] friendlyURLParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), '/');

		String oldGroupFriendlyURL = friendlyURLParts[2];

		if (oldGroupFriendlyURL.equals(_DATA_HANDLER_GROUP_FRIENDLY_URL)) {
			feed.setTargetLayoutFriendlyUrl(
				StringUtil.replace(
					feed.getTargetLayoutFriendlyUrl(),
					_DATA_HANDLER_GROUP_FRIENDLY_URL, newGroupFriendlyURL));
		}

		return content;
	}

	@Override
	public boolean validateContentReferences(long groupId, String content) {
		return true;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final String _DATA_HANDLER_GROUP_FRIENDLY_URL =
		"@data_handler_group_friendly_url@";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedExportImportContentProcessor.class);

	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;

}