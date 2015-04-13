<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/navigation/init.jsp" %>

<aui:row>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

		<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

		<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

			<aui:fieldset column="<%= true %>">
				<aui:select name="preferences--displayStyle--">
					<aui:option label="custom" selected='<%= displayStyle.equals("[custom]") %>' value="[custom]" />

					<optgroup label="<liferay-ui:message key="predefined" />">

						<%
						for (String displayStyleOption : PropsValues.NAVIGATION_DISPLAY_STYLE_OPTIONS) {
						%>

							<aui:option label="<%= displayStyleOption %>" selected="<%= displayStyle.equals(displayStyleOption) %>" />

						<%
						}
						%>

					</optgroup>
				</aui:select>

				<aui:select name="preferences--bulletStyle--">

					<%
					String[] bulletStyleOptions = theme.getSettingOptions("bullet-style");
					%>

					<c:choose>
						<c:when test="<%= ArrayUtil.isEmpty(bulletStyleOptions) %>">
							<aui:option label="default" value="" />
						</c:when>
						<c:otherwise>

							<%
							for (String bulletStyleOption : bulletStyleOptions) {
							%>

								<aui:option label="<%= bulletStyleOption %>" selected="<%= bulletStyle.equals(bulletStyleOption) %>" />

							<%
							}
							%>

						</c:otherwise>
					</c:choose>
				</aui:select>
			</aui:fieldset>

			<aui:fieldset column="<%= true %>">
				<div id="<portlet:namespace />customDisplayOptions">
					<aui:select label="header" name="preferences--headerType--">
						<aui:option label="none" selected='<%= headerType.equals("none") %>' />
						<aui:option label="portlet-title" selected='<%= headerType.equals("portlet-title") %>' />
						<aui:option label="root-layout" selected='<%= headerType.equals("root-layout") %>' />
						<aui:option label="breadcrumb" selected='<%= headerType.equals("breadcrumb") %>' />
					</aui:select>

					<aui:select label="root-layout" name="preferences--rootLayoutType--">
						<aui:option label="parent-at-level" selected='<%= rootLayoutType.equals("absolute") %>' value="absolute" />
						<aui:option label="relative-parent-up-by" selected='<%= rootLayoutType.equals("relative") %>' value="relative" />
					</aui:select>

					<aui:select name="preferences--rootLayoutLevel--">

						<%
						for (int i = 0; i <= 4; i++) {
						%>

							<aui:option label="<%= i %>" selected="<%= rootLayoutLevel == i %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select name="preferences--includedLayouts--">
						<aui:option label="auto" selected='<%= includedLayouts.equals("auto") %>' />
						<aui:option label="all" selected='<%= includedLayouts.equals("all") %>' />
					</aui:select>

					<aui:select name="preferences--nestedChildren--">
						<aui:option label="yes" selected="<%= nestedChildren %>" value="1" />
						<aui:option label="no" selected="<%= !nestedChildren %>" value="0" />
					</aui:select>
				</div>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:col>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/navigation/view"
			showBorders="<%= true %>"
		/>
	</aui:col>
</aui:row>

<aui:script use="aui-base">
	var customDisplayOptions = A.one('#<portlet:namespace />customDisplayOptions');
	var selectBulletStyle = A.one('#<portlet:namespace />bulletStyle');
	var selectDisplayStyle = A.one('#<portlet:namespace />displayStyle');
	var selectHeaderType = A.one('#<portlet:namespace />headerType');
	var selectIncludedLayouts = A.one('#<portlet:namespace />includedLayouts');
	var selectNestedChildren = A.one('#<portlet:namespace />nestedChildren');
	var selectRootLayoutLevel = A.one('#<portlet:namespace />rootLayoutLevel');
	var selectRootLayoutType = A.one('#<portlet:namespace />rootLayoutType');

	var selects = A.all('#<portlet:namespace />fm select');

	var curPortletBoundaryId = '#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_';

	var toggleCustomFields = function() {
		if (customDisplayOptions) {
			var data = {};

			var action = 'hide';

			var displayStyle = selectDisplayStyle.val();

			if (displayStyle == '[custom]') {
				action = 'show';

				data['_<%= HtmlUtil.escapeJS(portletResource) %>_headerType'] = selectHeaderType.val();
				data['_<%= HtmlUtil.escapeJS(portletResource) %>_includedLayouts'] = selectIncludedLayouts.val();
				data['_<%= HtmlUtil.escapeJS(portletResource) %>_nestedChildren'] = selectNestedChildren.val();
				data['_<%= HtmlUtil.escapeJS(portletResource) %>_rootLayoutLevel'] = selectRootLayoutLevel.val();
				data['_<%= HtmlUtil.escapeJS(portletResource) %>_rootLayoutType'] = selectRootLayoutType.val();
			}

			customDisplayOptions[action]();

			data['_<%= HtmlUtil.escapeJS(portletResource) %>_bulletStyle'] = selectBulletStyle.val();
			data['_<%= HtmlUtil.escapeJS(portletResource) %>_displayStyle'] = selectDisplayStyle.val();
			data['_<%= HtmlUtil.escapeJS(portletResource) %>_preview'] = true;

			Liferay.Portlet.refresh(curPortletBoundaryId, data);
		}
	}

	selects.on('change', toggleCustomFields);

	toggleCustomFields();
</aui:script>