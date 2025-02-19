/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useMemo} from 'react';

import {
	Field,
	FieldType,
	useStructureLabel,
} from '../../../structure_builder/contexts/StateContext';
import {FIELD_TYPE_ICON} from '../../../structure_builder/utils/fieldType';

type TreeItem = {
	children?: TreeItem[];
	icon: string;
	label: string;
	type?: FieldType;
};

export default function FieldsTree({fields}: {fields: Field[]}) {
	const structureLabel = useStructureLabel();

	const items: TreeItem[] = useMemo(() => {
		return [
			{
				children: fields.map((field) => ({
					...field,
					icon: FIELD_TYPE_ICON[field.type],
				})),
				icon: 'edit-layout',
				id: 'root',
				label: structureLabel,
			},
		];
	}, [fields, structureLabel]);

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
					<ClayTreeView.ItemStack>
						<ClayIcon symbol={item.icon} />

						<span className="ml-1">{item.label}</span>
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								className={classNames({
									'structure-builder__fields-tree-node--field':
										Boolean(item.type),
								})}
							>
								<ClayIcon symbol={item.icon} />

								<span className="ml-1">{item.label}</span>
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
}
