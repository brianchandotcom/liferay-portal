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

package com.liferay.commerce.punchout.portal.security.auto.login.internal.events;

import com.liferay.commerce.punchout.constants.PunchOutConstants;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jaclyn Ong
 */
@Component(
	immediate = true, property = "key=logout.events.pre",
	service = LifecycleAction.class
)
public class PunchOutTokenLogoutAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pathInfo = request.getPathInfo();

			if (!pathInfo.contains("/portal/logout")) {
				return;
			}

			HttpSession session = request.getSession();

			Object punchOutReturnUrlObject = session.getAttribute(
				PunchOutConstants.PUNCH_OUT_REDIRECT_URL_ATTRIBUTE_NAME);

			if (punchOutReturnUrlObject == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						PunchOutConstants.
							PUNCH_OUT_REDIRECT_URL_ATTRIBUTE_NAME +
								" not found in session");
				}

				return;
			}

			String redirectURL = (String)punchOutReturnUrlObject;

			if (Validator.isBlank(redirectURL)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						PunchOutConstants.
							PUNCH_OUT_REDIRECT_URL_ATTRIBUTE_NAME +
								" is blank");
				}

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Redirecting to " + redirectURL);
			}

			response.sendRedirect(redirectURL);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PunchOutTokenLogoutAction.class);

}