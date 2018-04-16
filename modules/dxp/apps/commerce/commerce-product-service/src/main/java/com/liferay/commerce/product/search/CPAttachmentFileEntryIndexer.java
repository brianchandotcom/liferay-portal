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

package com.liferay.commerce.product.search;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = Indexer.class)
public class CPAttachmentFileEntryIndexer
	extends BaseIndexer<CPAttachmentFileEntry> {

	public static final String CLASS_NAME =
		CPAttachmentFileEntry.class.getName();

	public static final String FIELD_DISPLAY_DATE = "displayDate";

	public static final String FIELD_FILE_ENTRY_ID = "fileEntryId";

	public static final String FIELD_RELATED_ENTITY_CLASS_NAME_ID =
		"relatedEntityClassNameId";

	public static final String FIELD_RELATED_ENTITY_CLASS_PK =
		"relatedEntityClassPK";

	public CPAttachmentFileEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			FIELD_RELATED_ENTITY_CLASS_NAME_ID, FIELD_RELATED_ENTITY_CLASS_PK,
			FIELD_RELATED_ENTITY_CLASS_PK, FIELD_FILE_ENTRY_ID);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextBooleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		long classNameId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_RELATED_ENTITY_CLASS_NAME_ID));

		int type = GetterUtil.getInteger(
			searchContext.getAttribute(Field.TYPE), -1);

		if (type >= 0) {
			contextBooleanFilter.addRequiredTerm(Field.TYPE, type);
		}

		if (classNameId > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_RELATED_ENTITY_CLASS_NAME_ID, classNameId);
		}

		long classPK = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_RELATED_ENTITY_CLASS_PK));

		if (classPK > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_RELATED_ENTITY_CLASS_PK, classPK);
		}

		String[] fieldNames = (String[])searchContext.getAttribute("OPTIONS");

		if (fieldNames == null) {
			return;
		}

		for (String fieldName : fieldNames) {
			String[] fieldValues = (String[])searchContext.getAttribute(
				fieldName);

			TermsFilter termsFilter = new TermsFilter(fieldName);

			termsFilter.addValues(fieldValues);

			Filter existFilter = new ExistsFilter(fieldName);

			BooleanFilter existBooleanFilter = new BooleanFilter();

			existBooleanFilter.add(existFilter, BooleanClauseOccur.MUST_NOT);

			BooleanFilter fieldBooleanFilter = new BooleanFilter();

			fieldBooleanFilter.add(
				existBooleanFilter, BooleanClauseOccur.SHOULD);
			fieldBooleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);

			contextBooleanFilter.add(
				fieldBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(
			searchQuery, searchContext, Field.CONTENT, false);
		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_RELATED_ENTITY_CLASS_NAME_ID,
			false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_RELATED_ENTITY_CLASS_PK, false);
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
	protected void doDelete(CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		deleteDocument(
			cpAttachmentFileEntry.getCompanyId(),
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	@Override
	protected Document doGetDocument(
			CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing attachment file entry " + cpAttachmentFileEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, cpAttachmentFileEntry);

		document.addText(Field.CONTENT, StringPool.BLANK);

		document.addNumber(Field.PRIORITY, cpAttachmentFileEntry.getPriority());

		document.addNumber(Field.TYPE, cpAttachmentFileEntry.getType());
		document.addDateSortable(
			FIELD_DISPLAY_DATE, cpAttachmentFileEntry.getDisplayDate());

		document.addNumber(
			FIELD_RELATED_ENTITY_CLASS_NAME_ID,
			cpAttachmentFileEntry.getClassNameId());
		document.addNumber(
			FIELD_RELATED_ENTITY_CLASS_PK, cpAttachmentFileEntry.getClassPK());
		document.addNumber(
			FIELD_FILE_ENTRY_ID, cpAttachmentFileEntry.getFileEntryId());

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap =
				_cpInstanceHelper.getCPDefinitionOptionRelsMap(
					cpAttachmentFileEntry.getJson());

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelListMapEntry :
					cpDefinitionOptionRelListMap.entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelListMapEntry.getKey();

			CPOption cpOption = cpDefinitionOptionRel.getCPOption();

			List<String> optionValueNames = new ArrayList<>();
			List<Long> optionValueIds = new ArrayList<>();

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionRelListMapEntry.getValue()) {

				optionValueNames.add(
					StringUtil.toLowerCase(
						cpDefinitionOptionValueRel.getKey()));
				optionValueIds.add(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId());
			}

			document.addText(
				"ATTRIBUTE_" + cpOption.getKey() +
					"_VALUES_NAMES",
				ArrayUtil.toStringArray(optionValueNames));
			document.addNumber(
				"ATTRIBUTE_" + cpOption.getKey() +
					"_VALUES_IDS",
				ArrayUtil.toLongArray(optionValueIds));

			document.addText(
				"ATTRIBUTE_" +
					cpDefinitionOptionRel.getCPDefinitionOptionRelId() +
						"_VALUES_NAMES",
				ArrayUtil.toStringArray(optionValueNames));
			document.addNumber(
				"ATTRIBUTE_" +
					cpDefinitionOptionRel.getCPDefinitionOptionRelId() +
						"_VALUES_IDS",
				ArrayUtil.toLongArray(optionValueIds));
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + cpAttachmentFileEntry + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, FIELD_RELATED_ENTITY_CLASS_NAME_ID,
			FIELD_RELATED_ENTITY_CLASS_PK);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPAttachmentFileEntry cpAttachmentFileEntry)
		throws Exception {

		Document document = getDocument(cpAttachmentFileEntry);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpAttachmentFileEntry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntry(
				classPK);

		doReindex(cpAttachmentFileEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPAttachmentFileEntries(companyId);
	}

	protected void reindexCPAttachmentFileEntries(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpAttachmentFileEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CPAttachmentFileEntry>() {

				@Override
				public void performAction(
					CPAttachmentFileEntry cpAttachmentFileEntry) {

					try {
						Document document = getDocument(cpAttachmentFileEntry);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce product attachment" +
									"file entry " +
										cpAttachmentFileEntry.
											getCPAttachmentFileEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPAttachmentFileEntryIndexer.class);

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}