import RecommendationQuery from 'settings/recommendations/queries/RecommendationQuery';
import {compose} from 'redux';
import {graphql} from '@apollo/react-hoc';
import {Routes} from 'shared/util/router';
import {safeResultToProps} from 'shared/util/mappers';
import {withError, withLoading, withNull} from 'shared/hoc/util';

const withRecommendation = compose(
	graphql(RecommendationQuery, {
		options: ({
			router: {
				params: {jobId}
			}
		}) => ({
			variables: {
				jobId
			}
		}),
		props: safeResultToProps(({jobById}) => ({job: jobById}))
	}),
	withLoading(),
	withError({page: true}),
	withNull('job', {
		entityType: Liferay.Language.get('recommendation-model'),
		linkRoute: Routes.SETTINGS_RECOMMENDATIONS
	})
);

export default withRecommendation;
