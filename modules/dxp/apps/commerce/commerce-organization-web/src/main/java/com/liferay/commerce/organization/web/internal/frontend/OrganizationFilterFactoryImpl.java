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

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.FilterFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrganizationClayTableDataSetDisplayView.NAME,
	service = FilterFactory.class
)
public class OrganizationFilterFactoryImpl implements FilterFactory {

	@Override
	public Filter create(HttpServletRequest httpServletRequest) {
		OrganizationFilterImpl organizationFilterImpl =
			new OrganizationFilterImpl();

		organizationFilterImpl.setOrganizationId(
			ParamUtil.getLong(httpServletRequest, "organizationId"));
		organizationFilterImpl.setUserId(
			ParamUtil.getLong(httpServletRequest, "userId"));

		organizationFilterImpl.setKeywords(
			ParamUtil.getString(httpServletRequest, "q"));

		return organizationFilterImpl;
	}

}