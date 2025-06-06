/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useMemo, useState} from 'react';

import {
	FIELD_TYPE_ICON,
	Field,
	FieldType,
} from '../../structure_builder/utils/field';
import {State, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectInvalids from '../selectors/selectInvalids';
import selectSelection from '../selectors/selectSelection';
import selectStructureError from '../selectors/selectStructureError';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureUuid from '../selectors/selectStructureUuid';
import {Uuid} from '../types/Uuid';

type TreeItem = {
	children?: TreeItem[];
	icon: string;
	id: Uuid;
	label: string;
	name?: string;
	type?: FieldType;
};

export default function FieldsTree({fields}: {fields: Field[]}) {
	const dispatch = useStateDispatch();

	const invalids = useSelector(selectInvalids);
	const selection = useSelector(selectSelection);
	const structureLabel = useSelector(selectStructureLocalizedLabel);
	const structureUuid = useSelector(selectStructureUuid);
	const structureError = useSelector(selectStructureError);

	const mode = useSelectionMode();

	const items: TreeItem[] = useMemo(() => {
		return [
			{
				children: fields.map((field) => ({
					icon: FIELD_TYPE_ICON[field.type],
					id: field.uuid,
					label: field.label[
						Liferay.ThemeDisplay.getDefaultLanguageId()
					]!,
					type: field.type,
				})),
				icon: 'edit-layout',
				id: structureUuid,
				label: structureLabel,
			},
		];
	}, [fields, structureLabel, structureUuid]);

	const onSelect = (item: TreeItem) => {
		let nextSelection: State['selection'] = selection;

		// Item is root

		if (item.id === structureUuid) {
			nextSelection = [structureUuid];
		}

		// Selecting with selection

		else if (mode === 'single') {
			nextSelection = [item.id];
		}

		// Selecting with multiple selection

		else if (mode === 'multiple' && !selection.includes(item.id)) {
			nextSelection = [
				...selection.filter((uuid) => uuid !== structureUuid),
				item.id,
			];
		}

		// Deselecting with multiple selection

		else if (
			mode === 'multiple' &&
			selection.includes(item.id) &&
			selection.length > 1
		) {
			nextSelection = selection.filter((id) => id !== item.id);
		}

		dispatch({
			selection: nextSelection,
			type: 'set-selection',
		});
	};

	const deleteField = (uuid: Uuid) =>
		dispatch({
			type: 'delete-field',
			uuid,
		});

	return (
		<ClayTreeView
			className="px-4 structure-builder__fields-tree"
			defaultExpandedKeys={new Set([structureUuid])}
			items={items}
			nestedKey="children"
			onSelect={onSelect}
			selectedKeys={new Set(selection)}
			selectionMode={mode}
			showExpanderOnHover={false}
		>
			{(item, selectedKeys) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack
						className={classNames({
							active: selectedKeys.has(item.id),
						})}
						expandOnClick={false}
					>
						<ClayIcon symbol={item.icon} />

						<span className="ml-1">{item.label}</span>

						{invalids.has(item.id) ||
						(item.id === structureUuid && structureError) ? (
							<ClayIcon
								className="ml-2 text-danger"
								symbol="exclamation-full"
							/>
						) : (
							<></>
						)}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item, selectedKeys) => (
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
												disabled={selection.length > 1}
												displayType="unstyled"
												size="sm"
												symbol="ellipsis-v"
											/>
										}
									/>
								}
								className={classNames({
									active: selectedKeys.has(item.id),
								})}
							>
								<ClayIcon
									className={classNames({
										'structure-builder__fields-tree-node--field-icon':
											Boolean(item.type),
									})}
									symbol={item.icon}
								/>

								<span className="ml-1">{item.label}</span>

								{invalids.has(item.id) ? (
									<ClayIcon
										className="ml-2 text-danger"
										symbol="exclamation-full"
									/>
								) : (
									<></>
								)}
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
}

function useSelectionMode() {
	const [multiple, setMultiple] = useState(false);

	useEventListener(
		'keydown',
		(event) => {
			const {key} = event as KeyboardEvent;

			if (key === 'Control' || key === 'Meta') {
				setMultiple(true);
			}
		},
		false,

		// @ts-ignore

		window
	);

	useEventListener(
		'keyup',
		(event) => {
			const {key} = event as KeyboardEvent;

			if (key === 'Control' || key === 'Meta') {
				setMultiple(false);
			}
		},
		false,

		// @ts-ignore

		window
	);

	return multiple ? 'multiple' : 'single';
}
