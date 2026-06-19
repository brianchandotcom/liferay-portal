/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.portlet.route;

import com.liferay.audiences.constants.AudiencesPortletKeys;
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"com.liferay.portlet.friendly-url-routes=META-INF/friendly-url-routes/routes.xml",
		"jakarta.portlet.name=" + AudiencesPortletKeys.AUDIENCES
	},
	service = FriendlyURLMapper.class
)
public class AudiencesFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String getMapping() {
		return _MAPPING;
	}

	private static final String _MAPPING = "audiences";

}