/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Arquillian.class)
public class JSImportMapsExtenderTopHeadDynamicIncludeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			JSImportMapsExtenderTopHeadDynamicIncludeTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Test
	public void testInclude() throws Exception {
		_registerJSImportMapsContributor(null);

		_company1 = CompanyTestUtil.addCompany(false);

		String json = _include(_company1.getCompanyId());

		Assert.assertTrue(
			json,
			json.matches(".*\"global\".*:.*\"http://localhost/global.js\".*"));

		_registerJSImportMapsContributor(_company1);

		_company2 = CompanyTestUtil.addCompany(false);

		_registerJSImportMapsContributor(_company2);

		json = _include(_company1.getCompanyId());

		Assert.assertFalse(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company2.getCompanyId() + ".js\".*"));
		Assert.assertTrue(
			json,
			json.matches(".*\"global\".*:.*\"http://localhost/global.js\".*"));
		Assert.assertTrue(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company1.getCompanyId() + ".js\".*"));

		json = _include(_company2.getCompanyId());

		Assert.assertFalse(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company1.getCompanyId() + ".js\".*"));
		Assert.assertTrue(
			json,
			json.matches(".*\"global\".*:.*\"http://localhost/global.js\".*"));
		Assert.assertTrue(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company2.getCompanyId() + ".js\".*"));

		json = _include(_portal.getDefaultCompanyId());

		Assert.assertFalse(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company1.getCompanyId() + ".js\".*"));
		Assert.assertFalse(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company2.getCompanyId() + ".js\".*"));
		Assert.assertTrue(
			json,
			json.matches(".*\"global\".*:.*\"http://localhost/global.js\".*"));
	}

	private String _include(long companyId) throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.COMPANY_ID, companyId);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			mockHttpServletRequest, mockHttpServletResponse, null);

		return mockHttpServletResponse.getContentAsString();
	}

	private void _registerJSImportMapsContributor(Company company) {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		if (company == null) {
			jsonObject.put("global", "http://localhost/global.js");
		}
		else {
			dictionary.put(
				"com.liferay.frontend.js.importmaps.company.id",
				company.getCompanyId());

			jsonObject.put(
				"specifier",
				"http://localhost/" + company.getCompanyId() + ".js");
		}

		_serviceRegistrations.add(
			_bundleContext.registerService(
				JSImportMapsContributor.class, () -> jsonObject, dictionary));
	}

	private static BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Company _company1;

	@DeleteAfterTestRun
	private Company _company2;

	@Inject(
		filter = "component.name=com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.JSImportMapsExtenderTopHeadDynamicInclude"
	)
	private DynamicInclude _dynamicInclude;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}