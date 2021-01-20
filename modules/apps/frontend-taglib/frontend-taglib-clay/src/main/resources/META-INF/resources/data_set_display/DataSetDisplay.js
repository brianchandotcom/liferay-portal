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

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import {AppContext} from './AppContext';
import DataSetDisplayContext from './DataSetDisplayContext';
import EmptyResultMessage from './EmptyResultMessage';
import {updateViewComponent} from './actions/updateViewComponent';
import ManagementBar from './management_bar/ManagementBar';
import Modal from './modal/Modal';
import SidePanel from './side_panel/SidePanel';
import {
	DATASET_ACTION_PERFORMED,
	DATASET_DISPLAY_UPDATED,
	OPEN_MODAL,
	OPEN_SIDE_PANEL,
	SIDE_PANEL_CLOSED,
	UPDATE_DATASET_DISPLAY,
} from './utilities/eventsDefinitions';
import {
	delay,
	executeAsyncAction,
	formatItemChanges,
	getCurrentItemUpdates,
	getRandomId,
	loadData,
} from './utilities/index';
import {logError} from './utilities/logError';
import getJsModule from './utilities/modules';
import ViewsContext from './views/ViewsContext';
import {getViewContentRenderer} from './views/index';

function DataSetDisplay({
	actionParameterName,
	bulkActions,
	creationMenu,
	currentURL,
	filters: filtersProp,
	formId,
	id,
	inlineAddingSettings,
	inlineEditingSettings,
	items: itemsProp,
	itemsActions,
	namespace,
	nestedItemsKey,
	nestedItemsReferenceKey,
	overrideEmptyResultView,
	pagination,
	selectedItems,
	selectedItemsKey,
	selectionType,
	showManagementBar,
	showPagination,
	showSearch,
	sidePanelId,
	sorting: sortingProp,
	style,
}) {
	const {apiURL} = useContext(AppContext);

	const wrapperRef = useRef(null);
	const [componentLoading, setComponentLoading] = useState(false);
	const [dataLoading, setDataLoading] = useState(!!apiURL);
	const [dataSetDisplaySupportModalId] = useState(
		`support-modal-${getRandomId()}`
	);
	const [dataSetDisplaySupportSidePanelId] = useState(
		sidePanelId || `support-side-panel-${getRandomId()}`
	);
	const [delta, setDelta] = useState(
		showPagination &&
			(pagination.initialDelta || pagination.deltas[0].label)
	);
	const [filters, updateFilters] = useState(filtersProp);
	const [highlightedItemsValue, setHighlightedItemsValue] = useState([]);
	const [items, updateItems] = useState(itemsProp);
	const [itemsChanges, updateItemsChanges] = useState({});
	const [pageNumber, setPageNumber] = useState(1);
	const [searchParam, updateSearchParam] = useState('');
	const [selectedItemsValue, setSelectedItemsValue] = useState(
		selectedItems || []
	);
	const [sorting, updateSorting] = useState(sortingProp);
	const [total, setTotal] = useState(0);
	const [{activeView}, dispatch] = useContext(ViewsContext);

	const {
		component: CurrentViewComponent,
		contentRenderer,
		contentRendererModuleURL,
		name: activeViewName,
		...currentViewProps
	} = activeView;

	const selectable = !!(bulkActions?.length && selectedItemsKey);

	const requestData = useCallback(() => {
		const activeFiltersOdataStrings = filters.reduce(
			(activeFilters, filter) =>
				filter.odataFilterString
					? [...activeFilters, filter.odataFilterString]
					: activeFilters,
			[]
		);

		return loadData(
			apiURL,
			currentURL,
			activeFiltersOdataStrings,
			searchParam,
			delta,
			pageNumber,
			sorting
		).catch((error) => {
			logError(error);
			openToast({
				message: Liferay.Language.get('unexpected-error'),
				type: 'danger',
			});

			throw error;
		});
	}, [apiURL, currentURL, delta, filters, pageNumber, searchParam, sorting]);

	const requestComponent = useCallback(() => {
		if (
			!CurrentViewComponent &&
			(contentRendererModuleURL || contentRenderer)
		) {
			return (contentRenderer
				? getViewContentRenderer(contentRenderer)
				: getJsModule(contentRendererModuleURL)
			).catch((error) => {
				logError(
					`Requested module: ${contentRendererModuleURL} not available`,
					error
				);
				openToast({
					message: Liferay.Language.get('unexpected-error'),
					type: 'danger',
				});

				throw error;
			});
		}

		return Promise.resolve(CurrentViewComponent);
	}, [contentRenderer, contentRendererModuleURL, CurrentViewComponent]);

	const isMounted = useIsMounted();

	function updateDataSetItems(dataSetData) {
		setTotal(dataSetData.totalCount);
		updateItems(dataSetData.items);
	}

	useEffect(() => {
		const itemsAreInjected = !apiURL && itemsProp?.length !== items.length;

		if (itemsAreInjected) {
			updateDataSetItems({items: itemsProp});
		}
	}, [items, apiURL, itemsProp]);

	function selectItems(value) {
		if (Array.isArray(value)) {
			return setSelectedItemsValue(value);
		}

		if (selectionType === 'single') {
			return setSelectedItemsValue([value]);
		}

		const itemAdded = selectedItemsValue.find((item) => item === value);

		if (itemAdded) {
			setSelectedItemsValue(
				selectedItemsValue.filter((element) => element !== value)
			);
		}
		else {
			setSelectedItemsValue(selectedItemsValue.concat(value));
		}
	}

	function highlightItems(value = []) {
		if (Array.isArray(value)) {
			return setHighlightedItemsValue(value);
		}

		const itemAdded = highlightedItemsValue.find((item) => item === value);

		if (!itemAdded) {
			setHighlightedItemsValue(highlightedItemsValue.concat(value));
		}
	}

	useEffect(() => {
		if (wrapperRef.current) {
			const form = wrapperRef.current.closest('form');

			if (form?.getAttribute('data-senna-off') === null) {
				form.setAttribute('data-senna-off', true);
			}
		}
	}, [wrapperRef]);

	function refreshData(successNotification) {
		setDataLoading(true);

		return requestData()
			.then((data) => {
				if (successNotification?.showSuccessNotification) {
					openToast({
						message:
							successNotification.message ||
							Liferay.Language.get('table-data-updated'),
						type: 'success',
					});
				}

				if (isMounted()) {
					updateDataSetItems(data);

					setDataLoading(false);

					Liferay.fire(DATASET_DISPLAY_UPDATED, {id});
				}

				return data;
			})
			.catch((error) => {
				logError(error);
				setDataLoading(false);

				throw error;
			});
	}

	useEffect(() => {
		setComponentLoading(true);

		requestComponent().then((component) => {
			if (isMounted()) {
				setComponentLoading(false);
				dispatch(updateViewComponent(activeViewName, component));
			}
		});
	}, [
		activeViewName,
		dispatch,
		isMounted,
		requestComponent,
		setComponentLoading,
	]);

	useEffect(() => {
		setDataLoading(true);

		requestData().then((data) => {
			if (isMounted()) {
				updateDataSetItems(data);

				setDataLoading(false);
			}
		});
	}, [isMounted, requestData, setDataLoading]);

	useEffect(() => {
		function handleRefreshFromTheOutside(event) {
			if (event.id === id) {
				refreshData();
			}
		}

		function handleCloseSidePanel() {
			setHighlightedItemsValue([]);
		}

		if (
			(nestedItemsReferenceKey && !nestedItemsKey) ||
			(!nestedItemsReferenceKey && nestedItemsKey)
		) {
			logError(
				'"nestedItemsKey" and "nestedItemsReferenceKey" params are both mandatory to manage nested items'
			);
		}

		Liferay.on(SIDE_PANEL_CLOSED, handleCloseSidePanel);
		Liferay.on(UPDATE_DATASET_DISPLAY, handleRefreshFromTheOutside);

		return () => {
			Liferay.detach(SIDE_PANEL_CLOSED, handleCloseSidePanel);
			Liferay.detach(UPDATE_DATASET_DISPLAY, handleRefreshFromTheOutside);
		};

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [id]);

	const managementBar = showManagementBar ? (
		<div className="data-set-display-management-bar-wrapper">
			<ManagementBar
				bulkActions={bulkActions}
				creationMenu={creationMenu}
				filters={filters}
				fluid={style === 'fluid'}
				onFiltersChange={updateFilters}
				selectAllItems={() =>
					selectItems(items.map((item) => item[selectedItemsKey]))
				}
				selectedItemsKey={selectedItemsKey}
				selectedItemsValue={selectedItemsValue}
				selectionType={selectionType}
				showSearch={showSearch}
				sidePanelId={dataSetDisplaySupportSidePanelId}
				total={items?.length ?? 0}
			/>
		</div>
	) : null;

	const view =
		!dataLoading && !componentLoading ? (
			<div className="data-set-display-content-wrapper">
				<input
					hidden
					name={`${namespace || id + '_'}${
						actionParameterName || selectedItemsKey
					}`}
					readOnly
					value={selectedItemsValue.join(',')}
				/>
				{items.length ||
				overrideEmptyResultView ||
				inlineAddingSettings ? (
					<CurrentViewComponent
						dataSetDisplayContext={DataSetDisplayContext}
						items={items}
						itemsActions={itemsActions}
						style={style}
						{...currentViewProps}
					/>
				) : (
					<EmptyResultMessage />
				)}
			</div>
		) : (
			<span aria-hidden="true" className="loading-animation my-7" />
		);

	const formRef = useRef(null);

	const wrappedView = formId ? view : <form ref={formRef}>{view}</form>;

	const paginationComponent =
		showPagination && pagination && items?.length ? (
			<div className="data-set-display-pagination-wrapper">
				<ClayPaginationBarWithBasicItems
					activeDelta={delta}
					activePage={pageNumber}
					deltas={pagination.deltas}
					ellipsisBuffer={3}
					onDeltaChange={(deltaVal) => {
						setPageNumber(1);
						setDelta(deltaVal);
					}}
					onPageChange={setPageNumber}
					totalItems={total}
				/>
			</div>
		) : null;

	function executeAsyncItemAction(url, method) {
		return executeAsyncAction(url, method)
			.then((_) => {
				return delay(500).then(() => {
					if (isMounted()) {
						Liferay.fire(DATASET_ACTION_PERFORMED, {
							id,
						});

						return refreshData();
					}
				});
			})
			.catch((error) => {
				logError(error);
				openToast({
					message: Liferay.Language.get('unexpected-error'),
					type: 'danger',
				});
			});
	}

	function openSidePanel(config) {
		return Liferay.fire(OPEN_SIDE_PANEL, {
			id: dataSetDisplaySupportSidePanelId,
			onSubmit: refreshData,
			...config,
		});
	}

	function openModal(config) {
		return Liferay.fire(OPEN_MODAL, {
			id: dataSetDisplaySupportModalId,
			onSubmit: refreshData,
			...config,
		});
	}

	function updateItem(itemKey, property, valuePath, value = null) {
		const itemChanges = getCurrentItemUpdates(
			items,
			itemsChanges,
			selectedItemsKey,
			itemKey,
			property,
			value,
			valuePath
		);

		updateItemsChanges({
			...itemsChanges,
			[itemKey]: itemChanges,
		});
	}

	function toggleItemInlineEdit(itemKey) {
		updateItemsChanges(({[itemKey]: foundItem, ...itemsChanges}) => {
			return foundItem
				? itemsChanges
				: {
						...itemsChanges,
						[itemKey]: {},
				  };
		});
	}

	function createInlineItem() {
		const defaultBodyContent =
			inlineAddingSettings.defaultBodyContent || {};
		const newItemBodyContent = formatItemChanges(itemsChanges[0]);

		return fetch(inlineAddingSettings.apiURL, {
			body: JSON.stringify({
				...defaultBodyContent,
				...newItemBodyContent,
			}),
			headers: {
				Accept: 'application/json',
				'Content-Type': 'application/json',
			},
			method: inlineAddingSettings.method || 'POST',
		})
			.then((response) => {
				if (!isMounted()) {
					return;
				}

				if (!response.ok) {
					return response
						.json()
						.then((jsonResponse) =>
							Promise.reject(new Error(jsonResponse.title))
						);
				}

				updateItemsChanges((itemsChanges) => ({
					...itemsChanges,
					[0]: {},
				}));

				return refreshData({
					message: Liferay.Language.get('item-successfully-created'),
					showSuccessNotification: true,
				});
			})
			.catch((error) => {
				logError(error);
				openToast({
					message: error.message,
					type: 'danger',
				});

				throw error;
			});
	}

	function applyItemInlineUpdates(itemKey) {
		const itemToBeUpdated = items.find(
			(item) => item[selectedItemsKey] === itemKey
		);

		const defaultBody = inlineEditingSettings.defaultBodyContent || {};

		return fetch(itemToBeUpdated.actions.update.href, {
			body: JSON.stringify({
				...defaultBody,
				...formatItemChanges(itemsChanges[itemKey]),
			}),
			headers: {
				Accept: 'application/json',
				'Content-Type': 'application/json',
			},
			method: itemToBeUpdated.actions.update.method,
		})
			.then((response) => {
				if (!isMounted()) {
					return;
				}

				if (!response.ok) {
					return response
						.json()
						.then((jsonResponse) =>
							Promise.reject(new Error(jsonResponse.title))
						);
				}

				toggleItemInlineEdit(itemKey);

				return refreshData({
					message: Liferay.Language.get('item-successfully-updated'),
					showSuccessNotification: true,
				});
			})
			.catch((error) => {
				logError(error);
				openToast({
					message: error.message,
					type: 'danger',
				});

				throw error;
			});
	}

	return (
		<DataSetDisplayContext.Provider
			value={{
				actionParameterName,
				applyItemInlineUpdates,
				createInlineItem,
				executeAsyncItemAction,
				formId,
				formRef,
				highlightItems,
				highlightedItemsValue,
				id,
				inlineAddingSettings,
				inlineEditingSettings,
				itemsActions,
				itemsChanges,
				loadData: refreshData,
				modalId: dataSetDisplaySupportModalId,
				namespace,
				nestedItemsKey,
				nestedItemsReferenceKey,
				openModal,
				openSidePanel,
				searchParam,
				selectItems,
				selectable,
				selectedItemsKey,
				selectedItemsValue,
				selectionType,
				sidePanelId: dataSetDisplaySupportSidePanelId,
				sorting,
				style,
				toggleItemInlineEdit,
				updateDataSetItems,
				updateItem,
				updateSearchParam,
				updateSorting,
			}}
		>
			<Modal id={dataSetDisplaySupportModalId} onClose={refreshData} />

			{!sidePanelId && (
				<SidePanel
					id={dataSetDisplaySupportSidePanelId}
					onAfterSubmit={refreshData}
				/>
			)}

			<div className="data-set-display-wrapper" ref={wrapperRef}>
				{style === 'default' && (
					<div className="data-set-display data-set-display-inline">
						{managementBar}
						{wrappedView}
						{paginationComponent}
					</div>
				)}
				{style === 'stacked' && (
					<div className="data-set-display data-set-display-stacked">
						{managementBar}
						{wrappedView}
						{paginationComponent}
					</div>
				)}
				{style === 'fluid' && (
					<div className="data-set-display data-set-display-fluid">
						{managementBar}
						<div className="container-fluid container-xl mt-3">
							{wrappedView}
							{paginationComponent}
						</div>
					</div>
				)}
			</div>
		</DataSetDisplayContext.Provider>
	);
}

DataSetDisplay.propTypes = {
	apiURL: PropTypes.string,
	bulkActions: PropTypes.array,
	creationMenu: PropTypes.shape({
		primaryItems: PropTypes.array,
		secondaryItems: PropTypes.array,
	}),
	currentURL: PropTypes.string,
	enableInlineAddModeSetting: PropTypes.shape({
		defaultBodyContent: PropTypes.object,
	}),
	filters: PropTypes.array,
	formId: PropTypes.string,
	id: PropTypes.string.isRequired,
	inlineAddingSettings: PropTypes.shape({
		apiURL: PropTypes.string.isRequired,
		defaultBodyContent: PropTypes.object,
	}),
	inlineEditingSettings: PropTypes.oneOfType([
		PropTypes.bool,
		PropTypes.shape({
			alwaysOn: PropTypes.bool,
			defaultBodyContent: PropTypes.object,
		}),
	]),
	items: PropTypes.array,
	itemsActions: PropTypes.array,
	namespace: PropTypes.string,
	nestedItemsKey: PropTypes.string,
	nestedItemsReferenceKey: PropTypes.string,
	overrideEmptyResultView: PropTypes.bool,
	pagination: PropTypes.shape({
		deltas: PropTypes.arrayOf(
			PropTypes.shape({
				href: PropTypes.string,
				label: PropTypes.number.isRequired,
			}).isRequired
		),
		initialDelta: PropTypes.number.isRequired,
	}),
	selectedItems: PropTypes.array,
	selectedItemsKey: PropTypes.string,
	selectionType: PropTypes.oneOf(['single', 'multiple']),
	showManagementBar: PropTypes.bool,
	showPagination: PropTypes.bool,
	showSearch: PropTypes.bool,
	sidePanelId: PropTypes.string,
	sorting: PropTypes.arrayOf(
		PropTypes.shape({
			direction: PropTypes.oneOf(['asc', 'desc']),
			key: PropTypes.string,
		})
	),
	style: PropTypes.oneOf(['default', 'fluid', 'stacked']),
};

DataSetDisplay.defaultProps = {
	bulkActions: [],
	filters: [],
	inlineEditingSettings: null,
	items: null,
	itemsActions: null,
	pagination: {
		initialDelta: 10,
	},
	selectedItemsKey: 'id',
	selectionType: 'multiple',
	showManagementBar: true,
	showPagination: true,
	showSearch: true,
	sorting: [],
	style: 'default',
};

export default DataSetDisplay;
