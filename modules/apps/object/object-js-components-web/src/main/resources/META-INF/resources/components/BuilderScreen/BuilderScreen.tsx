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
import React, {useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {Card} from '../Card';
import {ManagementToolbarSearch} from '../ManagementToolbarSearch';
import BuilderListItem from './BuilderListItem';

import './BuilderScreen.scss';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export function BuilderScreen({
	defaultSort,
	disableEdit,
	emptyState,
	filter,
	firstColumnHeader,
	hasDragAndDrop,
	objectColumns,
	onChangeColumnOrder,
	onDeleteColumn,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	openModal,
	secondColumnHeader,
	thirdColumnHeader,
	title,
}: IProps) {
	const [query, setQuery] = useState('');

	const filteredItems = objectColumns.filter(
		(objectColumns: TBuilderScreenColumn) =>
			objectColumns?.fieldLabel
				?.toLowerCase()
				.includes(query.toLowerCase())
	);

	const tableItems = query ? filteredItems : objectColumns;

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

			{tableItems.length ? (
				<ClayList>
					{tableItems.map((viewColumn, index) => (
						<React.Fragment key={viewColumn?.objectFieldName}>
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
									disableEdit={
										disableEdit ||
										(filter && viewColumn?.disableEdit)
									}
									hasDragAndDrop={hasDragAndDrop}
									index={index}
									label={viewColumn?.fieldLabel}
									objectFieldName={viewColumn.objectFieldName}
									onChangeColumnOrder={onChangeColumnOrder}
									onDeleteColumn={onDeleteColumn}
									onEditing={onEditing}
									onEditingObjectFieldName={
										onEditingObjectFieldName
									}
									onVisibleEditModal={onVisibleEditModal}
									secondColumnValue={
										defaultSort
											? viewColumn.sortOrder === 'asc'
												? Liferay.Language.get(
														'ascending'
												  )
												: Liferay.Language.get(
														'descending'
												  )
											: filter
											? viewColumn?.objectFieldBusinessType
											: viewColumn?.label[
													defaultLanguageId
											  ]
									}
									thirdColumnValues={
										viewColumn?.valueList ??
										viewColumn?.value
									}
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

type TName = {
	[key: string]: string;
};

type TLabelValueObject = {
	label: string;
	value: string;
};

type TBuilderScreenColumn = {
	defaultSort?: boolean;
	disableEdit?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	label: TName;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: TLabelValueObject[];
};

interface IProps {
	defaultSort?: boolean;
	disableEdit?: boolean;
	emptyState: {
		buttonText: string;
		description: string;
		title: string;
	};
	filter?: boolean;
	firstColumnHeader: string;
	hasDragAndDrop?: boolean;
	objectColumns: TBuilderScreenColumn[];
	onChangeColumnOrder?: (draggedIndex: number, targetIndex: number) => void;
	onDeleteColumn: (objectFieldName: string) => void;
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal: (boolean: boolean) => void;
	openModal: () => void;
	secondColumnHeader: string;
	thirdColumnHeader?: string;
	title: string;
}
