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
 * @author Joshua Cords, Felipe Lorenz
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
		_group = GroupTestUtil.addGroup();
		User user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, user.getUserId());
	}

	@Test
	public void testSXPBlueprintUpgradeProcess() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		Group group2 = GroupTestUtil.addGroup();

		SXPBlueprint sxpBlueprint = _addSXPBlueprint(
			_getElementInstancesJSON(group1, group2));

		_runUpgrade();

		_assertSXPBlueprint(
			_getExpectedInstancesJSON(group1, group2),
			sxpBlueprint.getSXPBlueprintId());
	}

	@Test
	public void testSXPElementUpgradeProcess() throws Exception {
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

		_runUpgrade();

		sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				"LIMIT_SEARCH_TO_THESE_SITES", TestPropsValues.getCompanyId());

		Assert.assertEquals(
			_getExpectedElementDefinitionJSON(),
			sxpElement.getElementDefinitionJSON());
	}

	@Rule
	public TestName testName = new TestName();

	private SXPBlueprint _addSXPBlueprint(String elementInstancesJSON)
		throws Exception {

		return _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(),
			StringUtil.read(
				_clazz,
				StringBundler.concat(
					"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".configurationJSON.json")),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			elementInstancesJSON, "1.1",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);
	}

	private void _assertSXPBlueprint(
		String expectedInstancesJSON, long sxpBlueprintId) {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprintId);

		JSONAssert.assertEquals(
			expectedInstancesJSON, sxpBlueprint.getElementInstancesJSON(),
			JSONCompareMode.STRICT);
	}

	private String _createGroupIDLabel(Group group) throws Exception {
		return StringBundler.concat(
			group.getDescriptiveName(), " (ID: ", group.getGroupId(), ")");
	}

	private String _createScopeGroupExternalReferenceCodeLabel(Group group)
		throws Exception {

		return StringBundler.concat(
			group.getDescriptiveName(), " (ERC: ",
			group.getExternalReferenceCode(), ")");
	}

	private String _getElementInstancesJSON(Group group1, Group group2)
		throws Exception {

		String elementInstancesJSON = StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".before.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_1$]",
			_createGroupIDLabel(group1));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_2$]",
			_createGroupIDLabel(group2));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_ID_1$]",
			String.valueOf(group1.getGroupId()));

		return StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_ID_2$]",
			String.valueOf(group2.getGroupId()));
	}

	private String _getExpectedElementDefinitionJSON() {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".json"));
	}

	private String _getExpectedInstancesJSON(Group group1, Group group2)
		throws Exception {

		String elementInstancesJSON = StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".after.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_1$]",
			group1.getExternalReferenceCode());
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_2$]",
			group2.getExternalReferenceCode());
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_1$]",
			_createScopeGroupExternalReferenceCodeLabel(group1));

		return StringUtil.replace(
			elementInstancesJSON, "[$SCOPE_GROUP_LABEL_2$]",
			_createScopeGroupExternalReferenceCodeLabel(group2));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.search.experiences.internal.upgrade.v3_1_3." +
				"SXPBlueprintAndSXPElementUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	@DeleteAfterTestRun
	private static Group _group;

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