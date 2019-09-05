/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import getCN from 'classnames';
import Item from '../list/Item.es';
import PropTypes from 'prop-types';
import React from 'react';
import {ClayCheckbox} from '@clayui/form';
import {sub} from '../../utils/language.es';

function AddResultModal({
	addResultSearchQuery,
	addResultSelectedIds,
	dataLoading,
	getCurrentResultSelectedIds,
	handleAllCheckbox,
	handleClearAllSelected,
	handleClose,
	handleSearchChange,
	handleSearchEnter,
	handleSearchKeyDown,
	handleSelect,
	handleSubmit,
	page,
	results,
	renderEmptyState,
	selectedDelta,
	showModal
}) {
	const {observer, onClose} = useModal({
		onClose: handleClose
	});

	const classManagementBar = getCN(
		'management-bar',
		addResultSelectedIds.length > 0
			? 'management-bar-primary'
			: 'management-bar-light',
		'navbar',
		'navbar-expand-md'
	);

	const start = page * selectedDelta;

	return showModal ? (
		<ClayModal
			className="modal-full-screen-sm-down results-ranking-modal-root"
			observer={observer}
			size="lg"
		>
			<div
				className="add-result-modal-root"
				data-testid="add-result-modal"
			>
				<ClayModal.Header>
					{Liferay.Language.get('add-result')}
				</ClayModal.Header>

				<ClayModal.Body>
					<div className="add-result-container container-fluid">
						<div className="management-bar navbar-expand-md">
							<div className="navbar-form navbar-form-autofit">
								<div className="input-group">
									<div className="input-group-item">
										<input
											aria-label={Liferay.Language.get(
												'search-the-engine'
											)}
											className="form-control input-group-inset input-group-inset-after"
											onChange={handleSearchChange}
											onKeyDown={handleSearchKeyDown}
											placeholder={Liferay.Language.get(
												'search-the-engine'
											)}
											type="text"
											value={addResultSearchQuery}
										/>

										<div className="input-group-inset-item input-group-inset-item-after">
											<ClayButton
												displayType="unstyled"
												onClick={handleSearchEnter}
											>
												<ClayIcon symbol="search" />
											</ClayButton>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div className="add-result-scroller inline-scroller">
						{dataLoading && (
							<div className="list-group sheet">
								<div className="sheet-title">
									<div className="load-more-container">
										<ClayLoadingIndicator />
									</div>
								</div>
							</div>
						)}

						{!dataLoading &&
							(results.total && results.items ? (
								<>
									<div className="add-result-sheet sheet">
										<div className={classManagementBar}>
											<div className="container-fluid container-fluid-max-xl">
												<ul className="navbar-nav navbar-nav-expand">
													<li className="nav-item">
														<ClayCheckbox
															aria-label={Liferay.Language.get(
																'select-all'
															)}
															checked={
																getCurrentResultSelectedIds()
																	.length ===
																results.items
																	.length
															}
															indeterminate={
																addResultSelectedIds.length >
																	0 &&
																getCurrentResultSelectedIds()
																	.length !==
																	results
																		.items
																		.length
															}
															onChange={
																handleAllCheckbox
															}
														/>
													</li>

													<li className="nav-item">
														<span className="navbar-text">
															{addResultSelectedIds.length >
															0
																? sub(
																		Liferay.Language.get(
																			'x-items-selected'
																		),
																		[
																			addResultSelectedIds.length
																		]
																  )
																: sub(
																		Liferay.Language.get(
																			'x-x-of-x-results'
																		),
																		[
																			start -
																				selectedDelta +
																				1,
																			Math.min(
																				start,
																				results.total
																			),
																			results.total
																		]
																  )}
														</span>
													</li>

													{addResultSelectedIds.length >
														0 && (
														<li className="nav-item nav-item-shrink">
															<ClayButton
																className="btn-outline-borderless"
																displayType="secondary"
																onClick={
																	handleClearAllSelected
																}
																small
															>
																{Liferay.Language.get(
																	'clear-all-selected'
																)}
															</ClayButton>
														</li>
													)}
												</ul>
											</div>
										</div>

										<ul
											className="list-group"
											data-testid="add-result-items"
										>
											{results.items.map(
												(result, index) => (
													<Item
														author={result.author}
														clicks={result.clicks}
														date={result.date}
														extension={
															result.extension
														}
														hidden={result.hidden}
														id={result.id}
														index={index}
														key={result.id}
														onSelect={handleSelect}
														selected={addResultSelectedIds.includes(
															result.id
														)}
														title={result.title}
														type={result.type}
													/>
												)
											)}
										</ul>
									</div>

									{/*
									Temporarily hide pagination bar (LPS-101090) until
									'show more results' bug (LPS-96397) is fixed.

									<div className="add-result-container">
										<PaginationBar
											deltas={DELTAS}
											onDeltaChange={handleDeltaChange}
											onPageChange={handlePageChange}
											page={page}
											selectedDelta={selectedDelta}
											totalItems={results.total}
										/>
									</div>
									*/}
								</>
							) : (
								renderEmptyState()
							))}
					</div>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								className="btn-outline-borderless"
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={addResultSelectedIds.length === 0}
								onClick={handleSubmit(onClose)}
							>
								{Liferay.Language.get('add')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</div>
		</ClayModal>
	) : null;
}

AddResultModal.propTypes = {
	DELTAS: PropTypes.array.isRequired,
	addResultSearchQuery: PropTypes.string.isRequired,
	addResultSelectedIds: PropTypes.array.isRequired,
	dataLoading: PropTypes.bool.isRequired,
	getCurrentResultSelectedIds: PropTypes.func.isRequired,
	handleAllCheckbox: PropTypes.func.isRequired,
	handleClearAllSelected: PropTypes.func.isRequired,
	handleClose: PropTypes.func.isRequired,
	handleDeltaChange: PropTypes.func.isRequired,
	handlePageChange: PropTypes.func.isRequired,
	handleSearchChange: PropTypes.func.isRequired,
	handleSearchEnter: PropTypes.func.isRequired,
	handleSearchKeyDown: PropTypes.func.isRequired,
	handleSelect: PropTypes.func.isRequired,
	handleSubmit: PropTypes.func.isRequired,
	page: PropTypes.number.isRequired,
	renderEmptyState: PropTypes.func.isRequired,
	results: PropTypes.object.isRequired,
	selectedDelta: PropTypes.number.isRequired,
	showModal: PropTypes.bool.isRequired
};

export default AddResultModal;
