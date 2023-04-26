import BaseConfigurationItem from '../BaseConfigurationItem';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('BaseConfigurationItem', () => {
	it('should render', () => {
		const {container} = render(
			<BaseConfigurationItem
				description='Test description'
				title='Test Test'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render as disabled', () => {
		const {getByText} = render(
			<BaseConfigurationItem
				buttonParams={{disabled: true}}
				description='Test description'
				title='Test Test'
			/>
		);

		expect(getByText('Configure')).toHaveClass('link-disabled');
	});

	it('should render with a status message', () => {
		const {getByText} = render(
			<BaseConfigurationItem
				buttonParams={{disabled: true}}
				description='Test description'
				statusMessage='Test Status Message'
				title='Test Test'
			/>
		);

		expect(getByText('Test Status Message')).toBeTruthy();
	});

	it('should render with a metric bar', () => {
		const {container} = render(
			<BaseConfigurationItem
				buttonParams={{disabled: true}}
				completion={0.8}
				description='Test description'
				showBar
				statusMessage='Test Status Message'
				title='Test Test'
			/>
		);

		expect(container.querySelector('.metric-bar-root')).toBeTruthy();
		expect(container.querySelector('.bar')).toHaveStyle('width: 80%;');
	});
});
