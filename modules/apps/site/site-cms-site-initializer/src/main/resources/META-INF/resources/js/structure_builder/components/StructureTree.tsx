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
import React, {Key, useEffect, useMemo, useState} from 'react';

import {useCache} from '../contexts/CacheContext';
import {
	Action,
	State,
	useSelector,
	useStateDispatch,
} from '../contexts/StateContext';
import selectInvalids from '../selectors/selectInvalids';
import selectSelection from '../selectors/selectSelection';
import selectStructureChildren from '../selectors/selectStructureChildren';
import selectStructureERC from '../selectors/selectStructureERC';
import selectStructureError from '../selectors/selectStructureError';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureUuid from '../selectors/selectStructureUuid';
import {
	RepeatableGroup,
	Structure,
	StructureChild,
	Structures,
} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import {FIELD_TYPE_ICON, FieldType} from '../utils/field';
import getReferencedStructureLabel from '../utils/getReferencedStructureLabel';
import getStructureEditURL from '../utils/getStructureEditURL';

type TreeItem = {
	children?: TreeItem[];
	erc?: string;
	icon: string;
	id: string;
	label: string;
	name?: string;
	type?: FieldType | 'referenced-structure' | 'repeatable-group';
	uuid: Uuid;
};

export default function StructureTree({search}: {search: string}) {
	const dispatch = useStateDispatch();

	const children = useSelector(selectStructureChildren);
	const invalids = useSelector(selectInvalids);
	const selection = useSelector(selectSelection);
	const structureLabel = useSelector(selectStructureLocalizedLabel);
	const structureUuid = useSelector(selectStructureUuid);
	const structureError = useSelector(selectStructureError);
	const structureERC = useSelector(selectStructureERC);

	const {
		data: structures,
		load: loadStructures,
		status: structuresStatus,
	} = useCache('structures');

	const mode = useSelectionMode();

	const [expandedKeys, setExpandedKeys] = useState<Set<Key>>(
		new Set([structureUuid])
	);
	const [selectedKeys, setSelectedKeys] = useState<Set<Key>>(new Set());

	const hasReferencedStructure = Array.from(children.values()).some(
		({type}) => type === 'referenced-structure'
	);

	const items: TreeItem[] = useMemo(() => {
		if (hasReferencedStructure && structuresStatus !== 'saved') {
			return [];
		}

		return [
			{
				children: buildItems({
					children,
					search,
					structureERC,
					structures,
				}),
				icon: 'edit-layout',
				id: structureUuid,
				label: structureLabel,
				uuid: structureUuid,
			},
		];
	}, [
		children,
		hasReferencedStructure,
		search,
		structureERC,
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
			nextSelection = [item.uuid];
		}

		// Selecting with multiple selection

		else if (mode === 'multiple' && !selection.includes(item.uuid)) {
			nextSelection = [
				...selection.filter((uuid) => uuid !== structureUuid),
				item.uuid,
			];
		}

		// Deselecting with multiple selection

		else if (
			mode === 'multiple' &&
			selection.includes(item.uuid) &&
			selection.length > 1
		) {
			nextSelection = selection.filter((uuid) => uuid !== item.uuid);
		}

		dispatch({
			selection: nextSelection,
			type: 'set-selection',
		});
	};

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
			className="px-4 structure-builder__tree"
			expandedKeys={expandedKeys}
			items={items}
			nestedKey="children"
			onExpandedChange={setExpandedKeys}
			onSelect={onSelect}
			onSelectionChange={setSelectedKeys}
			selectedKeys={selectedKeys}
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
								'structure-builder__tree-node--field-icon':
									item.type &&
									item.type !== 'referenced-structure',
								'structure-builder__tree-node--group-icon':
									item.type === 'repeatable-group',
								'structure-builder__tree-node--structure-icon':
									item.type === 'referenced-structure',
							})}
							symbol={item.icon}
						/>

						<span className="ml-1">{item.label}</span>

						{invalids.has(item.uuid) ||
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
						{(childItem, selectedKeys) => {
							const actions = getItemActions({
								dispatch,
								item: childItem,
								parent: item,
								structures,
							});

							return (
								<ClayTreeView.Item
									actions={
										actions.length ? (
											<ClayDropDownWithItems
												items={actions}
												trigger={
													<ClayButtonWithIcon
														aria-label={Liferay.Language.get(
															'field-options'
														)}
														borderless
														disabled={
															selection.length > 1
														}
														displayType="unstyled"
														size="sm"
														symbol="ellipsis-v"
													/>
												}
											/>
										) : undefined
									}
									className={classNames({
										active: selectedKeys.has(childItem.id),
									})}
								>
									<ClayIcon
										className="structure-builder__tree-node--field-icon"
										symbol={childItem.icon}
									/>

									<span className="ml-1">
										{childItem.label}
									</span>

									{invalids.has(childItem.uuid) ? (
										<ClayIcon
											className="ml-2 text-danger"
											symbol="exclamation-full"
										/>
									) : (
										<></>
									)}
								</ClayTreeView.Item>
							);
						}}
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

function buildItems({
	children,
	path = [],
	search,
	structureERC,
	structures,
}: {
	children: (Structure | RepeatableGroup)['children'];
	path?: string[];
	search: string;
	structureERC: Structure['erc'];
	structures: Structures;
}): TreeItem[] {
	return Array.from(children.values()).reduce(
		(items: TreeItem[], child: StructureChild) => {
			if (child.type === 'referenced-structure') {
				const structure = structures.get(child.erc)!;
				const label = getReferencedStructureLabel(
					child.erc,
					structures
				);

				const item: TreeItem = {
					children:
						child.erc === structureERC
							? []
							: buildItems({
									children: structure.children,
									path: [...path, child.name],
									search,
									structureERC,
									structures,
								}),
					erc: child.erc,
					icon: 'edit-layout',
					id: buildId(path, child),
					label: getReferencedStructureLabel(child.erc, structures),
					type: child.type,
					uuid: child.uuid,
				};

				if (match(label, search) || item.children?.length) {
					items.push(item);
				}
			}
			else if (child.type === 'repeatable-group') {
				const label =
					child.label[Liferay.ThemeDisplay.getDefaultLanguageId()]!;

				const item: TreeItem = {
					children: buildItems({
						children: child.children,
						path: [...path, child.name],
						search,
						structureERC,
						structures,
					}),
					erc: child.erc,
					icon: 'fieldset',
					id: buildId(path, child),
					label,
					type: child.type,
					uuid: child.uuid,
				};

				if (match(label, search) || item.children?.length) {
					items.push(item);
				}
			}
			else {
				const label =
					child.label[Liferay.ThemeDisplay.getDefaultLanguageId()]!;

				if (match(label, search)) {
					items.push({
						icon: FIELD_TYPE_ICON[child.type],
						id: buildId(path, child),
						label: child.label[
							Liferay.ThemeDisplay.getDefaultLanguageId()
						]!,
						type: child.type,
						uuid: child.uuid,
					});
				}
			}

			return items;
		},
		[]
	);
}

function buildId(path: string[], child: StructureChild) {
	return [...path, child.name].join('_');
}

function match(value: string, keyword: string) {
	if (!keyword) {
		return true;
	}

	return value.toLowerCase().includes(keyword.toLowerCase());
}

function getItemActions({
	dispatch,
	item,
	parent,
	structures,
}: {
	dispatch: React.Dispatch<Action>;
	item: TreeItem;
	parent: TreeItem;
	structures: Structures;
}) {
	const actions = [];

	if (item.type === 'referenced-structure' && item.erc) {
		const structure = structures.get(item.erc);

		if (structure) {
			actions.push({
				href: getStructureEditURL(structure),
				label: Liferay.Language.get('edit'),
				symbolLeft: 'pencil',
				symbolRight: 'shortcut',
				target: '_blank',
			});
		}
	}

	if (parent.type !== 'referenced-structure') {
		if (
			item.type !== 'referenced-structure' &&
			item.type !== 'repeatable-group'
		) {
			actions.push({
				label: Liferay.Language.get('create-repeatable-group'),
				onClick: () =>
					dispatch({
						type: 'add-repeatable-group',
					}),
				symbolLeft: 'repeat',
			});
		}

		actions.push({
			label: Liferay.Language.get('delete-field'),
			onClick: () =>
				dispatch({
					type: 'delete-child',
					uuid: item.uuid,
				}),
			symbolLeft: 'trash',
		});
	}

	return actions;
}
