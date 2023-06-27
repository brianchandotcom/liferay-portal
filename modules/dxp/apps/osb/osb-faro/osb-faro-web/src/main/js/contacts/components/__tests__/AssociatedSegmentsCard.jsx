import * as data from 'test/data';
import AssociatedSegmentsCard from '../AssociatedSegmentsCard';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import Promise from 'metal-promise';
import React from 'react';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<StaticRouter>
		<AssociatedSegmentsCard
			dataSourceFn={() =>
				Promise.resolve(data.mockSearch(data.mockSegment, 2))
			}
			groupId='23'
			id='123'
			pageUrl='/foo'
			{...props}
		/>
	</StaticRouter>
);

describe('AssociatedSegmentsCard', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ loading overlay', () => {
		const {container} = render(
			<DefaultComponent dataSourceFn={() => Promise.resolve({})} />
		);

		expect(container.querySelector('.overlay')).toBeTruthy();
	});

	it('should render with an error display', () => {
		const {getByText} = render(
			<DefaultComponent dataSourceFn={() => Promise.reject({})} />
		);

		jest.runAllTimers();

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});

	it('should render with an no results display', () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve(data.mockSearch(data.mockSegment, 0))
				}
				noResultsRenderer={() => <NoResultsDisplay />}
			/>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
