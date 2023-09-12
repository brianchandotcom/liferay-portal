<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewRelatedEntriesDisplayContext viewRelatedEntriesDisplayContext = (ViewRelatedEntriesDisplayContext)request.getAttribute(CTWebKeys.VIEW_RELATED_ENTRIES_DISPLAY_CONTEXT);

portletDisplay.setURLBack(viewRelatedEntriesDisplayContext.getRedirectURL());

portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(LanguageUtil.get(request, "move-changes"));
%>

<clay:container-fluid
	cssClass="publications-related-entries-container"
>
	<div class="sheet">
		<clay:sheet-section>
			<h2 class="sheet-title"><liferay-ui:message key="moved-changes" /></h2>

			<div class="sheet-text">
				<liferay-ui:message key="the-following-changes-will-be-moved" />
			</div>

			<div>
				<react:component
					data="<%= viewRelatedEntriesDisplayContext.getReactData() %>"
					module="publications/js/views/ChangeTrackingRelatedEntriesView"
				/>
			</div>
		</clay:sheet-section>

		<aui:form action="<%= viewRelatedEntriesDisplayContext.getSubmitMoveURL() %>" method="post" name="fm">
			<aui:input name="fromCTCollectionId" type="hidden" value="<%= viewRelatedEntriesDisplayContext.getCTCollectionId() %>" />

			<clay:select
				containerCssClass="mt-3"
				id='<%= liferayPortletResponse.getNamespace() + "toPublication" %>'
				label='<%= LanguageUtil.get(request, "publication") %>'
				name="toCTCollectionId"
				options="<%= viewRelatedEntriesDisplayContext.getSelectOptions() %>"
			/>

			<clay:sheet-footer>
				<aui:button disabled="<%= true %>" id="submitMove" primary="true" type="submit" value="move-changes" />

				<aui:button href="<%= viewRelatedEntriesDisplayContext.getRedirectURL() %>" type="cancel" />
			</clay:sheet-footer>
		</aui:form>
	</div>
</clay:container-fluid>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />toPublication').on('change', (event) => {
		var selection = A.one('#<portlet:namespace />toPublication').get('value');

		var button = A.one('#<portlet:namespace />submitMove');

		if (selection) {
			button.removeClass('disabled');
			button.attr('disabled', false);
		}
		else {
			button.addClass('disabled');
			button.attr('disabled', true);
		}
	});
</aui:script>