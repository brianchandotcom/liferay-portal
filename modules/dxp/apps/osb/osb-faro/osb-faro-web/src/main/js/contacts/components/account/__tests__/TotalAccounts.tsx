import React from 'react';
import TotalAccounts from '../TotalAccounts';
import {render} from '@testing-library/react';
import {TrendClassification} from 'segment/types';

jest.unmock('react-dom');

describe('TotalAccounts', () => {
	it('should render', () => {
		const useRequest = require('shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({
			data: {
				activeCount: {
					trend: {
						percentage: 0,
						trendClassification: TrendClassification.Neutral
					},
					value: 1
				},
				newCount: {
					trend: {
						percentage: -30,
						trendClassification: TrendClassification.Negative
					},
					value: 10
				},
				totalCount: {
					trend: {
						percentage: 50,
						trendClassification: TrendClassification.Positive
					},
					value: 15
				}
			}
		}));

		const {container} = render(<TotalAccounts groupId='123' />);

		expect(container).toMatchSnapshot();
	});

	it("should load with empty values when there's no data", () => {
		const useRequest = require('shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({}));

		const {container} = render(<TotalAccounts groupId='123' />);

		expect(container).toMatchSnapshot();
	});
});
