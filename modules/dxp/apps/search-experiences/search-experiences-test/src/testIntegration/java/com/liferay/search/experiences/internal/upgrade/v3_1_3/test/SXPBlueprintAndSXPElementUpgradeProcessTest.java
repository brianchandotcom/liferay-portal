/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
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
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Joshua Cords
 * @author Felipe Lorenz
 */
@RunWith(Arquillian.class)
public class SXPBlueprintAndSXPElementUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		User user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group1, user.getUserId());
	}

	@Test
	public void testSXPBlueprintUpgradeProcess() throws Exception {
		SXPElement sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				"LIMIT_SEARCH_TO_THESE_SITES", TestPropsValues.getCompanyId());

		if (sxpElement != null) {
			sxpElement.setElementDefinitionJSON(RandomTestUtil.randomString());

			sxpElement = _sxpElementLocalService.updateSXPElement(sxpElement);
		}
		else {
			_sxpElementLocalService.addSXPElement(
				"LIMIT_SEARCH_TO_THESE_SITES", TestPropsValues.getUserId(),
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

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(),
			StringUtil.read(
				_clazz,
				StringBundler.concat(
					"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".configurationJSON.json")),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_getElementInstancesJSON(), "1.1",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.search.experiences.internal.upgrade.v3_1_3." +
				"SXPBlueprintAndSXPElementUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();

		_assertSXPBlueprint(
			_getExpectedElementInstancesJSON(),
			sxpBlueprint.getSXPBlueprintId());

		sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				"LIMIT_SEARCH_TO_THESE_SITES", TestPropsValues.getCompanyId());

		Assert.assertEquals(
			StringUtil.read(
				_clazz,
				StringBundler.concat(
					"dependencies/", _clazz.getSimpleName(),
					".testSXPElementUpgradeProcess.json")),
			sxpElement.getElementDefinitionJSON());
	}

	@Rule
	public TestName testName = new TestName();

	private void _assertSXPBlueprint(
		String expectedElementInstancesJSON, long sxpBlueprintId) {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprintId);

		JSONAssert.assertEquals(
			expectedElementInstancesJSON,
			sxpBlueprint.getElementInstancesJSON(), JSONCompareMode.STRICT);
	}

	private String _getElementInstancesJSON() throws Exception {
		String elementInstancesJSON = StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".before.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_1$]",
			StringBundler.concat(
				_group1.getDescriptiveName(), " (ID: ", _group1.getGroupId(),
				")"));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_2$]",
			StringBundler.concat(
				_group2.getDescriptiveName(), " (ID: ", _group2.getGroupId(),
				")"));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_ID_1$]",
			String.valueOf(_group1.getGroupId()));

		return StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_ID_2$]",
			String.valueOf(_group2.getGroupId()));
	}

	private String _getExpectedElementInstancesJSON() throws Exception {
		String elementInstancesJSON = StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".after.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_1$]",
			_group1.getExternalReferenceCode());
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_2$]",
			_group2.getExternalReferenceCode());
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_1$]",
			StringBundler.concat(
				_group1.getDescriptiveName(), " (ERC: ",
				_group1.getExternalReferenceCode(), ")"));

		return StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_2$]",
			StringBundler.concat(
				_group2.getDescriptiveName(), " (ERC: ",
				_group2.getExternalReferenceCode(), ")"));
	}

	@DeleteAfterTestRun
	private static Group _group1;

	private static Group _group2;
	private static ServiceContext _serviceContext;

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private final Class<?> _clazz = getClass();

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

}