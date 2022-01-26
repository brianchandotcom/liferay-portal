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

import {fetch, openToast} from 'frontend-js-web';

import {FILE_MAPPED_FIELDS} from './constants';

function getMappingFromTemplate(template) {
	const {mappings} = template;

	return mappings.reduce((accumulator, map) => {
		accumulator[map.internalFieldName] = map.externalFieldName;

		return accumulator;
	}, {});
}

export async function fireTemplateSelectionEvent(
	templateId,
	NULL_TEMPLATE_VALUE,
	TEMPLATE_SELECTED_EVENT,
	HEADLESS_BATCH_PLANNER_URL,
	HEADLESS_ENDPOINT_POLICY_NAME
) {
	if (templateId === NULL_TEMPLATE_VALUE) {
		return Liferay.fire(TEMPLATE_SELECTED_EVENT, {
			template: null,
		});
	}

	try {
		const request = await fetch(
			`${HEADLESS_BATCH_PLANNER_URL}/plans/${templateId}`
		);

		if (!request.ok) {
			return openToast({
				message: Liferay.Language.get('your-request-has-failed'),
				type: 'danger',
			});
		}

		const templateRequest = await request.json();

		const headlessEndpoint = templateRequest.policies.find(
			(policy) => policy?.name === HEADLESS_ENDPOINT_POLICY_NAME
		);

		Liferay.fire(FILE_MAPPED_FIELDS, {
			fields: getMappingFromTemplate(templateRequest),
		});

		Liferay.fire(TEMPLATE_SELECTED_EVENT, {
			template: {
				externalType: templateRequest.externalType,
				headlessEndpoint: headlessEndpoint?.value,
				internalClassName: templateRequest.internalClassName,
				mapping: getMappingFromTemplate(templateRequest),
			},
		});

		return templateRequest;
	}
	catch (error) {
		openToast({
			message: Liferay.Language.get('your-request-has-failed'),
			type: 'danger',
		});
	}
}
