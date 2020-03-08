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