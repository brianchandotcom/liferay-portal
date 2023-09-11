import {gql} from 'apollo-boost';

export const EXPERIMENT_QUERY = gql`
	query Experiment($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			bestVariant @client
			description
			dxpSegmentName
			dxpExperienceName
			dxpVariants {
				changes
				control
				dxpVariantId
				dxpVariantName
				trafficSplit
				uniqueVisitors
			}
			finishedDate
			goal {
				metric
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
			modifiedDate
			name
			pageURL
			publishedDXPVariantId
			sessions
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
			description
			dxpSegmentName
			dxpExperienceName
			dxpVariants {
				control
				dxpVariantName
				dxpVariantId
				trafficSplit
			}
			goal {
				metric
				target
			}
			id
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

export const EXPERIMENT_SESSION_HISTOGRAM_QUERY = gql`
	query ExperimentSessionHistogram($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			id
			sessionsHistogram {
				key
				value
			}
		}
	}
`;

export const EXPERIMENT_SESSION_VARIANTS_HISTOGRAM_QUERY = gql`
	query ExperimentSessionPerVariantHistogram($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			dxpVariants {
				control
				dxpVariantName
				sessionsHistogram {
					key
					value
				}
			}
			id
		}
	}
`;

export const EXPERIMENT_VARIANTS_HISTOGRAM_QUERY = gql`
	query ExperimentVariantsHistogram($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			dxpVariants {
				control
				dxpVariantName
				dxpVariantId
			}
			goal {
				metric
			}
			id
			metricsHistogram {
				variantMetrics {
					confidenceInterval
					dxpVariantId
					improvement
					median
				}
				processedDate
			}
		}
	}
`;

export const EXPERIMENT_ROOT_QUERY = gql`
	query ExperimentRoot($experimentId: String!) {
		experiment(experimentId: $experimentId) {
			channelId
			id
			name
			pageURL
			publishable
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
