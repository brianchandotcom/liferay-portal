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
import {ClayInput} from '@clayui/form';
import ClayList from '@clayui/list';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useModal} from '@clayui/modal';
import React, {useContext, useEffect, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import Card from '../Card/Card';
import ModalAddColumnsObjectCustomView from './ModalAddColumnsObjectCustomView';
import ViewBuilderListItem from './ViewBuilderListItem';
import ViewContext from './context';

import './ViewBuilderScreen.scss';

const ViewBuilderScreen: React.FC<{}> = () => {
	const [{objectView}] = useContext(ViewContext);

	const [visibleModal, setVisibleModal] = useState(false);

	const [query, setQuery] = useState('');
	const [filteredItems, setFilteredItems] = useState(
		objectView.objectViewColumns
	);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		setFilteredItems(objectView.objectViewColumns);
	}, [objectView]);

	const newFiltredItems = filteredItems.filter((objectViewColumn) =>
		objectViewColumn.label.toLowerCase().includes(query.toLowerCase())
	);

	return (
		<>
			<Card>
				<Card.Header title="Columns" />

				<Card.Body>
					<ClayManagementToolbar>
						<ClayManagementToolbar.ItemList expand>
							<ClayManagementToolbar.Search>
								<ClayInput.Group>
									<ClayInput.GroupItem>
										<ClayInput
											aria-label="Search"
											className="form-control input-group-inset input-group-inset-after"
											onChange={({target}) =>
												setQuery(target.value)
											}
											placeholder="Search"
											type="text"
											value={query}
										/>

										<ClayInput.GroupInsetItem
											after
											tag="span"
										>
											<ClayButtonWithIcon
												className="navbar-breakpoint-d-none"
												displayType="unstyled"
												onClick={() => {}}
												symbol="times"
											/>

											<ClayButtonWithIcon
												displayType="unstyled"
												symbol="search"
												type="submit"
											/>
										</ClayInput.GroupInsetItem>
									</ClayInput.GroupItem>
								</ClayInput.Group>
							</ClayManagementToolbar.Search>

							<ClayManagementToolbar.Item>
								<ClayButtonWithIcon
									className="nav-btn nav-btn-monospaced"
									onClick={() => setVisibleModal(true)}
									symbol="plus"
								/>
							</ClayManagementToolbar.Item>
						</ClayManagementToolbar.ItemList>
					</ClayManagementToolbar>

					{objectView?.objectViewColumns?.length > 0 ? (
						<ClayList>
							{query ? (
								newFiltredItems.length > 0 ? (
									newFiltredItems.map((viewColumn, index) => (
										<>
											{index === 0 && (
												<ClayList.Item flex>
													<ClayList.ItemField expand>
														<ClayList.ItemField>
															{Liferay.Language.get(
																'name'
															)}
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
														{viewColumn.label}
													</ClayList.ItemTitle>
												</ClayList.ItemField>
											</ClayList.Item>
										</>
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
								objectView?.objectViewColumns.map(
									(viewColumn, index) => {
										return (
											<>
												{index === 0 && (
													<ClayList.Item flex>
														<ClayList.ItemField
															expand
														>
															<ClayList.ItemField>
																{Liferay.Language.get(
																	'name'
																)}
															</ClayList.ItemField>
														</ClayList.ItemField>
													</ClayList.Item>
												)}

												<DndProvider
													backend={HTML5Backend}
												>
													<ViewBuilderListItem
														index={index}
														objectViewColumn={
															viewColumn
														}
													/>
												</DndProvider>
											</>
										);
									}
								)
							)}
						</ClayList>
					) : (
						<div className="lfr-object__object-custom-view-builder-empty-space">
							<ClayEmptyState
								description={Liferay.Language.get(
									'add-columns-to-start-creating-a-view'
								)}
								title={Liferay.Language.get(
									'no-columns-added-yet'
								)}
							>
								<ClayButton
									displayType="secondary"
									onClick={() => setVisibleModal(true)}
								>
									{Liferay.Language.get('add-column')}
								</ClayButton>
							</ClayEmptyState>
						</div>
					)}
				</Card.Body>
			</Card>

			{visibleModal && (
				<ModalAddColumnsObjectCustomView
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

export default ViewBuilderScreen;
