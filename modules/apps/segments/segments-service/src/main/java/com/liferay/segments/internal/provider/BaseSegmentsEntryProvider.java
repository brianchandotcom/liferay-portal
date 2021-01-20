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

package com.liferay.segments.internal.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.context.Context;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.odata.matcher.ODataMatcher;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
public abstract class BaseSegmentsEntryProvider
	implements SegmentsEntryProvider {

	@Override
	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			return new long[0];
		}

		String filterString = getFilterString(
			segmentsEntry, Criteria.Type.MODEL);

		if (Validator.isNull(filterString)) {
			List<SegmentsEntryRel> segmentsEntryRels =
				segmentsEntryRelLocalService.getSegmentsEntryRels(
					segmentsEntryId, start, end, null);

			Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

			return stream.mapToLong(
				SegmentsEntryRel::getClassPK
			).toArray();
		}

		ODataRetriever<BaseModel<?>> oDataRetriever =
			serviceTrackerMap.getService(segmentsEntry.getType());

		if (oDataRetriever == null) {
			return new long[0];
		}

		List<BaseModel<?>> results = oDataRetriever.getResults(
			segmentsEntry.getCompanyId(), filterString, LocaleUtil.getDefault(),
			start, end);

		Stream<BaseModel<?>> stream = results.stream();

		return stream.mapToLong(
			baseModel -> (Long)baseModel.getPrimaryKeyObj()
		).toArray();
	}

	@Override
	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			return 0;
		}

		String filterString = getFilterString(
			segmentsEntry, Criteria.Type.MODEL);

		if (Validator.isNull(filterString)) {
			return segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				segmentsEntryId);
		}

		ODataRetriever<BaseModel<?>> oDataRetriever =
			serviceTrackerMap.getService(segmentsEntry.getType());

		if (oDataRetriever == null) {
			return 0;
		}

		return oDataRetriever.getResultsCount(
			segmentsEntry.getCompanyId(), filterString,
			LocaleUtil.getDefault());
	}

	@Override
	public long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context)
		throws PortalException {

		return getSegmentsEntryIds(groupId, className, classPK, context, null);
	}

	@Override
	public long[] getSegmentsEntryIds(
		long groupId, String className, long classPK, Context context,
		long[] segmentsEntryIds) {

		List<SegmentsEntry> segmentsEntries =
			segmentsEntryLocalService.getSegmentsEntries(
				groupId, true, getSource(), className, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		if (segmentsEntries.isEmpty()) {
			return new long[0];
		}

		Stream<SegmentsEntry> stream = segmentsEntries.stream();

		return stream.filter(
			segmentsEntry -> isMember(
				className, classPK, context, segmentsEntry, segmentsEntryIds)
		).mapToLong(
			SegmentsEntry::getSegmentsEntryId
		).toArray();
	}

	protected Criteria.Conjunction getConjunction(
		SegmentsEntry segmentsEntry, Criteria.Type type) {

		Criteria existingCriteria = segmentsEntry.getCriteriaObj();

		if (existingCriteria == null) {
			return Criteria.Conjunction.AND;
		}

		return existingCriteria.getTypeConjunction(type);
	}

	protected String getFilterString(
		SegmentsEntry segmentsEntry, Criteria.Type type) {

		Criteria existingCriteria = segmentsEntry.getCriteriaObj();

		if (existingCriteria == null) {
			return null;
		}

		Criteria criteria = new Criteria();

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			segmentsCriteriaContributorRegistry.getSegmentsCriteriaContributors(
				segmentsEntry.getType());

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			Criteria.Criterion criterion =
				segmentsCriteriaContributor.getCriterion(existingCriteria);

			if (criterion == null) {
				continue;
			}

			segmentsCriteriaContributor.contribute(
				criteria, criterion.getFilterString(),
				Criteria.Conjunction.parse(criterion.getConjunction()));
		}

		return criteria.getFilterString(type);
	}

	protected abstract String getSource();

	protected boolean isMember(
		String className, long classPK, Context context,
		SegmentsEntry segmentsEntry, long[] segmentsEntryIds) {

		String contextFilterString = getFilterString(
			segmentsEntry, Criteria.Type.CONTEXT);

		if (segmentsEntryRelLocalService.hasSegmentsEntryRel(
				segmentsEntry.getSegmentsEntryId(),
				portal.getClassNameId(className), classPK) &&
			Validator.isNull(contextFilterString)) {

			return true;
		}

		Criteria criteria = segmentsEntry.getCriteriaObj();

		if ((criteria == null) || MapUtil.isEmpty(criteria.getCriteria())) {
			return false;
		}

		Criteria.Conjunction contextConjunction = getConjunction(
			segmentsEntry, Criteria.Type.CONTEXT);

		if (context != null) {
			boolean matchesContext = false;

			if (Validator.isNotNull(contextFilterString)) {
				try {
					matchesContext = oDataMatcher.matches(
						contextFilterString, context);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}

				if (matchesContext &&
					contextConjunction.equals(Criteria.Conjunction.OR)) {

					return true;
				}

				if (!matchesContext &&
					contextConjunction.equals(Criteria.Conjunction.AND)) {

					return false;
				}
			}

			if (!GetterUtil.getBoolean(context.get(Context.SIGNED_IN))) {
				return matchesContext;
			}
		}

		Criteria.Conjunction modelConjunction = getConjunction(
			segmentsEntry, Criteria.Type.MODEL);
		ODataRetriever<BaseModel<?>> oDataRetriever =
			serviceTrackerMap.getService(className);
		String modelFilterString = getFilterString(
			segmentsEntry, Criteria.Type.MODEL);

		if (Validator.isNotNull(modelFilterString) &&
			(oDataRetriever != null)) {

			StringBundler sb = new StringBundler(5);

			sb.append("(");
			sb.append(modelFilterString);
			sb.append(") and (classPK eq '");
			sb.append(classPK);
			sb.append("')");

			boolean matchesModel = false;

			try {
				int count = oDataRetriever.getResultsCount(
					segmentsEntry.getCompanyId(), sb.toString(),
					LocaleUtil.getDefault());

				if (count > 0) {
					matchesModel = true;
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}

			if (matchesModel &&
				modelConjunction.equals(Criteria.Conjunction.OR)) {

				return true;
			}

			if (!matchesModel &&
				modelConjunction.equals(Criteria.Conjunction.AND)) {

				return false;
			}
		}

		return true;
	}

	@Reference(
		target = "(target.class.name=com.liferay.segments.context.Context)"
	)
	protected ODataMatcher<Context> oDataMatcher;

	@Reference
	protected Portal portal;

	@Reference
	protected SegmentsCriteriaContributorRegistry
		segmentsCriteriaContributorRegistry;

	@Reference
	protected SegmentsEntryLocalService segmentsEntryLocalService;

	@Reference
	protected SegmentsEntryRelLocalService segmentsEntryRelLocalService;

	protected ServiceTrackerMap<String, ODataRetriever<BaseModel<?>>>
		serviceTrackerMap;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSegmentsEntryProvider.class);

}