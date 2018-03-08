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
String redirect = ParamUtil.getString(request, "redirect");

FragmentEntry fragmentEntry = fragmentEntryDisplayContext.getFragmentEntry();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<liferay-portlet:param name="fragmentEntryId" value="<%= String.valueOf(fragmentEntryDisplayContext.getFragmentEntryId()) %>" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--fragmentEntryLinkId" type="hidden" value="<%= fragmentEntryDisplayContext.getFragmentEntryLinkId() %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div class="fragment-entry-preview row">
						<div class="col-md-3 col-sm-6 col-xs-12">
							<p class="text-muted">
								<liferay-ui:message key="fragment-entry" />
							</p>

							<div class="fragment-entry-preview-container">
								<c:if test="<%= fragmentEntry != null %>">
									<div class="fragment-entry-preview row">
										<div class="col-md-8 col-sm-6 col-xs-12">

											<%
											String imagePreviewURL = fragmentEntry.getImagePreviewURL(themeDisplay);
											%>

											<c:choose>
												<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
													<liferay-frontend:vertical-card
														cssClass="entry-display-style"
														imageCSSClass="aspect-ratio-bg-contain"
														imageUrl="<%= imagePreviewURL %>"
														title="<%= fragmentEntry.getName() %>"
													>
														<liferay-frontend:vertical-card-header>

															<%
															Date statusDate = fragmentEntry.getStatusDate();
															%>

															<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
														</liferay-frontend:vertical-card-header>

														<liferay-frontend:vertical-card-footer>
															<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fragmentEntry.getStatus() %>" />
														</liferay-frontend:vertical-card-footer>
													</liferay-frontend:vertical-card>
												</c:when>
												<c:otherwise>
													<liferay-frontend:icon-vertical-card
														cssClass="entry-display-style"
														icon="page"
														title="<%= fragmentEntry.getName() %>"
													>
														<liferay-frontend:vertical-card-header>

															<%
															Date statusDate = fragmentEntry.getStatusDate();
															%>

															<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
														</liferay-frontend:vertical-card-header>

														<liferay-frontend:vertical-card-footer>
															<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fragmentEntry.getStatus() %>" />
														</liferay-frontend:vertical-card-footer>
													</liferay-frontend:icon-vertical-card>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</c:if>
							</div>

							<div class="button-holder">
								<aui:button name="fragmentEntrySelector" value='<%= (fragmentEntry != null) ? "change": "select" %>' />

								<c:if test="<%= fragmentEntry != null %>">
									<aui:button name="removeFragmentEntry" value="remove" />
								</c:if>
							</div>
						</div>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />fragmentEntrySelector').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<%= fragmentEntryDisplayContext.getEventName() %>',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								retrieveFragmentEntry(selectedItem.fragmentEntryId);
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-fragment-entry" />',
					url: '<%= fragmentEntryDisplayContext.getItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace/>removeFragmentEntry').on(
		'click',
		function() {
			retrieveFragmentEntry(-1);
		}
	);

	function retrieveFragmentEntry(fragmentEntryId) {
		var uri = '<%= configurationRenderURL %>';

		uri = Liferay.Util.addParams('<portlet:namespace />fragmentEntryId=' + fragmentEntryId, uri);

		location.href = uri;
	}
</aui:script>