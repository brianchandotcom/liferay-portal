<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Country country = commerceCountriesDisplayContext.getCountry();

List<CommerceChannel> commerceChannels = commerceCountriesDisplayContext.getCommerceChannels();
long[] commerceChannelIds = commerceCountriesDisplayContext.getCommerceChannelRelCommerceChannelIds();
%>

<portlet:actionURL name="/commerce_country/edit_commerce_country" var="editCommerceCountryActionURL" />

<aui:form action="<%= editCommerceCountryActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateChannels" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="countryId" type="hidden" value="<%= country.getCountryId() %>" />
	<aui:input name="commerceChannelIds" type="hidden" />

	<div class="lfr-form-content">
		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input checked="<%= country.isGroupFilterEnabled() %>" inlineLabel="right" label="enable-filter-channels" labelCssClass="simple-toggle-switch" name="channelFilterEnabled" type="toggle-switch" value="<%= country.isGroupFilterEnabled() %>" />

					<c:choose>
						<c:when test="<%= commerceChannels.isEmpty() %>">
							<div class="alert alert-info">
								<liferay-ui:message key="there-are-no-channels" />
							</div>
						</c:when>
						<c:otherwise>

							<%
							for (CommerceChannel commerceChannel : commerceChannels) {
							%>

								<aui:input checked="<%= ArrayUtil.contains(commerceChannelIds, commerceChannel.getCommerceChannelId()) %>" label="<%= commerceChannel.getName() %>" name='<%= "commerceChannelId_" + commerceChannel.getCommerceChannelId() %>' onChange='<%= liferayPortletResponse.getNamespace() + "fulfillCommerceChannelIds();" %>' type="checkbox" value="<%= commerceChannel.getCommerceChannelId() %>" />

							<%
							}
							%>

						</c:otherwise>
					</c:choose>

					<aui:button-row>
						<aui:button cssClass="btn-lg" type="submit" />

						<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
					</aui:button-row>
				</aui:fieldset>
			</div>
		</div>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />fulfillCommerceChannelIds(e) {
		var form = window.document['<portlet:namespace />fm'];
		var values = Liferay.Util.getCheckedCheckboxes(
			form,
			'<portlet:namespace />allRowIds'
		);
		form['<portlet:namespace />commerceChannelIds'].value = values;
		return values;
	}

	<portlet:namespace />fulfillCommerceChannelIds();
</aui:script>