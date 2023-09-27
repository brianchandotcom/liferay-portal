/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render} from '@testing-library/react';
import fetch from 'jest-fetch-mock';
import React, {useEffect} from 'react';

import {AppContextProvider, TData, initialState, useData} from '../../../App';
import {mockResponse} from '../../../utils/__tests__/helpers';
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
		fetch
			.mockReturnValueOnce(mockResponse(JSON.stringify(response)))
			.mockReturnValueOnce(mockResponse(JSON.stringify({})));

		let data: TData = initialState;

		const onDataChange = jest.fn((newData: TData) => {
			data = newData;
		});

		const {container, getByText} = render(
			<AppContextProvider
				connected={false}
				liferayAnalyticsURL=""
				token=""
				wizardMode
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
