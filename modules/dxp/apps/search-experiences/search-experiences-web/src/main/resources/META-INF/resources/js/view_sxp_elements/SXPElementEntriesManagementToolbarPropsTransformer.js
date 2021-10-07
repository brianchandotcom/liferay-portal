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

export default function propsTransformer({
	additionalProps: {deleteElementURL, hideElementURL, showElementURL},
	portletNamespace,
	...otherProps
}) {
	var deleteEntries = function () {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-elements')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			const searchContainer = document.getElementById(
				`${portletNamespace}elementEntries`
			);

			if (form && searchContainer) {
				postForm(form, {
					data: {
						actionFormInstanceIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							`${portletNamespace}allRowIds`
						),
					},
					url: deleteElementURL,
				});
			}
		}
	};

	var showHideEntries = function (url) {
		const form = document.getElementById(`${portletNamespace}fm`);

		const searchContainer = document.getElementById(
			`${portletNamespace}elementEntries`
		);

		if (form && searchContainer) {
			postForm(form, {
				data: {
					actionFormInstanceIds: Liferay.Util.listCheckedExcept(
						searchContainer,
						`${portletNamespace}allRowIds`
					),
				},
				url,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteEntries') {
				deleteEntries();
			}

			if (action === 'hideEntries') {
				showHideEntries(hideElementURL);
			}

			if (action === 'showEntries') {
				showHideEntries(showElementURL);
			}
		},
	};
}
