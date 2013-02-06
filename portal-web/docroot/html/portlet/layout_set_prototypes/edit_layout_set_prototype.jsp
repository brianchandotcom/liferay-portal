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

<%@ include file="/html/portlet/layout_set_prototypes/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute(WebKeys.LAYOUT_PROTOTYPE);

if (layoutSetPrototype == null) {
	layoutSetPrototype = new LayoutSetPrototypeImpl();

	layoutSetPrototype.setNew(true);
	layoutSetPrototype.setActive(true);
}

long layoutSetPrototypeId = BeanParamUtil.getLong(layoutSetPrototype, request, "layoutSetPrototypeId");

boolean layoutsUpdateable = GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();

int mergeFailCount = layoutSetPrototypeId > 0 ? SitesUtil.getMergeFailCount(layoutSetPrototype) : 0;
%>

<liferay-util:include page="/html/portlet/layout_set_prototypes/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= layoutSetPrototype.isNew() ? "add" : "view-all" %>' />
</liferay-util:include>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= layoutSetPrototype.isNew() %>"
	title='<%= layoutSetPrototype.isNew() ? "new-site-template" : layoutSetPrototype.getName(locale) %>'
/>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveLayoutSetPrototype();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototypeId %>" />

	<aui:model-context bean="<%= layoutSetPrototype %>" model="<%= LayoutSetPrototype.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<aui:input name="active" />

		<aui:input helpMessage="allow-site-administrators-to-modify-pages-associated-with-this-site-template-help" label="allow-site-administrators-to-modify-pages-associated-with-this-site-template" name="layoutsUpdateable" type="checkbox" value="<%= layoutsUpdateable %>" />

		<c:if test="<%= !layoutSetPrototype.isNew() %>">
			<aui:field-wrapper label="configuration">
				<liferay-portlet:actionURL portletName="<%= PortletKeys.SITE_REDIRECTOR %>" var="viewURL">
					<portlet:param name="struts_action" value="/my_sites/view" />
					<portlet:param name="groupId" value="<%= String.valueOf(layoutSetPrototype.getGroup().getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					image="view"
					label="<%= true %>"
					message="open-site-template"
					method="get"
					target="_blank"
					url="<%= viewURL %>"
				/>
			</aui:field-wrapper>

			<aui:field-wrapper inlineField="true" label="">

				<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
					<span class="portlet-msg-alert">
						<aui:a cssClass="merge-fail-popup-button" href="javascript:;">
							<liferay-ui:message key="propagation-disabled-temporarily" />
						</aui:a>
					</span>

					<div class="aui-helper-hidden" id="<portlet:namespace />mergeFailCountDialogContentWrapper">
						<div class="content">
							<p>
								<liferay-ui:message arguments="<%= new Object[]{mergeFailCount} %>" key="the-propagation-has-been-disabled-temporarily-after-x-errors" />
							</p>

							<aui:button onClick='<%= renderResponse.getNamespace() + "resetMergeFailCount()" %>' value="reset-merge-fail-count" />
						</div>
					</div>
				</c:if>
			</aui:field-wrapper>
		</c:if>

		<%
		Set<String> servletContextNames = CustomJspRegistryUtil.getServletContextNames();

		String customJspServletContextName = StringPool.BLANK;

		if (layoutSetPrototype != null) {
			UnicodeProperties settingsProperties = layoutSetPrototype.getSettingsProperties();

			customJspServletContextName = GetterUtil.getString(settingsProperties.get("customJspServletContextName"));
		}
		%>

		<c:if test="<%= !servletContextNames.isEmpty() %>">
			<aui:select label="application-adapter" name="customJspServletContextName">
				<aui:option label="none" />

				<%
				for (String servletContextName : servletContextNames) {
				%>

					<aui:option selected="<%= customJspServletContextName.equals(servletContextName) %>" value="<%= servletContextName %>"><%= CustomJspRegistryUtil.getDisplayName(servletContextName) %></aui:option>

				<%
				}
				%>

			</aui:select>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayoutSetPrototype() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (layoutSetPrototype == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" /></portlet:actionURL>");
	}

	function <portlet:namespace />resetMergeFailCount() {
		<portlet:renderURL var="currentEditURL">
			<portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototypeId) %>" />
		</portlet:renderURL>

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.RESET_MERGE_FAIL_COUNT %>';
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= currentEditURL %>';
		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" /></portlet:actionURL>');
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<aui:script use="aui-base,aui-dialog">
	var warningElem = A.one(".merge-fail-popup-button");

	if(warningElem) {
		warningElem.on('click', function(){

			var dialogContent = A.one('#<portlet:namespace />mergeFailCountDialogContentWrapper .content');

			var options = {
				title: '<liferay-ui:message key="propagation-of-changes" />',
				bodyContent: dialogContent.html(),
				centered: true,
				width: 400,
				height: 150,
				modal: true
			};

			var popup = new A.Dialog(options).render();

			dialogContent.show();
		});
	}
</aui:script>

<%
if (!layoutSetPrototype.isNew()) {
	PortalUtil.addPortletBreadcrumbEntry(request, layoutSetPrototype.getName(locale), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-page"), currentURL);
}
%>