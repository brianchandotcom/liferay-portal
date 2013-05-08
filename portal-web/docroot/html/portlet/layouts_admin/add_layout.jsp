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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
long stagingGroupId = ((Long)request.getAttribute("edit_pages.jsp-stagingGroupId")).longValue();
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();
long layoutId = ((Long)request.getAttribute("edit_pages.jsp-layoutId")).longValue();
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

PortletURL redirectURL = ((PortletURL)request.getAttribute("edit_pages.jsp-redirectURL"));

List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
%>

<div class="hide" id="<portlet:namespace />addLayout">
	<aui:model-context model="<%= Layout.class %>" />

	<portlet:actionURL var="editPageURL">
		<portlet:param name="struts_action" value="/layouts_admin/edit_layouts" />
	</portlet:actionURL>

	<aui:form action="<%= editPageURL %>" enctype="multipart/form-data" method="post" name="fm2">
		<aui:input id="addLayoutCmd" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input id="addLayoutRedirect" name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selPlid) %>' />
		<aui:input id="addLayoutCloseRedirect" name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
		<aui:input id="addLayoutGroupId" name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input id="addLayoutLiveGroupId" name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
		<aui:input id="addLayoutStagingGroupId" name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
		<aui:input id="addLayoutPrivateLayoutId" name="privateLayout" type="hidden" value="<%= privateLayout %>" />
		<aui:input id="addLayoutParentPlid" name="parentPlid" type="hidden" value="<%= selPlid %>" />
		<aui:input id="addLayoutParentLayoutId" name="parentLayoutId" type="hidden" value="<%= layoutId %>" />
		<aui:input id="addLayoutExplicitCreation" name="explicitCreation" type="hidden" value="<%= true %>" />

		<aui:fieldset>
			<aui:input id="addLayoutName" name="name" />

			<c:if test="<%= !layoutPrototypes.isEmpty() %>">
				<aui:select label="template" name="layoutPrototypeId" showEmptyOption="<%= true %>">

					<%
					for (LayoutPrototype layoutPrototype : layoutPrototypes) {
					%>

						<aui:option label="<%= HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId())) %>" value="<%= layoutPrototype.getLayoutPrototypeId() %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>

			<div id="<portlet:namespace />hiddenFields">
				<aui:select id="addLayoutType" name="type">

					<%
					boolean firstLayout = ParamUtil.getBoolean(request, "firstLayout");

					for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
						if (PropsValues.LAYOUT_TYPES[i].equals("article") && (group.isLayoutPrototype() || group.isLayoutSetPrototype())) {
							continue;
						}
					%>

						<aui:option disabled="<%= firstLayout && !PortalUtil.isLayoutFirstPageable(PropsValues.LAYOUT_TYPES[i]) %>" label='<%= LanguageUtil.get(pageContext, "layout.types." + PropsValues.LAYOUT_TYPES[i]) %>' value="<%= PropsValues.LAYOUT_TYPES[i] %>" />

					<%
					}
					%>

				</aui:select>

				<c:if test="<%= (selLayout != null) && selLayout.isTypePortlet() %>">
					<aui:input label="copy-parent" name="inheritFromParentLayoutId" type="checkbox" />
				</c:if>
			</div>

			<aui:input id="addLayoutHidden" name="hidden" />

			<div class="hide" id="<portlet:namespace />layoutPrototypeLinkOptions">
				<aui:input id="addLayoutLayoutPrototypeLinkEnabled" label="automatically-apply-changes-done-to-the-page-template" name="layoutPrototypeLinkEnabled" type="checkbox" value="<%= PropsValues.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT %>" />
			</div>
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" value="add-page" />
		</aui:button-row>
	</aui:form>
</div>

<c:if test="<%= !layoutPrototypes.isEmpty() %>">
	<aui:script use="aui-base">
		var layoutPrototypeIdSelect = A.one('#<portlet:namespace />layoutPrototypeId');

		function showHiddenFields() {
			var hiddenFields = A.one('#<portlet:namespace />hiddenFields');

			hiddenFields.toggle(layoutPrototypeIdSelect && !layoutPrototypeIdSelect.val());

			var layoutPrototypeLinkOptions = A.one('#<portlet:namespace />layoutPrototypeLinkOptions');

			layoutPrototypeLinkOptions.toggle(layoutPrototypeIdSelect && layoutPrototypeIdSelect.val() != '');
		}

		showHiddenFields();

		if (layoutPrototypeIdSelect) {
			layoutPrototypeIdSelect.on('change', showHiddenFields);
		}
	</aui:script>
</c:if>