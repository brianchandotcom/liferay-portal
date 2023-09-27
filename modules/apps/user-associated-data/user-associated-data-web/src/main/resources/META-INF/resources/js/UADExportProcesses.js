/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, runScriptsInElement} from 'frontend-js-web';

const RENDER_INTERVAL_IDLE = 60000;

const RENDER_INTERVAL_IN_PROGRESS = 2000;

export default function UADExportProcesses({
	exportProcessesResourceURL,
	namespace,
}) {
	const exportProcessesNode = document.getElementById(
		`${namespace}exportProcesses`
	);

	const renderExportProcesses = () => {
		if (exportProcessesNode && exportProcessesResourceURL) {
			fetch(exportProcessesResourceURL)
				.then((response) => {
					return response.text();
				})
				.then((response) => {
					exportProcessesNode.innerHTML = response;

					runScriptsInElement(exportProcessesNode);

					scheduleRenderProcess();
				});
		}
	};

	let renderTimeOutId = setTimeout(
		renderExportProcesses,
		RENDER_INTERVAL_IN_PROGRESS
	);

	const isBackgroundTaskInProgress = () => {
		return !!document.querySelector('.export-process-status-in-progress');
	};

	const scheduleRenderProcess = () => {
		const renderInterval = isBackgroundTaskInProgress()
			? RENDER_INTERVAL_IN_PROGRESS
			: RENDER_INTERVAL_IDLE;

		renderTimeOutId = setTimeout(renderExportProcesses, renderInterval);
	};

	return {
		dispose: () => {
			clearTimeout(renderTimeOutId);
		},
	};
}
