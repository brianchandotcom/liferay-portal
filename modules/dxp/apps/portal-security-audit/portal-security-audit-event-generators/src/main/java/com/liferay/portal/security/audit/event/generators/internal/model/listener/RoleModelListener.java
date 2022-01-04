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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.event.generators.util.Attribute;
import com.liferay.portal.security.audit.event.generators.util.AttributesBuilder;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onBeforeAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		_auditOnAddorRemoveAssociation(
			EventTypes.ASSIGN, classPK, associationClassName,
			associationClassPK);
	}

	public void onBeforeCreate(Role role) throws ModelListenerException {
		auditOnCreateOrRemove(EventTypes.ADD, role);
	}

	public void onBeforeRemove(Role role) throws ModelListenerException {
		auditOnCreateOrRemove(EventTypes.DELETE, role);
	}

	@Override
	public void onBeforeRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		_auditOnAddorRemoveAssociation(
			EventTypes.UNASSIGN, classPK, associationClassName,
			associationClassPK);
	}

	public void onBeforeUpdate(Role originalRole, Role role)
		throws ModelListenerException {

		try {
			List<Attribute> attributes = _getModifiedAttributes(
				originalRole, role);

			if (!attributes.isEmpty()) {
				AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
						EventTypes.UPDATE, Role.class.getName(),
						role.getRoleId(), attributes);

				_auditRouter.route(auditMessage);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	protected void auditOnCreateOrRemove(String eventType, Role role)
		throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
				eventType, Role.class.getName(), role.getRoleId(), null);

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _auditOnAddorRemoveAssociation(
			String eventType, Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		if (!associationClassName.equals(Group.class.getName()) &&
			!associationClassName.equals(Organization.class.getName()) &&
			!associationClassName.equals(User.class.getName())) {

			return;
		}

		try {
			AuditMessage auditMessage = null;

			if (associationClassName.equals(Group.class.getName())) {
				long groupId = (Long)associationClassPK;

				Group group = _groupLocalService.getGroup(groupId);

				auditMessage = AuditMessageBuilder.buildAuditMessage(
					eventType, group.getClassName(), group.getClassPK(), null);
			}
			else {
				auditMessage = AuditMessageBuilder.buildAuditMessage(
					eventType, associationClassName, (Long)associationClassPK,
					null);
			}

			JSONObject additionalInfoJSONObject =
				auditMessage.getAdditionalInfo();

			long roleId = (Long)classPK;

			additionalInfoJSONObject.put("roleId", roleId);

			Role role = _roleLocalService.getRole(roleId);

			additionalInfoJSONObject.put("roleName", role.getName());

			_auditRouter.route(auditMessage);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private List<Attribute> _getModifiedAttributes(
		Role originalRole, Role role) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(
			role, originalRole);

		attributesBuilder.add("description");
		attributesBuilder.add("name");
		attributesBuilder.add("subtype");
		attributesBuilder.add("type");

		return attributesBuilder.getAttributes();
	}

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}