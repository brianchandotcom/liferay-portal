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

package com.liferay.portal.security.audit.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.audit.AuditEvent;
import com.liferay.portal.security.audit.storage.comparator.AuditEventCreateDateComparator;
import com.liferay.portal.security.audit.web.internal.AuditEventManagerUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class AuditDisplayContext {

	public AuditDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, TimeZone timeZone) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_timeZone = timeZone;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_today = CalendarFactoryUtil.getCalendar(
			timeZone, _themeDisplay.getLocale());
	}

	public SearchContainer<AuditEvent> getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		DisplayTerms displayTerms = new DisplayTerms(_liferayPortletRequest);

		_searchContainer = new SearchContainer(
			_liferayPortletRequest, displayTerms, null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			_getPortletURL(),
			ListUtil.fromArray(
				"user-id", "user-name", "resource-id", "resource-name",
				"resource-action", "client-ip", "create-date"),
			"there-are-no-events");

		if (displayTerms.isAdvancedSearch()) {
			Date endDate = PortalUtil.getDate(
				_getEndDateMonth(), _getEndDateDay(), _getEndDateYear(),
				(_getEndDateAmPm() != Calendar.PM) ? _getEndDateHour() :
					_getEndDateHour() + 12,
				_getEndDateMinute(), _timeZone, null);

			Date startDate = PortalUtil.getDate(
				_getStartDateMonth(), _getStartDateDay(), _getStartDateYear(),
				(_getStartDateAmPm() != Calendar.PM) ? _getStartDateHour() :
					_getStartDateHour() + 12,
				_getStartDateMinute(), _timeZone, null);

			_searchContainer.setResultsAndTotal(
				() -> AuditEventManagerUtil.getAuditEvents(
					_themeDisplay.getCompanyId(), _getUserId(), _getUserName(),
					startDate, endDate, _getEventType(), _getClassName(),
					_getClassPK(), _getClientHost(), _getClientIP(),
					_getServerName(), _getServerPort(), null,
					displayTerms.isAndOperator(), _searchContainer.getStart(),
					_searchContainer.getEnd(),
					new AuditEventCreateDateComparator()),
				AuditEventManagerUtil.getAuditEventsCount(
					_themeDisplay.getCompanyId(), _getUserId(), _getUserName(),
					startDate, endDate, _getEventType(), _getClassName(),
					_getClassPK(), _getClientHost(), _getClientIP(),
					_getServerName(), _getServerPort(), null,
					displayTerms.isAndOperator()));
		}
		else {
			String keywords = displayTerms.getKeywords();

			String number =
				Validator.isNumber(keywords) ? keywords : String.valueOf(0);

			_searchContainer.setResultsAndTotal(
				() -> AuditEventManagerUtil.getAuditEvents(
					_themeDisplay.getCompanyId(), Long.valueOf(number),
					keywords, null, null, keywords, keywords, keywords,
					keywords, keywords, keywords, Integer.valueOf(number), null,
					false, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					new AuditEventCreateDateComparator()),
				AuditEventManagerUtil.getAuditEventsCount(
					_themeDisplay.getCompanyId(), Long.valueOf(number),
					keywords, null, null, keywords, keywords, keywords,
					keywords, keywords, keywords, Integer.valueOf(number), null,
					false));
		}

		return _searchContainer;
	}

	private String _getClassName() {
		if (_className != null) {
			return _className;
		}

		_className = ParamUtil.getString(_httpServletRequest, "className");

		return _className;
	}

	private String _getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = ParamUtil.getString(_httpServletRequest, "classPK");

		return _classPK;
	}

	private String _getClientHost() {
		if (_clientHost != null) {
			return _clientHost;
		}

		_clientHost = ParamUtil.getString(_httpServletRequest, "clientHost");

		return _clientHost;
	}

	private String _getClientIP() {
		if (_clientIP != null) {
			return _clientIP;
		}

		_clientIP = ParamUtil.getString(_httpServletRequest, "clientIP");

		return _clientIP;
	}

	private int _getEndDateAmPm() {
		if (_endDateAmPm != null) {
			return _endDateAmPm;
		}

		_endDateAmPm = ParamUtil.getInteger(
			_httpServletRequest, "endDateAmPm", _today.get(Calendar.AM_PM));

		return _endDateAmPm;
	}

	private int _getEndDateDay() {
		if (_endDateDay != null) {
			return _endDateDay;
		}

		_endDateDay = ParamUtil.getInteger(
			_httpServletRequest, "endDateDay", _today.get(Calendar.DATE));

		return _endDateDay;
	}

	private int _getEndDateHour() {
		if (_endDateHour != null) {
			return _endDateHour;
		}

		_endDateHour = ParamUtil.getInteger(
			_httpServletRequest, "endDateHour", _today.get(Calendar.HOUR));

		return _endDateHour;
	}

	private int _getEndDateMinute() {
		if (_endDateMinute != null) {
			return _endDateMinute;
		}

		_endDateMinute = ParamUtil.getInteger(
			_httpServletRequest, "endDateMinute", _today.get(Calendar.MINUTE));

		return _endDateMinute;
	}

	private int _getEndDateMonth() {
		if (_endDateMonth != null) {
			return _endDateMonth;
		}

		_endDateMonth = ParamUtil.getInteger(
			_httpServletRequest, "endDateMonth", _today.get(Calendar.MONTH));

		return _endDateMonth;
	}

	private int _getEndDateYear() {
		if (_endDateYear != null) {
			return _endDateYear;
		}

		_endDateYear = ParamUtil.getInteger(
			_httpServletRequest, "endDateYear", _today.get(Calendar.YEAR));

		return _endDateYear;
	}

	private String _getEventType() {
		if (_eventType != null) {
			return _eventType;
		}

		_eventType = ParamUtil.getString(_httpServletRequest, "eventType");

		return _eventType;
	}

	private PortletURL _getPortletURL() throws Exception {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.create(
			PortletURLUtil.clone(
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse),
				_liferayPortletResponse)
		).setParameter(
			"className", _getClassName()
		).setParameter(
			"classPK", _getClassPK()
		).setParameter(
			"clientHost", _getClientHost()
		).setParameter(
			"clientIP", _getClientIP()
		).setParameter(
			"endDateAmPm", _getEndDateAmPm()
		).setParameter(
			"endDateDay", _getEndDateDay()
		).setParameter(
			"endDateHour", _getEndDateHour()
		).setParameter(
			"endDateMinute", _getEndDateMinute()
		).setParameter(
			"endDateMonth", _getEndDateMonth()
		).setParameter(
			"endDateYear", _getEndDateYear()
		).setParameter(
			"eventType", _getEventType()
		).setParameter(
			"serverName", _getServerName()
		).setParameter(
			"serverPort", _getServerPort()
		).setParameter(
			"startDateAmPm", _getStartDateAmPm()
		).setParameter(
			"startDateDay", _getStartDateDay()
		).setParameter(
			"startDateHour", _getStartDateHour()
		).setParameter(
			"startDateMinute", _getStartDateMinute()
		).setParameter(
			"startDateMonth", _getStartDateMonth()
		).setParameter(
			"startDateYear", _getStartDateYear()
		).setParameter(
			"userId", _getUserId()
		).setParameter(
			"userName", _getUserName()
		).buildPortletURL();

		return _portletURL;
	}

	private String _getServerName() {
		if (_serverName != null) {
			return _serverName;
		}

		_serverName = ParamUtil.getString(_httpServletRequest, "serverName");

		return _serverName;
	}

	private int _getServerPort() {
		if (_serverPort != null) {
			return _serverPort;
		}

		_serverPort = ParamUtil.getInteger(_httpServletRequest, "serverPort");

		return _serverPort;
	}

	private int _getStartDateAmPm() {
		if (_startDateAmPm != null) {
			return _startDateAmPm;
		}

		_startDateAmPm = ParamUtil.getInteger(
			_httpServletRequest, "startDateAmPm", _today.get(Calendar.AM_PM));

		return _startDateAmPm;
	}

	private int _getStartDateDay() {
		if (_startDateDay != null) {
			return _startDateDay;
		}

		_startDateDay = ParamUtil.getInteger(
			_httpServletRequest, "startDateDay", _today.get(Calendar.DATE));

		return _startDateDay;
	}

	private int _getStartDateHour() {
		if (_startDateHour != null) {
			return _startDateHour;
		}

		_startDateHour = ParamUtil.getInteger(
			_httpServletRequest, "startDateHour", _today.get(Calendar.HOUR));

		return _startDateHour;
	}

	private int _getStartDateMinute() {
		if (_startDateMinute != null) {
			return _startDateMinute;
		}

		_startDateMinute = ParamUtil.getInteger(
			_httpServletRequest, "startDateMinute",
			_today.get(Calendar.MINUTE));

		return _startDateMinute;
	}

	private int _getStartDateMonth() {
		if (_startDateMonth != null) {
			return _startDateMonth;
		}

		_startDateMonth = ParamUtil.getInteger(
			_httpServletRequest, "startDateMonth", _today.get(Calendar.MONTH));

		return _startDateMonth;
	}

	private int _getStartDateYear() {
		if (_startDateYear != null) {
			return _startDateYear;
		}

		_startDateYear = ParamUtil.getInteger(
			_httpServletRequest, "startDateYear", _today.get(Calendar.YEAR));

		return _startDateYear;
	}

	private long _getUserId() {
		if (_userId != null) {
			return _userId;
		}

		_userId = ParamUtil.getLong(_httpServletRequest, "userId");

		return _userId;
	}

	private String _getUserName() {
		if (_userName != null) {
			return _userName;
		}

		_userName = ParamUtil.getString(_httpServletRequest, "userName");

		return _userName;
	}

	private String _className;
	private String _classPK;
	private String _clientHost;
	private String _clientIP;
	private Integer _endDateAmPm;
	private Integer _endDateDay;
	private Integer _endDateHour;
	private Integer _endDateMinute;
	private Integer _endDateMonth;
	private Integer _endDateYear;
	private String _eventType;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private PortletURL _portletURL;
	private SearchContainer<AuditEvent> _searchContainer;
	private String _serverName;
	private Integer _serverPort;
	private Integer _startDateAmPm;
	private Integer _startDateDay;
	private Integer _startDateHour;
	private Integer _startDateMinute;
	private Integer _startDateMonth;
	private Integer _startDateYear;
	private final ThemeDisplay _themeDisplay;
	private final TimeZone _timeZone;
	private final Calendar _today;
	private Long _userId;
	private String _userName;

}