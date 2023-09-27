/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
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

		if (!_requiresDefaultExperienceReplacement(segmentsExperiment)) {
			return;
		}

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			long defaultSegmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(
						segmentsExperiment.getPlid());

			SegmentsExperienceUtil.copySegmentsExperienceData(
				_commentManager, segmentsExperiment.getGroupId(),
				_layoutLocalService.getLayout(segmentsExperiment.getPlid()),
				_portletRegistry,
				segmentsExperiment.getWinnerSegmentsExperienceId(),
				defaultSegmentsExperienceId, className -> serviceContext,
				segmentsExperiment.getUserId());

			Layout draftLayout = _layoutLocalService.fetchDraftLayout(
				segmentsExperiment.getPlid());

			if (draftLayout != null) {
				SegmentsExperienceUtil.copySegmentsExperienceData(
					_commentManager, segmentsExperiment.getGroupId(),
					draftLayout, _portletRegistry,
					segmentsExperiment.getWinnerSegmentsExperienceId(),
					defaultSegmentsExperienceId, className -> serviceContext,
					segmentsExperiment.getUserId());
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(
				"Unable to update segments experiment " +
					segmentsExperiment.getSegmentsExperimentId(),
				portalException);
		}
	}

	private boolean _requiresDefaultExperienceReplacement(
		SegmentsExperiment segmentsExperiment) {

		if (Objects.equals(
				segmentsExperiment.getSegmentsExperienceKey(),
				SegmentsExperienceConstants.KEY_DEFAULT) &&
			(segmentsExperiment.getStatus() ==
				SegmentsExperimentConstants.STATUS_COMPLETED) &&
			Objects.equals(
				segmentsExperiment.getWinnerSegmentsExperienceKey(),
				SegmentsExperienceConstants.KEY_DEFAULT)) {

			return true;
		}

		return false;
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}