import {gql} from 'apollo-boost';

export default gql`
	query RecommendationList(
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		jobs(keywords: $keywords, size: $size, sort: $sort, start: $start) {
			jobs {
				id
				name
				runDataPeriod
				runDate
				runFrequency
				status
				type
			}
			total
		}
	}
`;
