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

package com.liferay.oauth.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Igor Beslic
 * @author Raymond Augé
 */
public class OAuthApplicationDisplayTerms extends DisplayTerms {

	public static final String NAME = "name";

	public static final String OAUTH_APPLICATION_ID = "oAuthApplicationId";

	public OAuthApplicationDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		name = ParamUtil.getString(portletRequest, NAME);
		oAuthApplicationId = ParamUtil.getInteger(
			portletRequest, OAUTH_APPLICATION_ID);
	}

	public String getName() {
		return name;
	}

	public long getOAuthApplicationId() {
		return oAuthApplicationId;
	}

	protected String name;
	protected long oAuthApplicationId;

}