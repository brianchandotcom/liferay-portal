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

package com.liferay.segments.asah.connector.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.JSONWebServiceClient;
import com.liferay.segments.asah.connector.internal.processor.AsahSegmentsExperimentProcessor;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(service = ModelListener.class)
public class SegmentsExperimentModelListener
	extends BaseModelListener<SegmentsExperiment> {

	@Override
	public void onAfterUpdate(
			SegmentsExperiment originalSegmentsExperiment,
			SegmentsExperiment segmentsExperiment)
		throws ModelListenerException {

		if (AsahUtil.isSkipAsahEvent(
				segmentsExperiment.getCompanyId(),
				segmentsExperiment.getGroupId())) {

			return;
		}

		try {
			_asahSegmentsExperimentProcessor.processUpdateSegmentsExperiment(
				segmentsExperiment);
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to update segments experiment " +
					segmentsExperiment.getSegmentsExperimentId(),
				exception);
		}
	}

	@Override
	public void onBeforeCreate(SegmentsExperiment segmentsExperiment)
		throws ModelListenerException {

		if (AsahUtil.isSkipAsahEvent(
				segmentsExperiment.getCompanyId(),
				segmentsExperiment.getGroupId())) {

			return;
		}

		try {
			_asahSegmentsExperimentProcessor.processAddSegmentsExperiment(
				segmentsExperiment);
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to add segments experiment " +
					segmentsExperiment.getSegmentsExperimentId(),
				exception);
		}
	}

	@Override
	public void onBeforeRemove(SegmentsExperiment segmentsExperiment)
		throws ModelListenerException {

		if (AsahUtil.isSkipAsahEvent(
				segmentsExperiment.getCompanyId(),
				segmentsExperiment.getGroupId())) {

			return;
		}

		try {
			_asahSegmentsExperimentProcessor.processDeleteSegmentsExperiment(
				segmentsExperiment);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to delete segments experiment " +
						segmentsExperiment.getSegmentsExperimentId(),
					exception);
			}
		}
	}

	@Activate
	protected void activate() {
		_asahSegmentsExperimentProcessor = new AsahSegmentsExperimentProcessor(
			new AsahFaroBackendClientImpl(_jsonWebServiceClient),
			_companyLocalService, _groupLocalService, _layoutLocalService,
			_portal, _segmentsEntryLocalService,
			_segmentsExperienceLocalService);
	}

	@Deactivate
	protected void deactivate() {
		_asahSegmentsExperimentProcessor = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentModelListener.class);

	private AsahSegmentsExperimentProcessor _asahSegmentsExperimentProcessor;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}