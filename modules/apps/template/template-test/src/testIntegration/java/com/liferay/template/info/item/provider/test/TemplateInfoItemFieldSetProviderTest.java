/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.template.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class TemplateInfoItemFieldSetProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), _assetVocabulary.getVocabularyId());
	}

	@Test
	public void testGetInfoFieldSetByClassNameAndVariationKeyWhenNoTemplateEntryExists() {
		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				_journalArticle.getDDMStructureKey());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertTrue(infoFields.isEmpty());
	}

	@Test
	public void testGetInfoFieldSetByClassNameAndVariationKeyWhenTemplateEntryExists()
		throws PortalException {

		TemplateEntry articleTemplateEntry = TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			_journalArticle.getDDMStructureKey(), _serviceContext);

		TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				_journalArticle.getDDMStructureKey());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField infoField = infoFields.get(0);

		Assert.assertEquals(
			infoFields.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				articleTemplateEntry.getTemplateEntryId(),
			infoField.getName());
	}

	@Test
	public void testGetInfoFieldSetByClassNameWhenNoTemplateEntryExists() {
		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertTrue(infoFields.isEmpty());
	}

	@Test
	public void testGetInfoFieldSetByClassNameWhenTemplateEntryExists()
		throws PortalException {

		TemplateEntry articleTemplateEntry = TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			_journalArticle.getDDMStructureKey(), _serviceContext);

		TemplateEntry categoryTemplateEntry = TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField infoField = infoFields.get(0);

		Assert.assertEquals(
			infoFields.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				categoryTemplateEntry.getTemplateEntryId(),
			infoField.getName());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameAndVariationKeyWhenNoTemplateEntryExists() {
		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				_journalArticle.getDDMStructureKey(), _journalArticle);

		Assert.assertTrue(infoFieldValues.isEmpty());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameWhenNoTemplateEntryExists() {
		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), _assetCategory);

		Assert.assertTrue(infoFieldValues.isEmpty());
	}

	private AssetCategory _assetCategory;
	private AssetVocabulary _assetVocabulary;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	private JournalArticle _journalArticle;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}