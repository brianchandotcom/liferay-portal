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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/search.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setPortletMode(PortletMode.VIEW);
portletURL.setWindowState(WindowState.MAXIMIZED);

pageContext.setAttribute("portletURL", portletURL);
%>

<aui:form action="<%= portletURL %>" method="get" name="fm" onSubmit='<%= liferayPortletResponse.getNamespace() + "search(); event.preventDefault();" %>'>
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<div class="form-group-autofit search-input-group">
		<div class="form-group-item">
			<div class="input-group">
				<div class="input-group-item">
					<input class="form-control input-group-inset input-group-inset-after search-input search-portlet-keywords-input" id="<%= liferayPortletResponse.getNamespace() + "keywords" %>" name="<%= liferayPortletResponse.getNamespace() + "keywords" %>" placeholder="<%= LanguageUtil.get(request, "search") %>" type="text" value="<%= (searchDisplayContext.getKeywords() != null) ? HtmlUtil.escapeAttribute(searchDisplayContext.getKeywords()) : StringPool.BLANK %>" />

					<div class="input-group-inset-item input-group-inset-item-after">
						<button class="btn btn-light btn-unstyled" onclick="<%= liferayPortletResponse.getNamespace() + "search();" %>" type="submit">
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