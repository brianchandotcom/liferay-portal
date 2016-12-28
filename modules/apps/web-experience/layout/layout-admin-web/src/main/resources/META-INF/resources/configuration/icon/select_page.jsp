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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectPage");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<div class="container-fluid-1280">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
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
		</aui:fieldset>
	</aui:fieldset-group>
</div>

<aui:script use="aui-base">
	var copyLayoutId = A.one('#<portlet:namespace />copyLayoutId');

	Liferay.Util.getOpener().Liferay.fire(
		'<%= HtmlUtil.escapeJS(eventName) %>',
		{
			data: copyLayoutId.getDOMNode()
		}
	);
</aui:script>