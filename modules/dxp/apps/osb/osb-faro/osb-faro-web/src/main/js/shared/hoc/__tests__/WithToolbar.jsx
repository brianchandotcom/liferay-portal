import React from 'react';
import withToolbar from '../WithToolbar';
import {compose} from 'redux';
import {render} from '@testing-library/react';
import {withStaticRouter} from 'test/mock-router';

jest.unmock('react-dom');

describe('withToolbar', () => {
	it('renders', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withToolbar({showRangeDropdownKey: true})
		)(() => <div>{'foobar'}</div>);

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
