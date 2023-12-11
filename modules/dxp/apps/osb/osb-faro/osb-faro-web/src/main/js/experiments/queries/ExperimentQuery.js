import {gql} from 'apollo-boost';

export const EXPERIMENT_QUERY = gql`
	query Experiment($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			description
			dxpExperienceName
			dxpSegmentName
			dxpVariants {
				changes
				control
				dxpVariantId
				dxpVariantName
				sessionsHistogram {
					key
					value
				}
				trafficSplit
				uniqueVisitors
			}
			finishedDate
			goal {
				metric
				target
			}
			id
			metrics {
				completion
				elapsedDays
				estimatedDaysLeft
				variantMetrics {
					confidenceInterval
					dxpVariantId
					improvement
					median
					probabilityToWin
				}
			}
			metricsHistogram {
				processedDate
				variantMetrics {
					confidenceInterval
					dxpVariantId
					improvement
					median
				}
			}
			modifiedDate
			name
			pageURL
			publishable
			publishedDXPVariantId
			sessions
			sessionsHistogram {
				key
				value
			}
			startedDate
			status
			type
			winnerDXPVariantId
		}
	}
`;

export const EXPERIMENT_DRAFT_QUERY = gql`
	query ExperimentDraft($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			dxpExperienceName
			dxpSegmentName
			dxpVariants {
				control
			}
			goal {
				metric
				target
			}
			id
			name
			pageURL
			status
		}
	}
`;

export const EXPERIMENT_LIST_QUERY = gql`
	query Experiments(
		$channelId: String
		$size: Int!
		$start: Int!
		$keywords: String
		$sort: Sort!
	) {
		experiments(
			channelId: $channelId
			size: $size
			start: $start
			sort: $sort
			keywords: $keywords
		) {
			experiments {
				createDate
				description
				id
				modifiedDate
				name
				pageURL
				status
				type
			}
			total
		}
	}
`;

export const EXPERIMENT_STATUS_QUERY = gql`
	query ExperimentStatus($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			status
		}
	}
`;

export const EXPERIMENT_ESTIMATED_DAYS_DURATION = gql`
	query ExperimentEstimatedDaysDuration(
		$experimentId: String!
		$experimentSettings: ExperimentSettings!
	) {
		experimentEstimatedDaysDuration(
			experimentId: $experimentId
			experimentSettings: $experimentSettings
		)
	}
`;
