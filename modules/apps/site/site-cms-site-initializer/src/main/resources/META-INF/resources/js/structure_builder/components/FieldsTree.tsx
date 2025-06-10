/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useMemo, useState} from 'react';

import {
	FIELD_TYPE_ICON,
	Field,
	FieldType,
} from '../../structure_builder/utils/field';
import {useCache} from '../contexts/CacheContext';
import {State, useSelector, useStateDispatch} from '../contexts/StateContext';
import selectInvalids from '../selectors/selectInvalids';
import selectSelection from '../selectors/selectSelection';
import selectStructureError from '../selectors/selectStructureError';
import selectStructureFields from '../selectors/selectStructureFields';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureUuid from '../selectors/selectStructureUuid';
import {ReferencedStructure, Structures} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import getReferencedStructureLabel from '../utils/getReferencedStructureLabel';

type TreeItem = {
	children?: TreeItem[];
	erc?: string;
	icon: string;
	id: Uuid;
	label: string;
	name?: string;
	type?: FieldType | 'referenced-structure';
};

export default function FieldsTree({search}: {search: string}) {
	const dispatch = useStateDispatch();

	const fields = useSelector(selectStructureFields);
	const invalids = useSelector(selectInvalids);
	const selection = useSelector(selectSelection);
	const structureLabel = useSelector(selectStructureLocalizedLabel);
	const structureUuid = useSelector(selectStructureUuid);
	const structureError = useSelector(selectStructureError);

	const {
		data: structures,
		load: loadStructures,
		status: structuresStatus,
	} = useCache('structures');

	const mode = useSelectionMode();

	const hasReferencedStructure = fields.some(
		({type}) => type === 'referenced-structure'
	);

	const items: TreeItem[] = useMemo(() => {
		if (hasReferencedStructure && structuresStatus !== 'saved') {
			return [];
		}

		return [
			{
				children: buildItems(fields, structures, search),
				icon: 'edit-layout',
				id: structureUuid,
				label: structureLabel,
			},
		];
	}, [
		fields,
		hasReferencedStructure,
		search,
		structureLabel,
		structureUuid,
		structures,
		structuresStatus,
	]);

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

	useEffect(() => {
		if (structuresStatus === 'stale' && hasReferencedStructure) {
			loadStructures();
		}
	}, [hasReferencedStructure, loadStructures, structuresStatus]);

	if (structuresStatus === 'saving' && hasReferencedStructure) {
		return <ClayLoadingIndicator className="my-6" />;
	}

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
						<ClayIcon
							className={classNames({
								'structure-builder__fields-tree-node--field-icon':
									item.type &&
									item.type !== 'referenced-structure',
								'structure-builder__fields-tree-node--structure-icon':
									item.type === 'referenced-structure',
							})}
							symbol={item.icon}
						/>

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
									className="structure-builder__fields-tree-node--field-icon"
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

function buildItems(
	fields: (Field | ReferencedStructure)[],
	structures: Structures,
	search: string
): TreeItem[] {
	return fields.reduce(
		(items: TreeItem[], field: Field | ReferencedStructure) => {
			if (field.type === 'referenced-structure') {
				const structure = structures.get(field.erc)!;
				const children = selectStructureFields(structure);
				const label = getReferencedStructureLabel(
					field.erc,
					structures
				);

				const item = {
					children: buildItems(children, structures, search),
					erc: field.erc,
					icon: 'edit-layout',
					id: field.uuid,
					label: getReferencedStructureLabel(field.erc, structures),
					type: field.type,
				};

				if (match(label, search) || item.children.length) {
					items.push(item);
				}
			}
			else {
				const label =
					field.label[Liferay.ThemeDisplay.getDefaultLanguageId()]!;

				if (match(label, search)) {
					items.push({
						icon: FIELD_TYPE_ICON[field.type],
						id: field.uuid,
						label: field.label[
							Liferay.ThemeDisplay.getDefaultLanguageId()
						]!,
						type: field.type,
					});
				}
			}

			return items;
		},
		[]
	);
}

function match(value: string, keyword: string) {
	if (!keyword) {
		return true;
	}

	return value.toLowerCase().includes(keyword.toLowerCase());
}
