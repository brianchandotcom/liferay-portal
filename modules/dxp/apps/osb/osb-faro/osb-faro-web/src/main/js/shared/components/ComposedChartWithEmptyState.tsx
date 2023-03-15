import getCN from 'classnames';
import NoResultsDisplay from './NoResultsDisplay';
import React from 'react';

interface IComposedChartWithEmptyStateProps
	extends React.HTMLAttributes<HTMLElement> {
	emptyDescription: string | React.ReactElement;
	emptyTitle: string;
	showEmptyState?: boolean;
}

const ComposedChartWithEmptyState: React.FC<IComposedChartWithEmptyStateProps> = ({
	children,
	emptyDescription,
	emptyTitle,
	showEmptyState = false
}) => (
	<div
		className={getCN('composed-chart-with-empty-state', {
			'composed-chart-with-empty-state--show': showEmptyState
		})}
	>
		{children}

		{showEmptyState && (
			<NoResultsDisplay
				description={emptyDescription}
				title={emptyTitle}
			/>
		)}
	</div>
);

export default ComposedChartWithEmptyState;
