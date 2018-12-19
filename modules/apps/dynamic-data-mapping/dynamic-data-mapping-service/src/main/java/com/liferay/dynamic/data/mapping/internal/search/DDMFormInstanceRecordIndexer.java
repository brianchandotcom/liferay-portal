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

package com.liferay.dynamic.data.mapping.internal.search;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Leonardo Barros
 * @deprecated As of Judson (7.1.x), since 7.1.0
 */
@Deprecated
public class DDMFormInstanceRecordIndexer
	extends BaseIndexer<DDMFormInstanceRecord> {

	public static final String CLASS_NAME =
		DDMFormInstanceRecord.class.getName();

	public DDMFormInstanceRecordIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.DESCRIPTION, Field.TITLE);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public BooleanFilter getFacetBooleanFilter(
			String className, SearchContext searchContext)
		throws Exception {

		BooleanFilter facetBooleanFilter = new BooleanFilter();

		facetBooleanFilter.addTerm(
			Field.ENTRY_CLASS_NAME, DDMFormInstanceRecord.class.getName());

		if (searchContext.getUserId() > 0) {
			facetBooleanFilter =
				searchPermissionChecker.getPermissionBooleanFilter(
					searchContext.getCompanyId(), searchContext.getGroupIds(),
					searchContext.getUserId(), DDMFormInstance.class.getName(),
					facetBooleanFilter, searchContext);
		}

		return facetBooleanFilter;
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

		long ddmFormInstanceId = GetterUtil.getLong(
			searchContext.getAttribute("ddmFormInstanceId"));

		if (ddmFormInstanceId > 0) {
			contextBooleanFilter.addRequiredTerm(
				"ddmFormInstanceId", ddmFormInstanceId);
		}

		addSearchClassTypeIds(contextBooleanFilter, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			QueryFilter queryFilter = ddmIndexer.createFieldValueQueryFilter(
				ddmStructureFieldName, ddmStructureFieldValue,
				searchContext.getLocale());

			contextBooleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		addContentSearchTerm(searchQuery, searchContext);
	}

	protected void addContent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			DDMFormValues ddmFormValues, Document document)
		throws Exception {

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		for (Locale locale : locales) {
			StringBundler sb = new StringBundler(3);

			sb.append("ddmContent");
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));

			document.addText(
				sb.toString(),
				extractContent(ddmFormInstanceRecordVersion, locale));
		}
	}

	protected void addContentSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		Locale locale = searchContext.getLocale();

		StringBundler sb = new StringBundler(3);

		sb.append("ddmContent");
		sb.append(StringPool.UNDERLINE);
		sb.append(LocaleUtil.toLanguageId(locale));

		addSearchTerm(searchQuery, searchContext, sb.toString(), false);
	}

	@Override
	protected void doDelete(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws Exception {

		deleteDocument(
			ddmFormInstanceRecord.getCompanyId(),
			ddmFormInstanceRecord.getFormInstanceRecordId());
	}

	@Override
	protected Document doGetDocument(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws Exception {

		Document document = getBaseModelDocument(
			CLASS_NAME, ddmFormInstanceRecord);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getFormInstanceRecordVersion();

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecordVersion.getFormInstance();

		document.addKeyword(
			Field.CLASS_NAME_ID,
			classNameLocalService.getClassNameId(DDMFormInstance.class));
		document.addKeyword(
			Field.CLASS_PK, ddmFormInstance.getFormInstanceId());
		document.addKeyword(
			Field.CLASS_TYPE_ID,
			ddmFormInstanceRecordVersion.getFormInstanceId());
		document.addKeyword(Field.RELATED_ENTRY, true);
		document.addKeyword(
			Field.STATUS, ddmFormInstanceRecordVersion.getStatus());
		document.addKeyword(
			Field.VERSION, ddmFormInstanceRecordVersion.getVersion());

		document.addKeyword(
			"formInstanceId", ddmFormInstance.getFormInstanceId());

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMFormValues ddmFormValues =
			ddmFormInstanceRecordVersion.getDDMFormValues();

		addContent(ddmFormInstanceRecordVersion, ddmFormValues, document);

		ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long ddmFormInstanceId = GetterUtil.getLong(
			document.get("formInstanceId"));

		String title = getTitle(ddmFormInstanceId, locale);

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);
		summary.setTitle(title);

		return summary;
	}

	@Override
	protected void doReindex(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws Exception {

		Document document = getDocument(ddmFormInstanceRecord);

		indexWriterHelper.updateDocument(
			getSearchEngineId(), ddmFormInstanceRecord.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordLocalService.getFormInstanceRecord(classPK);

		doReindex(ddmFormInstanceRecord);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFormInstanceRecords(companyId);
	}

	protected String extractContent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			Locale locale)
		throws Exception {

		DDMFormValues ddmFormValues =
			ddmFormInstanceRecordVersion.getDDMFormValues();

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecordVersion.getFormInstance();

		return ddmIndexer.extractIndexableAttributes(
			ddmFormInstance.getStructure(), ddmFormValues, locale);
	}

	protected ResourceBundle getResourceBundle(Locale defaultLocale) {
		return PortalUtil.getResourceBundle(defaultLocale);
	}

	protected String getTitle(long ddmFormInstanceId, Locale locale) {
		try {
			DDMFormInstance ddmFormInstance =
				ddmFormInstanceLocalService.getFormInstance(ddmFormInstanceId);

			String ddmFormInstanceName = ddmFormInstance.getName(locale);

			return LanguageUtil.format(
				getResourceBundle(locale), "new-entry-for-form-x",
				ddmFormInstanceName, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	protected void reindexFormInstanceRecords(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			ddmFormInstanceRecordLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmFormInstanceRecordIdProperty =
					PropertyFactoryUtil.forName("formInstanceRecordId");

				DynamicQuery ddmFormInstanceRecordVersionDynamicQuery =
					ddmFormInstanceRecordVersionLocalService.dynamicQuery();

				ddmFormInstanceRecordVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("formInstanceRecordId"));

				dynamicQuery.add(
					ddmFormInstanceRecordIdProperty.in(
						ddmFormInstanceRecordVersionDynamicQuery));

				Property ddmFormInstanceProperty = PropertyFactoryUtil.forName(
					"formInstanceId");

				DynamicQuery ddmFormInstanceDynamicQuery =
					ddmFormInstanceLocalService.dynamicQuery();

				ddmFormInstanceDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("formInstanceId"));

				dynamicQuery.add(
					ddmFormInstanceProperty.in(ddmFormInstanceDynamicQuery));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(DDMFormInstanceRecord ddmFormInstanceRecord) -> {
				try {
					Document document = getDocument(ddmFormInstanceRecord);

					if (document != null) {
						indexableActionableDynamicQuery.addDocuments(document);
					}
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index form instance record " +
								ddmFormInstanceRecord.getFormInstanceRecordId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	protected ClassNameLocalService classNameLocalService;
	protected DDMFormInstanceLocalService ddmFormInstanceLocalService;
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;
	protected DDMFormInstanceRecordVersionLocalService
		ddmFormInstanceRecordVersionLocalService;
	protected DDMIndexer ddmIndexer;
	protected IndexWriterHelper indexWriterHelper;
	protected SearchPermissionChecker searchPermissionChecker;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordIndexer.class);

}