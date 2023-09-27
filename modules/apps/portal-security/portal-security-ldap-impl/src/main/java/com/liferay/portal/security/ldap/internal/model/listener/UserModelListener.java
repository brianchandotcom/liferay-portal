/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.MembershipRequestLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.ldap.internal.UserImportTransactionThreadLocal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Scott Lee
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Vilmos Papp
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseLDAPExportModelListener<User> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(Group.class.getName())) {
				Long userId = (Long)classPK;
				Long groupId = (Long)associationClassPK;

				_updateMembershipRequestStatus(
					userId.longValue(), groupId.longValue());
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		try {
			exportToLDAP(user);
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to export user " + user.getUserId() +
					" to LDAP on after create",
				exception);
		}
	}

	@Override
	public void onAfterUpdate(User originalUser, User user)
		throws ModelListenerException {

		try {
			exportToLDAP(user);
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to export user " + user.getUserId() +
					" to LDAP on after update",
				exception);
		}
	}

	@Override
	public void onBeforeUpdate(User originalUser, User user) {
		UserImportTransactionThreadLocal.setOriginalEmailAddress(
			user.getOriginalEmailAddress());
	}

	protected void exportToLDAP(User user) throws Exception {
		exportToLDAP(user, _userExporter, _ldapSettings);
	}

	private void _updateMembershipRequestStatus(long userId, long groupId)
		throws Exception {

		long principalUserId = GetterUtil.getLong(
			PrincipalThreadLocal.getName());

		User user = _userLocalService.getUser(userId);

		List<MembershipRequest> membershipRequests =
			_membershipRequestLocalService.getMembershipRequests(
				userId, groupId, MembershipRequestConstants.STATUS_PENDING);

		for (MembershipRequest membershipRequest : membershipRequests) {
			_membershipRequestLocalService.updateStatus(
				principalUserId, membershipRequest.getMembershipRequestId(),
				_language.get(
					user.getLocale(), "your-membership-has-been-approved"),
				MembershipRequestConstants.STATUS_APPROVED, false,
				new ServiceContext());
		}
	}

	@Reference
	private Language _language;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile LDAPSettings _ldapSettings;

	@Reference
	private MembershipRequestLocalService _membershipRequestLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile UserExporter _userExporter;

	@Reference
	private UserLocalService _userLocalService;

}