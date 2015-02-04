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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordSetTestHelper;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestHelper;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Zsolt Berentey
 */
public class DDLPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Override
	protected void addStagedModels() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DDLRecordSet.class.getName());

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			stagingGroup);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmStructure);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			stagingGroup, recordSet);

		recordTestHelper.addRecord();
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new DDLPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.DYNAMIC_DATA_LISTS;
	}

}