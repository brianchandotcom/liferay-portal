/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
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
import com.liferay.search.experiences.constants.SXPBlueprintConstants;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Joshua Cords
 * @author Felipe Lorenz
 * @author Selena Aungst
 */
@RunWith(Arquillian.class)
public class SXPBlueprintAndSXPElementUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(), _readJSON("configuration"),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			StringUtil.replace(
				_readJSON("elementInstances"),
				new String[] {
					"[$SCOPE_GROUP_ID_1$]", "[$SCOPE_GROUP_ID_2$]",
					"[$SCOPE_GROUP_LABEL_1$]", "[$SCOPE_GROUP_LABEL_2$]"
				},
				new String[] {
					String.valueOf(_group1.getGroupId()),
					String.valueOf(_group2.getGroupId()),
					StringBundler.concat(
						_group1.getDescriptiveName(), " (ID: ",
						_group1.getGroupId(), ")"),
					StringBundler.concat(
						_group2.getDescriptiveName(), " (ID: ",
						_group2.getGroupId(), ")")
				}),
			SXPBlueprintConstants.SCHEMA_VERSION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				_group1, TestPropsValues.getUserId()));

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

		sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprint.getSXPBlueprintId());

		JSONAssert.assertEquals(
			StringUtil.replace(
				_readJSON("elementInstancesUpdated"),
				new String[] {
					"[$SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_1$]",
					"[$SCOPE_GROUP_EXTERNAL_REFERENCE_CODE_2$]",
					"[$SCOPE_GROUP_LABEL_1$]", "[$SCOPE_GROUP_LABEL_2$]"
				},
				new String[] {
					String.valueOf(_group1.getExternalReferenceCode()),
					String.valueOf(_group2.getExternalReferenceCode()),
					StringBundler.concat(
						_group1.getDescriptiveName(), " (ERC: ",
						_group1.getExternalReferenceCode(), ")"),
					StringBundler.concat(
						_group2.getDescriptiveName(), " (ERC: ",
						_group2.getExternalReferenceCode(), ")")
				}),
			sxpBlueprint.getElementInstancesJSON(), JSONCompareMode.STRICT);

		sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				"LIMIT_SEARCH_TO_THESE_SITES", TestPropsValues.getCompanyId());

		Assert.assertEquals(
			_readJSON("elementDefinition"),
			sxpElement.getElementDefinitionJSON());
	}

	@Test
	public void testUpgradeDoesNotMoveMixedFieldMappingsToDefaultValue()
		throws Exception {

		_assertUpgradeField(
			JSONUtil.put(
				"fieldMappings",
				JSONUtil.putAll(
					JSONUtil.put(
						"label", "pdf"
					).put(
						"value", "pdf"
					),
					JSONUtil.put(
						"field", "title"
					).put(
						"label", "Title"
					))
			).put(
				"name", "file_extensions"
			).put(
				"type", "multiselect"
			),
			JSONUtil.put(
				"fieldMappings",
				JSONUtil.putAll(
					JSONUtil.put("value", "pdf"),
					JSONUtil.put("field", "title"))
			).put(
				"name", "file_extensions"
			).put(
				"type", "multiselect"
			));
	}

	@Test
	public void testUpgradeHandlesLegacyFieldMappingLabel() throws Exception {
		String elementDefinitionJSON = _readJSON(
			"legacyFieldMappingLabelElementDefinition");

		String elementInstancesJSON = _readJSON("legacyFieldMappingLabel");

		Assert.assertNotNull(
			ElementInstanceUtil.toElementInstances(elementInstancesJSON));

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(), StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			StringPool.BLANK, SXPBlueprintConstants.SCHEMA_VERSION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				_group1, TestPropsValues.getUserId()));

		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint);

		SXPElement sxpElement = _sxpElementLocalService.addSXPElement(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			elementDefinitionJSON, StringPool.BLANK, StringPool.BLANK, true,
			StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				TestPropsValues.getUserId()));

		_runUpgrade();

		sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprint.getSXPBlueprintId());

		Assert.assertFalse(
			sxpBlueprint.getElementInstancesJSON(
			).contains(
				"LegacyFieldMappingLabelSentinel"
			));

		sxpElement = _sxpElementLocalService.fetchSXPElement(
			sxpElement.getSXPElementId());

		Assert.assertFalse(
			sxpElement.getElementDefinitionJSON(
			).contains(
				"LegacyFieldMappingLabelSentinel"
			));
	}

	@Test
	public void testUpgradeLeavesFieldMappingListUnchanged() throws Exception {
		JSONObject fieldJSONObject = JSONUtil.put(
			"fieldMappings",
			JSONUtil.putAll(
				JSONUtil.put(
					"boost", "10"
				).put(
					"field", "title"
				).put(
					"locale", "en_US"
				),
				JSONUtil.put(
					"boost", "1"
				).put(
					"field", "content"
				).put(
					"locale", StringPool.BLANK
				))
		).put(
			"name", "fields"
		).put(
			"type", "fieldMappingList"
		);

		_assertUpgradeField(fieldJSONObject, fieldJSONObject);
	}

	@Test
	public void testUpgradeMovesLegacyMultiselectFieldMappingsToDefaultValue()
		throws Exception {

		JSONArray fieldMappingsJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"label", "pdf"
			).put(
				"value", "pdf"
			),
			JSONUtil.put(
				"label", "docx"
			).put(
				"value", "docx"
			));

		_assertUpgradeField(
			JSONUtil.put(
				"fieldMappings", fieldMappingsJSONArray
			).put(
				"name", "file_extensions"
			).put(
				"type", "multiselect"
			),
			JSONUtil.put(
				"defaultValue", fieldMappingsJSONArray
			).put(
				"name", "file_extensions"
			).put(
				"type", "multiselect"
			));
	}

	@Test
	public void testUpgradeSXPBlueprintWithUnknownLegacyField()
		throws Exception {

		String elementInstancesJSON = JSONUtil.put(
			JSONUtil.put(
				"sxpElement",
				JSONUtil.put(
					"externalReferenceCode", "OTHER"
				).put(
					"label", "legacy-label"
				))
		).toString();

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(), StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			StringPool.BLANK, SXPBlueprintConstants.SCHEMA_VERSION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				_group1, TestPropsValues.getUserId()));

		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint);

		_runUpgrade();

		sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprint.getSXPBlueprintId());

		JSONAssert.assertEquals(
			elementInstancesJSON, sxpBlueprint.getElementInstancesJSON(),
			JSONCompareMode.STRICT);
	}

	private void _assertUpgradeField(
			JSONObject fieldJSONObject, JSONObject expectedFieldJSONObject)
		throws Exception {

		String elementInstancesJSON = _toElementInstancesJSON(fieldJSONObject);

		Assert.assertNotNull(
			ElementInstanceUtil.toElementInstances(elementInstancesJSON));

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(), StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			StringPool.BLANK, SXPBlueprintConstants.SCHEMA_VERSION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				_group1, TestPropsValues.getUserId()));

		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);

		sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			sxpBlueprint);

		SXPElement sxpElement = _sxpElementLocalService.addSXPElement(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			_getElementDefinitionJSON(elementInstancesJSON), StringPool.BLANK,
			StringPool.BLANK, true, StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			0,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				TestPropsValues.getUserId()));

		_runUpgrade();

		String expectedElementInstancesJSON = _toElementInstancesJSON(
			expectedFieldJSONObject);

		sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprint.getSXPBlueprintId());

		JSONAssert.assertEquals(
			expectedElementInstancesJSON,
			sxpBlueprint.getElementInstancesJSON(), JSONCompareMode.STRICT);

		sxpElement = _sxpElementLocalService.fetchSXPElement(
			sxpElement.getSXPElementId());

		JSONAssert.assertEquals(
			_getElementDefinitionJSON(expectedElementInstancesJSON),
			sxpElement.getElementDefinitionJSON(), JSONCompareMode.STRICT);
	}

	private String _getElementDefinitionJSON(String elementInstancesJSON)
		throws Exception {

		JSONArray elementInstancesJSONArray = _jsonFactory.createJSONArray(
			elementInstancesJSON);

		JSONObject elementDefinitionJSONObject = JSONUtil.getValueAsJSONObject(
			elementInstancesJSONArray.getJSONObject(0), "JSONObject/sxpElement",
			"JSONObject/elementDefinition");

		return elementDefinitionJSONObject.toString();
	}

	private String _readJSON(String name) {
		return StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
				name, ".json"));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.search.experiences.internal.upgrade.v3_1_3." +
				"SXPBlueprintAndSXPElementUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	private String _toElementInstancesJSON(JSONObject fieldJSONObject) {
		return JSONUtil.put(
			JSONUtil.put(
				"sxpElement",
				JSONUtil.put(
					"elementDefinition",
					JSONUtil.put(
						"uiConfiguration",
						JSONUtil.put(
							"fieldSets",
							JSONUtil.put(
								JSONUtil.put(
									"fields", JSONUtil.put(fieldJSONObject)))))
				).put(
					"externalReferenceCode", "CUSTOM_ELEMENT"
				))
		).toString();
	}

	private final Class<?> _clazz = getClass();

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}