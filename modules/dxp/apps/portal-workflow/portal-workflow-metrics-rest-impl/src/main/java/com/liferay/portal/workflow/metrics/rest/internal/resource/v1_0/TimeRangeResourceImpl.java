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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.TimeRangeUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TimeRangeResource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/time-range.properties",
	scope = ServiceScope.PROTOTYPE, service = TimeRangeResource.class
)
public class TimeRangeResourceImpl extends BaseTimeRangeResourceImpl {

	@Override
	public Page<TimeRange> getTimeRangesPage() throws Exception {
		return Page.of(
			Stream.of(
				0, 1, 7, 30, 90, 180, 365
			).map(
				this::_createTimeRange
			).collect(
				Collectors.toList()
			));
	}

	private TimeRange _createTimeRange(int id) {
		TimeRange timeRange = new TimeRange();

		timeRange.setDefaultTimeRange(_isDefault(id));
		timeRange.setDateEnd(
			TimeRangeUtil.getEndDate(id, _user.getTimeZoneId()));
		timeRange.setDateStart(
			TimeRangeUtil.getStartDate(id, _user.getTimeZoneId()));
		timeRange.setId(id);
		timeRange.setName(_getName(id));

		return timeRange;
	}

	private String _getName(int id) {
		if (id == 0) {
			return _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				"today");
		}
		else if (id == 1) {
			return _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				"yesterday");
		}
		else if (id == 7) {
			return _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				"last-7-days");
		}
		else if (id == 30) {
			return _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				"last-30-days");
		}
		else if (id == 90) {
			return _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				"last-90-days");
		}
		else if (id == 180) {
			return _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				"last-180-days");
		}

		return _language.get(
			_resourceHelper.getResourceBundle(
				contextAcceptLanguage.getPreferredLocale()),
			"last-year");
	}

	private boolean _isDefault(int id) {
		if (id == 30) {
			return true;
		}

		return false;
	}

	@Reference
	private Language _language;

	@Reference
	private ResourceHelper _resourceHelper;

	@Context
	private User _user;

}