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
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {defaultLanguageId} from '../../../utils/locale';
import Card from '../../Card/Card';
import {ManagementToolbarSearch} from '../ManagementToolbarSearch/ManagementToolbarSearch';
import {TObjectColumn} from '../types';
import BuilderListItem from './BuilderListItem';

import './BuilderScreen.scss';

interface IProps {
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	firstColumnHeader: string;
	hasDragAndDrop?: boolean;
	isDefaultSort?: boolean;
	objectColumns: TObjectColumn[];
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal: (boolean: boolean) => void;
	onVisibleModal: (boolean: boolean) => void;
	secondColumnHeader: string;
	thirdColumnHeader?: string;
	title: string;
}

export function BuilderScreen({
	emptyState,
	firstColumnHeader,
	hasDragAndDrop,
	isDefaultSort,
	objectColumns,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	onVisibleModal,
	secondColumnHeader,
	thirdColumnHeader,
	title,
}: IProps) {
	const [query, setQuery] = useState('');
	const [filteredItems, setFilteredItems] = useState(objectColumns);

	useEffect(() => {
		setFilteredItems(objectColumns);
	}, [objectColumns]);

	const newFilteredItems = filteredItems.filter(
		(objectColumns: TObjectColumn) =>
			objectColumns.fieldLabel
				?.toLowerCase()
				.includes(query.toLowerCase())
	);

	return (
		<Card title={title}>
			<ManagementToolbar.Container>
				<ManagementToolbar.ItemList expand>
					<ManagementToolbarSearch
						query={query}
						setQuery={setQuery}
					/>

					<ManagementToolbar.Item>
						<ClayButtonWithIcon
							className="nav-btn nav-btn-monospaced"
							onClick={() => onVisibleModal(true)}
							symbol="plus"
						/>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>
			</ManagementToolbar.Container>

			{objectColumns?.length > 0 ? (
				<ClayList>
					{query ? (
						newFilteredItems.length > 0 ? (
							newFilteredItems.map((viewColumn, index) => (
								<React.Fragment
									key={viewColumn.objectFieldName}
								>
									{index === 0 && (
										<ClayList.Item flex>
											<ClayList.ItemField
												className={classNames(
													'lfr-object__object-builder-screen-first-column',
													{
														'drag-and-drop': hasDragAndDrop,
													}
												)}
												expand
											>
												{firstColumnHeader}
											</ClayList.ItemField>

											<ClayList.ItemField
												className="lfr-object__object-builder-screen-second-column"
												expand
											>
												<ClayList.ItemField>
													{secondColumnHeader}
												</ClayList.ItemField>
											</ClayList.ItemField>
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
												{viewColumn.fieldLabel}
											</ClayList.ItemTitle>
										</ClayList.ItemField>

										{isDefaultSort && (
											<ClayList.ItemField
												className="lfr-object__object-builder-screen-sort-order"
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
							<div className="lfr-object__object-builder-screen-empty-state">
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
												<ClayList.ItemField
													className={classNames({
														'lfr-object__object-builder-screen-first-column': hasDragAndDrop,
													})}
													expand
												>
													{firstColumnHeader}
												</ClayList.ItemField>
											</ClayList.ItemField>

											<ClayList.ItemField
												className={classNames({
													'lfr-object__object-builder-screen-second-column': hasDragAndDrop,
												})}
												expand
											>
												<ClayList.ItemField>
													{secondColumnHeader}
												</ClayList.ItemField>
											</ClayList.ItemField>

											{thirdColumnHeader && (
												<ClayList.ItemField
													className={classNames({
														'lfr-object__object-builder-screen-third-column': hasDragAndDrop,
													})}
													expand
												>
													<ClayList.ItemField>
														{thirdColumnHeader}
													</ClayList.ItemField>
												</ClayList.ItemField>
											)}
										</ClayList.Item>
									)}

									<DndProvider backend={HTML5Backend}>
										<BuilderListItem
											aliasColumnText={
												isDefaultSort
													? viewColumn.sortOrder ===
													  'asc'
														? Liferay.Language.get(
																'ascending'
														  )
														: Liferay.Language.get(
																'descending'
														  )
													: viewColumn.label[
															defaultLanguageId
													  ]
											}
											hasDragAndDrop={hasDragAndDrop}
											index={index}
											isDefaultSort={isDefaultSort}
											label={viewColumn.fieldLabel}
											objectFieldName={
												viewColumn.objectFieldName
											}
											onEditing={onEditing}
											onEditingObjectFieldName={
												onEditingObjectFieldName
											}
											onVisibleEditModal={
												onVisibleEditModal
											}
											thirdColumnValues={
												viewColumn.valueList
											}
										/>
									</DndProvider>
								</React.Fragment>
							);
						})
					)}
				</ClayList>
			) : (
				<div className="lfr-object__object-builder-screen-empty-state">
					<ClayEmptyState
						description={emptyState.description}
						title={emptyState.title}
					>
						<ClayButton
							displayType="secondary"
							onClick={() => onVisibleModal(true)}
						>
							{emptyState.buttonText}
						</ClayButton>
					</ClayEmptyState>
				</div>
			)}
		</Card>
	);
}
