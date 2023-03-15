import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterAssetQuery from 'shared/queries/globalFilterAssetQuery';
import {graphql} from '@apollo/react-hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

/**
 * HOC
 * @description Forms Filter
 */
const withFormsFilter = () =>
	graphql(
		globalFilterAssetQuery('form', 'submissionsMetric'),
		getFiltersMapper(result => result.form.submissionsMetric)
	);

export default withFilterComponent(withFormsFilter);
