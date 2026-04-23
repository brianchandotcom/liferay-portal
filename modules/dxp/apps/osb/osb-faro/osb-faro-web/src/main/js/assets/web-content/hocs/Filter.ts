import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterAssetQuery from 'shared/queries/globalFilterAssetQuery';
import {graphql, OperationOption} from '@apollo/client/react/hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

type JournalMetricResult = {
	journal: {
		viewsMetric: unknown;
	};
};

/**
 * HOC
 * @description Web Content Filter
 */
const withWebContentFilter = () =>
	graphql(
		globalFilterAssetQuery('journal', 'viewsMetric'),
		getFiltersMapper(
			(result: JournalMetricResult) => result.journal.viewsMetric
		) as OperationOption<object, object>
	);

export default withFilterComponent(withWebContentFilter);
