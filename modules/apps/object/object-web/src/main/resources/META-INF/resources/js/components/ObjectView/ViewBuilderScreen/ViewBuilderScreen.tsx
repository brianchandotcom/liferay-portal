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

import {useModal} from '@clayui/modal';
import {BuilderScreen} from '@liferay/object-js-components-web';
import React, {useContext, useState} from 'react';

import {ModalEditViewColumn} from '../ModalEditViewColumn/ModalEditViewColumn';
import ViewContext, {TYPES} from '../context';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ViewBuilderScreen: React.FC<{}> = () => {
	const [visibleEditModal, setVisibleEditModal] = useState(false);
	const [editingObjectFieldName, setEditingObjectFieldName] = useState('');

	const {observer, onClose} = useModal({
		onClose: () => setVisibleEditModal(false),
	});

	const [
		{
			objectFields,
			objectView: {objectViewColumns},
		},
		dispatch,
	] = useContext(ViewContext);

	const objectFieldNames = new Set(
		objectViewColumns.map(({objectFieldName}) => objectFieldName)
	);

	const selected = objectFields.filter(({name}) =>
		objectFieldNames.has(name)
	);

	const handleAddColumns = () => {
		const parentWindow = Liferay.Util.getOpener();

		parentWindow.Liferay.fire('openModalAddColumns', {
			getName: ({label}: ObjectField) => label[defaultLanguageId],
			header: Liferay.Language.get('add-columns'),
			items: objectFields,
			onSave: (selectedObjectFields: ObjectField[]) =>
				dispatch({
					payload: {
						selectedObjectFields,
					},
					type: TYPES.ADD_OBJECT_VIEW_COLUMN,
				}),
			selected,
			title: Liferay.Language.get('select-the-columns'),
		});
	};

	const handleChangeColumnOrder = (
		draggedIndex: number,
		targetIndex: number
	) => {
		dispatch({
			payload: {draggedIndex, targetIndex},
			type: TYPES.CHANGE_OBJECT_VIEW_COLUMN_ORDER,
		});
	};

	const handleDeleteColumn = (objectFieldName: string) => {
		dispatch({
			payload: {objectFieldName},
			type: TYPES.DELETE_OBJECT_VIEW_COLUMN,
		});

		dispatch({
			payload: {objectFieldName},
			type: TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN,
		});
	};

	return (
		<>
			<BuilderScreen
				emptyState={{
					buttonText: Liferay.Language.get('add-column'),
					description: Liferay.Language.get(
						'add-columns-to-start-creating-a-view'
					),
					title: Liferay.Language.get('no-columns-added-yet'),
				}}
				firstColumnHeader={Liferay.Language.get('name')}
				hasDragAndDrop
				objectColumns={objectViewColumns ?? []}
				onChangeColumnOrder={handleChangeColumnOrder}
				onDeleteColumn={handleDeleteColumn}
				onEditingObjectFieldName={setEditingObjectFieldName}
				onVisibleEditModal={setVisibleEditModal}
				openModal={handleAddColumns}
				secondColumnHeader={Liferay.Language.get('column-label')}
				title={Liferay.Language.get('columns')}
			/>

			{visibleEditModal && (
				<ModalEditViewColumn
					editingObjectFieldName={editingObjectFieldName}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

export default ViewBuilderScreen;
