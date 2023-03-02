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

const COMPONENT_NAME = 'availability-label';

export default function ({displayAvailability, namespace}) {
	Liferay.on(`${namespace}${Events.CP_INSTANCE_CHANGED}`, ({cpInstance}) => {
		const elementClassName = `${namespace}${COMPONENT_NAME}`;

		const componentElement = document.querySelector(`.${elementClassName}`);

		if (componentElement) {
			let availabilityDisplayType = 'danger';

			if (cpInstance.availability.label === 'available') {
				availabilityDisplayType = 'success';
			}

			componentElement.querySelector('.label-item').innerHTML =
				cpInstance.availability.label_i18n;

			componentElement.className = `label ${
				displayAvailability
					? `label-${availabilityDisplayType}`
					: 'invisible'
			} m-0 ${elementClassName}`;
		}
	});
}
