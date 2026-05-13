import LifecycleStatus from '../LifecycleStatus';
import React from 'react';
import {cleanup, render, screen, within} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('@clayui/multi-step-nav', () => {
	const MultiStepNav = ({
		children,
		className
	}: {
		children: React.ReactNode;
		className?: string;
	}) => <ol className={className}>{children}</ol>;

	MultiStepNav.Item = ({
		active,
		children,
		state
	}: {
		active?: boolean;
		children: React.ReactNode;
		state?: string;
	}) => (
		<li data-active={active ? 'true' : undefined} data-state={state}>
			{children}
		</li>
	);

	MultiStepNav.Title = ({children}: {children: React.ReactNode}) => (
		<span>{children}</span>
	);

	MultiStepNav.Divider = () => null;

	MultiStepNav.Indicator = ({
		label,
		subTitle
	}: {
		label?: React.ReactText;
		subTitle?: React.ReactText;
	}) => (
		<div>
			{subTitle ? <span>{subTitle}</span> : null}
			<span>{label}</span>
		</div>
	);

	return {__esModule: true, default: MultiStepNav};
});

describe('LifecycleStatus', () => {
	afterEach(cleanup);

	describe('rendering', () => {
		it('should render the card title and description', () => {
			render(<LifecycleStatus />);

			expect(screen.getByText('LIFECYCLE STATUS')).toBeInTheDocument();
			expect(
				screen.getByText('Current stage progression for this account.')
			).toBeInTheDocument();
		});

		it('should render every progression stage label in the multistep view', () => {
			const {container} = render(<LifecycleStatus />);

			const multistep = container.querySelector(
				'.lifecycle-status-multistep'
			) as HTMLElement;

			expect(within(multistep).getByText('Aware')).toBeInTheDocument();
			expect(within(multistep).getByText('Engaged')).toBeInTheDocument();
			expect(
				within(multistep).getByText('Pipeline')
			).toBeInTheDocument();
			expect(
				within(multistep).getByText('Onboarding')
			).toBeInTheDocument();
			expect(
				within(multistep).getByText('Established')
			).toBeInTheDocument();
		});

		it('should render numeric indicators 1 through 5 in the multistep view', () => {
			const {container} = render(<LifecycleStatus />);

			const multistep = container.querySelector(
				'.lifecycle-status-multistep'
			) as HTMLElement;

			for (const label of ['1', '2', '3', '4', '5']) {
				expect(within(multistep).getByText(label)).toBeInTheDocument();
			}
		});

		it('should render the start date below stages that have one', () => {
			const {container} = render(<LifecycleStatus />);

			const multistep = container.querySelector(
				'.lifecycle-status-multistep'
			) as HTMLElement;

			expect(
				within(multistep).getByText('Jan 4, 2026')
			).toBeInTheDocument();
			expect(
				within(multistep).getByText('Jan 16, 2026')
			).toBeInTheDocument();
		});

		it('should mark the in-progress stage as active and completed stages as complete', () => {
			const {container} = render(<LifecycleStatus />);

			const items = container.querySelectorAll(
				'.lifecycle-status-multistep li'
			);

			expect(items[0].getAttribute('data-state')).toBe('complete');
			expect(items[1].getAttribute('data-active')).toBe('true');
			expect(items[1].getAttribute('data-state')).toBeNull();
			expect(items[2].getAttribute('data-state')).toBeNull();
		});

		it('should render the At-Risk stage with a sticker and icon in the multistep view', () => {
			const {container} = render(<LifecycleStatus />);

			const multistep = container.querySelector(
				'.lifecycle-status-multistep'
			) as HTMLElement;

			expect(within(multistep).getByText('At Risk')).toBeInTheDocument();
			expect(multistep.querySelector('.sticker')).toBeInTheDocument();
			expect(
				multistep.querySelector('.lexicon-icon-exclamation-circle')
			).toBeInTheDocument();
		});
	});
});
