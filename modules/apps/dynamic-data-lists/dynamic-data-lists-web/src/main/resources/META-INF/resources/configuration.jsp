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
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String keywords = ParamUtil.getString(request, "keywords");

long displayDDMTemplateId = PrefsParamUtil.getLong(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "displayDDMTemplateId");
boolean editable = PrefsParamUtil.getBoolean(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "editable", true);
boolean formView = PrefsParamUtil.getBoolean(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "formView", false);
long formDDMTemplateId = PrefsParamUtil.getLong(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "formDDMTemplateId");
long recordSetId = PrefsParamUtil.getLong(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "recordSetId");
boolean spreadsheet = PrefsParamUtil.getBoolean(PortletPreferencesFactoryUtil.getPortletSetup(renderRequest), renderRequest, "spreadsheet");
long[] templateGroupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

DDLRecordSet selRecordSet = DDLRecordSetServiceUtil.fetchRecordSet(recordSetId);

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<div class="portlet-configuration-body-content">
	<liferay-ui:tabs
		names='<%= (selRecordSet == null) ? "lists" : "lists,optional-configuration" %>'
		refresh="<%= false %>"
	>
		<aui:form action="<%= configurationRenderURL %>" method="post" name="fm1">
			<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />

			<liferay-ui:section>
				<div class="container-fluid-1280">
					<div class="alert alert-info">
						<span class="displaying-help-message-holder <%= (selRecordSet == null) ? StringPool.BLANK : "hide" %>">
							<liferay-ui:message key="please-select-a-list-entry-from-the-list-below" />
						</span>
						<span class="displaying-record-set-id-holder <%= (selRecordSet == null) ? "hide" : StringPool.BLANK %>">
							<liferay-ui:message key="displaying-list" />: <span class="displaying-record-set-id"><%= (selRecordSet != null) ? HtmlUtil.escape(selRecordSet.getName(locale)) : StringPool.BLANK %></span>
						</span>
					</div>

					<%
					int searchTotal = DDLRecordSetServiceUtil.searchCount(company.getCompanyId(), scopeGroupId, keywords, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
					%>

					<aui:fieldset>
						<div class="lfr-ddl-content">
							<div class="sheet sheet-lg">
								<liferay-ui:search-container
									emptyResultsMessage="no-lists-were-found"
									iteratorURL="<%= configurationRenderURL %>"
									total="<%= searchTotal %>"
								>
									<div class="form-search input-append">
										<liferay-ui:input-search
											autoFocus="<%= true %>"
											placeholder='<%= LanguageUtil.get(request, "keywords") %>'
										/>
									</div>

									<liferay-ui:search-container-results
										results="<%= DDLRecordSetServiceUtil.search(company.getCompanyId(), scopeGroupId, keywords, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, searchContainer.getStart(), searchContainer.getEnd(), getDDLRecordSetOrderByComparator(orderByCol, orderByType)) %>"
									/>

									<liferay-ui:search-container-row
										className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
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
										sb.append(HtmlUtil.escapeJS(recordSet.getName(locale)));
										sb.append("');");

										String rowURL = sb.toString();
										%>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="name"
											orderable="<%= false %>"
											value="<%= recordSet.getName(locale) %>"
										/>

										<liferay-ui:search-container-column-text
											href="<%= rowURL %>"
											name="description"
											orderable="<%= false %>"
											value="<%= StringUtil.shorten(recordSet.getDescription(locale), 100) %>"
										/>

										<liferay-ui:search-container-column-date
											href="<%= rowURL %>"
											name="modified-date"
											orderable="<%= false %>"
											value="<%= recordSet.getModifiedDate() %>"
										/>
									</liferay-ui:search-container-row>

									<div class="separator"></div>

									<liferay-ui:search-iterator
										searchResultCssClass="show-quick-actions-on-hover table table-autofit"
									/>
								</liferay-ui:search-container>
							</div>
						</div>
					</aui:fieldset>
				</div>
			</liferay-ui:section>
		</aui:form>

		<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />
			<aui:input name="preferences--recordSetId--" type="hidden" value="<%= recordSetId %>" />

			<c:if test="<%= selRecordSet != null %>">
				<liferay-ui:section>
					<div class="container-fluid-1280">
						<div class="alert alert-info">
							<span class="displaying-record-set-id-holder <%= (selRecordSet == null) ? "hide" : StringPool.BLANK %>">
								<liferay-ui:message key="displaying-list" />: <span class="displaying-record-set-id"><%= (selRecordSet != null) ? HtmlUtil.escape(selRecordSet.getName(locale)) : StringPool.BLANK %></span>
							</span>
						</div>

						<aui:fieldset>
							<div class="lfr-ddl-content">
								<div class="sheet sheet-lg">
									<aui:select helpMessage="select-the-display-template-used-to-diplay-the-list-records" label="display-template" name="preferences--displayDDMTemplateId--">
										<aui:option label="default" value="<%= 0 %>" />

										<%
										List<DDMTemplate> templates = new ArrayList<DDMTemplate>();

										for (long templateGroupId : templateGroupIds) {
											List<DDMTemplate> displayTemplates = DDMTemplateLocalServiceUtil.getTemplates(templateGroupId, PortalUtil.getClassNameId(DDMStructure.class), selRecordSet.getDDMStructureId(), DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);

											templates.addAll(displayTemplates);
										}

										for (DDMTemplate template : templates) {
											if (!DDMTemplatePermission.contains(permissionChecker, template.getTemplateId(), ActionKeys.VIEW)) {
												continue;
											}

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

									<aui:select helpMessage="select-the-form-template-used-to-add-records-to-the-list" label="form-template" name="preferences--formDDMTemplateId--">
										<aui:option label="default" value="<%= 0 %>" />

										<%
										List<DDMTemplate> templates = new ArrayList<DDMTemplate>();

										for (long templateGroupId : templateGroupIds) {
											List<DDMTemplate> formTemplates = DDMTemplateLocalServiceUtil.getTemplates(templateGroupId, PortalUtil.getClassNameId(DDMStructure.class), selRecordSet.getDDMStructureId(), DDMTemplateConstants.TEMPLATE_TYPE_FORM, DDMTemplateConstants.TEMPLATE_MODE_CREATE);

											templates.addAll(formTemplates);
										}

										for (DDMTemplate template : templates) {
											if (!DDMTemplatePermission.contains(permissionChecker, template.getTemplateId(), ActionKeys.VIEW)) {
												continue;
											}

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

									<aui:input helpMessage="check-to-allow-users-to-add-records-to-the-list" name="preferences--editable--" type="checkbox" value="<%= editable %>" />

									<aui:input helpMessage="check-to-display-the-form-entry-view" label="form-view" name="preferences--formView--" type="checkbox" value="<%= formView %>" />

									<aui:input helpMessage="check-to-view-the-list-records-in-a-spreadsheet" label="spreadsheet-view" name="preferences--spreadsheet--" type="checkbox" value="<%= spreadsheet %>" />
								</div>
							</div>
						</aui:fieldset>
					</div>
				</liferay-ui:section>
			</c:if>
		</aui:form>
	</liferay-ui:tabs>
</div>

<aui:button-row>
	<aui:button id="fm_submit" type="submit" />
	<aui:button type="cancel" />
</aui:button-row>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />fm');
	var submitButton = A.one('#<portlet:namespace />fm_submit');

	if (submitButton) {
		submitButton.on('click', function(event) {
			if (form) {
				form.submit();
			}
		});
	}
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />selectRecordSet',
		function(recordSetId, recordSetName) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />recordSetId.value = recordSetId;

			A.one('.displaying-record-set-id-holder').show();
			A.one('.displaying-help-message-holder').hide();

			var displayRecordSetId = A.one('.displaying-record-set-id');

			displayRecordSetId.set(
				'innerHTML',
				recordSetName + ' (<liferay-ui:message key="modified" />)'
			);
			displayRecordSetId.addClass('modified');
		},
		['aui-base']
	);
</aui:script>

<%!
private OrderByComparator<DDLRecordSet> getDDLRecordSetOrderByComparator(String orderByCol, String orderByType) {
	boolean orderByAsc = false;

	if (orderByType.equals("asc")) {
		orderByAsc = true;
	}

	OrderByComparator<DDLRecordSet> orderByComparator = null;

	if (orderByCol.equals("create-date")) {
		orderByComparator = new DDLRecordSetCreateDateComparator(orderByAsc);
	}
	else if (orderByCol.equals("modified-date")) {
		orderByComparator = new DDLRecordSetModifiedDateComparator(orderByAsc);
	}
	else if (orderByCol.equals("name")) {
		orderByComparator = new DDLRecordSetNameComparator(orderByAsc);
	}

	return orderByComparator;
}
%>