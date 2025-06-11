/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import getCN from 'classnames';
import {fetch, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import ApiHelper from '../../../../services/ApiHelper';
import SpaceService from '../../../../services/SpaceService';
import {executeAsyncItemAction} from '../../../FDSPropsTransformer/utils/executeAsyncItemAction';
import {FETCH_URLS} from './MoveCategoryModalContent';

/**
 * Returns info about the tree level.
 * @param {number} depth Tree level in question
 * @returns {object}
 */
const getTreeLevelInfo = (depth: number) =>
	depth > 0
		? {
				getURL: FETCH_URLS.getSubCategories,
				icon: 'categories',
				showSelect: true,
			}
		: {
				getURL: FETCH_URLS.getCategories,
				icon: 'vocabulary',
				showSelect: false,
			};

/**
 * Returns a copy of the tree with properties added/updated to the object at index.
 * @param {Array} tree An array of categories
 * @param {number} index Position of object inside tree to update
 * @param {object} properties Properties to add/edit within object at index
 * @returns {Array}
 */
const getUpdatedTree = (tree: any, index: any, properties: any) => {
	const treeCopy = tree.slice();

	treeCopy.splice(index, 1, {...tree[index], ...properties});

	return treeCopy;
};

const getSpaceName = async (assetLibrary: any) => {
	if (assetLibrary?.some((item: any) => item === -1)) {
		return 'All Spaces';
	}
	const spaces = await SpaceService.getSpaces().then((response) => {
		return response.map((item) => ({
			erc: item.externalReferenceCode,
			label: item.name,
			value: item.id,
		}));
	});

	const sites = spaces.map((space: any) =>
		ApiHelper.get<{items: any}>(
			`/o/headless-site/v1.0/sites/by-external-reference-code/${space.erc}`
		).then((response: {data: any}) => {
			return response.data;
		})
	);

	const fetchedSpaceData = await Promise.all(sites);

	return assetLibrary?.map((item: any) => {
		return fetchedSpaceData.find((space: any) => space.id === item).name;
	});
};

function TreeViewLink({
	activeItem,
	depth,
	hasSubItems,
	isExpanded = false,
	item,
	itemData,
	onClickExpand,
	onSelect,
}: any) {
	const {icon, showSelect} = getTreeLevelInfo(depth);
	const [disabled, setDisabled] = useState(false);
	const [spaceName, setSpaceName] = useState('');
	const [loaded, setLoaded] = useState(false);

	const isActive = item.id === activeItem;

	useEffect(() => {
		if (item.assetLibraries) {
			getSpaceName(item.assetLibraries).then((result) => {
				setSpaceName(result);
				setLoaded(true);
			});
		}
		else {
			setLoaded(true);
		}
	}, [item.assetLibraries]);

	useEffect(() => {
		const disableItems = async () => {
			if (depth === 0) {
				const destinationAssetLibraryIds = item.assetLibraries?.flat();

				if (!itemData || destinationAssetLibraryIds[0] === -1) {
					setDisabled(false);

					return;
				}

				const {data, error} = await ApiHelper.get<any>(
					FETCH_URLS.getVocabulary(itemData.taxonomyVocabularyId)
				);

				if (error) {
					setDisabled(true);
				}

				const sourceAssetLibraryIds = data.assetLibraries.map(
					(item: any) => item.id
				);

				setDisabled(
					!sourceAssetLibraryIds.every((id: any) =>
						destinationAssetLibraryIds.includes(id)
					)
				);
			}
			else if (depth !== 0 && item.id === itemData.id) {
				setDisabled(true);
			}
			else {
				setDisabled(false);
			}
		};

		disableItems();
	}, [item.assetLibraries, item.id, depth, itemData]);

	const handleItemClick = () => {
		onSelect(item);
	};

	return (
		<div
			aria-expanded={isExpanded}
			className={getCN('treeview-link', {
				'active': isActive,
				'collapsed': !isExpanded,
				'disabled-link': disabled,
			})}
			role="treeitem"
			style={{paddingLeft: 24 * depth + 'px'}}
		>
			<div className="c-inner" style={{marginLeft: -24 * depth + 'px'}}>
				<div className={getCN('autofit-row')}>
					<div
						className={getCN('autofit-col', {
							'no-subcategories': !hasSubItems,
						})}
					>
						{hasSubItems && (
							<ClayButton
								className="component-expander"
								disabled={disabled}
								displayType={null}
								monospaced
								onClick={onClickExpand}
							>
								<span className="c-inner">
									<ClayIcon symbol="angle-down" />

									<ClayIcon
										className="component-expanded-d-none"
										symbol="angle-right"
									/>
								</span>
							</ClayButton>
						)}
					</div>

					<div className="autofit-col">
						<span
							className={getCN('component-icon', {
								disabled,
							})}
						>
							<ClayIcon symbol={icon} />
						</span>
					</div>

					<div
						className="autofit-col autofit-col-expand"
						onClick={disabled ? undefined : handleItemClick}
					>
						<span
							className={getCN('component-text', {
								disabled,
							})}
						>
							<span className="text-truncate-inline">
								<span className="text-truncate">
									{showSelect
										? item.name
										: loaded
											? `${item.name} (${spaceName})`
											: item.name}

									{depth !== 0 && disabled && (
										<span className="badge badge-pill badge-secondary ml-2">
											<span className="badge-item badge-item-expand">
												{Liferay.Language.get(
													'to-be-moved'
												).toUpperCase()}
											</span>
										</span>
									)}
								</span>
							</span>
						</span>
					</div>
				</div>
			</div>
		</div>
	);
}

function TreeViewGroup({
	activeItem,
	depth,
	itemData,
	items,
	onChangeItems,
	onSelect,
}: any) {
	const {getURL} = getTreeLevelInfo(depth);

	const _handleExpand = (item: any, index: any) => {
		if (!item.children && item.numberOfTaxonomyCategories > 0) {
			fetch(getURL(item.id))
				.then((response) => response.json())
				.then((responseContent: any) => {
					const children = responseContent.items.map(
						({
							id,
							name,
							numberOfTaxonomyCategories,
							taxonomyVocabularyId,
						}: {
							id: any;
							name: string;
							numberOfTaxonomyCategories: number;
							taxonomyVocabularyId: number;
						}) => ({
							id,
							name,
							numberOfTaxonomyCategories,
							taxonomyVocabularyId,
						})
					);

					onChangeItems(
						getUpdatedTree(items, index, {
							children,
							expand: true,
						})
					);
				});
		}
		else {
			onChangeItems(
				getUpdatedTree(items, index, {
					expand: !items[index].expand,
				})
			);
		}
	};

	const _handleChangeChildren = (index: any) => (children: any) => {
		onChangeItems(getUpdatedTree(items, index, {children}));
	};

	return (
		<ul className="treeview-group" role="group">
			{items.map((item: any, index: any) => (
				<li className="treeview-item" key={item.id} role="none">
					<TreeViewLink
						activeItem={activeItem}
						depth={depth}
						hasSubItems={item.numberOfTaxonomyCategories > 0}
						isExpanded={item.expand}
						item={item}
						itemData={itemData}
						onClickExpand={() => _handleExpand(item, index)}
						onSelect={onSelect}
					/>

					{item.children && (
						<div
							className={getCN('collapse', {
								show: item.expand,
							})}
						>
							<TreeViewGroup
								activeItem={activeItem}
								depth={depth + 1}
								itemData={itemData}
								items={item.children}
								onChangeItems={_handleChangeChildren(index)}
								onSelect={onSelect}
							/>
						</div>
					)}
				</li>
			))}
		</ul>
	);
}

function MoveCategoryTreeView({
	itemData,
	tree = [],
	loadData,
	onChangeTree,
	onClose,
}: any) {
	const [activeItem, setActiveItem] = useState<string | null>(null);
	const [selectedVocabulary, setSelectedVocabulary] = useState('');
	const [selectedCategory, setSelectedCategory] = useState('');

	const _handleChangeSiteChildren = (children: any) => {
		onChangeTree(
			getUpdatedTree(tree, 0, {
				children,
			})
		);
	};

	const _handleSave = async () => {
		const url = FETCH_URLS.getCategory(itemData.id);

		const body = {
			name: itemData.name,
			parentTaxonomyCategory: {
				id: selectedCategory.length ? selectedCategory : 0,
			},
			taxonomyVocabularyId: selectedVocabulary,
		};

		executeAsyncItemAction({
			method: 'PUT',
			refreshData: loadData,
			requestBody: JSON.stringify(body),
			url,
		});

		onClose();
	};

	const _handleSelect = (item: any) => {
		if (item.id === activeItem) {
			setActiveItem(null);
		}
		else {
			setActiveItem(item.id);
		}

		if (item?.assetLibraries) {
			setSelectedCategory('');
			setSelectedVocabulary(item.id);
		}
		else {
			setSelectedCategory(item.id);
			setSelectedVocabulary(item.taxonomyVocabularyId);
		}
	};

	return (
		<>
			<div className="categorization-section">
				<div className="category-selector-modal">
					<ClayModal.Header>
						{sub(Liferay.Language.get('move-x'), itemData?.name)}
					</ClayModal.Header>

					<ClayAlert displayType="info" variant="stripe">
						{Liferay.Language.get(
							'categories-can-only-be-moved-to-a-vocabulary-or-a-category-with-the-same-visibility-and-in-the-same-space'
						)}
					</ClayAlert>

					<ClayModal.Body>
						<div className="selector-modal-tree">
							{!tree[0]?.children?.length ? (
								<span className="component-text text-secondary">
									{Liferay.Language.get(
										'no-items-were-found'
									)}
								</span>
							) : (
								<ul
									className="treeview treeview-light treeview-nested"
									role="tree"
								>
									<TreeViewGroup
										activeItem={activeItem}
										depth={0}
										itemData={itemData}
										items={tree[0].children || []}
										onChangeItems={
											_handleChangeSiteChildren
										}
										onSelect={(item: any) =>
											_handleSelect(item)
										}
									/>
								</ul>
							)}
						</div>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton onClick={_handleSave}>
									{Liferay.Language.get('move')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</div>
			</div>
		</>
	);
}

export default function ({
	closeModal,
	itemData,
	loadData,
	onChangeTree,
	tree,
}: any) {
	return (
		<MoveCategoryTreeView
			itemData={itemData}
			loadData={loadData}
			onChangeTree={onChangeTree}
			onClose={() => closeModal()}
			tree={tree}
		/>
	);
}
