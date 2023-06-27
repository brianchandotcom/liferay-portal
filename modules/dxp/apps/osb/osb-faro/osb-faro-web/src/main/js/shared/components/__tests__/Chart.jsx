import Chart from '../Chart';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Chart', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<Chart data={[{data: [], id: 'foo'}]} dataId='bar' id='foo' />
		);
		expect(container).toMatchSnapshot();
	});

	it('should render w/ NoResultsDisplay', () => {
		const {container} = render(<Chart data={[]} dataId='bar' id='foo' />);

		expect(container.querySelector('.no-results-root')).toBeTruthy();
	});

	it('should render w/ loading', () => {
		const {container} = render(
			<Chart data={[]} dataId='bar' id='foo' loading />
		);

		expect(container.querySelector('.loading-root')).toBeTruthy();
	});
});
