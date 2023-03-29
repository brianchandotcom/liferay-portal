import {gql} from 'apollo-boost';

export default gql`
	query RecommendationPageAssets(
		$keywords: String
		$propertyFilters: [PropertyFilter]
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		pageAssets(
			keywords: $keywords
			propertyFilters: $propertyFilters
			size: $size
			sort: $sort
			start: $start
		) {
			pageAssets {
				canonicalUrl
				description
				keywords {
					type
					value
				}
				id
				title
				url
			}
			total
		}
	}
`;
