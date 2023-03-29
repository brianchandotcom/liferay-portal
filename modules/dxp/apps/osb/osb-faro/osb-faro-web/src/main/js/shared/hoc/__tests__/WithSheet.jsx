import React from 'react';
import withSheet from '../WithSheet';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('withSheet', () => {
	it('should render', () => {
		const WrappedComponent = withSheet({large: true})(() => (
			<p>{'Test Test'}</p>
		));

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
