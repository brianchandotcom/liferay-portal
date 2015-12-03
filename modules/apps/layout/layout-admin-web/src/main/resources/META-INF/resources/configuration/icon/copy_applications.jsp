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

<liferay-ui:icon
	id="copyApplications"
	message="copy-applications"
	url="javascript:;"
/>

<%
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();
%>

<portlet:actionURL name="copyApplications" var="copyApplicationsURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>

<aui:form action='<%= HttpUtil.addParameter(copyApplicationsURL, "refererPlid", plid) %>' name="fm">
	<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", layoutsAdminDisplayContext.getSelPlid()) %>' />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />
</aui:form>

<aui:script use="liferay-util-window">
	A.one('#<portlet:namespace />copyApplications').on(
		'click',
		function() {
			var content = A.one('#<portlet:namespace />copyPortletsFromPage');

			var popUp = Liferay.Util.Window.getWindow(
				{
					dialog: {
						bodyContent: content.show()
					},
					title: '<%= UnicodeLanguageUtil.get(request, "copy-applications") %>'
				}
			);

			popUp.show();

			var submitButton = popUp.get('contentBox').one('#<portlet:namespace />copySubmitButton');

			if (submitButton) {
				submitButton.on(
					'click',
					function(event) {
						popUp.hide();

						var form = A.one('#<portlet:namespace />fm');

						if (form) {
							form.append(content);

							submitForm(form);
						}
					}
				);
			}
		}
	);
</aui:script>