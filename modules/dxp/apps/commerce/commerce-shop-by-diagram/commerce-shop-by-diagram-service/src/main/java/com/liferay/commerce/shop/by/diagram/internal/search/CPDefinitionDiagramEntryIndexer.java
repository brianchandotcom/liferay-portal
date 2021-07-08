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

package com.liferay.commerce.shop.by.diagram.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryLocalService;
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

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CPDefinitionDiagramEntryIndexer
	extends BaseIndexer<CPDefinitionDiagramEntry> {

	public static final String CLASS_NAME =
		CPDefinitionDiagramEntry.class.getName();

	public static final String FIELD_NUMBER = "number";

	public static final String FIELD_QUANTITY = "quantity";

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
	}

	@Override
	protected void doDelete(CPDefinitionDiagramEntry cpDefinitionDiagramEntry)
		throws Exception {

		deleteDocument(
			cpDefinitionDiagramEntry.getCompanyId(),
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId());
	}

	@Override
	protected Document doGetDocument(
			CPDefinitionDiagramEntry cpDefinitionDiagramEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing commerce product definition diagram settings " +
					cpDefinitionDiagramEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, cpDefinitionDiagramEntry);

		document.addNumber(FIELD_NUMBER, cpDefinitionDiagramEntry.getNumber());
		document.addText(CPField.SKU, cpDefinitionDiagramEntry.getSku());
		document.addNumber(
			FIELD_QUANTITY, cpDefinitionDiagramEntry.getQuantity());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + cpDefinitionDiagramEntry +
					" indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPDefinitionDiagramEntry cpDefinitionDiagramEntry)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpDefinitionDiagramEntry.getCompanyId(),
			getDocument(cpDefinitionDiagramEntry), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_cpDefinitionDiagramEntryLocalService.getCPDefinitionDiagramEntry(
				classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPDefinitionDiagramEntry(companyId);
	}

	protected void reindexCPDefinitionDiagramEntry(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpDefinitionDiagramEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPDefinitionDiagramEntry cpDefinitionDiagramEntry) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpDefinitionDiagramEntry));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long cpDefinitionDiagramEntryId =
							cpDefinitionDiagramEntry.
								getCPDefinitionDiagramEntryId();

						_log.warn(
							"Unable to index commerce product definition " +
								"diagram entry" + cpDefinitionDiagramEntryId,
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramEntryIndexer.class);

	@Reference
	private CPDefinitionDiagramEntryLocalService
		_cpDefinitionDiagramEntryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}