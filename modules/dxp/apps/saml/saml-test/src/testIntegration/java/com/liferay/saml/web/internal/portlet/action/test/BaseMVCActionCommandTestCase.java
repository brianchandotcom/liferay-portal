/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.portlet.action.test;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upload.test.util.UploadTestUtil;
import com.liferay.saml.constants.SamlPortletKeys;

import jakarta.portlet.PortletException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseMVCActionCommandTestCase<T extends BaseModel<?>> {

	@Test
	public void testProcessAction() throws Exception {
		T baseModel = addBaseModel();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					RandomTestUtil.randomLong())) {

			Assert.assertThrows(
				PortletException.class,
				() -> processAction(
					baseModel,
					_companyLocalService.createCompany(
						CompanyThreadLocal.getCompanyId()),
					RandomTestUtil.randomString(), TestPropsValues.getUser()));
		}

		Company company = _companyLocalService.getCompanyById(
			TestPropsValues.getCompanyId());

		Assert.assertThrows(
			PortletException.class,
			() -> processAction(
				baseModel, company, RandomTestUtil.randomString(),
				UserTestUtil.addUser(company)));

		processAction(
			baseModel, company, RandomTestUtil.randomString(),
			TestPropsValues.getUser());
	}

	protected abstract T addBaseModel();

	protected abstract T fetchBaseModel(long primaryKey);

	protected abstract MVCActionCommand getMVCActionCommand();

	protected abstract String getName(T baseModel);

	protected abstract Map<String, List<String>> getRegularParameters(
		T baseModel);

	protected void processAction(
			T baseModel, Company company, String name, User user)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.COMPANY_ID, company.getCompanyId());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, SamlPortletKeys.SAML_ADMIN);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);

		Layout layout = _layoutLocalService.getLayout(
			TestPropsValues.getPlid());

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		themeDisplay.setUser(user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					getMVCActionCommand(), "_portal",
					ProxyUtil.newProxyInstance(
						Portal.class.getClassLoader(),
						new Class<?>[] {Portal.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(),
									"getUploadPortletRequest")) {

								return _createUploadPortletRequest(
									baseModel, mockLiferayPortletActionRequest,
									name);
							}

							return method.invoke(_portal, args);
						}))) {

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			MVCActionCommand mvcActionCommand = getMVCActionCommand();

			mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			T updatedBaseModel = fetchBaseModel(
				GetterUtil.getLong(baseModel.getPrimaryKeyObj()));

			Assert.assertEquals(name, getName(updatedBaseModel));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	private UploadPortletRequest _createUploadPortletRequest(
		T baseModel, LiferayPortletRequest liferayPortletRequest, String name) {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());

		return UploadTestUtil.createUploadPortletRequest(
			UploadTestUtil.createUploadServletRequest(
				mockMultipartHttpServletRequest, null,
				HashMapBuilder.putAll(
					getRegularParameters(baseModel)
				).put(
					"name", Collections.singletonList(name)
				).build()),
			liferayPortletRequest,
			_portal.getPortletNamespace(SamlPortletKeys.SAML_ADMIN));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

}