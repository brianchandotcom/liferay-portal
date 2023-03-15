import Panel from '../Panel';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Panel', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Panel title='Panel Title' />);
		expect(container).toMatchSnapshot();
	});

	it('should NOT render a panel body when expandable is true and the expanded state is false', () => {
		const {queryByText} = render(
			<Panel expandable title='Panel Title'>
				{'Foo Panel Body'}
			</Panel>
		);

		expect(queryByText('Foo Panel Body')).toBeNull();
	});

	it('should render a panel body when expandable is true and initialExpanded prop is true', () => {
		const {queryByText} = render(
			<Panel expandable initialExpanded title='Panel Title'>
				{'Foo Panel Body'}
			</Panel>
		);

		expect(queryByText('Foo Panel Body')).toBeTruthy();
	});
});
