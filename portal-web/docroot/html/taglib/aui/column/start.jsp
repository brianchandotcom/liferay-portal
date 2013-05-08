<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/aui/column/init.jsp" %>

<div class="column <%= (columnWidth > 0) ? "w" + columnWidth : StringPool.BLANK %> <%= cssClass %> <%= first ? "column-first" : StringPool.BLANK %> <%= last ? "column-last" : StringPool.BLANK %>" <%= Validator.isNotNull(id) ? "id=\"" + namespace + id + "\"" : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<div class="column-content <%= first ? "column-content-first" : StringPool.BLANK %> <%= last ? "column-content-last" : StringPool.BLANK %> <%= cssClasses %>" <%= Validator.isNotNull(id) ? "id=\"" + namespace + id + "Content\"" : StringPool.BLANK %>>