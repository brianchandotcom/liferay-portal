import React from 'react';
import StagedSubnav from '../StagedSubnav';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<StagedSubnav
		viewCurrentLinkText='view current items'
		viewStagedLinkText='view added items'
		{...props}
	/>
);

describe('StagedSubnav', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with the showStaged active labels', () => {
		const {getByText} = render(<DefaultComponent showStaged />);

		expect(getByText('Showing only selected items.')).toBeTruthy();
	});
});
