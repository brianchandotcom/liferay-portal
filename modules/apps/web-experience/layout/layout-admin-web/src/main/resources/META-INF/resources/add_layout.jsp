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

String selThemeId = null;

if (layout.isTypeControlPanel()) {
	if (layoutsAdminDisplayContext.getSelPlid() != 0) {
		selLayout = LayoutLocalServiceUtil.getLayout(layoutsAdminDisplayContext.getSelPlid());

		selThemeId = selLayout.getThemeId();

		privateLayout = selLayout.isPrivateLayout();
		parentPlid = selLayout.getPlid();
		parentLayoutId = selLayout.getLayoutId();
	}
	else {
		LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

		selThemeId = selLayoutSet.getThemeId();
	}
}
else {
	selLayout = layout;

	selThemeId = layout.getThemeId();

	privateLayout = layout.isPrivateLayout();
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
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="parentPlid" type="hidden" value="<%= parentPlid %>" />
	<aui:input name="parentLayoutId" type="hidden" value="<%= parentLayoutId %>" />
	<aui:input name="type" type="hidden" value="portlet" />
	<aui:input name="layoutPrototypeId" type="hidden" value="" />
	<aui:input name="explicitCreation" type="hidden" value="<%= true %>" />

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
			<aui:input autoFocus="<%= true %>" name="name" />

			<aui:input helpMessage="if-enabled-this-page-does-not-show-up-in-the-navigation-menu" label="hide-from-navigation-menu" name="hidden" type="toggle-switch" />

			<aui:select label="type" name="template">

				<%
				Map<String, Object> data = new HashMap<String, Object>();
				%>

				<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>

					<%
					data.put("type", "portlet");
					%>

					<aui:option data="<%= data %>" label="empty-page" value="portlet" />
				</c:if>

				<%
				int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(layoutsAdminDisplayContext.getGroup(), privateLayout);

				for (String type : types) {
					if (type.equals("portlet")) {
						continue;
					}

					LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

					if (!layoutTypeController.isInstanceable()) {
						continue;
					}

					data.put("type", type);

					ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
				%>

					<aui:option data="<%= data %>" disabled="<%= (layoutsCount == 0) && !layoutTypeController.isFirstPageable() %>" label='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type) %>' value="<%= type %>" />

				<%
				}
				%>

				<c:if test='<%= ArrayUtil.contains(types, "portlet") %>'>

					<%
					data.put("type", "portlet");
					%>

					<aui:option data="<%= data %>" label="copy-of-a-page" value="copy" />
				</c:if>

				<optgroup label="<liferay-ui:message key="templates" />">

					<%
					for (LayoutPrototype layoutPrototype : layoutPrototypes) {
						data.put("prototype-id", layoutPrototype.getLayoutPrototypeId());
						data.put("type", StringPool.BLANK);
					%>

						<aui:option data="<%= data %>" label="<%= HtmlUtil.escape(layoutPrototype.getName(locale)) %>" value="layout-prototype" />

					<%
					}
					%>

				</optgroup>
			</aui:select>

			<div id="<portlet:namespace />templateList">
				<liferay-util:include page="/layout_type_resources.jsp" servletContext="<%= application %>" />
			</div>
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
			<liferay-ui:asset-categories-error />

			<liferay-ui:asset-tags-error />

			<liferay-asset:asset-categories-selector className="<%= Layout.class.getName() %>" classPK="<%= selLayout != null ? selLayout.getPrimaryKey() : 0 %>" />

			<liferay-asset:asset-tags-selector className="<%= Layout.class.getName() %>" classPK="<%= selLayout != null ? selLayout.getPrimaryKey() : 0 %>" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="add-page" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-io-request,aui-parse-content">
	var type = A.one('#<portlet:namespace />type');

	var layoutPrototypeId = A.one('#<portlet:namespace />layoutPrototypeId');

	var nodeList = A.one('#<portlet:namespace />templateList');

	nodeList.plug(A.Plugin.ParseContent);

	A.one('#<portlet:namespace />template').on(
		'change',
		function(event) {
			var currentTarget = event.currentTarget;

			var id = currentTarget.val();

			var index = currentTarget.get('selectedIndex');

			var selectedOption = currentTarget.get('options').item(index);

			var selectedType = selectedOption.attr('data-type');

			var selectedPrototypeId = selectedOption.attr('data-prototype-id');

			type.attr('value', selectedType);

			layoutPrototypeId.attr('value', selectedPrototypeId);

			var data = Liferay.Util.ns(
				'<portlet:namespace />',
				{
					id: id,
					prototypeId: selectedPrototypeId,
					type: selectedType
				}
			);

			A.io.request(
				'<liferay-portlet:resourceURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/layout_type_resources.jsp" /></liferay-portlet:resourceURL>',
				{
					data: data,
					on: {
						failure: function() {
							nodeList.html('<div class="alert alert-danger"><liferay-ui:message key="an-unexpected-error-occurred" /></div>');
						},
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							nodeList.setContent(responseData);
						}
					}
				}
			);
		}
	);
</aui:script>