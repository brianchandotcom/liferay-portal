<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/locked_layout/init.jsp" %>

<div class="align-items-center bg-white d-flex min-vh-100">
	<clay:container-fluid
		cssClass="c-mb-7"
		size="md"
	>
		<clay:content-row
			cssClass="text-info"
		>
			<clay:content-col
				cssClass="c-mb-3"
			>
				<img alt="" src="<%= lockedLayoutDisplayContext.getImagesPath() %>/blocked_page.png" />
			</clay:content-col>
		</clay:content-row>

		<clay:content-row
			cssClass="c-mt-3 text-info"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<h1 class="text-11"><liferay-ui:message key="page-in-use" /></h1>
			</clay:content-col>
		</clay:content-row>

		<clay:content-row
			cssClass="c-my-3"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<liferay-ui:message key="this-page-is-currently-being-edited-by-another-user.-if-you-need-to-take-control-over-this-page,-you-can-contact-your-administrator-to-unlock-it" />
			</clay:content-col>
		</clay:content-row>

		<c:if test="<%= lockedLayoutDisplayContext.isShowGoBackButton() %>">
			<clay:content-row
				cssClass="c-pt-3"
			>
				<clay:content-col>
					<clay:button
						displayType="info"
						label="<%= lockedLayoutDisplayContext.getBackURLLabel() %>"
						onClick='<%= "location.href='" + HtmlUtil.escapeJS(lockedLayoutDisplayContext.getBackURL()) + "';" %>'
					/>
				</clay:content-col>
			</clay:content-row>
		</c:if>
	</clay:container-fluid>
</div>