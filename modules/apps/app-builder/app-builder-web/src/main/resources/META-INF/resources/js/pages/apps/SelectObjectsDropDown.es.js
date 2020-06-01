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
import React, {useEffect, useRef, useState} from 'react';

import {getItem} from '../../utils/client.es';
import DropDownWithSearch from './DropDownWithSearch.es';

export default ({onSelect, selectedValue, visible}) => {
	const [state, setState] = useState({
		error: null,
		isLoading: true,
	});
	const [items, setItems] = useState([]);
	const selectRef = useRef();

	const doFetch = () => {
		const params = {keywords: '', page: -1, pageSize: -1, sort: ''};

		setState({
			error: null,
			isLoading: true,
		});

		return Promise.all([
			getItem(
				'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder',
				params
			),
			getItem(
				'/o/data-engine/v2.0/data-definitions/by-content-type/native-object',
				params
			),
		])
			.then(([customObjects, nativeObjects]) => {
				setItems(
					[...customObjects.items, ...nativeObjects.items].sort(
						(a, b) => a - b
					)
				);
				setState({
					error: null,
					isLoading: false,
				});
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

	const handleOnSelect = (newValue) => {
		onSelect(newValue);
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

	return (
		<>
			<DropDownWithSearch
				{...state}
				isEmpty={items.length === 0}
				label={Liferay.Language.get('select-object')}
				stateProps={stateProps}
				trigger={
					<ClayButton
						className="clearfix w-100"
						displayType="secondary"
						ref={(element) => {
							selectRef.current = element;
						}}
					>
						<span className="float-left">
							{selectedValue ||
								Liferay.Language.get('select-object')}
						</span>

						<ClayIcon
							className="float-right icon"
							symbol="caret-bottom"
						/>
					</ClayButton>
				}
				visible={visible}
			>
				<DropDownWithSearch.Items
					emptyResultMessage={Liferay.Language.get(
						'no-objects-found-with-this-name-try-searching-again-with-a-different-name'
					)}
					items={items}
					onSelect={handleOnSelect}
				/>
			</DropDownWithSearch>
		</>
	);
};
