/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.rest.client.dto.v1_0.Report;
import com.liferay.ai.hub.rest.client.resource.v1_0.ReportResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.Calendar;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Fábio Alves
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class ReportResourceTest extends BaseReportResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_accountEntry = _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), TestPropsValues.getUserId());

		_defaultObjectEntryManager =
			(DefaultObjectEntryManager)_objectEntryManager;

		_dtoConverterContext = new DefaultDTOConverterContext(
			false, Map.of(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());

		AccountEntry aiHubAccountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			aiHubAccountEntry.getAccountEntryId(), TestPropsValues.getUserId());

		User user = _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, TestPropsValues.getCompanyId(), true,
			null, null, false,
			_accountEntry.getAccountEntryId() + "-guest-service-account",
			RandomTestUtil.randomString() + "@liferay.com",
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), 0, 0, true,
			Calendar.JANUARY, 1, 1970, StringPool.BLANK,
			UserConstants.TYPE_SERVICE_ACCOUNT, null, null, null, null, false,
			ServiceContextTestUtil.getServiceContext());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			aiHubAccountEntry.getAccountEntryId(), user.getUserId());
		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Override
	@Test
	public void testPostReport() throws Exception {
		super.testPostReport();

		_testPostReport(null, reportResource);

		String externalReferenceCode = RandomTestUtil.randomString();

		_defaultObjectEntryManager.addObjectEntry(
			_dtoConverterContext,
			_objectDefinitionLocalService.getObjectDefinition(
				testCompany.getCompanyId(), "AIHubChatbot"),
			new ObjectEntry() {
				{
					setProperties(
						() -> HashMapBuilder.<String, Object>put(
							"active", true
						).put(
							"externalReferenceCode",
							externalReferenceCode
						).put(
							"r_accountToAIHubChatbots_accountEntryId",
							_accountEntry.getAccountEntryId()
						).put(
							"title", RandomTestUtil.randomString()
						).build());
				}
			},
			null);

		_testPostReport(
			externalReferenceCode,
			ReportResource.builder(
			).endpoint(
				testCompany.getVirtualHostname(),
				PortalUtil.getPortalServerPort(false), "http"
			).locale(
				LocaleUtil.getDefault()
			).build());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"agentDefinitionExternalReferenceCodes",
			"chatbotExternalReferenceCode", "feedback", "reason", "surface",
			"userMessage"
		};
	}

	@Override
	protected Report randomReport() throws Exception {
		Report report = super.randomReport();

		report.setAgentDefinitionExternalReferenceCodes(
			new String[] {"L_FIX_SPELLING_AND_GRAMMAR", "L_MAKE_SHORTER"});
		report.setChatbotExternalReferenceCode(RandomTestUtil.randomString());
		report.setFeedback("negative");
		report.setReason("personalDataExposure");
		report.setSurface("aiAssistant");

		return report;
	}

	@Override
	protected Report testPostReport_addReport(Report report) throws Exception {
		return reportResource.postReport(report);
	}

	private void _testPostReport(
			String chatbotExternalReferenceCode, ReportResource reportResource)
		throws Exception {

		Report randomReport = randomReport();

		randomReport.setChatbotExternalReferenceCode(
			chatbotExternalReferenceCode);

		Report postReport = reportResource.postReport(randomReport);

		assertEquals(randomReport, postReport);

		Assert.assertEquals("critical", postReport.getLevel());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_REPORT", TestPropsValues.getCompanyId());

		Page<ObjectEntry> objectEntriesPage =
			_defaultObjectEntryManager.getRelatedObjectEntries(
				null, _dtoConverterContext,
				postReport.getExternalReferenceCode(), null,
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectDefinitionId1(
						objectDefinition.getObjectDefinitionId(),
						"aiHubAgentDefinitionsToAIHubReports"),
				null, null, null, null);

		Assert.assertArrayEquals(
			randomReport.getAgentDefinitionExternalReferenceCodes(),
			TransformUtil.transformToArray(
				objectEntriesPage.getItems(),
				ObjectEntry::getExternalReferenceCode, String.class));
	}

	private static AccountEntry _accountEntry;

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;

	private static DefaultObjectEntryManager _defaultObjectEntryManager;
	private static DTOConverterContext _dtoConverterContext;

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	@Inject(
		filter = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private static ObjectEntryManager _objectEntryManager;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private static UserLocalService _userLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}