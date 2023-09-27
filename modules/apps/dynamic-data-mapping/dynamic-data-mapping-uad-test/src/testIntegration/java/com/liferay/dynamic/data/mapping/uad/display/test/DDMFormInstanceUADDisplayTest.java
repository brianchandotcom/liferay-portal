/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceRecordTestUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcos Martins
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceUADDisplayTest
	extends BaseUADDisplayTestCase<DDMFormInstance> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetTopLevelContainer() throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecordWithoutValues(
				_group, TestPropsValues.getUserId());

		Assert.assertNull(
			_uadDisplay.getTopLevelContainer(
				DDMFormInstance.class, 0,
				ddmFormInstanceRecord.getFormInstance()));
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		return DDMFormInstanceRecordTestUtil.
			addDDMFormInstanceRecordWithoutValues(_group, userId);
	}

	@Override
	protected UADDisplay<DDMFormInstance> getUADDisplay() {
		return _uadDisplay;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.dynamic.data.mapping.uad.display.DDMFormInstanceUADDisplay"
	)
	private UADDisplay<DDMFormInstance> _uadDisplay;

}