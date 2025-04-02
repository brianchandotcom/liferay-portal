/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import BulkActions from './controls/BulkActions';
import BulkActionsDeprecated from './controls/BulkActionsDeprecated';
import NavBar from './controls/NavBar';
import ActiveFiltersBar from './controls/filters/ActiveFiltersBar';

function ManagementBar({
	bulkActions,
	creationMenu,
	deselectItems,
	fluid,
	items,
	onBulkActionsClear,
	selectItems,
	selectedItems,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
	showSearch,
	total,
}) {
	const pageSelectedItemsValue = selectedItemsValue.filter((id) =>
		items.some((item) => item.id === id)
	);

	function handleCheckboxClick() {
		const itemKeys = items.map((item) => item[selectedItemsKey]);

		if (pageSelectedItemsValue.length === items.length) {
			return deselectItems(itemKeys);
		}

		return selectItems(itemKeys);
	}

	return (
		<>
			{selectionType === 'multiple' &&
				(Liferay.FeatureFlags['LPD-42570'] ? (
					<BulkActions
						bulkActions={bulkActions}
						fluid={fluid}
						handleCheckboxClick={handleCheckboxClick}
						items={items}
						onClear={onBulkActionsClear}
						pageSelectedItemsValue={pageSelectedItemsValue}
						selectItems={selectItems}
						selectedItems={selectedItems}
						selectedItemsKey={selectedItemsKey}
						selectedItemsValue={selectedItemsValue}
						total={total}
					/>
				) : (
					<BulkActionsDeprecated
						bulkActions={bulkActions}
						fluid={fluid}
						handleCheckboxClick={handleCheckboxClick}
						items={items}
						onClear={onBulkActionsClear}
						pageSelectedItemsValue={pageSelectedItemsValue}
						selectItems={selectItems}
						selectedItems={selectedItems}
						selectedItemsKey={selectedItemsKey}
						selectedItemsValue={selectedItemsValue}
						total={total}
					/>
				))}

			{(!selectedItemsValue.length || selectionType === 'single') && (
				<NavBar
					creationMenu={creationMenu}
					handleCheckboxClick={handleCheckboxClick}
					items={items}
					showSearch={showSearch}
				/>
			)}

			<ActiveFiltersBar disabled={!!selectedItemsValue.length} />
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
	selectItems: PropTypes.func.isRequired,
	selectedItems: PropTypes.array,
	selectedItemsKey: PropTypes.string,
	selectedItemsValue: PropTypes.array,
	selectionType: PropTypes.oneOf(['single', 'multiple']),
	showSearch: PropTypes.bool,
	total: PropTypes.number,
};

ManagementBar.defaultProps = {
	filters: [],
	fluid: false,
	showSearch: true,
};

export default ManagementBar;
