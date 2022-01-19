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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import DisplayPageItemContextualSidebar from '../../src/main/resources/META-INF/resources/js/DisplayPageItemContextualSidebar';

const DEFAULT_LANGUAGE_ID = 'en_US';

const LOCALES = [
	{
		id: 'en_US',
		label: 'en-US',
		symbol: 'en-us',
	},
	{
		id: 'es_ES',
		label: 'es-ES',
		symbol: 'es-es',
	},
];

const renderContextualSidebar = ({
	item = {},
	localizedNames = {},
	subtype,
	type = '',
	useCustomName = false,
}) => {
	return render(
		<DisplayPageItemContextualSidebar
			chooseItemProps={{}}
			defaultLanguageId={DEFAULT_LANGUAGE_ID}
			item={item}
			itemSubtype={subtype}
			itemType={type}
			locales={LOCALES}
			localizedNames={localizedNames}
			namespace="namespace"
			useCustomName={useCustomName}
		/>
	);
};

describe('DisplayPageItemContextualSidebar', () => {
	afterEach(cleanup);

	it('renders name input disabled if custom name is disabled', () => {
		renderContextualSidebar({useCustomName: false});

		const nameInput = screen.getByLabelText('name');

		expect(nameInput).toHaveAttribute('disabled');
	});

	it('renders name input enabled if custom name is enabled', () => {
		renderContextualSidebar({useCustomName: true});

		const nameInput = screen.getByLabelText('name');

		expect(nameInput).not.toHaveAttribute('disabled');
	});

	it('renders name input with translated custom name for the default language when custom name is enabled', () => {
		const localizedNames = {
			[DEFAULT_LANGUAGE_ID]: 'localized-name',
		};

		renderContextualSidebar({localizedNames, useCustomName: true});

		const nameInput = screen.getByLabelText('name');

		expect(nameInput).toHaveValue('localized-name');
	});

	it('renders type and subtype', () => {
		renderContextualSidebar({subtype: 'item-subtype', type: 'item-type'});

		expect(screen.getByText('item-type')).toBeInTheDocument();
		expect(screen.getByText('item-subtype')).toBeInTheDocument();
	});

	it('renders extra data if it is not empty', () => {
		const data = [
			{
				title: 'Vocabulary',
				value: 'Animals',
			},
			{
				title: 'Site',
				value: 'Liferay',
			},
		];

		renderContextualSidebar({item: {data}});

		data.forEach(({title, value}) => {
			expect(screen.getByText(title)).toBeInTheDocument();
			expect(screen.getByText(value)).toBeInTheDocument();
		});
	});
});
