/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMTemplateInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akhash Ramprakash
 */
@RunWith(Arquillian.class)
public class DDMTemplateInfoItemFieldSetProviderImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetInfoItemFieldSet() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext(
				group1, TestPropsValues.getUserId()));

		_addDepotEntryGroupRel(depotEntry, group1);

		Group group2 = GroupTestUtil.addGroup();

		_addDepotEntryGroupRel(depotEntry, group2);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			depotEntry.getGroupId(), JournalArticle.class.getName());

		DDMTemplateTestUtil.addTemplate(
			group1.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		_assertInfoFieldsCount(0, ddmStructure, group2.getGroupId());

		DDMTemplateTestUtil.addTemplate(
			group2.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		_assertInfoFieldsCount(1, ddmStructure, group2.getGroupId());

		DDMTemplateTestUtil.addTemplate(
			depotEntry.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		_assertInfoFieldsCount(2, ddmStructure, group2.getGroupId());
	}

	private void _addDepotEntryGroupRel(DepotEntry depotEntry, Group site)
		throws Exception {

		DepotEntryGroupRel depotEntryGroupRel =
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), site.getGroupId());

		depotEntryGroupRel.setDdmStructuresAvailable(true);

		_depotEntryGroupRelLocalService.updateDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	private void _assertInfoFieldsCount(
			int count, DDMStructure ddmStructure, long groupId)
		throws Exception {

		InfoFieldSet infoFieldSet =
			_ddmTemplateInfoItemFieldSetProvider.getInfoItemFieldSet(
				ddmStructure.getStructureId(), groupId);

		List<InfoField<?>> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), count, infoFields.size());
	}

	@Inject
	private DDMTemplateInfoItemFieldSetProvider
		_ddmTemplateInfoItemFieldSetProvider;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

}