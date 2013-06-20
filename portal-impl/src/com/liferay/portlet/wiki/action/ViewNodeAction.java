/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 * @author Jack Li
 */
public class ViewNodeAction extends PortletAction {

	public static ActionForward viewNode(
			ActionMapping actionMapping, RenderRequest renderRequest,
			String defaultForward)
		throws Exception {

		try {
			WikiNode node = ActionUtil.getNode(renderRequest);

			if (node == null) {
				ActionUtil.getFirstVisibleNode(renderRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		long categoryId = ParamUtil.getLong(renderRequest, "categoryId");

		if (categoryId > 0) {
			return actionMapping.findForward(
				"portlet.wiki.view_categorized_pages");
		}
		else {
			return actionMapping.findForward(defaultForward);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return viewNode(
			actionMapping, renderRequest,
			getForward(renderRequest, "portlet.wiki.view_node"));
	}

}