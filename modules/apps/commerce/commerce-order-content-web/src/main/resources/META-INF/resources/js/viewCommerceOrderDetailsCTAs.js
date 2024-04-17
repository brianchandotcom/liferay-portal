/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

function toggleModalTitleTooltip(isShow) {
	const tooltipElement = document.querySelector(
		'#returnable-items-header-tooltip'
	);

	tooltipElement.classList[isShow ? 'add' : 'remove']('show');
}

export default function ({namespace, viewReturnableCommerceOrderItemsURL}) {
	const formElement = document[`${namespace}fm`];
	const cmdInputElement = formElement[`${namespace}cmd`];

	Liferay.provide(window, `${namespace}handleCTA`, (cmdValue) => {
		if (cmdValue === 'makeReturn') {
			openModal({
				containerProps: {
					center: true,
				},
				headerHTML: `
					<div class="d-inline-flex flex-row align-items-center">
						<h1 class="modal-title" id="clay-modal-label-1">${Liferay.Language.get(
							'select-returnable-items'
						)}</h1>
						<span
							class="label-icon lfr-portal-tooltip ml-2 mt-1 small"
							id="returnable-items-header-tooltip-icon"
						>
							${Liferay.Util.getLexiconIconTpl('question-circle-full')}
						</span>
						<div id="returnable-items-header-tooltip" class="position-relative fade tooltip clay-tooltip-right" role="tooltip">
							<div class="arrow"></div>
							<div class="tooltip-inner small">${Liferay.Language.get(
								'only-items-that-support-returns-are-displayed-here'
							)}</div>
						</div>
					</div>
				`,
				height: '32rem',
				iframeBodyCssClass: 'w-100',
				onOpen: () => {
					const tooltipHeaderIcon = document.querySelector(
						'#returnable-items-header-tooltip-icon'
					);

					tooltipHeaderIcon.onmouseover = () =>
						toggleModalTitleTooltip(true);
					tooltipHeaderIcon.onmouseout = () =>
						toggleModalTitleTooltip(false);
				},
				size: 'lg',
				title: Liferay.Language.get('select-returnable-items'),
				url: viewReturnableCommerceOrderItemsURL,
			});
		}
		else {
			cmdInputElement.value = cmdValue;

			submitForm(formElement);
		}
	});
}
