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

<%@ include file="/template_selector/init.jsp" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ddm:template-selector:classNameId"));
DDMTemplate portletDisplayDDMTemplate = (DDMTemplate)request.getAttribute("liferay-ddm:template-selector:portletDisplayDDMTemplate");

long ddmTemplateGroupId = PortletDisplayTemplateUtil.getDDMTemplateGroupId(themeDisplay.getScopeGroupId());

Group ddmTemplateGroup = GroupLocalServiceUtil.getGroup(ddmTemplateGroupId);
%>

<clay:content-row
	floatElements=""
	verticalAlign="center"
>
	<clay:content-col
		cssClass="inline-item-before"
	>
		<aui:input id="displayStyleGroupId" name="preferences--displayStyleGroupId--" type="hidden" value="<%= String.valueOf(displayStyleGroupId) %>" />

		<aui:select id="displayStyle" label="<%= HtmlUtil.escape(label) %>" name="preferences--displayStyle--" wrapperCssClass="c-mb-4">
			<c:if test="<%= showEmptyOption %>">
				<aui:option label="default" selected="<%= Validator.isNull(displayStyle) %>" />
			</c:if>

			<c:if test="<%= (displayStyles != null) && !displayStyles.isEmpty() %>">
				<optgroup label="<liferay-ui:message key="default" />">

					<%
					for (String curDisplayStyle : displayStyles) {
					%>

						<aui:option label="<%= HtmlUtil.escape(curDisplayStyle) %>" selected="<%= displayStyle.equals(curDisplayStyle) %>" />

					<%
					}
					%>

				</optgroup>
			</c:if>

			<%
			for (com.liferay.dynamic.data.mapping.model.DDMTemplate curDDMTemplate : DDMTemplateLocalServiceUtil.getTemplates(PortalUtil.getCurrentAndAncestorSiteGroupIds(ddmTemplateGroupId), classNameId, 0L)) {
				if (!DDMTemplatePermission.contains(permissionChecker, curDDMTemplate.getTemplateId(), ActionKeys.VIEW) || !DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY.equals(curDDMTemplate.getType())) {
					continue;
				}
			%>

				<aui:option
					data='<%=
						HashMapBuilder.<String, Object>put(
							"displaystylegroupid", curDDMTemplate.getGroupId()
						).build()
					%>'
					label="<%= HtmlUtil.escape(curDDMTemplate.getName(locale)) %>"
					selected="<%= (portletDisplayDDMTemplate != null) && (curDDMTemplate.getTemplateId() == portletDisplayDDMTemplate.getTemplateId()) %>"
					value="<%= PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + HtmlUtil.escape(curDDMTemplate.getTemplateKey()) %>"
				/>

			<%
			}
			%>

		</aui:select>
	</clay:content-col>

	<c:if test="<%= !ddmTemplateGroup.isLayoutPrototype() %>">
		<clay:content-col>
			<liferay-ui:icon
				icon="<%= HtmlUtil.escapeCSS(icon) %>"
				id="selectDDMTemplate"
				label="<%= true %>"
				markupView="lexicon"
				message='<%= LanguageUtil.get(request, "manage-templates") %>'
				url="javascript:;"
			/>
		</clay:content-col>
	</c:if>
</clay:content-row>

<aui:script sandbox="<%= true %>">
	const manageDDMTemplatesLink = document.getElementById(
		'<portlet:namespace />selectDDMTemplate'
	);

	if (manageDDMTemplatesLink) {
		manageDDMTemplatesLink.addEventListener('click', (event) => {
			const openerWindow = Liferay.Util.getOpener();

			openerWindow.Liferay.Util.openModal({
				iframeBodyCssClass: '',
				onClose: () => {
					const form = document.getElementById('<portlet:namespace />fm');

					if (form) {
						submitForm(form, '<%= HtmlUtil.escapeJS(refreshURL) %>');
					}
				},
				title:
					'<%= UnicodeLanguageUtil.get(request, "widget-templates") %>',
				url:
					'<%=
						PortletURLBuilder.create(
							PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
						).setMVCPath(
							"/view_template.jsp"
						).setParameter(
							"classNameId", classNameId
						).setParameter(
							"groupId", ddmTemplateGroupId
						).setParameter(
							"navigationStartsOn", DDMNavigationHelper.VIEW_TEMPLATES
						).setParameter(
							"refererPortletName", PortletKeys.PORTLET_DISPLAY_TEMPLATE
						).setParameter(
							"showHeader", false
						).setWindowState(
							LiferayWindowState.POP_UP
						).buildString()
				%>',
			});
		});
	}

	var displayStyle = document.getElementById('<portlet:namespace />displayStyle');
	var displayStyleGroupIdInput = document.getElementById(
		'<portlet:namespace />displayStyleGroupId'
	);

	if (displayStyle && displayStyleGroupIdInput) {
		displayStyle.addEventListener('change', (event) => {
			var selectedDisplayStyle = displayStyle.querySelector('option:checked');

			if (selectedDisplayStyle) {
				var displayStyleGroupId =
					selectedDisplayStyle.dataset.displaystylegroupid;

				if (displayStyleGroupId) {
					displayStyleGroupIdInput.value = displayStyleGroupId;
				}
			}
		});
	}
</aui:script>