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
import React, {useEffect, useState} from 'react';

import {getItem} from '../../utils/client.es';
import {getLocalizedValue} from '../../utils/lang.es';
import DropDownWithSearch from './DropDownWithSearch.es';

export function getDataObjects() {
	const PARAMS = {keywords: '', page: -1, pageSize: -1, sort: ''};

	return Promise.all([
		getItem(
			'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder',
			PARAMS
		).then(({items}) => items),
		getItem(
			'/o/data-engine/v2.0/data-definitions/by-content-type/native-object',
			PARAMS
		).then(({items}) => items),
	]).then(([customObjects, nativeObjects]) => {
		return [
			...customObjects.map((item) => ({
				...item,
				name: getLocalizedValue(item.defaultLanguageId, item.name),
				type: 'custom',
			})),
			...nativeObjects.map((item) => ({
				...item,
				name: getLocalizedValue(item.defaultLanguageId, item.name),
				type: 'native',
			})),
		];
	});
}

export default ({defaultValue, label, onSelect, selectedValue, visible}) => {
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

	useEffect(() => {
		doFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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

	const ItemWithLabel = ({name, type}) => (
		<>
			<span className="float-left text-left text-truncate w70">
				{name || label}
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
					emptyResultMessage={Liferay.Language.get(
						'no-objects-found-with-this-name-try-searching-again-with-a-different-name'
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
