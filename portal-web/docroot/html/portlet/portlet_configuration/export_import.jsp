<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String selPortletPrimaryKey = PortletPermissionUtil.getPrimaryKey(layout.getPlid(), selPortlet.getPortletId());

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/export_import");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());

boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());

boolean controlPanel = false;

if (layout.isTypeControlPanel()) {
	Group scopeGroup = themeDisplay.getScopeGroup();

	if (scopeGroup.isLayout()) {
		layout = LayoutLocalServiceUtil.getLayout(scopeGroup.getClassPK());
	}
	else if (!scopeGroup.isCompany()) {
		long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(scopeGroupId);

		if (defaultPlid > 0) {
			layout = LayoutLocalServiceUtil.getLayout(defaultPlid);
		}
	}

	supportsSetup = false;

	controlPanel = true;
}
%>

<c:choose>
	<c:when test="<%= supportsLAR || supportsSetup %>">

		<%
		String tabs2Names = "export,import";

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup()) {
			tabs2Names += ",staging";
		}
		else if (scopeGroup.isLayout()) {
			Group parentScopeGroup = GroupServiceUtil.getGroup(scopeGroup.getParentGroupId());

			if (parentScopeGroup.isStagingGroup()) {
				tabs2Names += ",staging";
			}
		}
		%>

		<liferay-ui:tabs
			backURL="<%= redirect %>"
			names="<%= tabs2Names %>"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />

		<liferay-ui:error exception="<%= LARTypeException.class %>">

			<%
			LARTypeException lpe = (LARTypeException)errorException;
			%>

			<liferay-ui:message arguments="<%= lpe.getMessage() %>" key="please-import-a-lar-file-of-the-correct-type-x-is-not-valid" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

		<liferay-ui:error exception="<%= LocaleException.class %>">

			<%
			LocaleException le = (LocaleException)errorException;
			%>

			<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-available-languages-in-the-lar-file-x-do-not-match-the-portal's-available-languages-x" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="an-error-occurred-because-the-live-group-does-not-have-the-current-page" />
		<liferay-ui:error exception="<%= PortletIdException.class %>" message="please-import-a-lar-file-for-the-current-portlet" />

		<liferay-ui:error exception="<%= PortletDataException.class %>">

			<%
			PortletDataException pde = (PortletDataException)errorException;
			%>

			<c:if test="<%= pde.getType() == PortletDataException.FUTURE_END_DATE %>">
				<liferay-ui:message key="please-enter-a-valid-end-date-that-is-in-the-past" />
			</c:if>

			<c:if test="<%= pde.getType() == PortletDataException.FUTURE_START_DATE %>">
				<liferay-ui:message key="please-enter-a-valid-start-date-that-is-in-the-past" />
			</c:if>

			<c:if test="<%= pde.getType() == PortletDataException.START_DATE_AFTER_END_DATE %>">
				<liferay-ui:message key="please-enter-a-start-date-that-comes-before-the-end-date" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RecordSetDuplicateRecordSetKeyException.class %>">

			<%
			RecordSetDuplicateRecordSetKeyException rsdrske = (RecordSetDuplicateRecordSetKeyException)errorException;
			%>

			<liferay-ui:message arguments="<%= rsdrske.getRecordSetKey() %>" key="dynamic-data-list-record-set-with-record-set-key-x-already-exists" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>">

			<%
			StructureDuplicateStructureKeyException sdske = (StructureDuplicateStructureKeyException)errorException;
			%>

			<liferay-ui:message arguments="<%= sdske.getStructureKey() %>" key="dynamic-data-mapping-structure-with-structure-key-x-already-exists" />
		</liferay-ui:error>

		<portlet:actionURL var="exportImportPagesURL">
			<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
		</portlet:actionURL>

		<aui:form action="<%= exportImportPagesURL %>" cssClass="lfr-export-dialog" method="post" name="fm1">
			<aui:input name="tabs1" type="hidden" value="export_import" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="plid" type="hidden" value="<%= layout.getPlid() %>" />
			<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
			<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<%
			String errorMessageKey = StringPool.BLANK;

			if (tabs2.equals("staging")) {
				Group stagingGroup = themeDisplay.getScopeGroup();
				Group liveGroup = stagingGroup.getLiveGroup();

				Layout targetLayout = null;

				if (!controlPanel) {
					if (liveGroup == null) {
						errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
					}
					else {
						try {
							if (stagingGroup.isLayout()) {
								targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
							}
							else {
								targetLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());
							}
						}
						catch (NoSuchLayoutException nsle) {
							errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
						}

						if (targetLayout != null) {
							LayoutType layoutType = targetLayout.getLayoutType();

							if (!(layoutType instanceof LayoutTypePortlet) || !((LayoutTypePortlet)layoutType).hasPortletId(selPortlet.getPortletId())) {
								errorMessageKey = "this-portlet-has-not-been-added-to-the-live-page-publish-the-page-first";
							}
						}
					}
				}
				else if (stagingGroup.isLayout()) {
					if (liveGroup == null) {
						errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
					}
					else {
						try {
							targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
						}
						catch (NoSuchLayoutException nsle) {
							errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
						}
					}
				}
			}
			%>

			<c:choose>
				<c:when test="<%= Validator.isNull(errorMessageKey) %>">
					<aui:fieldset>
						<c:choose>
							<c:when test='<%= tabs2.equals("export") %>'>
								<%@ include file="/html/portlet/portlet_configuration/export_options.jspf" %>
							</c:when>
							<c:when test='<%= tabs2.equals("import") %>'>
								<%@ include file="/html/portlet/portlet_configuration/import_options.jspf" %>
							</c:when>
							<c:when test='<%= tabs2.equals("staging") %>'>
								<c:if test="<%= (themeDisplay.getURLPublishToLive() != null) || controlPanel %>">
									<%@ include file="/html/portlet/portlet_configuration/staging_options.jspf" %>
								</c:if>
							</c:when>
						</c:choose>

						<aui:button-row>
							<c:choose>
								<c:when test='<%= tabs2.equals("export") %>'>
									<aui:button cssClass="btn-primary" onClick='<%= renderResponse.getNamespace() + "exportData();" %>' value="export" />

									<aui:button href="<%= redirect %>" type="cancel" />
								</c:when>
								<c:when test='<%= tabs2.equals("import") %>'>
									<aui:button cssClass="btn-primary" onClick='<%= renderResponse.getNamespace() + "importData();" %>' value="import" />

									<aui:button href="<%= redirect %>" type="cancel" />
								</c:when>
								<c:when test='<%= tabs2.equals("staging") %>'>
									<c:if test="<%= (themeDisplay.getURLPublishToLive() != null) || controlPanel %>">
										<aui:button cssClass="btn-primary" onClick='<%= renderResponse.getNamespace() + "publishToLive();" %>' value="publish-to-live" />

										<aui:button onClick='<%= renderResponse.getNamespace() + "copyFromLive();" %>' value="copy-from-live" />
									</c:if>
								</c:when>
							</c:choose>
						</aui:button-row>
					</aui:fieldset>
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="<%= errorMessageKey %>" />
				</c:otherwise>
			</c:choose>
		</aui:form>
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(locale, "the-x-portlet-does-not-have-any-data-that-can-be-exported-or-does-not-include-support-for-it", HtmlUtil.escape(PortalUtil.getPortletTitle(selPortlet, application, locale))) %>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-update-the-existing-staging-portlet-information") %>')) {
			submitForm(document.<portlet:namespace />fm1, '<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/export_import" /><portlet:param name="<%= Constants.CMD %>" value="copy_from_live" /></portlet:actionURL>');
		}
	}

	function <portlet:namespace />exportData() {
		document.<portlet:namespace />fm1.encoding = "multipart/form-data";

		submitForm(document.<portlet:namespace />fm1, '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" /></portlet:actionURL>&etag=0&strip=0', false);
	}

	function <portlet:namespace />importData() {
		document.<portlet:namespace />fm1.encoding = "multipart/form-data";

		submitForm(document.<portlet:namespace />fm1, '<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/export_import" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" /></portlet:actionURL>');
	}

	function <portlet:namespace />publishToLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-to-live-and-update-the-existing-portlet-data") %>')) {
			submitForm(document.<portlet:namespace />fm1, '<portlet:actionURL><portlet:param name="struts_action" value="/portlet_configuration/export_import" /><portlet:param name="<%= Constants.CMD %>" value="publish_to_live" /></portlet:actionURL>');
		}
	}
</aui:script>