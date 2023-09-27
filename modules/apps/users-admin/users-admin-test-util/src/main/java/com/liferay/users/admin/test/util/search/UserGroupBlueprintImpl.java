/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.test.util.search;

import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author André de Oliveira
 */
public class UserGroupBlueprintImpl implements UserGroupBlueprint {

	public UserGroupBlueprintImpl() {
	}

	public UserGroupBlueprintImpl(
		UserGroupBlueprintImpl userGroupBlueprintImpl) {

		_companyId = userGroupBlueprintImpl._companyId;
		_description = userGroupBlueprintImpl._description;
		_name = userGroupBlueprintImpl._name;
		_serviceContext = userGroupBlueprintImpl._serviceContext;
		_userId = userGroupBlueprintImpl._userId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	public static class UserGroupBlueprintBuilderImpl
		implements UserGroupBlueprintBuilder {

		@Override
		public UserGroupBlueprint build() {
			return new UserGroupBlueprintImpl(_userGroupBlueprintImpl);
		}

		@Override
		public UserGroupBlueprintBuilder companyId(long companyId) {
			_userGroupBlueprintImpl._companyId = companyId;

			return this;
		}

		@Override
		public UserGroupBlueprintBuilder description(String description) {
			_userGroupBlueprintImpl._description = description;

			return this;
		}

		@Override
		public UserGroupBlueprintBuilder name(String name) {
			_userGroupBlueprintImpl._name = name;

			return this;
		}

		@Override
		public UserGroupBlueprintBuilder serviceContext(
			ServiceContext serviceContext) {

			_userGroupBlueprintImpl._serviceContext = serviceContext;

			return this;
		}

		@Override
		public UserGroupBlueprintBuilder userId(long userId) {
			_userGroupBlueprintImpl._userId = userId;

			return this;
		}

		private final UserGroupBlueprintImpl _userGroupBlueprintImpl =
			new UserGroupBlueprintImpl();

	}

	private long _companyId;
	private String _description;
	private String _name;
	private ServiceContext _serviceContext;
	private long _userId;

}