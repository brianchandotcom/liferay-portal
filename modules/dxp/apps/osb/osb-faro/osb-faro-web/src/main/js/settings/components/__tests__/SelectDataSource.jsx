import React from 'react';
import SelectDataSource from '../SelectDataSource';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {noop} from 'lodash';

jest.unmock('react-dom');

const DATA_SOURCE_ARRAY = [
	{
		iconName: 'csv_logo',
		iconSize: 'xl',
		name: 'test',
		url: '#'
	},
	{
		iconName: 'liferay_logo',
		iconSize: 'xxl',
		name: 'test1',
		url: '#'
	}
];

const mockSections = [
	{
		dataSources: DATA_SOURCE_ARRAY,
		title: 'section 1 title'
	},
	{
		dataSources: [
			{
				iconName: 'salesforce_logo',
				iconSize: 'xxl',
				name: 'test1',
				onClick: noop
			}
		],
		title: 'section two title'
	}
];

describe('SelectDataSource', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<MemoryRouter>
				<SelectDataSource sections={mockSections} />
			</MemoryRouter>
		);
		expect(container).toMatchSnapshot();
	});
});
