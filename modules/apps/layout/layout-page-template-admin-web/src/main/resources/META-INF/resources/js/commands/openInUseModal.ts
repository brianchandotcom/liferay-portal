/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, openModal, sub} from 'frontend-js-web';

type Props = {
	assetType: string;
	onCancel?: () => void;
	status: string;
	viewUsagesURL: string;
};

export default function openInUseModal({
	assetType,
	status,
	viewUsagesURL,
}: Props) {
	openModal({
		bodyHTML: `
			<p class="text-secondary">
				${sub(
					Liferay.Language.get(
						'the-content-type-cannot-be-changed-because-this-display-page-is-assigned-to-one-or-more-assets-with-the-type-x'
					),
					assetType
				)}
			</p>
			<p class="text-secondary">
				${sub(
					Liferay.Language.get(
						'to-change-the-content-type-unassign-this-display-page-from-the-assets-affected-and-try-again'
					),
					assetType
				)}
			</p>`,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: status,
				label: Liferay.Language.get('view-display-page-usages'),
				onClick: () => {
					navigate(viewUsagesURL);
				},
			},
		],
		status,
		title: Liferay.Language.get('display-page-in-use'),
	});
}
