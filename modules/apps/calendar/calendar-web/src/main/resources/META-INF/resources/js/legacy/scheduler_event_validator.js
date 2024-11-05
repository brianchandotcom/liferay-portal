/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	for (const container of ['startDateContainer', 'endDateContainer'].map(
		(id) => document.getElementById(namespace + id)
	)) {
		if (container) {
			const callback = (mutationList) => {
				for (const mutation of mutationList) {
					const classMutation =
						mutation.type === 'attributes' &&
						mutation.attributeName === 'class';

					if (
						classMutation &&
						mutation.target.classList.contains('has-success')
					) {
						wrapper.classList.remove('has-success');
					}

					if (
						classMutation &&
						mutation.target.classList.contains('has-error')
					) {
						const inputDate =
							wrapper.querySelector('.lfr-input-date');
						const formGroupItem =
							inputDate.closest('.form-group-item');

						formGroupItem.classList.add('has-error');
						formGroupItem.classList.remove('has-success');
						formGroupItem.classList.remove('has-warning');

						wrapper.classList.remove('has-error');
					}
				}
			};

			const config = {attributes: true, childList: false, subtree: false};
			const observer = new MutationObserver(callback);
			const wrapper = container.querySelector('.input-long-wrapper');

			observer.observe(wrapper, config);
		}
	}

	function clearAlert(field) {
		const feedback = field.parentElement.querySelector(
			'.form-feedback-item'
		);
		const formGroupItem = field.closest('.form-group-item');

		if (formGroupItem) {
			formGroupItem.classList.add('has-success');
			formGroupItem.classList.remove('has-warning', 'has-error');
		}

		if (feedback) {
			feedback.remove();
		}
	}

	function showAlert(field, message, type) {
		clearAlert(field);

		if (!field.parentElement.querySelector('.form-feedback-item')) {
			field.insertAdjacentHTML(
				'afterend',
				'<div aria-live="polite" class="form-feedback-item" role="alert">' +
					'<span class="form-feedback-indicator">' +
					message +
					'</span>' +
					'</div>'
			);
		}

		const formGroupItem = field.closest('.form-group-item');

		if (formGroupItem) {
			formGroupItem.classList.remove('has-success');
			formGroupItem.classList.toggle('has-warning', type === 'warning');
			formGroupItem.classList.toggle('has-error', type === 'error');
		}
	}

	for (const dateInput of ['startTime', 'endTime'].map((id) =>
		document.getElementById(namespace + id)
	)) {
		if (dateInput) {
			dateInput.closest('.form-group-item').classList.add('has-success');

			dateInput.onblur = () => {
				const {value} = dateInput;

				if (value.length < dateInput.maxLength) {
					showAlert(
						dateInput,
						Liferay.Language.get(
							'this-field-will-be-automatically-filled-if-it-is-empty-or-incomplete'
						),
						'warning'
					);
				}
				else {
					clearAlert(dateInput);
				}
			};
		}
	}

	for (const timeInput of ['startTimeTime', 'endTimeTime'].map((id) =>
		document.getElementById(namespace + id)
	)) {
		if (timeInput) {
			timeInput.closest('.form-group-item').classList.add('has-success');

			timeInput.onblur = () => {
				const {validity, value} = timeInput;

				if (validity.valid && value.trim() === '') {
					showAlert(
						timeInput,
						Liferay.Language.get(
							'this-field-will-be-automatically-filled-if-it-is-empty-or-incomplete'
						),
						'warning'
					);
				}
				else if (!validity.valid) {
					showAlert(
						timeInput,
						Liferay.Language.get('please-enter-a-valid-time'),
						'error'
					);
				}
				else {
					clearAlert(timeInput);
				}
			};
		}
	}
}
