<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group guestGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.GUEST);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="advanced"
/>

<c:choose>
	<c:when test="<%= !layoutsAdminDisplayContext.isPrivateLayout() && (layoutsAdminDisplayContext.getLiveGroupId() != guestGroup.getGroupId()) %>">

		<%
		Group group = layoutsAdminDisplayContext.getLiveGroup();
		%>

		<c:choose>
			<c:when test="<%= group.isPrivateLayoutsEnabled() %>">
				<aui:input helpMessage='<%= LanguageUtil.format(request, "you-can-configure-the-top-level-pages-of-this-public-site-to-merge-with-the-top-level-pages-of-the-public-x-site", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' label='<%= LanguageUtil.format(request, "merge-x-public-pages", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' name="mergeGuestPublicPages" type="checkbox" value='<%= PropertiesParamUtil.getBoolean(layoutsAdminDisplayContext.getGroupTypeSettingsUnicodeProperties(), request, "mergeGuestPublicPages") %>' />
			</c:when>
			<c:otherwise>
				<aui:input helpMessage='<%= LanguageUtil.format(request, "you-can-configure-the-top-level-pages-of-this-site-to-merge-with-the-top-level-pages-of-the-x-site", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' label='<%= LanguageUtil.format(request, "merge-x-pages", HtmlUtil.escape(guestGroup.getDescriptiveName(locale)), false) %>' name="mergeGuestPublicPages" type="checkbox" value='<%= PropertiesParamUtil.getBoolean(layoutsAdminDisplayContext.getGroupTypeSettingsUnicodeProperties(), request, "mergeGuestPublicPages") %>' />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<clay:alert
			displayType="info"
			message="there-are-no-available-advanced-settings-for-these-pages"
		/>
	</c:otherwise>
</c:choose>