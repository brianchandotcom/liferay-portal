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

package com.liferay.blogs.uad.display;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(immediate = true, service = BlogsEntryUADEntityDisplayHelper.class)
public class BlogsEntryUADEntityDisplayHelper {

	public String getBlogsEntryEditURL(
			BlogsEntry blogsEntry, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			portal.getControlPanelPlid(liferayPortletRequest), portletId,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");
		portletURL.setParameter(
			"redirect", portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter(
			"entryId", String.valueOf(blogsEntry.getEntryId()));

		return portletURL.toString();
	}

	public String[] getDisplayFieldNames() {
		return new String[] {
			"title", "subtitle", "urlTitle", "description", "content",
			"smallImage", "smallImageId"
		};
	}

	public Map<String, Object> getUADEntityNonanonymizableFieldValues(
		BlogsEntry blogsEntry) {

		Map<String, Object> uadEntityNonanonymizableFieldValues =
			new HashMap<>();

		uadEntityNonanonymizableFieldValues.put(
			"content", blogsEntry.getContent());
		uadEntityNonanonymizableFieldValues.put(
			"description", blogsEntry.getDescription());
		uadEntityNonanonymizableFieldValues.put(
			"smallImage", blogsEntry.getSmallImage());
		uadEntityNonanonymizableFieldValues.put(
			"smallImageId", blogsEntry.getSmallImageId());
		uadEntityNonanonymizableFieldValues.put(
			"subtitle", blogsEntry.getSubtitle());
		uadEntityNonanonymizableFieldValues.put("title", blogsEntry.getTitle());
		uadEntityNonanonymizableFieldValues.put(
			"urlTitle", blogsEntry.getUrlTitle());

		return uadEntityNonanonymizableFieldValues;
	}

	@Reference
	protected Portal portal;

}