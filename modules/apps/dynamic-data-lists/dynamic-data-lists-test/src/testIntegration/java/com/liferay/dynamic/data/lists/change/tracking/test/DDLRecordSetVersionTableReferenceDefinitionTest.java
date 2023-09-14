/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class DDLRecordSetVersionTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		DDLRecordSetTestHelper ddlRecordSetTestHelper =
			new DDLRecordSetTestHelper(group);
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDLRecordSet.class.getName());

		_ddlRecordSet = ddlRecordSetTestHelper.addRecordSet(ddmStructure);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _ddlRecordSetLocalService.updateRecordSet(
			_ddlRecordSet.getRecordSetId(), _ddlRecordSet.getDDMStructureId(),
			_ddlRecordSet.getNameMap(), _ddlRecordSet.getDescriptionMap(),
			RandomTestUtil.randomInt(),
			ServiceContextTestUtil.getServiceContext());
	}

	private DDLRecordSet _ddlRecordSet;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

}