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

package com.liferay.wiki.internal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageResourceLocalService;
import com.liferay.wiki.util.comparator.PageVersionComparator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Iván Zaera
 */
@Component(
	immediate = true, property = "verify.process.name=com.liferay.wiki.service",
	service = VerifyProcess.class
)
public class WikiServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCreateDate();
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	@Reference(unbind = "-")
	protected void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {

		_wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	protected void verifyCreateDate() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_wikiPageResourceLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.
					PerformActionMethod<WikiPageResource>() {

					@Override
					public void performAction(WikiPageResource pageResource) {
						verifyCreateDate(pageResource);
					}

				});

			actionableDynamicQuery.performActions();

			if (_log.isDebugEnabled()) {
				_log.debug("Create dates verified for pages");
			}
		}
	}

	protected void verifyCreateDate(WikiPageResource pageResource) {
		List<WikiPage> pages = _wikiPageLocalService.getPages(
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

				_wikiPageLocalService.updateWikiPage(page);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiServiceVerifyProcess.class);

	private WikiPageLocalService _wikiPageLocalService;
	private WikiPageResourceLocalService _wikiPageResourceLocalService;

}