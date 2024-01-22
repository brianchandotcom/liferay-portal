import {gql} from 'apollo-boost';

export default gql`
	query PagePath(
		$canonicalUrl: String
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$segmentId: String
		$title: String!
	) {
		pagePath(
			canonicalUrl: $canonicalUrl
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			segmentId: $segmentId
			title: $title
		) {
			canonicalUrl
			followingPagePathNodes {
				canonicalUrl
				external
				views
				title
			}
			previousPagePathNodes {
				canonicalUrl
				external
				views
				title
			}
			views
			title
		}
	}
`;
