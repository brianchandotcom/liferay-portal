<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry" %><%@
page import="com.liferay.taglib.ui.SearchIteratorTag" %>

<%@ page import="java.util.AbstractMap" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");

boolean compactEmptyResultsMessage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search:compactEmptyResultsMessage"));
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ui:search-iterator:displayStyle"), SearchIteratorTag.DEFAULT_DISPLAY_STYLE);
boolean fixedHeader = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:fixedHeader"));
String markupView = (String)request.getAttribute("liferay-ui:search-iterator:markupView");
boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));
ResultRowSplitter resultRowSplitter = (ResultRowSplitter)request.getAttribute("liferay-ui:search-iterator:resultRowSplitter");
String searchContainerRowCssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:search-container-row:cssClass"));
String searchResultCssClass = (String)request.getAttribute("liferay-ui:search-iterator:searchResultCssClass");
String type = (String)request.getAttribute("liferay-ui:search:type");

String id = searchContainer.getId(request, namespace);

String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
String emptyResultsMessageCssClass = searchContainer.getEmptyResultsMessageCssClass();
List<String> headerNames = searchContainer.getHeaderNames();
Map<String, String> helpMessages = searchContainer.getHelpMessages();
List<String> normalizedHeaderNames = searchContainer.getNormalizedHeaderNames();
Map orderableHeaders = searchContainer.getOrderableHeaders();
RowChecker rowChecker = searchContainer.getRowChecker();
RowMover rowMover = searchContainer.getRowMover();
List resultRows = searchContainer.getResultRows();
String summary = searchContainer.getSummary();

JSONArray primaryKeysJSONArray = JSONFactoryUtil.createJSONArray();
%>

<%!
private String _getRowElementId(String namespace, String partialId, PortletRequest portletRequest, PortletResponse portletResponse, com.liferay.portal.kernel.dao.search.ResultRow row) {
	StringBundler sb = new StringBundler(row.getPrimaryKey() == null ? 4 : 6);

	sb.append(namespace);
	sb.append(partialId);
	sb.append(StringPool.UNDERLINE);
	sb.append(row.getRowId());

	if (row.getPrimaryKey() != null) {
		sb.append(StringPool.UNDERLINE);
		sb.append(row.getPrimaryKey());
	}

	String rowElementId = HtmlUtil.escapeAttribute(sb.toString());

	Map<String, Object> data = row.getData();

	if ((data == null) || (data instanceof AbstractMap)) {
		data = new HashMap<String, Object>();

		if (row.getData() instanceof AbstractMap) {
			data.putAll(row.getData());
		}
	}

	data.put("rowElementId", rowElementId);

	row.setData(data);

	return rowElementId;
}
%>