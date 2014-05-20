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

<liferay-ui:error-marker key="errorSection" value="interactions" />

<h3><liferay-ui:message key="interactions" /></h3>

<liferay-ui:error key="socialInteractionsInvalid" message="please-select-the-type-of-social-interaction" />
<liferay-ui:error key="socialInteractionsSocialRelationTypes" message="please-select-at-least-one-social-relation-type" />

<%
SocialInteractionsConfiguration socialInteractionsConfiguration = SocialInteractionsConfigurationUtil.getSocialInteractionsConfiguration(company.getCompanyId(), request);
%>

<aui:input checked="<%= socialInteractionsConfiguration.isSocialInteractionsEnabled() %>" label="enable-social-interactions" name="settings--socialInteractionsEnabled--" type="checkbox" value="<%= socialInteractionsConfiguration.isSocialInteractionsEnabled() %>" />

<div class="social-interactions-settings" id="<portlet:namespace />socialInteractionsSettings">
	<aui:input checked="<%= socialInteractionsConfiguration.isSocialInteractionsAnyUserEnabled() %>" id="socialInteractionsAnyUser" label="all-users-can-interact-with-each-other" name="settings--socialInteractionsAnyUserEnabled--" type="radio" value="<%= true %>" />

	<aui:input checked="<%= !socialInteractionsConfiguration.isSocialInteractionsAnyUserEnabled() %>" id="socialInteractionsChooseUsers" label="define-social-interactions-for-users" name="settings--socialInteractionsAnyUserEnabled--" type="radio" value="<%= false %>" />

	<div class="social-interactions-users" id="<portlet:namespace />socialInteractionsUsersWrapper">
		<aui:input checked="<%= socialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" label="site-members-can-interact-with-each-other" name="settings--socialInteractionsSitesEnabled--" type="checkbox" value="<%= socialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" />

		<aui:input checked="<%= socialInteractionsConfiguration.isSocialInteractionsSocialRelationTypesEnabled() %>" label="define-social-relation-types-for-interaction" name="settings--socialInteractionsSocialRelationTypesEnabled--" type="checkbox" value="<%= socialInteractionsConfiguration.isSocialInteractionsSocialRelationTypesEnabled() %>" />

		<aui:input name="settings--socialInteractionsSocialRelationTypes--" type="hidden" value="<%= socialInteractionsConfiguration.getSocialInteractionsSocialRelationTypes() %>" />

		<%
		List<Integer> allSocialInteractionsSocialRelationTypes = SocialRelationTypesUtil.getAllSocialRelationTypes();
		%>

		<div class="social-interactions-social-relations" id="<portlet:namespace />socialRelations">
			<aui:field-wrapper>

				<%

				// Left list

				List leftList = new ArrayList();

				int[] socialInteractionsSocialRelationTypesArray = socialInteractionsConfiguration.getSocialInteractionsSocialRelationTypesArray();

				for (int socialInteractionSocialRelationType : socialInteractionsSocialRelationTypesArray) {
					leftList.add(new KeyValuePair(Integer.toString(socialInteractionSocialRelationType), LanguageUtil.get(pageContext, SocialRelationTypesUtil.getTypeLabel(socialInteractionSocialRelationType))));
				}

				// Right list

				List rightList = new ArrayList();

				for (int socialInteractionsSocialRelationType : allSocialInteractionsSocialRelationTypes) {
					if (Arrays.binarySearch(socialInteractionsSocialRelationTypesArray, socialInteractionsSocialRelationType) < 0) {
						rightList.add(new KeyValuePair(Integer.toString(socialInteractionsSocialRelationType), LanguageUtil.get(pageContext, SocialRelationTypesUtil.getTypeLabel(socialInteractionsSocialRelationType))));
					}
				}
				%>

				<liferay-ui:input-move-boxes
					leftBoxName="currentSocialInteractionsSocialRelationTypes"
					leftList="<%= leftList %>"
					leftTitle="current"
					rightBoxName="availableSocialInteractionsSocialRelationTypes"
					rightList="<%= rightList %>"
					rightTitle="available"
				/>
			</aui:field-wrapper>
		</div>
	</div>
</div>

<aui:script use="aui-base,liferay-util-list-fields">
	Liferay.Util.toggleBoxes('<portlet:namespace />socialInteractionsEnabledCheckbox','<portlet:namespace />socialInteractionsSettings');
	Liferay.Util.toggleBoxes('<portlet:namespace />socialInteractionsSocialRelationTypesEnabledCheckbox','<portlet:namespace />socialRelations');

	Liferay.Util.toggleRadio('<portlet:namespace />socialInteractionsAnyUser', '', '<portlet:namespace />socialInteractionsUsersWrapper');
	Liferay.Util.toggleRadio('<portlet:namespace />socialInteractionsChooseUsers', '<portlet:namespace />socialInteractionsUsersWrapper', '');

	var form = A.one('#<portlet:namespace />fm');

	form.on(
		'submit',
		function(e) {
			var socialInteractionsSocialRelationTypesInputName = '<portlet:namespace />settings--socialInteractionsSocialRelationTypes--';

			document.<portlet:namespace />fm[socialInteractionsSocialRelationTypesInputName].value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentSocialInteractionsSocialRelationTypes);
		});
</aui:script>