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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Mika Koivisto
 */
public class PortletActionAdapter extends PortletAction {

	public PortletActionAdapter(StrutsPortletAction strutsPortletAction) {
		_strutsPortletAction = strutsPortletAction;
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		StrutsPortletAction originalStrutsPortletAction = null;

		if (_originalPortletAction != null) {
			originalStrutsPortletAction = new StrutsPortletActionAdapter(
				_originalPortletAction, actionMapping);
		}

		_strutsPortletAction.processAction(
			originalStrutsPortletAction, portletConfig, actionRequest,
			actionResponse);
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		StrutsPortletAction originalStrutsPortletAction = null;

		if (_originalPortletAction != null) {
			originalStrutsPortletAction = new StrutsPortletActionAdapter(
				_originalPortletAction, actionMapping);
		}

		String forward = _strutsPortletAction.render(
			originalStrutsPortletAction, portletConfig, renderRequest,
			renderResponse);

		if (Validator.isNull(forward)) {
			return null;
		}

		ActionForward actionForward = actionMapping.getActionForward(forward);

		if (actionForward == null) {
			actionForward = new ActionForward(null, forward);
		}

		return actionForward;
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		StrutsPortletAction originalStrutsPortletAction = null;

		if (_originalPortletAction != null) {
			originalStrutsPortletAction = new StrutsPortletActionAdapter(
				_originalPortletAction, actionMapping);
		}

		_strutsPortletAction.serveResource(
			originalStrutsPortletAction, portletConfig, resourceRequest,
			resourceResponse);
	}

	public void setOriginalPortletAction(PortletAction portletAction) {
		_originalPortletAction = portletAction;
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		if (_originalPortletAction != null) {
			return _originalPortletAction.isCheckMethodOnProcessAction();
		}

		return _strutsPortletAction.isCheckMethodOnProcessAction();
	}

	private PortletAction _originalPortletAction;
	private final StrutsPortletAction _strutsPortletAction;

}