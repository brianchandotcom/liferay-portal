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

package com.liferay.commerce.bom.internal.search;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMFolderLocalService;
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
@Component(immediate = true, service = Indexer.class)
public class CommerceBOMFolderIndexer extends BaseIndexer<CommerceBOMFolder> {

	public static final String CLASS_NAME = CommerceBOMFolder.class.getName();

	public static final String FIELD_PARENT_COMMERCE_BOM_FOLDER_ID =
		"parentCommerceBOMFolderId";

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long parentCommerceBOMFolderId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_PARENT_COMMERCE_BOM_FOLDER_ID));

		contextBooleanFilter.addRequiredTerm(
			FIELD_PARENT_COMMERCE_BOM_FOLDER_ID, parentCommerceBOMFolderId);
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
	protected void doDelete(CommerceBOMFolder commerceBOMFolder)
		throws Exception {

		deleteDocument(
			commerceBOMFolder.getCompanyId(),
			commerceBOMFolder.getCommerceBOMFolderId());
	}

	@Override
	protected Document doGetDocument(CommerceBOMFolder commerceBOMFolder)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing commerce BOM folder " + commerceBOMFolder);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceBOMFolder);

		document.addText(Field.NAME, commerceBOMFolder.getName());
		document.addNumber(
			FIELD_PARENT_COMMERCE_BOM_FOLDER_ID,
			commerceBOMFolder.getParentCommerceBOMFolderId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceBOMFolder + " indexed successfully");
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
	protected void doReindex(CommerceBOMFolder commerceBOMFolder)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceBOMFolder.getCompanyId(),
			getDocument(commerceBOMFolder), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_commerceBOMFolderLocalService.getCommerceBOMFolder(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceBOMFolders(companyId);
	}

	protected void reindexCommerceBOMFolders(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceBOMFolderLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceBOMFolder commerceBOMFolder) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceBOMFolder));
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce BOM folder " +
								commerceBOMFolder.getCommerceBOMFolderId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBOMFolderIndexer.class);

	@Reference
	private CommerceBOMFolderLocalService _commerceBOMFolderLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}