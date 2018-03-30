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

List<UADApplicationExportDisplay> uadApplicationExportDisplayList = (List<UADApplicationExportDisplay>)request.getAttribute(UADWebKeys.UAD_APPLICATION_EXPORT_DISPLAY_LIST);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "new-data-export")));
%>

<portlet:renderURL var="viewUADExportProcesses">
	<portlet:param name="mvcRenderCommandName" value="/view_uad_export_processes" />
	<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
</portlet:renderURL>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "exportApplicationData();" %>'>
		<aui:input name="redirect" type="hidden" value="<%= viewUADExportProcesses.toString() %>" />
		<aui:input name="p_u_i_d" type="hidden" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
		<aui:input name="applicationNames" type="hidden" />

		<div class="sheet sheet-lg">
			<div class="sheet-section">
				<h2 class="sheet-title"><liferay-ui:message key="export-personal-data" /></h2>

				<div class="sheet-text">
					<liferay-ui:message key="please-select-the-applications-for-which-you-want-to-start-an-export-process" />
				</div>

				<liferay-frontend:management-bar
					includeCheckBox="<%= true %>"
					searchContainerId="uadApplicationExportDisplay"
				/>

				<liferay-ui:search-container
					id="uadApplicationExportDisplay"
					rowChecker="<%= new UADApplicationExportDisplayChecker(renderResponse) %>"
					total="<%= uadApplicationExportDisplayList.size() %>"
				>
					<liferay-ui:search-container-results
						results="<%= uadApplicationExportDisplayList %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.user.associated.data.web.internal.display.UADApplicationExportDisplay"
						keyProperty="applicationName"
						modelVar="uadApplicationExportDisplay"
					>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-list-title"
							name="application"
							property="applicationName"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="data-to-export"
						>
							<c:choose>
								<c:when test="<%= uadApplicationExportDisplay.getDataCount() > 0 %>">
									<liferay-ui:message key="yes" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="no" />
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-column-text>

						<%
						Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat("yyyy.MM.dd - hh:mm a", locale, themeDisplay.getTimeZone());

						Date lastExportDate = uadApplicationExportDisplay.getLastExportDate();
						%>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="last-available-export"
						>
							<%= (lastExportDate != null) ? dateFormat.format(lastExportDate) : StringPool.DASH %>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>

			<div class="sheet-footer">
				<aui:button primary="<%= true %>" type="submit" value="export" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</div>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />exportApplicationData() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('applicationNames').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds', '<portlet:namespace />rowIds'));

		submitForm(form, '<portlet:actionURL name="/export_application_data" />');
	}
</aui:script>