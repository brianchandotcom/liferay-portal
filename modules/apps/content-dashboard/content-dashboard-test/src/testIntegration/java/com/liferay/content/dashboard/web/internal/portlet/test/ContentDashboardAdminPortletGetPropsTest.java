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

package com.liferay.content.dashboard.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.test.MockLiferayPortletContext;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class ContentDashboardAdminPortletGetPropsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		_user = UserTestUtil.getAdminUser(_company.getCompanyId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		_companyLocalService.deleteCompany(_company);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);
	}

	@Test
	public void testGetPropsWithAssetCategoriesSortedByKey() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_company.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"vocabulary", serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category-1",
			assetVocabulary.getVocabularyId(), serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category-2",
			assetVocabulary.getVocabularyId(), serviceContext);

		try {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {
						assetCategory1.getCategoryId(),
						assetCategory2.getCategoryId()
					}));
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {assetCategory2.getCategoryId()}));

			Map<String, Object> data = _getData(
				_getMockLiferayPortletRenderRequest(
					new String[] {
						String.valueOf(assetVocabulary.getVocabularyId())
					}));

			Map<String, Object> props = (Map<String, Object>)data.get("props");

			Assert.assertEquals(
				JSONUtil.putAll(
					JSONUtil.put(
						"key", String.valueOf(assetCategory1.getCategoryId())
					).put(
						"name", "category-1"
					).put(
						"value", 1L
					).put(
						"vocabularyName", "vocabulary"
					),
					JSONUtil.put(
						"key", String.valueOf(assetCategory2.getCategoryId())
					).put(
						"name", "category-2"
					).put(
						"value", 2L
					).put(
						"vocabularyName", "vocabulary"
					)
				).toString(),
				String.valueOf(props.get("vocabularies")));
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
		}
	}

	@Test
	public void testGetPropsWithChildAssetCategoriesSortedByKey()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_company.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"vocabulary", serviceContext);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category",
			assetVocabulary.getVocabularyId(), serviceContext);

		AssetVocabulary childAssetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-vocabulary", serviceContext);

		AssetCategory childAssetCategory1 =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-category-1", childAssetVocabulary.getVocabularyId(),
				serviceContext);
		AssetCategory childAssetCategory2 =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-category-2", childAssetVocabulary.getVocabularyId(),
				serviceContext);

		try {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {
						assetCategory.getCategoryId(),
						childAssetCategory1.getCategoryId(),
						childAssetCategory2.getCategoryId()
					}));
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {
						assetCategory.getCategoryId(),
						childAssetCategory2.getCategoryId()
					}));

			Map<String, Object> data = _getData(
				_getMockLiferayPortletRenderRequest(
					new String[] {
						String.valueOf(assetVocabulary.getVocabularyId()),
						String.valueOf(childAssetVocabulary.getVocabularyId())
					}));

			Map<String, Object> props = (Map<String, Object>)data.get("props");

			Assert.assertEquals(
				JSONUtil.putAll(
					JSONUtil.put(
						"categories",
						JSONUtil.putAll(
							JSONUtil.put(
								"key",
								String.valueOf(
									childAssetCategory1.getCategoryId())
							).put(
								"name", "child-category-1"
							).put(
								"value", 1L
							).put(
								"vocabularyName", "child-vocabulary"
							),
							JSONUtil.put(
								"key",
								String.valueOf(
									childAssetCategory2.getCategoryId())
							).put(
								"name", "child-category-2"
							).put(
								"value", 2L
							).put(
								"vocabularyName", "child-vocabulary"
							))
					).put(
						"key", String.valueOf(assetCategory.getCategoryId())
					).put(
						"name", "category"
					).put(
						"value", 2L
					).put(
						"vocabularyName", "vocabulary"
					)
				).toString(),
				String.valueOf(props.get("vocabularies")));
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
			_assetVocabularyLocalService.deleteVocabulary(childAssetVocabulary);
		}
	}

	@Test
	public void testGetPropsWithChildNoneAssetCategory() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_company.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"vocabulary", serviceContext);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category",
			assetVocabulary.getVocabularyId(), serviceContext);

		AssetVocabulary childAssetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-vocabulary", serviceContext);

		AssetCategory childAssetCategory =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-category", childAssetVocabulary.getVocabularyId(),
				serviceContext);

		try {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {assetCategory.getCategoryId()}));
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {
						assetCategory.getCategoryId(),
						childAssetCategory.getCategoryId()
					}));

			Map<String, Object> data = _getData(
				_getMockLiferayPortletRenderRequest(
					new String[] {
						String.valueOf(assetVocabulary.getVocabularyId()),
						String.valueOf(childAssetVocabulary.getVocabularyId())
					}));

			Map<String, Object> props = (Map<String, Object>)data.get("props");

			Assert.assertEquals(
				JSONUtil.putAll(
					JSONUtil.put(
						"categories",
						JSONUtil.putAll(
							JSONUtil.put(
								"key",
								String.valueOf(
									childAssetCategory.getCategoryId())
							).put(
								"name", "child-category"
							).put(
								"value", 1L
							).put(
								"vocabularyName", "child-vocabulary"
							),
							JSONUtil.put(
								"key", "none"
							).put(
								"name", "No child-vocabulary Specified"
							).put(
								"value", 1L
							).put(
								"vocabularyName", "child-vocabulary"
							))
					).put(
						"key", String.valueOf(assetCategory.getCategoryId())
					).put(
						"name", "category"
					).put(
						"value", 2L
					).put(
						"vocabularyName", "vocabulary"
					)
				).toString(),
				String.valueOf(props.get("vocabularies")));
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
			_assetVocabularyLocalService.deleteVocabulary(childAssetVocabulary);
		}
	}

	@Test
	public void testGetPropsWithMissingAssetVocabulary() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_company.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"vocabulary", serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category",
			assetVocabulary.getVocabularyId(), serviceContext);

		AssetVocabulary childAssetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-vocabulary", serviceContext);

		AssetCategory childAssetCategory =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-category", childAssetVocabulary.getVocabularyId(),
				serviceContext);

		try {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {childAssetCategory.getCategoryId()}));

			Map<String, Object> data = _getData(
				_getMockLiferayPortletRenderRequest(
					new String[] {
						String.valueOf(assetVocabulary.getVocabularyId()),
						String.valueOf(childAssetVocabulary.getVocabularyId())
					}));

			Map<String, Object> props = (Map<String, Object>)data.get("props");

			Assert.assertEquals(
				JSONUtil.put(
					JSONUtil.put(
						"key",
						String.valueOf(childAssetCategory.getCategoryId())
					).put(
						"name", "child-category"
					).put(
						"value", 1L
					).put(
						"vocabularyName", "child-vocabulary"
					)
				).toString(),
				String.valueOf(props.get("vocabularies")));
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
			_assetVocabularyLocalService.deleteVocabulary(childAssetVocabulary);
		}
	}

	@Test
	public void testGetPropsWithMissingChildAssetVocabulary() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_company.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"vocabulary", serviceContext);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category",
			assetVocabulary.getVocabularyId(), serviceContext);

		AssetVocabulary childAssetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		try {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {assetCategory.getCategoryId()}));

			Map<String, Object> data = _getData(
				_getMockLiferayPortletRenderRequest(
					new String[] {
						String.valueOf(assetVocabulary.getVocabularyId()),
						String.valueOf(childAssetVocabulary.getVocabularyId())
					}));

			Map<String, Object> props = (Map<String, Object>)data.get("props");

			Assert.assertEquals(
				JSONUtil.put(
					JSONUtil.put(
						"key", String.valueOf(assetCategory.getCategoryId())
					).put(
						"name", "category"
					).put(
						"value", 1L
					).put(
						"vocabularyName", "vocabulary"
					)
				).toString(),
				String.valueOf(props.get("vocabularies")));
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
			_assetVocabularyLocalService.deleteVocabulary(childAssetVocabulary);
		}
	}

	@Test
	public void testGetPropsWithNoneAssetCategory() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_company.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"vocabulary", serviceContext);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _company.getGroupId(), "category",
			assetVocabulary.getVocabularyId(), serviceContext);

		AssetVocabulary childAssetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-vocabulary", serviceContext);

		AssetCategory childAssetCategory =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _company.getGroupId(),
				"child-category", childAssetVocabulary.getVocabularyId(),
				serviceContext);

		try {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {
						assetCategory.getCategoryId(),
						childAssetCategory.getCategoryId()
					}));
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId(),
					new long[] {childAssetCategory.getCategoryId()}));

			Map<String, Object> data = _getData(
				_getMockLiferayPortletRenderRequest(
					new String[] {
						String.valueOf(assetVocabulary.getVocabularyId()),
						String.valueOf(childAssetVocabulary.getVocabularyId())
					}));

			Map<String, Object> props = (Map<String, Object>)data.get("props");

			Assert.assertEquals(
				JSONUtil.putAll(
					JSONUtil.put(
						"categories",
						JSONUtil.put(
							JSONUtil.put(
								"key",
								String.valueOf(
									childAssetCategory.getCategoryId())
							).put(
								"name", "child-category"
							).put(
								"value", 1L
							).put(
								"vocabularyName", "child-vocabulary"
							))
					).put(
						"key", String.valueOf(assetCategory.getCategoryId())
					).put(
						"name", "category"
					).put(
						"value", 1L
					).put(
						"vocabularyName", "vocabulary"
					)
				).put(
					JSONUtil.put(
						"categories",
						JSONUtil.put(
							JSONUtil.put(
								"key",
								String.valueOf(
									childAssetCategory.getCategoryId())
							).put(
								"name", "child-category"
							).put(
								"value", 1L
							).put(
								"vocabularyName", "child-vocabulary"
							))
					).put(
						"key", "none"
					).put(
						"name", "No vocabulary Specified"
					).put(
						"value", 1L
					).put(
						"vocabularyName", "vocabulary"
					)
				).toString(),
				String.valueOf(props.get("vocabularies")));
		}
		finally {
			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
			_assetVocabularyLocalService.deleteVocabulary(childAssetVocabulary);
		}
	}

	private Map<String, Object> _getData(
			MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest)
		throws Exception {

		MVCPortlet mvcPortlet = (MVCPortlet)_portlet;

		mvcPortlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		return ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"CONTENT_DASHBOARD_ADMIN_DISPLAY_CONTEXT"),
			"getData", new Class<?>[0]);
	}

	private MockLiferayPortletRenderRequest _getMockLiferayPortletRenderRequest(
			String[] assetVocabularyIds)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.COMPANY_ID, _company.getCompanyId());

		mockLiferayPortletRenderRequest.setAttribute(
			StringBundler.concat(
				mockLiferayPortletRenderRequest.getPortletName(), "-",
				WebKeys.CURRENT_PORTLET_URL),
			new MockLiferayPortletURL());

		String path = "/view.jsp";

		mockLiferayPortletRenderRequest.setParameter("mvcPath", path);

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			new MockLiferayPortletContext(path));

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		PortletPreferences portletPreferences =
			mockLiferayPortletRenderRequest.getPreferences();

		portletPreferences.setValues("assetVocabularyIds", assetVocabularyIds);

		return mockLiferayPortletRenderRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setUser(_company.getDefaultUser());

		return themeDisplay;
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static PermissionChecker _permissionChecker;
	private static User _user;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.web.internal.portlet.ContentDashboardAdminPortlet"
	)
	private Portlet _portlet;

}