/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {postForm} from 'frontend-js-web';

import openAddSXPElementModal from './OpenAddSXPElementModalCommand';

export default function propsTransformer({
	additionalProps: {
		deleteSXPElementURL,
		hideSXPElementURL,
		showSXPElementURL,
	},
	portletNamespace,
	...otherProps
}) {
	const addSXPElement = (itemData) => {
		openAddSXPElementModal({
			defaultLocale: itemData.defaultLocale,
			dialogTitle: Liferay.Language.get('new-search-element'),
			namespace: portletNamespace,
			type: itemData.type,
		});
	};

	const deleteSXPElements = function () {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-elements')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			const searchContainer = document.getElementById(
				`${portletNamespace}sxpElementEntries`
			);

			if (form && searchContainer) {
				postForm(form, {
					data: {
						actionFormInstanceIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							`${portletNamespace}allRowIds`
						),
					},
					url: deleteSXPElementURL,
				});
			}
		}
	};

	const hideSXPElements = function () {
		const form = document.getElementById(`${portletNamespace}fm`);

		const searchContainer = document.getElementById(
			`${portletNamespace}sxpElementEntries`
		);

		if (form && searchContainer) {
			postForm(form, {
				data: {
					actionFormInstanceIds: Liferay.Util.listCheckedExcept(
						searchContainer,
						`${portletNamespace}allRowIds`
					),
				},
				hideSXPElementURL,
			});
		}
	};

	const showSXPElements = function () {
		const form = document.getElementById(`${portletNamespace}fm`);

		const searchContainer = document.getElementById(
			`${portletNamespace}sxpElementEntries`
		);

		if (form && searchContainer) {
			postForm(form, {
				data: {
					actionFormInstanceIds: Liferay.Util.listCheckedExcept(
						searchContainer,
						`${portletNamespace}allRowIds`
					),
				},
				showSXPElementURL,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteSXPElements') {
				deleteSXPElements();
			}

			if (action === 'hideSXPElements') {
				hideSXPElements();
			}

			if (action === 'showSXPElements') {
				showSXPElements();
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item?.data;

			addSXPElement(data);
		},
	};
}
