/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.Date;
import java.util.Locale;

import org.junit.After;
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
public class MapToFieldInformationTemplateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_locale = _portal.getSiteDefaultLocale(_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_blogsEntry = _addBlogsEntry();
		_fragmentEntry = _addFragmentEntry();
		_journalArticle = _addJournalArticle();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = _layout.fetchDraftLayout();

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testMapToEditableFieldInCollectionDisplay() throws Exception {
		InfoField infoField = TemplateTestUtil.addTemplateEntryInfoField(
			"AssetEntry_title", AssetEntry.class.getName(), StringPool.BLANK,
			_infoItemServiceRegistry, _serviceContext);

		ContentLayoutTestUtil.addCollectionDisplayToLayout(
			JSONUtil.put(
				"itemType", AssetEntry.class.getName()
			).put(
				"key",
				"com.liferay.asset.internal.info.collection.provider." +
					"RecentContentInfoCollectionProvider"
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			),
			_draftLayout, _layoutStructureProvider, null, 0,
			_segmentsExperienceId,
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				_segmentsExperienceId, _draftLayout.getPlid(),
				_fragmentEntry.getCss(), _fragmentEntry.getHtml(),
				_fragmentEntry.getJs(), _fragmentEntry.getConfiguration(),
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"element-text",
						JSONUtil.put(
							"collectionFieldId", infoField.getUniqueId()))
				).toString(),
				StringPool.BLANK, 0, _fragmentEntry.getFragmentEntryKey(),
				_fragmentEntry.getType(), _serviceContext));

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		_assertRenderLayoutHTML(
			_blogsEntry.getTitle(), _journalArticle.getTitle());
	}

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, null, null, _serviceContext);
	}

	private FragmentEntry _addFragmentEntry() throws Exception {
		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, _serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK,
			"<h1 data-lfr-editable-id=\"element-text\" " +
				"data-lfr-editable-type=\"text\">Heading Example</h1>",
			StringPool.BLANK, false, StringPool.BLANK, null, 0, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private JournalArticle _addJournalArticle() throws Exception {
		_ddmFormField = _createDDMFormField();

		return JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, _ddmFormField,
			_ddmFormValuesToFieldsConverter, RandomTestUtil.randomString(),
			_group.getGroupId(), _journalConverter);
	}

	private void _assertRenderLayoutHTML(String... strings) throws Exception {
		String html = ContentLayoutTestUtil.getRenderLayoutHTML(
			_layout, _layoutServiceContextHelper, _layoutStructureProvider,
			_segmentsExperienceId);

		for (String string : strings) {
			Assert.assertTrue(
				html + " not contains " + string,
				StringUtil.contains(html, string, StringPool.BLANK));
		}
	}

	private DDMFormField _createDDMFormField() {
		DDMFormField ddmFormField = new DDMFormField(
			"name", DDMFormFieldTypeConstants.TEXT);

		ddmFormField.setDataType("text");
		ddmFormField.setIndexType("text");

		LocalizedValue localizedValue = new LocalizedValue(_locale);

		localizedValue.addString(_locale, RandomTestUtil.randomString(10));

		ddmFormField.setLabel(localizedValue);

		ddmFormField.setLocalizable(true);

		return ddmFormField;
	}

	private BlogsEntry _blogsEntry;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	private DDMFormField _ddmFormField;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	private Layout _draftLayout;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	private JournalArticle _journalArticle;

	@Inject
	private JournalConverter _journalConverter;

	private Layout _layout;

	@Inject
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	private Locale _locale;

	@Inject
	private Portal _portal;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}