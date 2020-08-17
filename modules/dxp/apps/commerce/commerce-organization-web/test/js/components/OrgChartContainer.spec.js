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

import OrgChartContainer from 'components/OrgChartContainer';
import {shallow} from 'enzyme';
import React from 'react';
import * as Utils from 'utils/utils.es';

import fakeJSON from './fakeData/0.json';

jest.mock('utils/utils.es', () => {
	const payload = require('./fakeData/0.json');

	return {
		bindAll: jest.fn(() => {}),
		callApi: jest.fn(() => Promise.resolve(payload)),
		setupDataset: jest.fn((payload) => payload),
		truncateTextNode: jest.fn(),
	};
});

describe('OrgChartContainer', () => {
	describe('Instantiation', () => {
		let wrapper, component;

		beforeEach(() => {
			wrapper = shallow(<OrgChartContainer />);
			component = wrapper.instance();
		});

		afterEach(() => {
			jest.clearAllMocks();
		});

		it('calls the API and stores the correct initial payload in the state', () => {
			return component.$didMount.then(() => {
				expect(wrapper.state()).toMatchObject({
					rootData: fakeJSON,
					selectedId: 0,
				});
			});
		});

		it('renders correctly without MembersPane', () => {
			return component.$didMount.then(() => {
				expect(wrapper.find('div').some('.organization-network')).toBe(
					true
				);
				expect(wrapper.find('div').some('.pane')).toBe(false);
			});
		});
	});

	describe('setSelection', () => {
		let wrapper, component;

		beforeEach(() => {
			wrapper = shallow(<OrgChartContainer />);
			component = wrapper.instance();
		});

		it('changes the state to the selected organization ID and its color identifier', () => {
			const someId = 42,
				colorIdentifier = 'hsl(0,100%,100%)';

			component.setSelection(someId, colorIdentifier);

			expect(wrapper.state()).toMatchObject({
				colorIdentifier: expect.any(String),
				selectedId: someId,
			});
		});

		it('renders also the MembersPane if selectedId > 0', () => {
			const someId = 43;

			return component.$didMount.then(() => {
				wrapper.setState({selectedId: someId}, () => {
					expect(wrapper.debug().includes('MembersPane')).toBe(true);
					expect(wrapper.state()).toMatchObject({
						rootData: fakeJSON,
						selectedId: someId,
					});
				});
			});
		});
	});

	describe('handleNodeClick', () => {
		let wrapper, component;

		beforeEach(() => {
			wrapper = shallow(<OrgChartContainer />);
			component = wrapper.instance();
		});

		it('calls the API with a specified organization ID if an OrgChart node is selected', () => {
			const someId = 45,
				someApiURL = 'http://someDomain.com';

			wrapper.setProps({apiURL: someApiURL});

			component.handleNodeClick(someId);

			expect(Utils.callApi).toHaveBeenCalledWith({
				baseURL: someApiURL,
				id: someId,
			});
		});
	});
});
