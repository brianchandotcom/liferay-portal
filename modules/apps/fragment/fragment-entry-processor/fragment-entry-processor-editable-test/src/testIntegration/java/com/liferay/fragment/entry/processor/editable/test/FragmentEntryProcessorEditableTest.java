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

package com.liferay.fragment.entry.processor.editable.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.editable.test.constants.FragmentEntryLinkPortletKeys;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.portlet.Portlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class FragmentEntryProcessorEditableTest {

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

		_layout = _addLayout(_group.getGroupId());

		_processedHTML = _getProcessedHTML("processed_fragment_entry.html");

		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		_originalThemeDisplayDefaultLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(
			_originalThemeDisplayDefaultLocale);
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testCanAddOneNoninstanceableWidget() throws Exception {
		_addFragmentEntry(
			"fragment_entry_with_noninstanceable_widget_tag.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testCannotAddMoreThanOneNoninstanceableWidget()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_duplicate_noninstanceable_widget_tag.html");
	}

	@Test
	public void testFragmentEntryLinkPortletPreferences() throws Exception {
		FragmentEntry fragmentEntry = _addFragmentEntry(
			"fragment_entry_with_instanceable_widget_tag.html");

		ServiceContext serviceContext = new MockServiceContext(
			_layout, _getThemeDisplay());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0,
				_portal.getClassNameId(Layout.class), _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, null, serviceContext);

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid());

		List<PortletPreferences> filteredPortletPreferencesList =
			ListUtil.filter(
				portletPreferencesList,
				portletPreferences -> {
					String portletId = portletPreferences.getPortletId();

					return portletId.startsWith(
						FragmentEntryLinkPortletKeys.
							FRAGMENT_ENTRY_LINK_INSTANCEABLE_TEST_PORTLET);
				});

		Assert.assertEquals(
			filteredPortletPreferencesList.toString(), 1,
			filteredPortletPreferencesList.size());

		PortletPreferences portletPreferences = portletPreferencesList.get(0);

		String instanceId = PortletIdCodec.decodeInstanceId(
			portletPreferences.getPortletId());

		Assert.assertEquals(
			fragmentEntryLink.getNamespace() + "widget", instanceId);
	}

	@Test
	public void testFragmentEntryProcessorEditable() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString("fragment_entry_link_editable_values.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, _getFragmentEntryProcessorContext()));
	}

	@Test
	public void testFragmentEntryProcessorEditableMappedAssetField()
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0,
				_portal.getClassNameId(Layout.class), TestPropsValues.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, null,
				ServiceContextTestUtil.getServiceContext());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String editableValues = _getJsonFileAsString(
			"fragment_entry_link_mapped_asset_field.json");

		editableValues = StringUtil.replace(
			editableValues, "CLASS_NAME_ID",
			String.valueOf(_portal.getClassNameId(JournalArticle.class)));

		editableValues = StringUtil.replace(
			editableValues, "CLASS_PK",
			String.valueOf(journalArticle.getResourcePrimKey()));

		_fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(), editableValues);

		int count =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesCount(
					_portal.getClassNameId(JournalArticle.class),
					journalArticle.getResourcePrimKey());

		Assert.assertEquals(1, count);

		_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink);

		count =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesCount(
					_portal.getClassNameId(JournalArticle.class),
					journalArticle.getResourcePrimKey());

		Assert.assertEquals(0, count);
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithDuplicateIds()
		throws Exception {

		_addFragmentEntry("fragment_entry_with_duplicate_editable_ids.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithEmptyString()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_empty_string.json"));

		Assert.assertEquals(
			_getProcessedHTML("processed_fragment_entry_empty_string.html"),
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.US)));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttribute()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttributeInImageTag()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute_in_image_" +
				"tag.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttributeInLinkTag()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute_in_link_tag." +
				"html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithInvalidTypeAttributeInTextTag()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_invalid_editable_type_attribute_in_text_tag." +
				"html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithMatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_matching_language.json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.US)));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithMissingAttributes()
		throws Exception {

		_addFragmentEntry(
			"fragment_entry_with_missing_editable_attributes.html");
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testFragmentEntryProcessorEditableWithNestedEditablesInHTML()
		throws Exception {

		_addFragmentEntry("fragment_entry_with_nested_editable_in_html.html");
	}

	@Test
	public void testFragmentEntryProcessorEditableWithUnmatchedLanguage()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		FragmentEntry fragmentEntry = _addFragmentEntry("fragment_entry.html");

		fragmentEntryLink.setHtml(fragmentEntry.getHtml());

		fragmentEntryLink.setEditableValues(
			_getJsonFileAsString(
				"fragment_entry_link_editable_values_unmatching_language." +
					"json"));

		Assert.assertEquals(
			_processedHTML,
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink,
				_getFragmentEntryProcessorContext(LocaleUtil.CHINESE)));
	}

	private FragmentEntry _addFragmentEntry(String htmlFile)
		throws IOException, PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				serviceContext);

		return _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			"fragment-entry", "Fragment Entry", null,
			_getFileAsString(htmlFile), null, null, 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	private Layout _addLayout(long groupId) throws PortalException {
		String name = RandomTestUtil.randomString();

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null,
			RandomTestUtil.randomString(), LayoutConstants.TYPE_PORTLET, false,
			friendlyURL, ServiceContextTestUtil.getServiceContext());
	}

	private String _getFileAsString(String fileName) throws IOException {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/editable/test/dependencies/" +
				fileName);
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext() {
		return _getFragmentEntryProcessorContext(
			LocaleUtil.getMostRelevantLocale());
	}

	private FragmentEntryProcessorContext _getFragmentEntryProcessorContext(
		Locale locale) {

		return new DefaultFragmentEntryProcessorContext(
			null, null, FragmentEntryLinkConstants.EDIT, locale);
	}

	private String _getJsonFileAsString(String jsonFileName)
		throws IOException, JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_getFileAsString(jsonFileName));

		return jsonObject.toString();
	}

	private String _getProcessedHTML(String fileName) throws IOException {
		Document document = Jsoup.parseBodyFragment(_getFileAsString(fileName));

		document.outputSettings(
			new Document.OutputSettings() {
				{
					prettyPrint(false);
				}
			});

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		Theme theme = _themeLocalService.getTheme(
			_company.getCompanyId(), layoutSet.getThemeId());

		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setLookAndFeel(theme, null);

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "javax.portlet.name=" + FragmentEntryLinkPortletKeys.FRAGMENT_ENTRY_LINK_INSTANCEABLE_TEST_PORTLET
	)
	private final Portlet _instanceablePortlet = null;

	private Layout _layout;

	@Inject
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(
		filter = "javax.portlet.name=" + FragmentEntryLinkPortletKeys.FRAGMENT_ENTRY_LINK_NONINSTANCEABLE_TEST_PORTLET
	)
	private final Portlet _nonInstanceablePortlet = null;

	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayDefaultLocale;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private String _processedHTML;

	@Inject
	private ThemeLocalService _themeLocalService;

	private static class MockServiceContext extends ServiceContext {

		public MockServiceContext(Layout layout, ThemeDisplay themeDisplay) {
			_layout = layout;
			_themeDisplay = themeDisplay;
		}

		@Override
		public HttpServletRequest getRequest() {
			HttpServletRequest httpServletRequest =
				new MockHttpServletRequest();

			httpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			return httpServletRequest;
		}

		@Override
		public HttpServletResponse getResponse() {
			return new MockHttpServletResponse();
		}

		private final Layout _layout;
		private final ThemeDisplay _themeDisplay;

	}

}