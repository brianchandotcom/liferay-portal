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
String scope = ParamUtil.getString(request, "scope", "personal-site");

long[] groupIds = (long[])request.getAttribute(UADWebKeys.GROUP_IDS);
int totalReviewableUADEntitiesCount = (int)request.getAttribute(UADWebKeys.TOTAL_UAD_ENTITIES_COUNT);
List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays = (List<UADApplicationSummaryDisplay>)request.getAttribute(UADWebKeys.UAD_APPLICATION_SUMMARY_DISPLAY_LIST);
List<UADDisplay> uadDisplays = (List<UADDisplay>)request.getAttribute(UADWebKeys.APPLICATION_UAD_DISPLAYS);
ViewUADEntitiesDisplay viewUADEntitiesDisplay = (ViewUADEntitiesDisplay)request.getAttribute(UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcRenderCommandName", "/view_uad_summary");
backURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));
%>

<liferay-util:include page="/uad_data_navigation_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="row">
		<div class="col-lg-3">
			<div class="panel panel-secondary">
				<div class="collapse-icon collapse-icon-middle panel-header" data-target="#<portlet:namespace />scopePanelBody" data-toggle="collapse">
					<span class="panel-title">
						<%= StringUtil.toUpperCase(LanguageUtil.get(request, "scope"), locale) %>
					</span>

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</div>

				<div class="collapse panel-collapse show" id="<portlet:namespace />scopePanelBody">
					<div class="panel-body">
						<clay:radio
							checked='<%= scope.equals("personal-site") %>'
							label='<%= LanguageUtil.get(request, "personal-site") %>'
							name="scope"
							value="personal-site"
						/>

						<clay:radio
							checked='<%= scope.equals("regular-sites") %>'
							label='<%= LanguageUtil.get(request, "regular-sites") %>'
							name="scope"
							value="regular-sites"
						/>

						<clay:radio
							checked='<%= scope.equals("instance") %>'
							label='<%= LanguageUtil.get(request, "instance") %>'
							name="scope"
							value="instance"
						/>
					</div>
				</div>
			</div>

			<div class="panel panel-secondary">
				<div class="collapse-icon collapse-icon-middle panel-header" data-target="#<portlet:namespace />applicationPanelBody" data-toggle="collapse">
					<span class="panel-title">

						<%
						String applicationPanelTitle = StringUtil.toUpperCase(LanguageUtil.get(request, "applications"), locale);
						%>

						<%= StringUtil.appendParentheticalSuffix(applicationPanelTitle, totalReviewableUADEntitiesCount) %>
					</span>

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</div>

				<div class="collapse panel-collapse show" id="<portlet:namespace />applicationPanelBody">
					<div class="panel-body">

						<%
						for (UADApplicationSummaryDisplay uadApplicationSummaryDisplay : uadApplicationSummaryDisplays) {
							String applicationName = UADLanguageUtil.getApplicationName(uadApplicationSummaryDisplay.getApplicationKey(), locale);
						%>

							<clay:radio
								checked="<%= Objects.equals(uadApplicationSummaryDisplay.getApplicationKey(), viewUADEntitiesDisplay.getApplicationKey()) %>"
								label="<%= StringUtil.appendParentheticalSuffix(applicationName, uadApplicationSummaryDisplay.getCount()) %>"
								name="applicationKey"
								value="<%= uadApplicationSummaryDisplay.getApplicationKey() %>"
							/>

						<%
						}
						%>

					</div>
				</div>
			</div>

			<div class="panel-group">
				<div class="panel panel-secondary">
					<div class="collapse-icon collapse-icon-middle panel-header" data-target="#<portlet:namespace />entitiesTypePanelBody" data-toggle="collapse">
						<span class="panel-title">

							<%
							String applicationName = UADLanguageUtil.getApplicationName(viewUADEntitiesDisplay.getApplicationKey(), locale);
							%>

							<%= StringUtil.toUpperCase(applicationName, locale) %>
						</span>

						<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

						<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
					</div>

					<div class="collapse panel-collapse show" id="<portlet:namespace />entitiesTypePanelBody">
						<div class="panel-body">

							<%
							for (UADDisplay uadDisplay : uadDisplays) {
							%>

								<clay:radio
									checked="<%= Objects.equals(uadDisplay.getTypeName(locale), viewUADEntitiesDisplay.getTypeName()) %>"
									label="<%= StringUtil.appendParentheticalSuffix(uadDisplay.getTypeName(locale), (int)uadDisplay.searchCount(selectedUser.getUserId(), groupIds, null)) %>"
									name="uadRegistryKey"
									value="<%= uadDisplay.getTypeClass().getName() %>"
								/>

							<%
							}
							%>

						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-lg-8">
			<div class="sheet sheet-lg">
				<div class="sheet-header">
					<h2 class="sheet-title"><liferay-ui:message key="review-data" /></h2>
				</div>

				<div class="sheet-section">
					<h3 class="sheet-subtitle">
						<liferay-ui:message key="status-summary" />
					</h3>

					<strong><liferay-ui:message key="remaining-items" />: </strong><%= totalReviewableUADEntitiesCount %>
				</div>

				<div class="sheet-section">
					<h3 class="sheet-subtitle"><liferay-ui:message key="view-data" /></h3>

					<liferay-util:include page="/view_uad_entities.jsp" servletContext="<%= application %>" />
				</div>
			</div>
		</div>
	</div>
</div>

<portlet:renderURL var="reviewUADDataURL">
	<portlet:param name="mvcRenderCommandName" value="/review_uad_data" />
	<portlet:param name="p_u_i_d" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
	<portlet:param name="applicationKey" value="<%= viewUADEntitiesDisplay.getApplicationKey() %>" />
</portlet:renderURL>

<aui:script require="metal-dom/src/dom as dom,metal-uri/src/Uri">
	const Uri = metalUriSrcUri.default;

	const baseURL = '<%= reviewUADDataURL %>';

	const applicationPanelBodyClickListener = dom.delegate(
		<portlet:namespace />applicationPanelBody,
		'click',
		'input',
		function(event) {
			const url = new Uri(baseURL);

			url.setParameterValue('<portlet:namespace />applicationKey', event.target.value);

			Liferay.Util.navigate(url.toString());
		}
	);

	const entitiesTypePanelBodyClickListener = dom.delegate(
		<portlet:namespace />entitiesTypePanelBody,
		'click',
		'input',
		function(event) {
			const url = new Uri(baseURL);

			url.setParameterValue('<portlet:namespace />uadRegistryKey', event.target.value);

			Liferay.Util.navigate(url.toString());
		}
	);

	const scopePanelBodyClickListener = dom.delegate(
		<portlet:namespace />scopePanelBody,
		'click',
		'input',
		function(event) {
			const url = new Uri(baseURL);

			url.setParameterValue('<portlet:namespace />applicationKey', '');
			url.setParameterValue('<portlet:namespace />scope', event.target.value);

			Liferay.Util.navigate(url.toString());
		}
	);

	function handleDestroyPortlet() {
		applicationPanelBodyClickListener.removeListener();
		entitiesTypePanelBodyClickListener.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>