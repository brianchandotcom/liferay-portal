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

<%@ include file="/html/portlet/navigation/init.jsp" %>

<aui:row>
	<aui:col width="<%= 50 %>">
		<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

		<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

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
				<div class="<%= displayStyle.equals("[custom]") ? "" : "hide" %>" id="<portlet:namespace />customDisplayOptions">
					<aui:select label="header" name="preferences--headerType--" value="<%= headerType %>">
						<aui:option label="none" />
						<aui:option label="portlet-title" />
						<aui:option label="root-layout" />
						<aui:option label="breadcrumb" />
					</aui:select>

					<aui:select label="root-layout" name="preferences--rootLayoutType--" value="<%= rootLayoutType %>">
						<aui:option label="parent-at-level" value="absolute" />
						<aui:option label="relative-parent-up-by" value="relative" />
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

					<aui:select name="preferences--includedLayouts--" value="<%= includedLayouts %>">
						<aui:option label="auto" />
						<aui:option label="all" />
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

<aui:script sandbox="<%= true %>">
	var form = $('#<portlet:namespace />fm');

	var customDisplayOptions = form.fm('customDisplayOptions');
	var selectBulletStyle = form.fm('bulletStyle');
	var selectDisplayStyle = form.fm('displayStyle');
	var selectHeaderType = form.fm('headerType');
	var selectIncludedLayouts = form.fm('includedLayouts');
	var selectNestedChildren = form.fm('nestedChildren');
	var selectRootLayoutLevel = form.fm('rootLayoutLevel');
	var selectRootLayoutType = form.fm('rootLayoutType');

	var curPortletBoundaryId = '#p_p_id_<%= HtmlUtil.escapeJS(portletResource) %>_';

	form.on(
		'change',
		'select',
		function() {
			var data = {
				bulletStyle: selectBulletStyle.val(),
				displayStyle: selectDisplayStyle.val(),
				preview: true
			};

			var hide = true;

			if (selectDisplayStyle.val() == '[custom]') {
				hide = false;

				data.headerType = selectHeaderType.val();
				data.includedLayouts = selectIncludedLayouts.val();
				data.nestedChildren = selectNestedChildren.val();
				data.rootLayoutLevel = selectRootLayoutLevel.val();
				data.rootLayoutType = selectRootLayoutType.val();
			}

			customDisplayOptions.toggleClass('hide', hide);

			data = Liferay.Util.ns('_<%= HtmlUtil.escapeJS(portletResource) %>_', data);

			Liferay.Portlet.refresh(curPortletBoundaryId, data);
		}
	);
</aui:script>