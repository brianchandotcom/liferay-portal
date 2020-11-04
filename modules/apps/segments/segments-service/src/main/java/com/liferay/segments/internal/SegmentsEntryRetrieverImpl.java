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

package com.liferay.segments.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.Context;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.simulator.SegmentsEntrySimulator;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Cristina González
 */
@Component(service = SegmentsEntryRetriever.class)
public class SegmentsEntryRetrieverImpl implements SegmentsEntryRetriever {

	@Override
	public long[] getSegmentsEntryIds(
		long groupId, long userId, Context context) {

		Optional<long[]> segmentsEntryIdsOptional =
			_getSegmentsEntryIdsOptional();

		long[] segmentsEntryIds = ArrayUtil.append(
			segmentsEntryIdsOptional.orElseGet(
				() -> {
					if ((_segmentsEntrySimulator != null) &&
						_segmentsEntrySimulator.isSimulationActive(userId)) {

						return _segmentsEntrySimulator.
							getSimulatedSegmentsEntryIds(userId);
					}

					try {
						return _segmentsEntryProviderRegistry.
							getSegmentsEntryIds(
								groupId, User.class.getName(), userId, context);
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException.getMessage());
						}

						return new long[0];
					}
				}),
			SegmentsEntryConstants.ID_DEFAULT);

		Optional<HttpServletRequest> httpServletRequestOptional =
			_getHttpServletRequestOptional();

		httpServletRequestOptional.ifPresent(
			httpServletRequest -> httpServletRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_ENTRY_IDS, segmentsEntryIds));

		return segmentsEntryIds;
	}

	private Optional<HttpServletRequest> _getHttpServletRequestOptional() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(serviceContext.getRequest());
	}

	private Optional<long[]> _getSegmentsEntryIdsOptional() {
		Optional<HttpServletRequest> httpServletRequestOptional =
			_getHttpServletRequestOptional();

		return Optional.ofNullable(
			httpServletRequestOptional.map(
				httpServletRequest -> (long[])httpServletRequest.getAttribute(
					SegmentsWebKeys.SEGMENTS_ENTRY_IDS)
			).orElse(
				null
			));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryRetrieverImpl.class);

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private volatile SegmentsEntrySimulator _segmentsEntrySimulator;

}