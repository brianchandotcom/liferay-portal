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
FragmentEntryLinkDisplayContext fragmentEntryLinkDisplayContext = new FragmentEntryLinkDisplayContext(renderRequest, renderResponse);

FragmentEntry fragmentEntry = fragmentEntryLinkDisplayContext.getFragmentEntry();

StringBundler titleSB = new StringBundler();

titleSB.append(LanguageUtil.get(request, "usages-and-propagation"));
titleSB.append(StringPool.SPACE);
titleSB.append(StringPool.OPEN_PARENTHESIS);
titleSB.append(fragmentEntry.getName());
titleSB.append(StringPool.CLOSE_PARENTHESIS);

portletDisplay.setTitle(titleSB.toString());
portletDisplay.setURLBack(ParamUtil.getString(request, "redirect"));
portletDisplay.setShowBackIcon(true);
%>