/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from '@liferay/layout-js-components-web';

import {FormStepLayoutDataItem} from '../../types/layout_data/FormStepLayoutDataItem';
import {LayoutData} from '../../types/layout_data/LayoutData';
import {Dispatch} from '../contexts/StoreContext';
import removeFormStepThunk from '../thunks/removeFormStep';
import {getFormParent} from './getFormParent';

type Props = {
	dispatch: Dispatch;
	item: FormStepLayoutDataItem;
	layoutData: LayoutData;
	selectItem: (id: string) => void;
};

export default function removeFormStep({
	dispatch,
	item,
	layoutData,
	selectItem,
}: Props) {
	const form = getFormParent(item, layoutData);

	if (!form) {
		return;
	}

	const index = layoutData.items[item.parentId]?.children.indexOf(
		item.itemId
	);

	const numberOfSteps = form.config.numberOfSteps;

	const executeAction = async () =>
		dispatch(
			removeFormStepThunk({
				index,
				itemId: item.itemId,
				selectItem,
			})
		);

	if (numberOfSteps === 2) {
		openConfirmModal({
			buttonLabel: Liferay.Language.get('remove-and-convert'),
			onConfirm: executeAction,
			status: 'info',
			text: Liferay.Language.get(
				'removing-this-step-will-convert-your-multistep-form-into-a-simple-form'
			),
			title: Liferay.Language.get(
				'remove-step-and-convert-to-simple-form'
			),
		});
	}
	else {
		executeAction();
	}
}
