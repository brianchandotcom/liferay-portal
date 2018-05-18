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

package com.liferay.commerce.product.search;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionLocalService;
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
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = Indexer.class)
public class CPOptionIndexer extends BaseIndexer<CPOption> {

	public static final String CLASS_NAME = CPOption.class.getName();

	public static final String FIELD_KEY = "key";

	public static final String FIELD_OPTION_VALUE_NAME = "optionValueName";

	public CPOptionIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID, FIELD_KEY);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, FIELD_KEY, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_OPTION_VALUE_NAME, false);
		addSearchLocalizedTerm(
			searchQuery, searchContext, FIELD_OPTION_VALUE_NAME, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, false);
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
	protected void doDelete(CPOption cpOption) throws Exception {
		deleteDocument(cpOption.getCompanyId(), cpOption.getCPOptionId());
	}

	@Override
	protected Document doGetDocument(CPOption cpOption) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Indexing option " + cpOption);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpOption);

		List<CPOptionValue> cpOptionValues = cpOption.getCPOptionValues();

		String cpOptionDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(cpOption.getName());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			cpOption.getName());

		for (String languageId : languageIds) {
			String description = cpOption.getDescription(languageId);
			String name = cpOption.getName(languageId);
			List<String> cpOptionValueNamesList = new ArrayList<>();

			for (CPOptionValue cpOptionValue : cpOptionValues) {
				cpOptionValueNamesList.add(cpOptionValue.getName(languageId));
			}

			String[] cpOptionValueNames = cpOptionValueNamesList.toArray(
				new String[cpOptionValueNamesList.size()]);

			if (languageId.equals(cpOptionDefaultLanguageId)) {
				document.addText(Field.DESCRIPTION, description);
				document.addText(FIELD_OPTION_VALUE_NAME, cpOptionValueNames);
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}

			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				name);
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, languageId),
				description);

			document.addText(FIELD_KEY, cpOption.getKey());
			document.addText(Field.CONTENT, name);

			document.addText(
				LocalizationUtil.getLocalizedName(
					FIELD_OPTION_VALUE_NAME, languageId),
				cpOptionValueNames);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpOption + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.NAME, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPOption cpOption) throws Exception {
		Document document = getDocument(cpOption);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpOption.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPOption cpOption = _cpOptionLocalService.getCPOption(classPK);

		doReindex(cpOption);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPOptions(companyId);
	}

	protected void reindexCPOptions(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpOptionLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CPOption>() {

				@Override
				public void performAction(CPOption cpOption) {
					try {
						Document document = getDocument(cpOption);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce product option " +
									cpOption.getCPOptionId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionIndexer.class);

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}