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

package com.liferay.portlet.blogs.trackback;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;

/**
 * @author André de Oliveira
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.blogs.web.internal.Trackback)}
 */
@Deprecated
public interface Trackback {

	public void addTrackback(
			BlogsEntry entry, ThemeDisplay themeDisplay, String excerpt,
			String url, String blogName, String title,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setCommentManager(CommentManager commentManager);

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setLinkbackConsumer(LinkbackConsumer linkbackConsumer);

}