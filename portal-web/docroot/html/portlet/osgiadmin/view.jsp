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
String tabs1 = ParamUtil.getString(request, "tabs1", "bundles");

String orderByCol = ParamUtil.getString(request, "orderByCol", "bundleId");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

BundleContext bundleContext = (BundleContext)OSGiServiceUtil.getBundleContext();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/osgiadmin/view");
portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:error exception="<%= OSGiException.class %>">

	<%
	OSGiException oe = (OSGiException)errorException;
	%>

	<%= LanguageUtil.get(pageContext, oe.getMessage()) %>

</liferay-ui:error>

<liferay-ui:tabs
	names="bundles,install-bundle"
	url="<%= portletURL.toString() %>"
/>

<portlet:actionURL var="editBundleURL">
	<portlet:param name="struts_action" value="/osgiadmin/edit_bundle" />
</portlet:actionURL>

<aui:form action="<%= editBundleURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="bundleId" type="hidden" />
</aui:form>

<c:choose>
	<c:when test='<%= tabs1.equals("bundles") %>'>
		<liferay-ui:search-container
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
		>
			<%
			List<Bundle> bundles = ListUtil.fromArray(bundleContext.getBundles());

			if (Validator.isNotNull(searchContainer.getOrderByCol())) {
				orderByCol = searchContainer.getOrderByCol();
			}

			if (Validator.isNotNull(searchContainer.getOrderByType())) {
				orderByType = searchContainer.getOrderByType();
			}

			bundles = ListUtil.sort(bundles, new com.liferay.util.PropertyComparator(orderByCol, orderByType.equalsIgnoreCase("asc"), false));

			int end = searchContainer.getEnd();

			if (bundles.size() < end) {
				end = bundles.size();
			}
			%>

			<liferay-ui:search-container-results
				results="<%= bundles.subList(searchContainer.getStart(), end) %>"
				total="<%= bundles.size() %>"
			/>

			<liferay-ui:search-container-row
				className="org.osgi.framework.Bundle"
				keyProperty="bundleId"
				modelVar="bundle"
			>

				<%
				Dictionary<String,String> headers = bundle.getHeaders(themeDisplay.getLanguageId());

				BundleStartLevel bundleStartLevel = (BundleStartLevel)bundle;
				%>

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="struts_action" value="/osgiadmin/edit_bundle" />
					<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="bundleId" value="<%= String.valueOf(bundle.getBundleId()) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="bundle-id"
					orderable="true"
					orderableProperty="bundleId"
					property="bundleId"
				/>
				<liferay-ui:search-container-column-text
					buffer="sb"
					name="name"
					orderable="true"
					orderableProperty="symbolicName"
				>

					<%
					String bundleName = headers.get(org.osgi.framework.Constants.BUNDLE_NAME);

					if (Validator.isNull(bundleName)) {
						bundleName = bundle.getSymbolicName();
					}

					sb.append("<a href=\"");
					sb.append(rowURL);
					sb.append("\"><strong>");
					sb.append(bundleName);
					sb.append("</strong></a><br/>");

					sb.append("<em>");
					sb.append(bundle.getSymbolicName());
					sb.append("</em>");

					String description = headers.get(org.osgi.framework.Constants.BUNDLE_DESCRIPTION);

					if (Validator.isNotNull(description)) {
						sb.append("<br/><br/><span>");
						sb.append(description);
						sb.append("</span>");
					}
					%>

				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text
					buffer="sb"
					href="<%= rowURL %>"
					name="last-modified"
					orderable="true"
					orderableProperty="lastModified"
				>

					<%
					Calendar cal = Calendar.getInstance();

					cal.setTimeInMillis(bundle.getLastModified());

					sb.append(dateFormatDateTime.format(cal.getTime()));
					%>

				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					property="version"
				/>
				<liferay-ui:search-container-column-text
					name="start-level"
				>

					<c:choose>
						<c:when test="<%= bundle.getBundleId() == 0 %>">
							<liferay-ui:message key="system" />
						</c:when>
						<c:otherwise>
							<aui:select label="" name="startLevel" onChange='<%= "Liferay.OSGiAdmin.Util.setStartLevel({bundleId:" + bundle.getBundleId() + ", message: \'" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-change-the-start-level-of-this-bundle") + "\', namespace: \'" + renderResponse.getNamespace() + "\', startLevel: AUI().one(this).val()})" %>'>

								<%
								for (int i = 1; i < 100; i++) {
									String label = String.valueOf(i);

									if (i == PropsValues.OSGI_FRAMEWORK_BEGINNING_STARTLEVEL) {
										label = LanguageUtil.get(pageContext, "framework");
									}
									else if (i == 1) {
										label = LanguageUtil.get(pageContext, "default");
									}

								%>

									<aui:option label="<%= label %>" selected="<%= (bundleStartLevel.getStartLevel() == i) %>" value="<%= i %>" />

								<%
								}
								%>

							</aui:select>
						</c:otherwise>
					</c:choose>

				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text
					buffer="sb"
					href="<%= rowURL %>"
					name="state"
				>

					<%
					sb.append("<span class='state-");
					sb.append(bundle.getBundleId());
					sb.append("'>");

					if (bundle.getState() == Bundle.ACTIVE) {
						sb.append(LanguageUtil.get(pageContext, "active").toUpperCase());
					}
					else if (bundle.getState() == Bundle.INSTALLED) {
						sb.append(LanguageUtil.get(pageContext, "installed").toUpperCase());
					}
					else if (bundle.getState() == Bundle.RESOLVED) {
						sb.append(LanguageUtil.get(pageContext, "resolved").toUpperCase());
					}
					else if (bundle.getState() == Bundle.STARTING) {
						sb.append(LanguageUtil.get(pageContext, "starting").toUpperCase());
					}
					else if (bundle.getState() == Bundle.STOPPING) {
						sb.append(LanguageUtil.get(pageContext, "stopping").toUpperCase());
					}
					else if (bundle.getState() == Bundle.UNINSTALLED) {
						sb.append(LanguageUtil.get(pageContext, "uninstalled").toUpperCase());
					}

					sb.append("</span>");
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					path="/html/portlet/osgiadmin/bundle_action.jsp"
				/>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:when test='<%= tabs1.equals("install-bundle") %>'>
		<portlet:actionURL var="editBundleURL">
			<portlet:param name="struts_action" value="/osgiadmin/edit_bundle" />
		</portlet:actionURL>

		<aui:form action="<%= editBundleURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<aui:fieldset>
				<aui:field-wrapper label="how-will-you-be-installing-the-bundle">
					<aui:input checked='true' inlineLabel="left" label="upload" name="<%= Constants.CMD %>" onClick='<%= renderResponse.getNamespace() + "switchType();" %>' type="radio" value="install-from-upload" />
					<aui:input inlineLabel="left" label="from-a-remote-location" name="<%= Constants.CMD %>" onClick='<%= renderResponse.getNamespace() + "switchType();" %>' type="radio" value="install-from-remote-location" />
				</aui:field-wrapper>

				<aui:input label="select-a-bundle-to-upload" name="importBundle" size="50" type="file" />

				<aui:input cssClass="aui-helper-hidden" label="specify-a-url-for-a-remote-bundle" name="location" size="50" type="text" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</c:when>
</c:choose>

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