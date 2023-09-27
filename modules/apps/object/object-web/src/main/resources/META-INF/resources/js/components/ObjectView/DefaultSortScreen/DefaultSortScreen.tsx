/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {useModal} from '@clayui/modal';
import {BuilderScreen, Card} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {ModalAddDefaultSortColumn} from '../ModalAddDefaultSortColumn/ModalAddDefaultSortColumn';
import {TYPES, useViewContext} from '../objectViewContext';

export function DefaultSortScreen() {
	const [
		{
			objectView: {objectViewSortColumns},
		},
		dispatch,
	] = useViewContext();

	const [visibleModal, setVisibleModal] = useState(false);
	const [isEditingSort, setIsEditingSort] = useState(false);
	const [editingObjectFieldName, setEditingObjectFieldName] = useState('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		visibleModal === false && setIsEditingSort(false);
	}, [visibleModal]);

	const handleChangeColumnOrder = (
		draggedIndex: number,
		targetIndex: number
	) => {
		dispatch({
			payload: {draggedIndex, targetIndex},
			type: TYPES.CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER,
		});
	};

	const handleDeleteColumn = (objectFieldName: string) => {
		dispatch({
			payload: {objectFieldName},
			type: TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN,
		});
	};

	return (
		<>
			<ClayAlert
				className="lfr-objects__side-panel-content-container"
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
			>
				{Liferay.Language.get(
					'the-hierarchy-of-the-default-sorting-will-be-defined-by-the-vertical-order-of-the-fields'
				)}
			</ClayAlert>

			<Card title={Liferay.Language.get('default-sort')}>
				<BuilderScreen
					defaultSort
					emptyState={{
						buttonText: Liferay.Language.get('new-default-sort'),
						description: Liferay.Language.get(
							'start-creating-a-sort-to-display-specific-data'
						),
						title: Liferay.Language.get(
							'no-default-sort-was-created-yet'
						),
					}}
					firstColumnHeader={Liferay.Language.get('name')}
					hasDragAndDrop
					objectColumns={objectViewSortColumns ?? []}
					onChangeColumnOrder={handleChangeColumnOrder}
					onDeleteColumn={handleDeleteColumn}
					onEditing={setIsEditingSort}
					onEditingObjectFieldName={setEditingObjectFieldName}
					onVisibleEditModal={setVisibleModal}
					openModal={() => setVisibleModal(true)}
					secondColumnHeader={Liferay.Language.get('sorting')}
				/>
			</Card>

			{visibleModal && (
				<ModalAddDefaultSortColumn
					editingObjectFieldName={editingObjectFieldName}
					header={
						isEditingSort
							? Liferay.Language.get('edit-default-sort')
							: Liferay.Language.get('new-default-sort')
					}
					isEditingSort={isEditingSort}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
}
