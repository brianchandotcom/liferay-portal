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

<%@ include file="/META-INF/resources/init.jsp" %>

<%
CommerceDashboardProductInstanceSelectorDisplayContext commerceDashboardProductInstanceSelectorDisplayContext = (CommerceDashboardProductInstanceSelectorDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPInstance> cpInstances = commerceDashboardProductInstanceSelectorDisplayContext.getCPInstances();
%>

<c:choose>
	<c:when test="<%= !cpInstances.isEmpty() %>">
		<liferay-portlet:actionURL name="editCommerceDashboardProductInstance" varImpl="editCommerceDashboardProductInstanceURL" />

		<aui:form action="<%= editCommerceDashboardProductInstanceURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<div class="form-group-autofit">
				<aui:select label="" name="cpInstanceId" title="products" wrapperCssClass="form-group-item">

					<%
					for (CPInstance cpInstance : cpInstances) {
					%>

						<aui:option label="<%= commerceDashboardProductInstanceSelectorDisplayContext.getCPInstanceLabel(cpInstance) %>" value="<%= cpInstance.getCPInstanceId() %>" />

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
			</div>

			<ol class="product-instances-list">

				<%
				Map<Long, Boolean> cpInstanceIds = commerceDashboardProductInstanceSelectorDisplayContext.getCPInstanceIds();

				int i = -1;

				for (Map.Entry<Long, Boolean> entry : cpInstanceIds.entrySet()) {
					i++;

					CPInstance cpInstance = commerceDashboardProductInstanceSelectorDisplayContext.getCPInstance(entry.getKey());

					if (cpInstance == null) {
						continue;
					}

					boolean visible = entry.getValue();

					CPDefinition cpDefinition = cpInstance.getCPDefinition();

					String color = commerceDashboardProductInstanceSelectorDisplayContext.getChartColor(i);
				%>

					<li>
						<div class="color-dot" style="background-color: <%= HtmlUtil.escapeCSS(color) %>"></div>

						<div class="product-instance">
							<h4 class="product-instance-title"><%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %></h4>

							<div class="product-instance-sku">
								<liferay-ui:message key="sku" /> <%= HtmlUtil.escape(cpInstance.getSku()) %>
							</div>
						</div>

						<div class="product-instance-selected">

							<%
							Map<String, String> data = new HashMap<>();

							if (visible) {
								data.put(Constants.CMD, Constants.DEACTIVATE);
							}
							else {
								data.put(Constants.CMD, Constants.VIEW);
							}

							data.put("cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));
							%>

							<clay:link
								data="<%= data %>"
								elementClasses="hide-show-link"
								href="javascript:;"
								icon="view"
								label='<%= StringUtil.toUpperCase(LanguageUtil.get(request, visible ? "hide" : "show")) %>'
							/>

							<%
							data = new HashMap<>();

							data.put(Constants.CMD, Constants.REMOVE);
							data.put("cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));
							%>

							<clay:link
								data="<%= data %>"
								elementClasses="remove-link"
								href="javascript:;"
								label='<%= LanguageUtil.get(request, "remove") %>'
							/>
						</div>
					</li>

				<%
				}
				%>

			</ol>
		</aui:form>

		<aui:script require="metal-dom/src/all/dom as dom">
			let domData = new dom.domData();

			var fm = document.querySelector('#<portlet:namespace/>fm');

			var editCommerceDashboardPeriodHandler = dom.delegate(
				fm,
				'click',
				'.product-instance-selected a',
				function(event) {
					event.preventDefault();

					var link = event.delegateTarget;

					var cmd = domData.get(link, '<%= Constants.CMD %>');
					var cpInstanceId = domData.get(link, 'cpInstanceId');

					submitForm(document.hrefFm, '<%= editCommerceDashboardProductInstanceURL %>'&<portlet:namespace /><%= Constants.CMD %>=' + cmd + '&<portlet:namespace />cpInstanceId=' + cpInstanceId);
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