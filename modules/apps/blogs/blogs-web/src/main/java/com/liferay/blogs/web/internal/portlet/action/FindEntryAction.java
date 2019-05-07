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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.FindStrutsAction;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "path=/blogs/find_entry",
	service = StrutsAction.class
)
public class FindEntryAction extends FindStrutsAction {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		BlogsEntry entry = _blogsEntryLocalService.getEntry(primaryKey);

		return entry.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "entryId";
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		BlogsEntry entry = _blogsEntryLocalService.getEntry(primaryKey);

		if (Validator.isNotNull(entry.getUrlTitle())) {
			portletURL.setParameter("urlTitle", entry.getUrlTitle());
		}
		else {
			portletURL.setParameter(
				"entryId", String.valueOf(entry.getEntryId()));
		}
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest httpServletRequest, String portletId,
		PortletURL portletURL) {

		String mvcRenderCommandName = null;

		if (portletId.equals(BlogsPortletKeys.BLOGS)) {
			mvcRenderCommandName = "/blogs/view_entry";
		}
		else if (portletId.equals(BlogsPortletKeys.BLOGS_ADMIN)) {
			mvcRenderCommandName = "/blogs_admin/view_entry";
		}
		else {
			mvcRenderCommandName = "/blogs_aggregator/view";
		}

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
	}

	@Override
	protected PortletLayoutFinder getPortletLayoutFinder() {
		return _portletLayoutFinder;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private PortletLayoutFinder _portletLayoutFinder;

}