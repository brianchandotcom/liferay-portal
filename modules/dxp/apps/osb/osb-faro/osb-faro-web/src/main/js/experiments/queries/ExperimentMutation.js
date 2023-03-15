import {gql} from 'apollo-boost';

export const EXPERIMENT_MUTATION = gql`
	mutation ExperimentMutation(
		$experimentId: String!
		$experimentSettings: ExperimentSettings
		$publishedDXPVariantId: String
		$status: String!
	) {
		experiment(
			experimentId: $experimentId
			experimentSettings: $experimentSettings
			publishedDXPVariantId: $publishedDXPVariantId
			status: $status
		) {
			id
		}
	}
`;

export const EXPERIMENT_DELETE_MUTATION = gql`
	mutation ExperimentDeleteMutation($experimentId: String!) {
		deleteExperiment(experimentId: $experimentId)
	}
`;
