import BaseConfigurationOverview from '../BaseConfigurationOverview';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('BaseConfigurationOverview', () => {
	it('should render', () => {
		const mockConfigurationItems = [
			{
				description: 'foo description',
				label: 'edit',
				title: 'foo title'
			}
		];

		const {container, queryByText} = render(
			<BaseConfigurationOverview
				configurationItems={mockConfigurationItems}
			/>
		);

		expect(queryByText('foo title')).toBeTruthy();
		expect(queryByText('foo description')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
