/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.softwarecatalog.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Philip Jones
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.SOFTWARE_CATALOG,
		"mvc.command.name=/software_catalog/view_product_entry"
	}
)
public class ViewProductEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			ActionUtil.getProductEntry(renderRequest);

			SCProductEntry productEntry =
				(SCProductEntry)renderRequest.getAttribute(
					WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY);

			if (productEntry == null) {
				throw new NoSuchProductEntryException();
			}
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/html/portlet/software_catalog/error.jsp";
		}

		return "/html/portlet/software_catalog/view_product_entry.jsp";
	}

}