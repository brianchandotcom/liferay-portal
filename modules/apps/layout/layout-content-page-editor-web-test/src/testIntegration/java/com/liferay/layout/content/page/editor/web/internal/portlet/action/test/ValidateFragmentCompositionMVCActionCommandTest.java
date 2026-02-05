/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
@Sync
public class ValidateFragmentCompositionMVCActionCommandTest {

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

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = layout.fetchDraftLayout();

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testValidateFragmentCompositionInvalid() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _group.getGroupId(), null,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				_segmentsExperienceId, _draftLayout.getPlid(), StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, 0, null,
				FragmentConstants.TYPE_COMPONENT, _serviceContext);

		JSONObject addItemJSONObject = ContentLayoutTestUtil.addItemToLayout(
			"{}", LayoutDataItemTypeConstants.TYPE_CONTAINER, _draftLayout,
			_layoutStructureProvider, _segmentsExperienceId);

		String containerItemId = addItemJSONObject.getString("addedItemId");

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, _draftLayout, containerItemId, 0,
			_segmentsExperienceId);

		JSONObject jsonObject = _testValidateFragmentComposition(
			containerItemId, _getMockLiferayPortletActionRequest());

		int invalidFragmentsCount = jsonObject.getInt("invalidFragmentsCount");

		Assert.assertTrue(invalidFragmentsCount > 0);
	}

	@Test
	public void testValidateFragmentCompositionValid() throws Exception {
		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, "<div>test</div>", StringPool.BLANK, false,
				StringPool.BLANK, null, 0, false, false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _group.getGroupId(), null,
				fragmentEntry.getExternalReferenceCode(),
				fragmentEntry.getScopeERC(), _segmentsExperienceId,
				_draftLayout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getConfiguration(),
				fragmentEntry.getConfiguration(), StringPool.BLANK,
				StringPool.BLANK, 0, fragmentEntry.getFragmentEntryKey(),
				fragmentEntry.getType(), _serviceContext);

		JSONObject addItemJSONObject = ContentLayoutTestUtil.addItemToLayout(
			"{}", LayoutDataItemTypeConstants.TYPE_CONTAINER, _draftLayout,
			_layoutStructureProvider, _segmentsExperienceId);

		String containerItemId = addItemJSONObject.getString("addedItemId");

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, _draftLayout, containerItemId, 0,
			_segmentsExperienceId);

		JSONObject jsonObject = _testValidateFragmentComposition(
			containerItemId, _getMockLiferayPortletActionRequest());

		int invalidFragmentsCount = jsonObject.getInt("invalidFragmentsCount");

		Assert.assertEquals(0, invalidFragmentsCount);
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_company, _group, _draftLayout);

		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId", String.valueOf(_segmentsExperienceId));

		return mockLiferayPortletActionRequest;
	}

	private JSONObject _testValidateFragmentComposition(
		String itemId,
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest) {

		mockLiferayPortletActionRequest.addParameter("itemId", itemId);
		mockLiferayPortletActionRequest.addParameter(
			"saveInlineContent", Boolean.TRUE.toString());
		mockLiferayPortletActionRequest.addParameter(
			"saveMappingConfiguration", Boolean.TRUE.toString());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_validateFragmentCompositionMVCActionCommand,
			"doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertNotNull(jsonObject);

		return jsonObject;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _draftLayout;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/validate_fragment_composition"
	)
	private MVCActionCommand _validateFragmentCompositionMVCActionCommand;

}