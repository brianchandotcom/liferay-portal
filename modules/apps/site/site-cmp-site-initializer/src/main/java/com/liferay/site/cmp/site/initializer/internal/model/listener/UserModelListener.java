/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.security.audit.event.generators.util.Attribute;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

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

		User user = _userLocalService.getUser((Long)classPK);

		if (associationClassName.equals(Group.class.getName())) {
			route(
				new Attribute(user.getFullName()), eventType,
				(Long)associationClassPK);
		}
		else if (associationClassName.equals(UserGroup.class.getName())) {
			for (Group group :
					_groupLocalService.getUserGroupGroups(
						(Long)associationClassPK)) {

				route(
					new Attribute(user.getFullName()), eventType,
					group.getGroupId());
			}
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}