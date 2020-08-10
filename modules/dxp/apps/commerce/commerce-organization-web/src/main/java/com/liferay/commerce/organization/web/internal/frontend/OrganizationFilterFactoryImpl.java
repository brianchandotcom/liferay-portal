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

package com.liferay.commerce.organization.web.internal.frontend;

import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.FilterFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceOrganizationClayTableDataSetDisplayView.NAME,
	service = FilterFactory.class
)
public class OrganizationFilterFactoryImpl implements FilterFactory {

	@Override
	public Filter create(HttpServletRequest httpServletRequest) {
		OrganizationFilterImpl organizationFilterImpl =
			new OrganizationFilterImpl();

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");
		long userId = ParamUtil.getLong(httpServletRequest, "userId");

		organizationFilterImpl.setOrganizationId(organizationId);
		organizationFilterImpl.setUserId(userId);

		String keywords = ParamUtil.getString(httpServletRequest, "q");

		organizationFilterImpl.setKeywords(keywords);

		return organizationFilterImpl;
	}

}