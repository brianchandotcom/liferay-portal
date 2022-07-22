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
