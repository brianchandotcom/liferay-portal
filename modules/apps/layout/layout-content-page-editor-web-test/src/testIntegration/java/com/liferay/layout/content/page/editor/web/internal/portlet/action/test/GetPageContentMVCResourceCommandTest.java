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
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class GetPageContentMVCResourceCommandTest {

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

		_layout = _addLayout();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testCollectionMapping() throws Exception {
		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), null, _serviceContext);

		_createLayoutStructure(
			layoutStructure -> {
				CollectionStyledLayoutStructureItem
					collectionStyledLayoutStructureItem =
						(CollectionStyledLayoutStructureItem)
							layoutStructure.
								addCollectionStyledLayoutStructureItem(
									layoutStructure.getMainItemId(), 0);

				collectionStyledLayoutStructureItem.setCollectionJSONObject(
					JSONUtil.put(
						"classNameId",
						_portal.getClassNameId(AssetListEntry.class)
					).put(
						"classPK", assetListEntry.getAssetListEntryId()
					));
			});

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest();

		JSONArray jsonArray = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getPageContentsJSONArray",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			jsonObject.getLong("classPK"));
		Assert.assertEquals(
			assetListEntry.getTitle(), jsonObject.getString("title"));
	}

	@Test
	public void testContainerBackgroundImageDirectSelection() throws Exception {
		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			new byte[0], null, null, new ServiceContext());

		_createLayoutStructure(
			layoutStructure -> {
				ContainerStyledLayoutStructureItem
					containerStyledLayoutStructureItem =
						(ContainerStyledLayoutStructureItem)
							layoutStructure.
								addContainerStyledLayoutStructureItem(
									layoutStructure.getMainItemId(), 0);

				containerStyledLayoutStructureItem.updateItemConfig(
					JSONUtil.put(
						"styles",
						JSONUtil.put(
							"backgroundImage",
							JSONUtil.put(
								"classNameId",
								_portal.getClassNameId(FileEntry.class)
							).put(
								"classPK", fileEntry.getFileEntryId()
							).put(
								"url",
								_dlURLHelper.getPreviewURL(
									fileEntry, fileEntry.getFileVersion(), null,
									StringPool.BLANK, false, false)
							))));
			});

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest();

		JSONArray jsonArray = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getPageContentsJSONArray",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			fileEntry.getFileEntryId(), jsonObject.getLong("classPK"));
		Assert.assertEquals(
			fileEntry.getTitle(), jsonObject.getString("title"));
	}

	@Test
	public void testContainerBackgroundImageMappingSelection()
		throws Exception {

		JournalArticle journalArticle = _createJournalArticle();

		_createLayoutStructure(
			layoutStructure -> {
				ContainerStyledLayoutStructureItem
					containerStyledLayoutStructureItem =
						(ContainerStyledLayoutStructureItem)
							layoutStructure.
								addContainerStyledLayoutStructureItem(
									layoutStructure.getMainItemId(), 0);

				containerStyledLayoutStructureItem.updateItemConfig(
					JSONUtil.put(
						"styles",
						JSONUtil.put(
							"backgroundImage",
							JSONUtil.put(
								"classNameId",
								_portal.getClassNameId(JournalArticle.class)
							).put(
								"classPK", journalArticle.getResourcePrimKey()
							))));
			});

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest();

		JSONArray jsonArray = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getPageContentsJSONArray",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			journalArticle.getResourcePrimKey(), jsonObject.getLong("classPK"));
		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			jsonObject.getString("title"));
	}

	@Test
	public void testFragmentEntryLinkMapped() throws Exception {
		JournalArticle journalArticle = _createJournalArticle();

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, "{fieldSets: []}", null,
				0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
			SegmentsExperienceConstants.ID_DEFAULT, _layout.getPlid(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			JSONUtil.put(
				"com.liferay.fragment.entry.processor.editable." +
					"EditableFragmentEntryProcessor",
				JSONUtil.put(
					"test",
					JSONUtil.put(
						"classNameId",
						_portal.getClassNameId(JournalArticle.class)
					).put(
						"classPK", journalArticle.getResourcePrimKey()
					))
			).toString(),
			StringPool.BLANK, 0, null, _serviceContext);

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest();

		JSONArray jsonArray = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getPageContentsJSONArray",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			journalArticle.getResourcePrimKey(), jsonObject.getLong("classPK"));
		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			jsonObject.getString("title"));
	}

	@Test
	public void testPageContentsAreNotDuplicated() throws Exception {
		JournalArticle journalArticle = _createJournalArticle();

		_createLayoutStructure(
			layoutStructure -> {
				ContainerStyledLayoutStructureItem
					containerStyledLayoutStructureItem =
						(ContainerStyledLayoutStructureItem)
							layoutStructure.
								addContainerStyledLayoutStructureItem(
									layoutStructure.getMainItemId(), 0);

				JSONObject jsonObject = JSONUtil.put(
					"styles",
					JSONUtil.put(
						"backgroundImage",
						JSONUtil.put(
							"classNameId",
							_portal.getClassNameId(JournalArticle.class)
						).put(
							"classPK", journalArticle.getResourcePrimKey()
						)));

				containerStyledLayoutStructureItem.updateItemConfig(jsonObject);

				containerStyledLayoutStructureItem =
					(ContainerStyledLayoutStructureItem)
						layoutStructure.addContainerStyledLayoutStructureItem(
							layoutStructure.getMainItemId(), 0);

				containerStyledLayoutStructureItem.updateItemConfig(jsonObject);
			});

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest();

		JSONArray jsonArray = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getPageContentsJSONArray",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		Assert.assertEquals(1, jsonArray.length());
	}

	private Layout _addLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	private JournalArticle _createJournalArticle() throws Exception {
		return JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);
	}

	private void _createLayoutStructure(
			UnsafeConsumer<LayoutStructure, Exception> layoutStructureConsumer)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _layout.getPlid());

		String data = layoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		layoutStructureConsumer.accept(layoutStructure);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), _layout.getPlid(),
				layoutStructure.toString());
	}

	private MockLiferayResourceRequest _getMockLiferayPortletResourceRequest()
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, null);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayResourceResponse());

		ThemeDisplay themeDisplay = _getThemeDisplay();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayResourceRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_page_content"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}