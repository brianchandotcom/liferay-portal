/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.graphql.query.v1_0;

import com.liferay.captcha.rest.dto.v1_0.Captcha;
import com.liferay.captcha.rest.resource.v1_0.CaptchaResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;
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
public class Query {

	public static void setCaptchaResourceComponentServiceObjects(
		ComponentServiceObjects<CaptchaResource>
			captchaResourceComponentServiceObjects) {

		_captchaResourceComponentServiceObjects =
			captchaResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {simpleCaptcha{captchaToken, image}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Captcha simpleCaptcha() throws Exception {
		return _applyComponentServiceObjects(
			_captchaResourceComponentServiceObjects,
			this::_populateResourceContext,
			captchaResource -> captchaResource.getSimpleCaptcha());
	}

	@GraphQLName("CaptchaPage")
	public class CaptchaPage {

		public CaptchaPage(Page captchaPage) {
			actions = captchaPage.getActions();

			items = captchaPage.getItems();
			lastPage = captchaPage.getLastPage();
			page = captchaPage.getPage();
			pageSize = captchaPage.getPageSize();
			totalCount = captchaPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Captcha> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}