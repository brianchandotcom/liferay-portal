/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import {ClayModalProvider, Context as ClayModalContext} from '@clayui/modal';
import ClayProgressBar from '@clayui/progress-bar';
import {fetch, openToast} from 'frontend-js-web';
import React, {useContext, useRef, useState} from 'react';

import {
	EXECUTION_MODES,
	PORTAL_TOOLTIP_TRIGGER_CLASS,
	SCOPES,
} from './constants';
import ConfirmationModalBody from './execution_options/ConfirmationModalBody';
import ExecutionOptions from './execution_options/index';

function IndexerListItem({
	className,
	cmd = 'reindex',
	disabled = false,
	displayName,
	id,
	onClick,
	progressPercentage,
}) {
	const _handleClick = () => {
		const data = {cmd, displayName, id};

		if (className) {
			data.className = className;
		}

		onClick(data);
	};

	return (
		<ClayList.Item flex>
			<ClayList.ItemField expand>
				<ClayList.ItemTitle>
					<span style={{wordBreak: 'break-word'}}>
						{className
							? `${displayName} (${className})`
							: displayName}
					</span>
				</ClayList.ItemTitle>
			</ClayList.ItemField>

			<ClayList.ItemField className="index-action-wrapper">
				{typeof progressPercentage === 'number' &&
				progressPercentage >= 0 ? (
					<ClayProgressBar value={progressPercentage} />
				) : (
					<ClayButton
						className="save-server-button"
						disabled={disabled}
						displayType="secondary"
						onClick={_handleClick}
					>
						{Liferay.Language.get('reindex')}
					</ClayButton>
				)}
			</ClayList.ItemField>
		</ClayList.Item>
	);
}

function IndexActions({
	controlMenuCategoryKey,
	elasticSearchDiskSpace = {
		availableDiskSpace: 0,
		currentDiskSpaceUsed: 0,
		isLowOnDiskSpace: false,
	},
	indexersMap = {},
	initialCompanyIds,
	initialExecutionMode,
	initialScope,
	isConcurrentModeSupported = true,
	virtualInstances = [],
	indexReindexerNames = [],
	portletNamespace,
	redirectURL = '',
	reindexURL = '',
}) {
	const [backgroundTaskMap, setBackgroundTaskMap] = useState({});
	const [selectedCompanyIds, setSelectedCompanyIds] = useState(
		initialCompanyIds || []
	);
	const [executionMode, setExecutionMode] = useState(
		initialExecutionMode || EXECUTION_MODES.FULL.value
	);
	const [executionScope, setExecutionScope] = useState(
		initialScope || SCOPES.ALL
	);

	const [state, dispatch] = useContext(ClayModalContext);

	const intervalRef = useRef(null);

	/*
	 * Returns the list of virtual instances to reindex, depending on the
	 * `executionScope` and `selectedCompanyIds`.
	 */
	const _getCompanyIds = () => {
		return executionScope === SCOPES.ALL
			? virtualInstances.map(({id}) => id)
			: selectedCompanyIds;
	};

	/*
	 * Executed after performing the reindex call. Continuously fetches the
	 * view of the current URL to get status of the background task.
	 * Calls `_handleBackgroundTaskStop` within when there are no more
	 * background tasks running.
	 */
	const _handleBackgroundTaskStart = () => {
		const fetchRedirectURL = new URL(redirectURL);

		if (!intervalRef.current) {
			intervalRef.current = setInterval(() => {
				fetch(fetchRedirectURL, {method: 'GET'})
					.then((response) => response.text())
					.then((text) => {
						const parser = new DOMParser();
						const htmlDocument = parser.parseFromString(
							text,
							'text/html'
						);
						const backgroundTaskMapString = htmlDocument.documentElement
							.querySelector(
								`#${portletNamespace}classNameToBackgroundTaskMap`
							)
							?.innerHTML?.trim();

						const newBackgroundTaskMap =
							JSON.parse(backgroundTaskMapString) || {};

						if (Object.keys(newBackgroundTaskMap).length) {
							setBackgroundTaskMap(newBackgroundTaskMap);
						}
						else {
							_handleBackgroundTaskStop();

							_handleSyncIconRemove();

							openToast({
								message: Liferay.Language.get(
									'reindexing-finished-successfully'
								),
								type: 'success',
							});
						}
					})
					.catch(() => {
						_handleBackgroundTaskStop();

						_handleSyncIconRemove();

						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							type: 'danger',
						});
					});
			}, 1000);
		}
	};

	/*
	 * Clears the interval involved with continuously fetching the background task
	 * status. Also clears the background task map.
	 */
	const _handleBackgroundTaskStop = () => {
		clearInterval(intervalRef.current);

		intervalRef.current = null;

		setBackgroundTaskMap({});
	};

	/*
	 * Fired after clicking on Confirmation Modal's "Execute" button.
	 * If the "Do not show me this again" checkbox is checked, this
	 * preference setting is saved in the local storage.
	 */
	const _handleConfirmationModalVisibilitySave = () => {
		if (
			document.getElementById(`${portletNamespace}hideModalCheckbox`)
				?.checked
		) {
			Liferay.Util.LocalStorage?.setItem(
				`${portletNamespace}${Liferay.ThemeDisplay.getUserId()}_hideReindexConfirmationModal`,
				true,
				Liferay.Util.LocalStorage.TYPES.FUNCTIONAL
			);
		}
	};

	/*
	 * First step when clicking on one of the indexer's action button (labeled
	 * "Reindex"). Validates for virtual instances and shows the confirmation modal,
	 * if expected. If ok, then calls `_handleReindex`.
	 */
	const _handleIndexerItemClick = (data) => {
		if (!_getCompanyIds().length) {
			openToast({
				message: Liferay.Language.get('missing-instance-error'),
				type: 'danger',
			});

			return;
		}

		// Determine whether confirmation modal should be shown

		const isConcurrentMode =
			executionMode === EXECUTION_MODES.CONCURRENT.value;

		const status =
			isConcurrentMode && elasticSearchDiskSpace.isLowOnDiskSpace
				? 'warning'
				: 'info';

		const hideModal = Liferay.Util.LocalStorage?.getItem(
			`${portletNamespace}${Liferay.ThemeDisplay.getUserId()}_hideReindexConfirmationModal`,
			Liferay.Util.LocalStorage.TYPES.FUNCTIONAL
		);

		if (
			!hideModal ||
			(isConcurrentMode && elasticSearchDiskSpace.isLowOnDiskSpace)
		) {
			dispatch({
				payload: {
					body: (
						<ConfirmationModalBody
							availableDiskSpace={
								elasticSearchDiskSpace.availableDiskSpace
							}
							cmd={data.cmd}
							currentDiskSpaceUsed={
								elasticSearchDiskSpace.currentDiskSpaceUsed
							}
							executionMode={executionMode}
							isLowOnDiskSpace={
								elasticSearchDiskSpace.isLowOnDiskSpace
							}
							portletNamespace={portletNamespace}
						/>
					),
					footer: [
						<></>,
						<></>,
						<ClayButton.Group key={0} spaced>
							<ClayButton
								displayType="secondary"
								key={2}
								onClick={state.onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								displayType="primary"
								key={3}
								onClick={() => {
									_handleConfirmationModalVisibilitySave();

									state.onClose();

									_handleReindex(data);
								}}
							>
								{Liferay.Language.get('execute')}
							</ClayButton>
						</ClayButton.Group>,
					],
					header:
						isConcurrentMode &&
						elasticSearchDiskSpace.isLowOnDiskSpace
							? Liferay.Language.get(
									'reindex-elasticsearch-disk-space-warning'
							  )
							: data.id === 'spellCheckDictionaries'
							? Liferay.Language.get(
									'reindex-spell-check-dictionaries'
							  )
							: data.id === 'portal'
							? Liferay.Language.get('reindex-search-indexes')
							: Liferay.Util.sub(
									Liferay.Language.get('reindex-type-x'),
									'<' + data.displayName + '>'
							  ),
					size: 'md',
					status,
				},
				type: 1,
			});
		}
		else {
			_handleReindex(data);
		}
	};

	/*
	 * Performs the reindex fetch call. Appends the necessary parameters and
	 * sets up the properties to start the background task visuals.
	 * Fired inside `_handleIndexerItemClick`.
	 */
	const _handleReindex = (data) => {
		const fetchReindexURL = new URL(reindexURL);

		Object.entries({
			...data,
			companyIds: _getCompanyIds(),
			executionMode,
		}).forEach(([property, value]) => {
			if (value) {
				fetchReindexURL.searchParams.append(
					`${portletNamespace}${property}`,
					value
				);
			}
		});

		setBackgroundTaskMap({
			...backgroundTaskMap,
			[data.id]: 0,
		});

		_handleSyncIconAppend();

		fetch(fetchReindexURL, {method: 'POST'})
			.then((response) => response.text())
			.then(() => {
				if (data.id !== 'spellCheckDictionaries') {
					_handleBackgroundTaskStart();
				}
				else {
					setBackgroundTaskMap({});

					_handleSyncIconRemove();

					openToast({
						message: Liferay.Language.get(
							'reindexing-finished-successfully'
						),
						type: 'success',
					});
				}
			})
			.catch(() => {
				setBackgroundTaskMap({});

				_handleSyncIconRemove();

				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	};

	/*
	 * Appends sync icon to the control menu, if not already there.
	 * Called after the reindex fetch.
	 */
	const _handleSyncIconAppend = () => {
		const currentControlMenu = document.getElementById(
			`${portletNamespace}controlMenu`
		);

		const currentControlMenuCategory = currentControlMenu.querySelector(
			`.${controlMenuCategoryKey}-control-group .control-menu-nav`
		);

		if (
			!currentControlMenuCategory.querySelector(
				`#${portletNamespace}syncIcon`
			)
		) {
			const syncIcon = document.createElement('div');

			syncIcon.className = PORTAL_TOOLTIP_TRIGGER_CLASS;
			syncIcon.setAttribute(
				'data-title',
				Liferay.Language.get('the-portal-is-currently-reindexing')
			);
			syncIcon.id = `${portletNamespace}syncIcon`;
			syncIcon.innerHTML = `
			<svg class="lexicon-icon" focusable="false">
				<use href="${Liferay.Icons.spritemap}#reload" />
			</svg>`;

			currentControlMenuCategory.appendChild(syncIcon);
		}
	};

	/*
	 * Removes sync icon to the control menu, if not already removed.
	 * Called after the background tasks complete.
	 */
	const _handleSyncIconRemove = () => {
		const currentControlMenu = document.getElementById(
			`${portletNamespace}controlMenu`
		);

		const syncIcon = currentControlMenu.querySelector(
			`.${controlMenuCategoryKey}-control-group .control-menu-nav #${portletNamespace}syncIcon`
		);

		if (syncIcon?.parentNode) {
			syncIcon.parentNode.removeChild(syncIcon);
		}
	};

	/*
	 * Returns true if any background task is running for the list of indexers.
	 * If no list is provided, returns true if there are any background tasks
	 * running.
	 */
	const _isBackgroundTaskRunning = (indexers = []) => {
		if (indexers.length) {
			return indexers.some(
				(type) => typeof backgroundTaskMap[type] !== 'undefined'
			);
		}

		return !!Object.keys(backgroundTaskMap).length;
	};

	return (
		<ClayLayout.Container
			className="search-admin-index-actions-container"
			fluidSize="xl"
			formSize="lg"
			id={`${portletNamespace}adminSearchAdminIndexActionsPanel`}
		>
			<ClayLayout.ContainerFluid view>
				<ClayLayout.Row>
					<ClayLayout.Col size={4}>
						<ExecutionOptions
							companyIds={selectedCompanyIds}
							executionMode={executionMode}
							executionScope={executionScope}
							isConcurrentModeSupported={
								isConcurrentModeSupported
							}
							portletNamespace={portletNamespace}
							setCompanyIds={setSelectedCompanyIds}
							setExecutionMode={setExecutionMode}
							setExecutionScope={setExecutionScope}
							virtualInstances={virtualInstances}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col size={8}>
						<div className="sheet sheet-lg">
							<h2 className="sheet-title">
								{Liferay.Language.get('actions')}
							</h2>

							<ClayList>
								<ClayList.Header>
									{Liferay.Language.get('global')}
								</ClayList.Header>

								<IndexerListItem
									disabled={_isBackgroundTaskRunning()}
									displayName={Liferay.Language.get(
										'all-search-indexes'
									)}
									id="portal"
									onClick={_handleIndexerItemClick}
									progressPercentage={
										backgroundTaskMap['portal']
									}
								/>

								<IndexerListItem
									cmd="reindexDictionaries"
									disabled={
										executionMode ===
											EXECUTION_MODES.CONCURRENT.value ||
										_isBackgroundTaskRunning([
											'portal',
											'spellCheckDictionaries',
										])
									}
									displayName={Liferay.Language.get(
										'all-spell-check-dictionaries'
									)}
									id="spellCheckDictionaries"
									onClick={_handleIndexerItemClick}
								/>

								{Object.keys(indexersMap)
									.sort()
									.map((category) => (
										<React.Fragment key={category}>
											<ClayList.Header>
												{category}
											</ClayList.Header>

											{indexersMap[category].map(
												({
													className,
													displayName,
													enabled,
												}) => (
													<IndexerListItem
														className={className}
														disabled={
															!enabled ||
															executionMode ===
																EXECUTION_MODES
																	.CONCURRENT
																	.value ||
															_isBackgroundTaskRunning(
																[
																	'portal',
																	'spellCheckDictionaries',
																]
															)
														}
														displayName={
															displayName
														}
														id={className}
														key={className}
														onClick={
															_handleIndexerItemClick
														}
														progressPercentage={
															backgroundTaskMap[
																className
															]
														}
													/>
												)
											)}
										</React.Fragment>
									))}

								{!!indexReindexerNames.length && (
									<ClayList.Header>
										{Liferay.Language.get('search-tuning')}
									</ClayList.Header>
								)}

								{indexReindexerNames.map(
									({className, displayName}) => (
										<IndexerListItem
											className={className}
											cmd="reindexIndexReindexer"
											disabled={
												executionMode ===
													EXECUTION_MODES.CONCURRENT
														.value ||
												_isBackgroundTaskRunning([
													'portal',
													'spellCheckDictionaries',
												])
											}
											displayName={displayName}
											id={className}
											key={className}
											onClick={_handleIndexerItemClick}
											progressPercentage={
												backgroundTaskMap[className]
											}
										/>
									)
								)}
							</ClayList>
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</ClayLayout.Container>
	);
}

export default function ({
	data = {},
	portletNamespace,
	redirectURL = '',
	reindexURL = '',
}) {
	const {
		controlMenuCategoryKey,
		elasticSearchDiskSpace,
		indexReindexerNames,
		indexersMap,
		initialCompanyIds,
		initialExecutionMode,
		initialScope,
		isConcurrentModeSupported,
		virtualInstances,
	} = data;

	return (
		<ClayModalProvider>
			<IndexActions
				controlMenuCategoryKey={controlMenuCategoryKey}
				elasticSearchDiskSpace={elasticSearchDiskSpace}
				indexReindexerNames={indexReindexerNames}
				indexersMap={indexersMap}
				initialCompanyIds={initialCompanyIds}
				initialExecutionMode={initialExecutionMode}
				initialScope={initialScope}
				isConcurrentModeSupported={isConcurrentModeSupported}
				portletNamespace={portletNamespace}
				redirectURL={redirectURL}
				reindexURL={reindexURL}
				virtualInstances={virtualInstances}
			/>
		</ClayModalProvider>
	);
}
