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

package com.liferay.layout.page.template.headless.delivery.dto.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.ColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentField;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldBackgroundImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldHTML;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldText;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInstanceDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.InlineLink;
import com.liferay.headless.delivery.dto.v1_0.InlineValue;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.RowDefinition;
import com.liferay.headless.delivery.dto.v1_0.SectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageDefinitionConverterUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class PageDefinitionConverterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					RandomTestUtil.randomString(), StringPool.BLANK,
					_serviceContext);

		_layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		_fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);
	}

	@Test
	public void testToPageDefinitionFragmentConfig() throws Exception {
		FragmentInstanceDefinition fragmentInstanceDefinition =
			_getFragmentInstanceDefinition(
				_read("fragment_config.json"),
				"editable_values_fragment_config.json", "my-fragment-entry-key",
				RandomTestUtil.randomString(),
				_read("html_fragment_config.ftl"));

		Map<String, Object> fragmentConfigMap =
			fragmentInstanceDefinition.getFragmentConfig();

		Assert.assertEquals("center", fragmentConfigMap.get("textAlign"));
		Assert.assertEquals(4, fragmentConfigMap.get("level"));

		Map<String, String> textColorMap =
			(Map<String, String>)fragmentConfigMap.get("textColor");

		Assert.assertEquals("danger", textColorMap.get("cssClass"));
		Assert.assertEquals("rgb(171, 16, 16)", textColorMap.get("rgbValue"));
	}

	@Test
	public void testToPageDefinitionFragmentFieldBackgroundImage()
		throws Exception {

		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_background_image.json",
			"my-background-image",
			"<div data-lfr-background-image-id=\"my-background-image\"></div>");

		FragmentFieldBackgroundImage fragmentFieldBackgroundImage =
			(FragmentFieldBackgroundImage)fragmentField.getValue();

		_validateFragmentImage(
			fragmentFieldBackgroundImage.getBackgroundImage());
	}

	@Test
	public void testToPageDefinitionFragmentFieldBackgroundImageTitle()
		throws Exception {

		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_background_image_title.json",
			"my-background-image",
			"<div data-lfr-background-image-id=\"my-background-image\"></div>");

		FragmentFieldBackgroundImage fragmentFieldBackgroundImage =
			(FragmentFieldBackgroundImage)fragmentField.getValue();

		_validateFragmentImageWithTitle(
			fragmentFieldBackgroundImage.getBackgroundImage(),
			"My background image title");
	}

	@Test
	public void testToPageDefinitionFragmentFieldHTML() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_html.json", "my-html",
			"<lfr-editable id=\"my-html\" type=\"html\"><h1>Example</h1>" +
				"</lfr-editable>");

		FragmentFieldHTML fragmentFieldHTML =
			(FragmentFieldHTML)fragmentField.getValue();

		_validateFragmentFieldHTML(fragmentFieldHTML);
	}

	@Test
	public void testToPageDefinitionFragmentFieldImage() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_image.json", "my-image",
			"<lfr-editable id=\"my-image\" type=\"image\"><img/>" +
				"</lfr-editable>");

		FragmentFieldImage fragmentFieldImage =
			(FragmentFieldImage)fragmentField.getValue();

		_validateFragmentImage(fragmentFieldImage.getFragmentImage());
	}

	@Test
	public void testToPageDefinitionFragmentFieldImageTitle() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_image_title.json", "my-image",
			"<lfr-editable id=\"my-image\" type=\"image\"><img/>" +
				"</lfr-editable>");

		FragmentFieldImage fragmentFieldImage =
			(FragmentFieldImage)fragmentField.getValue();

		_validateFragmentImageWithTitle(
			fragmentFieldImage.getFragmentImage(), "My image title");
	}

	@Test
	public void testToPageDefinitionFragmentFieldLink() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_link.json", "my-link",
			"<lfr-editable id=\"my-link\" type=\"link\"><a href=\"\" " +
				"id=\"my-link\">Go here</a></lfr-editable>");

		FragmentFieldText fragmentFieldText =
			(FragmentFieldText)fragmentField.getValue();

		_validateFragmentFieldText(fragmentFieldText);
	}

	@Test
	public void testToPageDefinitionFragmentFieldText() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_text.json", "my-text",
			"<lfr-editable id=\"my-text\" type=\"text\">Example" +
				"</lfr-editable>");

		FragmentFieldText fragmentFieldText =
			(FragmentFieldText)fragmentField.getValue();

		_validateFragmentFieldText(fragmentFieldText);
	}

	@Test
	public void testToPageDefinitionRoot() throws Exception {
		_addLayoutPageTemplateStructure(
			"layout_data_root.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		ColorScheme colorScheme = layout.getColorScheme();

		Theme theme = layout.getTheme();

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				layout);

		Settings settings = pageDefinition.getSettings();

		Assert.assertEquals(
			colorScheme.getName(), settings.getColorSchemeName());
		Assert.assertNull(settings.getCss());
		Assert.assertNull(settings.getJavascript());
		Assert.assertNull(settings.getMasterPage());
		Assert.assertEquals(theme.getName(), settings.getThemeName());
		Assert.assertNull(settings.getThemeSettings());

		PageElement pageElement = pageDefinition.getPageElement();

		Assert.assertNull(pageElement.getDefinition());
		Assert.assertNull(pageElement.getPageElements());
		Assert.assertEquals(PageElement.Type.ROOT, pageElement.getType());
	}

	@Test
	public void testToPageDefinitionRow() throws Exception {
		_addLayoutPageTemplateStructure(
			"layout_data_row.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 1, pageElements.length);

		PageElement rowPageElement = pageElements[0];

		Assert.assertEquals(PageElement.Type.ROW, rowPageElement.getType());

		RowDefinition rowDefinition =
			(RowDefinition)rowPageElement.getDefinition();

		Assert.assertFalse(rowDefinition.getGutters());

		Assert.assertEquals(
			Integer.valueOf(2), rowDefinition.getNumberOfColumns());

		PageElement[] columnPageElements = rowPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(columnPageElements), 2, columnPageElements.length);

		Assert.assertEquals(
			PageElement.Type.COLUMN, columnPageElements[0].getType());
		Assert.assertNull(columnPageElements[0].getPageElements());

		Assert.assertEquals(
			PageElement.Type.COLUMN, columnPageElements[1].getType());
		Assert.assertNull(columnPageElements[1].getPageElements());

		ColumnDefinition columnDefinition1 =
			(ColumnDefinition)columnPageElements[0].getDefinition();
		ColumnDefinition columnDefinition2 =
			(ColumnDefinition)columnPageElements[1].getDefinition();

		Assert.assertEquals(Integer.valueOf(5), columnDefinition1.getSize());
		Assert.assertEquals(Integer.valueOf(7), columnDefinition2.getSize());
	}

	@Test
	public void testToPageDefinitionSection() throws Exception {
		_addLayoutPageTemplateStructure(
			"layout_data_section.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 2, pageElements.length);

		PageElement sectionPageElement1 = pageElements[0];

		Assert.assertEquals(
			PageElement.Type.SECTION, sectionPageElement1.getType());

		SectionDefinition sectionDefinition1 =
			(SectionDefinition)sectionPageElement1.getDefinition();

		Assert.assertEquals(
			"primary", sectionDefinition1.getBackgroundColorCssClass());

		FragmentImage fragmentImage1 = sectionDefinition1.getBackgroundImage();

		InlineValue titleInlineValue = (InlineValue)fragmentImage1.getTitle();

		Assert.assertEquals(
			"My example1 background image title", titleInlineValue.getValue());

		InlineValue urlInlineValue1 = (InlineValue)fragmentImage1.getUrl();

		Assert.assertEquals(
			"http://myexample1.com/myexample1.png", urlInlineValue1.getValue());

		com.liferay.headless.delivery.dto.v1_0.Layout sectionLayout =
			sectionDefinition1.getLayout();

		Assert.assertEquals("Fluid", sectionLayout.getContainerTypeAsString());
		Assert.assertEquals(
			Integer.valueOf(8), sectionLayout.getPaddingBottom());
		Assert.assertEquals(
			Integer.valueOf(4), sectionLayout.getPaddingHorizontal());
		Assert.assertEquals(Integer.valueOf(1), sectionLayout.getPaddingTop());

		PageElement sectionPageElement2 = pageElements[1];

		Assert.assertEquals(
			PageElement.Type.SECTION, sectionPageElement2.getType());

		SectionDefinition sectionDefinition2 =
			(SectionDefinition)sectionPageElement2.getDefinition();

		FragmentImage fragmentImage2 = sectionDefinition2.getBackgroundImage();

		Assert.assertNull(fragmentImage2.getTitle());

		InlineValue urlInlineValue2 = (InlineValue)fragmentImage2.getUrl();

		Assert.assertEquals(
			"http://myexample2.com/myexample2.png", urlInlineValue2.getValue());
	}

	private void _addLayoutPageTemplateStructure(
			String fileName, Map<String, String> valuesMap)
		throws Exception {

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			_layoutPageTemplateEntry.getPlid(),
			StringUtil.replace(_read(fileName), "${", "}", valuesMap),
			_serviceContext);
	}

	private FragmentField _getFragmentField(
			String editableValuesFileName, String fragmentFieldId, String html)
		throws Exception {

		String fragmentName = RandomTestUtil.randomString();

		String fragmentEntryKey = "my-fragment-entry-key";

		FragmentInstanceDefinition fragmentInstanceDefinition =
			_getFragmentInstanceDefinition(
				StringPool.BLANK, editableValuesFileName, fragmentEntryKey,
				fragmentName, html);

		Fragment fragment = fragmentInstanceDefinition.getFragment();

		Assert.assertEquals(
			_fragmentCollection.getName(),
			fragment.getFragmentCollectionName());
		Assert.assertEquals(fragmentEntryKey, fragment.getFragmentKey());
		Assert.assertEquals(fragmentName, fragment.getFragmentName());

		FragmentField[] fragmentFields =
			fragmentInstanceDefinition.getFragmentFields();

		Assert.assertEquals(
			Arrays.toString(fragmentFields), 1, fragmentFields.length);

		FragmentField fragmentField = fragmentFields[0];

		Assert.assertEquals(fragmentFieldId, fragmentField.getId());

		return fragmentField;
	}

	private FragmentInstanceDefinition _getFragmentInstanceDefinition(
			String configuration, String editableValuesFileName,
			String fragmentEntryKey, String fragmentName, String html)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				fragmentName, StringPool.BLANK, html, StringPool.BLANK,
				configuration, 0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				StringPool.BLANK, html, StringPool.BLANK, configuration,
				_read(editableValuesFileName), StringPool.BLANK, 0, null,
				_serviceContext);

		_addLayoutPageTemplateStructure(
			"layout_data_fragment.json",
			HashMapBuilder.put(
				"FRAGMENT_ENTRY_LINK_ID",
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
			).build());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 1, pageElements.length);

		PageElement fragmentPageElement = pageElements[0];

		Assert.assertNull(fragmentPageElement.getPageElements());
		Assert.assertEquals(
			PageElement.Type.FRAGMENT, fragmentPageElement.getType());

		return (FragmentInstanceDefinition)fragmentPageElement.getDefinition();
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateFragmentFieldHTML(
		FragmentFieldHTML fragmentFieldHTML) {

		InlineValue inlineValue = (InlineValue)fragmentFieldHTML.getHtml();

		Assert.assertNull(inlineValue.getValue());

		Map<String, String> i18nMap = inlineValue.getValue_i18n();

		Assert.assertEquals("My example", i18nMap.get("en_US"));
		Assert.assertEquals("Mi ejemplo", i18nMap.get("es_ES"));
	}

	private void _validateFragmentFieldText(
		FragmentFieldText fragmentFieldText) {

		FragmentLink fragmentLink = fragmentFieldText.getFragmentLink();

		Assert.assertEquals(
			FragmentLink.Target.BLANK, fragmentLink.getTarget());

		InlineLink inlineLink = (InlineLink)fragmentLink.getValue();

		Assert.assertEquals("http://www.myexample.com", inlineLink.getHref());

		InlineValue inlineValue = (InlineValue)fragmentFieldText.getText();

		Assert.assertNull(inlineValue.getValue());

		Map<String, String> i18nMap = inlineValue.getValue_i18n();

		Assert.assertEquals("My example", i18nMap.get("en_US"));
		Assert.assertEquals("Mi ejemplo", i18nMap.get("es_ES"));
	}

	private void _validateFragmentImage(FragmentImage fragmentImage) {
		Assert.assertNull(fragmentImage.getTitle());

		InlineValue urlInlineValue = (InlineValue)fragmentImage.getUrl();

		Assert.assertNull(urlInlineValue.getValue());

		Map<String, String> urlI18nMap = urlInlineValue.getValue_i18n();

		Assert.assertEquals(
			"http://myexample.com/myexample.png", urlI18nMap.get("en_US"));
		Assert.assertEquals(
			"http://miejemplo.es/miejemplo.png", urlI18nMap.get("es_ES"));
	}

	private void _validateFragmentImageWithTitle(
		FragmentImage fragmentImage, String title) {

		InlineValue titleInlineValue = (InlineValue)fragmentImage.getTitle();

		Assert.assertEquals(title, titleInlineValue.getValue());

		InlineValue urlInlineValue = (InlineValue)fragmentImage.getUrl();

		Assert.assertNull(urlInlineValue.getValue());

		Map<String, String> urlI18nMap = urlInlineValue.getValue_i18n();

		Assert.assertEquals(
			"http://myexample.com/myexample.png", urlI18nMap.get("en_US"));
		Assert.assertEquals(
			"http://miejemplo.es/miejemplo.png", urlI18nMap.get("es_ES"));
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentRendererTracker _fragmentRendererTracker;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}