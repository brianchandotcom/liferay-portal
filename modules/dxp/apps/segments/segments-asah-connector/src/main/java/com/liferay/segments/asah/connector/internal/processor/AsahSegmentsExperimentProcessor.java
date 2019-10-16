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

package com.liferay.segments.asah.connector.internal.processor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.util.DXPVariantUtil;
import com.liferay.segments.asah.connector.internal.client.model.util.ExperimentUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Optional;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
public class AsahSegmentsExperimentProcessor {

	public AsahSegmentsExperimentProcessor(
		AsahFaroBackendClientFactory asahFaroBackendClientFactory,
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService, Portal portal,
		SegmentsEntryLocalService segmentsEntryLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_asahFaroBackendClientFactory = asahFaroBackendClientFactory;
		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_portal = portal;
		_segmentsEntryLocalService = segmentsEntryLocalService;
		_segmentsExperienceLocalService = segmentsExperienceLocalService;
	}

	public void processAddSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperiment == null) {
			return;
		}

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		Experiment experiment = _asahFaroBackendClient.addExperiment(
			ExperimentUtil.toExperiment(
				_companyLocalService, _asahFaroBackendClient.getDataSourceId(),
				_groupLocalService, _layoutLocalService,
				LocaleUtil.getSiteDefault(), _portal,
				_segmentsEntryLocalService, _segmentsExperienceLocalService,
				segmentsExperiment));

		segmentsExperiment.setSegmentsExperimentKey(experiment.getId());
	}

	public void processDeleteSegmentsExperiment(
		SegmentsExperiment segmentsExperiment) {

		if (segmentsExperiment == null) {
			return;
		}

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		_asahFaroBackendClient.deleteExperiment(
			segmentsExperiment.getSegmentsExperimentKey());
	}

	public void processUpdateSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperiment == null) {
			return;
		}

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		_asahFaroBackendClient.updateExperiment(
			ExperimentUtil.toExperiment(
				_companyLocalService, _asahFaroBackendClient.getDataSourceId(),
				_groupLocalService, _layoutLocalService,
				LocaleUtil.getSiteDefault(), _portal,
				_segmentsEntryLocalService, _segmentsExperienceLocalService,
				segmentsExperiment));
	}

	public void processUpdateSegmentsExperimentLayout(
			SegmentsExperiment segmentsExperiment,
			Layout segmentsExperimentLayout)
		throws PortalException {

		if ((segmentsExperiment == null) ||
			(segmentsExperimentLayout == null)) {

			return;
		}

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		_asahFaroBackendClient.updateExperiment(
			ExperimentUtil.toExperiment(
				_companyLocalService, _asahFaroBackendClient.getDataSourceId(),
				_groupLocalService, segmentsExperimentLayout,
				LocaleUtil.getSiteDefault(), _portal,
				_segmentsEntryLocalService, _segmentsExperienceLocalService,
				segmentsExperiment));
	}

	public void processUpdateSegmentsExperimentRel(
			String segmentsExperimentKey,
			List<SegmentsExperimentRel> segmentsExperimentRels)
		throws PortalException {

		if (segmentsExperimentRels == null) {
			return;
		}

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		_asahFaroBackendClient.updateExperimentDXPVariants(
			segmentsExperimentKey,
			DXPVariantUtil.toDXPVariants(
				LocaleUtil.getSiteDefault(), segmentsExperimentRels));
	}

	private AsahFaroBackendClient _asahFaroBackendClient;
	private final AsahFaroBackendClientFactory _asahFaroBackendClientFactory;
	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;
	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService;

}