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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

String formName = namespace + request.getAttribute("liferay-ui:page-iterator:formName");
int cur = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:cur"));
String curParam = (String)request.getAttribute("liferay-ui:page-iterator:curParam");
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:delta"));
boolean deltaConfigurable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:page-iterator:deltaConfigurable"));
String deltaParam = (String)request.getAttribute("liferay-ui:page-iterator:deltaParam");
boolean forcePost = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:page-iterator:forcePost"));
String id = (String)request.getAttribute("liferay-ui:page-iterator:id");
String jsCall = GetterUtil.getString((String)request.getAttribute("liferay-ui:page-iterator:jsCall"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:page-iterator:portletURL");
int total = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:total"));
String url = StringPool.BLANK;
String urlAnchor = StringPool.BLANK;
int pages = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:pages"));

int initialPages = 20;

if (portletURL != null) {
	String[] urlArray = PortalUtil.stripURLAnchor(portletURL.toString(), StringPool.POUND);

	url = urlArray[0];
	urlAnchor = urlArray[1];

	if (url.indexOf(CharPool.QUESTION) == -1) {
		url += "?";
	}
	else if (!url.endsWith("&")) {
		url += "&";
	}
}

if (Validator.isNull(id)) {
	id = PortalUtil.generateRandomKey(request, "taglib-page-iterator");
}

int start = (cur - 1) * delta;
int end = cur * delta;

if (end > total) {
	end = total;
}

String deltaURL = HttpUtil.removeParameter(url, namespace + deltaParam);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

if (forcePost && (portletURL != null)) {
	url = url.split(namespace)[0];
%>

	<liferay-util:html-bottom>
		<form action="<%= HtmlUtil.escapeAttribute(url) %>" id="<%= randomNamespace + namespace %>pageIteratorFm" method="post" name="<%= randomNamespace + namespace %>pageIteratorFm">
			<aui:input name="<%= curParam %>" type="hidden" />
			<liferay-portlet:renderURLParams portletURL="<%= portletURL %>" />
		</form>
	</liferay-util:html-bottom>

<%
}
%>

<c:if test="<%= (total > delta) || (total > PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES[0]) %>">
	<div class="pagination-bar" data-qa-id="paginator" id="<%= namespace + id %>">
		<c:if test="<%= deltaConfigurable %>">
			<div class="dropdown pagination-items-per-page">
				<a class="dropdown-toggle page-link" data-toggle="liferay-dropdown" href="javascript:;" role="button"><liferay-ui:message arguments="<%= delta %>" key="x-entries" />
					<span class="sr-only"><liferay-ui:message key="per-page" /></span>

					<aui:icon image="caret-double-l" markupView="lexicon" />
				</a>

				<ul class="dropdown-menu dropdown-menu-top">

					<%
					for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
						if (curDelta > SearchContainer.MAX_DELTA) {
							continue;
						}

						String curDeltaURL = HttpUtil.addParameter(deltaURL + urlAnchor, namespace + deltaParam, curDelta);
					%>

						<li>
							<a class="dropdown-icon" href="<%= HtmlUtil.escapeHREF(curDeltaURL) %>" onClick="<%= forcePost ? _getOnClick(namespace, deltaParam, curDelta) : "" %>">
								<%= String.valueOf(curDelta) %>

								<span class="sr-only"><liferay-ui:message key="entries-per-page" /></span>
							</a>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<p class="pagination-results">
			<liferay-ui:message arguments="<%= new Object[] {numberFormat.format(start + 1), numberFormat.format(end), numberFormat.format(total)} %>" key="showing-x-to-x-of-x-entries" />
		</p>

		<ul class="pagination">
			<li class="page-item <%= (cur > 1) ? StringPool.BLANK : "disabled" %>">
				<a class="page-link" href="<%= (cur > 1) ? _getHREF(formName, namespace + curParam, cur - 1, jsCall, url, urlAnchor) : "javascript:;" %>" onclick="<%= ((cur > 1) && forcePost) ? _getOnClick(namespace, curParam, cur -1) : "" %>">
					<liferay-ui:icon
						icon="angle-left"
						markupView="lexicon"
						message="previous-page"
					/>
				</a>
			</li>

			<c:choose>
				<c:when test="<%= pages <= 5 %>">

					<%
					for (int i = 1; i <= pages; i++) {
					%>

						<li class="page-item <%= (i == cur) ? "active" : StringPool.BLANK %>">
							<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, i) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= i %></a>
						</li>

					<%
					}
					%>

				</c:when>
				<c:when test="<%= cur == 1 %>">
					<li class="active page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, 1, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, 1) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span>1</a>
					</li>
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, 2, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, 2) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span>2</a>
					</li>
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, 3, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, 3) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span>3</a>
					</li>
					<li class="dropdown page-item">
						<a class="dropdown-toggle page-link page-link" data-toggle="liferay-dropdown" href="javascript:;">
							<span aria-hidden="true">...</span>

							<span class="sr-only"><liferay-ui:message key="intermediate-pages" /></span>
						</a>

						<div class="dropdown-menu dropdown-menu-top-center">
							<ul class="inline-scroller link-list">

								<%
								for (int i = 4; i < initialPages; i++) {
									if (i >= pages) {
										break;
									}
								%>

									<li>
										<a class="dropdown-icon" href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, i) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= i %></a>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</li>
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, pages, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, pages) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= pages %></a>
					</li>
				</c:when>
				<c:when test="<%= cur == pages %>">
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, 1, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, 1) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span>1</a>
					</li>
					<li class="dropdown page-item">
						<a class="dropdown-toggle page-link" data-toggle="liferay-dropdown" href="javascript:;">
							<span aria-hidden="true">...</span>

							<span class="sr-only"><liferay-ui:message key="intermediate-pages" /></span>
						</a>

						<div class="dropdown-menu dropdown-menu-top-center">
							<ul class="inline-scroller link-list" data-max-index="<%= pages - 2 %>">

								<%
								for (int i = 2; i < ((initialPages > (cur - 2)) ? cur - 2 : initialPages); i++) {
								%>

									<li>
										<a class="dropdown-icon" href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, i) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= i %></a>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</li>
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, pages - 2, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, pages - 2) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= pages - 2 %></a>
					</li>
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, pages - 1, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, pages - 1) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= pages - 1 %></a>
					</li>
					<li class="active page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, pages, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, pages) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= pages %></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, 1, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, 1) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span>1</a>
					</li>

					<c:if test="<%= (cur - 3) > 1 %>">
						<li class="dropdown page-item">
							<a class="dropdown-toggle page-link" data-toggle="liferay-dropdown" href="javascript:;">
								<span aria-hidden="true">...</span>

								<span class="sr-only"><liferay-ui:message key="intermediate-pages" /></span>
							</a>

							<div class="dropdown-menu dropdown-menu-top-center">
								<ul class="inline-scroller link-list" data-max-index="<%= cur - 1 %>">
					</c:if>

					<%
					for (int i = 2; i < ((initialPages > (cur - 1)) ? cur - 1 : initialPages); i++) {
					%>

						<li class="page-item">
							<a class="dropdown-icon page-link" href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, i) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= i %></a>
						</li>

					<%
					}
					%>

					<c:if test="<%= (cur - 3) > 1 %>">
								</ul>
							</div>
						</li>
					</c:if>

					<c:if test="<%= (cur - 1) > 1 %>">
						<li class="page-item">
							<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, cur - 1, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, cur - 1) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= cur - 1 %></a>
						</li>
					</c:if>

					<li class="active page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, cur, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, cur) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= cur %></a>
					</li>

					<c:if test="<%= (cur + 1) < pages %>">
						<li class="page-item">
							<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, cur + 1, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, cur + 1) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= cur + 1 %></a>
						</li>
					</c:if>

					<c:if test="<%= (cur + 3) < pages %>">
						<li class="dropdown page-item">
							<a class="dropdown-toggle page-link" data-toggle="liferay-dropdown" href="javascript:;">
								<span aria-hidden="true">...</span>

								<span class="sr-only"><liferay-ui:message key="intermediate-pages" /></span>
							</a>

							<div class="dropdown-menu dropdown-menu-top-center">
								<ul class="inline-scroller link-list" data-current-index="<%= cur + 2 %>">
					</c:if>

					<%
					int remainingPages = ((pages - (cur + 2)) < initialPages) ? (pages - (cur + 2)) : initialPages;

					for (int i = cur + 2; i < ((cur + 2) + remainingPages); i++) {
					%>

						<li class="page-item">
							<a class="dropdown-icon page-link" href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, i) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= i %></a>
						</li>

					<%
					}
					%>

					<c:if test="<%= (cur + 3) < pages %>">
								</ul>
							</div>
						</li>
					</c:if>

					<li class="page-item">
						<a class="page-link" href="<%= _getHREF(formName, namespace + curParam, pages, jsCall, url, urlAnchor) %>" onclick="<%= forcePost ? _getOnClick(namespace, curParam, pages) : "" %>"><span class="sr-only"><liferay-ui:message key="page" /></span><%= pages %></a>
					</li>
				</c:otherwise>
			</c:choose>

			<li class="page-item <%= (cur < pages) ? StringPool.BLANK : "disabled" %>">
				<a class="page-link" href="<%= (cur < pages) ? _getHREF(formName, namespace + curParam, cur + 1, jsCall, url, urlAnchor) : "javascript:;" %>" onclick="<%= ((cur < pages) && forcePost) ? _getOnClick(namespace, curParam, cur + 1) : "" %>">
					<liferay-ui:icon
						icon="angle-right"
						markupView="lexicon"
						message="next-page"
					/>
				</a>
			</li>
		</ul>
	</div>
</c:if>

<c:if test="<%= pages > initialPages %>">
	<aui:script require="frontend-js-web/liferay/DynamicInlineScroll.es">
		new frontendJsWebLiferayDynamicInlineScrollEs.default(
			{
				cur: '<%= cur %>',
				curParam: '<%= curParam %>',
				forcePost: <%= forcePost %>,
				formName: '<%= formName %>',
				initialPages: '<%= initialPages %>',
				jsCall: '<%= jsCall %>',
				namespace: '<%= namespace %>',
				pages: '<%= pages %>',
				randomNamespace: '<%= randomNamespace %>',
				url: '<%= HtmlUtil.escapeJS(url) %>',
				urlAnchor: '<%= urlAnchor %>'
			}
		);
	</aui:script>
</c:if>

<script>
	function <portlet:namespace />submitForm(curParam, cur) {
		var data = {};

		data[curParam] = cur;

		Liferay.Util.postForm(
			document.<%= randomNamespace + namespace %>pageIteratorFm,
			{
				data: data
			}
		);
	}
</script>

<%!
private String _getHREF(String formName, String curParam, int cur, String jsCall, String url, String urlAnchor) throws Exception {
	if (Validator.isNotNull(url)) {
		return HtmlUtil.escapeHREF(HttpUtil.addParameter(HttpUtil.removeParameter(url, curParam) + urlAnchor, curParam, cur));
	}

	return "javascript:document." + formName + "." + curParam + ".value = '" + cur + "'; " + jsCall;
}
%>

<%!
private String _getOnClick(String namespace, String curParam, int cur) {
	return "event.preventDefault(); " + namespace + "submitForm('" + namespace + curParam + "','" + cur + "');";
}
%>