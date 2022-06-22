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

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.configuration.SegmentsCompanyConfiguration;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/instance_settings/bind_segments_company_configuration"
	},
	service = MVCActionCommand.class
)
public class BindSegmentsCompanyConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_segmentsConfigurationProvider.updateSegmentsCompanyConfiguration(
			_portal.getCompanyId(actionRequest),
			_getSegmentsCompanyConfiguration(actionRequest));
	}

	private SegmentsCompanyConfiguration _getSegmentsCompanyConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		boolean segmentationEnabled = _isSegmentationEnabled(actionRequest);

		boolean roleSegmentationEnabled = _isRoleSegmentationEnabled(
			actionRequest);

		return new SegmentsCompanyConfiguration() {

			@Override
			public boolean roleSegmentationEnabled() {
				return roleSegmentationEnabled;
			}

			@Override
			public boolean segmentationEnabled() {
				return segmentationEnabled;
			}

		};
	}

	private boolean _isRoleSegmentationEnabled(ActionRequest actionRequest)
		throws Exception {

		String roleSegmentationEnabledString = ParamUtil.getString(
			actionRequest, "roleSegmentationEnabled");

		if (Validator.isNotNull(roleSegmentationEnabledString) &&
			Objects.equals(roleSegmentationEnabledString, "on")) {

			return true;
		}
		else if (Validator.isNull(roleSegmentationEnabledString)) {
			return _segmentsConfigurationProvider.isRoleSegmentationEnabled(
				_portal.getCompanyId(actionRequest));
		}

		return false;
	}

	private boolean _isSegmentationEnabled(ActionRequest actionRequest)
		throws Exception {

		String segmentationEnabledString = ParamUtil.getString(
			actionRequest, "segmentationEnabled");

		if (Validator.isNotNull(segmentationEnabledString) &&
			Objects.equals(segmentationEnabledString, "on")) {

			return true;
		}
		else if (Validator.isNull(segmentationEnabledString)) {
			return _segmentsConfigurationProvider.isSegmentationEnabled(
				_portal.getCompanyId(actionRequest));
		}

		return false;
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

}