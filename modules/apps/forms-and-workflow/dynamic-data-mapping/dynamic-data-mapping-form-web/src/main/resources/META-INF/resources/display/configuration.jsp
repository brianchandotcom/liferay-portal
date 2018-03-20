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

<%@ include file="/display/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String keywords = ParamUtil.getString(request, "keywords");

long formInstanceId = PrefsParamUtil.getLong(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "formInstanceId");

DDMFormInstance selFormInstance = DDMFormInstanceServiceUtil.fetchFormInstance(formInstanceId);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationRenderURL %>" method="post" name="fm1">
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<div class="alert alert-info">
				<span class="displaying-help-message-holder <%= (selFormInstance == null) ? StringPool.BLANK : "hide" %>">
					<liferay-ui:message key="please-select-a-form-from-the-list-below" />
				</span>
				<span class="displaying-form-instance-id-holder <%= (selFormInstance == null) ? "hide" : StringPool.BLANK %>">
					<liferay-ui:message key="displaying-form" />: <span class="displaying-form-instance-id"><%= (selFormInstance != null) ? HtmlUtil.escape(selFormInstance.getName(locale)) : StringPool.BLANK %></span>
				</span>
			</div>

			<aui:fieldset>
				<liferay-ui:search-container
					emptyResultsMessage="no-forms-were-found"
					iteratorURL="<%= configurationRenderURL %>"
					total="<%= DDMFormInstanceServiceUtil.searchCount(company.getCompanyId(), scopeGroupId, keywords) %>"
				>
					<div class="form-search input-append">
						<liferay-ui:input-search
							autoFocus="<%= true %>"
							placeholder='<%= LanguageUtil.get(request, "keywords") %>'
						/>
					</div>

					<liferay-ui:search-container-results
						results="<%= DDMFormInstanceServiceUtil.search(company.getCompanyId(), scopeGroupId, keywords, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.dynamic.data.mapping.model.DDMFormInstance"
						escapedModel="<%= true %>"
						keyProperty="formInstanceId"
						modelVar="formInstance"
					>

						<%
						StringBundler sb = new StringBundler(7);

						sb.append("javascript:");
						sb.append(renderResponse.getNamespace());
						sb.append("selectFormInstance('");
						sb.append(formInstance.getFormInstanceId());
						sb.append("','");
						sb.append(HtmlUtil.escapeJS(formInstance.getName(locale)));
						sb.append("');");

						String rowURL = sb.toString();
						%>

						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="name"
							orderable="<%= false %>"
							value="<%= formInstance.getName(locale) %>"
						/>

						<liferay-ui:search-container-column-text
							buffer="buffer"
							href="<%= rowURL %>"
							name="description"
							orderable="<%= false %>"
						>

							<%
							buffer.append(StringUtil.shorten(formInstance.getDescription(locale), 100));
							%>

						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-date
							href="<%= rowURL %>"
							name="modified-date"
							orderable="<%= false %>"
							value="<%= formInstance.getModifiedDate() %>"
						/>
					</liferay-ui:search-container-row>

					<div class="separator"><!-- --></div>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			</aui:fieldset>
		</div>
	</div>
</aui:form>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />
	<aui:input name="preferences--formInstanceId--" type="hidden" value="<%= formInstanceId %>" />

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />selectFormInstance',
		function(formInstanceId, formInstanceName) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />formInstanceId.value = formInstanceId;

			A.one('.displaying-form-instance-id-holder').show();
			A.one('.displaying-help-message-holder').hide();

			var displayFormInstanceId = A.one('.displaying-form-instance-id');

			displayFormInstanceId.set('innerHTML', formInstanceName + ' (<liferay-ui:message key="modified" />)');
			displayFormInstanceId.addClass('modified');
		},
		['aui-base']
	);
</aui:script>