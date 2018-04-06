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

package com.liferay.portal.security.audit.event.generators.internal.events;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.audit.event.generators.internal.constants.EventTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "key=servlet.service.events.pre",
	service = LifecycleAction.class
)
public class ImpersonationAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			doRun(request, response);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid <= 0) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();
		User realUser = themeDisplay.getRealUser();
		String doAsUserId = themeDisplay.getDoAsUserId();

		HttpSession session = request.getSession();

		Boolean impersonatingUser = (Boolean)session.getAttribute(
			_IMPERSONATING_USER);

		if (Validator.isNotNull(doAsUserId) &&
			(user.getUserId() != realUser.getUserId())) {

			if (impersonatingUser == null) {
				session.setAttribute(_IMPERSONATING_USER, Boolean.TRUE);

				JSONObject additionalInfo = _jsonFactory.createJSONObject();

				additionalInfo.put("userId", user.getUserId());
				additionalInfo.put("userName", user.getFullName());

				AuditMessage auditMessage = new AuditMessage(
					EventTypes.IMPERSONATE, themeDisplay.getCompanyId(),
					realUser.getUserId(), realUser.getFullName(),
					User.class.getName(), String.valueOf(user.getUserId()),
					null, additionalInfo);

				_auditRouter.route(auditMessage);
			}
		}
		else if (impersonatingUser != null) {
			session.removeAttribute(_IMPERSONATING_USER);
		}
	}

	private static final String _IMPERSONATING_USER =
		ImpersonationAction.class + ".IMPERSONATING_USER";

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private JSONFactory _jsonFactory;

}