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

import {fetch, objectToFormData} from 'frontend-js-web';

/**
 * Observes anchorElement position and updates overlayElement to match until
 * dispose callback is executed.
 * @param {HTMLElement} anchorElement
 * @param {HTMLElement} overlayElement
 * @param {object} [options={}]
 * @param {boolean} [options.matchHeight=false] Match overlayElement height too.
 * @param {boolean} [options.matchWidth=false] Match overlayElement width too.
 * @param {number} [options.updateInterval=2000] How ofter overlayElement should
 *  be updated (at least).
 * @return {{dispose: function}}
 */
function linkPosition(
	anchorElement,
	overlayElement,
	{matchHeight, matchWidth, updateInterval} = {
		matchHeight: false,
		matchWidth: false,
		updateInterval: 2000,
	}
) {
	const updateAnchorElement = () => {
		const {
			height,
			left,
			top,
			width,
		} = anchorElement.getBoundingClientRect();

		overlayElement.style.left = `${left}px`;
		overlayElement.style.top = `${top}px`;

		if (matchHeight) {
			overlayElement.style.height = `${height}px`;
		}

		if (matchWidth) {
			overlayElement.style.width = `${width}px`;
		}
	};

	if (matchWidth || matchHeight) {
		overlayElement.style.boxSizing = 'border-box';
	}

	overlayElement.style.position = 'fixed';
	overlayElement.style.zIndex = '1';

	const updateIntervalId = setInterval(updateAnchorElement, updateInterval);
	window.addEventListener('resize', updateAnchorElement);
	requestAnimationFrame(updateAnchorElement);

	return {
		dispose: () => {
			clearInterval(updateIntervalId);
			window.removeEventListener('resize', updateAnchorElement);
		},
	};
}

/**
 * Creates and returns an overlay element for the given column element
 * @param {HTMLElement} columnElement
 * @returns {HTMLElement}
 */
function createOverlayElement(columnElement) {
	const customizable = Boolean(
		columnElement.querySelector('.portlet-column-content.customizable')
	);

	const overlayElement = document.createElement('div');

	overlayElement.innerHTML = `
		<div class="${customizable ? 'customizable' : ''} h-100">
			<div class="layout-customizable-controls">
				<div
					class="d-inline-block lfr-portal-tooltip"
					title="${Liferay.Language.get('customizable-help')}"
				>
					<label class="toggle-switch">
						<span class="toggle-switch-check-bar">
							<input
								class="toggle-switch-check"
								id="TypeSettingsProperties--${columnElement.id}-customizable--"
								name="TypeSettingsProperties--${columnElement.id}-customizable--"
								type="checkbox"
								${customizable ? 'checked' : ''}
							/>
	
							<span aria-hidden="true" class="toggle-switch-bar">
								<span
									class="toggle-switch-handle"
									data-label-off="${Liferay.Language.get('not-customizable')}"
									data-label-on="${Liferay.Language.get('customizable')}"
								></span>
							</span>
						</span>
					</label>
					<svg class="d-inline-block lexicon-icon lexicon-icon-question-circle-full small text-white" role="presentation">
						<use xlink:href="${themeDisplay.getPathThemeImages()}/lexicon/icons.svg#question-circle-full" />
					</svg>
				</div>
			</div>
			<div
				class="customizable-layout-column-content h-100"
				style="opacity: 0.5;"
			></div>
		</div>
	`;

	const checkboxElement = overlayElement.querySelector('input');
	const dropzoneElement = columnElement.querySelector('.portlet-dropzone');
	const wrapperElement = overlayElement.firstElementChild;

	checkboxElement.addEventListener('change', () => {
		dropzoneElement.classList.toggle(
			'portlet-dropzone-disabled',
			!checkboxElement.checked
		);

		dropzoneElement.classList.toggle(
			'customizable',
			checkboxElement.checked
		);

		wrapperElement.classList.toggle(
			'customizable',
			checkboxElement.checked
		);

		fetch(themeDisplay.getPathMain() + '/portal/update_layout', {
			body: objectToFormData({
				cmd: 'update_type_settings',
				doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
				p_auth: Liferay.authToken,
				p_l_id: themeDisplay.getPlid(),
				p_v_l_s_g_id: themeDisplay.getSiteGroupId(),
				[checkboxElement.name]: checkboxElement.checked,
			}),
			method: 'POST',
		}).then(() => {
			Liferay.publish('updatedLayout', {defaultFn() {}});
		});
	});

	return overlayElement;
}

export default function LayoutCustomizationSettings() {
	let overlayList;

	const createOverlay = () => {
		overlayList = Array.from(
			document.querySelectorAll('.portlet-column')
		).map((columnElement) => {
			const overlayElement = createOverlayElement(columnElement);

			columnElement
				.querySelector('.portlet-dropzone')
				.appendChild(overlayElement);

			const overlay = linkPosition(
				columnElement.querySelector('.portlet-column-content'),
				overlayElement,
				{
					matchHeight: true,
					matchWidth: true,
				}
			);

			return {
				overlay,
				overlayElement,
			};
		});
	};

	const disposeOverlay = () => {
		overlayList?.forEach(({overlay, overlayElement}) => {
			overlay.dispose();
			overlayElement.parentElement.removeChild(overlayElement);
		});

		overlayList = [];
	};

	const manageCustomizationCheckbox = document.querySelector(
		'input[name="manageCustomization"]'
	);

	const handleCustomizationChange = () => {
		if (manageCustomizationCheckbox.checked) {
			createOverlay();
		}
		else {
			disposeOverlay();
		}
	};

	manageCustomizationCheckbox?.addEventListener(
		'change',
		handleCustomizationChange
	);

	return {
		dispose() {
			disposeOverlay();

			manageCustomizationCheckbox?.removeEventListener(
				'change',
				handleCustomizationChange
			);
		},
	};
}
