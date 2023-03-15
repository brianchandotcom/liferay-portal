import LoadingModal from '../LoadingModal';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('LoadingModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<LoadingModal onClose={noop} />);
		expect(container).toMatchSnapshot();
	});

	it('should render with a message and a title', () => {
		const {queryByText} = render(
			<LoadingModal message='foo' onClose={noop} title='bar' />
		);

		expect(queryByText('foo')).toBeTruthy();
		expect(queryByText('bar')).toBeTruthy();
	});

	it('should render an icon', () => {
		const {container} = render(<LoadingModal icon='foo' onClose={noop} />);

		expect(container.querySelector('.icon-root')).toBeTruthy();
	});
});
