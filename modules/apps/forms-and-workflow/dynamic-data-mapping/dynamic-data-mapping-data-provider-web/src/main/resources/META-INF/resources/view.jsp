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
String backURL = ParamUtil.getString(request, "backURL");

String displayStyle = ddmDataProviderDisplayContext.getDisplayStyle();
PortletURL portletURL = ddmDataProviderDisplayContext.getPortletURL();
portletURL.setParameter("displayStyle", displayStyle);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "data-providers"));
%>

<liferay-ui:error exception="<%= RequiredDataProviderInstanceException.MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks.class %>" message="the-data-provider-cannot-be-deleted-because-it-is-required-by-one-or-more-forms" />

<liferay-util:include page="/navbar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteDataProviderInstanceIds" type="hidden" />

		<liferay-ui:search-container
			id="dataProviderInstance"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddmDataProviderDisplayContext.getSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance"
				cssClass="entry-display-style"
				keyProperty="dataProviderInstanceId"
				modelVar="dataProviderInstance"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/edit_data_provider.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="dataProviderInstanceId" value="<%= String.valueOf(dataProviderInstance.getDataProviderInstanceId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-image
							src="<%= ddmDataProviderDisplayContext.getUserPortraitURL(dataProviderInstance.getUserId()) %>"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
							href="<%= rowURL %>"
							path="/data_provider_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/data_provider_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							href="<%= rowURL %>"
							name="name"
							value="<%= HtmlUtil.escape(dataProviderInstance.getName(locale)) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="description"
							value="<%= HtmlUtil.escape(dataProviderInstance.getDescription(locale)) %>"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= dataProviderInstance.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-jsp
							path="/data_provider_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>