/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = ModelPreFilterContributor.class
)
public class DDMFormInstanceRecordModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			booleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		long formInstanceId = GetterUtil.getLong(
			searchContext.getAttribute("formInstanceId"));

		if (formInstanceId > 0) {
			booleanFilter.addRequiredTerm("formInstanceId", formInstanceId);
		}

		String[] languageIds = GetterUtil.getStringValues(
			searchContext.getAttribute("languageIds"));
		String[] notEmptyFields = GetterUtil.getStringValues(
			searchContext.getAttribute("notEmptyFields"));
		long structureId = GetterUtil.getLong(
			searchContext.getAttribute("structureId"));

		if ((languageIds.length > 0) && (notEmptyFields.length > 0) &&
			(structureId > 0)) {

			List<Locale> locales = TransformUtil.transformToList(
				languageIds, LocaleUtil::fromLanguageId);

			for (String notEmptyField : notEmptyFields) {
				BooleanFilter notEmptyFieldBooleanFilter = new BooleanFilter();

				for (Locale locale : locales) {
					notEmptyFieldBooleanFilter.add(
						new ExistsFilter(
							ddmIndexer.encodeName(
								structureId, notEmptyField, locale)),
						BooleanClauseOccur.MUST);
				}

				booleanFilter.add(
					notEmptyFieldBooleanFilter, BooleanClauseOccur.MUST_NOT);
			}
		}

		_addSearchClassTypeIds(booleanFilter, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			try {
				QueryFilter queryFilter =
					ddmIndexer.createFieldValueQueryFilter(
						ddmStructureFieldName, ddmStructureFieldValue,
						searchContext.getLocale());

				booleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}
	}

	@Reference
	protected DDMIndexer ddmIndexer;

	private Filter _addSearchClassTypeIds(
		BooleanFilter contextBooleanFilter, SearchContext searchContext) {

		long[] classTypeIds = searchContext.getClassTypeIds();

		if (ArrayUtil.isEmpty(classTypeIds)) {
			return null;
		}

		TermsFilter classTypeIdsTermsFilter = new TermsFilter(
			Field.CLASS_TYPE_ID);

		classTypeIdsTermsFilter.addValues(
			ArrayUtil.toStringArray(classTypeIds));

		return contextBooleanFilter.add(
			classTypeIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordModelPreFilterContributor.class);

}