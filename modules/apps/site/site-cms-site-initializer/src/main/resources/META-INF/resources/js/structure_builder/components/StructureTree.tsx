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
	ReferencedStructure,
	RepeatableGroup,
	Structure,
	StructureChild,
} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import {FIELD_TYPE_ICON, FieldType} from '../utils/field';
import getLocalizedLabel from '../utils/getLocalizedLabel';

type TreeItem = {
	children?: TreeItem[];
	editURL?: string;
	erc?: string;
	icon: string;
	id: Uuid;
	label: string;
	name?: string;
	type?: FieldType | ReferencedStructure['type'] | RepeatableGroup['type'];
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

	const {load: loadObjectDefinitions, status: objectDefinitionsStatus} =
		useCache('object-definitions');

	const mode = useSelectionMode();

	const [expandedKeys, setExpandedKeys] = useState<Set<Key>>(
		new Set([structureUuid])
	);
	const [selectedKeys, setSelectedKeys] = useState<Set<Key>>(new Set());

	const hasReferencedStructure = Array.from(children.values()).some(
		({type}) => type === 'referenced-structure'
	);

	const items: TreeItem[] = useMemo(() => {
		if (hasReferencedStructure && objectDefinitionsStatus !== 'saved') {
			return [];
		}

		return [
			{
				children: buildItems({
					children,
					search,
					structureERC,
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
		objectDefinitionsStatus,
		search,
		structureERC,
		structureLabel,
		structureUuid,
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
			nextSelection = selection.filter((uuid) => uuid !== item.id);
		}

		dispatch({
			selection: nextSelection,
			type: 'set-selection',
		});
	};

	useEffect(() => {
		if (objectDefinitionsStatus === 'stale' && hasReferencedStructure) {
			loadObjectDefinitions().then((objectDefinitions) =>
				dispatch({
					objectDefinitions,
					type: 'refresh-referenced-structures',
				})
			);
		}
	}, [
		dispatch,
		hasReferencedStructure,
		loadObjectDefinitions,
		objectDefinitionsStatus,
	]);

	useEffect(() => {
		for (const uuid of selection) {
			if (!selectedKeys.has(uuid)) {
				setSelectedKeys(new Set(selection));

				setExpandedKeys((current) => new Set([...current, uuid]));
			}
		}

		// eslint-disable-next-line
	}, [selection]);

	if (objectDefinitionsStatus === 'saving' && hasReferencedStructure) {
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

						{item.type === 'referenced-structure' ||
						item.type === 'repeatable-group' ? (
							<ClayIcon className="ml-2" symbol="repeat" />
						) : (
							<></>
						)}

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
						{(childItem, selectedKeys) => {
							const actions = getItemActions({
								dispatch,
								item: childItem,
								parent: item,
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

									{invalids.has(childItem.id) ? (
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
}: {
	children: (ReferencedStructure | RepeatableGroup | Structure)['children'];
	path?: string[];
	search: string;
	structureERC: Structure['erc'];
}): TreeItem[] {
	return Array.from(children.values()).reduce(
		(items: TreeItem[], child: StructureChild) => {
			if (
				child.type === 'referenced-structure' ||
				child.type === 'repeatable-group'
			) {
				const label = getLocalizedLabel(child);

				const item: TreeItem = {
					children: buildItems({
						children: child.children,
						path: [...path, child.name],
						search,
						structureERC,
					}),
					erc: child.erc,
					icon: 'fieldset',
					id: child.uuid,
					label,
					type: child.type,
				};

				if (child.type === 'referenced-structure') {
					item.icon = 'edit-layout';
					item.editURL = child.editURL;
				}

				if (match(label, search) || item.children?.length) {
					items.push(item);
				}
			}
			else {
				const label = getLocalizedLabel(child);

				if (match(label, search)) {
					items.push({
						icon: FIELD_TYPE_ICON[child.type],
						id: child.uuid,
						label: child.label[
							Liferay.ThemeDisplay.getDefaultLanguageId()
						]!,
						type: child.type,
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

function getItemActions({
	dispatch,
	item,
	parent,
}: {
	dispatch: React.Dispatch<Action>;
	item: TreeItem;
	parent: TreeItem;
}) {
	const actions = [];

	if (item.type === 'referenced-structure' && item.erc) {
		actions.push({
			href: item.editURL,
			label: Liferay.Language.get('edit'),
			symbolLeft: 'pencil',
			symbolRight: 'shortcut',
			target: '_blank',
		});
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
					uuid: item.id,
				}),
			symbolLeft: 'trash',
		});
	}

	return actions;
}
