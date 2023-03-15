import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {CriteriaSidebarItem} from '../CriteriaSidebarItem';

const connectDnd = jest.fn(el => el);

jest.unmock('react-dom');

describe('CriteriaSidebarItem', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<CriteriaSidebarItem
				connectDragSource={connectDnd}
				label='Page Views'
				propertyKey='user'
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
