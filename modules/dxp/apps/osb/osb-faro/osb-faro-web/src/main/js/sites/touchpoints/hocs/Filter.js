import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterTouchpointQuery from 'shared/queries/globalFilterTouchpointQuery';
import {graphql} from '@apollo/react-hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

/**
 * HOC
 * @description Touchpoint Filter
 */
const withTouchpointFilter = () =>
	graphql(
		globalFilterTouchpointQuery('page', 'viewsMetric'),
		getFiltersMapper(result => result.page.viewsMetric)
	);

export default withFilterComponent(withTouchpointFilter);
