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

package com.liferay.mail.reader.internal.search;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeIndexerUtil;
import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.service.AccountLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Michael C. Han
 * @author Peter Fellwock
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class AccountIndexer extends BaseIndexer<Account> {

	public static final String CLASS_NAME = Account.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(Account account) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(account.getCompanyId());
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setSearchEngineId(getSearchEngineId());
		searchContext.setSorts(SortFactoryUtil.getDefaultSorts());
		searchContext.setStart(QueryUtil.ALL_POS);

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, CLASS_NAME);

		booleanQuery.addRequiredTerm("accountId", account.getAccountId());

		Hits hits = indexSearcherHelper.search(searchContext, booleanQuery);

		List<String> uids = new ArrayList<>(hits.getLength());

		for (int i = 0; i < hits.getLength(); i++) {
			Document document = hits.doc(i);

			uids.add(document.get(Field.UID));
		}

		indexWriterHelper.deleteDocuments(
			getSearchEngineId(), account.getCompanyId(), uids,
			isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(Account account) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, account);

		ExpandoBridge expandoBridge = account.getExpandoBridge();

		document.addKeyword("accountId", account.getAccountId());
		document.addText(Field.NAME, account.getAddress());

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document doc, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(Account account) throws Exception {
		indexWriterHelper.updateDocument(
			getSearchEngineId(), account.getCompanyId(), getDocument(account),
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(accountLocalService.getAccount(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexMessages(companyId);
	}

	protected void reindexMessages(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			accountLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(Account account) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(account));
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index account " + account.getAccountId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected AccountLocalService accountLocalService;
	protected IndexSearcherHelper indexSearcherHelper;
	protected IndexWriterHelper indexWriterHelper;

	private static final Log _log = LogFactoryUtil.getLog(AccountIndexer.class);

}