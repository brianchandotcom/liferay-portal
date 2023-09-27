/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.request.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormInvalidGroupException;
import com.liferay.info.exception.InfoFormInvalidLayoutModeException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.MockInfoServiceRegistrationHolder;
import com.liferay.info.test.util.info.item.creator.MockInfoItemCreator;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class EditInfoItemStrutsActionValidationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PrincipalThreadLocal.setName(_originalName);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testEditInfoItemStrutsAction() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));
			Assert.assertTrue(
				SessionMessages.contains(mockHttpServletRequest, formItemId));
		}
	}

	@Test
	public void testEditInfoItemStrutsActionCaptchaException()
		throws Exception {

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				true,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME, LoggerTestUtil.ERROR)) {

				_editInfoItemStrutsAction.execute(
					mockHttpServletRequest, new MockHttpServletResponse());

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				Assert.assertEquals(
					LoggerTestUtil.ERROR, logEntry.getPriority());

				Assert.assertEquals(
					"CAPTCHA text is null. User null may be trying to " +
						"circumvent the CAPTCHA.",
					logEntry.getMessage());
			}

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						_group.getGroupId(), layout.getPlid());

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

			Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
				layoutStructure.getFragmentLayoutStructureItems();

			for (Map.Entry<Long, LayoutStructureItem>
					fragmentLayoutStructureItem :
						fragmentLayoutStructureItems.entrySet()) {

				String key = String.valueOf(
					fragmentLayoutStructureItem.getKey());

				Assert.assertTrue(
					SessionErrors.contains(mockHttpServletRequest, key));

				InfoFormException infoFormException =
					(InfoFormException)SessionErrors.get(
						mockHttpServletRequest, key);

				Assert.assertTrue(
					infoFormException instanceof
						InfoFormValidationException.InvalidCaptcha);

				Assert.assertFalse(
					SessionMessages.contains(mockHttpServletRequest, key));
			}
		}
	}

	@Test
	public void testEditInfoItemStrutsActionFormRequiredFieldValidation()
		throws Exception {

		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));
			Assert.assertTrue(
				SessionMessages.contains(mockHttpServletRequest, formItemId));

			_markInputFragmentEntryLinkAsRequired(layout);

			mockHttpServletRequest = _getMockHttpServletRequest(
				layout, formItemId);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertTrue(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));

			Assert.assertTrue(
				SessionErrors.get(mockHttpServletRequest, formItemId) instanceof
					InfoFormValidationException.RequiredInfoField);

			InfoFormValidationException.RequiredInfoField requiredInfoField =
				(InfoFormValidationException.RequiredInfoField)
					SessionErrors.get(mockHttpServletRequest, formItemId);

			Assert.assertEquals(
				infoField.getUniqueId(),
				requiredInfoField.getInfoFieldUniqueId());

			Assert.assertEquals(
				requiredInfoField,
				SessionErrors.get(
					mockHttpServletRequest, infoField.getUniqueId()));

			Assert.assertFalse(
				SessionMessages.contains(mockHttpServletRequest, formItemId));
		}
	}

	@Test
	public void testEditInfoItemStrutsActionFromDraftLayout() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			JSONObject jsonObject = ContentLayoutTestUtil.addFormToLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid()),
				infoField);

			String formItemId = jsonObject.getString("addedItemId");

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(
					layout, formItemId, Constants.PREVIEW);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertTrue(
				SessionErrors.get(mockHttpServletRequest, formItemId) instanceof
					InfoFormInvalidLayoutModeException);
		}
	}

	@Test
	public void testEditInfoItemStrutsActionFromStagingGroup()
		throws Exception {

		InfoField<TextInfoFieldType> infoField = _getInfoField();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			_stagingLocalService.enableLocalStaging(
				TestPropsValues.getUserId(), _group, false, false,
				serviceContext);

			Layout layout = LayoutTestUtil.addTypeContentLayout(
				_group.getStagingGroup());

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertTrue(
				SessionErrors.get(mockHttpServletRequest, formItemId) instanceof
					InfoFormInvalidGroupException);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testEditInfoItemStrutsActionInfoFormException()
		throws Exception {

		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			MockInfoItemCreator mockInfoItemCreator =
				mockInfoServiceRegistrationHolder.getMockInfoItemCreator();

			InfoFormException infoFormException = new InfoFormException();

			mockInfoItemCreator.setInfoFormException(infoFormException);

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));
			Assert.assertEquals(
				infoFormException,
				SessionErrors.get(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionMessages.contains(mockHttpServletRequest, formItemId));
		}
	}

	@Test
	public void testEditInfoItemStrutsActionInfoFormValidationException()
		throws Exception {

		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			MockInfoItemCreator mockInfoItemCreator =
				mockInfoServiceRegistrationHolder.getMockInfoItemCreator();

			InfoFormValidationException.RequiredInfoField infoFormException =
				new InfoFormValidationException.RequiredInfoField(
					infoField.getUniqueId());

			mockInfoItemCreator.setInfoFormException(infoFormException);

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertTrue(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));
			Assert.assertEquals(
				infoFormException,
				SessionErrors.get(mockHttpServletRequest, formItemId));
			Assert.assertEquals(
				infoFormException,
				SessionErrors.get(
					mockHttpServletRequest, infoField.getUniqueId()));
			Assert.assertFalse(
				SessionMessages.contains(mockHttpServletRequest, formItemId));
		}
	}

	@Test
	public void testEditInfoItemStrutsActionPreviewLayoutMode()
		throws Exception {

		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(
					layout, formItemId, Constants.PREVIEW);

			_editInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertTrue(
				SessionErrors.get(mockHttpServletRequest, formItemId) instanceof
					InfoFormInvalidLayoutModeException);
		}
	}

	private InfoField<TextInfoFieldType> _getInfoField() {
		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			RandomTestUtil.randomString()
		).name(
			RandomTestUtil.randomString()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.singleValue(RandomTestUtil.randomString())
		).localizable(
			true
		).build();
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, String formItemId)
		throws Exception {

		return _getMockHttpServletRequest(layout, formItemId, Constants.VIEW);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, String formItemId, String layoutMode)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(layout.getCompanyId()), _group,
				layout);

		String layoutActualURL = _portal.getLayoutActualURL(layout);

		mockHttpServletRequest.addHeader(HttpHeaders.REFERER, layoutActualURL);
		mockHttpServletRequest.addParameter("backURL", layoutActualURL);

		mockHttpServletRequest.addParameter(
			"classNameId",
			String.valueOf(_portal.getClassNameId(MockObject.class.getName())));
		mockHttpServletRequest.addParameter("classTypeId", "0");
		mockHttpServletRequest.addParameter("formItemId", formItemId);
		mockHttpServletRequest.addParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		mockHttpServletRequest.addParameter("p_l_mode", layoutMode);
		mockHttpServletRequest.addParameter(
			"plid", String.valueOf(layout.getPlid()));
		mockHttpServletRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid())));
		mockHttpServletRequest.setContentType(
			"multipart/form-data;boundary=" + System.currentTimeMillis());

		return mockHttpServletRequest;
	}

	private void _markInputFragmentEntryLinkAsRequired(Layout layout)
		throws PortalException {

		FragmentEntryLink inputFragmentEntryLink = null;

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
					layout.getGroupId(), layout.getPlid())) {

			if (fragmentEntryLink.getType() == FragmentConstants.TYPE_INPUT) {
				inputFragmentEntryLink = fragmentEntryLink;

				break;
			}
		}

		Assert.assertNotNull(inputFragmentEntryLink);

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			inputFragmentEntryLink.getEditableValues());

		JSONObject freemarkerEntryProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		freemarkerEntryProcessorJSONObject.put("inputRequired", true);

		inputFragmentEntryLink =
			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				inputFragmentEntryLink.getFragmentEntryLinkId(),
				editableValuesJSONObject.toString());

		for (FragmentEntryLinkListener fragmentEntryLinkListener :
				_fragmentEntryLinkListenerRegistry.
					getFragmentEntryLinkListeners()) {

			fragmentEntryLinkListener.onUpdateFragmentEntryLink(
				inputFragmentEntryLink);
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl";

	private static String _originalName;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(filter = "path=/portal/edit_info_item")
	private StrutsAction _editInfoItemStrutsAction;

	@Inject(
		filter = "info.item.capability.key=" + EditPageInfoItemCapability.KEY
	)
	private InfoItemCapability _editPageInfoItemCapability;

	@Inject
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}