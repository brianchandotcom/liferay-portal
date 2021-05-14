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

package com.liferay.commerce.shop.by.diagram.web.internal.organization.application.context.provider;

import com.liferay.commerce.shop.by.diagram.web.internal.pagination.PaginationImpl;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = PaginationContextProvider.class
)
@Provider
public class PaginationContextProvider implements ContextProvider<Pagination> {

	@Override
	public Pagination createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		int pageNumber = ParamUtil.getInteger(httpServletRequest, "page", 1);
		int itemsPerPage = ParamUtil.getInteger(
			httpServletRequest, "pageSize", 20);

		return new PaginationImpl(itemsPerPage, pageNumber);
	}

}