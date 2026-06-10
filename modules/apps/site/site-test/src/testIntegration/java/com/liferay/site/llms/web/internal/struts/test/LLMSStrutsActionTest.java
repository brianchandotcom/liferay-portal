/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.llms.web.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.GroupConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jonathan McCann
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-80518"))
@RunWith(Arquillian.class)
public class LLMSStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		_virtualHostname = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		_virtualHostLocalService.updateVirtualHosts(
			_group.getCompanyId(), layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(
				_virtualHostname, StringPool.BLANK
			).build());
	}

	@Test
	public void testExecuteWithBlankContent() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						company.getCompanyId(), _PID_LLMS_COMPANY_CONFIGURATION,
						HashMapDictionaryBuilder.<String, Object>put(
							"content", StringPool.BLANK
						).put(
							"enabled", true
						).build())) {

			_assertSuccessfulResponse(
				_execute(company.getVirtualHostname()), StringPool.BLANK);
		}

		try (GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _PID_LLMS_GROUP_CONFIGURATION,
						HashMapDictionaryBuilder.<String, Object>put(
							"content", StringPool.BLANK
						).put(
							"enabled", true
						).build())) {

			_assertSuccessfulResponse(
				_execute(_virtualHostname), StringPool.BLANK);
		}
	}

	@Test
	public void testExecuteWithContent() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String companyContent = RandomTestUtil.randomString();

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						company.getCompanyId(), _PID_LLMS_COMPANY_CONFIGURATION,
						HashMapDictionaryBuilder.<String, Object>put(
							"content", companyContent
						).put(
							"enabled", true
						).build())) {

			_assertSuccessfulResponse(
				_execute(company.getVirtualHostname()), companyContent);
		}

		String groupContent = RandomTestUtil.randomString();

		try (GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _PID_LLMS_GROUP_CONFIGURATION,
						HashMapDictionaryBuilder.<String, Object>put(
							"content", groupContent
						).put(
							"enabled", true
						).build())) {

			_assertSuccessfulResponse(_execute(_virtualHostname), groupContent);
		}
	}

	@Test
	public void testExecuteWithLLMSDisabled() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						company.getCompanyId(), _PID_LLMS_COMPANY_CONFIGURATION,
						HashMapDictionaryBuilder.<String, Object>put(
							"content", RandomTestUtil.randomString()
						).put(
							"enabled", false
						).build())) {

			MockHttpServletResponse mockHttpServletResponse = _execute(
				company.getVirtualHostname());

			Assert.assertEquals(
				HttpServletResponse.SC_NOT_FOUND,
				mockHttpServletResponse.getStatus());
		}

		try (GroupConfigurationTemporarySwapper
				groupConfigurationTemporarySwapper =
					new GroupConfigurationTemporarySwapper(
						_group.getGroupId(), _PID_LLMS_GROUP_CONFIGURATION,
						HashMapDictionaryBuilder.<String, Object>put(
							"content", RandomTestUtil.randomString()
						).put(
							"enabled", false
						).build())) {

			MockHttpServletResponse mockHttpServletResponse = _execute(
				_virtualHostname);

			Assert.assertEquals(
				HttpServletResponse.SC_NOT_FOUND,
				mockHttpServletResponse.getStatus());
		}
	}

	private void _assertSuccessfulResponse(
			MockHttpServletResponse mockHttpServletResponse, String content)
		throws Exception {

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
		Assert.assertEquals(
			ContentTypes.TEXT_PLAIN_UTF8,
			mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			content, mockHttpServletResponse.getContentAsString());
	}

	private MockHttpServletResponse _execute(String host) throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("Host", host);
		mockHttpServletRequest.setServerName(host);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_llmsStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		return mockHttpServletResponse;
	}

	private static final String _PID_LLMS_COMPANY_CONFIGURATION =
		"com.liferay.site.internal.configuration.LLMSCompanyConfiguration";

	private static final String _PID_LLMS_GROUP_CONFIGURATION =
		"com.liferay.site.internal.configuration.LLMSGroupConfiguration";

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.site.llms.web.internal.struts.LLMSStrutsAction"
	)
	private StrutsAction _llmsStrutsAction;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

	private String _virtualHostname;

}