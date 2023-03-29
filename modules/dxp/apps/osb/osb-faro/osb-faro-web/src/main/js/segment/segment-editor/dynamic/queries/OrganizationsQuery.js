import {gql} from 'apollo-boost';

export default gql`
	query OrganizationsList(
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		organizations(
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			dxpEntities: organizations {
				id
				name
				... on Organization {
					dataSourceName
					parentName
					type
				}
			}
			total
		}
	}
`;
