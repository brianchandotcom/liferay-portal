/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.rss;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import com.sun.syndication.feed.synd.SyndEntry;

import java.util.Date;
import java.util.List;

/**
 * @author Carlos Sierra
 */
public interface RSSRenderer {

	public String getAlternateURL() throws PortalException, SystemException;

	public String getFeedURL() throws PortalException, SystemException;

	public Date getPublicationDate() throws PortalException, SystemException;

	public String getRSSDescription() throws PortalException, SystemException;

	public String getRSSFeedType() throws PortalException, SystemException;

	public String getRSSName() throws PortalException, SystemException;

	public void populateFeedEntries(List<? super SyndEntry> syndEntries)
		throws PortalException, SystemException;

}