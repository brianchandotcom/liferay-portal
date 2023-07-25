<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/locked_layout/init.jsp" %>

<clay:sheet>
	<clay:sheet-header>
		<clay:content-row
			cssClass="c-mt-5 text-info"
		>
			<clay:content-col>
				<img src="<%= lockedLayoutDisplayContext.getImagesPath() %>/blocked_page.png" />
			</clay:content-col>
		</clay:content-row>

		<clay:content-row
			cssClass="c-mt-3 text-info"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<h1><liferay-ui:message key="page-in-use" /></h1>
			</clay:content-col>
		</clay:content-row>
	</clay:sheet-header>

	<clay:sheet-section>
		<clay:content-row
			cssClass="c-mt-3"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<liferay-ui:message key="this-page-is-currently-being-edited-by-another-user.-if-you-need-to-take-control-over-this-page,-you-can-contact-your-admin-to-unlock-it" />
			</clay:content-col>
		</clay:content-row>
	</clay:sheet-section>

	<c:if test="<%= lockedLayoutDisplayContext.isShowGoBackButton() %>">
		<clay:sheet-footer>
			<clay:content-row
				cssClass="c-mt-3"
			>
				<clay:content-col>
					<clay:button
						displayType="info"
						label="go-back"
						onClick='<%= "location.href='" + HtmlUtil.escapeJS(lockedLayoutDisplayContext.getBackURL()) + "';" %>'
					/>
				</clay:content-col>
			</clay:content-row>
		</clay:sheet-footer>
	</c:if>
</clay:sheet>