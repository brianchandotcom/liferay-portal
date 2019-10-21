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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.processor.AsahSegmentsExperimentProcessor;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsExperienceModelListener
	extends BaseModelListener<SegmentsExperience> {

	@Override
	public void onAfterUpdate(SegmentsExperience segmentsExperience)
		throws ModelListenerException {

		if (AsahUtil.isSkipAsahEvent(segmentsExperience.getCompanyId())) {
			return;
		}

		try {
			List<SegmentsExperiment> segmentsExperiments =
				_segmentsExperimentLocalService.getSegmentsExperiments(
					segmentsExperience.getSegmentsExperienceId(),
					segmentsExperience.getClassNameId(),
					segmentsExperience.getClassPK(), new int[0], null);

			for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
				_processUpdateSegmentsExperience(
					segmentsExperience, segmentsExperiment);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update segments experience " +
						segmentsExperience.getSegmentsExperienceId(),
					e);
			}
		}
	}

	@Activate
	protected void activate() {
		_asahSegmentsExperimentProcessor = new AsahSegmentsExperimentProcessor(
			_asahFaroBackendClientFactory, _companyLocalService,
			_groupLocalService, _layoutLocalService, _portal,
			_segmentsEntryLocalService, _segmentsExperienceLocalService);
	}

	private void _processUpdateSegmentsExperience(
			SegmentsExperience segmentsExperience,
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperience.getSegmentsExperienceId() ==
				segmentsExperiment.getSegmentsExperienceId()) {

			_asahSegmentsExperimentProcessor.processUpdateSegmentsExperiment(
				segmentsExperiment);

			return;
		}

		_asahSegmentsExperimentProcessor.processUpdateSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentKey(),
			_segmentsExperimentRelLocalService.getSegmentsExperimentRels(
				segmentsExperiment.getSegmentsExperimentId()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceModelListener.class);

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

	private AsahSegmentsExperimentProcessor _asahSegmentsExperimentProcessor;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}