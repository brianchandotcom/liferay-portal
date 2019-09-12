<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String liferayAnalyticsDataSourceId = PrefsPropsUtil.getString(company.getCompanyId(), "liferayAnalyticsDataSourceId");
String liferayAnalyticsEndpointURL = PrefsPropsUtil.getString(company.getCompanyId(), "liferayAnalyticsEndpointURL");
boolean liferayAnalyticsEnableAllGroupIds = PrefsPropsUtil.getBoolean(company.getCompanyId(), "liferayAnalyticsEnableAllGroupIds");
String[] liferayAnalyticsGroupIds = PrefsPropsUtil.getStringArray(company.getCompanyId(), "liferayAnalyticsGroupIds", StringPool.COMMA);
%>

<c:if test="<%= _isAnalyticsTrackingEnabled(liferayAnalyticsDataSourceId, liferayAnalyticsEndpointURL, liferayAnalyticsEnableAllGroupIds, liferayAnalyticsGroupIds, layout.getGroup(), layout.isTypeControlPanel(), request) %>">
	<script data-senna-track="permanent" id="liferayAnalyticsScript" type="text/javascript">
		(function(u, c, a, m, o, l) {
			o = 'script';
			l = document;
			a = l.createElement(o);
			m = l.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = u;
			a.onload = c;
			m.parentNode.insertBefore(a, m);
		})('https://analytics-js-cdn.liferay.com', function() {
			var config = {
				dataSourceId: '<%= HtmlUtil.escapeJS(liferayAnalyticsDataSourceId) %>',
				endpointUrl: '<%= HtmlUtil.escapeJS(liferayAnalyticsEndpointURL) %>'
			};

			Analytics.create({...config});

			Analytics.registerMiddleware(
				function(request) {
					request.context.canonicalUrl = themeDisplay.getCanonicalURL();
					request.context.groupId = themeDisplay.getScopeGroupIdOrLiveGroupId();

					return request;
				}
			);

			if (themeDisplay.isSignedIn()) {
				Analytics.setIdentity(
					{
						email: themeDisplay.getUserEmailAddress(),
						name: themeDisplay.getUserName()
					}
				);
			}

			Analytics.send('pageViewed', 'Page');

			<c:if test="<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED)) %>">
				Liferay.on(
					'endNavigate',
					function(event) {
						Analytics.dispose();

						if (!themeDisplay.isControlPanel()) {
							Analytics.create({...config});

							Analytics.registerMiddleware(
								function(request) {
									request.context.canonicalUrl = themeDisplay.getCanonicalURL();
									request.context.groupId = themeDisplay.getScopeGroupIdOrLiveGroupId();

									return request;
								}
							);

							if (themeDisplay.isSignedIn()) {
								Analytics.setIdentity(
									{
										email: themeDisplay.getUserEmailAddress(),
										name: themeDisplay.getUserName()
									}
								);
							}

							Analytics.send('pageViewed', 'Page', {'page': event.path});
						}
					}
				);
			</c:if>
		});
	</script>
</c:if>

<%!
private static boolean _isAnalyticsTrackingEnabled(String liferayAnalyticsDataSourceId, String liferayAnalyticsEndpointURL, boolean liferayAnalyticsEnableAllGroupIds, String[] liferayAnalyticsGroupIds, Group group, boolean controlPanel, HttpServletRequest request) {
	if (controlPanel) {
		return false;
	}

	if (Validator.isNull(liferayAnalyticsDataSourceId)) {
		return false;
	}

	if (Validator.isNull(liferayAnalyticsEndpointURL)) {
		return false;
	}

	if (Objects.equals(request.getRequestURI(), "/c/portal/api/jsonws")) {
		return false;
	}

	if (_isSharedFormEnabled(liferayAnalyticsGroupIds, group, request)) {
		return true;
	}

	if (liferayAnalyticsEnableAllGroupIds || ArrayUtil.contains(liferayAnalyticsGroupIds, String.valueOf(group.getGroupId()))) {
		return true;
	}

	return false;
}

private static boolean _isSharedFormEnabled(String[] liferayAnalyticsGroupIds, Group group, HttpServletRequest request) {
	if (Objects.equals(group.getGroupKey(), "Forms")) {
		return ArrayUtil.contains(liferayAnalyticsGroupIds, String.valueOf(request.getAttribute("refererGroupId")));
	}

	return false;
}
%>