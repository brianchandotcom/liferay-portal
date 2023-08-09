import {gql} from 'apollo-boost';

export default gql`
	mutation DataControlRequest(
		$emailAddresses: [String]
		$fileName: String
		$ownerId: String!
		$types: [DataControlTaskType]!
		$userId: String!
		$userName: String!
	) {
		dataControlTasks(
			emailAddresses: $emailAddresses
			fileName: $fileName
			ownerId: $ownerId
			types: $types
			userId: $userId
			userName: $userName
		)
	}
`;
