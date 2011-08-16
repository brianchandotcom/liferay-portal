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

<%@ include file="/html/portlet/osgiadmin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long bundleId = ParamUtil.getLong(request, "bundleId");

BundleContext bundleContext = (BundleContext)OSGiServiceUtil.getBundleContext();

Bundle bundle = bundleContext.getBundle(bundleId);

Dictionary<String,String> headers = bundle.getHeaders(themeDisplay.getLanguageId());

String bundleName = headers.get(org.osgi.framework.Constants.BUNDLE_NAME);
String bundleDescription = headers.get(org.osgi.framework.Constants.BUNDLE_DESCRIPTION);

if (Validator.isNull(bundleName)) {
	bundleName = bundle.getSymbolicName();
}

String bundleUpdateLocation = headers.get(org.osgi.framework.Constants.BUNDLE_UPDATELOCATION);

if (Validator.isNull(bundleUpdateLocation)) {
	bundleUpdateLocation = bundle.getLocation();
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="<%= bundleName %>"
/>

<portlet:actionURL var="editBundleURL">
	<portlet:param name="struts_action" value="/osgiadmin/edit_bundle" />
</portlet:actionURL>

<aui:form action="<%= editBundleURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="bundleId" type="hidden" value="<%= bundleId %>" />

	<aui:layout>
		<aui:column columnWidth="75" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
			<liferay-ui:panel-container extended="<%= false %>" persistState="<%= true %>">

				<aui:field-wrapper label="symbolic-name">
					<%= bundle.getSymbolicName() %>
				</aui:field-wrapper>

				<aui:field-wrapper label="bundle-id">
					<%= bundleId %>
				</aui:field-wrapper>

				<c:if test="<%= Validator.isNotNull(bundleDescription) %>">
					<aui:field-wrapper label="description">
						<%= bundleDescription %>
					</aui:field-wrapper>
				</c:if>

				<c:if test="<%= Validator.isNotNull(bundleUpdateLocation) %>">
					<aui:field-wrapper label="location">
						<%= bundleUpdateLocation %>
					</aui:field-wrapper>
				</c:if>

				<aui:field-wrapper label="state">
					<span class='state-<%= bundle.getBundleId() %>'>

						<%
						String state = StringPool.BLANK;

						switch (bundle.getState()) {
							case Bundle.ACTIVE:
								state = LanguageUtil.get(pageContext, "active");
								break;
							case Bundle.INSTALLED:
								state = LanguageUtil.get(pageContext, "installed");
								break;
							case Bundle.RESOLVED:
								state = LanguageUtil.get(pageContext, "resolved");
								break;
							case Bundle.STARTING:
								state = LanguageUtil.get(pageContext, "starting");
								break;
							case Bundle.STOPPING:
								state = LanguageUtil.get(pageContext, "stopping");
								break;
							case Bundle.UNINSTALLED:
								state = LanguageUtil.get(pageContext, "uninstalled");
								break;
						}
						%>

						<%= state.toUpperCase() %>
					</span>
				</aui:field-wrapper>

				<liferay-ui:tabs
					names="headers,services-registered,services-in-use"
					refresh="<%= false %>"
				>
					<liferay-ui:section>
						<liferay-ui:search-container>
							<%
							List<String> keys = Collections.list(headers.keys());
							%>

							<liferay-ui:search-container-results
								results="<%= keys %>"
								total="<%= keys.size() %>"
							/>

							<liferay-ui:search-container-row
								className="java.lang.String"
								modelVar="headerKey"
							>

								<liferay-ui:search-container-column-text
									name="header"
									valign="top"
									value="<%= headerKey %>"
								/>

								<liferay-ui:search-container-column-text
									name="value"
									buffer="sb"
								>

									<%
									if (headerKey.equals(org.osgi.framework.Constants.BUNDLE_DOCURL)) {
										sb.append("<a href=\"");
										sb.append(headers.get(headerKey));
										sb.append("\" target=\"_new\">");
										sb.append(headers.get(headerKey));
										sb.append("</a>");
									}
									else {
										String[] parts = StringUtil.split(headers.get(headerKey));

										for (int i = 0; i < parts.length; i++) {
											if (i > 0) {
												sb.append("<br/>\n");
											}

											sb.append(parts[i]);
										}
									}
									%>

								</liferay-ui:search-container-column-text>

							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator paginate="<%= false %>" />
						</liferay-ui:search-container>
					</liferay-ui:section>
					<liferay-ui:section>
						<liferay-ui:search-container
							emptyResultsMessage="no-services-are-registered"
						>

							<%
							ServiceReference[] serviceReferences = bundle.getRegisteredServices();

							if (serviceReferences == null) {
								serviceReferences = new ServiceReference[0];
							}

							List<ServiceReference> serviceReferencesList = Arrays.asList(serviceReferences);
							%>

							<liferay-ui:search-container-results
								results="<%= serviceReferencesList %>"
								total="<%= serviceReferencesList.size() %>"
							/>

							<liferay-ui:search-container-row
								className="org.osgi.framework.ServiceReference"
								escapedModel="<%= false %>"
								modelVar="serviceReference"
							>

								<liferay-ui:search-container-column-text
									name="service"
									valign="top"
								>

									<%
									Object objectClass = serviceReference.getProperty(org.osgi.framework.Constants.OBJECTCLASS);
									%>

									<h4><%= StringUtil.merge((String[])objectClass, ", ") %></h4>

									<br />

									<%
									for (String key : serviceReference.getPropertyKeys()) {
										Object value = serviceReference.getProperty(key);

										if (key.equals(org.osgi.framework.Constants.OBJECTCLASS)) {
											continue;
										}
									%>
										<em>
											<u><%= key %></u>:
										</em>

										<c:choose>
											<c:when test="<%= value instanceof String[] %>">
												<%= StringUtil.merge((String[])value, ",<br/>") %>
											</c:when>
											<c:otherwise>
												<%= String.valueOf(value) %>
											</c:otherwise>
										</c:choose>

										</br />
									<%
									}
									%>

								</liferay-ui:search-container-column-text>

							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator paginate="<%= false %>" />
						</liferay-ui:search-container>
					</liferay-ui:section>
					<liferay-ui:section>
						<liferay-ui:search-container
							emptyResultsMessage="no-services-are-in-use"
						>

							<%
							ServiceReference[] serviceReferences = bundle.getServicesInUse();

							if (serviceReferences == null) {
								serviceReferences = new ServiceReference[0];
							}

							List<ServiceReference> serviceReferencesList = Arrays.asList(serviceReferences);
							%>

							<liferay-ui:search-container-results
								results="<%= serviceReferencesList %>"
								total="<%= serviceReferencesList.size() %>"
							/>

							<liferay-ui:search-container-row
								className="org.osgi.framework.ServiceReference"
								escapedModel="<%= false %>"
								modelVar="serviceReference"
							>

								<liferay-ui:search-container-column-text
									name="service"
									valign="top"
								>

									<%
									Object objectClass = serviceReference.getProperty(org.osgi.framework.Constants.OBJECTCLASS);
									%>

									<h4><%= StringUtil.merge((String[])objectClass, ", ") %></h4>

									<br />

									<%
									for (String key : serviceReference.getPropertyKeys()) {
										Object value = serviceReference.getProperty(key);

										if (key.equals(org.osgi.framework.Constants.OBJECTCLASS)) {
											continue;
										}
									%>
										<em>
											<u><%= key %></u>:
										</em>

										<c:choose>
											<c:when test="<%= value instanceof String[] %>">
												<%= StringUtil.merge((String[])value, ",<br/>") %>
											</c:when>
											<c:otherwise>
												<%= String.valueOf(value) %>
											</c:otherwise>
										</c:choose>

										</br />
									<%
									}
									%>

								</liferay-ui:search-container-column-text>

							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator paginate="<%= false %>" />
						</liferay-ui:search-container>
					</liferay-ui:section>
				</liferay-ui:tabs>

			</liferay-ui:panel-container>
		</aui:column>

		<aui:column columnWidth="25" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
			<div class="lfr-asset-summary">
				<liferay-ui:icon
					cssClass="lfr-asset-avatar"
					image='../file_system/large/compressed'
					message='<%= bundleName %>'
				/>

				<div class="lfr-asset-name">
					<h4><%= bundleName %></h4>
				</div>
			</div>

			<%
			request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
			request.setAttribute("edit_bundle.jsp-bundle", bundle);
			%>

			<liferay-util:include page="/html/portlet/osgiadmin/bundle_action.jsp" />
		</aui:column>
	</aui:layout>

	<c:if test="<%= bundle.getBundleId() != 0 %>">
		<aui:fieldset>
			<aui:field-wrapper label="how-will-you-be-updating-the-bundle">
				<aui:input checked='true' inlineLabel="left" label="upload" name="<%= Constants.CMD %>" onClick='<%= renderResponse.getNamespace() + "switchType();" %>' type="radio" value="update-from-upload" />
				<aui:input inlineLabel="left" label="from-a-remote-location" name="<%= Constants.CMD %>" onClick='<%= renderResponse.getNamespace() + "switchType();" %>' type="radio" value="update-from-remote-location" />
			</aui:field-wrapper>

			<aui:input label="select-a-bundle-to-upload" name="importBundle" size="50" type="file" />

			<aui:input cssClass="aui-helper-hidden" label="specify-a-url-for-a-remote-bundle" name="location" size="50" type="text" />
		</aui:fieldset>
	</c:if>

	<aui:button-row>
		<c:if test="<%= bundle.getBundleId() != 0 %>">
			<aui:button type="submit" value="update" />
		</c:if>

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script position="inline" use="aui-base">
	Liferay.provide(
		window,
		'<portlet:namespace />switchType',
		function() {
			A.one('#<portlet:namespace />importBundle').ancestor('.aui-field-text').toggle();
			A.one('#<portlet:namespace />location').ancestor('.aui-field-text').toggle();
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />uninstall',
		function(bundleId) {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-update-this-bundle") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'uninstall';
				document.<portlet:namespace />fm.<portlet:namespace />bundleId.value = bundleId;

				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-base']
	);
</aui:script>