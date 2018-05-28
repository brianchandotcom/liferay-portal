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

<%@ include file="/admin/init.jsp" %>

<%
OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();
%>

<aui:model-context bean="<%= oAuth2Application %>" model="<%= OAuth2Application.class %>" />

<aui:fieldset>
	<aui:input name="name" required="<%= true %>" />

	<aui:input name="homePageURL" />

	<c:if test="<%= oAuth2Application != null %>">
		<aui:input name="description" type="textarea" />
	</c:if>

	<aui:input helpMessage="redirect-uris-help" label="redirect-uris" name="redirectURIs" />

	<c:if test="<%= oAuth2Application != null %>">
		<aui:input name="privacyPolicyURL" />
	</c:if>

	<aui:select name="clientProfile">

		<%
		ClientProfile[] clientProfiles = ClientProfile.values();
		Arrays.sort(
			clientProfiles, new Comparator<ClientProfile>() {

				@Override
				public int compare(ClientProfile clientProfile1, ClientProfile clientProfile2) {
					return clientProfile1.id() - clientProfile2.id();
				}

			});

		for (ClientProfile clientProfile : clientProfiles) {
		%>

			<aui:option label="<%= clientProfile.name() %>" value="<%= clientProfile.id() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:fieldset label="allowed-grant-types">
		<aui:field-wrapper>
			<div id="<portlet:namespace />allowedGrantTypes">

				<%
				List<GrantType> allowedGrantTypesList = new ArrayList<>();

				if (oAuth2Application != null) {
					allowedGrantTypesList = oAuth2Application.getAllowedGrantTypesList();
				}

				List<GrantType> oAuth2Grants = oAuth2AdminPortletDisplayContext.getGrantTypes(portletPreferences);

				for (GrantType grantType : oAuth2Grants) {
					Set<String> cssClasses = new HashSet<>();

					for (ClientProfile clientProfile : ClientProfile.values()) {
						if (clientProfile.grantTypes().contains(grantType)) {
							cssClasses.add("client-profile-" + clientProfile.id());
						}
					}

					String cssClassesStr = StringUtil.merge(cssClasses, StringPool.SPACE);

					boolean checked = false;

					if ((oAuth2Application == null) || allowedGrantTypesList.contains(grantType)) {
						checked = true;
					}

					String name = "grant-" + grantType.name();

					checked = ParamUtil.getBoolean(request, name, checked);
				%>

					<div class="allowedGrantType <%= cssClassesStr %>">
						<div class="custom-checkbox custom-control">
							<label>
								<input class="custom-control-input"<%= checked ? " checked" : "" %> data-isredirect="<%= grantType.isRequiresRedirectURI() %>" data-issupportsconfidentialclients="<%= grantType.isSupportsConfidentialClients() %>" data-issupportspublicclients="<%= grantType.isSupportsPublicClients() %>" name='<%= renderResponse.getNamespace() + name %>' type="checkbox">
								<span class="custom-control-label">
									<span class="custom-control-label-text">
										<liferay-ui:message key="<%= grantType.name() %>" />
									</span>
								</span>
							</label>
						</div>
					</div>

				<%
				}
				%>

			</div>
		</aui:field-wrapper>
	</aui:fieldset>

	<c:if test="<%= oAuth2Application != null %>">
		<aui:fieldset label="supported-features">
			<aui:field-wrapper>

				<%
				List<String> oAuth2ApplicationFeaturesList = new ArrayList<>();

				if (oAuth2Application != null) {
					oAuth2ApplicationFeaturesList = oAuth2Application.getFeaturesList();
				}

				String[] oAuth2Features = oAuth2AdminPortletDisplayContext.getOAuth2Features(portletPreferences);

				for (String oAuth2Feature : oAuth2Features) {
					boolean checked = false;

					if ((oAuth2Application != null) && oAuth2ApplicationFeaturesList.contains(oAuth2Feature)) {
						checked = true;
					}

					String name = "feature-" + oAuth2Feature;

					checked = ParamUtil.getBoolean(request, name, checked);
				%>

					<div class="custom-checkbox custom-control">
						<label>
							<input class="custom-control-input"<%= checked ? " checked" : "" %> name="<%= renderResponse.getNamespace() + HtmlUtil.escapeAttribute(name) %>" type="checkbox" />

							<span class="custom-control-label">
								<span class="custom-control-label-text">
									<liferay-ui:message key="<%= HtmlUtil.escape(oAuth2Feature) %>" />
								</span>
							</span>
						</label>
					</div>

				<%
				}
				%>

			</aui:field-wrapper>
		</aui:fieldset>
	</c:if>
</aui:fieldset>