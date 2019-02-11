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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.util.JournalHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterSearcher;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content.properties",
	scope = ServiceScope.PROTOTYPE, service = StructuredContentResource.class
)
public class StructuredContentResourceImpl
	extends BaseStructuredContentResourceImpl {

	public static String encodeFilterAndSortIdentifier(
		DDMStructure ddmStructure, String name) {

		return StringBundler.concat(
			StringPool.UNDERLINE, ddmStructure.getStructureId(),
			StringPool.UNDERLINE, name);
	}

	@Override
	public Page<StructuredContent>
		getContentSpaceContentStructureStructuredContentsPage(
			Long contentSpaceId, Long contentStructureId, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Hits hits = _getHits(contentSpaceId, filter, pagination, sorts);

		return Page.of(
			transform(
				_journalHelper.getArticles(hits), this::_toStructuredContent),
			pagination, hits.getLength());
	}

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Hits hits = _getHits(contentSpaceId, filter, pagination, sorts);

		return Page.of(
			transform(
				_journalHelper.getArticles(hits), this::_toStructuredContent),
			pagination, hits.getLength());
	}

	public EntityModel getEntityModel(HttpServletRequest httpServletRequest)
		throws PortalException {

		String requestURI = httpServletRequest.getRequestURI();

		String[] parts = requestURI.split(StringPool.FORWARD_SLASH);

		Long contentStructureId = 0L;

		for (int i = 0; i < parts.length; i++) {
			if (Objects.equals(parts[i], "content-structure") &&
				((i + 1) < parts.length)) {

				contentStructureId = Long.valueOf(parts[i + 1]);

				break;
			}
		}

		List<EntityField> entityFields;

		if (contentStructureId > 0) {
			DDMStructure ddmStructure = _ddmStructureService.getStructure(
				contentStructureId);

			entityFields = _getEntityFields(ddmStructure);
		}
		else {
			entityFields = Collections.emptyList();
		}

		return new StructuredContentEntityModel(entityFields);
	}

	protected String encodeName(
		long ddmStructureId, String fieldName, Locale locale, String type) {

		return Field.getSortableFieldName(
			com.liferay.portal.kernel.util.StringBundler.concat(
				_ddmIndexer.encodeName(ddmStructureId, fieldName, locale),
				StringPool.UNDERLINE, type));
	}

	private Optional<EntityField> _createEntityField(
			DDMStructure ddmStructure, DDMFormField ddmFormField)
		throws PortalException {

		String indexType = ddmStructure.getFieldProperty(
			ddmFormField.getName(), "indexType");

		if (Validator.isNull(indexType)) {
			return Optional.empty();
		}

		if (Objects.equals(ddmFormField.getType(), DDMFormFieldType.CHECKBOX)) {
			return Optional.of(
				new BooleanEntityField(
					encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DATE)) {

			return Optional.of(
				new DateEntityField(
					encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String"),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String"),
					this::_getDDMDateFieldValue));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.DOUBLE) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.NUMBER)) {

			return Optional.of(
				new DoubleEntityField(
					encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "Number")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), FieldConstants.LONG) ||
				 Objects.equals(
					 ddmFormField.getDataType(), FieldConstants.INTEGER)) {

			return Optional.of(
				new IntegerEntityField(
					encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "Number")));
		}
		else if (Objects.equals(
					ddmFormField.getDataType(), DDMFormFieldType.RADIO) ||
				 (Objects.equals(
					 ddmFormField.getType(), DDMFormFieldType.TEXT) &&
				  Objects.equals(ddmFormField.getIndexType(), "keyword"))) {

			return Optional.of(
				new StringEntityField(
					encodeFilterAndSortIdentifier(
						ddmStructure, ddmFormField.getName()),
					locale -> encodeName(
						ddmStructure.getStructureId(), ddmFormField.getName(),
						locale, "String")));
		}

		return Optional.empty();
	}

	private SearchContext _createSearchContext(
		Long groupId, Pagination pagination,
		PermissionChecker permissionChecker, Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setCompanyId(company.getCompanyId());
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setSorts(sorts);
		searchContext.setStart(pagination.getStartPosition());
		searchContext.setUserId(permissionChecker.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	private String _getDDMDateFieldValue(Object fieldValue) {
		DateFormat indexDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		try {
			Date date = indexDateFormat.parse(String.valueOf(fieldValue));

			DateFormat searchDateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

			return searchDateFormat.format(date);
		}
		catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
	}

	private List<EntityField> _getEntityFields(DDMStructure ddmStructure)
		throws PortalException {

		List<EntityField> entityFields = new ArrayList<>();

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			Optional<EntityField> entityFieldOptional = _createEntityField(
				ddmStructure, ddmFormField);

			if (entityFieldOptional.isPresent()) {
				entityFields.add(entityFieldOptional.get());
			}
		}

		return entityFields;
	}

	private Hits _getHits(
			long groupId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		SearchContext searchContext = _createSearchContext(
			groupId, pagination, permissionChecker, sorts);

		Query query = _getQuery(filter, searchContext);

		SearchResultPermissionFilter searchResultPermissionFilter =
			_searchResultPermissionFilterFactory.create(
				new SearchResultPermissionFilterSearcher() {

					public Hits search(SearchContext searchContext)
						throws SearchException {

						return IndexSearcherHelperUtil.search(
							searchContext, query);
					}

				},
				permissionChecker);

		return searchResultPermissionFilter.search(searchContext);
	}

	private Query _getQuery(Filter filter, SearchContext searchContext)
		throws Exception {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		if (filter != null) {
			BooleanFilter booleanFilter = booleanQuery.getPreBooleanFilter();

			booleanFilter.add(filter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	private StructuredContent _toStructuredContent(
		JournalArticle journalArticle) {

		return new StructuredContent() {
			{
				setDateCreated(journalArticle.getCreateDate());
				setDateModified(journalArticle.getModifiedDate());
				setDatePublished(journalArticle.getDisplayDate());
				setDescription(
					journalArticle.getDescription(
						acceptLanguage.getPreferredLocale()));
				setId(journalArticle.getResourcePrimKey());
				setTitle(
					journalArticle.getTitle(
						acceptLanguage.getPreferredLocale()));
			}
		};
	}

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}