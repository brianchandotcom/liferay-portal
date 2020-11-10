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
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useState} from 'react';

import {getItem} from '../../utils/client.es';
import {getLocalizedValue} from '../../utils/lang.es';
import DropDownWithSearch from '../dropdown-with-search/DropDownWithSearch.es';
import NewCustomObjectModal from './NewCustomObjectModal.es';

export function getDataObjects() {
	return getItem(
		'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder',
		{keywords: '', page: -1, pageSize: -1, sort: ''}
	).then(({items}) =>
		items.map((item) => ({
			...item,
			name: getLocalizedValue(item.defaultLanguageId, item.name),
			type: 'custom',
		}))
	);
}

export default function SelectObjects({
	defaultValue,
	label,
	onCreateObject = () => {},
	onSelect,
	selectedValue,
	visible,
}) {
	const [items, setItems] = useState([]);
	const [isModalVisible, setModalVisible] = useState(false);
	const [state, setState] = useState({
		error: null,
		isLoading: true,
	});

	const doFetch = () => {
		setState({
			error: null,
			isLoading: true,
		});

		getDataObjects()
			.then((items) => {
				setItems(items);
				setState({
					error: null,
					isLoading: false,
				});

				if (defaultValue) {
					const defaultItem = items.find(
						({id}) => id === defaultValue
					);

					if (defaultItem) {
						onSelect(defaultItem);
					}
				}
			})
			.catch((error) => {
				setState({
					error,
					isLoading: false,
				});
			});
	};

	const labelProps = {
		custom: {
			displayType: 'success',
			label: Liferay.Language.get('custom'),
		},
		native: {
			displayType: 'info',
			label: Liferay.Language.get('native'),
		},
	};

	const ItemWithLabel = ({name, type}) => {
		const itemName = name || label;

		return (
			<>
				<span
					className="float-left text-left text-truncate w50"
					title={itemName}
				>
					{itemName}
				</span>

				{type && (
					<ClayLabel
						className="dropdown-button-asset float-right"
						displayType={labelProps[type].displayType}
					>
						{labelProps[type].label}
					</ClayLabel>
				)}
			</>
		);
	};

	const stateProps = {
		emptyProps: {
			label: Liferay.Language.get('there-are-no-objects-yet'),
		},
		errorProps: {
			children: (
				<ClayButton displayType="link" onClick={doFetch} small>
					{Liferay.Language.get('retry')}
				</ClayButton>
			),
			label: Liferay.Language.get('unable-to-retrieve-the-objects'),
		},
		loadingProps: {
			label: Liferay.Language.get('retrieving-all-objects'),
		},
	};

	useEffect(() => {
		doFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [visible]);

	return (
		<>
			<DropDownWithSearch
				{...state}
				addButton={
					<ClayTooltipProvider>
						<ClayButtonWithIcon
							className="mr-2"
							data-tooltip-align="bottom"
							data-tooltip-delay="0"
							displayType="secondary"
							onClick={() => setModalVisible(true)}
							small
							symbol="plus"
							title={Liferay.Language.get('new-custom-object')}
						/>
					</ClayTooltipProvider>
				}
				isEmpty={items.length === 0}
				label={label}
				stateProps={stateProps}
				trigger={
					<ClayButton
						aria-labelledby="select-object-label"
						className="clearfix w-100"
						displayType="secondary"
					>
						<ClayIcon
							className="dropdown-button-asset float-right ml-1"
							symbol="caret-bottom"
						/>

						<ItemWithLabel {...selectedValue} />
					</ClayButton>
				}
				visible={visible}
			>
				<DropDownWithSearch.Items
					emptyResultMessage={Liferay.Language.get(
						'there-were-no-objects-found-with-this-name'
					)}
					items={items}
					onSelect={onSelect}
				>
					{ItemWithLabel}
				</DropDownWithSearch.Items>
			</DropDownWithSearch>

			{isModalVisible && (
				<NewCustomObjectModal
					onCloseModal={() => setModalVisible(false)}
					onCreateObject={(newObject) => {
						onCreateObject(newObject);
						doFetch();
					}}
				/>
			)}
		</>
	);
}
