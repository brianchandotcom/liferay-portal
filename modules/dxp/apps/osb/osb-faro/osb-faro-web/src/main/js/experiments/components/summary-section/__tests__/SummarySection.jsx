import React from 'react';
import SummarySection from '../index';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummarySection', () => {
	it('should render', () => {
		const {container} = render(<SummarySection />);

		expect(container).toMatchSnapshot();
	});

	it('should render with title', () => {
		const summarySectionTitle = 'Summary title';

		const {getByText} = render(
			<SummarySection title={summarySectionTitle} />
		);

		expect(getByText(summarySectionTitle)).toBeTruthy();
	});

	describe('Variant', () => {
		it('should render', () => {
			const {container} = render(
				<SummarySection.Variant lift='5%' status='up' />
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('Description', () => {
		it('should render', () => {
			const {container} = render(
				<SummarySection.Description value='My Summary Description' />
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('Heading', () => {
		it('should render', () => {
			const {container} = render(
				<SummarySection.Heading value='My Summary Heading' />
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('ProgressBar', () => {
		it('should render', () => {
			const {container} = render(
				<SummarySection.ProgressBar value={100} />
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('MetricType', () => {
		it('should render', () => {
			const {container} = render(
				<SummarySection.MetricType value='Click Rate' />
			);

			expect(container).toMatchSnapshot();
		});
	});
});
