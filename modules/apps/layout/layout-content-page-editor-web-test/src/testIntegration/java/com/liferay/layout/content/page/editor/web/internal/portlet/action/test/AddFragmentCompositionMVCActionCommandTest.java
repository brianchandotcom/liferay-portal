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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.spring.mock.web.portlet.MockActionRequest;
import com.liferay.spring.mock.web.portlet.MockActionResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletResponse;
import javax.portlet.RenderParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AddFragmentCompositionMVCActionCommandTest {

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

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testDoTransactionalCommand() throws Exception {
		_layout = _addLayout();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		String html =
			"<div><a data-lfr-editable-id=\"my-link-editable-id\" " +
				"data-lfr-editable-type=\"link\" href=\"\" id=\"my-link-id\">" +
					"Example</a></div>";

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				"example-fragment-entry-key", RandomTestUtil.randomString(),
				StringPool.BLANK, html, StringPool.BLANK, StringPool.BLANK, 0,
				FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		JournalArticle journalArticle1 = _addJournalArticle(
			RandomTestUtil.randomString());

		JournalArticle journalArticle2 = _addJournalArticle(
			RandomTestUtil.randomString());

		HashMap<String, String> valuesMap = HashMapBuilder.put(
			"FRAGMENT_COLLECTION_NAME", fragmentCollection.getName()
		).put(
			"FRAGMENT_ENTRY_KEY", fragmentEntry.getFragmentEntryKey()
		).put(
			"FRAGMENT_ENTRY_NAME", fragmentEntry.getName()
		).put(
			"MAPPED_LINK_CLASS_NAME_ID",
			String.valueOf(
				_portal.getClassNameId(
					"com.liferay.journal.model.JournalArticle"))
		).put(
			"MAPPED_LINK_CLASS_PK",
			String.valueOf(journalArticle1.getResourcePrimKey())
		).put(
			"MAPPED_LINK_DEFAULT_VALUE", journalArticle1.getTitle()
		).put(
			"MAPPED_TEXT_CLASS_NAME_ID",
			String.valueOf(
				_portal.getClassNameId(
					"com.liferay.journal.model.JournalArticle"))
		).put(
			"MAPPED_TEXT_CLASS_PK",
			String.valueOf(journalArticle2.getResourcePrimKey())
		).put(
			"MAPPED_TEXT_DEFAULT_VALUE", journalArticle2.getTitle()
		).build();

		String editableValues = StringUtil.replace(
			_read("editable_values.json"), "${", "}", valuesMap);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_portal.getClassNameId(Layout.class), _layout.getPlid(),
				StringPool.BLANK, html, StringPool.BLANK,
				_read("fragment_configuration.json"), editableValues,
				StringPool.BLANK, 0, null, _serviceContext);

		String data = StringUtil.replace(
			_read("layout_data_with_section_with_fragment_with_mapping.json"),
			"${", "}",
			HashMapBuilder.put(
				"FRAGMENT_ENTRY_LINK_ID",
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
			).build());

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()), _layout.getPlid(),
			data, _serviceContext);

		MockActionRequest mockActionRequest = new MockLiferayPortletRequest() {
			{
				setAttribute(WebKeys.THEME_DISPLAY, _getThemeDisplay());
				setParameter("description", RandomTestUtil.randomString());
				setParameter(
					"fragmentCollectionId",
					String.valueOf(
						fragmentCollection.getFragmentCollectionId()));
				setParameter("itemId", "SECTION_ID");
				setParameter("name", RandomTestUtil.randomString());
				setParameter("saveInlineContent", Boolean.TRUE.toString());
				setParameter(
					"saveMappingConfiguration", Boolean.TRUE.toString());
			}
		};

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, new MockActionResponse());

		Assert.assertEquals(
			String.valueOf(fragmentCollection.getFragmentCollectionId()),
			jsonObject.getString("fragmentCollectionId"));
		Assert.assertEquals(
			fragmentCollection.getName(),
			jsonObject.getString("fragmentCollectionName"));

		Assert.assertTrue(
			Validator.isNotNull(jsonObject.getString("fragmentEntryKey")));
		Assert.assertEquals(
			String.valueOf(_group.getGroupId()),
			jsonObject.getString("groupId"));
		Assert.assertEquals(
			mockActionRequest.getParameter("name"),
			jsonObject.getString("name"));
		Assert.assertEquals("composition", jsonObject.getString("type"));

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				_group.getGroupId(), jsonObject.getString("fragmentEntryKey"));

		JSONObject expectedFragmentCompositionDataJSONObject =
			JSONFactoryUtil.createJSONObject(
				StringUtil.replace(
					_read("expected_fragment_composition_data.json"), "${", "}",
					valuesMap));

		JSONObject fragmentCompositionDataJSONObject =
			fragmentComposition.getDataJSONObject();

		Assert.assertEquals(
			expectedFragmentCompositionDataJSONObject.toJSONString(),
			fragmentCompositionDataJSONObject.toJSONString());
	}

	private JournalArticle _addJournalArticle(String title) throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, title
		).build();

		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		return JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap, null,
			contentMap, LocaleUtil.getSiteDefault(), false, true,
			_serviceContext);
	}

	private Layout _addLayout() throws PortalException {
		String randomString = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());

		String friendlyURL = StringPool.SLASH + randomString;

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			LayoutConstants.TYPE_CONTENT, false, friendlyURL, _serviceContext);
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/content_layout/add_fragment_composition"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	private static class MockLiferayPortletRequest
		extends MockActionRequest implements LiferayPortletRequest {

		@Override
		public void addParameter(String name, String value) {
			_mockHttpServletRequest.addParameter(name, value);

			super.addParameter(name, value);
		}

		@Override
		public void cleanUp() {
		}

		@Override
		public Map<String, String[]> clearRenderParameters() {
			return null;
		}

		@Override
		public void defineObjects(
			PortletConfig portletConfig, PortletResponse portletResponse) {
		}

		@Override
		public ActionParameters getActionParameters() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return super.getAttribute(name);
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return _mockHttpServletRequest;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public HttpServletRequest getOriginalHttpServletRequest() {
			return _mockHttpServletRequest;
		}

		@Override
		public Part getPart(String name) {
			return null;
		}

		@Override
		public Collection<Part> getParts() {
			return null;
		}

		@Override
		public long getPlid() {
			return 0;
		}

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public PortletContext getPortletContext() {
			return null;
		}

		@Override
		public String getPortletName() {
			return null;
		}

		@Override
		public HttpServletRequest getPortletRequestDispatcherRequest() {
			return null;
		}

		@Override
		public RenderParameters getRenderParameters() {
			return null;
		}

		@Override
		public String getUserAgent() {
			return null;
		}

		@Override
		public void invalidateSession() {
		}

		@Override
		public void setPortletRequestDispatcherRequest(
			HttpServletRequest httpServletRequest) {
		}

		private final MockHttpServletRequest _mockHttpServletRequest =
			new MockHttpServletRequest();

	}

}