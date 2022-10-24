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
 */

import {isEdge} from 'react-flow-renderer';

import {defaultLanguageId} from '../constants';
import {removeNewLine, replaceTabSpaces} from '../util/utils';
import {DEFAULT_LANGUAGE} from './constants';
import {
	parseActions,
	parseAssignments,
	parseNotifications,
	parseTimers,
} from './utils';
import XMLDefinition from './xmlDefinition';

export default function DeserializeUtil(content) {
	const instance = this;

	instance.definition = new XMLDefinition({
		value: content,
	});
}

DeserializeUtil.prototype = {
	getElements() {
		const instance = this;

		const elements = [];

		const transitionsIDs = [];

		const nodesIDs = [];

		instance.definition.forEachField((_, fieldData) => {
			fieldData.results.forEach((node) => {
				nodesIDs.push(node.name);
			});
		});

		instance.definition.forEachField((tagName, fieldData) => {
			fieldData.results.forEach((node) => {
				if (node.actions?.length) {
					node.notifications = node.actions.filter((item) => {
						if (item.template) {
							return item;
						}
					});

					node.actions = node.actions.filter((item) => {
						if (item.script) {
							return item;
						}
					});
				}

				const position = {};
				let type = tagName;

				if (node.initial) {
					type = 'start';
				}

				const metadata = node.metadata && JSON.parse(node.metadata);

				if (
					metadata?.terminal ||
					(type === 'state' && !node.transitions && !metadata)
				) {
					type = 'end';
				}

				position.x = metadata?.xy[0] || Math.floor(Math.random() * 800);
				position.y = metadata?.xy[1] || Math.floor(Math.random() * 500);

				let label = {};

				if (Array.isArray(node.labels)) {
					node.labels?.map((itemLabel) => {
						Object.entries(itemLabel).map(([key, value]) => {
							label[key] = replaceTabSpaces(removeNewLine(value));
						});
					});
				}
				else {
					label = {[defaultLanguageId]: node.name};
				}

				const data = {
					description: node.description,
					label,
					script: node.script,
				};

				if (type === 'condition') {
					data.scriptLanguage =
						node.scriptLanguage || DEFAULT_LANGUAGE;
				}

				if (type === 'task') {
					node.assignments?.forEach((assignment) => {
						const roleTypes = assignment['role-type'];

						roleTypes?.forEach((type, index) => {
							if (type === 'depot') {
								roleTypes[index] = 'asset library';
							}
						});
					});

					if (node.assignments) {
						data.assignments = parseAssignments(node);
					}
					if (node.taskTimers) {
						data.taskTimers = parseTimers(node);
					}

					data.scriptLanguage =
						node.scriptLanguage || DEFAULT_LANGUAGE;
				}

				data.actions = node.actions?.length && parseActions(node);

				data.notifications =
					node.notifications?.length && parseNotifications(node);

				node.notifications?.forEach((notification) => {
					const roleTypes = notification['role-type'];

					roleTypes?.forEach((type, index) => {
						if (type === 'depot') {
							roleTypes[index] = 'asset library';
						}
					});
				});

				let nodeId;

				if (node.id) {
					nodeId = node.id;
				}
				else if (node.name) {
					nodeId = node.name;
				}
				else {
					return;
				}

				elements.push({
					data,
					id: nodeId,
					position,
					type,
				});

				if (node.transitions) {
					node.transitions.forEach((transition) => {
						let label = {};

						if (Array.isArray(transition.labels)) {
							transition.labels?.map((itemLabel) => {
								Object.entries(itemLabel).map(
									([key, value]) => {
										label[key] = replaceTabSpaces(
											removeNewLine(value)
										);
									}
								);
							});
						}
						else {
							label = {[defaultLanguageId]: transition.name};
						}

						let transitionId;

						if (transition.id) {
							transitionId = transition.id;
						}
						else if (transition.name) {
							transitionId = transition.name;
						}
						else {
							return;
						}

						if (
							transitionsIDs.includes(transitionId) ||
							nodesIDs.includes(transitionId)
						) {
							transitionId = `${nodeId}_${transitionId}_${transition.target}`;
						}
						else {
							transitionsIDs.push(transitionId);
						}

						const hasDefaultEdge = elements.find(
							(element) =>
								isEdge(element) &&
								element.source === nodeId &&
								element.data.defaultEdge
						);

						elements.push({
							arrowHeadType: 'arrowclosed',
							data: {
								defaultEdge:
									transition?.default === 'true' ||
									!hasDefaultEdge
										? true
										: false,
								label,
							},
							id: transitionId,
							source: nodeId,
							target: transition.target,
							type: 'transition',
						});
					});
				}
			});
		});

		return elements;
	},

	getMetadata() {
		const instance = this;

		return instance.definition.getDefinitionMetadata();
	},

	updateXMLDefinition(content) {
		const instance = this;

		instance.definition = new XMLDefinition({
			value: content,
		});
	},
};
