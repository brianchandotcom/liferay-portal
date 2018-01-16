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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetTagsDisplayContext.getAssetTitle());
%>

<portlet:actionURL name="editTag" var="editTagURL">
	<portlet:param name="mvcPath" value="/edit_tag.jsp" />
</portlet:actionURL>

<aui:form action="<%= editTagURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= AssetTagException.class %>">

		<%
		AssetTagException ate = (AssetTagException)errorException;
		%>

		<c:if test="<%= ate.getType() == AssetTagException.INVALID_CHARACTER %>">
			<liferay-ui:message arguments='<%= new String[] {"tag-name", StringUtil.merge(AssetHelper.INVALID_CHARACTERS, StringPool.SPACE)} %>' key="the-x-cannot-contain-the-following-invalid-characters-x" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DuplicateTagException.class %>" message="a-tag-with-that-name-already-exists" />

	<aui:model-context bean="<%= assetTagsDisplayContext.getTag() %>" model="<%= AssetTag.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="tagId" type="hidden" value="<%= assetTagsDisplayContext.getTagId() %>" />

			<aui:input autoFocus="<%= true %>" cssClass="tag-name" name="name" placeholder="name" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>