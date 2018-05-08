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

package com.liferay.portal.security.audit.event.generators.internal.model.listener;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.security.audit.event.generators.internal.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.internal.model.listener.util.AuditMessageBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupRoleModelListener
	extends BaseModelListener<UserGroupRole> {

	public void onBeforeCreate(UserGroupRole userGroupRole)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.ASSIGN, userGroupRole);
	}

	public void onBeforeRemove(UserGroupRole userGroupRole)
		throws ModelListenerException {

		auditOnCreateOrRemove(EventTypes.UNASSIGN, userGroupRole);
	}

	protected void auditOnCreateOrRemove(
			String eventType, UserGroupRole userGroupRole)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, User.class.getName(), userGroupRole.getUserId(),
				null);

			JSONObject additionalInfo = auditMessage.getAdditionalInfo();

			additionalInfo.put("roleId", userGroupRole.getRoleId());

			Role role = userGroupRole.getRole();

			additionalInfo.put("roleName", role.getName());

			Group group = userGroupRole.getGroup();

			additionalInfo.put("scopeClassName", group.getClassName());
			additionalInfo.put("scopeClassPK", group.getClassPK());

			_auditRouter.route(auditMessage);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	private AuditRouter _auditRouter;

}