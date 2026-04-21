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
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.diff.DiffHtml;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.ByteArrayOutputStream;

import java.util.Locale;

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

	@FeatureFlag("LPD-75671")
	@Test
	public void testServeResource() throws Exception {
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

		Locale locale = LocaleUtil.SPAIN;

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayResourceRequest(ctEntry.getCtEntryId(), locale);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

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

			long ctEntryId = jsonArrayJSONObject.getLong("ctEntryId");

			Assert.assertTrue(
				ArrayUtil.contains(fragmentEntryLinkCTEntryIds, ctEntryId));

			Assert.assertEquals(
				HtmlUtil.stripHtml(
					_getPreview(
						ctEntryId, mockLiferayResourceRequest,
						mockLiferayResourceResponse)),
				HtmlUtil.stripHtml(jsonArrayJSONObject.getString("preview")));

			Assert.assertEquals(
				_getTitle(ctEntryId, locale),
				jsonArrayJSONObject.getString("title"));
		}
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayout(Layout layout)
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"element-text",
					JSONUtil.put(
						"config", "{}"
					).put(
						"defaultValue", RandomTestUtil.randomString()
					))
			).put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put("headingLevel", "h1")
			).toString(),
			fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
			fragmentEntry.getExternalReferenceCode(), null,
			fragmentEntry.getHtml(), fragmentEntry.getJs(), layout,
			fragmentEntry.getFragmentEntryKey(),
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()),
			fragmentEntry.getType());
	}

	private String _getFragmentEntryLinkPreview(
		FragmentEntryLink fragmentEntryLink,
		MockLiferayResourceRequest mockLiferayResourceRequest,
		MockLiferayResourceResponse mockLiferayResourceResponse) {

		if (fragmentEntryLink == null) {
			return StringPool.BLANK;
		}

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setLocale(
			LocaleUtil.getMostRelevantLocale());

		return _fragmentRendererController.render(
			defaultFragmentRendererContext,
			_portal.getHttpServletRequest(mockLiferayResourceRequest),
			_portal.getHttpServletResponse(mockLiferayResourceResponse));
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
			long ctEntryId, Locale locale)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_ctCollection.getCompanyId()));

		themeDisplay.setLocale(locale);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setParameter(
			"ctEntryId", String.valueOf(ctEntryId));

		return mockLiferayResourceRequest;
	}

	private String _getPreview(
			long ctEntryId,
			MockLiferayResourceRequest mockLiferayResourceRequest,
			MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		CTEntry ctEntry = _ctEntryLocalService.getCTEntry(ctEntryId);

		FragmentEntryLink productionFragmentEntryLink = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			productionFragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					ctEntry.getModelClassPK());
		}

		FragmentEntryLink ctFragmentEntryLink = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			ctFragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					ctEntry.getModelClassPK());
		}

		return _diffHtml.diff(
			new UnsyncStringReader(
				_getFragmentEntryLinkPreview(
					productionFragmentEntryLink, mockLiferayResourceRequest,
					mockLiferayResourceResponse)),
			new UnsyncStringReader(
				_getFragmentEntryLinkPreview(
					ctFragmentEntryLink, mockLiferayResourceRequest,
					mockLiferayResourceResponse)));
	}

	private String _getTitle(long ctEntryId, Locale locale) throws Exception {
		CTEntry ctEntry = _ctEntryLocalService.getCTEntry(ctEntryId);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				ctEntry.getModelClassPK());

		if (fragmentEntryLink == null) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						_ctCollection.getCtCollectionId())) {

				fragmentEntryLink =
					_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
						ctEntry.getModelClassPK());
			}
		}

		if (fragmentEntryLink == null) {
			return null;
		}

		CTDisplayRenderer<FragmentEntryLink> ctDisplayRenderer =
			_ctDisplayRendererRegistry.getCTDisplayRenderer(
				_classNameLocalService.getClassNameId(FragmentEntryLink.class));

		return ctDisplayRenderer.getTitle(locale, fragmentEntryLink);
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
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	private FragmentEntryLink _deletedFragmentEntryLink;

	@Inject
	private DiffHtml _diffHtml;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentRendererController _fragmentRendererController;

	private Group _group;
	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/change_tracking/get_layout_content_changes"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private FragmentEntryLink _updatedFragmentEntryLink;

}