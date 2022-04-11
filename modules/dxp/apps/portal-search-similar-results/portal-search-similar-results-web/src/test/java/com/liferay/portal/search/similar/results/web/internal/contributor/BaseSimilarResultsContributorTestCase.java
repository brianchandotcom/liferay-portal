/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.contributor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelper;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelperImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.util.PortalImpl;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 */
public abstract class BaseSimilarResultsContributorTestCase {

	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	protected AssetEntry setUpAssetEntry(long entryId) {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			entryId
		).when(
			assetEntry
		).getEntryId();

		return assetEntry;
	}

	protected AssetEntry setUpAssetEntry(String className) {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			className
		).when(
			assetEntry
		).getClassName();

		return assetEntry;
	}

	protected AssetEntry setUpAssetEntry(String className, long classPK) {
		AssetEntry assetEntry = setUpAssetEntry(className);

		Mockito.doReturn(
			classPK
		).when(
			assetEntry
		).getClassPK();

		return assetEntry;
	}

	protected void setUpAssetEntryLocalServiceFetchAssetEntry(
		AssetEntry assetEntry) {

		Mockito.doReturn(
			assetEntry
		).when(
			assetEntryLocalService
		).fetchAssetEntry(
			Matchers.anyLong()
		);
	}

	protected void setUpAssetEntryLocalServiceFetchEntry(
		AssetEntry assetEntry) {

		Mockito.doReturn(
			assetEntry
		).when(
			assetEntryLocalService
		).fetchEntry(
			Matchers.anyLong(), Matchers.anyString()
		);

		Mockito.doReturn(
			assetEntry
		).when(
			assetEntryLocalService
		).fetchEntry(
			Matchers.anyLong(), Matchers.anyLong()
		);
	}

	protected AssetRenderer<?> setUpAssetRenderer(WikiPage wikiPage) {
		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			wikiPage
		).when(
			assetRenderer
		).getAssetObject();

		return assetRenderer;
	}

	protected void setUpCriteriaHelper(long value) {
		Mockito.doReturn(
			value
		).when(
			criteriaHelper
		).getGroupId();
	}

	protected void setUpCriteriaHelper(
		String parameterName, long parameterValue) {

		Mockito.doReturn(
			parameterValue
		).when(
			criteriaHelper
		).getRouteParameter(
			Matchers.eq(parameterName)
		);
	}

	protected void setUpCriteriaHelper(
		String parameterName, String parameterValue) {

		Mockito.doReturn(
			parameterValue
		).when(
			criteriaHelper
		).getRouteParameter(
			Matchers.eq(parameterName)
		);
	}

	protected void setUpDestinationHelper(AssetEntry assetEntry) {
		Mockito.doReturn(
			assetEntry
		).when(
			destinationHelper
		).getAssetEntry();
	}

	protected void setUpDestinationHelper(AssetRenderer<?> assetRenderer) {
		Mockito.doReturn(
			assetRenderer
		).when(
			destinationHelper
		).getAssetRenderer();
	}

	protected void setUpDestinationHelper(String className) {
		Mockito.doReturn(
			className
		).when(
			destinationHelper
		).getClassName();
	}

	protected void setUpDestinationHelperGetRouteParameter(
		long entryId, String paramterName) {

		Mockito.doReturn(
			entryId
		).when(
			destinationHelper
		).getRouteParameter(
			Matchers.eq(paramterName)
		);
	}

	protected void setUpDestinationHelperGetRouteParameter(
		String parameterName, String parameterValue) {

		Mockito.doReturn(
			parameterValue
		).when(
			destinationHelper
		).getRouteParameter(
			Matchers.eq(parameterName)
		);
	}

	protected HttpHelper setUpHttpHelper() {
		return new HttpHelperImpl();
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	protected UIDFactory setUpUIDFactory(String uid) {
		UIDFactory uidFactory = Mockito.mock(UIDFactory.class);

		Mockito.doReturn(
			uid
		).when(
			uidFactory
		).getUID(
			Matchers.any(ClassedModel.class)
		);

		return uidFactory;
	}

	protected WikiNode setUpWikiNode(String name) {
		WikiNode wikiNode = Mockito.mock(WikiNode.class);

		Mockito.doReturn(
			name
		).when(
			wikiNode
		).getName();

		return wikiNode;
	}

	protected void setUpWikiNodeLocalService(WikiNode wikiNode) {
		Mockito.doReturn(
			wikiNode
		).when(
			wikiNodeLocalService
		).fetchNode(
			Matchers.anyLong(), Matchers.anyString()
		);
	}

	protected WikiPage setUpWikiPage(String title, WikiNode wikiNode) {
		WikiPage wikiPage = Mockito.mock(WikiPage.class);

		Mockito.doReturn(
			wikiNode
		).when(
			wikiPage
		).getNode();

		Mockito.doReturn(
			title
		).when(
			wikiPage
		).getTitle();

		return wikiPage;
	}

	protected void setUpWikiPageLocalService(WikiPage wikiPage) {
		Mockito.doReturn(
			wikiPage
		).when(
			wikiPageLocalService
		).fetchPage(
			Matchers.anyLong(), Matchers.anyString(), Matchers.anyDouble()
		);
	}

	@Mock
	protected AssetEntryLocalService assetEntryLocalService;

	@Mock
	protected CriteriaHelper criteriaHelper;

	@Mock
	protected DestinationHelper destinationHelper;

	@Mock
	protected WikiNodeLocalService wikiNodeLocalService;

	@Mock
	protected WikiPageLocalService wikiPageLocalService;

}