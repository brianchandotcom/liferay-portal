import * as data from 'test/data';
import KnownIndividualsCard from '../KnownIndividualsCard';
import Promise from 'metal-promise';
import React from 'react';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const dataSourceFn = total => () =>
	Promise.resolve(data.mockSearch(data.mockIndividual, total));

const DefaultComponent = props => (
	<StaticRouter>
		<KnownIndividualsCard
			channelId='123'
			dataSourceFn={dataSourceFn(3)}
			groupId='23'
			id='23'
			{...props}
		/>
	</StaticRouter>
);

describe('KnownIndividualsCard', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ NoResultsDisplay', () => {
		const {container} = render(
			<DefaultComponent dataSourceFn={dataSourceFn(0)} />
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ ErrorDisplay', () => {
		const {getByText} = render(
			<DefaultComponent dataSourceFn={() => Promise.reject({})} />
		);

		jest.runAllTimers();

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});
});
