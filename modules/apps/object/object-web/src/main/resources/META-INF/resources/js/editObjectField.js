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

import {ERRORS} from './utils/errors';

const onChangeFieldType = (iframeDocument, event, namespace) => {
	const searchableContainer = iframeDocument.getElementById(
		`${namespace}searchableContainer`
	);
	const indexedGroup = iframeDocument.getElementById(
		`${namespace}indexedGroup`
	);
	const indexed = iframeDocument.getElementById(`${namespace}indexed`).value;

	indexedGroup.style.display =
		indexed && event.target.value === 'String' ? 'block' : 'none';
	searchableContainer.style.display =
		event.target.value !== 'Blob' ? 'block' : 'none';
};

const onChangeSeachableSwitch = (iframeDocument, event, namespace) => {
	const indexedGroup = iframeDocument.getElementById(
		`${namespace}indexedGroup`
	);
	const type = iframeDocument.getElementById(`${namespace}type`).value;

	indexedGroup.style.display =
		event.target.checked && type === 'String' ? 'block' : 'none';
};

const onChangeSeachableType = (value, namespace) => {
	const indexedLanguageIdGroup = document.getElementById(
		`${namespace}indexedLanguageIdGroup`
	);

	indexedLanguageIdGroup.style.display = value === 'text' ? 'block' : 'none';
};

const saveObjectField = (iframeDocument, namespace, objectFieldId) => {
	const inputIndexed = iframeDocument.getElementById(`${namespace}indexed`);

	const inputIndexedTypeKeyword = iframeDocument
		.getElementById(`${namespace}inputIndexedTypeKeyword`)
		.querySelector('input');

	const inputIndexedTypeText = iframeDocument
		.getElementById(`${namespace}inputIndexedTypeText`)
		.querySelector('input');

	const inputIndexedLanguageId = iframeDocument.getElementById(
		`${namespace}indexedLanguageId`
	);

	const inputName = iframeDocument.getElementById(`${namespace}name`);
	const inputRequired = iframeDocument.getElementById(`${namespace}required`);
	const inputType = iframeDocument.getElementById(`${namespace}type`);

	const indexed = inputIndexed.checked;
	const indexedAsKeyword =
		inputIndexed.checked && inputIndexedTypeKeyword.checked;
	const indexedLanguageId =
		inputIndexed.checked &&
		inputIndexedTypeText.checked &&
		inputType.value === 'String'
			? inputIndexedLanguageId.value
			: null;

	const name = inputName.value;
	const required = inputRequired.checked;
	const type = inputType.value;

	const localizedInputs = iframeDocument
		.querySelector('form')
		.querySelectorAll(`input[id^=${namespace}][type='hidden']`);

	const localizedLabels = Array(...localizedInputs).reduce((prev, cur) => {
		if (cur.value) {
			prev[cur.id.replace(`${namespace}label_`, '')] = cur.value;
		}

		return prev;
	}, {});

	Liferay.Util.fetch(`/o/object-admin/v1.0/object-fields/${objectFieldId}`, {
		body: JSON.stringify({
			DBType: type,
			indexed,
			indexedAsKeyword,
			indexedLanguageId,
			label: localizedLabels,
			listTypeDefinitionId: 0,
			name,
			required,
		}),
		headers: new Headers({
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		}),
		method: 'PUT',
	})
		.then((response) => {
			if (response.status === 401) {
				window.location.reload();
			}
			else if (response.ok) {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'the-object-field-was-updated-successfully'
					),
					type: 'success',
				});

				setTimeout(() => {
					const parentWindow = Liferay.Util.getOpener();
					parentWindow.Liferay.fire('close-side-panel');
				}, 1500);
			}
			else {
				return response.json();
			}
		})
		.then((response) => {
			if (response && response.type) {
				const {type} = response;
				const isMapped = Object.prototype.hasOwnProperty.call(
					ERRORS,
					type
				);
				const errorMessage = isMapped
					? ERRORS[type]
					: Liferay.Language.get('an-error-occurred');

				Liferay.Util.openToast({
					message: errorMessage,
					type: 'danger',
				});
			}
		});
};

export default function ({namespace, objectFieldId}) {
	const form = document.getElementById(`${namespace}fm`);
	const inputType = document.getElementById(`${namespace}type`);
	const inputIndexed = document.getElementById(`${namespace}indexed`);
	const inputIndexedTypeKeyword = document
		.getElementById(`${namespace}inputIndexedTypeKeyword`)
		.querySelector('input');
	const inputIndexedTypeText = document
		.getElementById(`${namespace}inputIndexedTypeText`)
		.querySelector('input');

	inputType.addEventListener('change', (event) =>
		onChangeFieldType(document, event, namespace)
	);
	inputIndexed.addEventListener('change', (event) =>
		onChangeSeachableSwitch(document, event, namespace)
	);
	inputIndexedTypeKeyword.addEventListener('change', () =>
		onChangeSeachableType('keyword', namespace)
	);
	inputIndexedTypeText.addEventListener('change', () =>
		onChangeSeachableType('text', namespace)
	);
	form.addEventListener('submit', () =>
		saveObjectField(document, namespace, objectFieldId)
	);
}
