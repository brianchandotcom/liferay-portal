/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, runScriptsInElement, toggleDisabled} from 'frontend-js-web';

export default function ({currentURL, incomplete, namespace, publishURL}) {
	const submit = document.getElementById(`${namespace}submitLink`);

	const onSubmit = () => {
		const layoutRevisionDetails = document.getElementById(
			`${namespace}layoutRevisionDetails`
		);

		const layoutRevisionInfo = layoutRevisionDetails.querySelector(
			'.layout-revision-info'
		);

		if (layoutRevisionInfo) {
			layoutRevisionInfo.classList.add('loading');
		}

		if (submit) {
			submit.innerHTML = Liferay.Language.get('loading') + '...';
			runScriptsInElement(submit);
		}

		fetch(publishURL)
			.then(() => {
				if (incomplete) {
					location.href = currentURL;
				}
				else {
					Liferay.fire('updatedLayout');
				}
			})
			.catch(() => {
				layoutRevisionDetails.classList.add('alert alert-danger');

				layoutRevisionDetails.innerHTML = Liferay.Language.get(
					'there-was-an-unexpected-error.-please-refresh-the-current-page'
				);
				runScriptsInElement(layoutRevisionDetails);
			});
	};

	if (submit) {
		submit.addEventListener('click', onSubmit);
	}

	const toggle = document.getElementById(`${namespace}readyToggle`);

	const onToggleDisabled = () => {
		toggleDisabled(toggle, true);
		onSubmit();
	};

	if (toggle) {
		toggle.addEventListener('change', onToggleDisabled);
	}

	return {
		dispose() {
			if (submit) {
				submit.removeEventListener('click', onSubmit);
			}
			if (toggle) {
				toggle.removeEventListener('change', onToggleDisabled);
			}
		},
	};
}
