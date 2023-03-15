import {gql} from 'apollo-boost';

export default gql`
	query RecommendationJobRunsMonthlyStatistics($jobId: String!) {
		jobRunsMonthlyStatistics(jobId: $jobId) {
			availableJobRuns
			scheduledJobRuns
		}
	}
`;
