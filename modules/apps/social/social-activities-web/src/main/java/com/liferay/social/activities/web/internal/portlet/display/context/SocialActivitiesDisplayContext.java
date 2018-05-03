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

package com.liferay.social.activities.web.internal.portlet.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.social.kernel.model.SocialActivitySet;

import java.util.List;

import javax.portlet.ResourceURL;

/**
 * @author Adolfo Pérez
 */
public interface SocialActivitiesDisplayContext {

	public int getMax();

	public String getPaginationURL();

	public int getRSSDelta();

	public String getRSSDisplayStyle();

	public String getRSSFeedType();

	public ResourceURL getRSSResourceURL() throws PortalException;

	public String getSelectedTabName();

	public List<SocialActivitySet> getSocialActivitySets();

	public String getTabsNames();

	public String getTabsURL();

	public String getTaglibFeedTitle() throws PortalException;

	public boolean isRSSEnabled();

	public boolean isSeeMoreControlVisible();

	public boolean isTabsVisible();

}