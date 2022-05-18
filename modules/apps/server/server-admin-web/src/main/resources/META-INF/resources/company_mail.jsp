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

<portlet:actionURL name="/server_admin/edit_server" var="actionURL" />

<portlet:renderURL var="redirectURL" />

<aui:form action="<%= actionURL %>" cssClass="sheet-lg" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateMail" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<aui:fieldset-group markupView="lexicon">
		<h2>
			<liferay-ui:message key="mail-settings" />
		</h2>

		<liferay-util:include page="/mail_fields.jsp" servletContext="<%= application %>">
			<liferay-util:param name="preferencesCompanyId" value="<%= String.valueOf(company.getCompanyId()) %>" />
		</liferay-util:include>

		<clay:sheet-footer>
			<div class="btn-group">
				<div class="btn-group-item">
					<aui:button cssClass="save-server-button" type="submit" value="save" />
				</div>

				<div class="btn-group-item">
					<aui:button href="<%= redirectURL %>" type="cancel" />
				</div>
			</div>
		</clay:sheet-footer>
	</aui:fieldset-group>
</aui:form>