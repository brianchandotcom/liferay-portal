/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.graphql.mutation.v1_0;

import com.liferay.captcha.rest.dto.v1_0.CaptchaForm;
import com.liferay.captcha.rest.resource.v1_0.CaptchaResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Loc Pham
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setCaptchaResourceComponentServiceObjects(
		ComponentServiceObjects<CaptchaResource>
			captchaResourceComponentServiceObjects) {

		_captchaResourceComponentServiceObjects =
			captchaResourceComponentServiceObjects;
	}

	@GraphQLField
	public CaptchaForm createSimpleCaptcha(
			@GraphQLName("captchaForm") CaptchaForm captchaForm)
		throws Exception {

		return _applyComponentServiceObjects(
			_captchaResourceComponentServiceObjects,
			this::_populateResourceContext,
			captchaResource -> captchaResource.postSimpleCaptcha(captchaForm));
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

	private void _populateResourceContext(CaptchaResource captchaResource)
		throws Exception {

		captchaResource.setContextAcceptLanguage(_acceptLanguage);
		captchaResource.setContextCompany(_company);
		captchaResource.setContextHttpServletRequest(_httpServletRequest);
		captchaResource.setContextHttpServletResponse(_httpServletResponse);
		captchaResource.setContextUriInfo(_uriInfo);
		captchaResource.setContextUser(_user);
		captchaResource.setGroupLocalService(_groupLocalService);
		captchaResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<CaptchaResource>
		_captchaResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}