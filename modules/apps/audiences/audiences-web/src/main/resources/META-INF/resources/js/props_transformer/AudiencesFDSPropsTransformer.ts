/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {openConfirmModal, openToast} from 'frontend-js-components-web';
import {fetch, navigate, sub} from 'frontend-js-web';

interface DeleteActionData {
	deleteURL: string;
	getAudiencesEntryUsagesURL: string;
	id: string;
}

function getConfirmationMessage(elementVariationsCount: number) {
	if (elementVariationsCount === 1) {
		return Liferay.Language.get(
			'this-audience-is-used-in-one-element-variation.-deleting-it-will-remove-it-from-that-variation.-are-you-sure-you-want-to-delete-it'
		);
	}

	if (elementVariationsCount > 1) {
		return sub(
			Liferay.Language.get(
				'this-audience-is-used-in-x-element-variations.-deleting-it-will-remove-it-from-those-variations.-are-you-sure-you-want-to-delete-it'
			),
			elementVariationsCount
		);
	}

	return Liferay.Language.get('are-you-sure-you-want-to-delete-this');
}

async function deleteAudiencesEntry({
	deleteURL,
	getAudiencesEntryUsagesURL,
}: DeleteActionData) {
	let elementVariationsCount: number;

	try {
		const response = await fetch(getAudiencesEntryUsagesURL);

		if (!response.ok) {
			throw new Error(response.statusText);
		}

		const json = await response.json();

		elementVariationsCount = json.elementVariationsCount;
	}
	catch (error) {
		openToast({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			type: 'danger',
		});

		return;
	}

	openConfirmModal({
		message: getConfirmationMessage(elementVariationsCount),
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				navigate(deleteURL);
			}
		},
		status: elementVariationsCount ? 'warning' : undefined,
	});
}

export default function AudiencesFDSPropsTransformer(
	props: IFrontendDataSetProps
): IFrontendDataSetProps {
	return {
		...props,
		onActionDropdownItemClick: ({
			action,
		}: {
			action: {data: DeleteActionData};
		}) => {
			if (action.data.id === 'delete') {
				deleteAudiencesEntry(action.data);
			}
		},
	};
}
