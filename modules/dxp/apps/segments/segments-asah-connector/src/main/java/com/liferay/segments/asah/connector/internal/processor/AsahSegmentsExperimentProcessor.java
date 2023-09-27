/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.processor;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.util.DXPVariantUtil;
import com.liferay.segments.asah.connector.internal.client.model.util.ExperimentUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
public class AsahSegmentsExperimentProcessor {

	public AsahSegmentsExperimentProcessor(
		AnalyticsSettingsManager analyticsSettingsManager,
		AsahFaroBackendClient asahFaroBackendClient,
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService, Portal portal,
		SegmentsEntryLocalService segmentsEntryLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_analyticsSettingsManager = analyticsSettingsManager;
		_asahFaroBackendClient = asahFaroBackendClient;
		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_portal = portal;
		_segmentsEntryLocalService = segmentsEntryLocalService;
		_segmentsExperienceLocalService = segmentsExperienceLocalService;
	}

	public void processAddSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws Exception {

		if ((segmentsExperiment == null) ||
			!_analyticsSettingsManager.isAnalyticsEnabled(
				segmentsExperiment.getCompanyId())) {

			return;
		}

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				segmentsExperiment.getCompanyId());

		Experiment experiment = _asahFaroBackendClient.addExperiment(
			segmentsExperiment.getCompanyId(),
			ExperimentUtil.toExperiment(
				_companyLocalService,
				analyticsConfiguration.liferayAnalyticsDataSourceId(),
				_groupLocalService, _layoutLocalService,
				LocaleUtil.getSiteDefault(), _portal,
				_segmentsEntryLocalService, _segmentsExperienceLocalService,
				segmentsExperiment));

		segmentsExperiment.setSegmentsExperimentKey(experiment.getId());
	}

	public void processDeleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws Exception {

		if ((segmentsExperiment == null) ||
			!_analyticsSettingsManager.isAnalyticsEnabled(
				segmentsExperiment.getCompanyId())) {

			return;
		}

		_asahFaroBackendClient.deleteExperiment(
			segmentsExperiment.getCompanyId(),
			segmentsExperiment.getSegmentsExperimentKey());
	}

	public void processUpdateSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws Exception {

		if ((segmentsExperiment == null) ||
			!_analyticsSettingsManager.isAnalyticsEnabled(
				segmentsExperiment.getCompanyId())) {

			return;
		}

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				segmentsExperiment.getCompanyId());

		_asahFaroBackendClient.updateExperiment(
			segmentsExperiment.getCompanyId(),
			ExperimentUtil.toExperiment(
				_companyLocalService,
				analyticsConfiguration.liferayAnalyticsDataSourceId(),
				_groupLocalService, _layoutLocalService,
				LocaleUtil.getSiteDefault(), _portal,
				_segmentsEntryLocalService, _segmentsExperienceLocalService,
				segmentsExperiment));
	}

	public void processUpdateSegmentsExperimentLayout(
			SegmentsExperiment segmentsExperiment,
			Layout segmentsExperimentLayout)
		throws Exception {

		if ((segmentsExperiment == null) ||
			(segmentsExperimentLayout == null) ||
			!_analyticsSettingsManager.isAnalyticsEnabled(
				segmentsExperiment.getCompanyId())) {

			return;
		}

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				segmentsExperiment.getCompanyId());

		_asahFaroBackendClient.updateExperiment(
			segmentsExperiment.getCompanyId(),
			ExperimentUtil.toExperiment(
				_companyLocalService,
				analyticsConfiguration.liferayAnalyticsDataSourceId(),
				_groupLocalService, segmentsExperimentLayout,
				LocaleUtil.getSiteDefault(), _portal,
				_segmentsEntryLocalService, _segmentsExperienceLocalService,
				segmentsExperiment));
	}

	public void processUpdateSegmentsExperimentRel(
			long companyId, String segmentsExperimentKey,
			List<SegmentsExperimentRel> segmentsExperimentRels)
		throws Exception {

		if ((segmentsExperimentRels == null) ||
			!_analyticsSettingsManager.isAnalyticsEnabled(companyId)) {

			return;
		}

		_asahFaroBackendClient.updateExperimentDXPVariants(
			companyId, segmentsExperimentKey,
			DXPVariantUtil.toDXPVariants(
				LocaleUtil.getSiteDefault(), segmentsExperimentRels));
	}

	private final AnalyticsSettingsManager _analyticsSettingsManager;
	private final AsahFaroBackendClient _asahFaroBackendClient;
	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;
	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService;

}