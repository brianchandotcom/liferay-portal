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
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

COREntry corEntry = corEntryDisplayContext.getCOREntry();
long corEntryId = corEntryDisplayContext.getCOREntryId();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((corEntry != null) && (corEntry.getExpirationDate() != null)) {
	neverExpire = false;
}

String type = BeanParamUtil.getString(corEntry, renderRequest, "type", COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT);
%>

<portlet:actionURL name="/cor_entry/edit_cor_entry" var="editCOREntryActionURL" />

<aui:form action="<%= editCOREntryActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (corEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= corEntry.getExternalReferenceCode() %>" />
	<aui:input name="corEntryId" type="hidden" value="<%= corEntryId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= corEntry %>" model="<%= COREntry.class %>" />

	<div class="row">
		<div class="col-12 col-xl-8">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<div class="row">
					<div class="col">
						<aui:input autoFocus="<%= true %>" label="name" name="name" required="<%= true %>" />
					</div>

					<div class="col-auto">
						<aui:select label="type" name="type" required="<%= true %>">

							<%
							for (COREntryType corEntryType : corEntryDisplayContext.getCOREntryTypes()) {
							%>

								<aui:option label="<%= corEntryType.getLabel(locale) %>" value="<%= corEntryType.getKey() %>" />

							<%
							}
							%>

						</aui:select>
					</div>

					<div class="col-auto">
						<aui:input label='<%= HtmlUtil.escape("active") %>' name="active" type="toggle-switch" value="<%= corEntry.isActive() %>" />
					</div>
				</div>

				<div class="row">
					<div class="col">
						<aui:input name="description" type="textarea" value="<%= corEntry.getDescription() %>" />
					</div>
				</div>

				<div class="row">
					<div class="col">
						<aui:input name="priority" />
					</div>
				</div>
			</commerce-ui:panel>

			<%
			COREntryTypeJSPContributor corEntryTypeJSPContributor = corEntryDisplayContext.getCOREntryTypeJSPContributor(type);
			%>

			<c:if test="<%= corEntryTypeJSPContributor != null %>">

				<%
				corEntryTypeJSPContributor.render(corEntryId, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
				%>

			</c:if>
		</div>

		<div class="col-12 col-xl-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "schedule") %>'
			>
				<liferay-ui:error exception="<%= COREntryExpirationDateException.class %>" message="please-select-a-valid-expiration-date" />

				<aui:input formName="fm" label="publish-date" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"corEntryId", corEntryId
		).put(
			"currentURL", currentURL
		).build()
	%>'
	module="js/details"
/>