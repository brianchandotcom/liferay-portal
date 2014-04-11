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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<liferay-ui:error-marker key="errorSection" value="relations" />
<liferay-ui:error key="restrictedRelationInvalid" message="please-restrict-by-site-or-type" />
<liferay-ui:error key="selectAtLeastOneRelation" message="please-select-at-least-one-relation-type" />

<h3><liferay-ui:message key="relations" /><h3>

<%
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

boolean interactionsEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "interactionsEnabled", true);

boolean interactionsAnyUser = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "interactionsAnyUser", true);

boolean interactionsSitesEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "interactionsSitesEnabled", true);

boolean interactionsSocialRelationTypesEnabled = PrefsParamUtil.getBoolean(companyPortletPreferences, request, "interactionsSocialRelationTypesEnabled", true);

String interactionsSocialRelationTypesString = companyPortletPreferences.getValue("interactionsSocialRelationTypes", StringPool.BLANK);
int[] interactionsSocialRelationTypes = GetterUtil.getIntegerValues(StringUtil.split(interactionsSocialRelationTypesString));
%>

<aui:input checked="<%= interactionsEnabled %>" label="enable-interactions" name="settings--interactionsEnabled--" type="checkbox" value="<%= interactionsEnabled %>" />

<div class="interactions-settings" id="<portlet:namespace />interactionsSettings">
	<aui:input checked="<%= interactionsAnyUser %>" id="interactionsAnyUser" label="each-user-can-interact-with-any-user" name="settings--interactionsAnyUser--" type="radio" value="<%= true %>" />

	<aui:input checked="<%= !interactionsAnyUser %>" id="interactionsChooseUsers" label="each-user-can-interact-only-with" name="settings--interactionsAnyUser--" type="radio" value="<%= false %>" />

	<div class="interactions-users" id="<portlet:namespace />interactionsUsersWrapper">
		<aui:input checked="<%= interactionsSitesEnabled %>" label="users-that-belong-to-the-sites-that-the-user-also-belongs-to" name="settings--interactionsSitesEnabled--" type="checkbox" value="<%= interactionsSitesEnabled %>" />

		<aui:input checked="<%= interactionsSocialRelationTypesEnabled %>" label="users-with-the-following-social-relations" name="settings--interactionsSocialRelationTypesEnabled--" type="checkbox" value="<%= interactionsSocialRelationTypesEnabled %>" />

		<aui:input name="settings--interactionsSocialRelationTypes--" type="hidden" value="<%= interactionsSocialRelationTypesString %>" />

		<%
		List<Integer> allSocialRelationTypes = SocialRelationConstants.getAllSocialRelationTypes();
		%>

		<div class="social-relations" id="<portlet:namespace />socialRelations">
			<aui:field-wrapper>

				<%

				// Left list

				List leftList = new ArrayList();

				int[] socialRelationTypesArray = interactionsSocialRelationTypes;

				for (int socialRelationType : socialRelationTypesArray) {
					leftList.add(new KeyValuePair(Integer.toString(socialRelationType), LanguageUtil.get(pageContext, SocialRelationConstants.getTypeLabel(socialRelationType))));
				}

				// Right list

				List rightList = new ArrayList();

				for (int socialRelationType : allSocialRelationTypes) {
					if (Arrays.binarySearch(socialRelationTypesArray, socialRelationType) < 0) {
						rightList.add(new KeyValuePair(Integer.toString(socialRelationType), LanguageUtil.get(pageContext, SocialRelationConstants.getTypeLabel(socialRelationType))));
					}
				}
				%>

				<liferay-ui:input-move-boxes
					leftBoxName="currentSocialRelationTypes"
					leftList="<%= leftList %>"
					leftTitle="current"
					rightBoxName="availableSocialRelationTypes"
					rightList="<%= rightList %>"
					rightTitle="available"
				/>
			</aui:field-wrapper>
		</div>
	</div>
</div>

<aui:script use="aui-base,liferay-util-list-fields">
	Liferay.Util.toggleBoxes('<portlet:namespace />interactionsEnabledCheckbox','<portlet:namespace />interactionsSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />interactionsSocialRelationTypesEnabledCheckbox','<portlet:namespace />socialRelations');

	Liferay.Util.toggleRadio('<portlet:namespace />interactionsAnyUser', '', '<portlet:namespace />interactionsUsersWrapper');
	Liferay.Util.toggleRadio('<portlet:namespace />interactionsChooseUsers','<portlet:namespace />interactionsUsersWrapper', '');

	var form = A.one('#<portlet:namespace />fm');

	form.on('submit', function(e) {
		var socialRelationTypesInputName = '<portlet:namespace />settings--interactionsSocialRelationTypes--';

		document.<portlet:namespace />fm[socialRelationTypesInputName].value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentSocialRelationTypes);
	});
</aui:script>