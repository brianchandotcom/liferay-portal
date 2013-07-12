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

<%@ include file="/html/taglib/aui/panel/init.jsp" %>

</div>

<aui:script use="anim,aui-panel-deprecated">
	var container = new A.Panel(
		{
			bodyContent: A.one('#<%= id %>bodyContent'),
			collapsed: <%= collapsed %>,
			collapsible: <%= collapsible %>,
			contentBox: '#<%= id %>',
			headerContent: '<liferay-ui:message key="<%= label %>" />'

			<c:if test="<%= toolTags != null %>">
				,icons: [

				<%
				for (int i = 0; i < toolTags.size(); i++) {
					ToolTag toolTag = toolTags.get(i);
				%>

					{
						icon: '<%= toolTag.getIcon() %>',
						id: '<%= toolTag.getId() %>',
						on: {
							click: function(event) {
								<%= toolTag.getHandler() %>
							}
						}

					}

					<c:if test="<%= (i + 1) < toolTags.size() %>">
						,
					</c:if>

				<%
				}
				%>

				]
			</c:if>

		}
	).render();
</aui:script>