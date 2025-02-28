/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useMemo} from 'react';

import {
	FIELD_TYPE_ICON,
	Field,
	FieldType,
} from '../../../structure_builder/utils/field';
import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectStructureLabel from '../../selectors/selectStructureLabel';

type TreeItem = {
	children?: TreeItem[];
	icon: string;
	id: string;
	label: string;
	name?: string;
	type?: FieldType;
};

export default function FieldsTree({fields}: {fields: Field[]}) {
	const dispatch = useStateDispatch();
	const structureLabel = useSelector(selectStructureLabel);

	const items: TreeItem[] = useMemo(() => {
		return [
			{
				children: fields.map((field) => ({
					icon: FIELD_TYPE_ICON[field.type],
					id: field.name,
					label: field.label[
						Liferay.ThemeDisplay.getDefaultLanguageId()
					]!,
					type: field.type,
				})),
				icon: 'edit-layout',
				id: 'root',
				label: structureLabel,
			},
		];
	}, [fields, structureLabel]);

	const onItemClick = (item: TreeItem) => {
		if (item.id === 'root') {
			dispatch({
				selection: [],
				type: 'set-selection',
			});
		}
		else {
			dispatch({
				selection: [item.id],
				type: 'set-selection',
			});
		}
	};

	const deleteField = (fieldName: string) =>
		dispatch({
			fieldName,
			type: 'delete-field',
		});

	return (
		<ClayTreeView
			className="structure-builder__fields-tree"
			defaultExpandedKeys={new Set(['root'])}
			items={items}
			nestedKey="children"
			showExpanderOnHover={false}
		>
			{(item) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack onClick={() => onItemClick(item)}>
						<ClayIcon symbol={item.icon} />

						<span className="ml-1">{item.label}</span>
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								actions={
									<ClayDropDownWithItems
										items={[
											{
												label: Liferay.Language.get(
													'delete-field'
												),
												onClick: () =>
													deleteField(item.id),
												symbolLeft: 'trash',
											},
										]}
										trigger={
											<ClayButtonWithIcon
												aria-label={Liferay.Language.get(
													'field-options'
												)}
												borderless
												displayType="unstyled"
												size="sm"
												symbol="ellipsis-v"
											/>
										}
									/>
								}
								onClick={() => onItemClick(item)}
							>
								<ClayIcon
									className={classNames({
										'structure-builder__fields-tree-node--field-icon':
											Boolean(item.type),
									})}
									symbol={item.icon}
								/>

								<span className="ml-1">{item.label}</span>
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
}
