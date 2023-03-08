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

import * as Events from 'commerce-frontend-js/utilities/eventsDefinitions';

const deepValue = (object, path, defaultValue) => {
	const value = path
		.replace(/\[|\]\.?/g, '.')
		.split('.')
		.filter((s) => s)
		.reduce((acc, val) => acc && acc[val], object);

	if (value !== undefined) {
		return value;
	}
	else {
		return defaultValue;
	}
};

export default function ({elementId, field, namespace}) {
	const element = document.getElementById(elementId);
	if (element) {
		Liferay.on(
			`${namespace}${Events.CP_INSTANCE_CHANGED}`,
			({cpInstance}) => {
				if (cpInstance) {
					const valueElement = element.querySelector('.node-value');
					valueElement.innerText = deepValue(cpInstance, field, '');
				}
			}
		);
	}
}
