import InfoPopover from '../InfoPopover';
import React from 'react';
import ReactDOM from 'react-dom';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

ReactDOM.createPortal = jest.fn();

describe('InfoPopover', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<InfoPopover content='foo content' title='foo title' />
		);
		expect(container).toMatchSnapshot();
	});
});
