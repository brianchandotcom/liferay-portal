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

import openAddSXPBlueprintModal from './OpenAddSXPBlueprintModalCommand';

export default function propsTransformer({
	additionalProps: {deleteSXPBlueprintURL},
	portletNamespace,
	...otherProps
}) {
	const addSXPBlueprint = (itemData) => {
		openAddSXPBlueprintModal({
			contextPath: itemData.contextPath,
			defaultLocale: itemData.defaultLocale,
			dialogTitle: Liferay.Language.get('new-search-blueprint'),
			namespace: portletNamespace,
		});
	};

	const deleteSXPBlueprints = function () {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-blueprints'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			const searchContainer = document.getElementById(
				`${portletNamespace}sxpBlueprintEntries`
			);

			if (form && searchContainer) {
				postForm(form, {
					data: {
						actionFormInstanceIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							`${portletNamespace}allRowIds`
						),
					},
					url: deleteSXPBlueprintURL,
				});
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteSXPBlueprints') {
				deleteSXPBlueprints();
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item?.data;

			addSXPBlueprint(data);
		},
	};
}
