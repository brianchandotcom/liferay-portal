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

import {delegate, fetch, toggleDisabled} from 'frontend-js-web';

export default function ({eventName, journalArticleAssetClassName, namespace}) {
	const structureFormContainer = document.getElementById(
		`${namespace}selectDDMStructureFieldForm`
	);

	const selectorButtons = structureFormContainer.querySelectorAll(
		'.selector-button'
	);

	selectorButtons.forEach((button) => {
		const fieldsnamespace = button.dataset.fieldsnamespace;
		const value = JSON.parse(button.dataset.value);

		const componentId = `${namespace}${fieldsnamespace}ddmForm`;

		Liferay.componentReady(componentId).then(() => {
			const initialDDMForm = Liferay.component(componentId);

			initialDDMForm.get('fields').forEach((field) => {
				if (field.get('name') === value.ddmStructureFieldName) {
					field.setValue(value.ddmStructureFieldValue);
				}
			});
		});
	});

	const onSubmitForm = function (event) {
		event.preventDefault();
		const selectorButton = event.target.closest('.selector-button');

		const dataset = selectorButton.dataset;

		const ddmForm = Liferay.component(
			`${namespace}${dataset.fieldsnamespace}ddmForm`
		);

		ddmForm.updateDDMFormInputValue();

		const form = document.getElementById(dataset.form);

		fetch(form.action, {
			body: new FormData(form),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				const message = document.getElementById(`${namespace}message`);

				if (response.success) {
					dataset.className = journalArticleAssetClassName;
					dataset.displayValue = response.displayValue;
					dataset.value = response.value;

					message.classList.add('hide');

					Liferay.Util.getOpener().Liferay.fire(eventName, dataset);

					Liferay.Util.getWindow().destroy();
				}
				else {
					message.classList.remove('hide');
				}
			});
	};

	const clickSubmitForm = delegate(
		structureFormContainer,
		'click',
		'.selector-button',
		onSubmitForm
	);

	const classTypeFieldsSearchContainerId = `${namespace}classTypeFieldsSearchContainer`;
	const fieldSubtypeForms = structureFormContainer.querySelectorAll('form');

	Liferay.componentReady(classTypeFieldsSearchContainerId).then(() => {
		const classTypeFieldsSearchContainer = document.getElementById(
			classTypeFieldsSearchContainerId
		);

		const toggleDisabledFormFields = (form, state) => {
			toggleDisabled(
				form.querySelectorAll('input, select, textarea'),
				state
			);
		};

		const onToggleDisabled = ({target}) => {
			const {buttonId, formId} = target.dataset;

			toggleDisabled(selectorButtons, true);

			fieldSubtypeForms.forEach((fieldSubtypeForm) => {
				toggleDisabledFormFields(fieldSubtypeForm, true);
			});

			toggleDisabled(`#${buttonId}`, false);

			toggleDisabledFormFields(
				document.querySelector(`#${formId}`),
				false
			);
		};

		const clickToggleDisabled = delegate(
			classTypeFieldsSearchContainer,
			'click',
			`input[name=${namespace}selectStructureFieldSubtype]`,
			onToggleDisabled
		);

		return {
			dispose() {
				clickSubmitForm.dispose();
				clickToggleDisabled.dispose();
			},
		};
	});
}
