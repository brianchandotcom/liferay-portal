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

import {navigate} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const convertSelectedPages = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-convert-the-selected-pages'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form, itemData?.convertLayoutURL);
			}
		}
	};

	const deleteSelectedPages = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-pages-if-the-selected-pages-have-child-pages-they-will-also-be-removed'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form, itemData?.deleteLayoutURL);
			}
		}
	};

	const exportTranslation = ({exportTranslationURL}) => {
		const url = new URL(exportTranslationURL);

		const urlSearchParams = new URLSearchParams(url.search);

		const paramName = `_${urlSearchParams.get('p_p_id')}_classPK`;

		const nodes = Array.from(
			document.getElementsByName(`${portletNamespace}rowIds`)
		);

		nodes.forEach((node) => {
			if (node.checked) {
				url.searchParams.append(paramName, node.value);
			}
		});

		navigate(url.toString());
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
