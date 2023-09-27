/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	postForm,
} from 'frontend-js-web';
export default function propsTransformer({
	additionalProps: {
		activateResultsRankingEntryURL,
		deactivateResultsRankingEntryURL,
		deleteResultsRankingEntryURL,
	},
	portletNamespace,
	...otherProps
}) {
	const activateResultsRankingsEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		const searchContainer = document.getElementById(
			`${portletNamespace}resultsRankingEntries`
		);

		if (form && searchContainer) {
			postForm(form, {
				data: {
					actionFormInstanceIds: getCheckedCheckboxes(
						searchContainer,
						`${portletNamespace}allRowIds`
					),
				},
				url: activateResultsRankingEntryURL,
			});
		}
	};

	const deactivateResultsRankingsEntries = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		const searchContainer = document.getElementById(
			`${portletNamespace}resultsRankingEntries`
		);

		if (form && searchContainer) {
			postForm(form, {
				data: {
					actionFormInstanceIds: getCheckedCheckboxes(
						searchContainer,
						`${portletNamespace}allRowIds`
					),
				},
				url: deactivateResultsRankingEntryURL,
			});
		}
	};

	const deleteResultsRankingsEntries = () => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					const searchContainer = document.getElementById(
						`${portletNamespace}resultsRankingEntries`
					);

					if (form && searchContainer) {
						postForm(form, {
							data: {
								actionFormInstanceIds: getCheckedCheckboxes(
									searchContainer,
									`${portletNamespace}allRowIds`
								),
							},
							url: deleteResultsRankingEntryURL,
						});
					}
				}
			},
		});
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'activateResultsRankingsEntries') {
				activateResultsRankingsEntries();
			}
			else if (action === 'deactivateResultsRankingsEntries') {
				deactivateResultsRankingsEntries();
			}
			else if (action === 'deleteResultsRankingsEntries') {
				deleteResultsRankingsEntries();
			}
		},
	};
}
