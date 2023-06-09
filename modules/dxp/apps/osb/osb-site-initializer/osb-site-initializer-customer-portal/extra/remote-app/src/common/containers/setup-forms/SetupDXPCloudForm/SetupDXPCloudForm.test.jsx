/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {MockedProvider} from '@apollo/client/testing';

import {act, cleanup, render, screen} from '@testing-library/react';
import gql from 'graphql-tag';
import {MemoryRouter} from 'react-router-dom';
import {vi} from 'vitest';
import SetupDXPCloudForm from './index';

const queryUser = gql`
	query getDXPCloudPageInfo($accountSubscriptionsFilter: String) {
		c {
			dXPCDataCenterRegions(sort: "name:asc") {
				items {
					dxpcDataCenterRegionId
					name
					value
				}
			}
		}
	}
`;

const mocksmocks = [
	{
		request: {
			query: queryUser,
			variables: {accountSubscriptionsFilter: ''},
		},
		result: {
			data: {
				c: {
					dXPCDataCenterRegions: {
						items: [
							{
								dxpcDataCenterRegionId: 45891,
								name: 'Doha, Qatar',
								value: 'Doha, Qatar',
							},
							{
								dxpcDataCenterRegionId: 45893,
								name: 'Frankfurt, Germany',
								value: 'Frankfurt, Germany',
							},
						],
						totalCount: 2,
					},
				},
			},
		},
	},
];

describe('SetupDXPCloudForm', () => {
	const dXPCDataCenterRegions = [{value: 'Doha, Qatar'}];
	const values = {
		dxp: {
			disasterDataCenterRegion: dXPCDataCenterRegions[0].value,
		},
	};

	beforeEach(() => {
		vi.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		vi.clearAllTimers();
		vi.restoreAllMocks();
	});

	afterAll(() => {
		vi.useRealTimers();
	});

	it(`Renders`, async () => {
		const client = {
			query: vi.fn().mockImplementation(() => ({
				data: {
					listTypeDefinitions: {
						items: [
							{
								listTypeEntries: [
									{
										key: '73',
										name: '7.3',
									},
									{
										key: '74',
										name: '7.4',
									},
								],
							},
						],
					},
				},
			})),
		};

		const {debug} = render(
			<MemoryRouter>
				<MockedProvider mocks={mocksmocks}>
					<SetupDXPCloudForm
						client={client}
						dxpVersion="7.3"
						handlePage={() => vi.fn()}
						leftButton=""
						listType=""
						project={{
							name: 'Liferay',
						}}
						subscriptionGroupId=""
					/>
				</MockedProvider>
			</MemoryRouter>
		);

		await act(async () => {
			vi.runAllTimers();
		});

		debug();
	});

	it.skip('disable options based on the value of dxp.disasterDataCenterRegion', () => {
		render(
			<MemoryRouter>
				<MockedProvider mocks={mocksmocks}>
					<SetupDXPCloudForm
						dxpVersion=""
						errors={{}}
						handlePage={() => {}}
						leftButton=""
						listType=""
						project={{}}
						setFieldValue={() => {}}
						setFormAlreadySubmitted={() => {}}
						subscriptionGroupId=""
						touched={{}}
						values={values}
					/>
				</MockedProvider>
			</MemoryRouter>
		);

		const selectElement = screen.getByLabelText(
			'Disaster Recovery Data Center Region'
		);
		const options = Array.from(selectElement.querySelectorAll('option'));
		const expectedDisabledOptions = ['Doha, Qatar'];

		const disabledOptions = options
			.filter((option) => option.disabled)
			.map((option) => option.value);

		expect(disabledOptions).toEqual(expectedDisabledOptions);
	});
});
