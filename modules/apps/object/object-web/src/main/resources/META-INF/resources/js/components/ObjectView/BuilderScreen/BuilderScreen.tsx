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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayList from '@clayui/list';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useEffect, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import Card from '../../Card/Card';
import {ManagementToolbarSearch} from '../ManagementToolbarSearch/ManagementToolbarSearch';
import {TObjectViewSortColumn} from '../types';
import BuilderListItem from './BuilderListItem';

import './BuilderScreen.scss';

interface IProps {
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	isDefaultSort?: boolean;
	objectColumns: TObjectViewSortColumn[];
	setEditingObjectFieldName?: (objectFieldName: string) => void;
	setIsEditingSort?: (boolean: boolean) => void;
	setVisibleModal: (boolean: boolean) => void;
	title: string;
}

export function BuilderScreen({
	emptyState,
	isDefaultSort,
	objectColumns,
	setEditingObjectFieldName,
	setIsEditingSort,
	setVisibleModal,
	title,
}: IProps) {
	const [query, setQuery] = useState('');
	const [filteredItems, setFilteredItems] = useState(objectColumns);

	useEffect(() => {
		setFilteredItems(objectColumns);
	}, [objectColumns]);

	const newFiltredItems = filteredItems.filter((objectColumns: any) =>
		objectColumns.label.toLowerCase().includes(query.toLowerCase())
	);

	return (
		<Card>
			<Card.Header title={title} />

			<Card.Body>
				<ClayManagementToolbar>
					<ClayManagementToolbar.ItemList expand>
						<ManagementToolbarSearch
							query={query}
							setQuery={setQuery}
						/>

						<ClayManagementToolbar.Item>
							<ClayButtonWithIcon
								className="nav-btn nav-btn-monospaced"
								onClick={() => setVisibleModal(true)}
								symbol="plus"
							/>
						</ClayManagementToolbar.Item>
					</ClayManagementToolbar.ItemList>
				</ClayManagementToolbar>

				{objectColumns?.length > 0 ? (
					<ClayList>
						{query ? (
							newFiltredItems.length > 0 ? (
								newFiltredItems.map((viewColumn, index) => (
									<React.Fragment
										key={viewColumn.objectFieldName}
									>
										{index === 0 && (
											<ClayList.Item flex>
												<ClayList.ItemField expand>
													<ClayList.ItemField className="object-builder-screen-name">
														{Liferay.Language.get(
															'name'
														)}
													</ClayList.ItemField>
												</ClayList.ItemField>

												{isDefaultSort && (
													<ClayList.ItemField
														className="object-builder-screen-sorting"
														expand
													>
														<ClayList.ItemField>
															{Liferay.Language.get(
																'sorting'
															)}
														</ClayList.ItemField>
													</ClayList.ItemField>
												)}
											</ClayList.Item>
										)}

										<ClayList.Item flex>
											<ClayList.ItemField>
												<ClayButtonWithIcon
													displayType={null}
													symbol="drag"
												/>
											</ClayList.ItemField>

											<ClayList.ItemField expand>
												<ClayList.ItemTitle>
													{viewColumn.label}
												</ClayList.ItemTitle>
											</ClayList.ItemField>

											{isDefaultSort && (
												<ClayList.ItemField
													className="object-builder-screen-sort-order"
													expand
												>
													<ClayList.ItemText>
														{viewColumn.sortOrder ===
														'asc'
															? Liferay.Language.get(
																	'ascending'
															  )
															: Liferay.Language.get(
																	'descending'
															  )}
													</ClayList.ItemText>
												</ClayList.ItemField>
											)}
										</ClayList.Item>
									</React.Fragment>
								))
							) : (
								<div className="lfr-object__object-custom-view-builder-empty-space">
									<ClayEmptyState
										description={Liferay.Language.get(
											'sorry,-no-results-were-found'
										)}
										title={Liferay.Language.get(
											'no-results-found'
										)}
									></ClayEmptyState>
								</div>
							)
						) : (
							objectColumns.map((viewColumn, index) => {
								return (
									<React.Fragment
										key={viewColumn.objectFieldName}
									>
										{index === 0 && (
											<ClayList.Item flex>
												<ClayList.ItemField expand>
													<ClayList.ItemField className="object-builder-screen-name">
														{Liferay.Language.get(
															'name'
														)}
													</ClayList.ItemField>
												</ClayList.ItemField>

												{isDefaultSort && (
													<ClayList.ItemField
														className="object-builder-screen-sorting"
														expand
													>
														<ClayList.ItemField>
															{Liferay.Language.get(
																'sorting'
															)}
														</ClayList.ItemField>
													</ClayList.ItemField>
												)}
											</ClayList.Item>
										)}

										<DndProvider backend={HTML5Backend}>
											<BuilderListItem
												index={index}
												isDefaultSort={isDefaultSort}
												label={viewColumn.label}
												objectFieldName={
													viewColumn.objectFieldName
												}
												setEditingObjectFieldName={
													setEditingObjectFieldName
												}
												setIsEditingSort={
													setIsEditingSort
												}
												setVisibleModal={
													setVisibleModal
												}
												sortOrder={viewColumn.sortOrder}
											/>
										</DndProvider>
									</React.Fragment>
								);
							})
						)}
					</ClayList>
				) : (
					<div className="lfr-object__object-custom-view-builder-empty-space">
						<ClayEmptyState
							description={emptyState.description}
							title={emptyState.title}
						>
							<ClayButton
								displayType="secondary"
								onClick={() => setVisibleModal(true)}
							>
								{emptyState.buttonText}
							</ClayButton>
						</ClayEmptyState>
					</div>
				)}
			</Card.Body>
		</Card>
	);
}
