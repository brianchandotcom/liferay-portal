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

import {ModalAddFilterColumn} from '../ModalAddFilterColumn/ModalAddFilterColumn';
import ViewContext, {TYPES} from '../context';

export function FilterScreen() {
	const [{objectView}, dispatch] = useContext(ViewContext);

	const {objectViewFilterColumns} = objectView;

	const [editingObjectFieldName, setEditingObjectFieldName] = useState('');
	const [editingFilter, setEditingFilter] = useState(false);

	const [visibleModal, setVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setEditingFilter(false);
			setVisibleModal(false);
		},
	});

	const handleDeleteColumn = (objectFieldName: string) => {
		dispatch({
			payload: {objectFieldName},
			type: TYPES.DELETE_OBJECT_VIEW_FILTER_COLUMN,
		});
	};

	return (
		<>
			<BuilderScreen
				disableEdit={(businessType: string) =>
					businessType !== 'Picklist' &&
					businessType !== 'Workflow Status'
				}
				emptyState={{
					buttonText: Liferay.Language.get('new-filter'),
					description: Liferay.Language.get(
						'start-creating-a-filter-to-display-specific-data'
					),
					title: Liferay.Language.get('no-filter-was-created-yet'),
				}}
				filter
				firstColumnHeader={Liferay.Language.get('filter-by')}
				objectColumns={objectViewFilterColumns ?? []}
				onDeleteColumn={handleDeleteColumn}
				onEditing={setEditingFilter}
				onEditingObjectFieldName={setEditingObjectFieldName}
				onVisibleEditModal={setVisibleModal}
				openModal={() => setVisibleModal(true)}
				secondColumnHeader={Liferay.Language.get('type')}
				thirdColumnHeader={Liferay.Language.get('value')}
				title={Liferay.Language.get('filters')}
			/>

			{visibleModal && (
				<ModalAddFilterColumn
					editingFilter={editingFilter}
					editingObjectFieldName={editingObjectFieldName}
					header={Liferay.Language.get('new-filter')}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
}
