/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.context.Context;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.odata.matcher.ODataMatcher;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.provider.SegmentsEntryProvider;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"segments.entry.provider.order:Integer=200",
		"segments.entry.provider.source=" + SegmentsEntryConstants.SOURCE_REFERRED
	},
	service = SegmentsEntryProvider.class
)
public class ReferredSegmentsEntryProvider
	extends BaseSegmentsEntryProvider implements SegmentsEntryProvider {

	@Activate
	protected void activate(BundleContext bundleContext) {
		serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ODataRetriever<BaseModel<?>>>)(Class<?>)ODataRetriever.class,
			"model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		serviceTrackerMap.close();
	}

	@Override
	protected String getSource() {
		return SegmentsEntryConstants.SOURCE_REFERRED;
	}

	@Override
	protected boolean isMember(
		String className, long classPK, Context context,
		SegmentsEntry segmentsEntry, long[] segmentsEntryIds) {

		Criteria criteria = segmentsEntry.getCriteriaObj();

		if ((criteria == null) || MapUtil.isEmpty(criteria.getCriteria())) {
			return false;
		}

		Criteria.Conjunction referredConjunction = getConjunction(
			segmentsEntry, Criteria.Type.REFERRED);
		String referredFilterString = getFilterString(
			segmentsEntry, Criteria.Type.REFERRED);

		boolean member = super.isMember(
			className, classPK, context, segmentsEntry, segmentsEntryIds);

		if (ArrayUtil.isEmpty(segmentsEntryIds) ||
			Validator.isNull(referredFilterString) ||
			(member && referredConjunction.equals(Criteria.Conjunction.OR)) ||
			(!member && referredConjunction.equals(Criteria.Conjunction.AND))) {

			return member;
		}

		Map<String, String> segmentsEntryMap = HashMapBuilder.put(
			"segmentsEntryIds", StringUtil.merge(segmentsEntryIds, ",")
		).build();

		try {
			return _segmentsEntryODataMatcher.matches(
				referredFilterString, segmentsEntryMap);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return member;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReferredSegmentsEntryProvider.class);

	@Reference(
		target = "(target.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	private ODataMatcher<Map<String, String>> _segmentsEntryODataMatcher;

}