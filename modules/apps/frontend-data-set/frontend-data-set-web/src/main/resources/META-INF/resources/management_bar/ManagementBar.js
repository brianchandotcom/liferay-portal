/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {getObjectValueFromPath} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../FrontendDataSetContext';
import BulkActions from './controls/BulkActions';
import NavBar from './controls/NavBar';
import ResultsBar from './controls/ResultsBar';
import SelectionCheckbox from './controls/SelectionCheckbox';

function ManagementBar({
	bulkActions,
	creationMenu,
	dataLoading,
	deselectItems,
	fluid,
	items,
	onBulkActionsClear,
	onSelectAll,
	selectItems,
	selectedItems,
	selectedItemsKey,
	selectedItemsValue,
	showNavBarWhenSelected = false,
	showSearch,
	showSelectAll,
	total,
}) {
	const {selectable, selectionType} = useContext(FrontendDataSetContext);

	const pageSelectedItemsValue = selectedItemsValue.filter((id) =>
		items.some(
			(item) =>
				getObjectValueFromPath({
					object: item,
					path: selectedItemsKey,
				}) === id
		)
	);

	function handleCheckboxClick() {
		if (pageSelectedItemsValue.length) {
			deselectItems(pageSelectedItemsValue);

			return;
		}

		selectItems(
			items.map((item) =>
				getObjectValueFromPath({object: item, path: selectedItemsKey})
			)
		);
	}

	const showBulkActions =
		selectionType === 'multiple' &&
		!showNavBarWhenSelected &&
		!!selectedItemsValue.length;

	return (
		<>
			<div
				className={classNames(
					'container-fluid d-flex align-items-center management-bar',
					{'management-bar-primary': showBulkActions},
					!fluid && 'px-0'
				)}
			>
				{!!items.length &&
					selectable &&
					selectionType === 'multiple' && (
						<div className="ml-4 mt-2">
							<SelectionCheckbox
								handleCheckboxClick={handleCheckboxClick}
								items={items}
								selectedItemsValue={pageSelectedItemsValue}
							/>
						</div>
					)}

				{showBulkActions ? (
					<BulkActions
						bulkActions={bulkActions}
						handleSelectAll={(value) => onSelectAll(value)}
						items={items}
						onClear={onBulkActionsClear}
						pageSelectedItemsValue={pageSelectedItemsValue}
						selectedItems={selectedItems}
						selectedItemsKey={selectedItemsKey}
						selectedItemsValue={selectedItemsValue}
						showSelectAll={showSelectAll}
						total={total}
					/>
				) : (
					<NavBar
						creationMenu={creationMenu}
						showSearch={showSearch}
					/>
				)}
			</div>

			<ResultsBar
				dataLoading={dataLoading}
				disabled={!!selectedItemsValue.length}
				total={total}
			/>
		</>
	);
}

ManagementBar.propTypes = {
	bulkActions: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			icon: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			method: PropTypes.string,
			target: PropTypes.oneOf(['sidePanel', 'modal']),
		})
	),
	creationMenu: PropTypes.shape({
		primaryItems: PropTypes.array,
		secondaryItems: PropTypes.array,
	}),
	deselectItems: PropTypes.func.isRequired,
	fluid: PropTypes.bool,
	items: PropTypes.array.isRequired,
	onBulkActionsClear: PropTypes.func.isRequired,
	onSelectAll: PropTypes.func.isRequired,
	pageSelectedItemsValue: PropTypes.array,
	selectItems: PropTypes.func.isRequired,
	selectedItems: PropTypes.array,
	selectedItemsKey: PropTypes.string,
	selectedItemsValue: PropTypes.array,
	selectionType: PropTypes.oneOf(['single', 'multiple']),
	showSearch: PropTypes.bool,
	showSelectAll: PropTypes.bool,
	total: PropTypes.number,
};

ManagementBar.defaultProps = {
	filters: [],
	fluid: false,
	showSearch: true,
};

export default ManagementBar;
