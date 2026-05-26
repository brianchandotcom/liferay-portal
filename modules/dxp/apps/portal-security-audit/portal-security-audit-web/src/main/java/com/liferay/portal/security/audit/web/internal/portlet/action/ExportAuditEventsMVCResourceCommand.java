/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.web.internal.portlet.action;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayResourceResponse;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.audit.AuditEvent;
import com.liferay.portal.security.audit.web.internal.constants.AuditPortletKeys;
import com.liferay.portal.security.audit.web.internal.display.context.AuditDisplayContext;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.router.configuration.CSVLogMessageFormatterConfiguration",
	property = {
		"jakarta.portlet.name=" + AuditPortletKeys.AUDIT,
		"mvc.command.name=/audit/export_audit_events"
	},
	service = MVCResourceCommand.class
)
public class ExportAuditEventsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		List<String> columns = new ArrayList<>();

		String[] selectedColumns = GetterUtil.getStringValues(
			properties.get("columns"));

		if (selectedColumns.length < 1) {
			selectedColumns = _functionsKeys.keySet(
			).toArray(
				new String[0]
			);
		}

		for (String column : selectedColumns) {
			String key = _functionsKeys.get(column);

			if (key != null) {
				columns.add(key);
			}
		}

		_columns = columns.toArray(new String[0]);
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			SessionMessages.add(
				resourceRequest,
				_portal.getPortletId(resourceRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);

			String auditEventsCSV = _getAuditEventsCSV(
				_columns, resourceRequest, resourceResponse);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, "audit_events.csv",
				auditEventsCSV.getBytes(), ContentTypes.TEXT_CSV_UTF8);
		}
		catch (Exception exception) {
			SessionErrors.add(resourceRequest, exception.getClass());

			_log.error(exception);
		}
	}

	private String _buildCSV(
		List<AuditEvent> auditEvents, String[] columns,
		ProgressTracker progressTracker) {

		int percentage = 10;
		int total = auditEvents.size();

		if (progressTracker != null) {
			progressTracker.setPercent(percentage);
		}

		StringBundler sb = new StringBundler((auditEvents.size() * 3) + 4);

		sb.append(
			StringUtil.merge(
				TransformUtil.transform(columns, CSVUtil::encode, String.class),
				StringPool.COMMA));
		sb.append(StringPool.NEW_LINE);

		for (int i = 0; i < auditEvents.size(); i++) {
			AuditEvent auditEvent = auditEvents.get(i);

			sb.append(
				StringUtil.merge(
					TransformUtil.transform(
						columns,
						column -> {
							Function<AuditEvent, String> function =
								_functions.get(column);

							if (function == null) {
								return StringPool.BLANK;
							}

							String value = function.apply(auditEvent);

							return CSVUtil.encode(value);
						},
						String.class),
					StringPool.COMMA));

			sb.append(StringPool.NEW_LINE);

			percentage = Math.min(10 + ((i * 90) / total), 99);

			if (progressTracker != null) {
				progressTracker.setPercent(percentage);
			}
		}

		return sb.toString();
	}

	private String _formatDate(Date date) {
		if (date instanceof Timestamp) {
			date = new Date(date.getTime());
		}

		return CSVUtil.encode(String.valueOf(date));
	}

	private List<AuditEvent> _getAuditEvents(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		LiferayResourceResponse liferayResourceResponse =
			(LiferayResourceResponse)resourceResponse;

		liferayResourceResponse.createRenderURL(AuditPortletKeys.AUDIT);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AuditDisplayContext auditDisplayContext = new AuditDisplayContext(
			_portal.getHttpServletRequest(resourceRequest),
			_portal.getLiferayPortletRequest(resourceRequest),
			liferayResourceResponse, themeDisplay.getTimeZone());

		auditDisplayContext.setPaging(false);

		SearchContainer<AuditEvent> searchContainer =
			auditDisplayContext.getSearchContainer();

		return searchContainer.getResults();
	}

	private String _getAuditEventsCSV(
			String[] columns, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		List<AuditEvent> auditEvents = _getAuditEvents(
			resourceRequest, resourceResponse);

		if (auditEvents.isEmpty()) {
			return StringPool.BLANK;
		}

		String exportProgressId = ParamUtil.getString(
			resourceRequest, "exportProgressId");

		ProgressTracker progressTracker = new ProgressTracker(exportProgressId);

		progressTracker.start(resourceRequest);

		String csv = _buildCSV(auditEvents, columns, progressTracker);

		progressTracker.finish(resourceRequest);

		return csv;
	}

	private String _getUserEmailAddress(AuditEvent auditEvent) {
		User user = _userLocalService.fetchUser(auditEvent.getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getEmailAddress();
	}

	private String _getUserLogin(AuditEvent auditEvent) {
		User user = _userLocalService.fetchUser(auditEvent.getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getScreenName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportAuditEventsMVCResourceCommand.class);

	private static final Map<String, String> _functionsKeys =
		LinkedHashMapBuilder.put(
			"additionalInfo", "additional-information"
		).put(
			"className", "resource-name"
		).put(
			"classPK", "resource-id"
		).put(
			"clientHost", "client-host"
		).put(
			"clientIP", "client-ip"
		).put(
			"companyId", "company-id"
		).put(
			"eventType", "resource-action"
		).put(
			"message", "message"
		).put(
			"serverName", "server-name"
		).put(
			"serverPort", "server-port"
		).put(
			"sessionID", "session-id"
		).put(
			"timestamp", "create-date"
		).put(
			"userEmailAddress", "user-email-address"
		).put(
			"userId", "user-id"
		).put(
			"userLogin", "user-login"
		).put(
			"userName", "user-name"
		).build();

	private volatile String[] _columns;
	private final LinkedHashMap<String, Function<AuditEvent, String>>
		_functions =
			LinkedHashMapBuilder.<String, Function<AuditEvent, String>>put(
				"additional-information", AuditEvent::getAdditionalInfo
			).put(
				"client-host", AuditEvent::getClientHost
			).put(
				"client-ip", AuditEvent::getClientIP
			).put(
				"company-id",
				auditEvent -> String.valueOf(auditEvent.getCompanyId())
			).put(
				"create-date",
				auditEvent -> _formatDate(auditEvent.getCreateDate())
			).put(
				"event-id",
				auditEvent -> String.valueOf(auditEvent.getAuditEventId())
			).put(
				"group-id",
				auditEvent -> String.valueOf(auditEvent.getGroupId())
			).put(
				"message", AuditEvent::getMessage
			).put(
				"resource-action", AuditEvent::getEventType
			).put(
				"resource-id", AuditEvent::getClassPK
			).put(
				"resource-name", AuditEvent::getClassName
			).put(
				"server-name", AuditEvent::getServerName
			).put(
				"server-port",
				auditEvent -> String.valueOf(auditEvent.getServerPort())
			).put(
				"session-id", AuditEvent::getSessionID
			).put(
				"user-email-address", this::_getUserEmailAddress
			).put(
				"user-id", auditEvent -> String.valueOf(auditEvent.getUserId())
			).put(
				"user-login", this::_getUserLogin
			).put(
				"user-name", AuditEvent::getUserName
			).build();

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}