/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.exportimport.content.processor;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizerRegistry;
import com.liferay.segments.internal.odata.entity.EntityModelFieldMapper;
import com.liferay.segments.internal.odata.filter.expression.ExportExpressionVisitorImpl;
import com.liferay.segments.internal.odata.filter.expression.ImportExpressionVisitorImpl;
import com.liferay.segments.model.SegmentsEntry;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsEntry",
	service = ExportImportContentProcessor.class
)
public class SegmentsEntryExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		Criteria criteria = CriteriaSerializer.deserialize(content);

		content = _replaceExportCriteriaReferences(
			portletDataContext, stagedModel, criteria);

		return _replaceExportExpandoColumnReferences(content);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		content = _replaceImportExpandoColumnReferences(
			portletDataContext.getCompanyId(), content);

		Criteria criteria = CriteriaSerializer.deserialize(content);

		return _replaceImportSegmentsEntryReferences(
			portletDataContext, stagedModel, criteria);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {
	}

	private String _replaceExportCriteriaReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Criteria criteria)
		throws Exception {

		SegmentsEntry segmentsEntry = (SegmentsEntry)stagedModel;

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			_segmentsCriteriaContributorRegistry.
				getSegmentsCriteriaContributors(segmentsEntry.getType());

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			Criteria.Criterion criterion =
				segmentsCriteriaContributor.getCriterion(criteria);

			if (criterion == null) {
				continue;
			}

			FilterParser filterParser = _filterParserProvider.provide(
				segmentsCriteriaContributor.getEntityModel());

			Filter filter = new Filter(
				filterParser.parse(criterion.getFilterString()));

			Expression expression = filter.getExpression();

			expression.accept(
				new ExportExpressionVisitorImpl(
					portletDataContext, stagedModel,
					segmentsCriteriaContributor.getEntityModel(),
					_entityModelFieldMapper, _segmentsFieldCustomizerRegistry));
		}

		return CriteriaSerializer.serialize(criteria);
	}

	private String _replaceExportExpandoColumnReferences(String content) {
		Matcher matcher = _exportCustomFieldPattern.matcher(content);

		StringBuffer sb = null;

		while (matcher.find()) {
			if (sb == null) {
				sb = new StringBuffer(content.length());
			}

			String expandoColumnId = matcher.group(1);

			ExpandoColumn expandoColumn =
				_expandoColumnLocalService.fetchExpandoColumn(
					GetterUtil.getLong(expandoColumnId));

			if (expandoColumn == null) {
				continue;
			}

			StagedExpandoColumn stagedExpandoColumn = ModelAdapterUtil.adapt(
				expandoColumn, ExpandoColumn.class, StagedExpandoColumn.class);

			String uuid = stagedExpandoColumn.getUuid();

			matcher.appendReplacement(
				sb,
				Matcher.quoteReplacement(
					"customField\\/_" + uuid.replaceAll(" ", "_")));
		}

		if (sb != null) {
			matcher.appendTail(sb);

			content = sb.toString();
		}

		return content;
	}

	private String _replaceImportExpandoColumnReferences(
		long companyId, String content) {

		Matcher matcher = _importCustomFieldPattern.matcher(content);

		StringBuffer sb = null;

		while (matcher.find()) {
			if (sb == null) {
				sb = new StringBuffer(content.length());
			}

			String className = matcher.group(1);
			String name = matcher.group(3);
			String tableName = matcher.group(2);

			ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
				companyId, className, tableName, name);

			if (expandoColumn == null) {
				expandoColumn = _expandoColumnLocalService.getColumn(
					companyId, className, tableName, name.replaceAll("_", " "));
			}

			if (expandoColumn == null) {
				continue;
			}

			String fieldName =
				_entityModelFieldMapper.getExpandoColumnEntityFieldName(
					expandoColumn);

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement("customField\\/" + fieldName));
		}

		if (sb != null) {
			matcher.appendTail(sb);

			content = sb.toString();
		}

		return content;
	}

	private String _replaceImportSegmentsEntryReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Criteria criteria)
		throws Exception {

		SegmentsEntry segmentsEntry = (SegmentsEntry)stagedModel;

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			_segmentsCriteriaContributorRegistry.
				getSegmentsCriteriaContributors(segmentsEntry.getType());

		Criteria importCriteria = new Criteria();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			Criteria.Criterion criterion =
				segmentsCriteriaContributor.getCriterion(criteria);

			if (criterion == null) {
				continue;
			}

			FilterParser filterParser = _filterParserProvider.provide(
				segmentsCriteriaContributor.getEntityModel());

			Filter filter = new Filter(
				filterParser.parse(criterion.getFilterString()));

			Expression expression = filter.getExpression();

			String filterString = (String)expression.accept(
				new ImportExpressionVisitorImpl(
					portletDataContext,
					segmentsCriteriaContributor.getEntityModel(),
					_entityModelFieldMapper, criterion.getFilterString(),
					_segmentsFieldCustomizerRegistry));

			segmentsCriteriaContributor.contribute(
				importCriteria, filterString,
				Criteria.Conjunction.parse(criterion.getConjunction()));
		}

		return CriteriaSerializer.serialize(importCriteria);
	}

	private static final Pattern _exportCustomFieldPattern = Pattern.compile(
		"customField\\\\/_(\\d+)_[^ ]+");
	private static final Pattern _importCustomFieldPattern = Pattern.compile(
		"customField\\\\/_([\\w\\.]+)#([\\w]+)#([^ ]+)");

	@Reference
	private EntityModelFieldMapper _entityModelFieldMapper;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;

	@Reference
	private SegmentsFieldCustomizerRegistry _segmentsFieldCustomizerRegistry;

}