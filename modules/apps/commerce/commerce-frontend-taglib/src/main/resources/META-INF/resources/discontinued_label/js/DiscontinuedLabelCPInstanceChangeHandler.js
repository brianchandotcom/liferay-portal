/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as Events from 'commerce-frontend-js/utilities/eventsDefinitions';

const COMPONENT_NAME = 'discontinued-label';

export default function ({namespace}) {
	Liferay.on(`${namespace}${Events.CP_INSTANCE_CHANGED}`, ({cpInstance}) => {
		const elementClassName = `${namespace}${COMPONENT_NAME}`;

		const componentElement = document.querySelector(`.${elementClassName}`);

		if (componentElement) {
			componentElement.querySelector(
				'.label-item'
			).innerHTML = Liferay.Language.get('discontinued');

			componentElement.className = `label ${
				cpInstance.discontinued ? 'label-danger' : 'invisible'
			} m-0 ${elementClassName}`;
		}
	});
}
