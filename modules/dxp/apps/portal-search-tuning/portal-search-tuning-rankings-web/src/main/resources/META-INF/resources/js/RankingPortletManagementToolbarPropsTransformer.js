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

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

import {openConfirmModal} from 'frontend-js-web'

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
