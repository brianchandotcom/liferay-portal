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

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;

import java.io.PrintWriter;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=/segments/get_segments_entry_class_pks_count"
	},
	service = MVCResourceCommand.class
)
public class GetSegmentsEntryClassPKsCountMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			PrintWriter printWriter = resourceResponse.getWriter();

			printWriter.write(_getText(resourceRequest, resourceResponse));

			return false;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ODataRetriever<?>>)(Class<?>)ODataRetriever.class,
			"model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private int _getSegmentsEntryClassPKsCount(
		long companyId, Criteria criteria, String type, Locale locale) {

		ODataRetriever<?> oDataRetriever = _serviceTrackerMap.getService(type);

		if (oDataRetriever == null) {
			return 0;
		}

		try {
			return oDataRetriever.getResultsCount(
				companyId, criteria.getFilterString(Criteria.Type.MODEL),
				locale);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to obtain the segment user count", portalException);
			}

			return 0;
		}
	}

	private String _getText(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(resourceRequest));

		long companyId = _portal.getCompanyId(httpServletRequest);

		String type = ParamUtil.getString(resourceRequest, "type");

		Criteria criteria = ActionUtil.getCriteria(
			resourceRequest,
			_segmentsCriteriaContributorRegistry.
				getSegmentsCriteriaContributors(type));

		_saveCriteriaInSession(resourceRequest, criteria);

		int count = _getSegmentsEntryClassPKsCount(
			companyId, criteria, type, _portal.getLocale(resourceRequest));

		return String.valueOf(count);
	}

	private void _saveCriteriaInSession(
		ResourceRequest resourceRequest, Criteria criteria) {

		PortletSession portletSession = resourceRequest.getPortletSession();

		portletSession.setAttribute(
			SegmentsWebKeys.PREVIEW_SEGMENTS_ENTRY_CRITERIA, criteria);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSegmentsEntryClassPKsCountMVCResourceCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;

	@Reference
	private SegmentsEntryService _segmentsEntryService;

	private ServiceTrackerMap<String, ODataRetriever<?>> _serviceTrackerMap;

}