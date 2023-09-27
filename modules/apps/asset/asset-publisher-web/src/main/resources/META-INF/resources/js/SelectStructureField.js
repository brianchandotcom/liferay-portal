/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	delegate,
	fetch,
	getOpener,
	getWindow,
	objectToFormData,
	runScriptsInElement,
} from 'frontend-js-web';

export default function ({
	assetClassName,
	eventName,
	getFieldItemURL,
	namespace,
}) {
	const fieldNameSelector = document.getElementById(`${namespace}fieldName`);
	const selectDDMStructureFieldForm = document.getElementById(
		`${namespace}selectDDMStructureFieldForm`
	);

	const eventDelegates = [];

	const onClickApplyButton = function () {
		const ddmForm = Liferay.component(`${namespace}ddmForm`);

		ddmForm.updateDDMFormInputValue();

		const form = document.getElementById(`${namespace}fieldForm`);

		const nameInput = document.getElementById(`${namespace}name`);

		nameInput.value = fieldNameSelector.value;

		fetch(form.action, {
			body: new FormData(form),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				const message = document.getElementById(`${namespace}message`);

				if (response.success) {
					message.classList.add('hide');

					getOpener().Liferay.fire(eventName, {
						data: {
							className: assetClassName,
							displayValue: response.displayValue,
							label:
								fieldNameSelector.options[
									fieldNameSelector.selectedIndex
								].label,
							name: fieldNameSelector.value,
							value: response.value,
						},
					});

					getWindow().destroy();
				}
				else {
					message.classList.remove('hide');
				}
			});
	};

	const clickApplyButton = delegate(
		selectDDMStructureFieldForm,
		'click',
		`#${namespace}applyButton`,
		onClickApplyButton
	);

	eventDelegates.push(clickApplyButton);

	const selectDDMStructureFieldContainer = document.getElementById(
		`${namespace}selectDDMStructureFieldContainer`
	);

	const onChangeField = () => {
		if (fieldNameSelector.value !== '') {
			fetch(getFieldItemURL, {
				body: objectToFormData({
					[`${namespace}name`]: fieldNameSelector.value,
				}),
				method: 'POST',
			})
				.then((response) => response.text())
				.then((response) => {
					selectDDMStructureFieldContainer.innerHTML = response;

					runScriptsInElement(selectDDMStructureFieldContainer);
				});
		}
		else {
			selectDDMStructureFieldContainer.innerHTML = '';
		}
	};

	onChangeField();

	const changeField = delegate(
		selectDDMStructureFieldForm,
		'change',
		`select#${namespace}fieldName`,
		onChangeField
	);

	eventDelegates.push(changeField);

	return {
		dispose() {
			eventDelegates.forEach((eventDelegate) => eventDelegate.dispose());
		},
	};
}
