/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.asset.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class KBArticleAssetRendererTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_kbArticle = _addKBArticle();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetURLViewInContextWithKBPortlet() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		ContentLayoutTestUtil.addPortletToLayout(
			layout, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE);

		Assert.assertNotNull(
			_getURLViewInContext(
				_kbArticle.getResourcePrimKey(),
				ContentLayoutTestUtil.getThemeDisplay(
					_companyLocalService.getCompany(_group.getCompanyId()),
					_group, layout)));
	}

	@Test
	public void testGetURLViewInContextWithoutKBPortlet() throws Exception {
		Assert.assertNull(
			_getURLViewInContext(
				_kbArticle.getResourcePrimKey(),
				ContentLayoutTestUtil.getThemeDisplay(
					_companyLocalService.getCompany(_group.getCompanyId()),
					_group, LayoutTestUtil.addTypeContentLayout(_group))));
	}

	private KBArticle _addKBArticle() throws Exception {
		return _kbArticleLocalService.addKBArticle(
			null, TestPropsValues.getUserId(),
			ClassNameLocalServiceUtil.getClassNameId(
				KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title KB Article",
			StringUtil.randomString(), "<strong>Context text</strong>",
			"Description", null, StringPool.BLANK, RandomTestUtil.nextDate(),
			null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private LiferayPortletRequest _getLiferayPortletRequest(
		ThemeDisplay themeDisplay) {

		MockRenderRequest renderRequest = new MockLiferayPortletRenderRequest();

		renderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return _portal.getLiferayPortletRequest(renderRequest);
	}

	private String _getURLViewInContext(
			long resourcePrimKey, ThemeDisplay themeDisplay)
		throws Exception {

		AssetRendererFactory<KBArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				KBArticle.class);

		AssetRenderer<KBArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(resourcePrimKey);

		return assetRenderer.getURLViewInContext(
			_getLiferayPortletRequest(themeDisplay), null, null);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private KBArticle _kbArticle;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	@Inject
	private Portal _portal;

}