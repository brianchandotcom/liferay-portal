import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import TouchpointPathQuery from 'shared/queries/TouchpointPathQuery';
import {graphql} from '@apollo/react-hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

/**
 * HOC
 * @description Touchpoint Path Filter
 */
const withTouchpointPathFilter = () =>
	graphql(
		TouchpointPathQuery,
		getFiltersMapper(result => result.page.viewsMetric)
	);

export default withFilterComponent(withTouchpointPathFilter);
