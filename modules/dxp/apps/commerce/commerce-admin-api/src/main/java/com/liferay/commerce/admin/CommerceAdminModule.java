/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.admin;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceAdminModule {

	public String getLabel(Locale locale);

	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse);

	public boolean isVisible(long groupId) throws PortalException;

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException;

}