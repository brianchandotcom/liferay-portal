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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.portlet.wiki.util.comparator.PageVersionComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyWiki extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCreateDate();
		verifyNoAssetPages();
	}

	protected void verifyCreateDate() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			WikiPageResourceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					WikiPageResource pageResource = (WikiPageResource)object;

					verifyCreateDate(pageResource);
				}

			});

		actionableDynamicQuery.performActions();

		if (_log.isDebugEnabled()) {
			_log.debug("Create dates verified for pages");
		}
	}

	protected void verifyCreateDate(WikiPageResource pageResource) {
		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			pageResource.getNodeId(), pageResource.getTitle(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		if (pages.size() <= 1) {
			return;
		}

		WikiPage firstPage = pages.get(0);

		Date createDate = firstPage.getCreateDate();

		for (WikiPage page : pages) {
			if (!createDate.equals(page.getCreateDate())) {
				page.setCreateDate(createDate);

				WikiPageLocalServiceUtil.updateWikiPage(page);
			}
		}
	}

	protected void verifyNoAssetPages() throws Exception {
		List<WikiPage> pages = WikiPageLocalServiceUtil.getNoAssetPages();

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + pages.size() + " pages with no asset");
		}

		for (WikiPage page : pages) {
			try {
				WikiPageLocalServiceUtil.updateAsset(
					page.getUserId(), page, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for page " + page.getPageId() +
							": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for pages");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyWiki.class);

}