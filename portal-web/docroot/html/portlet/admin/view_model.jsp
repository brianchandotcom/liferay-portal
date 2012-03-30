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

<%@ include file="/html/portlet/admin/init.jsp" %>

<%
String randomId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ResourcePermission resourcePermission = (ResourcePermission)row.getObject();

String modelString = StringPool.BLANK;
String resourceTitle = resourcePermission.getName();

try {
	BaseModel model = PortalUtil.getBaseModel(resourcePermission);

	Document doc = SAXReaderUtil.read(new UnsyncStringReader(model.toXmlString()));

	Element root = doc.getRootElement();

	List<Element> elements = root.elements("column");

	if (elements.isEmpty()) {
		modelString = "<table class=\"lfr-table\">\n</table>";
	}
	else {
		StringBundler sb = new StringBundler(elements.size() * 5 + 2);

		sb.append("<table class=\"lfr-table\">\n");

		for (Element column : elements) {
			String name = column.elementText("column-name");
			String value = column.elementText("column-value");

			sb.append("<tr><td align=\"right\" valign=\"top\"><strong>");
			sb.append(name);
			sb.append("</strong></td><td>");
			sb.append(value);
			sb.append("</td></tr>");
		}

		sb.append("</table>");

		modelString = sb.toString();
	}

	String[] parts = StringUtil.split(resourcePermission.getName(), CharPool.PERIOD);

	resourceTitle = parts[parts.length - 1] + ", " + resourcePermission.getPrimKey();
}
catch (Exception e) {
	modelString = e.toString();
}
%>

<div style="overflow: auto; vertical-align: top;">
	<liferay-ui:panel-container cssClass="model-details" id='<%= randomId + "modelDetails" %>'>
		<liferay-ui:panel defaultState="closed" id='<%= randomId + "adminModelDetailsPanel" %>' title="<%= resourceTitle %>">
			<div style="height: 100px; width: 350px;"><%= modelString %></div>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</div>