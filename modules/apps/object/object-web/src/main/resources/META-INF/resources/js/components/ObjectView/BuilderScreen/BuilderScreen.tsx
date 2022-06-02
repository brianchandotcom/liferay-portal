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
import {Card} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {defaultLanguageId} from '../../../utils/locale';
import {ManagementToolbarSearch} from '../ManagementToolbarSearch/ManagementToolbarSearch';
import {TObjectColumn} from '../types';
import BuilderListItem from './BuilderListItem';

import './BuilderScreen.scss';

interface IProps {
	defaultSort?: boolean;
	disableEdit?: (businessType: string) => boolean;
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	filter?: boolean;
	firstColumnHeader: string;
	hasDragAndDrop?: boolean;
	objectColumns: TObjectColumn[];
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal: (boolean: boolean) => void;
	openModal: () => void;
	secondColumnHeader: string;
	thirdColumnHeader?: string;
	title: string;
}

export function BuilderScreen({
	defaultSort,
	disableEdit,
	emptyState,
	filter,
	firstColumnHeader,
	hasDragAndDrop,
	objectColumns,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	openModal,
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

	const tableItems = query ? newFilteredItems : objectColumns;

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
							onClick={openModal}
							symbol="plus"
						/>
					</ManagementToolbar.Item>
				</ManagementToolbar.ItemList>
			</ManagementToolbar.Container>

			{tableItems.length > 0 ? (
				<ClayList>
					{tableItems.map((viewColumn, index) => (
						<React.Fragment key={viewColumn.objectFieldName}>
							{index === 0 && (
								<ClayList.Item flex>
									<ClayList.ItemField
										className={classNames(
											'lfr-object__object-builder-screen-first-column',
											!hasDragAndDrop &&
												'lfr-object__object-builder-screen-first-column--not-draggable'
										)}
										expand
									>
										{firstColumnHeader}
									</ClayList.ItemField>

									<ClayList.ItemField
										className={classNames(
											'lfr-object__object-builder-screen-second-column',
											!hasDragAndDrop &&
												'lfr-object__object-builder-screen-second-column--not-draggable'
										)}
										expand
									>
										<ClayList.ItemField>
											{secondColumnHeader}
										</ClayList.ItemField>
									</ClayList.ItemField>

									{thirdColumnHeader && (
										<ClayList.ItemField
											className={classNames(
												'lfr-object__object-builder-screen-third-column',
												!hasDragAndDrop &&
													'lfr-object__object-builder-screen-third-column--not-draggable'
											)}
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
										defaultSort
											? viewColumn.sortOrder === 'asc'
												? Liferay.Language.get(
														'ascending'
												  )
												: Liferay.Language.get(
														'descending'
												  )
											: filter
											? viewColumn.objectFieldBusinessType
											: viewColumn.label[
													defaultLanguageId
											  ]
									}
									defaultSort={defaultSort}
									disableEdit={
										disableEdit &&
										disableEdit(
											viewColumn.objectFieldBusinessType!
										)
									}
									filter={filter}
									hasDragAndDrop={hasDragAndDrop}
									index={index}
									label={viewColumn.fieldLabel}
									objectFieldName={viewColumn.objectFieldName}
									onEditing={onEditing}
									onEditingObjectFieldName={
										onEditingObjectFieldName
									}
									onVisibleEditModal={onVisibleEditModal}
									thirdColumnValues={viewColumn.valueList}
								/>
							</DndProvider>
						</React.Fragment>
					))}
				</ClayList>
			) : query ? (
				<div className="lfr-object__object-builder-screen-empty-state">
					<ClayEmptyState
						description={Liferay.Language.get(
							'sorry,-no-results-were-found'
						)}
						title={Liferay.Language.get('no-results-found')}
					/>
				</div>
			) : (
				<div className="lfr-object__object-builder-screen-empty-state">
					<ClayEmptyState
						description={emptyState.description}
						title={emptyState.title}
					>
						<ClayButton displayType="secondary" onClick={openModal}>
							{emptyState.buttonText}
						</ClayButton>
					</ClayEmptyState>
				</div>
			)}
		</Card>
	);
}
