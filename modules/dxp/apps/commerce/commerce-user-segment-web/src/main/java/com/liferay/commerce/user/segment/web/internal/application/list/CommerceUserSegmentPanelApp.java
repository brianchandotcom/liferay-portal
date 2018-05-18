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

package com.liferay.commerce.user.segment.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.commerce.application.list.constants.CommercePanelCategoryKeys;
import com.liferay.commerce.user.segment.constants.CommerceUserSegmentPortletKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=500",
		"panel.category.key=" + CommercePanelCategoryKeys.SITE_ADMINISTRATION_COMMERCE
	},
	service = PanelApp.class
)
public class CommerceUserSegmentPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CommerceUserSegmentPortletKeys.COMMERCE_USER_SEGMENT;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CommerceUserSegmentPortletKeys.COMMERCE_USER_SEGMENT + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}