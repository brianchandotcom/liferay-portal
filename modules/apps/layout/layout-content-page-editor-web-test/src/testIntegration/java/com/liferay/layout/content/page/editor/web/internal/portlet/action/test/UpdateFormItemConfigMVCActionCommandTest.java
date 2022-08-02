/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.MockInfoServiceRegistrationHolder;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

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

		_prepareServiceContext();
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpdateFormItemConfigMVCActionCommandMappingFormFFDisabled()
		throws Exception {

		InfoField<?>[] infoFields = _getInfoFields();

		try (ComponentEnablerTemporarySwapper componentEnablerTemporarySwapper =
				new ComponentEnablerTemporarySwapper(
					"com.liferay.fragment.collection.contributor.inputs",
					"com.liferay.fragment.collection.contributor.inputs." +
						"InputsFragmentCollectionContributor",
					true);
			MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoFields)
						).build(),
						_editPageInfoItemCapability);
			PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper("feature.flag.LPS-157738", false)) {

			long segmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid());

			JSONObject jsonObject = ContentLayoutTestUtil.addItemToLayout(
				_layout, "{}", LayoutDataItemTypeConstants.TYPE_FORM,
				segmentsExperienceId);

			long classNameId = _portal.getClassNameId(
				MockObject.class.getName());

			String formItemId = jsonObject.getString("addedItemId");

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				_getMockLiferayPortletActionRequest(
					JSONUtil.put(
						"classNameId", classNameId
					).put(
						"classTypeId", "0"
					).toString(),
					formItemId, _layout, segmentsExperienceId);

			ReflectionTestUtil.invoke(
				_mvcActionCommand, "_updateFormStyledLayoutStructureItemConfig",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

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
				classNameId, formStyledLayoutStructureItem.getClassNameId());
			Assert.assertEquals(
				0, formStyledLayoutStructureItem.getClassTypeId());

			List<String> childrenItemIds =
				formStyledLayoutStructureItem.getChildrenItemIds();

			Assert.assertEquals(
				childrenItemIds.toString(), 0, childrenItemIds.size());
		}
	}

	private <T extends InfoFieldType> InfoField<T> _getInfoField(
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

	private InfoField<?>[] _getInfoFields() {
		return new InfoField<?>[] {
			_getInfoField(BooleanInfoFieldType.INSTANCE),
			_getInfoField(DateInfoFieldType.INSTANCE),
			_getInfoField(FileInfoFieldType.INSTANCE),
			_getInfoField(NumberInfoFieldType.INSTANCE),
			_getInfoField(RelationshipInfoFieldType.INSTANCE),
			_getInfoField(SelectInfoFieldType.INSTANCE),
			_getInfoField(TextInfoFieldType.INSTANCE)
		};
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String itemConfig, String formItemId, Layout layout,
			long segmentsExperienceId)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_company, _group, layout);

		mockLiferayPortletActionRequest.addParameter("itemConfig", itemConfig);
		mockLiferayPortletActionRequest.addParameter("itemId", formItemId);
		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId", String.valueOf(segmentsExperienceId));

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
		mockHttpServletRequest.setRequestURI(
			StringPool.SLASH + RandomTestUtil.randomString());

		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_company, _group, _layout);

		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		_serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private EditPageInfoItemCapability _editPageInfoItemCapability;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_form_item_config"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	private ServiceContext _serviceContext;

	private class ComponentEnablerTemporarySwapper implements AutoCloseable {

		public ComponentEnablerTemporarySwapper(
			String bundleSymbolicName, String componentClassName,
			boolean enabled) {

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
				_serviceComponentRuntime.enableComponent(
					_componentDescriptionDTO);
			}
			else {
				_serviceComponentRuntime.disableComponent(
					_componentDescriptionDTO);
			}
		}

		@Override
		public void close() throws Exception {
			if (_componentDescriptionDTO == null) {
				return;
			}

			if (_componentEnabled) {
				_serviceComponentRuntime.enableComponent(
					_componentDescriptionDTO);
			}
			else {
				_serviceComponentRuntime.disableComponent(
					_componentDescriptionDTO);
			}
		}

		private final ComponentDescriptionDTO _componentDescriptionDTO;
		private final boolean _componentEnabled;

	}

}