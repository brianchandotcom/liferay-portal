<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/accounts_data_set/init.jsp" %>

<frontend-data-set:headless-display
	additionalProps="<%= additionalProps %>"
	apiURL="<%= apiURL %>"
	fdsActionDropdownItems="<%= fdsActionDropdownItems %>"
	id="<%= CommerceFragmentFDSNames.ACCOUNT_ENTRIES %>"
	propsTransformer="{AccountsFDSPropsTransformer} from commerce-fragment-impl"
	style="<%= displayStyle %>"
/>