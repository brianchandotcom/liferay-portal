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
CPContentConfigurationDisplayContext cpContentConfigurationDisplayContext = (CPContentConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<div id="<portlet:namespace/>configuration-tabs">
				<ul class="nav nav-tabs">

					<%
					for (CPType cpType : cpContentConfigurationDisplayContext.getCPTypes()) {
					%>

						<li>
							<a href="#<%= cpType.getName() %>"><%= cpType.getLabel(locale) %></a>
						</li>

					<%
					}
					%>

				</ul>

				<div class="tab-content">

					<%
					for (CPType cpType : cpContentConfigurationDisplayContext.getCPTypes()) {
						Class<?> cpTypeClass = cpType.getClass();
					%>

						<div id="<%= cpType.getName() %>">
							<aui:fieldset-group markupView="lexicon">
								<aui:fieldset>
									<aui:input name="preferences--cpType--" type="hidden" value="<%= cpType.getName() %>" />

									<div class="display-template">
										<liferay-ddm:template-selector
											className="<%= cpTypeClass.getName() %>"
											displayStyle="<%= cpContentConfigurationDisplayContext.getDisplayStyle(cpType.getName()) %>"
											displayStyleGroupId="<%= cpContentConfigurationDisplayContext.getDisplayStyleGroupId(cpType.getName()) %>"
											refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
											showEmptyOption="<%= true %>"
										/>
									</div>
								</aui:fieldset>
							</aui:fieldset-group>
						</div>

					<%
					}
					%>

				</div>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-tabview">
	new A.TabView(
		{
			srcNode: '#<portlet:namespace/>configuration-tabs'
		}
	).render();
</aui:script>