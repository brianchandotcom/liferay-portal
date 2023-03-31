import ErrorDisplay from '../ErrorDisplay';
import React from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import {ApolloError} from 'apollo-client';

interface IAudienceReportStateRendererProps {
	error: ApolloError;
	loading: boolean;
}

const AudienceReportStateRenderer: React.FC<IAudienceReportStateRendererProps> = ({
	children,
	error,
	loading
}) => (
	<StatesRenderer empty={false} error={!!error} loading={loading}>
		<StatesRenderer.Loading />
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export default AudienceReportStateRenderer;
