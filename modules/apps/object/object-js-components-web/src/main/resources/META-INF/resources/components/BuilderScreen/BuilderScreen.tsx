/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {filterArrayByQuery} from '../../utils/array';
import {getLocalizableLabel} from '../../utils/string';
import {ManagementToolbarSearch} from '../ManagementToolbar/ManagementToolbarSearch';
import BuilderListItem from './BuilderListItem';

import './BuilderScreen.scss';

export function BuilderScreen({
	creationLanguageId,
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
}: IProps) {
	const [query, setQuery] = useState('');

	const filteredItems = useMemo(() => {
		return filterArrayByQuery({
			array: objectColumns,
			query,
			str: 'fieldLabel',
		});
	}, [objectColumns, query]);

	const tableItems = query ? filteredItems : objectColumns;

	return (
		<>
			<ManagementToolbar.Container>
				<ManagementToolbar.ItemList expand>
					<ManagementToolbarSearch
						query={query}
						setQuery={setQuery}
					/>

					<ManagementToolbar.Item>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('add')}
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
											: getLocalizableLabel(
													creationLanguageId as Liferay.Language.Locale,
													viewColumn?.label,
													viewColumn.objectFieldName
											  )
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
		</>
	);
}

type TLabelValueObject = {
	label: string;
	value: string;
};

type TBuilderScreenColumn = {
	defaultSort?: boolean;
	disableEdit?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: TLabelValueObject[];
};

interface IProps {
	creationLanguageId?: Liferay.Language.Locale;
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
}
