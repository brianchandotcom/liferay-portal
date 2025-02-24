/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openModal from './openModal';

import type {EventHandler} from '../types';

export interface OpenPortletModalProps {
	containerProps?: {};
	footerCssClass?: string;
	headerCssClass?: string;
	iframeBodyCssClass?: string;
	onClose?: EventHandler;
	portletSelector?: string;
	subTitle?: string;
	title?: string;
	url?: string;
}

export default function openPortletModal({
	containerProps,
	footerCssClass,
	headerCssClass,
	iframeBodyCssClass,
	onClose,
	portletSelector,
	subTitle,
	title,
	url,
}: OpenPortletModalProps) {
	const portlet = document.querySelector(portletSelector ?? '');

	if (portlet && url) {
		const titleElement =
			portlet.querySelector('.portlet-title') ||
			portlet.querySelector('.portlet-title-default');

		if (titleElement) {
			if (portlet.querySelector('#cpPortletTitle')) {
				const titleTextElement = titleElement.querySelector(
					'.portlet-title-text'
				);

				if (titleTextElement) {
					title = `${titleTextElement.outerHTML} - ${title}`;
				}
			}
			else {
				title = `${titleElement.textContent} - ${title}`;
			}
		}

		let headerHTML;

		if (subTitle) {
			headerHTML = `${title}<div class="portlet-configuration-subtitle small"><span class="portlet-configuration-subtitle-text">${subTitle}</span></div>`;
		}

		openModal({
			containerProps,
			footerCssClass,
			headerCssClass,
			headerHTML,
			iframeBodyCssClass,
			onClose,
			title,
			url,
		});
	}
}
