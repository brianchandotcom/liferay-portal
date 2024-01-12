<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
portletDisplay.setBeta(true);
portletDisplay.setShowBackIcon(true);

ViewRelatedEntriesDisplayContext viewRelatedEntriesDisplayContext = (ViewRelatedEntriesDisplayContext)request.getAttribute(CTWebKeys.VIEW_RELATED_ENTRIES_DISPLAY_CONTEXT);

portletDisplay.setURLBack(viewRelatedEntriesDisplayContext.getRedirectURL());

renderResponse.setTitle(LanguageUtil.get(request, "move-changes"));
%>

<clay:container-fluid
	cssClass="publications-related-entries-container"
>
	<div class="sheet">
		<clay:sheet-section>
			<h2 class="sheet-title"><liferay-ui:message key="moved-changes" /></h2>

			<c:if test="<%= SessionErrors.contains(renderRequest, CTCollectionStatusException.class) %>">
				<clay:alert
					displayType="danger"
					message="the-changes-could-not-be-moved"
				/>
			</c:if>

			<c:if test="<%= SessionErrors.contains(renderRequest, CTPublishConflictException.class) %>">
				<clay:alert
					displayType="danger"
					message="one-or-more-changes-conflict-with-existing-changes-in-the-destination-publication"
				/>
			</c:if>

			<div>
				<react:component
					data="<%= viewRelatedEntriesDisplayContext.getReactData() %>"
					module="{ChangeTrackingRelatedEntriesView} from change-tracking-web"
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
	const toPublicationSelector = document.getElementById(
		'<portlet:namespace />toPublication'
	);

	toPublicationSelector.addEventListener('change', (event) => {
		const selection = toPublicationSelector.value;

		const button = document.getElementById('<portlet:namespace />submitMove');

		if (selection) {
			button.classList.remove('disabled');
			button.disabled = false;
		}
		else {
			button.classList.add('disabled');
			button.disabled = true;
		}
	});
</aui:script>