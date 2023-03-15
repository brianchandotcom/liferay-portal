import Promise from 'metal-promise';
import React from 'react';
import SearchableEntityList from '../SearchableEntityList';
import {cleanup, render} from '@testing-library/react';
import {mockIndividual} from 'test/data';
import {times} from 'lodash';
import {withStaticRouter} from 'test/mock-router';

jest.unmock('react-dom');

const TOTAL = 5;
const INDIVIDUALS = times(TOTAL, i => mockIndividual(i));

const DefaultComponent = withStaticRouter(SearchableEntityList);

describe('SearchableEntityList', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId='23'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ Checkboxes', () => {
		const {queryByTestId} = render(
			<DefaultComponent
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId='23'
				showCheckbox
			/>
		);

		expect(queryByTestId('select-all-checkbox')).toBeTruthy();
	});
});
