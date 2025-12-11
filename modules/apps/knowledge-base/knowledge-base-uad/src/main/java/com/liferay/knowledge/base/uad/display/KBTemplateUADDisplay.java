/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.uad.display;

import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@Component(service = UADDisplay.class)
public class KBTemplateUADDisplay extends BaseKBTemplateUADDisplay {

	@Override
	public String getEditURL(
			KBTemplate kbTemplate, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			"/admin/common/edit_kb_template.jsp"
		).setRedirect(
			_portal.getCurrentURL(liferayPortletRequest)
		).setParameter(
			"kbTemplateId", kbTemplate.getKbTemplateId()
		).buildString();
	}

	@Reference
	private Portal _portal;

}