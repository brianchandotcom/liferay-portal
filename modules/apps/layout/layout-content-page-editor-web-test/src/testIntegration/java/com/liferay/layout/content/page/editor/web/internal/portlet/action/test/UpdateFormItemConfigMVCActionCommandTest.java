/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.LongTextInfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.MockInfoServiceRegistrationHolder;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class UpdateFormItemConfigMVCActionCommandTest {

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

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		_prepareServiceContext();
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandFragmentEntryNotAvailable()
		throws Exception {

		String expectedFieldTypeLabel = RandomTestUtil.randomString();

		InfoFieldType infoFieldType = new InfoFieldType() {

			@Override
			public String getLabel(Locale locale) {
				return expectedFieldTypeLabel;
			}

			@Override
			public String getName() {
				return RandomTestUtil.randomString();
			}

		};

		InfoField<?>[] allInfoFields = ArrayUtil.append(
			_INFO_FIELDS, _getInfoField(infoFieldType));

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					_BUNDLE_SYMBOLIC_NAME, _COMPONENT_CLASS_NAME, true);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(allInfoFields)
						).build(),
						_editPageInfoItemCapability)) {

			JSONObject addItemJSONObject =
				ContentLayoutTestUtil.addItemToLayout(
					"{}", LayoutDataItemTypeConstants.TYPE_FORM, _layout,
					_layoutStructureProvider, _segmentsExperienceId);

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			String formItemId = addItemJSONObject.getString("addedItemId");

			JSONObject updateFormJSONObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"classNameId", classNameId
					).put(
						"classTypeId", "0"
					).toString(),
					formItemId, _layout),
				new MockLiferayPortletActionResponse());

			_assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
				updateFormJSONObject, _INFO_FIELDS.length + 1, StringPool.BLANK,
				_language.format(
					_portal.getSiteDefaultLocale(_group),
					"some-fragments-are-missing.-x-fields-cannot-have-an-" +
						"associated-fragment-or-cannot-be-available-in-master",
					expectedFieldTypeLabel),
				0);

			_assertFormStyledLayoutStructureItem(
				classNameId, _INFO_FIELDS.length + 1, formItemId, _INFO_FIELDS,
				true, true);
		}
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandInputsFragmentCollectionProviderNotAvailable()
		throws Exception {

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					_BUNDLE_SYMBOLIC_NAME, _COMPONENT_CLASS_NAME, false);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(_INFO_FIELDS)
						).build(),
						_editPageInfoItemCapability)) {

			JSONObject addItemJSONObject =
				ContentLayoutTestUtil.addItemToLayout(
					"{}", LayoutDataItemTypeConstants.TYPE_FORM, _layout,
					_layoutStructureProvider, _segmentsExperienceId);

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			String formItemId = addItemJSONObject.getString("addedItemId");

			JSONObject updateFormJSONObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"classNameId", classNameId
					).put(
						"classTypeId", "0"
					).toString(),
					formItemId, _layout),
				new MockLiferayPortletActionResponse());

			_assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
				updateFormJSONObject, 0, StringPool.BLANK,
				_language.get(
					_portal.getSiteDefaultLocale(_group),
					"your-form-could-not-be-loaded-because-fragments-are-not-" +
						"available"),
				0);

			_assertFormStyledLayoutStructureItem(
				classNameId, 0, formItemId, new InfoField<?>[0], true, false);
		}
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandMappingForm()
		throws Exception {

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					_BUNDLE_SYMBOLIC_NAME, _COMPONENT_CLASS_NAME, true);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(_INFO_FIELDS)
						).build(),
						_editPageInfoItemCapability)) {

			JSONObject addItemJSONObject =
				ContentLayoutTestUtil.addItemToLayout(
					"{}", LayoutDataItemTypeConstants.TYPE_FORM, _layout,
					_layoutStructureProvider, _segmentsExperienceId);

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			String formItemId = addItemJSONObject.getString("addedItemId");

			JSONObject updateFormJSONObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"classNameId", classNameId
					).put(
						"classTypeId", "0"
					).toString(),
					formItemId, _layout),
				new MockLiferayPortletActionResponse());

			_assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
				updateFormJSONObject, _INFO_FIELDS.length + 1, StringPool.BLANK,
				StringPool.BLANK, 0);

			_assertFormStyledLayoutStructureItem(
				classNameId, _INFO_FIELDS.length + 1, formItemId, _INFO_FIELDS,
				true, true);
		}
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandMoreThanOneFragmentEntryNotAvailable()
		throws Exception {

		TreeSet<String> expectedFieldTypeLabels = new TreeSet<>();

		expectedFieldTypeLabels.add(RandomTestUtil.randomString());
		expectedFieldTypeLabels.add(RandomTestUtil.randomString());

		InfoFieldType infoFieldType1 = new InfoFieldType() {

			@Override
			public String getLabel(Locale locale) {
				return expectedFieldTypeLabels.first();
			}

			@Override
			public String getName() {
				return RandomTestUtil.randomString();
			}

		};

		InfoFieldType infoFieldType2 = new InfoFieldType() {

			@Override
			public String getLabel(Locale locale) {
				return expectedFieldTypeLabels.last();
			}

			@Override
			public String getName() {
				return RandomTestUtil.randomString();
			}

		};

		InfoField<?>[] allInfoFields = ArrayUtil.append(
			_INFO_FIELDS,
			new InfoField<?>[] {
				_getInfoField(infoFieldType1), _getInfoField(infoFieldType2)
			});

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					_BUNDLE_SYMBOLIC_NAME, _COMPONENT_CLASS_NAME, true);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(allInfoFields)
						).build(),
						_editPageInfoItemCapability)) {

			JSONObject addItemJSONObject =
				ContentLayoutTestUtil.addItemToLayout(
					"{}", LayoutDataItemTypeConstants.TYPE_FORM, _layout,
					_layoutStructureProvider, _segmentsExperienceId);

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			String formItemId = addItemJSONObject.getString("addedItemId");

			JSONObject updateFormJSONObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"classNameId", classNameId
					).put(
						"classTypeId", "0"
					).toString(),
					formItemId, _layout),
				new MockLiferayPortletActionResponse());

			_assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
				updateFormJSONObject, _INFO_FIELDS.length + 1, StringPool.BLANK,
				_language.format(
					_portal.getSiteDefaultLocale(_group),
					"some-fragments-are-missing.-x-and-x-fields-cannot-have-" +
						"an-associated-fragment-or-cannot-be-available-in-" +
							"master",
					ArrayUtil.sortedUnique(
						new String[] {
							expectedFieldTypeLabels.first(),
							expectedFieldTypeLabels.last()
						})),
				0);

			_assertFormStyledLayoutStructureItem(
				classNameId, _INFO_FIELDS.length + 1, formItemId, _INFO_FIELDS,
				true, true);
		}
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandNoMappingChange()
		throws Exception {

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					_BUNDLE_SYMBOLIC_NAME, _COMPONENT_CLASS_NAME, true);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(_INFO_FIELDS)
						).build(),
						_editPageInfoItemCapability)) {

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			JSONObject jsonObject = ContentLayoutTestUtil.addFormToLayout(
				false, String.valueOf(classNameId), "0", _layout,
				_layoutStructureProvider, _segmentsExperienceId, _INFO_FIELDS);

			String formItemId = jsonObject.getString("addedItemId");

			_assertFormStyledLayoutStructureItem(
				classNameId, _INFO_FIELDS.length, formItemId, _INFO_FIELDS,
				false, false);

			JSONObject updateFormJSONObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"successMessage",
						JSONUtil.put(
							"message",
							JSONUtil.put(
								LocaleUtil.toLanguageId(
									LocaleUtil.getMostRelevantLocale()),
								RandomTestUtil.randomString()))
					).toString(),
					formItemId, _layout),
				new MockLiferayPortletActionResponse());

			_assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
				updateFormJSONObject, 0, StringPool.BLANK, StringPool.BLANK, 0);

			_assertFormStyledLayoutStructureItem(
				classNameId, _INFO_FIELDS.length, formItemId, _INFO_FIELDS,
				false, false);
		}
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandResetMapping()
		throws Exception {

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					_BUNDLE_SYMBOLIC_NAME, _COMPONENT_CLASS_NAME, true);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(_INFO_FIELDS)
						).build(),
						_editPageInfoItemCapability)) {

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			JSONObject jsonObject = ContentLayoutTestUtil.addFormToLayout(
				false, String.valueOf(classNameId), "0", _layout,
				_layoutStructureProvider, _segmentsExperienceId, _INFO_FIELDS);

			String formItemId = jsonObject.getString("addedItemId");

			_assertFormStyledLayoutStructureItem(
				classNameId, _INFO_FIELDS.length, formItemId, _INFO_FIELDS,
				false, false);

			JSONObject updateFormJSONObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"classNameId", "0"
					).put(
						"classTypeId", "0"
					).toString(),
					formItemId, _layout),
				new MockLiferayPortletActionResponse());

			_assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
				updateFormJSONObject, 0, StringPool.BLANK, StringPool.BLANK,
				_INFO_FIELDS.length);

			_assertFormStyledLayoutStructureItem(
				0, 0, formItemId, new InfoField<?>[0], false, false);
		}
	}

	private static <T extends InfoFieldType> InfoField<T> _getInfoField(
		T infoFieldTypeInstance) {

		return InfoField.builder(
		).infoFieldType(
			infoFieldTypeInstance
		).namespace(
			RandomTestUtil.randomString()
		).name(
			RandomTestUtil.randomString()
		).editable(
			true
		).labelInfoLocalizedValue(
			InfoLocalizedValue.singleValue(RandomTestUtil.randomString())
		).localizable(
			true
		).build();
	}

	private void _assertFormStyledLayoutStructureItem(
			long expectedClassNameId, int expectedChildrenSize,
			String formItemId, InfoField<?>[] infoFields,
			boolean assertRendererKey, boolean submitButton)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(formItemId);

		Assert.assertEquals(
			expectedClassNameId,
			formStyledLayoutStructureItem.getClassNameId());
		Assert.assertEquals(0, formStyledLayoutStructureItem.getClassTypeId());

		List<String> childrenItemIds =
			formStyledLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), expectedChildrenSize,
			childrenItemIds.size());

		for (int i = 0; i < infoFields.length; i++) {
			InfoField<?> infoField = infoFields[i];

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)
						layoutStructure.getLayoutStructureItem(
							childrenItemIds.get(i));

			_assertFragmentEntry(
				infoField.getUniqueId(), _getExpectedRendererKey(infoField),
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId(),
				assertRendererKey);
		}

		if (!submitButton) {
			return;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(
					childrenItemIds.get(infoFields.length));

		_assertFragmentEntry(
			StringPool.BLANK, "INPUTS-submit-button",
			fragmentStyledLayoutStructureItem.getFragmentEntryLinkId(),
			assertRendererKey);
	}

	private void _assertFragmentEntry(
			String expectedInputFieldId, String expectedRendererKey,
			long fragmentEntryLinkId, boolean assertRendererKey)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				fragmentEntryLinkId);

		String inputFieldId = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputFieldId", "string", "", false, "text"),
				LocaleUtil.getMostRelevantLocale()));

		Assert.assertEquals(expectedInputFieldId, inputFieldId);

		if (!assertRendererKey) {
			return;
		}

		Assert.assertEquals(
			expectedRendererKey, fragmentEntryLink.getRendererKey());
	}

	private void _assertUpdateFormStyledLayoutStructureItemConfigJSONObject(
		JSONObject jsonObject, int expectedAddedFragmentEntryLinks,
		String expectedError, String expectedErrorMessage,
		int expectedRemovedLayoutStructureItems) {

		Assert.assertEquals(expectedError, jsonObject.getString("error"));

		if (Validator.isNotNull(expectedError)) {
			return;
		}

		Assert.assertTrue(jsonObject.has("layoutData"));

		JSONObject addedFragmentEntryLinksJSONObject = jsonObject.getJSONObject(
			"addedFragmentEntryLinks");

		Assert.assertEquals(
			expectedAddedFragmentEntryLinks,
			addedFragmentEntryLinksJSONObject.length());

		JSONArray removedLayoutStructureItemsJSONArray =
			jsonObject.getJSONArray("removedFragmentEntryLinkIds");

		Assert.assertEquals(
			expectedRemovedLayoutStructureItems,
			removedLayoutStructureItemsJSONArray.length());

		Assert.assertEquals(
			expectedErrorMessage, jsonObject.getString("errorMessage"));
	}

	private String _getExpectedRendererKey(InfoField infoField) {
		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		if (infoFieldType instanceof BooleanInfoFieldType) {
			return "INPUTS-checkbox";
		}

		if (infoFieldType instanceof DateInfoFieldType) {
			return "INPUTS-date-input";
		}

		if (infoFieldType instanceof FileInfoFieldType) {
			return "INPUTS-file-upload";
		}

		if (infoFieldType instanceof LongTextInfoFieldType) {
			return "INPUTS-textarea";
		}

		if (infoFieldType instanceof MultiselectInfoFieldType) {
			return "INPUTS-multiselect-list";
		}

		if (infoFieldType instanceof NumberInfoFieldType) {
			return "INPUTS-numeric-input";
		}

		if (infoFieldType instanceof RelationshipInfoFieldType ||
			infoFieldType instanceof SelectInfoFieldType) {

			return "INPUTS-select-from-list";
		}

		if (infoFieldType instanceof TextInfoFieldType) {
			return "INPUTS-text-input";
		}

		return null;
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String itemConfig, String formItemId, Layout layout)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_company, _group, layout);

		mockLiferayPortletActionRequest.addParameter("itemConfig", itemConfig);
		mockLiferayPortletActionRequest.addParameter("itemId", formItemId);
		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId", String.valueOf(_segmentsExperienceId));

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			_serviceContext.getRequest());

		return mockLiferayPortletActionRequest;
	}

	private void _prepareServiceContext() throws Exception {
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_company, _group, _layout);

		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setRequestURI(
			StringPool.SLASH + RandomTestUtil.randomString());

		_serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.fragment.collection.contributor.inputs";

	private static final String _COMPONENT_CLASS_NAME =
		"com.liferay.fragment.collection.contributor.inputs." +
			"InputsFragmentCollectionContributor";

	private static final InfoField<?>[] _INFO_FIELDS = new InfoField<?>[] {
		_getInfoField(BooleanInfoFieldType.INSTANCE),
		_getInfoField(DateInfoFieldType.INSTANCE),
		_getInfoField(FileInfoFieldType.INSTANCE),
		_getInfoField(NumberInfoFieldType.INSTANCE),
		_getInfoField(RelationshipInfoFieldType.INSTANCE),
		_getInfoField(SelectInfoFieldType.INSTANCE),
		_getInfoField(TextInfoFieldType.INSTANCE)
	};

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "info.item.capability.key=" + EditPageInfoItemCapability.KEY
	)
	private InfoItemCapability _editPageInfoItemCapability;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_form_item_config"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	private ServiceContext _serviceContext;

	private class ComponentEnablerTemporarySwapper implements AutoCloseable {

		public ComponentEnablerTemporarySwapper(
				String bundleSymbolicName, String componentClassName,
				boolean enabled)
			throws Exception {

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			ComponentDescriptionDTO componentDescriptionDTO = null;

			for (Bundle bundle : bundleContext.getBundles()) {
				String symbolicName = bundle.getSymbolicName();

				if (symbolicName.startsWith(bundleSymbolicName)) {
					componentDescriptionDTO =
						_serviceComponentRuntime.getComponentDescriptionDTO(
							bundle, componentClassName);

					break;
				}
			}

			Assert.assertNotNull(componentDescriptionDTO);

			_componentDescriptionDTO = componentDescriptionDTO;

			_componentEnabled = _serviceComponentRuntime.isComponentEnabled(
				_componentDescriptionDTO);

			if (enabled) {
				Promise<?> promise = _serviceComponentRuntime.enableComponent(
					_componentDescriptionDTO);

				promise.getValue();
			}
			else {
				Promise<?> promise = _serviceComponentRuntime.disableComponent(
					_componentDescriptionDTO);

				promise.getValue();
			}
		}

		@Override
		public void close() throws Exception {
			if (_componentDescriptionDTO == null) {
				return;
			}

			if (_componentEnabled) {
				Promise<?> promise = _serviceComponentRuntime.enableComponent(
					_componentDescriptionDTO);

				promise.getValue();
			}
			else {
				Promise<?> promise = _serviceComponentRuntime.disableComponent(
					_componentDescriptionDTO);

				promise.getValue();
			}
		}

		private final ComponentDescriptionDTO _componentDescriptionDTO;
		private final boolean _componentEnabled;

	}

}