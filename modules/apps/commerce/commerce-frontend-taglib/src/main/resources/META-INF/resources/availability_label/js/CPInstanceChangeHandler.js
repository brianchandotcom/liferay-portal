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

function updateAvailability({cpInstance, namespace}) {
	const availabilityLabel = document.querySelector(
		`.${namespace}-availability`
	);

	if (availabilityLabel) {
		const {availability, availabilityDisplayType} = cpInstance;

		availabilityLabel.querySelector('.label-item').innerHTML = availability;

		availabilityLabel.className = `label label-${
			availabilityDisplayType || 'default'
		} m-0`;
	}
}

export default function ({namespace}) {
	Liferay.on(`${namespace}${Events.CP_INSTANCE_CHANGED}`, ({cpInstance}) => {
		updateAvailability({cpInstance, namespace});
	});
}
