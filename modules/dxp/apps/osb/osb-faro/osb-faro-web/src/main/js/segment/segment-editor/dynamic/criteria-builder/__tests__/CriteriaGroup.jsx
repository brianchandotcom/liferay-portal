import CriteriaGroup from '../CriteriaGroup';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

jest.unmock('react-dom');

describe('CriteriaGroup', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaGroup />
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
