<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_directory/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:layout>
	<aui:column columnWidth="50">
		<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

		<aui:form action="<%= configurationURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

			<aui:fieldset column="<%= true %>">
				<aui:select name="preferences--displayStyle--">
					<aui:option label="icon" selected='<%= displayStyle.equals("icon") %>' />
					<aui:option label="descriptive" selected='<%= displayStyle.equals("descriptive") %>' />
					<aui:option label="custom" selected='<%= displayStyle.equals("[custom]") %>' value="[custom]" />

					<optgroup label="<liferay-ui:message key="predefined" />">

						<%
						for (String displayStyleOption : PropsValues.SITES_DIRECTORY_DISPLAY_STYLE_OPTIONS) {
						%>

							<aui:option label="<%= displayStyleOption %>" selected="<%= displayStyle.equals(displayStyleOption) %>" />

						<%
						}
						%>

					</optgroup>
				</aui:select>

				<div id="<portlet:namespace />bulletStyleOptions">
					<aui:select name="preferences--bulletStyle--">

						<%
						String[] bulletStyleOptions = theme.getSettingOptions("bullet-style");
						%>

						<c:choose>
							<c:when test="<%= (bulletStyleOptions == null) || (bulletStyleOptions.length == 0) %>">
								<aui:option label="default" value="" />
							</c:when>
							<c:otherwise>

								<%
								for (String bulletStyleOption : bulletStyleOptions) {
								%>

									<aui:option label="<%= LanguageUtil.get(pageContext, bulletStyleOption) %>" selected="<%= bulletStyle.equals(bulletStyleOption) %>" />

								<%
								}
								%>

							</c:otherwise>
						</c:choose>
					</aui:select>
				</div>
			</aui:fieldset>

			<aui:fieldset column="<%= true %>">
				<div id="<portlet:namespace />customDisplayOptions">
					<aui:select label="header" name="preferences--headerType--">
						<aui:option label="none" selected='<%= headerType.equals("none") %>' />
						<aui:option label="portlet-title" selected='<%= headerType.equals("portlet-title") %>' />
						<aui:option label="root-group" selected='<%= headerType.equals("root-group") %>' />
						<aui:option label="breadcrumb" selected='<%= headerType.equals("breadcrumb") %>' />
					</aui:select>

					<aui:select label="root-group" name="preferences--rootGroupType--">
						<aui:option label="parent-at-level" selected='<%= rootGroupType.equals("absolute") %>' value="absolute" />
						<aui:option label="relative-parent-up-by" selected='<%= rootGroupType.equals("relative") %>' value="relative" />
					</aui:select>

					<aui:select name="preferences--rootGroupLevel--">

						<%
						for (int i = 0; i <= 4; i++) {
						%>

							<aui:option label="<%= i %>" selected="<%= rootGroupLevel == i %>" />

						<%
						}
						%>

					</aui:select>

					<div id="<portlet:namespace />customListDisplayOptions">
						<aui:select name="preferences--includedGroups--">
							<aui:option label="auto" selected='<%= includedGroups.equals("auto") %>' />
							<aui:option label="all" selected='<%= includedGroups.equals("all") %>' />
						</aui:select>

						<aui:select name="preferences--nestedChildren--">
							<aui:option label="yes" selected="<%= nestedChildren %>" value="1" />
							<aui:option label="no" selected="<%= !nestedChildren %>" value="0" />
						</aui:select>
					</div>
				</div>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</aui:column>
	<aui:column columnWidth="50">
		<liferay-portlet:preview
			portletName="<%= portletResource %>"
			queryString="struts_action=/navigation/view"
			showBorders="<%= true %>"
		/>
	</aui:column>
</aui:layout>

<aui:script use="aui-base">
	var bulletStyleOptions = A.one('#<portlet:namespace />bulletStyleOptions');
	var customDisplayOptions = A.one('#<portlet:namespace />customDisplayOptions');
	var customListDisplayOptions = A.one('#<portlet:namespace />customListDisplayOptions');
	var selectBulletStyle = A.one('#<portlet:namespace />bulletStyle');
	var selectDisplayStyle = A.one('#<portlet:namespace />displayStyle');
	var selectHeaderType = A.one('#<portlet:namespace />headerType');
	var selectIncludedGroups = A.one('#<portlet:namespace />includedGroups');
	var selectNestedChildren = A.one('#<portlet:namespace />nestedChildren');
	var selectRootGroupLevel = A.one('#<portlet:namespace />rootGroupLevel');
	var selectRootGroupType = A.one('#<portlet:namespace />rootGroupType');

	var selects = A.all('#<portlet:namespace />fm select');

	var curPortletBoundaryId = '#p_p_id_<%= portletResource %>_';

	var toggleCustomFields = function() {
		if (customDisplayOptions) {
			var data = {};

			var bulletStyleOptionsAction = 'show';
			var customDisplayOptionsAction = 'hide';
			var customListDisplayOptionsAction = 'show';

			var displayStyle = selectDisplayStyle.val();

			if ((displayStyle == '[custom]') || (displayStyle == 'icon') || (displayStyle == 'descriptive')) {
				customDisplayOptionsAction = 'show';

				data['_<%= portletResource %>_headerType'] = selectHeaderType.val();
				data['_<%= portletResource %>_includedGroups'] = selectIncludedGroups.val();
				data['_<%= portletResource %>_nestedChildren'] = selectNestedChildren.val();
				data['_<%= portletResource %>_rootGroupLevel'] = selectRootGroupLevel.val();
				data['_<%= portletResource %>_rootGroupType'] = selectRootGroupType.val();
			}

			if ((displayStyle == 'icon') || (displayStyle == 'descriptive')) {
				bulletStyleOptionsAction = 'hide';
				customListDisplayOptionsAction = 'hide';
			}

			bulletStyleOptions[bulletStyleOptionsAction]();
			customDisplayOptions[customDisplayOptionsAction]();
			customListDisplayOptions[customListDisplayOptionsAction]();

			data['_<%= portletResource %>_bulletStyle'] = selectBulletStyle.val();
			data['_<%= portletResource %>_displayStyle'] = selectDisplayStyle.val();

			Liferay.Portlet.refresh(curPortletBoundaryId, data);
		}
	}

	selects.on('change', toggleCustomFields);

	toggleCustomFields();
</aui:script>