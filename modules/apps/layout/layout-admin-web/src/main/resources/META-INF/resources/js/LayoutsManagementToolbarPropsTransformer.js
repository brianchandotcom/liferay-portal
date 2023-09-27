/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, navigate, openConfirmModal} from 'frontend-js-web';

import openDeleteLayoutModal from './openDeleteLayoutModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const convertSelectedPages = (itemData) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-convert-the-selected-pages'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(form, itemData?.convertLayoutURL);
					}
				}
			},
		});
	};

	const deleteSelectedPages = (itemData) => {
		openDeleteLayoutModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-pages-if-the-selected-pages-have-child-pages-they-will-also-be-removed'
			),
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form, itemData?.deleteLayoutURL);
				}
			},
		});
	};

	const exportTranslation = ({exportTranslationURL}) => {
		const keys = Array.from(
			document.querySelectorAll(
				`[name=${portletNamespace}rowIds]:checked`
			)
		).map(({value}) => value);

		const url = new URL(exportTranslationURL);

		navigate(
			addParams(
				{
					[`_${url.searchParams.get('p_p_id')}_classPK`]: keys.join(
						','
					),
				},
				exportTranslationURL
			)
		);
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'convertSelectedPages') {
				convertSelectedPages(data);
			}
			else if (action === 'deleteSelectedPages') {
				deleteSelectedPages(data);
			}
			else if (action === 'exportTranslation') {
				exportTranslation(data);
			}
		},
	};
}
