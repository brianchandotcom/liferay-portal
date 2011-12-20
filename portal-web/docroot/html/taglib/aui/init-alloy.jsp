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

<%
boolean useMarkup = GetterUtil.getBoolean((Serializable)_options.get("useMarkup"), true);

Object boundingBox = (Object)request.getAttribute(_NAMESPACE.concat("boundingBox"));
Object contentBox = (Object)request.getAttribute(_NAMESPACE.concat("contentBox"));
Object srcNode = (Object)request.getAttribute(_NAMESPACE.concat("srcNode"));

boolean hasBoundingBox = false;

if (Validator.isNotNull(boundingBox)) {
	hasBoundingBox = true;
}

boolean hasContentBox = false;

if (Validator.isNotNull(contentBox)) {
	hasContentBox = true;
}

boolean hasSrcNode = false;

if (Validator.isNotNull(srcNode)) {
	hasSrcNode = true;
}

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