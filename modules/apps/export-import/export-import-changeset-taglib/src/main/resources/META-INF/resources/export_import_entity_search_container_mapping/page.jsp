<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/export_import_entity_search_container_mapping/init.jsp" %>

<div id="<portlet:namespace /><%= searchContainerMappingId %>">

	<%
	List<com.liferay.portal.kernel.dao.search.ResultRow> resultRows = searchContainer.getResultRows();

	for (com.liferay.portal.kernel.dao.search.ResultRow resultRow : resultRows) {
		StagedModel stagedModel = (StagedModel)resultRow.getObject();

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"classNameId", ExportImportClassedModelUtil.getClassNameId(stagedModel)
		).put(
			"groupId", BeanPropertiesUtil.getLong(stagedModel, "groupId")
		).put(
			"rowPK", resultRow.getPrimaryKey()
		).put(
			"uuid", stagedModel.getUuid()
		).build();
	%>

		<div <%= HtmlUtil.buildData(data) %>></div>

	<%
	}
	%>

</div>