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

import {delegate, navigate} from 'frontend-js-web';

const createButton = ({action, buttonClass, label, type = 'submit'}) => {
	const wrapper = document.createElement('div');

	wrapper.classList.add('btn-group-item');

	const button = document.createElement('button');

	button.classList.add('add-category-toolbar-button', 'btn', buttonClass);
	button.dataset.action = action;
	button.textContent = label;
	button.type = type;

	wrapper.appendChild(button);

	return wrapper;
};

export default function ({currentURL, namespace, redirect}) {
	const formSheet = document.querySelector('.lfr-form-content .sheet');

	formSheet.classList.add('border-0');

	const openerWindow = Liferay.Util.getOpener();

	const modalTitle = openerWindow.document.querySelector('.modal-title');

	const initialModalTitle = modalTitle.textContent;

	modalTitle.textContent = `${
		modalTitle.textContent
	} - ${Liferay.Language.get('add-new')}`;

	const initialModalFooterButtons = openerWindow.document.querySelectorAll(
		'.liferay-modal .modal-footer .btn-group-item'
	);

	initialModalFooterButtons.forEach((item) => {
		item.classList.add('hide');
	});

	const footer = openerWindow.document.querySelector(
		'.modal-footer .btn-group'
	);

	const addCategoryButtons = footer.querySelectorAll(
		'.add-category-toolbar-button'
	);

	if (addCategoryButtons.length) {
		addCategoryButtons.forEach((button) => {
			button.parentElement.classList.remove('hide');
		});
	}
	else {
		const buttons = [
			createButton({
				action: 'cancel',
				buttonClass: 'btn-link',
				label: Liferay.Language.get('cancel'),
				type: 'button',
			}),

			createButton({
				action: 'saveAndAddNew',
				buttonClass: 'btn-secondary',
				label: Liferay.Language.get('save-and-add-a-new-one'),
			}),

			createButton({
				action: 'save',
				buttonClass: 'btn-primary',
				label: Liferay.Language.get('save'),
			}),
		];

		buttons.forEach((button) => footer.appendChild(button));
	}

	const delegateHandler = delegate(
		footer,
		'click',
		'.add-category-toolbar-button',
		(event) => {
			const delegateTarget = event.delegateTarget;

			modalTitle.textContent = initialModalTitle;

			initialModalFooterButtons.forEach((item) => {
				item.classList.remove('hide');
			});

			const hideAddCategoryButtons = () => {
				footer
					.querySelectorAll('.add-category-toolbar-button')
					.forEach((button) =>
						button.parentElement.classList.add('hide')
					);
			};

			const action = delegateTarget.dataset.action;

			if (action === 'cancel') {
				hideAddCategoryButtons();

				navigate(redirect);
			}
			else if (action === 'saveAndAddNew') {
				document.getElementById(
					`${namespace}redirect`
				).value = currentURL;

				submitForm(document.getElementById(`${namespace}fm`));
			}
			else if (action === 'save') {
				hideAddCategoryButtons();

				submitForm(document.getElementById(`${namespace}fm`));
			}
		}
	);

	return {
		dispose: () => delegateHandler.dispose(),
	};
}
