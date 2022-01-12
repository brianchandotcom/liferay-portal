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

import React from 'react';

import launcher from '../../src/main/resources/META-INF/resources/components/autocomplete/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('autocomplete', 'autocomplete-root', {
	active: true,
	apiUrl: '/o/headless-commerce-admin-catalog/v1.0/products/',
	customView: (props) => {
		return props.items ? (
			<>
				<ul>
					{props.items.map((item) => (
						<li key={item.id}>{item.id}</li>
					))}
				</ul>
				<button
					disabled={props.page === props.lastPage}
					onClick={() => props.updatePage(props.page + 1)}
				>
					Get more Items
				</button>
			</>
		) : null;
	},
	id: 'autocomplete',
	infinityScrollMode: true,
	inputName: 'test-name',
	itemsKey: 'productId',
	itemsLabel: 'externalReferenceCode',
	onValueUpdated: (value, itemData) =>
		// eslint-disable-next-line no-console
		console.log(`Value: ${value}`, `Data: ${JSON.stringify(itemData)}`),
	pageSize: 5,
});

launcher('autocomplete-2', 'autocomplete-root-2', {
	apiUrl: '/o/headless-commerce-admin-catalog/v1.0/products/',
	autofill: true,
	fetchDataDebounce: 1000,
	id: 'autocomplete-2',
	initialLabel: 'Initial Label',
	initialValue: 'initial-value',
	inputName: 'test-name',
	itemsKey: 'productId',
	itemsLabel: 'externalReferenceCode',
	onValueUpdated: (value, itemData) =>
		// eslint-disable-next-line no-console
		console.log(`Value: ${value}`, `Data: ${JSON.stringify(itemData)}`),
	showDeleteButton: true,
});
