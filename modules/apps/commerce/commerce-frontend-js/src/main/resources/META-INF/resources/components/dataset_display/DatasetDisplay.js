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

import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {listenToBulkActionStatus} from '../../utilities/actionItems/bulkActions';
import {closest} from '../../utilities/closest';
import {
	DATASET_ACTION_PERFORMED,
	DATASET_DISPLAY_UPDATED,
	OPEN_MODAL,
	OPEN_SIDE_PANEL,
	SIDE_PANEL_CLOSED,
	UPDATE_DATASET_DISPLAY,
} from '../../utilities/eventsDefinitions';
import {executeAsyncAction, getRandomId, loadData} from '../../utilities/index';
import getJsModule from '../../utilities/modules';
import {
	showErrorNotification,
	showNotification,
} from '../../utilities/notifications';
import Modal from '../modal/Modal';
import SidePanel from '../side_panel/SidePanel';
import DatasetDisplayContext from './DatasetDisplayContext';
import EmptyResultMessage from './EmptyResultMessage';
import ManagementBar from './management_bar/index';
import {getViewById} from './views/index';

function DatasetDisplay(props) {
	const wrapperRef = useRef(null);
	const [views, updateViews] = useState(props.views);
	const [loading, setLoading] = useState(false);
	const [actionLoading, setActionLoading] = useState(false);
	const [datasetDisplaySupportSidePanelId] = useState(
		props.sidePanelId || 'support-side-panel-' + getRandomId()
	);

	const [datasetDisplaySupportModalId] = useState(
		'support-modal-' + getRandomId()
	);

	const [selectedItemsValue, setSelectedItemsValue] = useState(
		props.selectedItems || []
	);
	const [highlightedItemsValue, setHighlightedItemsValue] = useState([]);
	const [filters, updateFilters] = useState(props.filters);
	const [searchParam, updateSearchParam] = useState('');
	const [sorting, updateSorting] = useState(props.sorting);
	const [items, updateItems] = useState(props.items);
	const [pageNumber, setPageNumber] = useState(1);
	const [delta, setDelta] = useState(
		props.pagination.initialDelta || props.pagination.deltas[0].label
	);
	const [totalItems, setTotalItems] = useState(0);
	const [activeView, setActiveView] = useState(props.activeView || 0);
	const {
		component: CurrentViewComponent,
		contentRenderer,
		contentRendererModuleUrl: currentViewModuleUrl,
		...currentViewProps
	} = views[activeView];

	const selectable =
		props.bulkActions &&
		!!props.bulkActions.length &&
		props.selectedItemsKey;

	useEffect(() => {
		if (
			!CurrentViewComponent &&
			(currentViewModuleUrl || contentRenderer)
		) {
			setLoading(true);
			(contentRenderer
				? getViewById(contentRenderer)
				: getJsModule(currentViewModuleUrl)
			)
				.then((component) => {
					updateViews((views) =>
						views.map((view, i) =>
							i === activeView
								? {
										...view,
										component,
								  }
								: view
						)
					);
					setLoading(false);
				})
				.catch((err) => {
					showNotification(
						Liferay.Language.get('unexpected-error'),
						'danger'
					);
					setLoading(false);
					throw new Error(
						`Requested module: ${currentViewModuleUrl} not available`,
						err
					);
				});
		}
	}, [
		activeView,
		contentRenderer,
		views,
		currentViewModuleUrl,
		CurrentViewComponent,
		setLoading,
	]);

	const formRef = useRef(null);

	function updateDatasetItems(dataSetData) {
		setTotalItems(dataSetData.totalItems || dataSetData.totalCount || 0);
		updateItems(dataSetData.items);
	}

	function getData(
		apiUrl,
		currentUrl,
		delta,
		filters,
		pageNumber,
		searchParam,
		sorting,
		successNotification = {}
	) {
		setLoading(true);

		return loadData(
			apiUrl,
			currentUrl,
			delta,
			filters,
			pageNumber,
			searchParam,
			sorting
		)
			.then(updateDatasetItems)
			.then(() => {
				const {message, showSuccessNotification} = successNotification;

				if (showSuccessNotification) {
					const notificationMessage =
						message || Liferay.Language.get('table-data-updated');

					showNotification(notificationMessage, 'success');
				}

				setLoading(false);
				Liferay.fire(DATASET_DISPLAY_UPDATED, {id: props.id});
			})
			.catch((e) => {
				console.error(e);
				setLoading(false);

				showNotification(
					Liferay.Language.get('unexpected-error'),
					'danger'
				);
			});
	}

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const refreshData = (successNotification) =>
		getData(
			props.apiUrl,
			props.currentUrl,
			delta,
			filters.filter((e) => !!e.value),
			pageNumber,
			searchParam,
			sorting,
			successNotification
		);

	useEffect(() => {
		if (props.apiUrl) {
			getData(
				props.apiUrl,
				props.currentUrl,
				delta,
				filters.filter((e) => !!e.value),
				pageNumber,
				searchParam,
				sorting,
				false
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		props.apiUrl,
		props.currentUrl,
		filters,
		searchParam,
		delta,
		pageNumber,
		sorting,
		refreshData,
	]);

	useEffect(() => {
		const {apiUrl, items: injectedItems = []} = props,
			itemsAreInjected =
				!apiUrl &&
				!!injectedItems &&
				injectedItems.length !== items.length;

		if (itemsAreInjected) {
			updateDatasetItems({items: props.items});
		}
	}, [items, props, props.apiUrl, props.items]);

	function selectItems(val) {
		if (Array.isArray(val)) {
			return setSelectedItemsValue(val);
		}

		if (props.selectionType === 'single') {
			return setSelectedItemsValue([val]);
		}

		const itemAdded = selectedItemsValue.find((item) => item === val);

		if (itemAdded) {
			setSelectedItemsValue(
				selectedItemsValue.filter((el) => el !== val)
			);
		}
		else {
			setSelectedItemsValue(selectedItemsValue.concat(val));
		}
	}

	function highlightItems(val = []) {
		if (Array.isArray(val)) {
			return setHighlightedItemsValue(val);
		}

		const itemAdded = highlightedItemsValue.find((item) => item === val);

		if (!itemAdded) {
			setHighlightedItemsValue(highlightedItemsValue.concat(val));
		}
	}

	useEffect(() => {
		if (wrapperRef.current) {
			const form = closest(wrapperRef.current, 'form');
			if (form && form.getAttribute('data-senna-off') === null) {
				form.setAttribute('data-senna-off', true);
			}
		}
	}, [wrapperRef]);

	useEffect(() => {
		function handleRefreshFromTheOutside(e) {
			if (e.id === props.id) {
				refreshData();
			}
		}

		function handleCloseSidePanel(_e) {
			setHighlightedItemsValue([]);
		}

		if (
			(props.nestedItemsReferenceKey && !props.nestedItemsKey) ||
			(!props.nestedItemsReferenceKey && props.nestedItemsKey)
		) {
			throw new Error(
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
	}, [props.id]);

	const managementBar = props.showManagementBar ? (
		<div className="dataset-display-management-bar-wrapper">
			<ManagementBar
				activeView={activeView}
				bulkActions={props.bulkActions}
				creationMenuItems={props.creationMenuItems}
				filters={filters}
				fluid={props.style === 'fluid'}
				onFiltersChange={updateFilters}
				selectAllItems={() =>
					selectItems(
						items.map((item) => item[props.selectedItemsKey])
					)
				}
				selectable={selectable}
				selectedItemsKey={props.selectedItemsKey}
				selectedItemsValue={selectedItemsValue}
				selectionType={props.selectionType}
				setActiveView={setActiveView}
				showSearch={props.showSearch}
				sidePanelId={datasetDisplaySupportSidePanelId}
				totalItemsCount={items ? items.length : 0}
				views={props.views}
			/>
		</div>
	) : null;

	const view =
		!loading && CurrentViewComponent ? (
			<div className="dataset-display-content-wrapper">
				<input
					hidden
					name={`${props.namespace || props.id + '_'}${
						props.selectedItemsKey
					}`}
					readOnly
					value={selectedItemsValue.join(',')}
				/>
				{(items && items.length) || props.overrideEmptyResultView ? (
					<CurrentViewComponent
						datasetDisplayContext={DatasetDisplayContext}
						items={items}
						itemsActions={props.itemsActions}
						style={props.style}
						{...currentViewProps}
					/>
				) : null}
				{items && items.length === 0 && <EmptyResultMessage />}
			</div>
		) : (
			<span aria-hidden="true" className="loading-animation my-7" />
		);

	const wrappedView = props.formId ? view : <form ref={formRef}>{view}</form>;

	const pagination =
		props.showPagination && props.pagination && items && items.length ? (
			<div className="dataset-display-pagination-wrapper">
				<ClayPaginationBarWithBasicItems
					activeDelta={delta}
					activePage={pageNumber}
					className="mb-2"
					deltas={props.pagination.deltas}
					ellipsisBuffer={3}
					labels={{
						paginationResults: Liferay.Language.get(
							'showing-x-to-x-of-x-entries'
						),
						perPageItems: Liferay.Language.get('x-items-per-page'),
						selectPerPageItems: Liferay.Language.get(
							'x-items-per-page'
						),
					}}
					onDeltaChange={(deltaVal) => {
						setPageNumber(1);
						setDelta(deltaVal);
					}}
					onPageChange={setPageNumber}
					spritemap={props.spritemap}
					totalItems={totalItems}
				/>
			</div>
		) : null;

	function handleCompletedAction(e = null) {
		refreshData();
		Liferay.fire(DATASET_ACTION_PERFORMED, {
			id: props.id,
		});
		setActionLoading(false);
		if (e) {
			showNotification(e);
		}
	}

	function handleFailedAction(e) {
		showErrorNotification(Liferay.Language.get('unexpected-error'));
		setActionLoading(false);
		throw new Error(e);
	}

	function executeAsyncBulkAction(url, method, bodyKeys) {
		const bodyContent = items.reduce((foundData, item) => {
			if (selectedItemsValue.includes(item[props.selectedItemsKey])) {
				return [
					...foundData,
					bodyKeys.reduce(
						(keys, key) => ({...keys, [key]: item[key]}),
						{}
					),
				];
			}

			return foundData;
		}, []);
		setActionLoading(true);

		return executeAsyncAction(url, method, JSON.stringify(bodyContent))
			.then((response) => response.json())
			.then((jsonResponse) =>
				listenToBulkActionStatus(
					jsonResponse.id,
					props.batchTasksStatusApiUrl
				)
			)
			.then(handleCompletedAction)
			.catch(handleFailedAction);
	}

	function executeAsyncItemAction(url, method) {
		setActionLoading(true);

		return executeAsyncAction(url, method)
			.then((_) => handleCompletedAction())
			.catch(handleFailedAction);
	}

	function openSidePanel(config) {
		return Liferay.fire(OPEN_SIDE_PANEL, {
			id: datasetDisplaySupportSidePanelId,
			onSubmit: refreshData,
			...config,
		});
	}

	function openModal(config) {
		return Liferay.fire(OPEN_MODAL, {
			id: datasetDisplaySupportModalId,
			onSubmit: refreshData,
			...config,
		});
	}

	return (
		<DatasetDisplayContext.Provider
			value={{
				actionLoading,
				executeAsyncBulkAction,
				executeAsyncItemAction,
				formId: props.formId,
				formRef,
				highlightItems,
				highlightedItemsValue,
				itemsActions: props.itemsActions,
				loadData: refreshData,
				modalId: datasetDisplaySupportModalId,
				namespace: props.namespace,
				nestedItemsKey: props.nestedItemsKey,
				nestedItemsReferenceKey: props.nestedItemsReferenceKey,
				openModal,
				openSidePanel,
				searchParam,
				selectItems,
				selectable,
				selectedItemsKey: props.selectedItemsKey,
				selectedItemsValue,
				selectionType: props.selectionType,
				sidePanelId: datasetDisplaySupportSidePanelId,
				sorting,
				style: props.style,
				updateDatasetItems,
				updateSearchParam,
				updateSorting,
			}}
		>
			<ClayIconSpriteContext.Provider value={props.spritemap}>
				<Modal
					id={datasetDisplaySupportModalId}
					onClose={refreshData}
				/>
				{!props.sidePanelId && (
					<SidePanel
						id={datasetDisplaySupportSidePanelId}
						onAfterSubmit={refreshData}
					/>
				)}
				<div className="dataset-display-wrapper" ref={wrapperRef}>
					{props.style === 'default' && (
						<div className="dataset-display dataset-display-inline">
							{managementBar}
							{wrappedView}
							{pagination}
						</div>
					)}
					{props.style === 'stacked' && (
						<div className="dataset-display dataset-display-stacked">
							{managementBar}
							{wrappedView}
							{pagination}
						</div>
					)}
					{props.style === 'fluid' && (
						<div className="dataset-display dataset-display-fluid">
							{managementBar}
							<div className="container mt-3">
								{wrappedView}
								{pagination}
							</div>
						</div>
					)}
				</div>
			</ClayIconSpriteContext.Provider>
		</DatasetDisplayContext.Provider>
	);
}

DatasetDisplay.propTypes = {
	activeViewId: PropTypes.string,
	apiUrl: PropTypes.string,
	batchTasksStatusApiUrl: PropTypes.string,
	bulkActions: PropTypes.array,
	creationMenuItems: PropTypes.array,
	currentUrl: PropTypes.string,
	filters: PropTypes.array,
	formId: PropTypes.string,
	id: PropTypes.string.isRequired,
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
	spritemap: PropTypes.string.isRequired,
	style: PropTypes.oneOf(['default', 'fluid', 'stacked']),
	views: PropTypes.arrayOf(
		PropTypes.shape({
			component: PropTypes.any,
			contentRenderer: PropTypes.string,
			contentRendererModuleUrl: PropTypes.string,
			icon: PropTypes.string,
			label: PropTypes.string,
			schema: PropTypes.object,
		})
	).isRequired,
};

DatasetDisplay.defaultProps = {
	bulkActions: [],
	filters: [],
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

export default DatasetDisplay;
