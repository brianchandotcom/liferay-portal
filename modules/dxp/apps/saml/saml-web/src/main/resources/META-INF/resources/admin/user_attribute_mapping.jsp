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

					<div class="form-group-autofit lfr-form-row user-attribute-mapping-row" data-prefix="<%= prefix %>">
						<div class="form-group-item">
							<aui:select fieldParam="<%= userFieldExpressionId %>" id="<%= userFieldExpressionId %>" inlineField="<%= true %>" label="user-field-expression" name="<%= userFieldExpressionId %>" showEmptyOption="<%= true %>">

								<%
								for (String userFieldExpression : userFieldExpressionHandler.getValidFieldExpressions()) {
								%>

									<aui:option data-authsupported="<%= userFieldExpressionHandler.isSupportedForUserMatching(userFieldExpression) %>" label="<%= userFieldExpression %>" selected="<%= Objects.equals(userFieldExpression, userAttributeMappingEntry.getKey()) %>" value="<%= userFieldExpression %>"></aui:option>

								<%
								}
								%>

							</aui:select>
						</div>

						<div class="form-group-item">
							<aui:input cssClass="saml-attribute-field" fieldParam="<%= samlAttributeId %>" id="<%= samlAttributeId %>" inlineField="<%= true %>" label="saml-attribute" name="<%= samlAttributeId %>" type="text" value="<%= userAttributeMappingEntry.getValue() %>" />
						</div>

						<div class="form-group-item form-group-item-label-spacer form-group-item-shrink">
							<aui:input checked='<%= Objects.equals(userIdentifierExpression, "attribute:" + (!Validator.isBlank(prefix) ? prefix + ":" : "") + userAttributeMappingEntry.getKey()) %>' cssClass="primary-ctrl" disabled="<%= true %>" id='<%= prefix + ":userIdentifierExpression-" + prefixEntriesIndex %>' inlineField="<%= true %>" label="use-to-match-users" name="attribute:userIdentifierExpressionIndex" type="radio" value="<%= prefixEntriesIndex %>" />
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
	<portlet:namespace />evaluateAttributeMappingRow = function (row, event) {
		var radioTarget = row.querySelector(
			'input[name="<portlet:namespace />attribute:userIdentifierExpressionIndex"]'
		);
		var selectTarget = row.querySelector('select');
		var attributeMappingSelected = document.querySelector(
			'input[name="<portlet:namespace />userIdentifierExpression"][value="attribute"]'
		).checked;

		if (event == null || event.target == radioTarget) {
			if (radioTarget.checked) {
				<portlet:namespace />handleAttributeMappingMatchingSelection(row);
			}
		}

		if (
			attributeMappingSelected &&
			(event == null || event.target == selectTarget)
		) {
			if (
				selectTarget.options[selectTarget.selectedIndex].dataset
					.authsupported == 'true'
			) {
				radioTarget.disabled = false;
				radioTarget.closest('label').classList.toggle('disabled', false);
			}
			else {
				radioTarget.disabled = true;
				radioTarget.closest('label').classList.toggle('disabled', true);
			}
		}
	};

	<portlet:namespace />handleAttributeMappingMatchingDeselection = function () {
		document.querySelector(
			'input[name="<portlet:namespace />attribute:userIdentifierExpressionPrefix"]'
		).value = '';
		document
			.querySelectorAll(
				'input[name="<portlet:namespace />attribute:userIdentifierExpressionIndex"]'
			)
			.forEach((radioControl) => {
				radioControl.checked = false;
				radioControl.disabled = true;
				radioControl.closest('label').classList.toggle('disabled', true);
			});
	};

	<portlet:namespace />handleAttributeMappingMatchingSelection = function (row) {
		document.querySelector(
			'input[name="<portlet:namespace />attribute:userIdentifierExpressionPrefix"]'
		).value = row.dataset.prefix;
	};

	var userAttributeMappings = document.getElementById(
		'<portlet:namespace />userAttributeMappings'
	);

	userAttributeMappings.addEventListener('change', (event) => {
		<portlet:namespace />evaluateAttributeMappingRow(
			event.target.closest('.user-attribute-mapping-row'),
			event
		);
	});
	userAttributeMappings.addEventListener('click', (event) => {
		if (event.target.closest('.user-attribute-mapping-row button')) {
			document
				.querySelectorAll('.user-attribute-mapping-row')
				.forEach((row) => {
					<portlet:namespace />evaluateAttributeMappingRow(row);
				});
		}
	});

	if (userAttributeMappings.querySelector('input[name="<portlet:namespace />attribute:userIdentifierExpressionIndex"]:checked')) {
		document.querySelector(
			'input[name="<portlet:namespace />userIdentifierExpression"][value="attribute"]'
		).checked = true;
	}

	document
		.querySelectorAll(
			'input[name="<portlet:namespace />userIdentifierExpression"]:not([value="attribute"])'
		)
		.forEach((radioControl) =>
			radioControl.addEventListener('change', (event) => {
				<portlet:namespace />handleAttributeMappingMatchingDeselection();
			})
		);
	document
		.querySelector(
			'input[name="<portlet:namespace />userIdentifierExpression"][value="attribute"]'
		)
		.addEventListener('change', (event) => {
			document
				.querySelectorAll('.user-attribute-mapping-row')
				.forEach((row) =>
					<portlet:namespace />evaluateAttributeMappingRow(row)
				);
		});

	document
		.querySelectorAll('.user-attribute-mapping-row')
		.forEach((row) => <portlet:namespace />evaluateAttributeMappingRow(row));
</script>