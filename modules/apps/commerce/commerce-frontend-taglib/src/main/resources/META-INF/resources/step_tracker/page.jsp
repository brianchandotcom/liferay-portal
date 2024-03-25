<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/step_tracker/init.jsp" %>

<div class="step-tracker-root" id="<%= stepTrackerId %>"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"portletId", portletDisplay.getRootPortletId()
		).put(
			"spritemap", spritemap
		).put(
			"steps", steps
		).put(
			"stepTrackerId", stepTrackerId
		).build()
	%>'
	module="{stepTracker} from commerce-frontend-taglib"
/>