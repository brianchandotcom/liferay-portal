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

package com.liferay.portal.security.audit.event.generators.internal.events;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "key=login.events.post",
	service = LifecycleAction.class
)
public class LoginPostAction extends Action {

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

		User user = _portal.getUser(request);

		AuditMessage auditMessage = new AuditMessage(
			EventTypes.LOGIN, user.getCompanyId(), user.getUserId(),
			user.getFullName(), User.class.getName(),
			String.valueOf(user.getUserId()));

		_auditRouter.route(auditMessage);
	}

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private Portal _portal;

}