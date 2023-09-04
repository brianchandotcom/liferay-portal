<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout_templates_list/init.jsp" %>

<%
String layoutTemplateId = (String)request.getAttribute("liferay-layout:layout-templates-list:layoutTemplateId");
String layoutTemplateIdPrefix = (String)request.getAttribute("liferay-layout:layout-templates-list:layoutTemplateIdPrefix");
List<LayoutTemplate> layoutTemplates = (List<LayoutTemplate>)request.getAttribute("liferay-layout:layout-templates-list:layoutTemplates");
%>

<div class="container-fluid <portlet:namespace />layout-template-list lfr-page-layouts">
	<ul class="card-page">

		<%
		layoutTemplates = PluginUtil.restrictPlugins(layoutTemplates, user);

		for (int i = 0; i < layoutTemplates.size(); i++) {
			LayoutTemplate layoutTemplate = layoutTemplates.get(i);

			String templateId = layoutTemplate.getLayoutTemplateId();

			// LPS-90259

			if (templateId.equals("1_column_dynamic")) {
				continue;
			}
		%>

			<li class="card-page-item card-page-item-directory lfr-layout-template">
				<div class="checkbox-card">
					<label class="d-block" for="<portlet:namespace /><%= layoutTemplateIdPrefix %>layoutTemplateId<%= i %>">
						<aui:input checked="<%= layoutTemplateId.equals(layoutTemplate.getLayoutTemplateId()) %>" id='<%= layoutTemplateIdPrefix + "layoutTemplateId" + i %>' label="" name="layoutTemplateId" type="radio" value="<%= layoutTemplate.getLayoutTemplateId() %>" wrappedField="<%= true %>" />

						<div class="card card-interactive card-interactive-primary card-type-template <%= layoutTemplateId.equals(layoutTemplate.getLayoutTemplateId()) ? "active" : StringPool.BLANK %>">
							<div class="card-body">
								<div class="card-row">
									<div class="autofit-col">
										<img alt="" class="layout-template-entry inline-item-before modify-linkm <%= layoutTemplateId.equals(layoutTemplate.getLayoutTemplateId()) ? "layout-selected" : StringPool.BLANK %>" height="28" src="<%= layoutTemplate.getStaticResourcePath() %><%= HtmlUtil.escapeAttribute(layoutTemplate.getThumbnailPath()) %>" width="28" />
									</div>

									<div class="autofit-col autofit-col-expand">
										<span class="text-truncate" title="<%= HtmlUtil.escape(layoutTemplate.getName(locale)) %>"><%= HtmlUtil.escape(layoutTemplate.getName(locale)) %></span>
									</div>
								</div>
							</div>
						</div>
					</label>
				</div>
			</li>

		<%
		}
		%>

	</ul>
</div>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	const {delegate} = frontendJsWeb;

	const delegateHandler = delegate(
		document.querySelector(
			'.<portlet:namespace />layout-template-list.lfr-page-layouts'
		),
		'click',
		'.lfr-layout-template',
		(event) => {
			const layoutTemplateInput = event.delegateTarget.querySelector('input');

			if (layoutTemplateInput) {
				layoutTemplateInput.checked = true;
			}

			const currentActiveCard = event.currentTarget.querySelector('.active');

			if (currentActiveCard) {
				currentActiveCard.classList.remove('active');
			}

			const card = event.delegateTarget.querySelector('.card');

			if (card) {
				card.classList.add('active');
			}
		}
	);

	const onDestroyPortlet = () => {
		delegateHandler.dispose();

		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.once('destroyPortlet', onDestroyPortlet);
</aui:script>