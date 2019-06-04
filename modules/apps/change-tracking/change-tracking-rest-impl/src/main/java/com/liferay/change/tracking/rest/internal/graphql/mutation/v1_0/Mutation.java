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

package com.liferay.change.tracking.rest.internal.graphql.mutation.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.dto.v1_0.SettingsUpdate;
import com.liferay.change.tracking.rest.resource.v1_0.SettingsResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Mate Thurzo
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setSettingsResourceComponentServiceObjects(
		ComponentServiceObjects<SettingsResource>
			settingsResourceComponentServiceObjects) {

		_settingsResourceComponentServiceObjects =
			settingsResourceComponentServiceObjects;
	}

	@GraphQLInvokeDetached
	public Settings putSettings(
			@GraphQLName("companyId") Long companyId,
			@GraphQLName("userId") Long userId,
			@GraphQLName("settingsUpdate") SettingsUpdate settingsUpdate)
		throws Exception {

		return _applyComponentServiceObjects(
			_settingsResourceComponentServiceObjects,
			this::_populateResourceContext,
			settingsResource -> settingsResource.putSettings(
				companyId, userId, settingsUpdate));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(SettingsResource settingsResource)
		throws Exception {

		settingsResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<SettingsResource>
		_settingsResourceComponentServiceObjects;

}