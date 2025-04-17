/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.instance.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ClientExtensionAllCompaniesPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_originalProps = com.liferay.portal.kernel.util.PropsUtil.getProps();

		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());
	}

	@After
	public void tearDown() {
		com.liferay.portal.kernel.util.PropsUtil.setProps(_originalProps);

		for (AutoCloseable autoCloseable : _autoCloseables) {
			try {
				autoCloseable.close();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	@Test
	public void testPortalInstanceRegistered() throws Exception {
		String externalReferenceCode1 =
			"included-custom-element-" + StringUtil.randomString(4);

		_addCETConfiguration(
			externalReferenceCode1,
			ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
			"friendlyURLMapping=vanilla-counter", "instanceable=false",
			StringBundler.concat(
				"urls=index.", RandomTestUtil.randomString(), ".js"),
			"useESM=false", "htmlElementName=vanilla-counter",
			StringBundler.concat(
				"cssURLs=style.", RandomTestUtil.randomString(), ".css"),
			"portletCategoryName=category.client-extensions");

		String externalReferenceCode2 =
			"included-global-js-" + RandomTestUtil.randomString(4);

		_addCETConfiguration(
			externalReferenceCode2,
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
			StringBundler.concat(
				"url=global.", RandomTestUtil.randomString(), ".js"));

		String externalReferenceCode3 =
			"excluded-global-js-" + RandomTestUtil.randomString(4);

		_addCETConfiguration(
			externalReferenceCode3,
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS,
			StringBundler.concat(
				"url=global.", RandomTestUtil.randomString(), ".js"));

		PropsUtil.set(
			"client.extension.all.companies.external.reference.codes",
			externalReferenceCode1 + "," + externalReferenceCode2);

		Company company = CompanyTestUtil.addCompany(
			RandomTestUtil.randomString());

		Assert.assertNotNull(
			_cetManager.getCET(company.getCompanyId(), externalReferenceCode1));
		Assert.assertNotNull(
			_cetManager.getCET(company.getCompanyId(), externalReferenceCode2));

		Assert.assertNull(
			_cetManager.getCET(company.getCompanyId(), externalReferenceCode3));
	}

	private void _addCETConfiguration(
			String externalReferenceCode, String type, String... typeSettings)
		throws Exception {

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			CETConfiguration.class.getName(), externalReferenceCode,
			HashMapDictionaryBuilder.<String, Object>put(
				"baseURL", "${portalURL}/o/" + externalReferenceCode
			).put(
				"buildTimestamp", System.currentTimeMillis()
			).put(
				"description", ""
			).put(
				"dxp.lxc.liferay.com.virtualInstanceId", "default"
			).put(
				"name", "Test " + externalReferenceCode
			).put(
				"projectId",
				StringUtil.removeSubstring(externalReferenceCode, "-")
			).put(
				"projectName", externalReferenceCode
			).put(
				"properties", new String[] {""}
			).put(
				"service.pid",
				CETConfiguration.class.getName() + "~" + externalReferenceCode
			).put(
				"sourceCodeURL", ""
			).put(
				"type", type
			).put(
				"typeSettings", typeSettings
			).put(
				"webContextPath", "/test_" + externalReferenceCode
			).build());

		_autoCloseables.add(
			() -> ConfigurationTestUtil.deleteConfiguration(pid));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClientExtensionAllCompaniesPortalInstanceLifecycleListenerTest.class);

	private final List<AutoCloseable> _autoCloseables = new ArrayList<>();

	@Inject
	private CETManager _cetManager;

	private Props _originalProps;

}