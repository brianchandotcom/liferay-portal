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

import {defaultLanguageId} from '../constants';
import {removeNewLine, replaceTabSpaces} from '../util/utils';
import {DEFAULT_LANGUAGE} from './constants';
import XMLDefinition from './xmlDefinition';

export default function DeserializeUtil(content) {
	const instance = this;

	instance.definition = new XMLDefinition({
		value: content,
	});
}

DeserializeUtil.prototype = {
	getConnectors() {
		const instance = this;

		const connectors = [];

		instance.definition.forEachField((_, fieldData) => {
			fieldData.results.forEach((item1) => {
				item1.transitions.forEach((item2) => {
					connectors.push({
						connector: {
							default: item2.default,
							name: item2.name,
						},
						source: item1.name,
						target: item2.target,
					});
				});
			});
		});

		return connectors;
	},

	getNodes() {
		const instance = this;

		const nodes = [];

		instance.definition.forEachField((tagName, fieldData) => {
			fieldData.results.forEach((item) => {
				const position = {};
				let type = tagName;

				if (item.initial) {
					type = 'start';
				}

				const metadata = JSON.parse(item.metadata);

				if (metadata.terminal) {
					type = 'end';
				}

				position.x = metadata.xy[0];
				position.y = metadata.xy[1];

				let label = {};

				if (Array.isArray(item.labels)) {
					item.labels?.map((itemLabel) => {
						Object.entries(itemLabel).map(([key, value]) => {
							label[key] = replaceTabSpaces(removeNewLine(value));
						});
					});
				}
				else {
					label = {[defaultLanguageId]: item.name};
				}

				const data = {
					description: item.description,
					label,
					script: item.script,
				};

				if (type === 'task') {
					data.scriptLanguage =
						item.scriptLanguage || DEFAULT_LANGUAGE;
				}

				let id;

				if (item.id) {
					id = item.id;
				}
				else if (item.name) {
					id = item.name;
				}
				else {
					return;
				}

				// To be removed after next stories

				if (type !== 'start' && type !== 'end' && type !== 'state') {
					type = 'state';
				}

				nodes.push({
					data,
					id,
					position,
					type,
				});
			});
		});

		return nodes;
	},

	updateXMLDefinition(content) {
		const instance = this;

		instance.definition = new XMLDefinition({
			value: content,
		});
	},
};
