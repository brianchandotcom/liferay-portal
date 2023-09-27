/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import javax.mvc.Models;
import javax.mvc.engine.ViewEngineContext;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.ws.rs.core.Configuration;

/**
 * @author Neil Griffin
 */
@ApplicationScoped
public class ViewEngineContextProducer {

	@Dependent
	@Produces
	public ViewEngineContext getViewEngineContext(
		Configuration configuration, MimeResponse mimeResponse, Models models,
		PortletRequest portletRequest) {

		return new ViewEngineContextImpl(
			configuration, portletRequest.getLocale(), mimeResponse, models,
			portletRequest);
	}

}