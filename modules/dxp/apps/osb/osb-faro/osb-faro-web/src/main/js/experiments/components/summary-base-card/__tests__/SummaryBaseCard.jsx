import React from 'react';
import SummaryBaseCard from '../index';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummaryBaseCard', () => {
	it('should render component', () => {
		const {container} = render(<SummaryBaseCard />);

		expect(container).toMatchSnapshot();
	});
});

describe('SummaryBaseCard.Header Actions', () => {
	it('should render component with Header', () => {
		const {container, getByText} = render(
			<SummaryBaseCard>
				<SummaryBaseCard.Header>
					{'Summary Card with Header'}
				</SummaryBaseCard.Header>
			</SummaryBaseCard>
		);

		expect(getByText('Summary Card with Header')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('should render component with Header and 1 modal', () => {
		const MODALS = [
			{
				title: 'action 01'
			}
		];

		const {container, getByText} = render(
			<SummaryBaseCard>
				<SummaryBaseCard.Header modals={MODALS}>
					{'Summary Card with Header'}
				</SummaryBaseCard.Header>
			</SummaryBaseCard>
		);

		expect(getByText('action 01')).toBeEnabled();
		expect(
			container.querySelector('.btn-group').firstChild.nextSibling
		).toBeFalsy();
	});

	it('should render component with Header and multiple modals', () => {
		const MODALS = [
			{
				title: 'action 01'
			},
			{
				label: 'action 02',
				type: 'action02'
			},
			{
				label: 'action 03',
				type: 'action03'
			},
			{
				label: 'action 04',
				type: 'action04'
			}
		];

		const {container, getByText} = render(
			<SummaryBaseCard>
				<SummaryBaseCard.Header modals={MODALS}>
					{'Summary Card with Header'}
				</SummaryBaseCard.Header>
			</SummaryBaseCard>
		);

		expect(getByText('action 01')).toBeTruthy();
		expect(
			container.querySelector('.btn-group').firstChild.nextSibling
		).toBeTruthy();
	});
});

describe('SummaryBaseCard.Body', () => {
	it('should render component with Body', () => {
		const {container, getByText} = render(
			<SummaryBaseCard>
				<SummaryBaseCard.Body>
					{'Summary Card with Body'}
				</SummaryBaseCard.Body>
			</SummaryBaseCard>
		);

		expect(getByText('Summary Card with Body')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});

describe('SummaryBaseCard.Footer', () => {
	it('should render component with Footer', () => {
		const {container, getByText} = render(
			<SummaryBaseCard>
				<SummaryBaseCard.Footer>
					{'Summary Card with Footer'}
				</SummaryBaseCard.Footer>
			</SummaryBaseCard>
		);

		expect(container.querySelector('.card-footer')).toBeTruthy();
		expect(getByText('Summary Card with Footer'));
		expect(container).toMatchSnapshot();
	});
});
