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
CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCountry commerceCountry = commerceCountriesDisplayContext.getCommerceCountry();

List<CommerceChannel> commerceChannels = commerceCountriesDisplayContext.getCommerceChannels();
long[] commerceChannelIds = commerceCountriesDisplayContext.getCommerceChannelRelCommerceChannelIds();
%>

<portlet:actionURL name="editCommerceCountry" var="editCommerceCountryActionURL" />

<aui:form action="<%= editCommerceCountryActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateChannels" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceCountryId" type="hidden" value="<%= commerceCountry.getCommerceCountryId() %>" />
	<aui:input name="commerceChannelIds" type="hidden" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input checked="<%= commerceCountry.isChannelFilterEnabled() %>" inlineLabel="right" label="enable-filter-channels" labelCssClass="simple-toggle-switch" name="channelFilterEnabled" type="toggle-switch" value="<%= commerceCountry.isChannelFilterEnabled() %>" />

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
		</aui:fieldset-group>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />fulfillCommerceChannelIds(e) {
		var form = window.document['<portlet:namespace />fm'];
		var values = Liferay.Util.listCheckedExcept(
			form,
			'<portlet:namespace />allRowIds'
		);
		form['<portlet:namespace />commerceChannelIds'].value = values;
		return values;
	}

	<portlet:namespace />fulfillCommerceChannelIds();
</aui:script>