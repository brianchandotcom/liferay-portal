import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterAssetQuery from 'shared/queries/globalFilterAssetQuery';
import {graphql} from '@apollo/react-hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

/**
 * HOC
 * @description Blogs Filters
 */
const withBlogFilter = () =>
	graphql(
		globalFilterAssetQuery('blog', 'viewsMetric'),
		getFiltersMapper(result => result.blog.viewsMetric)
	);

export default withFilterComponent(withBlogFilter);
