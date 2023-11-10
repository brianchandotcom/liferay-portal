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
				views
				title
			}
			previousPagePathNodes {
				canonicalUrl
				views
				title
			}
			views
			title
		}
	}
`;
