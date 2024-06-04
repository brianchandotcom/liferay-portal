/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.term.contributor.NotificationTermProvider;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class GetObjectFieldNotificationTemplateTermsMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetCommerceOrderObjectFieldNotificationTemplateTerms()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setLocale(LocaleUtil.getDefault());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_COMMERCE_ORDER", company.getCompanyId());

		mockLiferayResourceRequest.setParameter(
			"objectDefinitionId",
			String.valueOf(objectDefinition.getObjectDefinitionId()));

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject byteArrayToJSONObject = _jsonFactory.createJSONObject(
			byteArrayOutputStream.toString());

		JSONArray termsJSONArray = (JSONArray)byteArrayToJSONObject.get(
			"terms");

		Iterator<JSONObject> termsJSONArrayIterator = termsJSONArray.iterator();

		while (termsJSONArrayIterator.hasNext()) {
			JSONObject jsonObject = termsJSONArrayIterator.next();

			if (Objects.equals(jsonObject.get("termLabel"), "test")) {
				Assert.assertEquals("[%TEST%]", jsonObject.get("termName"));
			}
		}

		_companyLocalService.deleteCompany(company);
	}

	public static class TestNotificationTermProvider
		implements NotificationTermProvider {

		@Override
		public Map<String, String> getNotificationTerms() {
			return HashMapBuilder.put(
				"test", "[%TEST%]"
			).build();
		}

	}

	@Component(service = ObjectDefinitionDeployer.class)
	public static class TestObjectDefinitionDeployerImpl
		implements ObjectDefinitionDeployer {

		@Override
		public List<ServiceRegistration<?>> deploy(
			ObjectDefinition objectDefinition) {

			if (StringUtil.equalsIgnoreCase(
					"CommerceOrder", objectDefinition.getShortName())) {

				return Collections.singletonList(
					_bundleContext.registerService(
						NotificationTermProvider.class,
						new TestNotificationTermProvider(),
						HashMapDictionaryBuilder.<String, Object>put(
							"class.name", objectDefinition.getClassName()
						).build()));
			}

			return Collections.emptyList();
		}

		@Activate
		protected void activate(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		private BundleContext _bundleContext;

	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "mvc.command.name=/notification_templates/get_object_field_notification_template_terms"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}