import {gql} from '@apollo/client';

export default gql`
	query CustomAsset($dashboardId: String!) {
		dashboard(dashboardId: $dashboardId) {
			assetId
			assetTitle
			category
			createDate
			definition
			id
			modifiedByUserName
			modifiedDate
		}
	}
`;
