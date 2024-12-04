/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class EditOpenGraphMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	@TestInfo("LPD-42898")
	public void testEditOpenGraphWithExistingLayoutSEOEntry() throws Exception {
		Locale locale = LocaleUtil.getSiteDefault();

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_company.getGroupId(),
			_classNameLocalService.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");

		DDMFormValues ddmFormValues = _createDDMFormValues(
			ddmStructure.getDDMForm(), locale);

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				_layout.getLayoutId(), true,
				HashMapBuilder.put(
					locale, RandomTestUtil.randomString()
				).build(),
				false, Collections.emptyMap(), Collections.emptyMap(), 0, false,
				Collections.emptyMap(),
				_getServiceContext(
					ddmFormValues, ddmStructure.getStructureId()));

		_assertDDMFormValues(ddmFormValues, layoutSEOEntry.getDDMStorageId());

		String languageId = _language.getLanguageId(locale);
		String description = RandomTestUtil.randomString();
		String imageAlt = RandomTestUtil.randomString();
		long fileEntryId = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.put(
					"openGraphDescription_" + languageId, description
				).put(
					"openGraphDescriptionEnabled", "true"
				).put(
					"openGraphImageAlt_" + languageId, imageAlt
				).put(
					"openGraphImageFileEntryId", String.valueOf(fileEntryId)
				).put(
					"openGraphTitle_" + languageId, title
				).put(
					"openGraphTitleEnabled", "true"
				).build()),
			new MockLiferayPortletActionResponse());

		LayoutSEOEntry curLayoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				_layout.getGroupId(), _layout.isPrivateLayout(),
				_layout.getLayoutId());

		Assert.assertEquals(
			layoutSEOEntry.getCanonicalURL(),
			curLayoutSEOEntry.getCanonicalURL());

		_assertDDMFormValues(
			ddmFormValues, curLayoutSEOEntry.getDDMStorageId());

		Assert.assertTrue(curLayoutSEOEntry.isOpenGraphDescriptionEnabled());
		Assert.assertEquals(
			description, curLayoutSEOEntry.getOpenGraphDescription(languageId));
		Assert.assertTrue(curLayoutSEOEntry.isOpenGraphTitleEnabled());
		Assert.assertEquals(
			title, curLayoutSEOEntry.getOpenGraphTitle(languageId));
		Assert.assertEquals(
			imageAlt, curLayoutSEOEntry.getOpenGraphImageAlt(languageId));
		Assert.assertEquals(
			fileEntryId, curLayoutSEOEntry.getOpenGraphImageFileEntryId());
	}

	private void _assertDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue,
		DDMFormFieldValue curDDMFormFieldValue) {

		Assert.assertEquals(
			ddmFormFieldValue.getInstanceId(),
			curDDMFormFieldValue.getInstanceId());
		Assert.assertEquals(
			ddmFormFieldValue.getName(), curDDMFormFieldValue.getName());
		Assert.assertEquals(
			ddmFormFieldValue.getValue(), curDDMFormFieldValue.getValue());
	}

	private void _assertDDMFormValues(
			DDMFormValues ddmFormValues, long ddmStorageId)
		throws Exception {

		Assert.assertNotEquals(0, ddmStorageId);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		DDMFormFieldValue propertyDDMFormFieldValue = ddmFormFieldValues.get(0);

		DDMFormValues curDDMFormValues =
			_ddmStorageEngineManager.getDDMFormValues(ddmStorageId);

		List<DDMFormFieldValue> curDDMFormFieldValues =
			curDDMFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			curDDMFormFieldValues.toString(), 1, curDDMFormFieldValues.size());

		DDMFormFieldValue curPropertyDDMFormFieldValue =
			curDDMFormFieldValues.get(0);

		_assertDDMFormFieldValue(
			propertyDDMFormFieldValue, curPropertyDDMFormFieldValue);

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			propertyDDMFormFieldValue.getNestedDDMFormFieldValues();

		DDMFormFieldValue contentDDMFormFieldValue =
			nestedDDMFormFieldValues.get(0);

		List<DDMFormFieldValue> curNestedDDMFormFieldValues =
			curPropertyDDMFormFieldValue.getNestedDDMFormFieldValues();

		Assert.assertEquals(
			curNestedDDMFormFieldValues.toString(), 1,
			curNestedDDMFormFieldValues.size());

		_assertDDMFormFieldValue(
			contentDDMFormFieldValue, curNestedDDMFormFieldValues.get(0));
	}

	private DDMFormValues _createDDMFormValues(DDMForm ddmForm, Locale locale)
		throws Exception {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue propertyDDMFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"property",
				new UnlocalizedValue(RandomTestUtil.randomString()));

		Value value = new LocalizedValue(locale);

		value.addString(locale, RandomTestUtil.randomString());

		propertyDDMFormFieldValue.setNestedDDMFormFields(
			ListUtil.fromArray(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"content", value)));

		ddmFormValues.addDDMFormFieldValue(propertyDDMFormFieldValue);

		return ddmFormValues;
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Map<String, String> parameterMap)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			ContentLayoutTestUtil.getThemeDisplay(_company, _group, _layout));

		mockLiferayPortletActionRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.setParameter(
			"layoutId", String.valueOf(_layout.getLayoutId()));
		mockLiferayPortletActionRequest.setParameter(
			"privateLayout", String.valueOf(_layout.isPrivateLayout()));

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			mockLiferayPortletActionRequest.setParameter(
				entry.getKey(), entry.getValue());
		}

		return mockLiferayPortletActionRequest;
	}

	private ServiceContext _getServiceContext(
			DDMFormValues ddmFormValues, long ddmStructureId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(builder.build());

		String serializedDDMFormValues =
			ddmFormValuesSerializerSerializeResponse.getContent();

		serviceContext.setAttribute(
			ddmStructureId + "ddmFormValues", serializedDDMFormValues);

		return serviceContext;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "ddm.form.values.serializer.type=json")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	@Inject
	private Language _language;

	private Layout _layout;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject(filter = "mvc.command.name=/layout/edit_open_graph")
	private MVCActionCommand _mvcActionCommand;

}