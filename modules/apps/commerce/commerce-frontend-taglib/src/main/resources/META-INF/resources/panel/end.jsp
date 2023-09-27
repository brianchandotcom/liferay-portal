<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/panel/init.jsp" %>

		</div>

		<c:if test="<%= Validator.isNotNull(showMoreId) || Validator.isNotNull(showMoreUrl) %>">
			<div class="border-top card-footer p-0" id="<%= HtmlUtil.escapeAttribute(showMoreButtonWrapperId) %>">
				<a class="border-0 btn btn-secondary w-100" href="<%= Validator.isNotNull(showMoreUrl) ? HtmlUtil.escapeHREF(showMoreUrl) : '#' %>" id="<%= HtmlUtil.escapeAttribute(showMoreButtonId) %>">
					<liferay-ui:message key="you" />
				</a>
			</div>
		</c:if>
	</div>
</div>