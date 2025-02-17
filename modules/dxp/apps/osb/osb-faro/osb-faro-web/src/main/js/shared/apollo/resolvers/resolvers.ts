import AssetAppearsOnResolver from './AssetAppearsOnResolver';
import CommerceAverageOrderValueResolver from './CommerceAverageOrderValueResolver';
import CommerceAverageRevenuePerAccountResolver from './CommerceAverageRevenuePerAccountResolver';
import CommerceIncompleteOrdersResolver from './CommerceIncompleteOrdersResolver';
import CommerceTotalOrderValueResolver from './CommerceTotalOrderValueResolver';
import CustomAssetsListResolver from './CustomAssetsListResolver';
import DocumentsAndMediaListResolver from './DocumentsAndMediaListResolver';
import DocumentsAndMediaMetricsResolver from './DocumentsAndMediaMetricsResolver';
import EventAnalysisListResolver from './EventAnalysisListResolver';
import ExperimentResolver from './ExperimentResolver';
import IndividualSiteMetricsResolver from './individualSiteMetricsResolver';
import InterestsResolver from './InterestsResolver';
import PagePathResolver from './PagePathResolver';

/**
 * How it works?
 *
 * Add a @client value on the query to mock data
 * on frontend side, example:
 *
 * query Foo ($foo: String!) {
 *     queryName (title: $title) @client {
 *         data
 *     }
 * }
 */

export const resolvers = {
	assetPages: AssetAppearsOnResolver,
	dashboards: CustomAssetsListResolver,
	document: DocumentsAndMediaMetricsResolver,
	documents: DocumentsAndMediaListResolver,
	eventAnalysisList: EventAnalysisListResolver,
	experiment: ExperimentResolver,
	individualInterests: InterestsResolver,
	orderAccountAverageCurrencyValues: CommerceAverageRevenuePerAccountResolver,
	orderAverageCurrencyValues: CommerceAverageOrderValueResolver,
	orderIncompleteCurrencyValues: CommerceIncompleteOrdersResolver,
	orderTotalCurrencyValues: CommerceTotalOrderValueResolver,
	pagePath: PagePathResolver,
	site: IndividualSiteMetricsResolver,
	siteInterests: InterestsResolver
};
