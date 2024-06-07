/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {IItem} from '@clayui/drop-down/lib/drilldown/Menu';
import ClayIcon from '@clayui/icon';
import React, {
	MouseEvent,
	useCallback,
	useContext,
	useEffect,
	useState,
} from 'react';

// @ts-ignore

import ChartContext from '../ChartContext';

const _toDropDownItems = (
	items: TOrganization[],
	onChange: (id: number) => void,
	isRootOrgFromConfig = false
): IItem[] =>
	items.map(
		({id: value, name: label}) =>
			({
				checked: isRootOrgFromConfig,
				disabled: isRootOrgFromConfig,
				label,
				onChange: () => onChange(Number(value)),
				type: 'checkbox',
				value: Number(value),
			}) as unknown as IItem
	);

const MAX_ROOT_ORGANIZATIONS = 10;

const RootOrganizationFilter = () => {
	const {
		chartInstanceRef,
		configRootOrganizationId,
		fetchOrganizations,
		organizations,
		organizationsIds,
		searchResult,
		spritemap,
	} = useContext(ChartContext);

	const [changeApplied, setChangeApplied] = useState(true);
	const [itemList, setItemList] = useState([] as IItem[]);
	const [rootOrganizations, setRootOrganizations] = useState(
		[] as TOrganization[]
	);
	const [searchValue, setSearchValue] = useState('');
	const [selectedItems, setSelectedItems] = useState(
		new Set() as Set<number>
	);
	const [rerenderKey, setRerenderKey] = useState(0);

	const onItemsChange = useCallback((organizationId: number): void => {
		setSelectedItems((current: Set<number>) => {
			current[current.has(organizationId) ? 'delete' : 'add'](
				organizationId
			);

			return new Set([...current]);
		});

		setChangeApplied(false);
	}, []);

	useEffect(() => {
		setItemList(
			_toDropDownItems(
				rootOrganizations,
				onItemsChange,
				!!configRootOrganizationId
			)
		);
	}, [configRootOrganizationId, onItemsChange, rootOrganizations]);

	useEffect(() => {
		if (!configRootOrganizationId) {
			setItemList((current: IItem[]) =>
				current.map((item: IItem) => ({
					...item,
					checked: selectedItems.has(item.value),
					disabled:
						selectedItems.size === MAX_ROOT_ORGANIZATIONS &&
						!selectedItems.has(item.value),
				}))
			);
		}
	}, [configRootOrganizationId, selectedItems]);

	useEffect(() => {
		if (!rootOrganizations.length) {
			setRootOrganizations(organizations);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [organizations]);

	useEffect(() => {
		if (searchResult.id) {
			setSelectedItems((current) => {
				const isOrgAlreadyInView = organizationsIds.find((id: number) =>
					current.has(id)
				);

				if (isOrgAlreadyInView) {
					if (chartInstanceRef && chartInstanceRef.current) {
						chartInstanceRef.current.search(
							searchResult.id,
							searchResult.type
						);
					}
				}

				return rootOrganizations.length === organizationsIds.length
					? current // won't update
					: new Set(organizationsIds);
			});

			/**
			 * Note: Mandatory to have UI checkboxes to reflect the current state.
			 * <ClayDropDownWithItems /> misbehaviour for DropDown items in React Portal
			 */
			setRerenderKey((value) => value + 1);
		}
	}, [
		chartInstanceRef,
		organizationsIds,
		rootOrganizations,
		searchResult.id,
		searchResult.name,
		searchResult.type,
	]);

	return (
		<ClayDropDownWithItems
			className="mr-4"
			footerContent={
				<ClayButton
					className="btn-block"
					disabled={changeApplied}
					displayType="primary"
					onClick={(event: MouseEvent) => {
						event.preventDefault();

						fetchOrganizations([...selectedItems]);
						setChangeApplied(true);
					}}
					size="sm"
				>
					{Liferay.Language.get('apply')}
				</ClayButton>
			}
			items={itemList}
			key={rerenderKey}
			menuElementAttrs={{className: 'root-organizations-filter'}}
			onSearchValueChange={(value) => {
				const filteredList: TOrganization[] = rootOrganizations.filter(
					({name}: TOrganization) => name.includes(value)
				);

				setItemList(_toDropDownItems(filteredList, onItemsChange));
				setSearchValue(value);
			}}
			renderMenuOnClick
			searchValue={searchValue}
			searchable={!configRootOrganizationId}
			spritemap={spritemap}
			trigger={
				<ClayButton className="nav-link" displayType="unstyled">
					<span className="align-items-center d-flex justify-content-around navbar-breakpoint-down-d-none">
						<span className="navbar-text-truncate">
							{Liferay.Language.get('select-organizations')}
						</span>

						<ClayIcon
							className="inline-item inline-item-after"
							spritemap={spritemap}
							symbol="caret-bottom"
						/>
					</span>
				</ClayButton>
			}
		/>
	);
};

type TOrganization = {
	id: number;
	name: string;
};

export default RootOrganizationFilter;
