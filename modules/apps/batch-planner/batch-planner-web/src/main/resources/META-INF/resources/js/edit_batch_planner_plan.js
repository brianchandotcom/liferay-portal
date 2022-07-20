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

import {render} from '@liferay/frontend-js-react-web';
import {fetch, openToast} from 'frontend-js-web';

import TemplateSelect from './TemplateSelect';
import {
	HEADLESS_BATCH_PLANNER_URL,
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED_EVENT,
} from './constants';

const HEADERS = new Headers({
	'content-type': 'application/json',
	'x-csrf-token': window.Liferay.authToken,
});

function handleOverrideExistingRecordsCheckbox(namespace) {
	const overrideExistingRecordsCheckbox = document.querySelector(
		`#${namespace}allowUpdate`
	);

	const ignoreBlankFieldCheckbox = document.querySelector(
		`#${namespace}onUpdateDoPatch`
	);

	overrideExistingRecordsCheckbox.addEventListener('change', ({target}) => {
		ignoreBlankFieldCheckbox.disabled = !target.checked;

		ignoreBlankFieldCheckbox.checked = false;
	});
}

function trimPackage(name) {
	if (!name || name.lastIndexOf('.') < 0) {
		return name;
	}

	return name.substr(name.lastIndexOf('.') + 1);
}

export default function ({
	initialExternalType,
	initialTemplateClassName,
	initialTemplateMapping,
	isExport,
	namespace,
	templatesOptions,
}) {
	const internalClassNameSelect = document.querySelector(
		`#${namespace}internalClassName`
	);
	const taskItemDelegateNameInput = document.querySelector(
		`#${namespace}taskItemDelegateName`
	);
	const externalTypeInput = document.querySelector(
		`#${namespace}externalType`
	);

	if (isExport) {
		const containsHeadersCheckboxWrapper = document
			.getElementById(`${namespace}containsHeaders`)
			.closest('.contains-headers-wrapper');

		externalTypeInput.addEventListener('change', ({target}) => {
			if (target.value === 'CSV') {
				containsHeadersCheckboxWrapper.classList.remove('d-none');
			}
			else {
				containsHeadersCheckboxWrapper.classList.add('d-none');
			}
		});
	}
	else {
		handleOverrideExistingRecordsCheckbox(namespace);
	}

	async function handleTemplateSelectedEvent({template}) {
		if (template) {
			if (template.externalType) {
				externalTypeInput.value = template.externalType;
			}

			const internalClassTemplateOption = internalClassNameSelect.querySelector(
				`option[value='${template.internalClassName}']`
			);

			internalClassTemplateOption.selected = true;

			await handleClassNameSelectChange();
		}
	}

	async function handleClassNameSelectChange(event) {
		if (event) {
			Liferay.fire(TEMPLATE_SOILED_EVENT);
		}

		const selectedOption =
			internalClassNameSelect.options[
				internalClassNameSelect.selectedIndex
			];

		const schemaName = selectedOption.getAttribute('schemaName');

		taskItemDelegateNameInput.value = schemaName || 'DEFAULT';

		const internalClassNameValue = trimPackage(
			schemaName || selectedOption.value
		);

		if (!internalClassNameValue) {
			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				schema: null,
			});

			return;
		}

		try {
			const response = await fetch(
				`${HEADLESS_BATCH_PLANNER_URL}/plans/${selectedOption.value.replace(
					'#',
					encodeURIComponent('#')
				)}/fields?export=${isExport}`,
				{
					credentials: 'include',
					headers: HEADERS,
					method: 'GET',
				}
			);

			const data = await response.json();

			Liferay.fire(SCHEMA_SELECTED_EVENT, {
				isExport,
				schema: data.items,
				schemaName: selectedOption.value,
			});
		}
		catch (error) {
			openToast({
				message: Liferay.Language.get('your-request-has-failed'),
				type: 'danger',
			});

			console.error(`Failed to fetch ${error}`);
		}
	}

	Liferay.on(TEMPLATE_SELECTED_EVENT, handleTemplateSelectedEvent);

	internalClassNameSelect.addEventListener(
		'change',
		handleClassNameSelectChange
	);

	let initialTemplate;

	if (initialTemplateClassName && initialTemplateMapping) {
		initialTemplate = {
			externalType: initialExternalType,
			internalClassName: initialTemplateClassName,
			mapping: initialTemplateMapping,
		};
	}

	render(
		TemplateSelect,
		{
			initialTemplate,
			initialTemplateOptions: templatesOptions,
			portletNamespace: namespace,
		},
		document.getElementById(`${namespace}templateSelect`)
	);

	return {
		dispose: () => {
			Liferay.detach(
				TEMPLATE_SELECTED_EVENT,
				handleTemplateSelectedEvent
			);
		},
	};
}
