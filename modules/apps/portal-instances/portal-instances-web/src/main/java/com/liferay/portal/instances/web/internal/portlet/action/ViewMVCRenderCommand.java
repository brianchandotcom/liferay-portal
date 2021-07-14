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

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.portal.instances.action.contributor.PortalInstanceActionContributor;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesPortletKeys;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
		"mvc.command.name=/", "mvc.command.name=/portal_instances/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			PortalInstancesWebKeys.PORTAL_INSTANCE_ACTION_CONTRIBUTORS,
			_portalInstanceActionContributor);

		return "/view.jsp";
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setPortalInstanceActionContributor(
		PortalInstanceActionContributor portalInstanceActionContributor) {

		_portalInstanceActionContributor.add(portalInstanceActionContributor);
	}

	protected void unsetPortalInstanceActionContributor(
		PortalInstanceActionContributor portalInstanceActionContributor) {

		_portalInstanceActionContributor.remove(
			portalInstanceActionContributor);
	}

	private final List<PortalInstanceActionContributor>
		_portalInstanceActionContributor = new CopyOnWriteArrayList<>();

}