/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';
import {openModal, openToast} from 'frontend-js-components-web';
import {fetch, sub} from 'frontend-js-web';

import {IVocabulary} from '../categorization/types/IVocabulary';
import VocabularyRenderer from './cell_renderers/VocabularyRenderer';

export default function VocabularyFDSPropsTransformer({
	...otherProps
}: {
	otherProps: any;
}) {
	const openConfirmationModal = (itemData: IVocabulary, loadData: any) => {
		openModal({
			bodyHTML: Liferay.Language.get('delete-vocabulary-confirmation'),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						const deleteMethod = itemData.actions?.delete?.method;
						const deleteURL = itemData.actions?.delete?.href;

						if (deleteMethod && deleteURL) {
							fetch(deleteURL, {
								headers: {
									'Accept': 'application/json',
									'Content-Type': 'application/json',
									'x-csrf-token': Liferay.authToken,
								},
								method: deleteMethod,
							})
								.then(() => {
									openToast({
										message: Liferay.Language.get(
											'your-request-completed-successfully'
										),
										title: Liferay.Language.get('success'),
										type: 'success',
									});

									loadData();
								})
								.catch(() => {
									openToast({
										message: Liferay.Language.get(
											'an-unexpected-error-occurred'
										),
										title: Liferay.Language.get('error'),
										type: 'danger',
									});
								});
						}
						else {
							openToast({
								message: Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
								title: Liferay.Language.get('error'),
								type: 'danger',
							});
						}
					},
				},
			],
			status: 'danger',
			title: sub(
				Liferay.Language.get('delete-x'),
				'"' + itemData.name + '"'
			),
		});
	};

	return {
		...otherProps,
		customRenderers: {
			tableCell: [
				{
					component: VocabularyRenderer,
					name: 'customVocabularyRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
		onActionDropdownItemClick({
			action,
			itemData,
			loadData,
		}: {
			action: {data: {id: string}};
			itemData: IVocabulary;
			loadData: any;
		}) {
			if (action.data.id === 'delete') {
				openConfirmationModal(itemData, loadData);
			}
		},
	};
}
