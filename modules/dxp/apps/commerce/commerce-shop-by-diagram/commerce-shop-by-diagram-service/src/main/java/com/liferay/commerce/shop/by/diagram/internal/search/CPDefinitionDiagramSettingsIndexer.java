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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsLocalService;
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
public class CPDefinitionDiagramSettingsIndexer
	extends BaseIndexer<CPDefinitionDiagramSettings> {

	public static final String CLASS_NAME =
		CPDefinitionDiagramSettings.class.getName();

	public static final String FIELD_ASSET_CATEGORY_ID = "assetCategoryId";

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long assetCategoryId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_ASSET_CATEGORY_ID));

		contextBooleanFilter.addRequiredTerm(
			FIELD_ASSET_CATEGORY_ID, assetCategoryId);
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
	protected void doDelete(
			CPDefinitionDiagramSettings cpDefinitionDiagramSettings)
		throws Exception {

		deleteDocument(
			cpDefinitionDiagramSettings.getCompanyId(),
			cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId());
	}

	@Override
	protected Document doGetDocument(
			CPDefinitionDiagramSettings cpDefinitionDiagramSettings)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing commerce product definition diagram settings " +
					cpDefinitionDiagramSettings);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, cpDefinitionDiagramSettings);

		document.addText(Field.NAME, cpDefinitionDiagramSettings.getName());
		document.addNumber(
			FIELD_ASSET_CATEGORY_ID,
			cpDefinitionDiagramSettings.getAssetCategoryId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + cpDefinitionDiagramSettings +
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
	protected void doReindex(
			CPDefinitionDiagramSettings cpDefinitionDiagramSettings)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpDefinitionDiagramSettings.getCompanyId(),
			getDocument(cpDefinitionDiagramSettings), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPDefinitionDiagramSettings(companyId);
	}

	protected void reindexCPDefinitionDiagramSettings(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpDefinitionDiagramSettingsLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPDefinitionDiagramSettings cpDefinitionDiagramSettings) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpDefinitionDiagramSettings));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long cpDefinitionDiagramSettingsId =
							cpDefinitionDiagramSettings.
								getCPDefinitionDiagramSettingsId();

						_log.warn(
							"Unable to index commerce product definition " +
								"diagram settings" +
									cpDefinitionDiagramSettingsId,
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramSettingsIndexer.class);

	@Reference
	private CPDefinitionDiagramSettingsLocalService
		_cpDefinitionDiagramSettingsLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}