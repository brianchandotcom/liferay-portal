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
String formName = namespace + request.getAttribute("liferay-ui:page-iterator:formName");
int cur = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:cur"));
String curParam = (String)request.getAttribute("liferay-ui:page-iterator:curParam");
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:delta"));
boolean deltaConfigurable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:page-iterator:deltaConfigurable"));
String deltaParam = (String)request.getAttribute("liferay-ui:page-iterator:deltaParam");
String id = (String)request.getAttribute("liferay-ui:page-iterator:id");
String jsCall = GetterUtil.getString((String)request.getAttribute("liferay-ui:page-iterator:jsCall"));
int total = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:page-iterator:total"));
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

String deltaURL = HttpUtil.removeParameter(url, namespace + deltaParam);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<c:if test="<%= (total > delta) || (total > PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES[0]) %>">
	<div class="pagination-bar" data-qa-id="paginator" id="<%= namespace + id %>">
		<c:if test="<%= deltaConfigurable %>">
			<div class="dropdown pagination-items-per-page">
				<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;" type="button"><liferay-ui:message arguments="<%= delta %>" key="x-entries" /><span class="icon-sort"></span></a>

				<ul class="dropdown-menu dropdown-menu-top">

					<%
					for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
						if (curDelta > SearchContainer.MAX_DELTA) {
							continue;
						}
					%>

						<li>
							<a href='<%= deltaURL + "&" + namespace + deltaParam + "=" + curDelta + urlAnchor %>'><%= String.valueOf(curDelta) %></a>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<div class="pagination-results">
			<liferay-ui:message arguments="<%= new Object[] {numberFormat.format(start + 1), numberFormat.format(end), numberFormat.format(total)} %>" key="showing-x-to-x-of-x-entries" />
		</div>

		<ul class="pagination">
			<li class="<%= (cur > 1) ? StringPool.BLANK : "disabled" %>">
				<a href="<%= (cur > 1) ? _getHREF(formName, namespace + curParam, cur - 1, jsCall, url, urlAnchor) : "javascript:;" %>"><span class="icon-caret-left"></span></a>
			</li>

			<c:choose>
				<c:when test="<%= pages <= 5 %>">

					<%
					for (int i = 1; i <= pages; i++) {
					%>

						<li class="<%= (i == cur) ? "active" : StringPool.BLANK %>">
							<a href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>"><%= i %></a>
						</li>

					<%
					}
					%>

				</c:when>
				<c:when test="<%= cur == 1 %>">
					<li class="active">
						<a href="<%= _getHREF(formName, namespace + curParam, 1, jsCall, url, urlAnchor) %>">1</a>
					</li>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, 2, jsCall, url, urlAnchor) %>">2</a>
					</li>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, 3, jsCall, url, urlAnchor) %>">3</a>
					</li>

					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">...</a>

						<div class="dropdown-menu dropdown-menu-top-center">
							<ul class="inline-scroller link-list">

								<%
								for (int i = 4; i < pages; i++) {
								%>

									<li>
										<a href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>"><%= i %></a>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</li>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, pages, jsCall, url, urlAnchor) %>"><%= pages %></a>
					</li>
				</c:when>
				<c:when test="<%= cur == pages %>">
					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, 1, jsCall, url, urlAnchor) %>">1</a>
					</li>

					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">...</a>

						<div class="dropdown-menu dropdown-menu-top-center">
							<ul class="inline-scroller link-list">

								<%
								for (int i = 2; i < (pages - 2); i++) {
								%>

									<li>
										<a href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>"><%= i %></a>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</li>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, pages - 2, jsCall, url, urlAnchor) %>"><%= pages - 2 %></a>
					</li>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, pages - 1, jsCall, url, urlAnchor) %>"><%= pages - 1 %></a>
					</li>

					<li class="active">
						<a href="<%= _getHREF(formName, namespace + curParam, pages, jsCall, url, urlAnchor) %>"><%= pages %></a>
					</li>
				</c:when>
				<c:otherwise>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, 1, jsCall, url, urlAnchor) %>">1</a>
					</li>

					<c:if test="<%= (cur - 3) > 1 %>">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">...</a>

							<div class="dropdown-menu dropdown-menu-top-center">
								<ul class="inline-scroller link-list">
					</c:if>

					<%
					for (int i = 2; i < (cur - 1); i++) {
					%>

						<li>
							<a href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>"><%= i %></a>
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
						<li>
							<a href="<%= _getHREF(formName, namespace + curParam, cur - 1, jsCall, url, urlAnchor) %>"><%= cur - 1 %></a>
						</li>
					</c:if>

					<li class="active">
						<a href="<%= _getHREF(formName, namespace + curParam, cur, jsCall, url, urlAnchor) %>"><%= cur %></a>
					</li>

					<c:if test="<%= (cur + 1) < pages %>">
						<li>
							<a href="<%= _getHREF(formName, namespace + curParam, cur + 1, jsCall, url, urlAnchor) %>"><%= cur + 1 %></a>
						</li>
					</c:if>

					<c:if test="<%= (cur + 3) < pages %>">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">...</a>

							<div class="dropdown-menu dropdown-menu-top-center">
								<ul class="inline-scroller link-list">
					</c:if>

					<%
					for (int i = (cur + 2); i < pages; i++) {
					%>

						<li>
							<a href="<%= _getHREF(formName, namespace + curParam, i, jsCall, url, urlAnchor) %>"><%= i %></a>
						</li>

					<%
					}
					%>

					<c:if test="<%= (cur + 3) < pages %>">
								</ul>
							</div>
						</li>
					</c:if>

					<li>
						<a href="<%= _getHREF(formName, namespace + curParam, pages, jsCall, url, urlAnchor) %>"><%= pages %></a>
					</li>
				</c:otherwise>
			</c:choose>

			<li class="<%= (cur < pages) ? StringPool.BLANK : "disabled" %>">
				<a href="<%= (cur < pages) ? _getHREF(formName, namespace + curParam, cur + 1, jsCall, url, urlAnchor) : "javascript:;" %>"><span class="icon-caret-right"></span></a>
			</li>
		</ul>
	</div>
</c:if>

<%!
private String _getHREF(String formName, String curParam, int cur, String jsCall, String url, String urlAnchor) throws Exception {
	if (Validator.isNotNull(url)) {
		return HtmlUtil.escape(url + curParam + "=" + cur + urlAnchor);
	}

	return "javascript:document." + formName + "." + curParam + ".value = '" + cur + "'; " + jsCall;
}
%>