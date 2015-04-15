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
String rootNodeName = StringPool.BLANK;

List<LayoutDescription> layoutDescriptions = LayoutListUtil.getLayoutDescriptions(layout.getGroupId(), false, rootNodeName, locale);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset>
		<aui:select label="root-layout" name="preferences--rootLayoutUuid--">
			<aui:option value="" />

			<%
			for (LayoutDescription layoutDescription : layoutDescriptions) {
				Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

				if (layoutDescriptionLayout != null) {
			%>

				<aui:option label="<%= layoutDescription.getDisplayName() %>" selected="<%= layoutDescriptionLayout.getUuid().equals(rootLayoutUuid) %>" value="<%= layoutDescriptionLayout.getUuid() %>" />

			<%
				}
			}
			%>

		</aui:select>

		<aui:select name="preferences--displayDepth--">
			<aui:option label="unlimited" value="0" />

			<%
			for (int i = 1; i <= 20; i++) {
			%>

				<aui:option label="<%= i %>" selected="<%= displayDepth == i %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="preferences--includeRootInTree--" type="checkbox" value="<%= includeRootInTree %>" />

		<aui:input name="preferences--showCurrentPage--" type="checkbox" value="<%= showCurrentPage %>" />

		<aui:input name="preferences--useHtmlTitle--" type="checkbox" value="<%= useHtmlTitle %>" />

		<aui:input name="preferences--showHiddenPages--" type="checkbox" value="<%= showHiddenPages %>" />

		<div class="display-template">
			<liferay-ui:ddm-template-selector
				className="<%= LayoutSet.class.getName() %>"
				displayStyle="<%= displayStyle %>"
				displayStyleGroupId="<%= displayStyleGroupId %>"
				refreshURL="<%= configurationRenderURL %>"
				showEmptyOption="<%= true %>"
			/>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>