<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_list_display/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

DDLRecordSet selRecordSet = null;

try {
	if (Validator.isNotNull(recordSetId)) {
		selRecordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);
	}
}
catch (NoSuchRecordSetException nsrse) {
}

request.setAttribute("record_set_action.jsp-selRecordSet", selRecordSet);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />

	<liferay-ui:error exception="<%= NoSuchRecordSetException.class %>" message="the-list-could-not-be-found" />

	<div class="portlet-msg-info">
		<span class="displaying-help-message-holder <%= selRecordSet == null ? StringPool.BLANK : "aui-helper-hidden" %>">
			<liferay-ui:message key="please-select-a-list-entry-from-the-list-below" />
		</span>

		<span class="displaying-record-set-id-holder <%= selRecordSet == null ? "aui-helper-hidden" : StringPool.BLANK %>">
			<liferay-ui:message key="displaying-list" />: <span class="displaying-record-set-id"><%= selRecordSet != null ? HtmlUtil.escape(selRecordSet.getName(locale)) : StringPool.BLANK %></span>
		</span>
	</div>

	<c:if test="<%= selRecordSet != null %>">

		<%
		long structureClassNameId = PortalUtil.getClassNameId(DDMStructure.class);
		%>

		<aui:fieldset label="templates">
			<aui:select helpMessage="select-the-display-template-used-to-diplay-the-list-records" label="display-template" name="displayTemplateId" onChange='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "displayDDMTemplateId.value = this.value;" %>'>
				<aui:option label="default" value="<%= 0 %>" />

				<%
				List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.getTemplates(structureClassNameId, selRecordSet.getDDMStructureId(), DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

				for (DDMTemplate template : templates) {
					boolean selected = false;

					if (displayDDMTemplateId == template.getTemplateId()) {
						selected = true;
					}
				%>

					<aui:option label="<%= HtmlUtil.escape(template.getName(locale)) %>" selected="<%= selected %>" value="<%= template.getTemplateId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select helpMessage="select-the-form-template-used-to-add-records-to-the-list" label="form-template" name="formTemplateId" onChange='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "formDDMTemplateId.value = this.value;" %>'>
				<aui:option label="default" value="<%= 0 %>" />

				<%
				List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.getTemplates(structureClassNameId, selRecordSet.getDDMStructureId(), DDMTemplateConstants.TEMPLATE_TYPE_FORM, DDMTemplateConstants.TEMPLATE_MODE_CREATE);

				for (DDMTemplate template : templates) {
					boolean selected = false;

					if (formDDMTemplateId == template.getTemplateId()) {
						selected = true;
					}
				%>

					<aui:option label="<%= HtmlUtil.escape(template.getName(locale)) %>" selected="<%= selected %>" value="<%= template.getTemplateId() %>" />

				<%
				}
				%>

			</aui:select>

			<%
			String editableHelpMessage = null;

			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.isInStagingPortlet(portletDisplay.getId())) {
				editableHelpMessage = "check-to-allow-users-to-add-records-to-the-list-once-this-application-is-published-to-live";
			}
			else {
				editableHelpMessage = "check-to-allow-users-to-add-records-to-the-list";
			}
			%>

			<aui:input helpMessage="<%= editableHelpMessage %>" name="editable" onChange='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "editable.value = this.checked;" %>' type="checkbox" value="<%= editable %>" />

			<aui:input helpMessage="check-to-view-the-list-records-in-a-spreadsheet" label="spreadsheet-view" name="spreadsheet" onChange='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "spreadsheet.value = this.checked;" %>' type="checkbox" value="<%= spreadsheet %>" />
		</aui:fieldset>
	</c:if>

	<aui:fieldset label="lists">
		<br />

		<liferay-ui:search-container
			searchContainer="<%= new RecordSetSearch(renderRequest, configurationRenderURL) %>"
		>

			<%
			RecordSetDisplayTerms displayTerms = (RecordSetDisplayTerms)searchContainer.getDisplayTerms();
			RecordSetSearchTerms searchTerms = (RecordSetSearchTerms)searchContainer.getSearchTerms();
			%>

			<liferay-ui:search-form
				page="/html/portlet/dynamic_data_lists/record_set_search.jsp"
			/>

			<liferay-ui:search-container-results>
				<%@ include file="/html/portlet/dynamic_data_lists/record_set_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet"
				escapedModel="<%= true %>"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>

				<%
				StringBundler sb = new StringBundler(7);

				sb.append("javascript:");
				sb.append(renderResponse.getNamespace());
				sb.append("selectRecordSet('");
				sb.append(recordSet.getRecordSetId());
				sb.append("','");
				sb.append(recordSet.getName(locale));
				sb.append("');");

				String rowURL = sb.toString();
				%>

				<%@ include file="/html/portlet/dynamic_data_lists/search_columns.jspf" %>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/dynamic_data_lists/record_set_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</aui:fieldset>
</aui:form>

<br />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />
	<aui:input name="preferences--recordSetId--" type="hidden" value="<%= recordSetId %>" />
	<aui:input name="preferences--displayDDMTemplateId--" type="hidden" value="<%= displayDDMTemplateId %>" />
	<aui:input name="preferences--formDDMTemplateId--" type="hidden" value="<%= formDDMTemplateId %>" />
	<aui:input name="preferences--editable--" type="hidden" value="<%= editable %>" />
	<aui:input name="preferences--spreadsheet--" type="hidden" value="<%= spreadsheet %>" />

	<aui:fieldset cssClass="aui-helper-hidden">
		<aui:field-wrapper label="portlet-id">
			<%= portletResource %>
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />selectRecordSet',
		function(recordSetId, recordSetName) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />recordSetId.value = recordSetId;
			document.<portlet:namespace />fm.<portlet:namespace />displayDDMTemplateId.value = "";
			document.<portlet:namespace />fm.<portlet:namespace />formDDMTemplateId.value = "";

			A.one('.displaying-record-set-id-holder').show();
			A.one('.displaying-help-message-holder').hide();

			var displayRecordSetId = A.one('.displaying-record-set-id');

			displayRecordSetId.html(recordSetName + ' (<liferay-ui:message key="modified" />)');

			displayRecordSetId.addClass('modified');

			var dialog = Liferay.Util.getWindow();

			if (dialog) {
				dialog.set('title', recordSetName + ' - <liferay-ui:message key="configuration" />');
			}
		},
		['aui-base']
	);
</aui:script>