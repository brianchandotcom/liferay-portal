/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class FriendlyUrlHistoryResourceTest
	extends BaseFriendlyUrlHistoryResourceTestCase {

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getDisplayPageLayoutPageTemplateEntry(serviceContext);

		List<String> friendlyURLs = _updateLayout(
			_layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid()));

		FriendlyUrlHistory friendlyUrlHistory =
			friendlyUrlHistoryResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory(
					testGroup.getExternalReferenceCode(),
					layoutPageTemplateEntry.getExternalReferenceCode());

		_assertFriendlyUrlHistoryJSONObject(
			_jsonFactory.createJSONObject(
				GetterUtil.getString(
					friendlyUrlHistory.getFriendlyUrlPath_i18n())),
			friendlyURLs);

		_assertProblemException(
			_getBasicLayoutPageTemplateEntry(serviceContext));
		_assertProblemException(
			_getMasterLayoutPageTemplateEntry(serviceContext));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			testGroup.getGroupId());

		_testGetSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
			layout);

		layout = LayoutTestUtil.addTypeContentLayout(testGroup);

		_testGetSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
			layout);

		_assertProblemException(layout.fetchDraftLayout());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		_assertProblemException(
			_getBasicLayoutPageTemplateEntryLayout(serviceContext));
		_assertProblemException(
			_getDisplayPageLayoutPageTemplateEntryLayout(serviceContext));
		_assertProblemException(
			_getMasterLayoutPageTemplateEntryLayout(serviceContext));
		_assertProblemException(
			_getLayoutUtilityPageEntryLayout(serviceContext));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeUtilityPageFriendlyUrlHistory()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry(
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId()));

		List<String> friendlyURLs = _updateLayout(
			_layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid()));

		FriendlyUrlHistory friendlyUrlHistory =
			friendlyUrlHistoryResource.
				getSiteSiteByExternalReferenceCodeUtilityPageFriendlyUrlHistory(
					testGroup.getExternalReferenceCode(),
					layoutUtilityPageEntry.getExternalReferenceCode());

		_assertFriendlyUrlHistoryJSONObject(
			_jsonFactory.createJSONObject(
				GetterUtil.getString(
					friendlyUrlHistory.getFriendlyUrlPath_i18n())),
			friendlyURLs);
	}

	private void _assertFriendlyUrlHistoryJSONObject(
		JSONObject jsonObject, List<String> friendlyURLs) {

		Assert.assertEquals(1, jsonObject.length());

		JSONArray jsonArray = jsonObject.getJSONArray(
			LocaleUtil.toBCP47LanguageId(LocaleUtil.getSiteDefault()));

		Assert.assertEquals(4, jsonArray.length());

		for (int i = 0; i < friendlyURLs.size(); i++) {
			Assert.assertEquals(friendlyURLs.get(i), jsonArray.getString(i));
		}
	}

	private void _assertProblemException(Layout layout) throws Exception {
		try {
			friendlyUrlHistoryResource.
				getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private void _assertProblemException(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		try {
			friendlyUrlHistoryResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory(
					testGroup.getExternalReferenceCode(),
					layoutPageTemplateEntry.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private LayoutPageTemplateEntry _getBasicLayoutPageTemplateEntry(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(),
					serviceContext.getScopeGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					serviceContext);

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.BASIC, 0,
			WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	private Layout _getBasicLayoutPageTemplateEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getBasicLayoutPageTemplateEntry(serviceContext);

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private LayoutPageTemplateEntry _getDisplayPageLayoutPageTemplateEntry(
			ServiceContext serviceContext)
		throws Exception {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			_portal.getClassNameId(
				"com.liferay.asset.kernel.model.AssetCategory"),
			0, RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private Layout _getDisplayPageLayoutPageTemplateEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getDisplayPageLayoutPageTemplateEntry(serviceContext);

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private LayoutUtilityPageEntry _getLayoutUtilityPageEntry(
			ServiceContext serviceContext)
		throws Exception {

		return _layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			0, 0, false, RandomTestUtil.randomString(),
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR, 0,
			serviceContext);
	}

	private Layout _getLayoutUtilityPageEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry(serviceContext);

		return _layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid());
	}

	private LayoutPageTemplateEntry _getMasterLayoutPageTemplateEntry(
			ServiceContext serviceContext)
		throws Exception {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
			WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	private Layout _getMasterLayoutPageTemplateEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getMasterLayoutPageTemplateEntry(serviceContext);

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private void
			_testGetSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
				Layout layout)
		throws Exception {

		List<String> friendlyURLs = _updateLayout(layout);

		FriendlyUrlHistory friendlyUrlHistory =
			friendlyUrlHistoryResource.
				getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode());

		_assertFriendlyUrlHistoryJSONObject(
			_jsonFactory.createJSONObject(
				GetterUtil.getString(
					friendlyUrlHistory.getFriendlyUrlPath_i18n())),
			friendlyURLs);
	}

	private List<String> _updateLayout(Layout layout) throws Exception {
		List<String> friendlyURLs = new ArrayList<>();

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		friendlyURLs.add(layout.getFriendlyURL(LocaleUtil.getSiteDefault()));

		for (int i = 0; i < 3; i++) {
			layout = _layoutLocalService.updateFriendlyURL(
				TestPropsValues.getUserId(), layout.getPlid(),
				"/" + RandomTestUtil.randomString(), defaultLanguageId);

			friendlyURLs.add(
				layout.getFriendlyURL(LocaleUtil.getSiteDefault()));
		}

		for (Locale locale :
				_language.getAvailableLocales(testGroup.getGroupId())) {

			layout = _layoutLocalService.updateName(
				layout, RandomTestUtil.randomString(),
				LocaleUtil.toLanguageId(locale));
		}

		Collections.reverse(friendlyURLs);

		return friendlyURLs;
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Language _language;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private Portal _portal;

}