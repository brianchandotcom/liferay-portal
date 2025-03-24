/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {fetchJSON} from '../../../api/api';
import {executeAsyncItemAction} from '../utils/executeAsyncItemAction';

async function deleteStructureToast({
	method = 'DELETE',
	name,
	refreshData,
	url,
}: {
	method?: string;
	name: string;
	refreshData: () => {};
	url: string;
}) {
	await executeAsyncItemAction({
		method,
		refreshData,
		successMessage: sub(
			Liferay.Language.get('x-was-deleted-successfully'),
			`<strong>${Liferay.Util.escapeHTML(name)}</strong>`
		),
		url,
	});
}

export default async function deleteStructureAction({
	deleteAction,
	getObjectDefinitionDeleteInfoURL,
	loadData,
	name,
	status,
}: {
	deleteAction: {href: string; method: string};
	getObjectDefinitionDeleteInfoURL: string;
	loadData: () => {};
	name: string;
	status: number;
}) {
	if (status !== 0) {
		await deleteStructureToast({
			method: deleteAction.method,
			name,
			refreshData: loadData,
			url: deleteAction.href,
		});

		return;
	}

	const {hasObjectRelationship, objectEntriesCount} = await fetchJSON<{
		hasObjectRelationship: boolean;
		objectEntriesCount: number;
	}>(getObjectDefinitionDeleteInfoURL);

	if (hasObjectRelationship) {
		openModal({
			bodyHTML: `<p>${sub(
				Liferay.Language.get(
					'x-is-currently-referenced-by-or-referencing-other-structures-and-cannot-be-deleted'
				),
				`<strong>${Liferay.Util.escapeHTML(name)}</strong>`
			)}</p>`,
			buttons: [
				{
					displayType: 'warning',
					label: Liferay.Language.get('ok'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();
					},
				},
			],
			size: 'md',
			status: 'warning',
			title: Liferay.Language.get('deletion-not-allowed'),
		});
	}
	else {

		// TODO: show the confirmation modal and delete after confirmation

	}
}
