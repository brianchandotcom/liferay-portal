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
import React, {useEffect, useState} from 'react';

import {request} from '../../utils/client.es';
import DropDownWithSearch from './DropDownWithSearch.es';

export default ({onClickEmptyState, onSelect, selectedvalue}) => {
	const [fetchState, setFetchState] = useState({isLoading: true});
	const [items, setItems] = useState([]);

	const doFetch = () => {
		const ENDPOINT_CUSTOM_OBJECTS =
			'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder';
		const ENDPOINT_NATIVE_OBJECTS =
			'/o/data-engine/v2.0/data-definitions/by-content-type/native-object';

		const params = {keywords: '', page: -1, pageSize: -1, sort: ''};

		const fetchCustomObjects = () =>
			request({endpoint: ENDPOINT_CUSTOM_OBJECTS, params});
		const fetchNativeObjects = () =>
			request({endpoint: ENDPOINT_NATIVE_OBJECTS, params});

		setFetchState({
			hasError: null,
			isLoading: true,
		});

		return Promise.all([fetchCustomObjects(), fetchNativeObjects()])
			.then(([customObjects, nativeObjects]) => {
				setItems(
					[...customObjects.items, ...nativeObjects.items].sort(
						(a, b) => a - b
					)
				);
				setFetchState({
					hasError: null,
					isLoading: false,
				});
			})
			.catch((hasError) => {
				setFetchState({
					hasError,
					isLoading: false,
				});
			});
	};

	useEffect(() => {
		doFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleOnSelect = (event, newValue) => {
		event.stopPropagation();

		onSelect(newValue);
	};

	const stateProps = {
		emptyProps: {
			children: (
				<ClayButton
					className="emptyButton"
					displayType="secondary"
					onClick={onClickEmptyState}
				>
					{Liferay.Language.get('new-custom-object')}
				</ClayButton>
			),
			label: Liferay.Language.get(
				'no-objects-yet-create-your-first-object'
			),
		},
		errorProps: {
			children: (
				<ClayButton displayType="link" onClick={doFetch}>
					{Liferay.Language.get('retry')}
				</ClayButton>
			),
			label: Liferay.Language.get('failed-to-retrieve-objects'),
		},
		loadingProps: {
			label: Liferay.Language.get('retrieving-all-objects'),
		},
	};

	return (
		<DropDownWithSearch
			{...fetchState}
			items={items}
			label={Liferay.Language.get('select-object')}
			onSelect={handleOnSelect}
			stateProps={stateProps}
			trigger={
				<ClayButton className="clearfix w-100" displayType="secondary">
					<span className="float-left">
						{selectedvalue || Liferay.Language.get('select-object')}
					</span>

					<ClayIcon
						className="float-right icon"
						symbol="caret-bottom"
					/>
				</ClayButton>
			}
		/>
	);
};
