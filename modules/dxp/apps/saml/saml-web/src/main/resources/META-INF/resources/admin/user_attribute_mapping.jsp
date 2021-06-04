<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
AttributeMappingDisplayContext attributeMappingDisplayContext = (AttributeMappingDisplayContext)request.getAttribute(AttributeMappingDisplayContext.class.getName());

String userIdentifierExpression = attributeMappingDisplayContext.getUserIdentifierExpression();
%>

<aui:fieldset helpMessage="attribute-mapping-help" id='<%= liferayPortletResponse.getNamespace() + "userAttributeMappings" %>' label="attribute-mapping">
	<aui:input name="attribute:userIdentifierExpressionPrefix" type="hidden" value="" />

	<%
	for (Map.Entry<String, UserFieldExpressionHandler> entry : attributeMappingDisplayContext.getOrderedUserFieldExpressionHandlers()) {
		String prefix = entry.getKey();
		UserFieldExpressionHandler userFieldExpressionHandler = entry.getValue();

		List<Map.Entry<String, String>> prefixEntries = attributeMappingDisplayContext.getMapEntries(prefix);
		String userAttributeMappingsContentBox = HtmlUtil.getAUICompatibleId(prefix + ":userAttributeMappingsContentBox");
		int[] userAttributeMappingsIndexes = attributeMappingDisplayContext.getIndexes(prefix);
	%>

		<aui:field-wrapper label="<%= userFieldExpressionHandler.getSectionLabel(locale) %>">
			<div id="<portlet:namespace /><%= userAttributeMappingsContentBox %>">

				<%
				for (int i = 0; i < userAttributeMappingsIndexes.length; i++) {
					int prefixEntriesIndex = userAttributeMappingsIndexes[i];

					Map.Entry<String, String> userAttributeMappingEntry = prefixEntries.get(i);
					String userFieldExpressionId = "attribute:" + prefix + ":userAttributeMappingFieldExpression-" + prefixEntriesIndex;
					String samlAttributeId = "attribute:" + prefix + ":userAttributeMappingSamlAttribute-" + prefixEntriesIndex;
				%>

					<div class="form-group-autofit lfr-form-row">
						<div class="form-group-item">
							<aui:select fieldParam="<%= userFieldExpressionId %>" id="<%= userFieldExpressionId %>" inlineField="<%= true %>" label="user-field-expression" name="<%= userFieldExpressionId %>" showEmptyOption="<%= true %>">

								<%
								for (String userFieldExpression : userFieldExpressionHandler.getValidFieldExpressions()) {
								%>

									<aui:option label="<%= userFieldExpression %>" selected="<%= Objects.equals(userFieldExpression, userAttributeMappingEntry.getKey()) %>" value="<%= userFieldExpression %>"></aui:option>

								<%
								}
								%>

							</aui:select>
						</div>

						<div class="form-group-item">
							<aui:input cssClass="saml-attribute-field" fieldParam="<%= samlAttributeId %>" id="<%= samlAttributeId %>" inlineField="<%= true %>" label="saml-attribute" name="<%= samlAttributeId %>" type="text" value="<%= userAttributeMappingEntry.getValue() %>" />
						</div>

						<div class="form-group-item form-group-item-label-spacer form-group-item-shrink">
							<aui:input checked='<%= Objects.equals(userIdentifierExpression, "attribute:" + (!Validator.isBlank(prefix) ? prefix + ":" : "") + userAttributeMappingEntry.getKey()) %>' cssClass="primary-ctrl" data-prefix="<%= prefix %>" id='<%= prefix + ":userIdentifierExpression-" + prefixEntriesIndex %>' inlineField="<%= true %>" label="use-to-match-users" name="attribute:userIdentifierExpressionIndex" type="radio" value="<%= prefixEntriesIndex %>" />
						</div>
					</div>

				<%
				}
				%>

				<aui:input name='<%= "attribute:" + prefix + ":userAttributeMappingsIndexes" %>' type="hidden" value="<%= StringUtil.merge(userAttributeMappingsIndexes) %>" />
			</div>

			<aui:script use="liferay-auto-fields">
				new Liferay.AutoFields({
					contentBox: '#<portlet:namespace /><%= userAttributeMappingsContentBox %>',
					fieldIndexes:
						'<portlet:namespace />attribute:<%= prefix %>:userAttributeMappingsIndexes',
					namespace: '<portlet:namespace />',
				}).render();
			</aui:script>
		</aui:field-wrapper>

	<%
	}
	%>

	<aui:input name="attribute:userAttributeMappingsPrefixes" type="hidden" value="<%= StringUtil.merge(attributeMappingDisplayContext.getPrefixes()) %>" />
</aui:fieldset>

<script>
	<portlet:namespace />handleAttributeMappingMatchingDeselection = function () {
		document.querySelector(
			'input[name="<portlet:namespace />attribute:userIdentifierExpressionPrefix"]'
		).value = '';
		document
			.querySelectorAll(
				'input[name="<portlet:namespace />attribute:userIdentifierExpressionIndex"]'
			)
			.forEach((radioControl) => (radioControl.checked = false));
	};

	<portlet:namespace />handleAttributeMappingMatchingSelection = function (
		radioControl
	) {
		if (!radioControl) {
			return;
		}

		document.querySelector(
			'input[name="<portlet:namespace />attribute:userIdentifierExpressionPrefix"]'
		).value = radioControl.dataset.prefix;

		document
			.querySelectorAll(
				'input[name="<portlet:namespace />userIdentifierExpression"]'
			)
			.forEach(
				(userIdentifierExpressionRadioControl) =>
					(userIdentifierExpressionRadioControl.checked = false)
			);

		document.querySelector(
			'input[name="<portlet:namespace />userIdentifierExpression"][value="attribute"]'
		).checked = true;
	};

	<portlet:namespace />handleAttributeMappingMatchingSelection(
		document.querySelector(
			'input[name="<portlet:namespace />attribute:userIdentifierExpressionIndex"][checked]'
		)
	);

	document
		.getElementById('<portlet:namespace />userAttributeMappings')
		.addEventListener('change', (event) => {
			if (
				event.target.name ==
				'<portlet:namespace />attribute:userIdentifierExpressionIndex'
			) {
				<portlet:namespace />handleAttributeMappingMatchingSelection(
					event.target
				);
			}
		});

	document
		.querySelectorAll(
			'input[name="<portlet:namespace />userIdentifierExpression"]:not([value="attribute"])'
		)
		.forEach((radioControl) =>
			radioControl.addEventListener('change', (event) => {
				<portlet:namespace />handleAttributeMappingMatchingDeselection();
			})
		);
</script>