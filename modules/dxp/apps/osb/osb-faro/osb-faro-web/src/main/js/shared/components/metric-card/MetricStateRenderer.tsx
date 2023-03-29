import ErrorDisplay from '../ErrorDisplay';
import React from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import {ApolloError} from 'apollo-client';

interface IMetricStateRendererProps {
	error: ApolloError;
	loading: boolean;
	loadingHeight?: number;
}

const MetricStateRenderer: React.FC<IMetricStateRendererProps> = ({
	children,
	error,
	loading,
	loadingHeight
}) => (
	<StatesRenderer empty={false} error={!!error} loading={loading}>
		<StatesRenderer.Loading style={{minHeight: loadingHeight}} />
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export default MetricStateRenderer;
