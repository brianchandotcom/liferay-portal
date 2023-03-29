import React from 'react';
import SubscriptionTitle from '../SubscriptionTitle';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<SubscriptionTitle name='Business' price={750} {...props} />
);

describe('SubscriptionTitle', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a label', () => {
		const textToMatch = '1x';

		const {getByText} = render(
			<DefaultComponent labelText={textToMatch} />
		);

		expect(getByText(textToMatch)).toBeTruthy();
	});
});
