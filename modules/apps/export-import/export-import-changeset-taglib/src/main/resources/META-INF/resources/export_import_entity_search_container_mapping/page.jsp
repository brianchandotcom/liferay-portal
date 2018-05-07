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

<%@ include file="/export_import_entity_search_container_mapping/init.jsp" %>

<div id="<portlet:namespace /><%= searchContainerMappingId %>">

	<%
	List<ResultRow> resultRows = searchContainer.getResultRows();

	for (ResultRow resultRow : resultRows) {
		Map<String, Object> data = new HashMap<>();

		data.put("rowPK", resultRow.getPrimaryKey());

		StagedModel stagedModel = (StagedModel)resultRow.getObject();

		data.put("classNameId", ExportImportClassedModelUtil.getClassNameId(stagedModel));
		data.put("groupId", BeanPropertiesUtil.getLong(stagedModel, "groupId"));
		data.put("uuid", stagedModel.getUuid());
	%>

		<div <%= HtmlUtil.buildData(data) %>></div>

	<%
	}
	%>

</div>