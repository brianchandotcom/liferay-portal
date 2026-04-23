import {gql} from '@apollo/client';

export default gql`
	query SiteMetrics(
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		siteVisitorHeatMap(
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			column: colDimension
			row: rowDimension
			value
		}
	}
`;
