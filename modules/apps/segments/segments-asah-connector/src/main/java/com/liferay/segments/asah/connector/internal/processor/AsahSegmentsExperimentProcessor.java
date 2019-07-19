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
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.converter.ExperimentDTOConverter;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalService;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(immediate = true, service = AsahSegmentsExperimentProcessor.class)
public class AsahSegmentsExperimentProcessor {

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
			_experimentDTOConverter.toDTO(
				segmentsExperiment, _asahFaroBackendClient.getDataSourceId()));

		segmentsExperiment.setSegmentsExperimentKey(experiment.getId());

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment);
	}

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

	@Reference
	private ExperimentDTOConverter _experimentDTOConverter;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

}