<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="/server_admin/edit_server" var="actionURL" />

<portlet:renderURL var="redirectURL" />

<%
long preferencesCompanyId = company.getCompanyId();

Function<String, String> defaultValueFunction = key -> StringPool.BLANK;
%>

<aui:form action="<%= actionURL %>" cssClass="sheet-lg" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateMail" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<div class="sheet">
		<div class="panel-group panel-group-flush">
			<h2>
				<liferay-ui:message key="mail-settings" />
			</h2>

			<%@ include file="/mail_fields.jspf" %>

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
		</div>
	</div>
</aui:form>