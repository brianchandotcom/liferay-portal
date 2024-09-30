/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class SXPElementUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSXPElementUpgradeProcess() throws Exception {
		for (String elementExternalReferenceCode :
				_ELEMENT_EXTERNAL_REFERENCE_CODES) {

			_createOldElement(elementExternalReferenceCode);
		}

		_runUpgrade("v3_1_4.SXPElementUpgradeProcess");

		for (String elementExternalReferenceCode :
				_ELEMENT_EXTERNAL_REFERENCE_CODES) {

			_assertElementUpgraded(elementExternalReferenceCode);
		}
	}

	@Rule
	public TestName testName = new TestName();

	private void _assertElementUpgraded(String externalReferenceCode)
		throws Exception {

		SXPElement sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				externalReferenceCode, TestPropsValues.getCompanyId());

		JSONAssert.assertEquals(
			sxpElement.getElementDefinitionJSON(),
			_getExpectedElementDefinitionJSON(externalReferenceCode), true);
	}

	private void _createOldElement(String externalReferenceCode)
		throws Exception {

		SXPElement sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				externalReferenceCode, TestPropsValues.getCompanyId());

		if (sxpElement != null) {
			sxpElement.setElementDefinitionJSON(RandomTestUtil.randomString());

			_sxpElementLocalService.updateSXPElement(sxpElement);
		}
		else {
			_sxpElementLocalService.addSXPElement(
				externalReferenceCode, TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
				RandomTestUtil.randomString(), StringPool.BLANK,
				StringPool.BLANK, true, StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				0,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		}
	}

	private String _getExpectedElementDefinitionJSON(
		String externalReferenceCode) {

		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), StringPool.PERIOD,
				StringUtil.toLowerCase(externalReferenceCode), ".json"));
	}

	private void _runUpgrade(String name) throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.search.experiences.internal.upgrade." + name);

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	private static final String[] _ELEMENT_EXTERNAL_REFERENCE_CODES = {
		"BOOST_CONTENTS_IN_A_CATEGORY",
		"BOOST_CONTENTS_IN_A_CATEGORY_BY_KEYWORD_MATCH",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_PERIOD_OF_TIME",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_NEW_USER_ACCOUNTS",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_THE_TIME_OF_DAY",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_USER_SEGMENTS",
		"HIDE_CONTENTS_IN_A_CATEGORY",
		"HIDE_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS"
	};

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

}