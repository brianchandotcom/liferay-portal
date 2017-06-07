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

package com.liferay.commerce.product.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Arquillian.class)
@Sync
public class CPPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected void addStagedModels() throws Exception {
		long groupId = stagingGroup.getGroupId();

		CPOption cpOption = CPTestUtil.addCPOption(groupId);

		CPTestUtil.addCPOptionCategory(groupId);

		CPTestUtil.addCPOptionValue(groupId, cpOption.getCPOptionId());

		CPTestUtil.addCPDefinition(groupId);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(groupId);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			CPTestUtil.addCPDefinitionOptionRel(
				groupId, cpDefinition.getCPDefinitionId(),
				cpOption.getCPOptionId());

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>(
				CPInstanceStagedModelDataHandlerTest.
					CP_DEFINITION_OPTION_VALUE_RELS_COUNT);

		for (int i = 0; i <
			CPInstanceStagedModelDataHandlerTest.
				CP_DEFINITION_OPTION_VALUE_RELS_COUNT; i++) {

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				CPTestUtil.addCPDefinitionOptionValueRel(
					groupId,
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
		}

		CPTestUtil.addCPInstance(
			groupId, cpDefinition.getCPDefinitionId(),
			cpDefinitionOptionValueRels);

		FileEntry fileEntry = CPTestUtil.addFileEntry(groupId);

		CPTestUtil.addCPAttachmentFileEntry(
			groupId, PortalUtil.getClassNameId(CPDefinition.class),
			cpDefinition.getCPDefinitionId(), fileEntry.getFileEntryId(),
			cpDefinitionOptionValueRels);
	}

	@Override
	protected String getPortletId() {
		return CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS;
	}

}