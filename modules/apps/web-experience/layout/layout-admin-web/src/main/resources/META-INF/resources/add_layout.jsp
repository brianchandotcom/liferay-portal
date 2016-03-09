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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

Long groupId = layoutsAdminDisplayContext.getGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
long parentPlid = LayoutConstants.DEFAULT_PLID;
long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

if (layout.isTypeControlPanel()) {
	if (layoutsAdminDisplayContext.getSelPlid() != 0) {
		selLayout = LayoutLocalServiceUtil.getLayout(layoutsAdminDisplayContext.getSelPlid());

		privateLayout = selLayout.isPrivateLayout();
		parentPlid = selLayout.getPlid();
		parentLayoutId = selLayout.getLayoutId();
	}
}
else {
	selLayout = layout;

	privateLayout = selLayout.isPrivateLayout();
	parentPlid = layout.getParentPlid();
	parentLayoutId = layout.getParentLayoutId();
}

String[] types = LayoutTypeControllerTracker.getTypes();

renderResponse.setTitle(LanguageUtil.get(request, "add-new-page"));
%>

<portlet:actionURL name="addLayout" var="addLayoutURL">
	<portlet:param name="mvcPath" value="/add_layout.jsp" />
</portlet:actionURL>

<aui:form action="<%= addLayoutURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="addPageFm">
	<aui:input id="addLayoutGroupId" name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />
	<aui:input id="addLayoutPrivateLayout" name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input id="addLayoutParentPlid" name="parentPlid" type="hidden" value="<%= parentPlid %>" />
	<aui:input id="addLayoutParentLayoutId" name="parentLayoutId" type="hidden" value="<%= parentLayoutId %>" />
	<aui:input id="addLayoutType" name="type" type="hidden" value="portlet" />
	<aui:input id="addLayoutPrototypeId" name="layoutPrototypeId" type="hidden" value="" />
	<aui:input id="addLayoutExplicitCreation" name="explicitCreation" type="hidden" value="<%= true %>" />

	<liferay-ui:error exception="<%= LayoutTypeException.class %>">

		<%
		LayoutTypeException lte = (LayoutTypeException)errorException;

		String type = BeanParamUtil.getString(selLayout, request, "type");
		%>

		<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
			<liferay-ui:message arguments='<%= Validator.isNull(lte.getLayoutType()) ? type : "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
		</c:if>

		<c:if test="<%= lte.getType() == LayoutTypeException.NOT_INSTANCEABLE %>">
			<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-be-selected" />
		</c:if>

		<c:if test="<%= lte.getType() == LayoutTypeException.NOT_PARENTABLE %>">
			<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-have-child-pages" />
		</c:if>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= LayoutNameException.class %>" message="please-enter-a-valid-name" />

	<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

		<%
		RequiredLayoutException rle = (RequiredLayoutException)errorException;
		%>

		<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
			<liferay-ui:message key="you-must-have-at-least-one-page" />
		</c:if>
	</liferay-ui:error>

	<aui:model-context model="<%= Layout.class %>" />

	<%
	List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" id="addLayoutName" name="name" />

			<aui:input helpMessage="if-enabled-this-page-does-not-show-up-in-the-navigation-menu" id="addLayoutHidden" label="hide-from-navigation-menu" name="hidden" type="toggle-switch" />

			<aui:select name="type">
				<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>
					<aui:option label="empty-page" value="blank" />
				</c:if>

				<%
				for (LayoutPrototype layoutPrototype : layoutPrototypes) {
				%>

					<aui:option label="<%= HtmlUtil.escape(layoutPrototype.getName(locale)) %>" value="<%= layoutPrototype.getUuid() %>" />

				<%
				}

				int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(layoutsAdminDisplayContext.getGroup(), privateLayout);

				for (String type : types) {
					if (type.equals("portlet")) {
						continue;
					}

					LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

					if (!layoutTypeController.isInstanceable()) {
						continue;
					}

					ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
				%>

					<aui:option disabled="<%= (layoutsCount == 0) && !layoutTypeController.isFirstPageable() %>" label='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type) %>' value="<%= type %>" />

				<%
				}
				%>

				<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>
					<aui:option label="copy-of-a-page" value="copy" />
				</c:if>
			</aui:select>

			<div id="<portlet:namespace />templateList">
				<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>
					<div class="layout-type" data-type="portlet" id="blank">
						<p class="small text-muted">
							<liferay-ui:message key="empty-page-description" />
						</p>

						<liferay-ui:layout-templates-list
							layoutTemplateId="<%= PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID %>"
							layoutTemplateIdPrefix="addLayout"
							layoutTemplates="<%= LayoutTemplateLocalServiceUtil.getLayoutTemplates(layout.getThemeId()) %>"
						/>
					</div>
				</c:if>

				<%
				for (LayoutPrototype layoutPrototype : layoutPrototypes) {
				%>

					<div class="hide layout-type" data-prototype-id="<%= layoutPrototype.getLayoutPrototypeId() %>" id="<%= layoutPrototype.getUuid() %>">
						<p class="small text-muted">
							<%= HtmlUtil.escape(layoutPrototype.getDescription(locale)) %>
						</p>

						<aui:input helpMessage="if-enabled-this-page-will-inherit-changes-made-to-the-page-template" id='<%= "addLayoutLayoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' label="inherit-changes" name='<%= "layoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' type="toggle-switch" value="<%= PropsValues.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT %>" />
					</div>

				<%
				}

				liferayPortletRequest.setAttribute(WebKeys.LAYOUT_DESCRIPTIONS, layoutsAdminDisplayContext.getLayoutDescriptions());

				for (String type : types) {
					if (type.equals("portlet")) {
						continue;
					}

					LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

					if (!layoutTypeController.isInstanceable()) {
						continue;
					}

					ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
				%>

					<div class="hide layout-type" data-type="<%= type %>" id="<%= type %>">
						<p class="small text-muted">
							<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type + ".description") %>
						</p>

						<%= layoutTypeController.includeEditContent(request, response, selLayout) %>
					</div>

				<%
				}
				%>

				<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>
					<div class="hide layout-type" data-type="portlet" id="copy">
						<p class="small text-muted">
							<liferay-ui:message key="copy-of-a-page-description" />
						</p>

						<liferay-util:include page="/html/portal/layout/edit/portlet_applications.jsp">
							<liferay-util:param name="copyLayoutIdPrefix" value="addLayout" />
						</liferay-util:include>
					</div>
				</c:if>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="add-page" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	var addLayoutType = A.one('#<portlet:namespace />addLayoutType');

	var addLayoutPrototypeId = A.one('#<portlet:namespace />addLayoutPrototypeId');

	var nodeList = A.one('#<portlet:namespace />templateList');

	A.one('#<portlet:namespace />type').on(
		'change',
		function(event) {
			var id = event.currentTarget.val();

			nodeList.all('.layout-type').addClass('hide');

			var currentContent = A.one('#' + id);

			currentContent.removeClass('hide');

			var selectedType = currentContent.attr('data-type');

			var selectedPrototypeId = currentContent.attr('data-prototype-id');

			addLayoutType.attr('value', selectedType);

			addLayoutPrototypeId.attr('value', selectedPrototypeId);
		}
	);
</aui:script>