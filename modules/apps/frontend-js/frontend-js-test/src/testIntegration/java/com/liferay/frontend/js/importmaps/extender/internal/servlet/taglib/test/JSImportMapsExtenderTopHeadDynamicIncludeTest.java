/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			JSImportMapsExtenderTopHeadDynamicIncludeTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_company1 = CompanyTestUtil.addCompany(false);

		_company2 = CompanyTestUtil.addCompany(false);

		try (ServiceTrackerList<DynamicInclude> dynamicIncludes =
				ServiceTrackerListFactory.open(
					bundleContext, DynamicInclude.class)) {

			for (DynamicInclude dynamicInclude : dynamicIncludes) {
				Class<? extends DynamicInclude> clazz =
					dynamicInclude.getClass();

				if (Objects.equals(
						clazz.getName(),
						"com.liferay.frontend.js.importmaps.extender." +
							"internal.servlet.taglib." +
								"JSImportMapsExtenderTopHeadDynamicInclude")) {

					_dynamicInclude = dynamicInclude;

					break;
				}
			}
		}

		_registerJSImportMapsContributor(bundleContext, _company1);

		_registerJSImportMapsContributor(bundleContext, _company2);
	}

	@After
	public void tearDown() throws PortalException {
		_companyLocalService.deleteCompany(_company1);
		_companyLocalService.deleteCompany(_company2);

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Test
	public void testInclude() throws IOException {
		String json = _include(_company1.getCompanyId());

		Assert.assertTrue(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company1.getCompanyId() + ".js\".*"));

		Assert.assertFalse(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company2.getCompanyId() + ".js\".*"));

		json = _include(_company2.getCompanyId());

		Assert.assertFalse(
			json,
			json.matches(
				".*\"specifier\".*:.*\"http://localhost/" +
					_company1.getCompanyId() + ".js\".*"));

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
	}

	private String _include(long companyId) throws IOException {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.COMPANY_ID, companyId);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_dynamicInclude.include(
			mockHttpServletRequest, mockHttpServletResponse, null);

		return mockHttpServletResponse.getContentAsString();
	}

	private void _registerJSImportMapsContributor(
		BundleContext bundleContext, Company company) {

		_serviceRegistrations.add(
			bundleContext.registerService(
				JSImportMapsContributor.class,
				() -> {
					JSONObject jsonObject = _jsonFactory.createJSONObject();

					jsonObject.put(
						"specifier",
						"http://localhost/" + company.getCompanyId() + ".js");

					return jsonObject;
				},
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.importmaps.company", company.getCompanyId()
				).build()));
	}

	private Company _company1;
	private Company _company2;

	@Inject
	private CompanyLocalService _companyLocalService;

	private DynamicInclude _dynamicInclude;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}