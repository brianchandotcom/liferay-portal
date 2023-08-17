/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function attachListener(element, eventType, callback) {
	element?.addEventListener(eventType, callback);

	return {
		detach() {
			element?.removeEventListener(eventType, callback);
		},
	};
}

export default function EditKBArticle({kbArticle, namespace, publishAction}) {
	const contextualSidebarButton = document.getElementById(
		`${namespace}contextualSidebarButton`
	);

	const contextualSidebarContainer = document.getElementById(
		`${namespace}contextualSidebarContainer`
	);

	const contextualSidebarButtonOnClick = () => {
		contextualSidebarContainer?.classList.toggle(
			'contextual-sidebar-visible'
		);
	};

	const titleInput = document.getElementById(`${namespace}title`);
	const urlTitleInput = document.getElementById(`${namespace}urlTitle`);

	const titleOnInputEvent = (event) => {
		const customUrl = urlTitleInput.dataset.customUrl;

		if (customUrl === 'false') {
			urlTitleInput.value = Liferay.Util.normalizeFriendlyURL(
				event.target.value
			);
		}
	};

	const urlTitleOnInputEvent = (event) => {
		event.currentTarget.dataset.customUrl = urlTitleInput.value !== '';
	};

	const form = document.getElementById(`${namespace}fm`);

	let publishButton;

	if (Liferay.FeatureFlags['LPS-188060']) {
		publishButton = document.getElementById(`${namespace}publishItem`);
	}
	else {
		publishButton = document.getElementById(`${namespace}publishButton`);
	}

	const updateMultipleKBArticleAttachments = function () {
		const selectedFileNameContainer = document.getElementById(
			`${namespace}selectedFileNameContainer`
		);
		const buffer = [];
		const filesChecked = form.querySelectorAll(
			`input[name=${namespace}selectUploadedFile]:checked`
		);

		for (let i = 0; i < filesChecked.length; i++) {
			buffer.push(
				`<input id="${namespace}selectedFileName${i}"
					name="${namespace}selectedFileName"
					type="hidden"
					value="${filesChecked[i].value}"
				/>`
			);
		}

		selectedFileNameContainer.innerHTML = buffer.join('');
	};

	const beforeSubmit = function () {
		document.getElementById(`${namespace}content`).value = window[
			`${namespace}contentEditor`
		].getHTML();

		updateMultipleKBArticleAttachments();
	};

	const publishButtonOnClick = () => {
		const workflowActionInput = document.getElementById(
			`${namespace}workflowAction`
		);

		if (workflowActionInput) {
			workflowActionInput.value = publishAction;
		}

		if (!kbArticle) {
			const customUrl = urlTitleInput.dataset.customUrl;

			if (customUrl === 'false') {
				urlTitleInput.value = '';
			}
		}

		if (Liferay.FeatureFlags['LPS-188060']) {
			beforeSubmit();
			submitForm(form);
		}
	};

	const eventHandlers = [
		attachListener(publishButton, 'click', publishButtonOnClick),
		attachListener(
			contextualSidebarButton,
			'click',
			contextualSidebarButtonOnClick
		),
		attachListener(form, 'submit', () => {
			beforeSubmit();
		}),
	];

	if (!kbArticle) {
		eventHandlers.push(
			attachListener(titleInput, 'input', titleOnInputEvent)
		);

		eventHandlers.push(
			attachListener(urlTitleInput, 'input', urlTitleOnInputEvent)
		);
	}

	return {
		dispose() {
			eventHandlers.forEach(({detach}) => {
				detach();
			});
		},
	};
}
