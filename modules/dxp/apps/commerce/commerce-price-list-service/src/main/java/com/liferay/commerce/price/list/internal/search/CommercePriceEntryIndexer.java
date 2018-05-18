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

package com.liferay.commerce.price.list.internal.search;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommercePriceEntryIndexer extends BaseIndexer<CommercePriceEntry> {

	public static final String CLASS_NAME = CommercePriceEntry.class.getName();

	public static final String FIELD_COMMERCE_PRICE_LIST_ID =
		"commercePriceListId";

	public CommercePriceEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.UID);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long commercePriceListId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_COMMERCE_PRICE_LIST_ID));

		if (commercePriceListId > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_COMMERCE_PRICE_LIST_ID, commercePriceListId);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	@Override
	protected void doDelete(CommercePriceEntry commercePriceEntry)
		throws Exception {

		deleteDocument(
			commercePriceEntry.getCompanyId(),
			commercePriceEntry.getCommercePriceEntryId());
	}

	@Override
	protected Document doGetDocument(CommercePriceEntry commercePriceEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing price entry " + commercePriceEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commercePriceEntry);

		document.addKeyword(
			FIELD_COMMERCE_PRICE_LIST_ID,
			commercePriceEntry.getCommercePriceEntryId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commercePriceEntry + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, FIELD_COMMERCE_PRICE_LIST_ID,
			FIELD_COMMERCE_PRICE_LIST_ID);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommercePriceEntry commercePriceEntry)
		throws Exception {

		Document document = getDocument(commercePriceEntry);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commercePriceEntry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.getCommercePriceEntry(classPK);

		doReindex(commercePriceEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommercePriceEntries(companyId);
	}

	protected void reindexCommercePriceEntries(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commercePriceEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CommercePriceEntry>() {

				@Override
				public void performAction(
					CommercePriceEntry commercePriceEntry) {

					try {
						Document document = getDocument(commercePriceEntry);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce price entry " +
									commercePriceEntry.
										getCommercePriceEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryIndexer.class);

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}