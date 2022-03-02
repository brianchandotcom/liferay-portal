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

import {isObject} from '../util/utils';

function parse(value, field) {
	if (field.parser) {
		if (field.parser instanceof Function) {
			value.content = field.parser.call(this, value.content);
		}
	}

	return value;
}

function getChildAttributes(childNodes) {
	const attributes = [];

	Object.entries(childNodes).map((childNode) => {
		const childAttributes = childNode[1].attributes;
		if (childAttributes) {
			Object.entries(childAttributes).map((attribute) => {
				attributes.push({
					[attribute[1].value]: attribute[1].ownerElement.textContent,
				});
			});
		}
	});

	return attributes;
}

function getLocationValue(field, context) {
	const locator = field.locator || field.key || field;
	const xmlDoc = context.ownerDocument || context;
	let result;
	let res;
	const value = {};

	if (xmlDoc.evaluate !== undefined) {
		result = xmlDoc.evaluate(
			locator,
			context,
			xmlDoc.createNSResolver(
				!context.ownerDocument
					? context.documentElement
					: context.ownerDocument.documentElement
			),
			0,
			null
		);

		while ((res = result.iterateNext())) {
			const resNodesAttributes = getChildAttributes(res.childNodes);

			if (resNodesAttributes.length) {
				value.content = resNodesAttributes;
			}
			else if (res.children.length) {
				const content = [];

				for (const child of res.children) {
					const childContent = {};

					if (!child.children.length) {
						const childNodesAttributes = getChildAttributes(
							child.childNodes
						);

						let itemContent;

						if (childNodesAttributes.length) {
							itemContent = childNodesAttributes;
						}
						else {
							itemContent = child.textContent;
						}

						childContent[child.tagName] = itemContent;
					}
					else {
						for (const item of child.children) {
							if (item.children.length) {
								for (const itemChild of item.children) {
									const childNodesAttributes = getChildAttributes(
										itemChild.childNodes
									);

									const itemChildNodesAttributes = getChildAttributes(
										item.childNodes
									);

									let itemContent;

									if (childNodesAttributes.length) {
										itemContent = childNodesAttributes;
									}
									else if (
										itemChildNodesAttributes.length
									) {
										itemContent = itemChildNodesAttributes;

										if (itemChildNodesAttributes.length) {
											if (!childContent[item.tagName]) {
												childContent[item.tagName] = [];
											}

											childContent[
												item.tagName
											] = itemContent;
										}

										break;
									}
									else {
										itemContent = itemChild.textContent;
									}

									if (!childContent[itemChild.tagName]) {
										childContent[itemChild.tagName] = [];
									}

									childContent[itemChild.tagName].push(
										itemContent
									);
								}
							}
							else {
								const childNodesAttributes = getChildAttributes(
									item.childNodes
								);

								let itemContent;

								if (childNodesAttributes.length) {
									itemContent = childNodesAttributes;
								}
								else {
									itemContent = item.textContent;
								}

								if (childContent[item.tagName]) {
									childContent[item.tagName] = [
										childContent[item.tagName],
										itemContent,
									];
								}
								else {
									childContent[item.tagName] = itemContent;
								}
							}
						}
					}

					content.push(childContent);
				}

				value.content = content;
			}
			else {
				value.content = res.textContent;
			}
		}
	}

	return parse(value, field);
}

function parseMeta(metaFields, xmldoc_in, data_out) {
	if (isObject(metaFields)) {
		let key;
		const xmldoc = xmldoc_in.ownerDocument || xmldoc_in;

		for (key in metaFields) {
			if (Object.keys(metaFields).includes(key)) {
				data_out.meta[key] = getLocationValue(
					metaFields[key],
					xmldoc
				).content;
			}
		}
	}

	return data_out;
}

function parseResults(schema, xmldoc_in, data_out) {
	if (schema.resultListLocator && Array.isArray(schema.resultFields)) {
		const nodeList = xmldoc_in.getElementsByTagName(
			schema.resultListLocator
		);
		const fields = schema.resultFields;
		const results = [];
		let node;
		let field;
		let result;
		let i;
		let j;

		if (nodeList.length) {

			// Loop through each result node

			for (i = nodeList.length - 1; i >= 0; i--) {
				result = {};
				node = nodeList[i];

				// Find each field value

				for (j = fields.length - 1; j >= 0; j--) {
					field = fields[j];

					const locationValue = getLocationValue(field, node);

					result[field.key || field] = locationValue.content;

					if (locationValue.attributes) {
						result.metaAttributes = locationValue.attributes;
					}
				}

				results[i] = result;
			}

			data_out.results = results;
		}
		else {
			data_out.error = new Error(
				'XML schema result nodes retrieval failure'
			);
		}
	}

	return data_out;
}

const XMLSchemaUtil = {
	applySchema(schema, data) {
		const xmlDoc = data;
		let data_out = {meta: {}, results: []};

		if (
			xmlDoc &&
			xmlDoc.nodeType &&
			(xmlDoc.nodeType === 9 ||
				xmlDoc.nodeType === 1 ||
				xmlDoc.nodeType === 11) &&
			schema
		) {
			data_out = parseResults(schema, xmlDoc, data_out);

			data_out = parseMeta(schema.metaFields, xmlDoc, data_out);
		}
		else {
			data_out.error = new Error('XML schema parse failure');
		}

		return data_out;
	},
};

export default XMLSchemaUtil;
