<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<div class="row">
	<div class="col-md-12">
		<br />

		<aui:select label="default-portlet-decorator" name='<%= "settings--" + PropsKeys.DEFAULT_PORTLET_DECORATOR_ID + "--" %>' value="<%= (String)request.getAttribute(PropsKeys.DEFAULT_PORTLET_DECORATOR_ID) %>">
			<aui:option label="barebone" value="barebone" />
			<aui:option label="borderless" value="borderless" />
			<aui:option label="decorate" value="decorate" />
		</aui:select>
	</div>
</div>