/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class SXPElementLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_sxpElement = _sxpElementLocalService.addSXPElement(
			TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, ""), "{}", false,
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0,
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));
	}

	@Test
	public void testAddSXPElementWithKeyVersion() throws Exception {
		Assert.assertEquals("1.0", _sxpElement.getVersion());

		Assert.assertNotNull(_sxpElement.getKey());
	}

	@Test
	public void testUpdateSXPElementWithKeyVersion() throws Exception {
		String key = _sxpElement.getKey();

		_sxpElement = _sxpElementLocalService.updateSXPElement(
			_sxpElement.getUserId(), _sxpElement.getSXPElementId(),
			_sxpElement.getDescriptionMap(),
			_sxpElement.getElementDefinitionJSON(), _sxpElement.isHidden(),
			_sxpElement.getSchemaVersion(), _sxpElement.getTitleMap(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals("1.1", _sxpElement.getVersion());
		Assert.assertEquals(key, _sxpElement.getKey());

		_sxpElement = _sxpElementLocalService.updateSXPElement(
			_sxpElement.getUserId(), _sxpElement.getSXPElementId(),
			_sxpElement.getDescriptionMap(),
			_sxpElement.getElementDefinitionJSON(), _sxpElement.isHidden(),
			_sxpElement.getSchemaVersion(), _sxpElement.getTitleMap(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals("1.2", _sxpElement.getVersion());
		Assert.assertEquals(key, _sxpElement.getKey());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private SXPElement _sxpElement;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

}