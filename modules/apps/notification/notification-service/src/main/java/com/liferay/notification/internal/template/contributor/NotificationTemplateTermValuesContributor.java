/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.template.contributor;

import com.liferay.notification.contributor.TermValuesContributor;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Victor Kammerer
 */
@Component(service = TermValuesContributor.class)
public class NotificationTemplateTermValuesContributor
	implements TermValuesContributor {

	@Override
	public void contribute(Map<String, Object> termValues) {
		HttpServletRequest httpServletRequest =
			ServiceContextThreadLocal.getServiceContext(
			).getRequest();

		termValues.put("locale", _portal.getLocale(httpServletRequest));
		termValues.put("portalURL", _portal.getPortalURL(httpServletRequest));
	}

	@Reference
	private Portal _portal;

}