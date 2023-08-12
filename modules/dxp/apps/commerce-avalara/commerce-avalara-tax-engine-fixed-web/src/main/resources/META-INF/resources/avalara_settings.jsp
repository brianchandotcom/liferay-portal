<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_avalara" var="editCommerceAvalaraConnectorActionURL" />

<aui:form action="<%= editCommerceAvalaraConnectorActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value='<%= ParamUtil.getLong(request, "commerceTaxMethodId") %>' />

	<commerce-ui:panel>
		<c:choose>
			<c:when test="<%= GetterUtil.getBoolean(request.getAttribute(CommerceAvalaraWebKeys.CONNECTION_ESTABLISHED)) %>">
				<%@ include file="/sections/avalara_channel_configuration.jspf" %>
				<%@ include file="/sections/dispatch_trigger_setup.jspf" %>
			</c:when>
			<c:otherwise>
				<clay:alert
					displayType="warning"
					message="configure-credentials-before-continuing"
				/>
			</c:otherwise>
		</c:choose>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>