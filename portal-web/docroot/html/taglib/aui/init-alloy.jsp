<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ taglib prefix="alloy" uri="http://alloy.liferay.com/tld/alloy" %>
<%@ taglib prefix="alloy-util" uri="http://alloy.liferay.com/tld/alloy_util" %>
<%@ page import="com.liferay.alloy.util.MarkupUtil" %>

<%
java.lang.Object boundingBox = (java.lang.Object)request.getAttribute(_NAMESPACE.concat("boundingBox"));
java.lang.Object contentBox = (java.lang.Object)request.getAttribute(_NAMESPACE.concat("contentBox"));
java.lang.Object srcNode = (java.lang.Object)request.getAttribute(_NAMESPACE.concat("srcNode"));

boolean hasBoundingBox = GetterUtil.getBoolean(String.valueOf(boundingBox));
boolean hasContentBox = GetterUtil.getBoolean(String.valueOf(contentBox));
boolean hasSrcNode = GetterUtil.getBoolean(String.valueOf(srcNode));

boolean useJavaScript = GetterUtil.getBoolean((Serializable)dynamicAttributes.get("useJavaScript"), true);
boolean useMarkup = GetterUtil.getBoolean((Serializable)dynamicAttributes.get("useMarkup"), true);

String uniqueId = StringPool.BLANK;

if (useMarkup) {
	uniqueId = MarkupUtil.getUniqueId();

	String prefix = StringPool.POUND.concat(uniqueId);

	if (!hasBoundingBox) {
		boundingBox = prefix.concat("BoundingBox");

		_options.put("boundingBox", boundingBox);
	}

	if (!hasSrcNode && !hasContentBox) {
		srcNode = prefix.concat("SrcNode");

		_options.put("srcNode", srcNode);
	}

	if (!hasSrcNode && hasContentBox) {
		contentBox = prefix.concat("ContentBox");

		_options.put("contentBox", contentBox);
	}
}
%>