/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.security.audit.event.generators.util.Attribute;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class UserGroupModelListener extends BaseModelListener<UserGroup> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			_onAfterAssociation(
				classPK, associationClassName, associationClassPK,
				"CMP_ADD_MEMBER");
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			_onAfterAssociation(
				classPK, associationClassName, associationClassPK,
				"CMP_REMOVE_MEMBER");
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _onAfterAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK, String eventType)
		throws PortalException {

		if (!associationClassName.equals(Group.class.getName())) {
			return;
		}

		UserGroup userGroup = _userGroupLocalService.getUserGroup(
			(Long)classPK);

		route(
			new Attribute(userGroup.getName()), eventType,
			(Long)associationClassPK);
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}