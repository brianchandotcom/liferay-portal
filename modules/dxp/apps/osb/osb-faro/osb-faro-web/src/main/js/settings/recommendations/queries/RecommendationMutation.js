import {gql} from 'apollo-boost';

export const RECOMMENDATION_MUTATION = gql`
	mutation RecommendationMutation(
		$name: String!
		$parameters: [JobParameterInput]
		$runDataPeriod: JobRunDataPeriod
		$runFrequency: JobRunFrequency
		$runNow: Boolean
		$type: JobType!
	) {
		createJob(
			name: $name
			parameters: $parameters
			runDataPeriod: $runDataPeriod
			runFrequency: $runFrequency
			runNow: $runNow
			type: $type
		) {
			id
			name
			parameters {
				name
				value
			}
			status
			runDataPeriod
			runDate
			runFrequency
			type
		}
	}
`;

export const RECOMMENDATION_DELETE_MUTATION = gql`
	mutation RecommendationDeleteMutation($jobIds: [String]!) {
		deleteJobs(jobIds: $jobIds)
	}
`;

export const RECOMMENDATION_RUN_MUTATION = gql`
	mutation RecommendationRunMutation(
		$jobId: String!
		$runDataPeriod: JobRunDataPeriod!
	) {
		runJob(jobId: $jobId, runDataPeriod: $runDataPeriod) {
			id
		}
	}
`;

export const RECOMMENDATION_UPDATE_MUTATION = gql`
	mutation RecommendationUpdateMutation(
		$jobId: String!
		$name: String!
		$parameters: [JobParameterInput]
		$runDataPeriod: JobRunDataPeriod
		$runFrequency: JobRunFrequency
		$runNow: Boolean
	) {
		updateJob(
			jobId: $jobId
			name: $name
			parameters: $parameters
			runDataPeriod: $runDataPeriod
			runFrequency: $runFrequency
			runNow: $runNow
		) {
			id
			name
			parameters {
				name
				value
			}
			status
			runDataPeriod
			runDate
			runFrequency
			type
		}
	}
`;
