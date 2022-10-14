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

	const publishButton = document.getElementById(`${namespace}publishButton`);

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
	};

	const form = document.getElementById(`${namespace}fm`);

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

	const eventHandlers = [
		attachListener(publishButton, 'click', publishButtonOnClick),
		attachListener(
			contextualSidebarButton,
			'click',
			contextualSidebarButtonOnClick
		),
		attachListener(form, 'submit', () => {
			document.getElementById(`${namespace}content`).value = window[
				`${namespace}contentEditor`
			].getHTML();

			updateMultipleKBArticleAttachments();
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
