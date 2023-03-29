import {gql} from 'apollo-boost';

export default gql`
	mutation DataControlRequest(
		$emailAddresses: [String]
		$fileName: String
		$ownerId: String!
		$types: [DataControlTaskType]!
	) {
		dataControlTasks(
			emailAddresses: $emailAddresses
			fileName: $fileName
			ownerId: $ownerId
			types: $types
		)
	}
`;
