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
import {act, fireEvent, render} from '@testing-library/react';
import fetch from 'jest-fetch-mock';
import React, {useEffect} from 'react';

import {AppContextProvider, TData, initialState, useData} from '../../../App';
import AttributesStep from '../AttributesStep';

const response = {
	account: 25,
	order: 0,
	people: 43,
	product: 0,
};

const AttributesStepContent = ({
	onDataChange,
}: {
	onDataChange: (data: TData) => void;
}) => {
	const data = useData();

	useEffect(() => {
		onDataChange(data);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	return <AttributesStep onCancel={() => {}} onChangeStep={() => {}} />;
};

describe('Attributes Step', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('render AttributesStep without crashing', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		let data: TData = initialState;

		const onDataChange = jest.fn((newData: TData) => {
			data = newData;
		});

		const {container, getByText} = render(
			<AppContextProvider
				connected={false}
				liferayAnalyticsURL=""
				token=""
			>
				<AttributesStepContent onDataChange={onDataChange} />
			</AppContextProvider>
		);

		expect(data.pageView).toEqual('VIEW_WIZARD_MODE');
		expect(getByText(/finish/i)).toBeInTheDocument();

		const attributesStepTitle = getByText('attributes');

		const attributesStepDescription = getByText(
			'attributes-step-description'
		);

		const finishButton = getByText(/finish/i);

		await act(async () => {
			await fireEvent.click(finishButton);
		});

		expect(data.pageView).toEqual('VIEW_DEFAULT_MODE');
		expect(onDataChange).toBeCalledTimes(2);
		expect(attributesStepTitle).toBeInTheDocument();
		expect(attributesStepDescription).toBeInTheDocument();
		expect(container.firstChild).toHaveClass('sheet');
	});
});
