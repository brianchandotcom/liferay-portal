/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.exception.DuplicateCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Cordeiro
 */
@RunWith(Arquillian.class)
public class CPAttachmentFileEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);
	}

	@Before
	public void setUp() throws Exception {
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_company.getGroupId(), _user.getUserId());

		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(), _serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		List<CPDefinition> cpDefinitions =
			_cpDefinitionLocalService.getCPDefinitions(
				_commerceCatalog.getGroupId(), WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinition cpDefinition : cpDefinitions) {
			_cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}

		_cpOptionLocalService.deleteCPOptions(_company.getCompanyId());
	}

	@Test(expected = DuplicateCPAttachmentFileEntryException.class)
	public void testUpdateWithExistingExternalReferenceCode() throws Exception {
		frutillaRule.scenario(
			"Update a product attachment"
		).given(
			"A product with an attachment"
		).and(
			"A second product without an attachment"
		).when(
			"A second attachment with the same ERC as the first attachment " +
				"is posted to the second product"
		).then(
			"An exception is thrown"
		);

		CPDefinition cpDefinition1 = CPTestUtil.addCPDefinition(
			_company.getGroupId(), "simple", true, false);

		CPDefinition cpDefinition2 = CPTestUtil.addCPDefinition(
			_company.getGroupId(), "simple", true, false);

		CPAttachmentFileEntry cpAttachmentFileEntry = _addCPAttachmentFileEntry(
			cpDefinition1);

		Calendar displayDateCalendar = Calendar.getInstance();

		displayDateCalendar.setTime(RandomTestUtil.nextDate());

		Calendar expirationDateCalendar = Calendar.getInstance();

		expirationDateCalendar.setTime(RandomTestUtil.nextDate());

		_cpAttachmentFileEntryLocalService.addOrUpdateCPAttachmentFileEntry(
			cpAttachmentFileEntry.getExternalReferenceCode(), _user.getUserId(),
			_company.getGroupId(),
			_classNameLocalService.getClassNameId(CPDefinition.class),
			cpDefinition2.getCPDefinitionId(), 0,
			cpAttachmentFileEntry.getFileEntryId(), false, null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR),
			displayDateCalendar.get(Calendar.MINUTE),
			expirationDateCalendar.get(Calendar.MONTH),
			expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
			expirationDateCalendar.get(Calendar.YEAR),
			expirationDateCalendar.get(Calendar.HOUR),
			expirationDateCalendar.get(Calendar.MINUTE), true, true,
			RandomTestUtil.randomLocaleStringMap(), null,
			RandomTestUtil.nextDouble(),
			CPAttachmentFileEntryConstants.TYPE_OTHER, _serviceContext);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CPAttachmentFileEntry _addCPAttachmentFileEntry(
			CPDefinition cpDefinition)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			_company.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, RandomTestUtil.nextDate(), _serviceContext);

		Calendar displayDateCalendar = Calendar.getInstance();

		displayDateCalendar.setTime(RandomTestUtil.nextDate());

		Calendar expirationDateCalendar = Calendar.getInstance();

		expirationDateCalendar.setTime(RandomTestUtil.nextDate());

		return _cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			_company.getGroupId(),
			_classNameLocalService.getClassNameId(CPDefinition.class),
			cpDefinition.getCPDefinitionId(), fileEntry.getFileEntryId(), false,
			null, displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR),
			displayDateCalendar.get(Calendar.MINUTE),
			expirationDateCalendar.get(Calendar.MONTH),
			expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
			expirationDateCalendar.get(Calendar.YEAR),
			expirationDateCalendar.get(Calendar.HOUR),
			expirationDateCalendar.get(Calendar.MINUTE), true, true,
			RandomTestUtil.randomLocaleStringMap(), null,
			RandomTestUtil.nextDouble(),
			CPAttachmentFileEntryConstants.TYPE_OTHER, _serviceContext);
	}

	private static Company _company;
	private static User _user;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Inject
	private CPOptionLocalService _cpOptionLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private ServiceContext _serviceContext;

}