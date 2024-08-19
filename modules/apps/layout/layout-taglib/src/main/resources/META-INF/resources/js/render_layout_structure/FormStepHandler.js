/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function FormStepHandler({formId}) {
	const form = document.querySelector(`.lfr-layout-structure-item-${formId}`);

	const steps = form.querySelector(
		'.lfr-layout-structure-item-form-step-container'
	).children;

	const onStepChange = ({emitter, step}) => {

		// Return if the emitter is not in this form

		if (!form.contains(emitter)) {
			return;
		}

		// Hide current active step

		for (const formStep of steps) {
			if (!formStep.classList.contains('d-none')) {
				formStep.classList.add('d-none');

				break;
			}
		}

		// Show new active step

		steps[step].classList.remove('d-none');
	};

	Liferay.on('formFragment:changeStep', onStepChange);

	return {
		dispose: () => {
			Liferay.detach('formFragment:changeStep', onStepChange);
		},
	};
}
