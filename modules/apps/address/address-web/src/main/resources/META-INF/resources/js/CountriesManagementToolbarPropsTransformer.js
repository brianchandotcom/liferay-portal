/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const activateCountries = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					countryIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
				},
				url: itemData?.activateCountriesURL,
			});
		}
	};

	const deactivateCountries = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-deactivate-the-selected-countries'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				postForm(form, {
					data: {
						countryIds: getCheckedCheckboxes(
							form,
							`${portletNamespace}allRowIds`
						),
					},
					url: itemData?.deactivateCountriesURL,
				});
			}
		}
	};

	const deleteCountries = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-countries'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				postForm(form, {
					data: {
						countryIds: getCheckedCheckboxes(
							form,
							`${portletNamespace}allRowIds`
						),
					},
					url: itemData?.deleteCountriesURL,
				});
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'activateCountries') {
				activateCountries(data);
			}
			else if (action === 'deactivateCountries') {
				deactivateCountries(data);
			}
			else if (action === 'deleteCountries') {
				deleteCountries(data);
			}
		},
	};
}
