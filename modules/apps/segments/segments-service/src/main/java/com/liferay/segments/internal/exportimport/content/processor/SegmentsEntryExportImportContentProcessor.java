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

package com.liferay.segments.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsEntry",
	service = {
		ExportImportContentProcessor.class,
		SegmentsEntryExportImportContentProcessor.class
	}
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

		return _replaceExportCriteriaReferences(
			portletDataContext, stagedModel, criteria);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

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

	@Reference
	private EntityModelFieldMapper _entityModelFieldMapper;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;

	@Reference
	private SegmentsFieldCustomizerRegistry _segmentsFieldCustomizerRegistry;

}