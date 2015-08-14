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

<h3><liferay-ui:message key="default-interactions" /></h3>

<liferay-ui:error key="socialInteractionsInvalid" message="please-select-the-type-of-social-interaction" />
<liferay-ui:error key="socialInteractionsSocialRelationTypes" message="please-select-at-least-one-social-relation-type" />

<%
SocialInteractionsConfiguration defaultSocialInteractionsConfiguration = SocialInteractionsConfigurationUtil.getSocialInteractionsConfiguration(company.getCompanyId(), request);
%>

<div id="<portlet:namespace />socialInteractionsSettings">
	<aui:input checked="<%= defaultSocialInteractionsConfiguration.isSocialInteractionsAnyUserEnabled() %>" id="socialInteractionsAnyUser" label="all-users-can-interact-with-each-other" name="settings--socialInteractionsType--" type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.ALL_USERS.toString() %>" />

	<aui:input checked="<%= defaultSocialInteractionsConfiguration.isSocialInteractionsSelectUsersEnabled() %>" id="socialInteractionsChooseUsers" label="define-social-interactions-for-users" name="settings--socialInteractionsType--" type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.SELECT_USERS.toString() %>" />

	<div class="social-interactions-users" id="<portlet:namespace />socialInteractionsUsersWrapper">
		<aui:input checked="<%= defaultSocialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" label="site-members-can-interact-with-each-other" name="settings--socialInteractionsSitesEnabled--" type="checkbox" value="<%= defaultSocialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" />

		<aui:input checked="<%= defaultSocialInteractionsConfiguration.isSocialInteractionsSocialRelationTypesEnabled() %>" label="define-social-relation-types-for-interaction" name="settings--socialInteractionsSocialRelationTypesEnabled--" type="checkbox" value="<%= defaultSocialInteractionsConfiguration.isSocialInteractionsSocialRelationTypesEnabled() %>" />

		<aui:input name="settings--socialInteractionsSocialRelationTypes--" type="hidden" value="<%= defaultSocialInteractionsConfiguration.getSocialInteractionsSocialRelationTypes() %>" />

		<%
		List<Integer> allSocialInteractionsSocialRelationTypes = SocialRelationTypesUtil.getAllSocialRelationTypes();
		%>

		<div class="social-interactions-social-relations" id="<portlet:namespace />socialRelations">
			<aui:field-wrapper>

				<%

				// Left list

				List leftList = new ArrayList();

				int[] socialInteractionsSocialRelationTypesArray = defaultSocialInteractionsConfiguration.getSocialInteractionsSocialRelationTypesArray();

				for (int socialInteractionSocialRelationType : socialInteractionsSocialRelationTypesArray) {
					leftList.add(new KeyValuePair(Integer.toString(socialInteractionSocialRelationType), LanguageUtil.get(request, SocialRelationTypesUtil.getTypeLabel(socialInteractionSocialRelationType))));
				}

				// Right list

				List rightList = new ArrayList();

				for (int socialInteractionsSocialRelationType : allSocialInteractionsSocialRelationTypes) {
					if (Arrays.binarySearch(socialInteractionsSocialRelationTypesArray, socialInteractionsSocialRelationType) < 0) {
						rightList.add(new KeyValuePair(Integer.toString(socialInteractionsSocialRelationType), LanguageUtil.get(request, SocialRelationTypesUtil.getTypeLabel(socialInteractionsSocialRelationType))));
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

<%
List<Portlet> socialInteractionsConfigurationPortlets = new ArrayList<Portlet>();

List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(company.getCompanyId());

String tabs1Names = StringPool.BLANK;

for (Portlet portlet : portlets) {
	if (portlet.isSocialInteractionsConfiguration()) {
		if (Validator.isNotNull(tabs1Names)) {
			tabs1Names += (StringPool.COMMA);
		}

		tabs1Names += PortalUtil.getPortletTitle(portlet, themeDisplay.getLocale());

		socialInteractionsConfigurationPortlets.add(portlet);
	}
}
%>

<c:if test="<%= !socialInteractionsConfigurationPortlets.isEmpty() %>">
	<h3><liferay-ui:message key="application-interactions" /></h3>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		refresh="<%= false %>"
	>

		<%
		for (Portlet socialInteractionsConfigurationPortlet : socialInteractionsConfigurationPortlets) {
			SocialInteractionsConfiguration socialInteractionsConfiguration = SocialInteractionsConfigurationUtil.getSocialInteractionsConfiguration(company.getCompanyId(), request, socialInteractionsConfigurationPortlet.getPortletId());
		%>

			<liferay-ui:section>
				<div id="<portlet:namespace />socialInteractionsSettings">
					<aui:input checked="<%= socialInteractionsConfiguration.isInheritSocialInteractionsConfiguration() %>" id='<%= "inheritSocialInteractionsConfiguration" + socialInteractionsConfigurationPortlet.getPortletId() %>' label="use-default-interactions-settings" name='<%= "settings--socialInteractionsType" + socialInteractionsConfigurationPortlet.getPortletId() + "--" %>' type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.INHERIT.toString() %>" />

					<aui:input checked="<%= !socialInteractionsConfiguration.isInheritSocialInteractionsConfiguration() && socialInteractionsConfiguration.isSocialInteractionsAnyUserEnabled() %>" id='<%= "socialInteractionsAnyUser" + socialInteractionsConfigurationPortlet.getPortletId() %>' label="all-users-can-interact-with-each-other" name='<%= "settings--socialInteractionsType" + socialInteractionsConfigurationPortlet.getPortletId() + "--" %>' type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.ALL_USERS.toString() %>" />

					<aui:input checked="<%= !socialInteractionsConfiguration.isInheritSocialInteractionsConfiguration() && socialInteractionsConfiguration.isSocialInteractionsSelectUsersEnabled() %>" id='<%= "socialInteractionsChooseUsers" + socialInteractionsConfigurationPortlet.getPortletId() %>' label="define-social-interactions-for-users" name='<%= "settings--socialInteractionsType" + socialInteractionsConfigurationPortlet.getPortletId() + "--" %>' type="radio" value="<%= SocialInteractionsConfiguration.SocialInteractionsType.SELECT_USERS.toString() %>" />

					<div class="social-interactions-users" id="<%= renderResponse.getNamespace() + "socialInteractionsUsersWrapper" + socialInteractionsConfigurationPortlet.getPortletId() %>">
						<aui:input checked="<%= socialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" label="site-members-can-interact-with-each-other" name='<%= "settings--socialInteractionsSitesEnabled" + socialInteractionsConfigurationPortlet.getPortletId() + "--" %>' type="checkbox" value="<%= socialInteractionsConfiguration.isSocialInteractionsSitesEnabled() %>" />

						<aui:input checked="<%= socialInteractionsConfiguration.isSocialInteractionsSocialRelationTypesEnabled() %>" label="define-social-relation-types-for-interaction" name='<%= "settings--socialInteractionsSocialRelationTypesEnabled" + socialInteractionsConfigurationPortlet.getPortletId() + "--" %>' type="checkbox" value="<%= socialInteractionsConfiguration.isSocialInteractionsSocialRelationTypesEnabled() %>" />

						<aui:input name='<%= "settings--socialInteractionsSocialRelationTypes" + socialInteractionsConfigurationPortlet.getPortletId() + "--" %>' type="hidden" value="<%= socialInteractionsConfiguration.getSocialInteractionsSocialRelationTypes() %>" />

						<div class="social-interactions-social-relations" id="<%= renderResponse.getNamespace() + "socialRelations" + socialInteractionsConfigurationPortlet.getPortletId() %>">
							<aui:field-wrapper>

								<%

								// Left list

								List leftList = new ArrayList();

								int[] socialInteractionsSocialRelationTypesArray = socialInteractionsConfiguration.getSocialInteractionsSocialRelationTypesArray();

								for (int socialInteractionSocialRelationType : socialInteractionsSocialRelationTypesArray) {
									leftList.add(new KeyValuePair(Integer.toString(socialInteractionSocialRelationType), LanguageUtil.get(request, SocialRelationTypesUtil.getTypeLabel(socialInteractionSocialRelationType))));
								}

								// Right list

								List rightList = new ArrayList();

								for (int socialInteractionsSocialRelationType : allSocialInteractionsSocialRelationTypes) {
									if (Arrays.binarySearch(socialInteractionsSocialRelationTypesArray, socialInteractionsSocialRelationType) < 0) {
										rightList.add(new KeyValuePair(Integer.toString(socialInteractionsSocialRelationType), LanguageUtil.get(request, SocialRelationTypesUtil.getTypeLabel(socialInteractionsSocialRelationType))));
									}
								}
								%>

								<liferay-ui:input-move-boxes
									leftBoxName='<%= "currentSocialInteractionsSocialRelationTypes" + socialInteractionsConfigurationPortlet.getPortletId() %>'
									leftList="<%= leftList %>"
									leftTitle="current"
									rightBoxName='<%= "availableSocialInteractionsSocialRelationTypes" + socialInteractionsConfigurationPortlet.getPortletId() %>'
									rightList="<%= rightList %>"
									rightTitle="available"
								/>
							</aui:field-wrapper>
						</div>
					</div>
				</div>
			</liferay-ui:section>

		<%
		}
		%>

	</liferay-ui:tabs>
</c:if>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	Util.toggleBoxes('<portlet:namespace />socialInteractionsSocialRelationTypesEnabled', '<portlet:namespace />socialRelations');

	Util.toggleRadio('<portlet:namespace />socialInteractionsAnyUser', '', '<portlet:namespace />socialInteractionsUsersWrapper');
	Util.toggleRadio('<portlet:namespace />socialInteractionsChooseUsers', '<portlet:namespace />socialInteractionsUsersWrapper', '');

	<%
	for (Portlet socialInteractionsConfigurationPortlet : socialInteractionsConfigurationPortlets) {
	%>

		Util.toggleBoxes('<%= renderResponse.getNamespace() + "socialInteractionsSocialRelationTypesEnabled" + socialInteractionsConfigurationPortlet.getPortletId() %>', '<%= renderResponse.getNamespace() + "socialRelations" + socialInteractionsConfigurationPortlet.getPortletId() %>');

		Util.toggleRadio('<%= renderResponse.getNamespace() + "inheritSocialInteractionsConfiguration" + socialInteractionsConfigurationPortlet.getPortletId() %>', '', '<%= renderResponse.getNamespace() + "socialInteractionsUsersWrapper" + socialInteractionsConfigurationPortlet.getPortletId() %>');
		Util.toggleRadio('<%= renderResponse.getNamespace() + "socialInteractionsAnyUser" + socialInteractionsConfigurationPortlet.getPortletId() %>', '', '<%= renderResponse.getNamespace() + "socialInteractionsUsersWrapper" + socialInteractionsConfigurationPortlet.getPortletId() %>');
		Util.toggleRadio('<%= renderResponse.getNamespace() + "socialInteractionsChooseUsers" + socialInteractionsConfigurationPortlet.getPortletId() %>', '<%= renderResponse.getNamespace() + "socialInteractionsUsersWrapper" + socialInteractionsConfigurationPortlet.getPortletId() %>', '');

	<%
	}
	%>

	var form = $(document.<portlet:namespace />fm);

	form.on(
		'submit',
		function() {
			form.fm('settings--socialInteractionsSocialRelationTypes--').val(Util.listSelect(form.fm('currentSocialInteractionsSocialRelationTypes')));

			<%
			for (Portlet socialInteractionsConfigurationPortlet : socialInteractionsConfigurationPortlets) {
			%>

				form.fm('settings--socialInteractionsSocialRelationTypes<%= socialInteractionsConfigurationPortlet.getPortletId() %>--').val(Util.listSelect(form.fm('currentSocialInteractionsSocialRelationTypes<%= socialInteractionsConfigurationPortlet.getPortletId() %>')));

			<%
			}
			%>

		}
	);
</aui:script>