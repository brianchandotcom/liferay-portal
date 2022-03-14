/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import {useModal} from '@clayui/modal';
import React, {useContext, useEffect, useState} from 'react';

import {BuilderScreen} from '../BuilderScreen/BuilderScreen';
import {ModalAddDefaultSortColumn} from '../ModalAddDefaultSortColumn/ModalAddDefaultSortColumn';
import ViewContext from '../context';

export function DefaultSortScreen() {
	const [
		{
			objectView: {objectViewSortColumns},
		},
	] = useContext(ViewContext);

	const [visibleModal, setVisibleModal] = useState(false);
	const [isEditingSort, setIsEditingSort] = useState(false);
	const [editingObjectFieldName, setEditingObjectFieldName] = useState('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		visibleModal === false && setIsEditingSort(false);
	}, [visibleModal]);

	return (
		<>
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
			>
				{Liferay.Language.get(
					'the-hierarchy-of-the-default-sorting-will-be-defined-by-the-vertical-order-of-the-fields'
				)}
			</ClayAlert>

			<BuilderScreen
				emptyState={{
					buttonText: Liferay.Language.get('new-default-sort'),
					description: Liferay.Language.get(
						'start-creating-a-sort-to-display-specific-data'
					),
					title: Liferay.Language.get(
						'no-default-sort-was-created-yet'
					),
				}}
				isDefaultSort
				objectColumns={objectViewSortColumns ?? []}
				onEditing={setIsEditingSort}
				onEditingObjectFieldName={setEditingObjectFieldName}
				onVisibleEditModal={setVisibleModal}
				onVisibleModal={setVisibleModal}
				secondColumnHeader={Liferay.Language.get('sorting')}
				title={Liferay.Language.get('default-sort')}
			/>

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
