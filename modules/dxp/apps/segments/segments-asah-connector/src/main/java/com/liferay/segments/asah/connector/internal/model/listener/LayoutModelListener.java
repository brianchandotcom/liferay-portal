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

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.processor.AsahSegmentsExperimentProcessor;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterUpdate(Layout originalLayout, Layout layout)
		throws ModelListenerException {

		try {
			if (_isSkipEvent(layout)) {
				return;
			}

			List<SegmentsExperiment> segmentsExperiments =
				_segmentsExperimentLocalService.getSegmentsExperiments(
					layout.getGroupId(),
					_classNameLocalService.getClassNameId(
						Layout.class.getName()),
					layout.getPlid());

			for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
				_asahSegmentsExperimentProcessor.
					processUpdateSegmentsExperimentLayout(
						segmentsExperiment, layout);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update layout " + layout.getLayoutId(),
					exception);
			}
		}
	}

	@Activate
	protected void activate() {
		_asahSegmentsExperimentProcessor = new AsahSegmentsExperimentProcessor(
			_analyticsSettingsManager,
			new AsahFaroBackendClientImpl(_analyticsSettingsManager, _http),
			_companyLocalService, _groupLocalService, _layoutLocalService,
			_portal, _segmentsEntryLocalService,
			_segmentsExperienceLocalService);
	}

	@Deactivate
	protected void deactivate() {
		_asahSegmentsExperimentProcessor = null;
	}

	private boolean _isSkipEvent(Layout layout) throws Exception {
		if (AsahUtil.isSkipAsahEvent(
				_analyticsSettingsManager, layout.getCompanyId(),
				layout.getGroupId())) {

			return true;
		}

		Layout oldLayout = _layoutLocalService.fetchLayout(layout.getPlid());

		if (!Objects.equals(
				oldLayout.getFriendlyURL(), layout.getFriendlyURL()) ||
			!Objects.equals(oldLayout.getTitle(), layout.getTitle())) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutModelListener.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private AsahSegmentsExperimentProcessor _asahSegmentsExperimentProcessor;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

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

}