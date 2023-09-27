<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/search.jsp"
).setRedirect(
	currentURL
).setPortletMode(
	PortletMode.VIEW
).setWindowState(
	WindowState.MAXIMIZED
).buildPortletURL();

pageContext.setAttribute("portletURL", portletURL);
%>

<aui:form action="<%= portletURL %>" method="get" name="fm" onSubmit='<%= liferayPortletResponse.getNamespace() + "search(); event.preventDefault();" %>'>
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<div class="form-group-autofit search-input-group">
		<div class="form-group-item">
			<div class="input-group">
				<div class="input-group-item">
					<input class="form-control input-group-inset input-group-inset-after search-input search-portlet-keywords-input" id="<portlet:namespace />keywords" name="<portlet:namespace />keywords" placeholder="<%= LanguageUtil.get(request, "search") %>" type="text" value="<%= (searchDisplayContext.getKeywords() != null) ? HtmlUtil.escapeAttribute(searchDisplayContext.getKeywords()) : StringPool.BLANK %>" />

					<div class="input-group-inset-item input-group-inset-item-after">
						<button class="btn btn-light btn-unstyled" onclick="<portlet:namespace />search();" type="submit">
							<liferay-ui:icon
								icon="search"
								markupView="lexicon"
							/>
						</button>
					</div>
				</div>
			</div>
		</div>

		<%
		String taglibOnClick = "Liferay.Util.focusFormField('#" + liferayPortletResponse.getNamespace() + "keywords');";
		%>

		<liferay-ui:quick-access-entry
			label="skip-to-search"
			onClick="<%= taglibOnClick %>"
		/>

		<c:choose>
			<c:when test="<%= searchDisplayContext.isSearchScopePreferenceLetTheUserChoose() %>">
				<div class="form-group-item scope-selector">
					<aui:select cssClass="search-select" inlineField="<%= true %>" label="" name="scope" title="scope">
						<aui:option label="this-site" value="this-site" />

						<c:if test="<%= searchDisplayContext.isSearchScopePreferenceEverythingAvailable() %>">
							<aui:option label="everything" value="everything" />
						</c:if>
					</aui:select>
				</div>
			</c:when>
			<c:otherwise>
				<aui:input name="scope" type="hidden" value="<%= searchDisplayContext.getSearchScopeParameterString() %>" />
			</c:otherwise>
		</c:choose>
	</div>

	<aui:script>
		window.<portlet:namespace />search = function () {
			var keywords =
				document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

			keywords = keywords.replace(/^\s+|\s+$/, '');

			if (keywords != '') {
				submitForm(document.<portlet:namespace />fm);
			}
		};
	</aui:script>
</aui:form>