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
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();
CPInstance cpInstance = cpDefinitionVirtualSettingDisplayContext.getCPInstance();
long cpInstanceId = cpDefinitionVirtualSettingDisplayContext.getCPInstanceId();

boolean override = BeanParamUtil.getBoolean(cpDefinitionVirtualSetting, request, "override", false);
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_virtual_setting" var="editProductDefinitionVirtualSettingActionURL" />

<aui:form action="<%= editProductDefinitionVirtualSettingActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="className" type="hidden" value="<%= CPInstance.class.getName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= cpInstanceId %>" />
	<aui:input name="cpDefinitionVirtualSettingId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId() %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getFileEntryId() %>" />
	<aui:input name="sampleFileEntryId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getSampleFileEntryId() %>" />
	<aui:input name="termsOfUseJournalArticleResourcePrimKey" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePrimKey() %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input checked="<%= override %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="override" type="toggle-switch" value="<%= override %>" />
		</aui:fieldset>

		<div id="<portlet:namespace />cpDefinitionVirtualSettingContainer">
			<aui:fieldset collapsible="<%= true %>" label="details">

				<%
				FileEntry fileEntry = cpDefinitionVirtualSettingDisplayContext.getFileEntry();

				long fileEntryId = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "fileEntryId");

				String textCssClass = "text-default ";

				boolean useFileEntry = false;

				if (fileEntryId > 0) {
					textCssClass += "hide";

					useFileEntry = true;
				}
				%>

				<%@ include file="/details.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="base-information">

				<%
				boolean durationDisabled = true;

				if (cpInstance.getCPSubscriptionInfo() == null) {
					durationDisabled = false;
				}

				long durationDays = 0;

				if ((cpDefinitionVirtualSetting != null) && (cpDefinitionVirtualSetting.getDuration() > 0)) {
					durationDays = cpDefinitionVirtualSetting.getDuration() / Time.DAY;
				}
				%>

				<%@ include file="/base_information.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="sample">

				<%
				FileEntry sampleFileEntry = cpDefinitionVirtualSettingDisplayContext.getSampleFileEntry();

				long sampleFileEntryId = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "sampleFileEntryId");

				String textCssClass = "text-default ";

				boolean useSampleFileEntry = false;

				if (sampleFileEntryId > 0) {
					textCssClass += "hide";

					useSampleFileEntry = true;
				}
				%>

				<%@ include file="/sample.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="terms-of-use">

				<%
				JournalArticle journalArticle = cpDefinitionVirtualSettingDisplayContext.getJournalArticle();

				long termsOfUseJournalArticleResourcePrimKey = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "termsOfUseJournalArticleResourcePrimKey");

				boolean useTermsOfUseJournal = false;

				if (termsOfUseJournalArticleResourcePrimKey > 0) {
					useTermsOfUseJournal = true;
				}
				%>

				<%@ include file="/terms_of_use.jspf" %>
			</aui:fieldset>
		</div>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />override',
		'<portlet:namespace />cpDefinitionVirtualSettingContainer'
	);
</aui:script>