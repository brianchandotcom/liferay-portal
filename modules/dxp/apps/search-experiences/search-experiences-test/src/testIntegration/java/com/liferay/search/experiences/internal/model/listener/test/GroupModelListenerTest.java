/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class GroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnBeforeRemove() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		SXPBlueprint sxpBlueprint1 = _addSXPBlueprint(
			group1.getExternalReferenceCode());

		Group group2 = GroupLocalServiceUtil.getGroup(
			TestPropsValues.getGroupId());

		SXPBlueprint sxpBlueprint2 = _addSXPBlueprint(
			group1.getExternalReferenceCode(),
			group2.getExternalReferenceCode());

		GroupLocalServiceUtil.deleteGroup(group1);

		JSONObject generalConfiguration1JSONObject =
			_getGeneralConfigurationJSONObject(sxpBlueprint1);

		Assert.assertFalse(generalConfiguration1JSONObject.has("scope"));

		JSONObject generalConfiguration2JSONObject =
			_getGeneralConfigurationJSONObject(sxpBlueprint2);

		JSONArray scopeJSONArray = generalConfiguration2JSONObject.getJSONArray(
			"scope");

		Assert.assertArrayEquals(
			new String[] {group2.getExternalReferenceCode()},
			JSONUtil.toStringArray(scopeJSONArray));
	}

	private SXPBlueprint _addSXPBlueprint(
			String... scopeGroupExternalReferenceCodes)
		throws Exception {

		JSONObject generalConfigurationJSONObject = JSONUtil.put(
			"generalConfiguration",
			JSONUtil.put(
				"scope",
				JSONUtil.putAll((Object[])scopeGroupExternalReferenceCodes)));

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(),
			generalConfigurationJSONObject.toString(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			StringPool.BLANK, StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));

		_sxpBlueprints.add(sxpBlueprint);

		return sxpBlueprint;
	}

	private JSONObject _getGeneralConfigurationJSONObject(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		sxpBlueprint = _sxpBlueprintLocalService.getSXPBlueprint(
			sxpBlueprint.getSXPBlueprintId());

		JSONObject configurationJSONObject = _jsonFactory.createJSONObject(
			sxpBlueprint.getConfigurationJSON());

		return configurationJSONObject.getJSONObject("generalConfiguration");
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@DeleteAfterTestRun
	private final List<SXPBlueprint> _sxpBlueprints = new ArrayList<>();

}