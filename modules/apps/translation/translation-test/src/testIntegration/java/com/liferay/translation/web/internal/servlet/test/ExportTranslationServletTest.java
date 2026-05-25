/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.translation.test.util.TranslationTestUtil;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuel Rives
 */
@RunWith(Arquillian.class)
public class ExportTranslationServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		if (_ctCollection != null) {
			_ctPreferencesService.checkoutCTCollection(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			_ctCollectionLocalService.deleteCTCollection(_ctCollection);
		}
	}

	@Test
	@TestInfo("LPD-88077")
	public void testDoGetExportsArticleCreatedInsidePublication()
		throws Exception {

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		JournalArticle journalArticle;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			journalArticle = TranslationTestUtil.getJournalArticle(
				_group, _ddmFormDeserializer);
		}

		_ctPreferencesService.checkoutCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_ctCollection.getCtCollectionId());

		String xliff = _exportTranslation(
			JournalArticle.class, journalArticle.getResourcePrimKey(),
			_MIMETYPE_XLIFF_1_2);

		Assert.assertFalse(xliff.isEmpty());
	}

	@Test
	@TestInfo("LPD-92001")
	public void testDoGetExportsIteratedFragmentEditable() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			null, _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(), null,
			RandomTestUtil.randomString(), null, _read("index.html"), null,
			false, _read("configuration.json"), null, 0, false, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		long segmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					"com.liferay.fragment.entry.processor.editable." +
						"EditableFragmentEntryProcessor",
					JSONUtil.put(
						"title-1", JSONUtil.put("defaultValue", "Title One")
					).put(
						"title-2", JSONUtil.put("defaultValue", "Title Two")
					)
				).put(
					"com.liferay.fragment.entry.processor.freemarker." +
						"FreeMarkerFragmentEntryProcessor",
					JSONUtil.put("itemCount", "2")
				).toString(),
				fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
				fragmentEntry.getExternalReferenceCode(), null,
				fragmentEntry.getHtml(), fragmentEntry.getJs(), draftLayout,
				null, FragmentConstants.TYPE_COMPONENT, null, 0,
				segmentsExperienceId);

		String xliff = _exportTranslation(
			Layout.class, layout.getPlid(), _MIMETYPE_XLIFF_2_0);

		String fragmentEntryLinkPrefix =
			"FragmentEntryLink_" + fragmentEntryLink.getFragmentEntryLinkId() +
				":";

		Assert.assertTrue(xliff.contains(fragmentEntryLinkPrefix + "title-1"));
		Assert.assertTrue(xliff.contains(fragmentEntryLinkPrefix + "title-2"));

		Assert.assertTrue(xliff.contains("Title One"));
		Assert.assertTrue(xliff.contains("Title Two"));
	}

	private String _exportTranslation(
			Class<?> clazz, long classPK, String xliffMimeType)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/translation/export_translation");
		mockHttpServletRequest.setAttribute(
			WebKeys.USER,
			_userLocalService.getUser(TestPropsValues.getUserId()));
		mockHttpServletRequest.setMethod("GET");
		mockHttpServletRequest.setParameter(
			"classNameId", String.valueOf(_portal.getClassNameId(clazz)));
		mockHttpServletRequest.setParameter("classPK", String.valueOf(classPK));
		mockHttpServletRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockHttpServletRequest.setParameter(
			"sourceLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));
		mockHttpServletRequest.setParameter(
			"targetLanguageIds", LocaleUtil.toLanguageId(LocaleUtil.SPAIN));
		mockHttpServletRequest.setParameter("xliffMimeType", xliffMimeType);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		ReflectionTestUtil.invoke(
			_servlet, "doGet",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class
			},
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		return _readXLIFFEntry(mockHttpServletResponse.getContentAsByteArray());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getResourceAsStream("dependencies/" + fileName));
	}

	private String _readXLIFFEntry(byte[] zipBytes) throws Exception {
		try (ZipInputStream zipInputStream = new ZipInputStream(
				new ByteArrayInputStream(zipBytes))) {

			ZipEntry zipEntry;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String name = zipEntry.getName();

				if (name.endsWith(".xlf") || name.endsWith(".xliff")) {
					return StringUtil.read(zipInputStream);
				}
			}

			throw new AssertionError("Export ZIP contains no XLIFF entry");
		}
	}

	private static final String _MIMETYPE_XLIFF_1_2 = "application/x-xliff+xml";

	private static final String _MIMETYPE_XLIFF_2_0 = "application/xliff+xml";

	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTPreferencesService _ctPreferencesService;

	@Inject(filter = "ddm.form.deserializer.type=json")
	private DDMFormDeserializer _ddmFormDeserializer;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "osgi.http.whiteboard.servlet.pattern=/translation/export_translation"
	)
	private Servlet _servlet;

	@Inject
	private UserLocalService _userLocalService;

}