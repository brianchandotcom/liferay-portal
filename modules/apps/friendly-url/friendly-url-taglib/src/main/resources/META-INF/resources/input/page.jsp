<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/input/init.jsp" %>

<%
boolean disabled = (boolean)request.getAttribute("liferay-friendly-url:input:disabled");
int friendlyURLMaxLength = (int)request.getAttribute("liferay-friendly-url:input:friendlyURLMaxLength");
boolean localizable = (boolean)request.getAttribute("liferay-friendly-url:input:localizable");
String name = (String)request.getAttribute("liferay-friendly-url:input:name");
String value = (String)request.getAttribute("liferay-friendly-url:input:value");
%>

<c:if test='<%= (boolean)request.getAttribute("liferay-friendly-url:input:showHistory") %>'>
	<liferay-friendly-url:history
		className='<%= (String)request.getAttribute("liferay-friendly-url:input:className") %>'
		classPK='<%= (long)request.getAttribute("liferay-friendly-url:input:classPK") %>'
		disabled="<%= disabled %>"
		elementId="<%= portletDisplay.getNamespace() + name %>"
		localizable="<%= localizable %>"
	/>
</c:if>

<div class="form-group friendly-url">
	<c:if test='<%= (boolean)request.getAttribute("liferay-friendly-url:input:showLabel") %>'>
		<label for="<portlet:namespace /><%= name %>">
			<liferay-ui:message key="friendly-url" />

			<liferay-ui:icon-help message='<%= LanguageUtil.format(request, "there-is-a-limit-of-x-characters-in-encoded-format-for-friendly-urls-(e.g.-x)", new String[] {String.valueOf(friendlyURLMaxLength), "<em>/news</em>"}, false) %>' />
		</label>
	</c:if>

	<c:choose>
		<c:when test="<%= localizable %>">
			<liferay-ui:input-localized
				defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
				disabled="<%= disabled %>"
				helpMessage='<%= (String)request.getAttribute("liferay-friendly-url:input:helpMessage") %>'
				ignoreRequestValue="<%= SessionErrors.isEmpty(request) %>"
				inputAddon='<%= (String)request.getAttribute("liferay-friendly-url:input:inputAddon") %>'
				name="<%= name %>"
				xml="<%= HttpComponentsUtil.decodeURL(value) %>"
			/>
		</c:when>
		<c:otherwise>
			<div class="form-text">
				<%= (String)request.getAttribute("liferay-friendly-url:input:inputAddon") %>
			</div>

			<aui:input cssClass="input-medium" disabled="<%= disabled %>" ignoreRequestValue="<%= true %>" label="" name="<%= name %>" type="text" value="<%= value %>" />
		</c:otherwise>
	</c:choose>
</div>