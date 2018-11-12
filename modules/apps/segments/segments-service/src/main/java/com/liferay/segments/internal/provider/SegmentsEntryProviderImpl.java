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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = SegmentsEntryProvider.class)
public class SegmentsEntryProviderImpl implements SegmentsEntryProvider {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ODataRetriever.class, "model.class.name");
	}

	@Override
	public void clearCache() {
		_portalCache.removeAll();
	}

	@Override
	public void clearCache(String className, long classPK) {
		_portalCache.remove(_getCacheKey(className, classPK));
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	public long[] getSegmentsEntryClassPKs(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			return new long[0];
		}

		if (Validator.isNotNull(segmentsEntry.getCriteria())) {
			ODataRetriever oDataRetriever = _serviceTrackerMap.getService(
				segmentsEntry.getType());

			if (oDataRetriever == null) {
				return new long[0];
			}

			List<BaseModel<?>> results = oDataRetriever.getResults(
				segmentsEntry.getCompanyId(), segmentsEntry.getCriteria(),
				Locale.getDefault(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Stream<BaseModel<?>> stream = results.stream();

			return stream.mapToLong(
				baseModel -> (Long)baseModel.getPrimaryKeyObj()
			).toArray();
		}
		else {
			List<SegmentsEntryRel> segmentsEntryRels =
				_segmentsEntryRelLocalService.getSegmentsEntryRels(
					segmentsEntryId);

			Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

			return stream.mapToLong(
				SegmentsEntryRel::getClassPK
			).toArray();
		}
	}

	@Override
	public long[] getSegmentsEntryIds(String className, long classPK)
		throws PortalException {

		String cacheKey = _getCacheKey(className, classPK);

		long[] segmentEntryIds = _portalCache.get(cacheKey);

		if (segmentEntryIds != null) {
			return segmentEntryIds;
		}

		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntries(
				true, className, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (segmentsEntries.isEmpty()) {
			return new long[0];
		}

		ODataRetriever oDataRetriever = _serviceTrackerMap.getService(
			className);

		List<SegmentsEntry> allSegmentsEntries = new ArrayList();

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			if (Validator.isNotNull(segmentsEntry.getCriteria()) &&
				(oDataRetriever != null)) {

				StringBundler sb = new StringBundler(5);

				sb.append("(");
				sb.append(segmentsEntry.getCriteria());
				sb.append(") and (classPK eq '");
				sb.append(classPK);
				sb.append("')");

				if (oDataRetriever.getResultsCount(
						segmentsEntry.getCompanyId(), sb.toString(),
						Locale.getDefault()) == 0) {

					continue;
				}
			}
			else if (!_segmentsEntryRelLocalService.hasSegmentsEntryRel(
						segmentsEntry.getSegmentsEntryId(),
						_portal.getClassNameId(className), classPK)) {

				continue;
			}

			allSegmentsEntries.add(segmentsEntry);
		}

		Stream<SegmentsEntry> stream = allSegmentsEntries.stream();

		segmentEntryIds = stream.mapToLong(
			SegmentsEntry::getSegmentsEntryId
		).toArray();

		_portalCache.put(cacheKey, segmentEntryIds);

		return segmentEntryIds;
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, long[]>)
				multiVMPool.getPortalCache(
					SegmentsEntryProvider.class.getName());
	}

	private String _getCacheKey(String className, long classPK) {
		return _portal.getClassNameId(className) + "_" + classPK;
	}

	private static PortalCache<String, long[]> _portalCache;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	private ServiceTrackerMap<String, ODataRetriever> _serviceTrackerMap;

}