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

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public class ActionAdapter implements Action {

	public ActionAdapter(StrutsAction strutsAction) {
		_strutsAction = strutsAction;
	}

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		StrutsAction originalStrutsAction = null;

		if (_originalAction != null) {
			originalStrutsAction = new StrutsActionAdapter(
				_originalAction, actionMapping);
		}

		String forward = _strutsAction.execute(
			originalStrutsAction, request, response);

		if (Validator.isNull(forward)) {
			return null;
		}

		ActionForward actionForward = actionMapping.getActionForward(forward);

		if (actionForward == null) {
			actionForward = new ActionForward(null, forward);
		}

		return actionForward;
	}

	public void setOriginalAction(Action originalAction) {
		_originalAction = originalAction;
	}

	private Action _originalAction;
	private final StrutsAction _strutsAction;

}