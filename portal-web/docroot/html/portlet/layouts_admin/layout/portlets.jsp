<%@ page import="java.util.Comparator" %>

<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
	Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
	LayoutTypePortlet selLayoutTypePortlet = (LayoutTypePortlet) selLayout.getLayoutType();

	boolean isCustomizable = selLayoutTypePortlet.isCustomizable();

	// fetch portlets registered in the layout
	Set<String> regularPortlets = new HashSet<String>();
	Set<String> runtimePortlets = new HashSet<String>();
	UnicodeProperties typeSettings = selLayoutTypePortlet.getTypeSettingsProperties();
	for(String propKey : typeSettings.keySet()){
		if(propKey.startsWith(LayoutTypePortletConstants.RUNTIME_COLUMN_PREFIX)){
			runtimePortlets.addAll(Arrays.asList(StringUtil.split(typeSettings.get(propKey))));
		}
		if(propKey.startsWith(LayoutTypePortletConstants.COLUMN_PREFIX)){
			regularPortlets.addAll(Arrays.asList(StringUtil.split(typeSettings.get(propKey))));
		}
	}
	Set<String> nestedPortlets = new HashSet<String>();
	String[] nestedColumns = StringUtil.split(typeSettings.getProperty(LayoutTypePortletConstants.NESTED_COLUMN_IDS));
	for(String nestedCol : nestedColumns){
		nestedPortlets.addAll(Arrays.asList(StringUtil.split(typeSettings.get(nestedCol))));
	}

	// get all portlets rendered on this layout by their preferences
	List<com.liferay.portal.model.PortletPreferences> portletPreferencesList = PortletPreferencesLocalServiceUtil.getPortletPreferences(PortletKeys.PREFS_OWNER_ID_DEFAULT, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, selLayout.getPlid());
	List<Map<String, String>> models = new ArrayList<Map<String, String>>();
	for(com.liferay.portal.model.PortletPreferences portletPrefs : portletPreferencesList){
		Map<String, String> model = new HashMap<String, String>(3);

		String portletId = portletPrefs.getPortletId();
		Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);

		model.put("portlet-id", portletId);

		String portletTitle = null;
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.fromXML(
			themeDisplay.getCompanyId(), portletPrefs.getOwnerId(), portletPrefs.getOwnerType(), portletPrefs.getPlid(), portletId,
			portletPrefs.getPreferences());

		if (Validator.isNull(portletTitle)) {
			portletTitle = PortletConfigurationUtil.getPortletTitle(portletSetup, themeDisplay.getLanguageId());
		}
		if (Validator.isNull(portletTitle)) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);
			portletTitle = LanguageUtil.get(themeDisplay.getLocale(), JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(rootPortletId), null);
		}
		if (Validator.isNull(portletTitle) && (portlet != null) && (portlet.getPortletApp() != null) && portlet.getPortletApp().isWARFile()) {
			String servletCtxName = portlet.getPortletApp().getServletContextName();
			ServletContext servletCtx = ServletContextPool.get(servletCtxName);
			portletTitle = PortalUtil.getPortletTitle(portlet, servletCtx, themeDisplay.getLocale());
		}
		if (Validator.isNull(portletTitle) && (portlet != null)) {
			portletTitle = portlet.getDisplayName();
		}
		model.put("title", portletTitle);

		String placementType = "unknown";
		if(regularPortlets.contains(portletId)){
			placementType = "on-page";
		} else
		if(nestedPortlets.contains(portletId)){
			placementType = "nested";
		} else
		if(runtimePortlets.contains(portletId)){
			placementType = "embedded";
		} else
		if(portlet != null && portlet.isSystem()){
			placementType = "system";
		}
		model.put("placement-type", LanguageUtil.get(themeDisplay.getLocale(), placementType));

		String portletStatus = LanguageUtil.get(themeDisplay.getLocale(), "active");
		if(portlet != null && portlet.isUndeployedPortlet()){
			portletStatus = LanguageUtil.get(themeDisplay.getLocale(), "undeployed");
		} else
		if(portlet != null && !portlet.isReady()){
			portletStatus = LanguageUtil.format(themeDisplay.getLocale(), "is-not-ready", new Object[]{"portlet"});
		} else
		if(portlet != null && !portlet.isActive()){
			portletStatus = LanguageUtil.get(themeDisplay.getLocale(), "inactive");
		}
		model.put("status", portletStatus);

		models.add(model);
	}

	// sort by title
	Collections.sort(models, new Comparator<Map<String, String>>() {
		public int compare(Map<String, String> model1, Map<String, String> model2) {
			String portletTitle = model1.get("title");
			if(Validator.isNull(portletTitle)){
				return -1;
			}
			return portletTitle.compareTo(model2.get("title"));
		}
	});

	RowChecker rowChecker = null;
	PortletResponse portletResponse = renderResponse;
	if(Validator.isNull(portletResponse)){
		portletResponse = resourceResponse;
	}
	if(Validator.isNotNull(portletResponse)){
		rowChecker = new RowChecker(portletResponse);
		rowChecker.setRowIds("removePortlet");
	}
%>

<h3><liferay-ui:message key="portlets" /></h3>

<c:if test="<%= models.size() > 0 && isCustomizable %>">
	<% rowChecker = null; %>
	<div class="portlet-msg-info">
		<liferay-ui:message key="layout-is-customizable.-portlets-cant-be-manipulated" />
	</div>
</c:if>
<c:if test="<%= models.size() > 0 && selLayout.isLayoutPrototypeLinkActive() %>">
	<% rowChecker = null; %>
	<div class="portlet-msg-info">
		<liferay-ui:message key="layout-inherits-from-a-prototype.-portlets-cant-be-manipulated" />
	</div>
</c:if>

<c:if test="<%= (models.size() > 0) && (rowChecker != null) %>">
	<div class="portlet-msg-alert">
		<liferay-ui:message key="warning-selected-portlets-will-be-removed" />
	</div>
</c:if>


<liferay-ui:search-container
	deltaConfigurable="<%= false %>"
	emptyResultsMessage="there-are-no-portlets-on-the-page"
	rowChecker="<%= rowChecker %>"
	>

	<liferay-ui:search-container-results results="<%= models %>" />

	<liferay-ui:search-container-row
		className="java.util.Map"
		escapedModel="<%= true %>"
		keyProperty="portlet-id"
		modelVar="removePortletModel"
		>
		<liferay-ui:search-container-column-text name="portlet-id" />
		<liferay-ui:search-container-column-text name="title" />
		<liferay-ui:search-container-column-text name="placement-type" />
		<liferay-ui:search-container-column-text name="status" />
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator type="more" />
</liferay-ui:search-container>