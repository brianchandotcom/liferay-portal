/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {Dispatch} from '../contexts/StoreContext';
import {addFragment} from '../js-index';

type Props = {
	dispatch: Dispatch;
	formId: string;
};

export function openAddLocalizationSelect({dispatch, formId}: Props) {
	const title = sub(
		Liferay.Language.get('add-x'),
		Liferay.Language.get('localization-select')
	);

	openConfirmModal({
		buttonLabel: title,
		onConfirm: async () =>
			dispatch(
				addFragment({
					fieldTypes: ['localizationSelect'],
					fragmentEntryKey: 'localization-select',
					itemType: LAYOUT_DATA_ITEM_TYPES.fragment,
					parentItemId: formId,
					type: 'input',
				})
			),
		status: 'info',
		text: Liferay.Language.get(
			'at-least-one-localizable-form-field-has-been-added-to-the-page'
		),
		title,
	});
}
