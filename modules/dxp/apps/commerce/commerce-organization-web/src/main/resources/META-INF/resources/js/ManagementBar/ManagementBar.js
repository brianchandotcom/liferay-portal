/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {AutocompleteComponent as Autocomplete} from 'commerce-frontend-js';
import {ManagementToolbar} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useContext, useRef} from 'react';

import ChartContext from '../ChartContext';
import {MODEL_TYPE_MAP} from '../utils/constants';
import RootOrganizationFilter from './RootOrganizationFilter';

function ManagementBar({onSearchSelected}) {
	const {chartInstanceRef} = useContext(ChartContext);

	const searchSelectedItemRef = useRef(null);

	return (
		<ManagementToolbar.Container className="org-chart-management-toolbar">
			<ManagementToolbar.ItemList>
				<ManagementToolbar.Item className="root-org-filter">
					<RootOrganizationFilter />
				</ManagementToolbar.Item>

				<ManagementToolbar.Item className="search">
					<Autocomplete
						apiUrl={[
							'/o/headless-admin-user/v1.0/accounts',
							'/o/headless-admin-user/v1.0/organizations?flatten=true',
							'/o/headless-admin-user/v1.0/user-accounts',
						]}
						autoload={false}
						customView={CustomAutocompleteRenderer}
						customViewInsideDropDown={true}
						fetchDataDebounce={300}
						infiniteScrollMode={true}
						initialLabel={
							searchSelectedItemRef.current
								? searchSelectedItemRef.current.name
								: ''
						}
						initialValue={
							searchSelectedItemRef.current
								? searchSelectedItemRef.current.id
								: ''
						}
						inputName="search"
						itemsKey="id"
						itemsLabel="name"
						onValueUpdated={(currentValue, selectedItem) => {
							let type;

							if (currentValue) {
								if (
									(searchSelectedItemRef.current &&
										String(
											searchSelectedItemRef.current.id
										) === String(currentValue) &&
										String(
											searchSelectedItemRef.current
												.randomId
										) === String(selectedItem.randomId)) ||
									!selectedItem.randomId
								) {
									return;
								}

								searchSelectedItemRef.current = selectedItem;

								if ('accountBriefs' in selectedItem) {
									type = MODEL_TYPE_MAP.user;
								}
								else if (
									'numberOfOrganizations' in selectedItem
								) {
									type = MODEL_TYPE_MAP.organization;
								}
								else if ('parentAccountId' in selectedItem) {
									type = MODEL_TYPE_MAP.account;
								}

								onSearchSelected(
									currentValue,
									selectedItem.name,
									type
								);
							}
							else {
								onSearchSelected(null, null, null);
							}
						}}
						pageSize={10}
					/>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item>
					<ClayButton
						displayType="secondary"
						onClick={() =>
							chartInstanceRef.current.collapseAllNodes()
						}
						size="sm"
					>
						{Liferay.Language.get('collapse-all')}
					</ClayButton>
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}

function CustomAutocompleteRenderer({items, updateActive, updateSelectedItem}) {
	items = Object.values(
		(items || []).reduce(
			(accumulator, item) => ({...accumulator, [item.id]: item}),
			{}
		)
	)
		.map((item) => {
			if ('accountBriefs' in item) {
				item.localizedType = Liferay.Language.get('user');
			}
			else if ('numberOfOrganizations' in item) {
				item.localizedType = Liferay.Language.get('organization');
			}
			else if ('parentAccountId' in item) {
				item.localizedType = Liferay.Language.get('account');
			}

			return item;
		})
		.sort((a, b) => {
			const nameA = a.name;
			const nameB = b.name;
			if (nameA < nameB) {
				return -1;
			}
			if (nameA > nameB) {
				return 1;
			}

			return 0;
		});

	return (
		<ClayDropDown.ItemList className="mb-0">
			{items && !items.length && (
				<ClayDropDown.Item className="disabled">
					{Liferay.Language.get('no-items-were-found')}
				</ClayDropDown.Item>
			)}

			{items &&
				!!items.length &&
				items.map((item) => (
					<ClayAutocomplete.Item
						key={item.id}
						onClick={() => {
							updateSelectedItem({
								randomId: Math.random(),
								...item,
							});
							updateActive(false);
						}}
					>
						{item.name + ' - ' + item.localizedType}
					</ClayAutocomplete.Item>
				))}
		</ClayDropDown.ItemList>
	);
}

ManagementBar.propTypes = {
	onSearchSelected: PropTypes.func.isRequired,
};

export default ManagementBar;
