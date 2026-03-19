/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kiana Suetani
 */
@RunWith(Arquillian.class)
public class GetLayoutContentChangesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_deletedFragmentEntryLink = _addFragmentEntryLinkToLayout(_layout);
		_updatedFragmentEntryLink = _addFragmentEntryLinkToLayout(_layout);
	}

	@Test
	public void testGetLayoutContentChanges() throws Exception {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_addFragmentEntryLinkToLayout(_layout);

			_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				_deletedFragmentEntryLink);

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				_updatedFragmentEntryLink);
		}

		_waitForReindex();

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(),
			_classNameLocalService.getClassNameId(Layout.class),
			_layout.getPlid());

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(ctEntry.getCtEntryId()),
			mockLiferayResourceResponse);

		JSONObject jsonObject = _getJSONObject(mockLiferayResourceResponse);

		long[] fragmentEntryLinkCTEntryIds = TransformUtil.transformToLongArray(
			_ctEntryLocalService.getCTEntries(
				_ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(FragmentEntryLink.class)),
			CTEntry::getCtEntryId);

		Assert.assertEquals(
			fragmentEntryLinkCTEntryIds.length, jsonObject.getInt("total"));

		JSONArray jsonArray = jsonObject.getJSONArray("layoutContentChanges");

		Assert.assertEquals(
			fragmentEntryLinkCTEntryIds.length, jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonArrayJSONObject = jsonArray.getJSONObject(i);

			Assert.assertTrue(
				ArrayUtil.contains(
					fragmentEntryLinkCTEntryIds,
					jsonArrayJSONObject.getLong("ctEntryId")));
		}
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayout(Layout layout)
		throws Exception {

		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			"{}", layout,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));
	}

	private JSONObject _getJSONObject(
			MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			long ctEntryId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_ctCollection.getCompanyId()));

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setParameter(
			"ctEntryId", String.valueOf(ctEntryId));

		return mockLiferayResourceRequest;
	}

	private void _waitForReindex() throws Exception {
		Destination destination = MessageBusUtil.getDestination(
			CTDestinationNames.CT_ENTRY_REINDEX);

		DestinationStatistics destinationStatistics =
			destination.getDestinationStatistics();

		int i = 0;

		while ((destinationStatistics.getActiveThreadCount() > 0) ||
			   (destinationStatistics.getPendingMessageCount() > 0)) {

			if (i++ > 60) {
				break;
			}

			Thread.sleep(500);

			destinationStatistics = destination.getDestinationStatistics();
		}
	}

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private FragmentEntryLink _deletedFragmentEntryLink;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private Group _group;
	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/change_tracking/get_layout_content_changes"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private FragmentEntryLink _updatedFragmentEntryLink;

}