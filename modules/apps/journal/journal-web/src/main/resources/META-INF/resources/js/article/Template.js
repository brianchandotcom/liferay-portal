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

import {addParams, openSelectionModal} from 'frontend-js-web';

export default function ({
	currentURL,
	ddmTemplateId,
	editDDMTemplateURL,
	namespace,
	previewArticleContentTemplateURL,
	selectDDMTemplateURL,
}) {
	const eventHandlers = [];

	const namespaceId = (id) => `${namespace}${id}`;

	const changeDDMTemplate = (newDDMTemplate) => {
		if (newDDMTemplate && newDDMTemplate.ddmtemplateid === ddmTemplateId) {
			return;
		}

		if (
			confirm(
				Liferay.Language.get(
					'editing-the-current-template-deletes-all-unsaved-content'
				)
			)
		) {
			const newDDMTemplateId =
				(newDDMTemplate && newDDMTemplate.ddmtemplateid) || -1;

			const url = addParams(
				`${namespaceId('ddmTemplateId')}=${newDDMTemplateId}`,
				currentURL
			);

			const form = document.getElementById(namespaceId('fm1'));

			if (form) {
				Liferay.Util.setFormValues(form, {
					ddmTemplateId: newDDMTemplateId,
					ddmTemplateKey:
						(newDDMTemplate && newDDMTemplate.ddmtemplatekey) || '',
					ddmTemplateName:
						(newDDMTemplate && newDDMTemplate.name) || '',
				});

				form.action = url;

				form.submit();
			}
		}
	};

	const previewWithTemplateButton = document.getElementById(
		namespaceId('previewWithTemplate')
	);

	if (previewWithTemplateButton) {
		const previewWithTemplateButtonClick = () => {
			const url = new URL(previewArticleContentTemplateURL);

			url.searchParams.append(
				namespaceId('ddmTemplateId'),
				ddmTemplateId
			);

			url.searchParams.append(
				namespaceId('languageId'),
				getCurrentLanguage(namespace)
			);

			openSelectionModal({
				iframeBodyCssClass: '',
				onSelect: (selectedItem) => changeDDMTemplate(selectedItem),
				selectEventName: namespaceId('preview'),
				title: Liferay.Language.get('title'),
				url: url.href,
			});
		};

		previewWithTemplateButton.addEventListener(
			'click',
			previewWithTemplateButtonClick
		);

		eventHandlers.push({
			detach: () => {
				previewWithTemplateButton.removeEventListener(
					'click',
					previewWithTemplateButtonClick
				);
			},
		});
	}

	const clearDDMTemplateButton = document.getElementById(
		namespaceId('clearDDMTemplate')
	);

	if (clearDDMTemplateButton) {
		const clearDDMTemplateButtonClick = () => {
			changeDDMTemplate(null);
		};

		clearDDMTemplateButton.addEventListener(
			'click',
			clearDDMTemplateButtonClick
		);

		eventHandlers.push({
			detach: () => {
				clearDDMTemplateButton.removeEventListener(
					'click',
					clearDDMTemplateButtonClick
				);
			},
		});
	}

	const editDDMTemplateButton = document.getElementById(
		namespaceId('editDDMTemplate')
	);

	if (editDDMTemplateButton) {
		const editDDMTemplateButtonClick = () => {
			if (
				confirm(
					Liferay.Language.get(
						'editing-the-current-template-deletes-all-unsaved-content'
					)
				)
			) {
				Liferay.Util.navigate(editDDMTemplateURL);
			}
		};

		editDDMTemplateButton.addEventListener(
			'click',
			editDDMTemplateButtonClick
		);

		eventHandlers.push({
			detach: () => {
				editDDMTemplateButton.removeEventListener(
					'click',
					editDDMTemplateButton
				);
			},
		});
	}

	const selectDDMTemplateButton = document.getElementById(
		namespaceId('selectDDMTemplate')
	);

	if (selectDDMTemplateButton) {
		const selectDDMTemplateButtonClick = () => {
			openSelectionModal({
				onSelect: (selectedItem) => changeDDMTemplate(selectedItem),
				selectEventName: namespaceId('selectDDMTemplate'),
				title: Liferay.Language.get('templates'),
				url: selectDDMTemplateURL,
			});
		};

		selectDDMTemplateButton.addEventListener(
			'click',
			selectDDMTemplateButtonClick
		);

		eventHandlers.push({
			detach: () => {
				selectDDMTemplateButton.removeEventListener(
					'click',
					selectDDMTemplateButton
				);
			},
		});
	}

	return {
		dispose: () => {
			eventHandlers.forEach((eventHandler) => eventHandler.detach());
		},
	};
}

function getCurrentLanguage(namespace) {
	var inputComponent = Liferay.component(`${namespace}titleMapAsXML`);

	if (inputComponent) {
		return inputComponent.getSelectedLanguageId();
	}

	return themeDisplay.getLanguageId();
}
