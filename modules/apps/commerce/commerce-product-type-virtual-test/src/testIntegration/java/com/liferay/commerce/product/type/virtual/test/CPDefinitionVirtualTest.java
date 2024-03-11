/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.test.util.VirtualCPTypeTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Chiappa
 */
@RunWith(Arquillian.class)
public class CPDefinitionVirtualTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.product.type.virtual.service"));

	@Before
	public void setUp() throws Exception {
		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(),
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinition> iterator = _cpDefinitions.iterator();

		while (iterator.hasNext()) {
			CPDefinition cpDefinitionToDelete = iterator.next();

			_cpDefinitionLocalService.deleteCPDefinition(
				cpDefinitionToDelete.getCPDefinitionId());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a virtual product definition"
		).when(
			"ignoreSKUCombinations is true"
		).and(
			"hasDefaultInstance is true"
		).and(
			"shippable is true"
		).then(
			"product definition should be APPROVED"
		).and(
			"shippable should be false"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, cpDefinition.getStatus());

		Assert.assertEquals("virtual", cpDefinition.getProductTypeName());
		Assert.assertFalse(cpDefinition.isShippable());
	}

	@Test
	public void testCreateCPDVirtualSettingFileEntry() throws Exception {
		frutillaRule.scenario(
			"Add product definition"
		).given(
			"I add a virtual product definition"
		).when(
			"adding file entries"
		).and(
			"deleting the file entries"
		).then(
			"when the last file entry is deleted the DLFileEntry gets deleted"
		);

		DLFolder dlFolder = DLTestUtil.addDLFolder(
			_commerceCatalog.getGroupId());

		DLFileEntry dlFileEntry1 = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		CPDefinition cpDefinition1 = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting1 =
			VirtualCPTypeTestUtil.addCPDefinitionVirtualSetting(
				_commerceCatalog.getGroupId(),
				cpDefinition1.getModelClassName(),
				cpDefinition1.getCPDefinitionId(),
				dlFileEntry1.getFileEntryId(), 1, 0, 0, 0);

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry1 =
			cpDefinitionVirtualSetting1.getCPDVirtualSettingFileEntries(
			).get(
				0
			);

		CPDefinition cpDefinition2 = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting2 =
			VirtualCPTypeTestUtil.addCPDefinitionVirtualSetting(
				_commerceCatalog.getGroupId(),
				cpDefinition2.getModelClassName(),
				cpDefinition2.getCPDefinitionId(),
				dlFileEntry1.getFileEntryId(), 1, 0, 0, 0);

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry2 =
			cpDefinitionVirtualSetting2.getCPDVirtualSettingFileEntries(
			).get(
				0
			);

		DLFileEntry dlFileEntry2 = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		VirtualCPTypeTestUtil.updateCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntry1.
				getCPDefinitionVirtualSettingFileEntryId(),
			dlFileEntry2.getFileEntryId(), cpdVirtualSettingFileEntry1.getUrl(),
			cpdVirtualSettingFileEntry1.getVersion());

		VirtualCPTypeTestUtil.updateCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntry2.
				getCPDefinitionVirtualSettingFileEntryId(),
			dlFileEntry2.getFileEntryId(), cpdVirtualSettingFileEntry2.getUrl(),
			cpdVirtualSettingFileEntry2.getVersion());

		Assert.assertTrue(
			"The DLFileEntry did not get deleted",
			DLFileEntryLocalServiceUtil.fetchDLFileEntry(
				dlFileEntry1.getFileEntryId()) == null);

		CPDefinition cpDefinition3 = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME);

		DLFileEntry dlFileEntry3 = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		VirtualCPTypeTestUtil.addCPDefinitionVirtualSetting(
			_commerceCatalog.getGroupId(), cpDefinition3.getModelClassName(),
			cpDefinition3.getCPDefinitionId(), dlFileEntry3.getFileEntryId(), 1,
			0, 0, 0);

		VirtualCPTypeTestUtil.deleteCPDefinitionVirtualSetting(
			cpDefinition3.getModelClassName(),
			cpDefinition3.getCPDefinitionId());

		Assert.assertTrue(
			"The DLFileEntry did not get deleted",
			DLFileEntryLocalServiceUtil.fetchDLFileEntry(
				dlFileEntry1.getFileEntryId()) == null);
	}

	@Test
	public void testUpdate() throws Exception {
		frutillaRule.scenario(
			"Update virtual product with shippable true"
		).given(
			"I add a a virtual product definition with shippable false"
		).when(
			"shippable is now set to true"
		).then(
			"product definition should have shippable false"
		);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId(), VirtualCPTypeConstants.NAME);

		long cpDefinitionId = cpDefinition.getCPDefinitionId();

		Date displayDate = cpDefinition.getDisplayDate();
		Date expirationDate = cpDefinition.getExpirationDate();

		_cpDefinitionLocalService.updateCPDefinition(
			cpDefinitionId, cpDefinition.getNameMap(),
			cpDefinition.getShortDescriptionMap(),
			cpDefinition.getDescriptionMap(), cpDefinition.getUrlTitleMap(),
			cpDefinition.getMetaTitleMap(),
			cpDefinition.getMetaDescriptionMap(),
			cpDefinition.getMetaKeywordsMap(),
			cpDefinition.isIgnoreSKUCombinations(), true, true, true,
			cpDefinition.getShippingExtraPrice(), cpDefinition.getWidth(),
			cpDefinition.getHeight(), cpDefinition.getDepth(),
			cpDefinition.getWeight(), cpDefinition.getCPTaxCategoryId(),
			cpDefinition.isTaxExempt(), cpDefinition.isTelcoOrElectronics(),
			cpDefinition.getDDMStructureKey(), cpDefinition.isPublished(),
			displayDate.getMonth(), displayDate.getDate(),
			displayDate.getYear(), displayDate.getHours(),
			displayDate.getMinutes(), expirationDate.getMonth(),
			expirationDate.getDate(), expirationDate.getYear(),
			expirationDate.getHours(), expirationDate.getMinutes(), true,
			ServiceContextTestUtil.getServiceContext());

		cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		Assert.assertEquals("virtual", cpDefinition.getProductTypeName());
		Assert.assertFalse(cpDefinition.isShippable());
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	private final List<CPDefinition> _cpDefinitions = new ArrayList<>();

}