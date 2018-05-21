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

import com.liferay.oauth.constants.OAuthPortletKeys;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.impl.OAuthApplicationModelImpl;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Igor Beslic
 */
public class OAuthApplicationSearch extends SearchContainer<OAuthApplication> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-oauth-applications-were-found";

	public OAuthApplicationSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new OAuthApplicationDisplayTerms(portletRequest),
			new OAuthApplicationDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			5, iteratorURL, null, EMPTY_RESULTS_MESSAGE);

		OAuthApplicationDisplayTerms displayTerms =
			(OAuthApplicationDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			OAuthApplicationDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			OAuthApplicationDisplayTerms.OAUTH_APPLICATION_ID,
			String.valueOf(displayTerms.getOAuthApplicationId()));

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					OAuthPortletKeys.OAUTH_ADMIN,
					"oauth-applications-order-by-col", orderByCol);
				preferences.setValue(
					OAuthPortletKeys.OAUTH_ADMIN,
					"oauth-applications-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					OAuthPortletKeys.OAUTH_ADMIN,
					"oauth-applications-order-by-col", "id");
				orderByType = preferences.getValue(
					OAuthPortletKeys.OAUTH_ADMIN,
					"oauth-applications-order-by-type", "desc");
			}

			OrderByComparator<OAuthApplication> orderByComparator = null;

			boolean ascending = false;

			if (orderByType.equals("asc")) {
				ascending = true;
			}

			if (orderByCol.equals("id")) {
				orderByComparator = OrderByComparatorFactoryUtil.create(
					OAuthApplicationModelImpl.TABLE_NAME, "oAuthApplicationId",
					ascending);
			}
			else if (orderByCol.equals("name")) {
				orderByComparator = OrderByComparatorFactoryUtil.create(
					OAuthApplicationModelImpl.TABLE_NAME, "name", ascending);
			}

			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthApplicationSearch.class);

}