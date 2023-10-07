/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.charon.integration.internal.user.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.scim.charon.integration.internal.util.ModelConverterUtil;
import com.liferay.scim.user.manager.SCIMUser;
import com.liferay.scim.user.manager.SCIMUserManager;

import java.util.Map;

import org.wso2.charon3.core.exceptions.BadRequestException;
import org.wso2.charon3.core.exceptions.CharonException;
import org.wso2.charon3.core.exceptions.NotFoundException;
import org.wso2.charon3.core.exceptions.NotImplementedException;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.objects.Group;
import org.wso2.charon3.core.objects.User;
import org.wso2.charon3.core.objects.plainobjects.GroupsGetResponse;
import org.wso2.charon3.core.objects.plainobjects.UsersGetResponse;
import org.wso2.charon3.core.utils.codeutils.SearchRequest;

/**
 * @author Rafael Praxedes
 */
public class UserManagerImpl implements UserManager {

	public UserManagerImpl(
		CompanyLocalService companyLocalService,
		SCIMUserManager scimUserManager) {

		_companyLocalService = companyLocalService;
		_scimUserManager = scimUserManager;
	}

	@Override
	public Group createGroup(
			Group group, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User createMe(User user, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User createUser(User user, Map<String, Boolean> requiredAttributes)
		throws CharonException {

		return _addOrUpdateUser(user);
	}

	@Override
	public void deleteGroup(String id) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteMe(String userName) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteUser(String userId) throws CharonException {
		try {
			_scimUserManager.deleteSCIMUser(
				CompanyThreadLocal.getCompanyId(), GetterUtil.getLong(userId));
		}
		catch (PortalException portalException) {
			throw new CharonException(
				"Unable to delete user with id " + userId, portalException);
		}
	}

	@Override
	public Group getGroup(String id, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User getMe(String userName, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User getUser(String id, Map<String, Boolean> requiredAttributes)
		throws BadRequestException, CharonException, NotFoundException {

		SCIMUser scimUser = _scimUserManager.fetchSCIMUser(
			CompanyThreadLocal.getCompanyId(), GetterUtil.getLong(id));

		if (scimUser == null) {
			throw new NotFoundException("No user found with id : " + id);
		}

		try {
			return ModelConverterUtil.toUser(scimUser);
		}
		catch (Exception exception) {
			throw new CharonException(exception.getMessage(), exception);
		}
	}

	@Override
	public GroupsGetResponse listGroupsWithPost(
			SearchRequest searchRequest,
			Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public UsersGetResponse listUsersWithPost(
			SearchRequest searchRequest,
			Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public Group updateGroup(
			Group oldGroup, Group newGroup,
			Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User updateMe(
			User updatedUser, Map<String, Boolean> requiredAttributes)
		throws NotImplementedException {

		throw new NotImplementedException();
	}

	@Override
	public User updateUser(User user, Map<String, Boolean> requiredAttributes)
		throws CharonException {

		return _addOrUpdateUser(user);
	}

	private User _addOrUpdateUser(User user) throws CharonException {
		try {
			Company company = _companyLocalService.getCompany(
				CompanyThreadLocal.getCompanyId());

			SCIMUser scimUser = _scimUserManager.addOrUpdateSCIMUser(
				ModelConverterUtil.toSCIMUser(
					company.getCompanyId(), company.getLocale(), user));

			return ModelConverterUtil.toUser(scimUser);
		}
		catch (Exception exception) {
			throw new CharonException(
				"Unable to provisioning an portal user for " +
					user.getDisplayName(),
				exception);
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final SCIMUserManager _scimUserManager;

}