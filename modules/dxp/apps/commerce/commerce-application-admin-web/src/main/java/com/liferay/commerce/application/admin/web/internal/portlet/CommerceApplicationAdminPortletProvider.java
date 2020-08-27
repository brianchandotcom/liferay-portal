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

package com.liferay.commerce.application.admin.web.internal.portlet;

import com.liferay.commerce.application.constants.CommerceApplicationPortletKeys;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.ManagePortletProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.application.model.CommerceApplicationBrand",
	service = ManagePortletProvider.class
)
public class CommerceApplicationAdminPortletProvider
	extends BasePortletProvider implements ManagePortletProvider {

	@Override
	public String getPortletName() {
		return CommerceApplicationPortletKeys.COMMERCE_APPLICATION_ADMIN;
	}

}