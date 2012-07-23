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

<%@ include file="/html/taglib/init.jsp" %>

<%
String formName = namespace + request.getAttribute("liferay-ui:page-iterator:formName");
int cur = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:cur"));
String curParam = (String)request.getAttribute("liferay-ui:page-iterator:curParam");
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:delta"));
boolean deltaConfigurable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:page-iterator:deltaConfigurable"));
String deltaParam = (String)request.getAttribute("liferay-ui:page-iterator:deltaParam");
String id = (String)request.getAttribute("liferay-ui:page-iterator:id");
String jsCall = GetterUtil.getString((String)request.getAttribute("liferay-ui:page-iterator:jsCall"));
int maxPages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:maxPages"));
String target = (String)request.getAttribute("liferay-ui:page-iterator:target");
int total = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:total"));
String type = (String)request.getAttribute("liferay-ui:page-iterator:type");
String url = (String)request.getAttribute("liferay-ui:page-iterator:url");
String urlAnchor = (String)request.getAttribute("liferay-ui:page-iterator:urlAnchor");
int pages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:pages"));

if (Validator.isNull(id)) {
	id = PortalUtil.generateRandomKey(request, "taglib-page-iterator");
}

int start = (cur - 1) * delta;
int end = cur * delta;

if (end > total) {
	end = total;
}

int resultRowsSize = delta;

if (total < delta) {
	resultRowsSize = total;
}
else {
	resultRowsSize = total - ((cur - 1) * delta);

	if (resultRowsSize > delta) {
		resultRowsSize = delta;
	}
}

String deltaURL = HttpUtil.removeParameter(url, namespace + deltaParam);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<c:if test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") || (type.equals("article") && (total > resultRowsSize)) %>'>
	<div class="taglib-page-iterator" id="<%= namespace + id %>">
</c:if>

<c:if test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") %>'>
	<%@ include file="/html/taglib/ui/page_iterator/showing_x_results.jspf" %>
</c:if>

<c:if test='<%= type.equals("article") && (total > resultRowsSize) %>'>
	<div class="search-results">
		<liferay-ui:message key="pages" />:

		<%
		int pagesIteratorMax = maxPages;
		int pagesIteratorBegin = 1;
		int pagesIteratorEnd = pages;

		if (pages > pagesIteratorMax) {
			pagesIteratorBegin = cur - pagesIteratorMax;
			pagesIteratorEnd = cur + pagesIteratorMax;

			if (pagesIteratorBegin < 1) {
				pagesIteratorBegin = 1;
			}

			if (pagesIteratorEnd > pages) {
				pagesIteratorEnd = pages;
			}
		}

		String content = null;

		if (pagesIteratorEnd < pagesIteratorBegin) {
			content = StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler((pagesIteratorEnd - pagesIteratorBegin + 1) * 6);

			for (int i = pagesIteratorBegin; i <= pagesIteratorEnd; i++) {
				if (i == cur) {
					sb.append("<strong class='journal-article-page-number'>");
					sb.append(i);
					sb.append("</strong>");
				}
				else {
					sb.append("<a class='journal-article-page-number' href='");
					sb.append(_getHREF(formName, curParam, i, jsCall, url, urlAnchor));
					sb.append("'>");
					sb.append(i);
					sb.append("</a>");
				}

				sb.append("&nbsp;&nbsp;");
			}

			content = sb.toString();
		}
		%>

		<%= content %>
	</div>
</c:if>

<c:if test="<%= (total > delta) || (total > PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES[0]) %>">
	<div class="search-pages">
		<c:if test='<%= type.equals("regular") %>'>
			<c:if test="<%= PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES.length > 0 %>">
				<div class="delta-selector">
					<c:choose>
						<c:when test="<%= !deltaConfigurable || themeDisplay.isFacebook() %>">
							<liferay-ui:message key="items-per-page" />

							<%= delta %>
						</c:when>
						<c:otherwise>
							<aui:select changesContext="<%= true %>" id='<%= id + "_itemsPerPage" %>' inlineLabel="left" name="itemsPerPage" onchange='<%= namespace + deltaParam + "updateDelta(this);" %>'>

								<%
								for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
									if (curDelta > SearchContainer.MAX_DELTA) {
										continue;
									}
								%>

									<aui:option label="<%= curDelta %>" selected="<%= delta == curDelta %>" />

								<%
								}
								%>

							</aui:select>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<div class="page-selector">
				<c:choose>
					<c:when test="<%= themeDisplay.isFacebook() %>">
						<liferay-ui:message key="page" />

						<%= cur %>
					</c:when>
					<c:otherwise>

						<%
						String suffix = LanguageUtil.get(pageContext, "of") + StringPool.SPACE + numberFormat.format(pages);

						if (type.equals("approximate") || type.equals("more")) {
							suffix = StringPool.BLANK;
						}
						%>

						<aui:select changesContext="<%= true %>" id='<%= id + "_page" %>' inlineLabel="left" name="page" onchange='<%= namespace + curParam + "updateCur(this);" %>' suffix="<%= suffix %>">

							<%
							int pagesIteratorMax = maxPages;
							int pagesIteratorBegin = 1;
							int pagesIteratorEnd = pages;

							if (pages > pagesIteratorMax) {
								pagesIteratorBegin = cur - pagesIteratorMax;
								pagesIteratorEnd = cur + pagesIteratorMax;

								if (pagesIteratorBegin < 1) {
									pagesIteratorBegin = 1;
								}

								if (pagesIteratorEnd > pages) {
									pagesIteratorEnd = pages;
								}
							}

							for (int i = pagesIteratorBegin; i <= pagesIteratorEnd; i++) {
							%>

								<aui:option label="<%= i %>" selected="<%= (i == cur) %>" />

							<%
							}
							%>

						</aui:select>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>

		<div class="page-links">
			<c:if test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") %>'>
				<c:choose>
					<c:when test="<%= cur != 1 %>">
						<a class="first" href="<%= _getHREF(formName, curParam, 1, jsCall, url, urlAnchor) %>" target="<%= target %>">
					</c:when>
					<c:otherwise>
						<span class="first">
					</c:otherwise>
				</c:choose>

				<liferay-ui:message key="first" />

				<c:choose>
					<c:when test="<%= cur != 1 %>">
						</a>
					</c:when>
					<c:otherwise>
						</span>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:choose>
				<c:when test="<%= cur != 1 %>">
					<a class="previous" href="<%= _getHREF(formName, curParam, cur - 1, jsCall, url, urlAnchor) %>" target="<%= target %>">
				</c:when>
				<c:when test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") %>'>
					<span class="previous">
				</c:when>
			</c:choose>

			<c:if test='<%= (type.equals("approximate") || type.equals("more") || type.equals("regular") || cur != 1) %>'>
				<liferay-ui:message key="previous" />
			</c:if>

			<c:choose>
				<c:when test="<%= cur != 1 %>">
					</a>
				</c:when>
				<c:when test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") %>'>
					</span>
				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="<%= cur != pages %>">
					<a class="next" href="<%= _getHREF(formName, curParam, cur + 1, jsCall, url, urlAnchor) %>" target="<%= target %>">
				</c:when>
				<c:when test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") %>'>
					<span class="next">
				</c:when>
			</c:choose>

			<c:if test='<%= (type.equals("approximate") || type.equals("more") || type.equals("regular") || cur != pages) %>'>
				<c:choose>
					<c:when test='<%= type.equals("approximate") || type.equals("more") %>'>
						<liferay-ui:message key="more" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="next" />
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:choose>
				<c:when test="<%= cur != pages %>">
					</a>
				</c:when>
				<c:when test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") %>'>
					</span>
				</c:when>
			</c:choose>

			<c:if test='<%= type.equals("regular") %>'>
				<c:choose>
					<c:when test="<%= cur != pages %>">
						<a class="last" href="<%= _getHREF(formName, curParam, pages, jsCall, url, urlAnchor) %>" target="<%= target %>">
					</c:when>
					<c:otherwise>
						<span class="last">
					</c:otherwise>
				</c:choose>

				<liferay-ui:message key="last" />

				<c:choose>
					<c:when test="<%= cur != pages %>">
						</a>
					</c:when>
					<c:otherwise>
						</span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</div>
</c:if>

<c:if test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") || (type.equals("article") && (total > resultRowsSize)) %>'>
	</div>
</c:if>

<c:if test='<%= type.equals("approximate") || type.equals("more") || type.equals("regular") && !themeDisplay.isFacebook() %>'>
	<aui:script>
		Liferay.provide(
			window,
			'<%= namespace %><%= curParam %>updateCur',
			function(box) {
				var A = AUI();

				var cur = A.one(box).val();

				if (<%= Validator.isNotNull(url) %>) {
					var href = "<%= url %><%= namespace %><%= curParam %>=" + cur + "<%= urlAnchor %>";

					location.href = href;
				}
				else {
					document.<%= formName %>.<%= curParam %>.value = cur;

					<%= jsCall %>;
				}
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<%= namespace %><%= deltaParam %>updateDelta',
			function(box) {
				var A = AUI();

				var delta = A.one(box).val();

				if (<%= Validator.isNotNull(url) %>) {
					var href = "<%= deltaURL %>&<%= namespace %><%= deltaParam %>=" + delta + "<%= urlAnchor %>";

					location.href = href;
				}
				else {
					document.<%= formName %>.<%= deltaParam %>.value = delta;

					<%= jsCall %>;
				}
			},
			['aui-base']
		);
	</aui:script>
</c:if>

<%!
private String _getHREF(String formName, String curParam, int cur, String jsCall, String url, String urlAnchor) throws Exception {
	String href = null;

	if (Validator.isNotNull(url)) {
		href = HtmlUtil.escape(url + curParam + "=" + cur + urlAnchor);
	}
	else {
		href = "javascript:document." + formName + "." + curParam + ".value = '" + cur + "'; " + jsCall;
	}

	return href;
}
%>