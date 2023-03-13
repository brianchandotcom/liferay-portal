/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.service;

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.expression.IndividualSegmentsExpressionVisitorImpl;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionLexer;
import com.liferay.segments.asah.connector.internal.expression.parser.IndividualSegmentsExpressionParser;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryLocalServiceWrapper;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = ServiceWrapper.class)
public class AsahSegmentsEntryLocalServiceWrapper
	extends SegmentsEntryLocalServiceWrapper {

	@Override
	public SegmentsEntry recalculateSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry = super.recalculateSegmentsEntry(
			segmentsEntryId);

		if (!FeatureFlagManagerUtil.isEnabled("LPS-172194") ||
			!_isAnalyticsEnabled(segmentsEntry.getCompanyId()) ||
			!SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND.equals(
				segmentsEntry.getSource())) {

			return segmentsEntry;
		}

		IndividualSegment individualSegment =
			_asahFaroBackendClient.getIndividualSegment(
				segmentsEntry.getCompanyId(),
				segmentsEntry.getSegmentsEntryKey());

		ServiceContext serviceContext = _getServiceContext(
			segmentsEntry.getCompanyId());

		Map<Locale, String> nameMap = Collections.singletonMap(
			_portal.getSiteDefaultLocale(serviceContext.getScopeGroupId()),
			individualSegment.getName());

		IndividualSegmentsExpressionParser individualSegmentsExpressionParser =
			new IndividualSegmentsExpressionParser(
				new CommonTokenStream(
					new IndividualSegmentsExpressionLexer(
						new ANTLRInputStream(individualSegment.getFilter()))));

		IndividualSegmentsExpressionParser.ExpressionContext expressionContext =
			individualSegmentsExpressionParser.expression();

		Criteria criteria = expressionContext.accept(
			new IndividualSegmentsExpressionVisitorImpl());

		return super.updateSegmentsEntry(
			segmentsEntry.getSegmentsEntryId(), individualSegment.getId(),
			nameMap, null, true, _serialize(criteria), serviceContext);
	}

	@Override
	public SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, String segmentsEntryKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, String criteria, ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);

		if (FeatureFlagManagerUtil.isEnabled("LPS-172194") &&
			_isAnalyticsEnabled(segmentsEntry.getCompanyId()) &&
			SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND.equals(
				segmentsEntry.getSource())) {

			IndividualSegment individualSegment =
				_asahFaroBackendClient.getIndividualSegment(
					segmentsEntry.getCompanyId(),
					segmentsEntry.getSegmentsEntryKey());

			individualSegment.setFilter(criteria);
			individualSegment.setName(
				nameMap.get(
					_portal.getSiteDefaultLocale(
						serviceContext.getScopeGroupId())));

			_asahFaroBackendClient.updateIndividualSegment(
				segmentsEntry.getCompanyId(), individualSegment);
		}

		return super.updateSegmentsEntry(
			segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap, active,
			criteria, serviceContext);
	}

	@Activate
	protected void activate() {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_analyticsSettingsManager, _http);
	}

	@Deactivate
	protected void deactivate() {
		_asahFaroBackendClient = null;
	}

	private ServiceContext _getServiceContext(long companyId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		Company company = _companyLocalService.getCompany(companyId);

		serviceContext.setScopeGroupId(company.getGroupId());

		User user = company.getDefaultUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private boolean _isAnalyticsEnabled(long companyId) {
		try {
			return _analyticsSettingsManager.isAnalyticsEnabled(companyId);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	private String _serialize(Criteria criteria) {
		if (criteria == null) {
			return null;
		}

		return CriteriaSerializer.serialize(criteria);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahSegmentsEntryLocalServiceWrapper.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}