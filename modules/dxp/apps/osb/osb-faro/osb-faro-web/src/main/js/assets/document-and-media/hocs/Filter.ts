import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterAssetQuery from 'shared/queries/globalFilterAssetQuery';
import {graphql, OperationOption} from '@apollo/client/react/hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

type DocumentMetricResult = {
	document: {
		downloadsMetric: unknown;
	};
};

/**
 * HOC
 * @description Documents And Media Filter
 */
const withDocumentsAndMediaFilter = () =>
	graphql(
		globalFilterAssetQuery('document', 'downloadsMetric'),
		getFiltersMapper(
			(result: DocumentMetricResult) => result.document.downloadsMetric
		) as OperationOption<object, object>
	);

export default withFilterComponent(withDocumentsAndMediaFilter);
