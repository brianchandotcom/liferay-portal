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
String redirect = ParamUtil.getString(request, "redirect");

long wsrpProducerId = ParamUtil.getLong(request, "wsrpProducerId");

WSRPProducer wsrpProducer = WSRPProducerLocalServiceUtil.fetchWSRPProducer(wsrpProducerId);

String version = Constants.WSRP_V2;

if (wsrpProducer != null) {
	version = GetterUtil.getString(wsrpProducer.getVersion(), Constants.WSRP_V2);
}

String[] portletIds = StringUtil.split(BeanParamUtil.getString(wsrpProducer, request, "portletIds"));

String portalServletContextName = PortalUtil.getPathContext();

ServletContext portalServletContext = ServletContextPool.get(portalServletContextName);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((wsrpProducer == null) ? LanguageUtil.get(request, "new-producer") : wsrpProducer.getName()));
%>

<portlet:actionURL name="updateWSRPProducer" var="updateWSRPProducerURL" />

<aui:form action="<%= updateWSRPProducerURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveProducer();" %>'>
	<aui:input name="mvcPath" type="hidden" value="/admin/edit_producer.jsp" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="wsrpProducerId" type="hidden" value="<%= wsrpProducerId %>" />
	<aui:input name="portletIds" type="hidden" value="" />

	<liferay-ui:error exception="<%= WSRPProducerNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= wsrpProducer %>" model="<%= WSRPProducer.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" name="name" />

			<aui:select name="version" value="<%= version %>">
				<aui:option label="<%= Constants.WSRP_V2 %>" />
				<aui:option label="<%= Constants.WSRP_V1 %>" />
			</aui:select>

			<c:if test="<%= wsrpProducer != null %>">
				<aui:field-wrapper label="url">

					<%
					String wsrpProducerURL = wsrpProducer.getURL(themeDisplay.getPortalURL() + "/o/wsrp-service");
					%>

					<aui:a href="<%= wsrpProducerURL %>" target="_blank"><%= wsrpProducerURL %></aui:a><br />
				</aui:field-wrapper>
			</c:if>

			<aui:field-wrapper label="portlets">

				<%

				// Left list

				List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

				for (String portletId : portletIds) {
					Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletId);

					if ((portlet == null) || portlet.isUndeployedPortlet()) {
						continue;
					}

					leftList.add(new KeyValuePair(portletId, PortalUtil.getPortletTitle(portlet, portalServletContext, locale)));
				}

				leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

				// Right list

				List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

				for (int i = 0; i < portletIds.length; i++) {
					String portletId = portletIds[i];

					int index = portletId.indexOf(PortletConstants.INSTANCE_SEPARATOR);

					if (index != -1) {
						portletIds[i] = portletId.substring(0, index);
					}
				}

				Arrays.sort(portletIds);

				Iterator<Portlet> itr = PortletLocalServiceUtil.getPortlets(company.getCompanyId(), false, false).iterator();

				while (itr.hasNext()) {
					Portlet portlet = (Portlet)itr.next();

					if (portlet.isUndeployedPortlet()) {
						continue;
					}

					if (!portlet.isRemoteable()) {
						continue;
					}

					String portletId = portlet.getPortletId();

					if (Arrays.binarySearch(portletIds, portletId) < 0) {
						rightList.add(new KeyValuePair(portletId, PortalUtil.getPortletTitle(portlet, portalServletContext, locale)));
					}
				}

				rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
				%>

				<liferay-ui:input-move-boxes
					leftBoxName="currentPortletIds"
					leftList="<%= leftList %>"
					leftTitle="current"
					rightBoxName="availablePortletIds"
					rightList="<%= rightList %>"
					rightTitle="available"
				/>
			</aui:field-wrapper>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveProducer',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />portletIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentPortletIds);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%
PortletURL producersURL = renderResponse.createRenderURL();

producersURL.setParameter("tabs1", "producers");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "producers"), producersURL.toString());

if (wsrpProducer != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-producer"), currentURL);
}
%>