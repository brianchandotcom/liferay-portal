<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<liferay-util:include page="/admin/common/vertical_menu.jsp" servletContext="<%= application %>" />

<div class="knowledge-base-admin-content">

	<%
	String backURL = ParamUtil.getString(request, "backURL");

	KBTemplate kbTemplate = (KBTemplate)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);

	if (Validator.isNotNull(backURL)) {
		portletDisplay.setURLBack(backURL);
		portletDisplay.setURLBackTitle(portletDisplay.getTitle());
	}

	boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

	if (portletTitleBasedNavigation) {
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(redirect);
		portletDisplay.setURLBackTitle(portletDisplay.getTitle());

		renderResponse.setTitle(kbTemplate.getTitle());
	}
	%>

	<c:if test="<%= portletTitleBasedNavigation %>">

		<%
		KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
		%>

		<div class="management-bar management-bar-light navbar navbar-expand-md">
			<clay:container-fluid>
				<ul class="justify-content-end navbar-nav navbar-nav-expand">
					<li class="nav-item">
						<clay:link
							aria-label='<%= LanguageUtil.get(request, "edit") %>'
							cssClass="btn-monospaced btn-secondary btn-sm"
							href='<%=
								PortletURLBuilder.createRenderURL(
									liferayPortletResponse
								).setMVCPath(
									"/admin/common/edit_kb_template.jsp"
								).setRedirect(
									currentURL
								).setParameter(
									"kbTemplateId", kbTemplate.getKbTemplateId()
								).buildRenderURL(
								).toString()
							%>'
							icon="pencil"
							title='<%= LanguageUtil.get(request, "edit") %>'
						/>
					</li>
					<li class="nav-item">
						<clay:dropdown-actions
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= kbDropdownItemsProvider.getKBTemplateMoreActionsDropdownItems(kbTemplate) %>"
							propsTransformer="admin/js/KBDropdownPropsTransformer"
						/>
					</li>
				</ul>
			</clay:container-fluid>
		</div>
	</c:if>

	<div class="container-fluid container-fluid-max-xl container-form-lg">
		<div class="kb-article sheet">
			<div class="kb-entity-body">
				<div class="kb-article-title">
					<%= HtmlUtil.escape(kbTemplate.getTitle()) %>
				</div>

				<%= kbTemplate.getContent() %>
			</div>
		</div>
	</div>
</div>