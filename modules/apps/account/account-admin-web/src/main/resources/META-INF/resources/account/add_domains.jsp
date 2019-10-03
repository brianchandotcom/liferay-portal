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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "addDomains");
%>

<div class="modal-body">
	<aui:field-wrapper cssClass="form-group">
		<aui:input label="domain" name="domain" />

		<div class="form-text">
			<liferay-ui:message key="for-multiple-domains,-separate-each-domain-by-a-comma" />
		</div>
	</aui:field-wrapper>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "addDomains();" %>' primary="<%= true %>" value="save" />

		<aui:button type="cancel" />
	</aui:button-row>
</div>

<aui:script>
	function <portlet:namespace />addDomains() {
		var Util = Liferay.Util;

		var openingLiferay = Util.getOpener().Liferay;

		openingLiferay.fire(
			'<%= HtmlUtil.escapeJS(eventName) %>',
			{
				data: document.getElementById('<portlet:namespace />domain').value
			}
		);

		Util.getWindow().hide();
	}
</aui:script>