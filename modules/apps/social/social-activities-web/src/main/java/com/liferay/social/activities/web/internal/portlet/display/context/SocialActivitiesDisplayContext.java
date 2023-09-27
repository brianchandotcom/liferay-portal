/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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