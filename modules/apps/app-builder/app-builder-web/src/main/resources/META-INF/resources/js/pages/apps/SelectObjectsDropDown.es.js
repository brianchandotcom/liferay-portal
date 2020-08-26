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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import {getItem} from '../../utils/client.es';
import {getLocalizedValue} from '../../utils/lang.es';
import DropDownWithSearch from './DropDownWithSearch.es';

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

export default ({defaultValue, label, onSelect, selectedValue, visible}) => {
	const {objectsPortletURL} = useContext(AppContext);
	const [state, setState] = useState({
		error: null,
		isLoading: true,
	});
	const [items, setItems] = useState([]);

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

	const getMessageWithNewObjectLink = (message) => (
		<>
			<span className="d-block">{message}</span>

			<ClayLink
				href={`${objectsPortletURL}#/?showCustomObjectPopover=1`}
				target="_blank"
			>
				{Liferay.Language.get('create-new-object')}{' '}
				<ClayIcon fontSize="10px" symbol="shortcut" />
			</ClayLink>
		</>
	);

	useEffect(() => {
		doFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const stateProps = {
		emptyProps: {
			label: getMessageWithNewObjectLink(
				Liferay.Language.get('there-are-no-objects-yet')
			),
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

	return (
		<>
			<DropDownWithSearch
				{...state}
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
					emptyResultMessage={getMessageWithNewObjectLink(
						Liferay.Language.get(
							'there-were-no-objects-found-with-this-name'
						)
					)}
					items={items}
					onSelect={onSelect}
				>
					{ItemWithLabel}
				</DropDownWithSearch.Items>
			</DropDownWithSearch>
		</>
	);
};
