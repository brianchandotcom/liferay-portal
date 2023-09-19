/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			if (iframe?.contentWindow) {
				iframe.contentWindow.location.reload();
			}
		});
	};

	document.addEventListener('beforeunload', fetchDeactivateSimulation);

	form.addEventListener('change', simulateSegmentsEntries);

	const simulationToggle = document.querySelector(
		'.product-menu-toggle.lfr-has-simulation-panel'
	);

	// @ts-ignore

	const sidenavInstance = Liferay.SideNavigation.initialize(simulationToggle);

	sidenavInstance.on('closed.lexicon.sidenav', () => {
		fetchDeactivateSimulation();
	});

	sidenavInstance.on('open.lexicon.sidenav', () => {
		simulateSegmentsEntries();
	});

	return {
		dispose() {
			document.removeEventListener(
				'beforeunload',
				fetchDeactivateSimulation
			);

			form.removeEventListener('change', simulateSegmentsEntries);

			Liferay.SideNavigation.destroy(simulationToggle);
		},
	};
}
