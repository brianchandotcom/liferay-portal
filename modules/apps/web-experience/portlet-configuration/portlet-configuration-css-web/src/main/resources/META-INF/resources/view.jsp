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
String portletResource = ParamUtil.getString(request, "portletResource");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(portletResource) && PortletPermissionUtil.contains(permissionChecker, themeDisplay.getLayout(), portletResource, ActionKeys.CONFIGURATION) %>">
		<aui:form action="" cssClass="container-fluid-1280" method="post" name="fm">
			<input id="portlet-area" name="portlet-area" type="hidden" />
			<input id="portlet-boundary-id" name="portlet-boundary-id" type="hidden" />

			<div class="portlet-configuration-body-content">
				<div class="container-fluid-1280">
					<liferay-ui:form-navigator
						id="<%= PortletConfigurationCSSConstants.FORM_NAVIGATOR_ID %>"
						markupView="lexicon"
						showButtons="<%= false %>"
					/>
				</div>
			</div>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>