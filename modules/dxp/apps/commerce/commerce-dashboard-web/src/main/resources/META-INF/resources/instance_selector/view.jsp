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
CommerceDashboardInstanceSelectorDisplayContext commerceDashboardInstanceSelectorDisplayContext = (CommerceDashboardInstanceSelectorDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPInstance> cpInstances = commerceDashboardInstanceSelectorDisplayContext.getCPInstances();
%>

<div class="wrapper">
	<c:choose>
		<c:when test="<%= !cpInstances.isEmpty() %>">
			<liferay-portlet:actionURL name="editCommerceDashboardInstance" varImpl="editCommerceDashboardInstanceURL" />

			<aui:form action="<%= editCommerceDashboardInstanceURL.toString() %>" cssClass="form-group-autofit" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<aui:select label="" name="cpInstanceId" title="products" wrapperCssClass="form-group-item">

					<%
					for (CPInstance cpInstance : cpInstances) {
					%>

						<aui:option label="<%= commerceDashboardInstanceSelectorDisplayContext.getCPInstanceLabel(cpInstance) %>" value="<%= cpInstance.getCPInstanceId() %>" />

					<%
					}
					%>

				</aui:select>

				<clay:button
					ariaLabel='<%= LanguageUtil.get(request, "add") %>'
					icon="plus"
					monospaced="<%= true %>"
					type="submit"
				/>
			</aui:form>

			<ol class="instances-list" id="<portlet:namespace />instancesList">

				<%
				Map<Long, Boolean> cpInstanceIds = commerceDashboardInstanceSelectorDisplayContext.getCPInstanceIds();

				int i = -1;

				for (Map.Entry<Long, Boolean> entry : cpInstanceIds.entrySet()) {
					i++;

					CPInstance cpInstance = commerceDashboardInstanceSelectorDisplayContext.getCPInstance(entry.getKey());

					if (cpInstance == null) {
						continue;
					}

					boolean visible = entry.getValue();

					CPDefinition cpDefinition = cpInstance.getCPDefinition();

					String color = commerceDashboardInstanceSelectorDisplayContext.getChartColor(i);
				%>

					<li class="instances-list-item">
						<div class="color-dot <%= visible ? "is-visible" : "is-hidden" %>" style="color: <%= color %>"></div>

						<div class="instance <%= visible ? "is-visible" : "is-hidden" %>">
							<h4 class="instance-title"><%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %></h4>

							<div class="instance-sku">
								<liferay-ui:message key="sku" /> <%= HtmlUtil.escape(cpInstance.getSku()) %>
							</div>
						</div>

						<div class="instance-selected">

							<%
							if (visible) {
								editCommerceDashboardInstanceURL.setParameter(Constants.CMD, Constants.DEACTIVATE);
							}
							else {
								editCommerceDashboardInstanceURL.setParameter(Constants.CMD, Constants.VIEW);
							}

							editCommerceDashboardInstanceURL.setParameter("cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));
							%>

							<clay:link
								elementClasses="hide-show-link"
								href="<%= editCommerceDashboardInstanceURL.toString() %>"
								icon="view"
								label='<%= StringUtil.toUpperCase(LanguageUtil.get(request, visible ? "hide" : "show")) %>'
							/>

							<%
							editCommerceDashboardInstanceURL.setParameter(Constants.CMD, Constants.REMOVE);
							%>

							<clay:link
								elementClasses="remove-link"
								href="<%= editCommerceDashboardInstanceURL.toString() %>"
								label='<%= LanguageUtil.get(request, "remove") %>'
							/>
						</div>
					</li>

				<%
				}
				%>

			</ol>

			<aui:script require="metal-dom/src/all/dom as dom">
				var instancesList = document.querySelector('#<portlet:namespace/>instancesList');

				var editCommerceDashboardPeriodHandler = dom.delegate(
					instancesList,
					'click',
					'.instance-selected a',
					function(event) {
						event.preventDefault();

						submitForm(document.hrefFm, event.delegateTarget.href);
					}
				);

				function removeListener() {
					editCommerceDashboardPeriodHandler.removeListener();

					Liferay.detach('destroyPortlet', removeListener);
				}

				Liferay.on('destroyPortlet', removeListener);
			</aui:script>
		</c:when>
		<c:otherwise>
			<clay:alert
				message='<%= LanguageUtil.get(request, "there-are-no-skus") %>'
				title="Info"
			/>
		</c:otherwise>
	</c:choose>
</div>