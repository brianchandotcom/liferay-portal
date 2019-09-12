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
String saveCallback = ParamUtil.getString(request, "saveCallback");

if (Validator.isNotNull(saveCallback)) {
	saveCallback = "Liferay.Util.getOpener()['" + HtmlUtil.escapeJS(saveCallback) + "'](Liferay.Util.getWindow());";
}

String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");

List<MDRRuleGroupInstance> ruleGroupInstances = MDRRuleGroupInstanceServiceUtil.getRuleGroupInstances(className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, RuleGroupInstancePriorityComparator.INSTANCE_ASCENDING);
%>

<portlet:actionURL name="/mobile_device_rules/edit_rule_group_instance" var="editRuleGroupInstancesURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule_group_instance" />
</portlet:actionURL>

<aui:form action="<%= editRuleGroupInstancesURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveRuleGroupInstancesPriorities(event)" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="ruleGroupsInstancesJSON" type="hidden" />

	<div class="alert alert-info">
		<liferay-ui:message key="to-manage-priorities,-drag-the-rule-to-the-desired-position" />
	</div>

	<div class="rule-group-instance-container" id="<portlet:namespace />ruleGroupInstancesPriorities">

		<%
		for (int i = 0; i < ruleGroupInstances.size(); i++) {
			MDRRuleGroupInstance ruleGroupInstance = ruleGroupInstances.get(i);

			MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();
		%>

			<div class="list-group-item" data-rule-group-instance-id="<%= ruleGroupInstance.getRuleGroupInstanceId() %>">
				<aui:icon cssClass="rule-group-handle" image="ellipsis-v" markupView="lexicon" />

				<strong><%= HtmlUtil.escape(ruleGroup.getName(locale)) %></strong>

				<span class="pull-right">
					<liferay-ui:message key="priority" />: <strong class="rule-group-instance-priority-value"><%= ruleGroupInstance.getPriority() %></strong>
				</span>
			</div>

		<%
		}
		%>

	</div>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= saveCallback %>" value="close" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveRuleGroupInstancesPriorities(event) {
		event.preventDefault();

		var ruleGroupsInstancesJSONElement = document.getElementById(
			'<portlet:namespace />ruleGroupsInstancesJSON'
		);

		if (ruleGroupsInstancesJSONElement) {
			var ruleGroupInstanceNodes = document.querySelectorAll(
				'#<portlet:namespace />ruleGroupInstancesPriorities [data-rule-group-instance-id]'
			);

			var ruleGroupInstanceNodesArray = Array.prototype.slice.call(
				ruleGroupInstanceNodes
			);

			var ruleGroupInstances = ruleGroupInstanceNodesArray.map(function(
				item,
				index
			) {
				return {
					priority: index,
					ruleGroupInstanceId: item.dataset.ruleGroupInstanceId
				};
			});

			ruleGroupsInstancesJSONElement.value = JSON.stringify(
				ruleGroupInstances
			);
		}

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<aui:script use="aui-base,dd-constrain,sortable">
	var container = A.one('#<portlet:namespace />ruleGroupInstancesPriorities');

	if (container) {
		var sortable = new A.Sortable({
			container: container,
			handles: ['.rule-group-handle'],
			nodes: '.list-group-item',
			on: {
				moved: function(event) {
					var instance = this;

					var delegate = instance.delegate;

					var nodes = container.all('.list-group-item');

					var dragNode = event.drag.get('dragNode');

					var priorityNode = dragNode.one(
						'.rule-group-instance-priority-value'
					);

					if (priorityNode) {
						var currentNode = delegate.get('currentNode');

						priorityNode.html(nodes.indexOf(currentNode));
					}
				}
			},
			opacity: '.4'
		});

		var sortableDD = sortable.delegate.dd;

		sortableDD.after({
			'drag:end': function(event) {
				var drag = event.target;
				var dragNode = drag.get('dragNode');

				var nodes = container.all('.list-group-item');

				nodes.each(function(item, index, collection) {
					var priorityNode = item.one(
						'.rule-group-instance-priority-value'
					);

					priorityNode.html(index);
				});

				dragNode.removeClass('rule-group-instance-dragging');
			},
			'drag:start': function(event) {
				var drag = event.target;
				var dragNode = drag.get('dragNode');

				dragNode.addClass('rule-group-instance-dragging');
			}
		});

		sortableDD.plug(A.Plugin.DDConstrained, {
			constrain: container,
			stickY: true
		});
	}
</aui:script>