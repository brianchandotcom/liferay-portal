import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterAssetQuery from 'shared/queries/globalFilterAssetQuery';
import {graphql} from '@apollo/react-hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

/**
 * HOC
 * @description Web Content Filter
 */
const withWebContentFilter = () =>
	graphql(
		globalFilterAssetQuery('journal', 'viewsMetric'),
		getFiltersMapper(result => result.journal.viewsMetric)
	);

export default withFilterComponent(withWebContentFilter);
