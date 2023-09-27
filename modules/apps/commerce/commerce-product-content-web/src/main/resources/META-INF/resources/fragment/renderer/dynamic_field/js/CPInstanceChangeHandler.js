/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
