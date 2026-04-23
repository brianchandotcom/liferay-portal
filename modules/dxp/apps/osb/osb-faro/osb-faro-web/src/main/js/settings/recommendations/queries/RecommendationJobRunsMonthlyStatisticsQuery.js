import {gql} from '@apollo/client';

export default gql`
	query RecommendationJobRunsMonthlyStatistics($jobId: String!) {
		jobRunsMonthlyStatistics(jobId: $jobId) {
			availableJobRuns
			scheduledJobRuns
		}
	}
`;
