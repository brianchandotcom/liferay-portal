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
String redirect = layoutsAdminDisplayContext.getRedirect();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "copy-applications"));
%>

<portlet:actionURL name="/layout/copy_applications" var="copyApplicationsURL" />

<liferay-frontend:edit-form
	action="<%= copyApplicationsURL %>"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= layoutsAdminDisplayContext.getSelGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<c:if test="<%= selLayout != null %>">
					<div class="alert alert-info">
						<liferay-ui:message arguments="<%= HtmlUtil.escape(selLayout.getName(locale)) %>" key="the-applications-in-page-x-will-be-replaced-with-the-ones-in-the-page-you-select-below" translateArguments="<%= false %>" />
					</div>
				</c:if>

				<aui:select label="copy-from-page" name="copyLayoutId">

					<%
					List<LayoutDescription> layoutDescriptions = (List<LayoutDescription>)request.getAttribute(WebKeys.LAYOUT_DESCRIPTIONS);

					for (LayoutDescription layoutDescription : layoutDescriptions) {
						Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

						if (layoutDescriptionLayout != null) {
					%>

							<aui:option disabled="<%= (selLayout != null) && selLayout.getPlid() == layoutDescriptionLayout.getPlid() %>" label="<%= layoutDescription.getDisplayName() %>" value="<%= layoutDescriptionLayout.getLayoutId() %>" />

					<%
						}
					}
					%>

				</aui:select>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="save" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>