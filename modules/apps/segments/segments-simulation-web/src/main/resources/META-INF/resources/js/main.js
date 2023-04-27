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

import {fetch} from 'frontend-js-web';

export default function ({
	deactivateSimulationURL,
	namespace: portletNamespace,
	simulateSegmentsEntriesURL,
}) {
	const form = document.getElementById(
		`${portletNamespace}segmentsSimulationFm`
	);

	const fetchDeactivateSimulation = () => {
		fetch(deactivateSimulationURL, {
			body: new FormData(form),
			method: 'POST',
		}).then(() => {
			const simulationElements = document.querySelectorAll(
				`#${form.id} input`
			);

			for (let i = 0; i < simulationElements.length; i++) {
				simulationElements[i].setAttribute('checked', false);
			}
		});
	};

	const simulateSegmentsEntries = () => {
		fetch(simulateSegmentsEntriesURL, {
			body: new FormData(form),
			method: 'POST',
		}).then(() => {
			const iframe = document.querySelector('iframe');

			if (iframe) {
				iframe.contentWindow.location.reload();
			}
		});
	};

	Liferay.on(
		'SimulationMenu:closeSimulationPanel',
		fetchDeactivateSimulation
	);

	Liferay.on('SimulationMenu:openSimulationPanel', simulateSegmentsEntries);

	document.addEventListener('beforeunload', fetchDeactivateSimulation);

	return {
		dispose() {
			document.removeEventListener(
				'beforeunload',
				fetchDeactivateSimulation
			);

			Liferay.detach(
				'SimulationMenu:openSimulationPanel',
				simulateSegmentsEntries
			);

			Liferay.detach(
				'SimulationMenu:closeSimulationPanel',
				fetchDeactivateSimulation
			);
		},
	};
}
