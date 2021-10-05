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

import {
	createPortletURL,
	delegate,
	fetch,
	openSelectionModal,
	openToast,
} from 'frontend-js-web';

export default function ({
	actionURL,
	ddmStructure,
	ddmStructureId,
	eventName,
	namespace,
	portletNamespace,
	portletURL,
	windowState,
}) {
	const form = document.getElementById(`${namespace}fm`);
	const templateKeyInput = document.getElementById(
		`${namespace}ddmTemplateKey`
	);
	const templatePreview = document.querySelector('.template-preview-content');

	const createTemplatePreview = ({className, content, label}) => {
		const element = document.createElement(label);
		element.className = className;

		if (content) {
			element.innerHTML = content;
		}

		templatePreview.innerHTML = '';
		templatePreview.append(element);
	};

	const onSelectTemplate = (event) => {
		event.preventDefault();

		openSelectionModal({
			onSelect: (selectedItem) => {
				templateKeyInput.value = selectedItem.ddmtemplatekey;

				createTemplatePreview({
					className: 'loading-animation',
					label: 'div',
				});

				const data = new URLSearchParams(
					Liferay.Util.ns(portletNamespace, {
						ddmTemplateKey: selectedItem.ddmtemplatekey,
					})
				);

				fetch(actionURL, {
					body: data,
					method: 'POST',
				})
					.then((response) => {
						return response.text();
					})
					.then((response) => {
						templatePreview.innerHTML = response;
					})
					.catch(() => {
						createTemplatePreview({
							className: 'alert alert-danger hidden',
							content: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							label: 'div',
						});
					});

				openToast({
					container: form.parentElement.previousElementSibling,
					message: Liferay.Language.get(
						'changing-the-template-will-not-affect-the-original-web-content-default-template.-the-change-only-applies-to-this-web-content-display'
					),
					type: 'info',
				});
			},
			selectEventName: eventName,
			title: Liferay.Language.get('templates'),
			url: createPortletURL(portletURL, {
				eventName,
				p_p_state: windowState,
				...(ddmStructure !== null && {ddmStructureId}),
			}).toString(),
		});
	};

	const eventDelegates = [
		delegate(
			form,
			'click',
			`#${namespace}selectDDMTemplateButton`,
			onSelectTemplate
		),
		delegate(
			form,
			'click',
			`#${namespace}clearddmTemplateButton, #${namespace}ddmTemplateTypeDefault`,
			() => {
				templateKeyInput.value = '';

				createTemplatePreview({
					className: 'text-default',
					content: Liferay.Language.get('no-template'),
					label: 'p',
				});
			}
		),
	];

	Liferay.Util.toggleRadio(
		`${namespace}ddmTemplateTypeCustom`,
		`${namespace}customDDMTemplateContainer`,
		null
	);

	Liferay.Util.toggleRadio(
		`${namespace}ddmTemplateTypeDefault`,
		null,
		`${namespace}customDDMTemplateContainer`
	);

	return {
		dispose() {
			eventDelegates.forEach((eventDelegate) => eventDelegate.dispose());
		},
	};
}
