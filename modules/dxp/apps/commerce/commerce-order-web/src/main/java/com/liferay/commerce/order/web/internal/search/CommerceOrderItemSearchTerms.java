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

package com.liferay.commerce.order.web.internal.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderItemSearchTerms
	extends CommerceOrderItemDisplayTerms {

	public CommerceOrderItemSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		sku = DAOParamUtil.getString(portletRequest, SKU);
		title = DAOParamUtil.getString(portletRequest, TITLE);
	}

}