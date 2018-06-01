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
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

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

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			additionalInfoJSONObject.put("roleId", userGroupRole.getRoleId());

			Role role = userGroupRole.getRole();

			additionalInfoJSONObject.put("roleName", role.getName());

			Group group = userGroupRole.getGroup();

			additionalInfoJSONObject.put(
				"scopeClassName", group.getClassName());
			additionalInfoJSONObject.put("scopeClassPK", group.getClassPK());

			_auditRouter.route(auditMessage);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	private AuditRouter _auditRouter;

}