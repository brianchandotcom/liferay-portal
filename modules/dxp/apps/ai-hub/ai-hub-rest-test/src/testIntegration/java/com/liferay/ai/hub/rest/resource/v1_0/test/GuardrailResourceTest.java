/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.rest.client.dto.v1_0.Guardrail;
import com.liferay.ai.hub.rest.client.problem.Problem;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class GuardrailResourceTest extends BaseGuardrailResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), TestPropsValues.getUserId());

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
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@After
	public void tearDown() throws Exception {
		if (_guardrail != null) {
			guardrailResource.deleteGuardrailByExternalReferenceCode(
				_guardrail.getExternalReferenceCode());
		}
	}

	@Ignore
	@Override
	@Test
	public void testDeleteGuardrailByExternalReferenceCode() throws Exception {
		super.testDeleteGuardrailByExternalReferenceCode();

		DefaultObjectEntryManager defaultObjectEntryManager =
			(DefaultObjectEntryManager)_objectEntryManager;

		Assert.assertNull(
			defaultObjectEntryManager.fetchObjectEntry(
				_dtoConverterContext, _guardrail.getExternalReferenceCode(),
				_getObjectDefinition(), null));

		_guardrail = null;
	}

	@Ignore
	@Override
	@Test
	public void testPostGuardrail() throws Exception {
		super.testPostGuardrail();

		Guardrail guardrail = randomGuardrail();

		guardrail.setExternalReferenceCode(
			_guardrail.getExternalReferenceCode());

		AssertUtils.assertFailure(
			Problem.ProblemException.class,
			"This external reference code is already in use.",
			() -> testPostGuardrail_addGuardrail(guardrail));
	}

	@Ignore
	@Override
	@Test
	public void testPutGuardrailByExternalReferenceCode() throws Exception {
		Guardrail guardrail = randomGuardrail();

		_guardrail = guardrailResource.putGuardrailByExternalReferenceCode(
			guardrail.getExternalReferenceCode(), guardrail);

		Assert.assertNotNull(
			_objectEntryManager.getObjectEntry(
				TestPropsValues.getCompanyId(), _dtoConverterContext,
				_guardrail.getExternalReferenceCode(), _getObjectDefinition(),
				null));
	}

	@Override
	protected Guardrail randomGuardrail() throws Exception {
		Guardrail guardrail = super.randomGuardrail();

		guardrail.setGuardrailType(Guardrail.GuardrailType.INPUT);
		guardrail.setLocation("europe-southwest1");
		guardrail.setTitle_i18n(RandomTestUtil.randomLanguageIdStringMap());

		return guardrail;
	}

	@Override
	protected Guardrail
			testDeleteGuardrailByExternalReferenceCode_addGuardrail()
		throws Exception {

		Guardrail guardrail = randomGuardrail();

		_guardrail = guardrailResource.putGuardrailByExternalReferenceCode(
			guardrail.getExternalReferenceCode(), guardrail);

		return _guardrail;
	}

	@Override
	protected Guardrail testPostGuardrail_addGuardrail(Guardrail guardrail)
		throws Exception {

		_guardrail = guardrailResource.postGuardrail(guardrail);

		return _guardrail;
	}

	private ObjectDefinition _getObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_AI_HUB_MODEL_ARMOR_TEMPLATE",
				TestPropsValues.getCompanyId());
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;

	private static DTOConverterContext _dtoConverterContext;

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	private Guardrail _guardrail;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(filter = "object.entry.manager.storage.type=default")
	private ObjectEntryManager _objectEntryManager;

}