<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAddressDisplayContext commerceAddressDisplayContext = (CommerceAddressDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("commerceAddressDisplayContext", commerceAddressDisplayContext);

SearchContainer<CommerceAddress> commerceAddressSearchContainer = commerceAddressDisplayContext.getSearchContainer();

PortletURL portletURL = commerceAddressDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceAddresses");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-ddm:template-renderer
	className="<%= CommerceAddressContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceAddressDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceAddressDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceAddressSearchContainer.getResults() %>"
>
	<div class="container-fluid-1280" id="<portlet:namespace />addressesContainer">
		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<div class="addresses-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="commerceAddresses"
					iteratorURL="<%= portletURL %>"
					searchContainer="<%= commerceAddressSearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.commerce.model.CommerceAddress"
						cssClass="entry-display-style"
						keyProperty="commerceAddressId"
						modelVar="commerceAddress"
					>
						<liferay-ui:search-container-column-text
							href="<%= commerceAddressDisplayContext.getEditCommerceAddressURL(commerceAddress.getCommerceAddressId()) %>"
							property="name"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							property="street1"
						/>

						<liferay-ui:search-container-column-text
							property="city"
						/>

						<liferay-ui:search-container-column-text
							property="zip"
						/>

						<liferay-ui:search-container-column-date
							name="create-date"
							property="createDate"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							property="modifiedDate"
						/>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action-column"
							path="/address_action.jsp"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</aui:form>

		<aui:button-row>
			<aui:button cssClass="btn-lg" href="<%= commerceAddressDisplayContext.getAddCommerceAddressURL() %>" name="addAddressButton" value='<%= LanguageUtil.get(request, "add-address") %>' />
		</aui:button-row>
	</div>
</liferay-ddm:template-renderer>