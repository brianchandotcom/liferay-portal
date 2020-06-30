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
import React, {useContext, useEffect, useRef, useState} from 'react';

import DatasetDisplayContext from './DatasetDisplayContext';
import EmptyResultMessage from './EmptyResultMessage';
import ManagementBar from './management_bar/index';
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
import {executeAsyncAction, getRandomId, loadData} from './utilities/index';
import {logError} from './utilities/logError';
import getJsModule from './utilities/modules';
import {showNotification} from './utilities/notifications';
import {getViewById} from './views/index';

function DatasetDisplay({
	actionParameterName,
	apiUrl,
	bulkActions,
	creationMenu,
	currentUrl,
	filters: filtersProp,
	formId,
	id,
	items: itemsProp,
	itemsActions,
	namespace,
	nestedItemsKey,
	nestedItemsReferenceKey,
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
	views: viewsProp,
}) {
	const wrapperRef = useRef(null);
	const [views, updateViews] = useState(viewsProp);
	const [loading, setLoading] = useState(false);
	const [datasetDisplaySupportSidePanelId] = useState(
		sidePanelId || 'support-side-panel-' + getRandomId()
	);

	const [datasetDisplaySupportModalId] = useState(
		'support-modal-' + getRandomId()
	);

	const [selectedItemsValue, setSelectedItemsValue] = useState(
		selectedItems || []
	);
	const [highlightedItemsValue, setHighlightedItemsValue] = useState([]);
	const [filters, updateFilters] = useState(filtersProp);
	const [searchParam, updateSearchParam] = useState('');
	const [sorting, updateSorting] = useState(sortingProp);
	const [items, updateItems] = useState(itemsProp);
	const [pageNumber, setPageNumber] = useState(1);
	const [delta, setDelta] = useState(
		pagination.initialDelta || pagination.deltas[0].label
	);
	const [total, setTotal] = useState(0);
	const [activeView, setActiveView] = useState(activeView || 0);
	const {
		component: CurrentViewComponent,
		contentRenderer,
		contentRendererModuleUrl: currentViewModuleUrl,
		...currentViewProps
	} = views[activeView];

	const selectable = bulkActions?.length && selectedItemsKey;

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

	function updateDataset(dataSetData) {
		setTotal(dataSetData.total || 0);
		updateItems(dataSetData.items);
	}

	function getData(
		apiUrl,
		currentUrl,
		filters,
		searchParam,
		delta,
		pageNumber,
		sorting,
		successNotification = {}
	) {
		setLoading(true);

		return loadData(
			apiUrl,
			currentUrl,
			filters,
			searchParam,
			delta,
			pageNumber,
			sorting
		)
			.then(updateDataset)
			.then(() => {
				const {message, showSuccessNotification} = successNotification;

				if (showSuccessNotification) {
					const notificationMessage =
						message || Liferay.Language.get('table-data-updated');

					showNotification(notificationMessage, 'success');
				}

				setLoading(false);
				Liferay.fire(DATASET_DISPLAY_UPDATED, {id});
			})
			.catch((error) => {
				logError(error);
				setLoading(false);

				showNotification(
					Liferay.Language.get('unexpected-error'),
					'danger'
				);
			});
	}

	useEffect(() => {
		getData(
			apiUrl,
			currentUrl,
			filters.filter((filter) => filter.value),
			searchParam,
			delta,
			pageNumber,
			sorting,
			false
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		apiUrl,
		currentUrl,
		filters,
		searchParam,
		delta,
		pageNumber,
		sorting,
		refreshData,
	]);

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

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const refreshData = (successNotification) =>
		getData(
			apiUrl,
			currentUrl,
			filters.filter((filter) => filter.value),
			searchParam,
			delta,
			pageNumber,
			sorting,
			successNotification
		);

	useEffect(() => {
		if (wrapperRef.current) {
			const form = wrapperRef.current.closest('form');

			if (form?.getAttribute('data-senna-off') === null) {
				form.setAttribute('data-senna-off', true);
			}
		}
	}, [wrapperRef]);

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
		<div className="dataset-display-management-bar-wrapper">
			<ManagementBar
				activeView={activeView}
				bulkActions={bulkActions}
				creationMenu={creationMenu}
				filters={filters}
				fluid={style === 'fluid'}
				onFiltersChange={updateFilters}
				selectable={selectable}
				selectAllItems={() =>
					selectItems(items.map((item) => item[selectedItemsKey]))
				}
				selectedItemsKey={selectedItemsKey}
				selectedItemsValue={selectedItemsValue}
				selectionType={selectionType}
				setActiveView={setActiveView}
				showSearch={showSearch}
				sidePanelId={datasetDisplaySupportSidePanelId}
				total={items ? items.length : 0}
				views={views}
			/>
		</div>
	) : null;

	const view =
		!loading && CurrentViewComponent ? (
			<div className="dataset-display-content-wrapper">
				{!formId && (
					<input
						hidden
						name={`${namespace || id + '_'}${
							actionParameterName ?? selectedItemsKey
						}`}
						readOnly
						value={selectedItemsValue.join(',')}
					/>
				)}
				{items?.length > 0 && (
					<CurrentViewComponent
						datasetDisplayContext={DatasetDisplayContext}
						items={items}
						itemsActions={itemsActions}
						style={style}
						{...currentViewProps}
					/>
				)}
				{items?.length === 0 && <EmptyResultMessage />}
			</div>
		) : (
			<span aria-hidden="true" className="loading-animation my-7" />
		);

	const wrappedView = formId ? view : <form ref={formRef}>{view}</form>;

	const paginationComponent =
		showPagination && pagination && items?.length ? (
			<div className="dataset-display-pagination-wrapper">
				<ClayPaginationBarWithBasicItems
					activeDelta={delta}
					activePage={pageNumber}
					className="mb-2"
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
				refreshData();
				Liferay.fire(DATASET_ACTION_PERFORMED, {
					id,
				});
			})
			.catch((error) => {
				logError(error);

				showNotification(
					Liferay.Language.get('unexpected-error'),
					'danger'
				);
			});
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

	const spritemap = useContext(ClayIconSpriteContext);

	return (
		<DatasetDisplayContext.Provider
			value={{
				actionParameterName,
				executeAsyncItemAction,
				formId,
				formRef,
				highlightItems,
				highlightedItemsValue,
				itemsActions,
				loadData: refreshData,
				modalId: datasetDisplaySupportModalId,
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
				sidePanelId: datasetDisplaySupportSidePanelId,
				sorting,
				style,
				updateSearchParam,
				updateSorting,
			}}
		>
			<Modal id={datasetDisplaySupportModalId} onClose={refreshData} />

			{!sidePanelId && (
				<SidePanel
					id={datasetDisplaySupportSidePanelId}
					onAfterSubmit={refreshData}
					spritemap={spritemap}
				/>
			)}

			<div className="dataset-display-wrapper" ref={wrapperRef}>
				{style === 'default' && (
					<div className="dataset-display dataset-display-inline">
						{managementBar}
						{wrappedView}
						{paginationComponent}
					</div>
				)}
				{style === 'stacked' && (
					<div className="dataset-display dataset-display-stacked">
						{managementBar}
						{wrappedView}
						{paginationComponent}
					</div>
				)}
				{style === 'fluid' && (
					<div className="dataset-display dataset-display-fluid">
						{managementBar}
						<div className="container mt-3">
							{wrappedView}
							{paginationComponent}
						</div>
					</div>
				)}
			</div>
		</DatasetDisplayContext.Provider>
	);
}

DatasetDisplay.propTypes = {
	activeViewId: PropTypes.string,
	apiUrl: PropTypes.string.isRequired,
	bulkActions: PropTypes.array,
	creationMenu: PropTypes.shape({
		primaryItems: PropTypes.array,
		secondaryItems: PropTypes.array,
	}),
	currentUrl: PropTypes.string,
	filters: PropTypes.array,
	formId: PropTypes.string,
	id: PropTypes.string.isRequired,
	items: PropTypes.array,
	itemsActions: PropTypes.array,
	namespace: PropTypes.string,
	nestedItemsKey: PropTypes.string,
	nestedItemsReferenceKey: PropTypes.string,
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
	selectedItemsKey: 'id',
	selectionType: 'multiple',
	showManagementBar: true,
	showPagination: true,
	showSearch: true,
	sorting: [],
	style: 'default',
};

export default DatasetDisplay;
