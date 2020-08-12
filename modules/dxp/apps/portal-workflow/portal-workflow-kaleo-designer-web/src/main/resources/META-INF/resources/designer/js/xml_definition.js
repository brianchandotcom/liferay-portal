/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

AUI.add(
	'liferay-kaleo-designer-xml-definition',
	(A) => {
		var Lang = A.Lang;

		var isNumber = Lang.isNumber;
		var isString = Lang.isString;

		var COL_TYPES_FIELD = [
			'condition',
			'fork',
			'join',
			'join-xor',
			'state',
			'task',
		];

		var STR_BLANK = '';
		var XMLUtil = Liferay.XMLUtil;

		var XMLDefinition = A.Component.create({
			ATTRS: {
				description: {
					validator: isString,
					value: STR_BLANK,
				},

				name: {
					validator: isString,
				},

				value: {},

				version: {
					validator: isNumber,
					value: 1,
				},

				xmlNamespace: {
					value: {
						xmlns: 'urn:liferay.com:liferay-workflow_7.3.0',
						'xmlns:xsi':
							'http://www.w3.org/2001/XMLSchema-instance',
						'xsi:schemaLocation':
							'urn:liferay.com:liferay-workflow_7.3.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_3_0.xsd',
					},
				},
			},

			EXTENDS: A.Base,

			NAME: 'kaleo-designer-xml-definition',

			prototype: {
				_getSchemaActions(key, tagName) {
					return {
						key: key || 'actions',
						schema: {
							resultFields: [
								{
									key: 'description',
									locator: 'description',
								},
								{
									key: 'executionType',
									locator: 'execution-type',
								},
								{
									key: 'name',
									locator: 'name',
								},
								{
									key: 'priority',
									locator: 'priority',
								},
								{
									key: 'script',
									locator: 'script',
								},
								{
									key: 'scriptLanguage',
									locator: 'script-language',
								},
							],
							resultListLocator: tagName || 'action',
						},
					};
				},

				_getSchemaAssignments(key, tagName) {
					return {
						key: key || 'assignments',
						schema: {
							resultFields: [
								{
									key: 'address',
									locator: 'address',
								},
								{
									key: 'resourceActions',
									schema: {
										resultFields: [
											{
												key: 'resourceAction',
												locator: 'resource-action',
											},
										],
										resultListLocator: 'resource-actions',
									},
								},
								{
									key: 'roleId',
									schema: {
										resultFields: [
											{
												key: 'roleId',
												locator: 'role-id',
											},
											{
												key: 'roleNameAC',
												locator: 'role-name-ac',
											},
										],
										resultListLocator: 'role',
									},
								},
								{
									key: 'roleType',
									schema: {
										resultFields: [
											{
												key: 'autoCreate',
												locator: 'auto-create',
											},
											{
												key: 'roleName',
												locator: 'name',
											},
											{
												key: 'roleType',
												locator: 'role-type',
											},
										],
										resultListLocator: 'role',
									},
								},
								{
									key: 'scriptedAssignment',
									schema: {
										resultFields: [
											{
												key: 'script',
												locator: 'script',
											},
											{
												key: 'scriptLanguage',
												locator: 'script-language',
											},
										],
										resultListLocator:
											'scripted-assignment',
									},
								},
								{
									key: 'scriptedRecipient',
									schema: {
										resultFields: [
											{
												key: 'script',
												locator: 'script',
											},
											{
												key: 'scriptLanguage',
												locator: 'script-language',
											},
										],
										resultListLocator: 'scripted-recipient',
									},
								},
								{
									key: 'taskAssignees',
									locator: 'assignees',
								},
								{
									key: 'user',
									schema: {
										resultFields: [
											{
												key: 'emailAddress',
												locator: 'email-address',
											},
											{
												key: 'fullName',
												locator: 'full-name',
											},
											{
												key: 'screenName',
												locator: 'screen-name',
											},
											{
												key: 'userId',
												locator: 'user-id',
											},
										],
										resultListLocator: 'user',
									},
								},
								{
									key: 'receptionType',
									locator: '@receptionType',
								},
							],
							resultListLocator: tagName || 'assignments',
						},
					};
				},

				_getSchemaNotifications(
					key,
					tagName,
					assignmentKey,
					assignmentTagName
				) {
					var instance = this;

					assignmentKey = assignmentKey || 'recipients';
					assignmentTagName = assignmentTagName || 'recipients';

					return {
						key: key || 'notifications',
						schema: {
							resultFields: [
								{
									key: 'description',
									locator: 'description',
								},
								{
									key: 'executionType',
									locator: 'execution-type',
								},
								{
									key: 'name',
									locator: 'name',
								},
								{
									key: 'notificationTypes',
									schema: {
										resultFields: [
											{
												key: 'notificationType',
												locator: '.',
											},
										],
										resultListLocator: 'notification-type',
									},
								},
								{
									key: 'template',
									locator: 'template',
								},
								{
									key: 'templateLanguage',
									locator: 'template-language',
								},
								instance._getSchemaAssignments(
									assignmentKey,
									assignmentTagName
								),
							],
							resultListLocator: tagName || 'notification',
						},
					};
				},

				_getSchemaTaskTimers(key, tagNode) {
					var instance = this;

					return {
						key: key || 'taskTimers',
						schema: {
							resultFields: [
								{
									key: 'blocking',
									locator: 'blocking',
								},
								{
									key: 'delay',
									schema: {
										resultFields: [
											{
												key: 'duration',
												locator: 'duration',
											},
											{
												key: 'scale',
												locator: 'scale',
											},
										],
										resultListLocator: 'delay',
									},
								},
								{
									key: 'description',
									locator: 'description',
								},
								{
									key: 'name',
									locator: 'name',
								},
								{
									key: 'recurrence',
									schema: {
										resultFields: [
											{
												key: 'duration',
												locator: 'duration',
											},
											{
												key: 'scale',
												locator: 'scale',
											},
										],
										resultListLocator: 'recurrence',
									},
								},
								instance._getSchemaActions(
									'timerActions',
									'timer-action'
								),
								instance._getSchemaAssignments(
									'reassignments',
									'reassignments'
								),
								instance._getSchemaNotifications(
									'timerNotifications',
									'timer-notification'
								),
							],
							resultListLocator: tagNode || 'task-timer',
						},
					};
				},

				_getSchemaTransitions(key, tagName) {
					return {
						key: key || 'transitions',
						schema: {
							resultFields: [
								{
									key: 'default',
									locator: 'default',
								},
								{
									key: 'name',
									locator: 'name',
								},
								{
									key: 'target',
									locator: 'target',
								},
							],
							resultListLocator: tagName || 'transition',
						},
					};
				},

				_sanitizeDefinitionXML(val) {
					var instance = this;

					val = decodeURIComponent(val);

					val = val.replace(/\s*(<!\[CDATA\[)/g, '$1');
					val = val.replace(/(\]\]>)\s*/g, '$1');

					instance._updateXMLNamespace(val);

					return val.replace(
						/(<workflow-definition)[^>]*(>)/,
						'$1$2'
					);
				},

				_updateXMLNamespace(definition) {
					var instance = this;

					var workflowDefinition = /(<workflow-definition)[^>]*(>)/.exec(
						definition
					);

					if (workflowDefinition) {
						var xmlns = /xmlns="([^"]*)"/.exec(workflowDefinition);
						var xmlnsXsi = /xmlns:xsi="([^"]*)"/.exec(
							workflowDefinition
						);
						var xsiSchemaLocation = /xsi:schemaLocation="([^"]*)"/.exec(
							workflowDefinition
						);

						if (xmlns && xmlnsXsi && xsiSchemaLocation) {
							instance.set('xmlNamespace', {
								xmlns: xmlns[1],
								'xmlns:xsi': xmlnsXsi[1],
								'xsi:schemaLocation': xsiSchemaLocation[1],
							});
						}
					}
				},

				forEachField(fn) {
					var instance = this;

					COL_TYPES_FIELD.forEach((item) => {
						var fieldData = instance.translate(item);

						if (fn && !fieldData.error) {
							fn.call(instance, item, fieldData);
						}
					});
				},

				getDefinitionMetadata() {
					var instance = this;

					var output = A.DataSchema.XML.apply(
						{
							metaFields: {
								description:
									'//workflow-definition/description',
								name: '//workflow-definition/name',
								version: '//workflow-definition/version',
							},
						},
						instance.definitionDoc
					);

					return output.meta;
				},

				initializer(config) {
					var instance = this;

					var val = instance._sanitizeDefinitionXML(config.value);

					if (!val || XMLUtil.validateDefinition(val)) {
						instance.definitionDoc = A.DataType.XML.parse(val);
					}

					var metadata = instance.getDefinitionMetadata();

					if (metadata) {
						instance.setAttrs(metadata);
					}
				},

				translate(tagName) {
					var instance = this;

					var schema = {
						resultFields: [
							'description',
							'initial',
							'metadata',
							'name',
							'script',
							{
								key: 'scriptLanguage',
								locator: 'script-language',
							},
							instance._getSchemaActions(),
							instance._getSchemaAssignments(),
							instance._getSchemaNotifications(),
							instance._getSchemaTaskTimers(),
							instance._getSchemaTransitions(),
						],
						resultListLocator: tagName,
					};

					return A.DataSchema.XML.apply(
						schema,
						instance.definitionDoc
					);
				},
			},
		});

		Liferay.KaleoDesignerXMLDefinition = XMLDefinition;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-component',
			'dataschema-xml',
			'datatype-xml',
			'liferay-kaleo-designer-xml-util',
		],
	}
);
