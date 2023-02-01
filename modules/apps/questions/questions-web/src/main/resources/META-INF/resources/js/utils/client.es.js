/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {GraphQLClient} from 'graphql-hooks';
import memCache from 'graphql-hooks-memcache';

const headers = {
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'text/plain; charset=utf-8',
};

export const client = new GraphQLClient({
	cache: memCache(),
	fetch,
	headers,
	url: '/o/graphql',
});

export const clientNestedFields = new GraphQLClient({
	cache: memCache(),
	fetch,
	headers,
	url: '/o/graphql?nestedFields=lastPostDate',
});

export const createAnswerQuery = `
	mutation createMessageBoardThreadMessageBoardMessage(
		$articleBody: String!
		$messageBoardThreadId: Long!
	) {
		createMessageBoardThreadMessageBoardMessage(
			messageBoardMessage: {
				articleBody: $articleBody
				encodingFormat: "html"
				viewableBy: ANYONE
			}
			messageBoardThreadId: $messageBoardThreadId
		) {
			viewableBy
		}
	}
`;

export const createCommentQuery = `
	mutation createMessageBoardMessageMessageBoardMessage(
		$articleBody: String!
		$parentMessageBoardMessageId: Long!
	) {
		createMessageBoardMessageMessageBoardMessage(
			messageBoardMessage: {
				articleBody: $articleBody
				encodingFormat: "html"
				viewableBy: ANYONE
			}
			parentMessageBoardMessageId: $parentMessageBoardMessageId
		) {
			actions
			articleBody
			creator {
				name
			}
			dateCreated
			dateModified
			friendlyUrlPath
			hasCompanyMx
			id
			status
		}
	}
`;

export const createQuestionInRootQuery = `
	mutation createSiteMessageBoardThread(
		$articleBody: String!
		$headline: String!
		$keywords: [String]
		$siteKey: String!
	) {
		createSiteMessageBoardThread(
			siteKey: $siteKey
			messageBoardThread: {
				articleBody: $articleBody
				encodingFormat: "html"
				headline: $headline
				keywords: $keywords
				showAsQuestion: true
				subscribed: true
				viewableBy: ANYONE
			}
		) {
			articleBody
			headline
			keywords
			showAsQuestion
		}
	}
`;

export const createQuestionInASectionQuery = `
	mutation createMessageBoardSectionMessageBoardThread(
		$messageBoardSectionId: Long!
		$articleBody: String!
		$headline: String!
		$keywords: [String]
	) {
		createMessageBoardSectionMessageBoardThread(
			messageBoardSectionId: $messageBoardSectionId
			messageBoardThread: {
				articleBody: $articleBody
				encodingFormat: "html"
				headline: $headline
				keywords: $keywords
				showAsQuestion: true
				subscribed: true
				viewableBy: ANYONE
			}
		) {
			articleBody
			headline
			keywords
			showAsQuestion
		}
	}
`;

export const createSubTopicQuery = `
	mutation createMessageBoardSectionMessageBoardSection(
		$description: String
		$parentMessageBoardSectionId: Long!
		$title: String!
	) {
		createMessageBoardSectionMessageBoardSection(
			parentMessageBoardSectionId: $parentMessageBoardSectionId
			messageBoardSection: {
				description: $description
				title: $title
				viewableBy: ANYONE
			}
		) {
			id
			title
		}
	}
`;

export const createTopicQuery = `
	mutation createSiteMessageBoardSection(
		$description: String
		$siteKey: String!
		$title: String!
	) {
		createSiteMessageBoardSection(
			siteKey: $siteKey
			messageBoardSection: {
				description: $description
				title: $title
				viewableBy: ANYONE
			}
		) {
			id
			title
		}
	}
`;

export const createVoteMessageQuery = `
	mutation createMessageBoardMessageMyRating(
		$messageBoardMessageId: Long!
		$ratingValue: Float!
	) {
		createMessageBoardMessageMyRating(
			messageBoardMessageId: $messageBoardMessageId
			rating: {ratingValue: $ratingValue}
		) {
			id
			ratingValue
		}
	}
`;

export const createVoteThreadQuery = `
	mutation createMessageBoardThreadMyRating(
		$messageBoardThreadId: Long!
		$ratingValue: Float!
	) {
		createMessageBoardThreadMyRating(
			messageBoardThreadId: $messageBoardThreadId
			rating: {ratingValue: $ratingValue}
		) {
			id
			ratingValue
		}
	}
`;

export const deleteMessageQuery = `
	mutation deleteMessageBoardMessage($messageBoardMessageId: Long!) {
		deleteMessageBoardMessage(messageBoardMessageId: $messageBoardMessageId)
	}
`;

export const deleteMessageBoardThreadQuery = `
	mutation deleteMessageBoardThread($messageBoardThreadId: Long!) {
		deleteMessageBoardThread(messageBoardThreadId: $messageBoardThreadId)
	}
`;

export const getTagsOrderByDateCreatedQuery = `
	query keywords(
		$page: Int!
		$filter: String
		$pageSize: Int!
		$search: String
		$siteKey: String!
	) {
		keywords(
			page: $page
			filter: $filter
			pageSize: $pageSize
			search: $search
			siteKey: $siteKey
			sort: "dateCreated:desc"
		) {
			items {
				actions
				id
				dateCreated
				name
				subscribed
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getTagsOrderByNumberOfUsagesQuery = `
	query keywordsRanked(
		$page: Int!
		$pageSize: Int!
		$search: String
		$siteKey: String!
	) {
		keywordsRanked(
			page: $page
			pageSize: $pageSize
			search: $search
			siteKey: $siteKey
		) {
			items {
				actions
				id
				keywordUsageCount
				name
				subscribed
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getMessageQuery = `
	query messageBoardMessageByFriendlyUrlPath(
		$friendlyUrlPath: String!
		$siteKey: String!
	) {
		messageBoardMessageByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			articleBody
			headline
			id
		}
	}
`;

export const getThreadQuery = `
	query messageBoardThreadByFriendlyUrlPath(
		$friendlyUrlPath: String!
		$siteKey: String!
	) {
		messageBoardThreadByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			actions
			aggregateRating {
				ratingAverage
				ratingCount
				ratingValue
			}
			articleBody
			creator {
				id
				image
				name
			}
			creatorStatistics {
				joinDate
				lastPostDate
				postsNumber
				rank
			}
			dateCreated
			dateModified
			encodingFormat
			friendlyUrlPath
			headline
			id
			messageBoardRootMessageId
			keywords
			locked
			messageBoardSection {
				friendlyUrlPath
				id
				numberOfMessageBoardSections
				parentMessageBoardSectionId
				title
			}
			myRating {
				ratingValue
			}
			seen
			status
			subscribed
			viewCount
			withValidAnswers: messageBoardMessages(filter: "showAsAnswer eq true") {
				totalCount
				items {
				  id
				  headline
				  articleBody
				  showAsAnswer
				}
			  }
		}
	}
`;

export const getSectionByMessageQuery = `
	query messageBoardMessage($messageBoardMessageId: Long!) {
		messageBoardMessage(messageBoardMessageId: $messageBoardMessageId) {
			friendlyUrlPath
			messageBoardThread {
				messageBoardSection {
					friendlyUrlPath
					id
					title
				}
			}
		}
	}
`;

export const getThreadContentQuery = `
	query messageBoardThreadByFriendlyUrlPath(
		$friendlyUrlPath: String!
		$siteKey: String!
	) {
		messageBoardThreadByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			articleBody
			headline
			id
			keywords
		}
	}
`;

export const getMessagesQuery = `
	query messageBoardThreadMessageBoardMessages(
		$messageBoardThreadId: Long!
		$page: Int!
		$pageSize: Int!
		$sort: String!
	) {
		messageBoardThreadMessageBoardMessages(
			messageBoardThreadId: $messageBoardThreadId
			page: $page
			pageSize: $pageSize
			sort: $sort
		) {
			items {
				actions
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				creatorStatistics {
					joinDate
					lastPostDate
					postsNumber
					rank
				}
				dateCreated
				dateModified
				encodingFormat
				friendlyUrlPath
				hasCompanyMx
				id
				messageBoardMessages(flatten: true, sort: "dateCreated:asc") {
					items {
						actions
						articleBody
						creator {
							id
							image
							name
						}
						dateCreated
						dateModified
						encodingFormat
						friendlyUrlPath
						hasCompanyMx
						id
						modified
						showAsAnswer
						status
					}
				}
				modified
				myRating {
					ratingValue
				}
				showAsAnswer
				status
			}
			pageSize
			totalCount
		}
	}
`;

export const hasListPermissionsQuery = `
	query messageBoardThreads($siteKey: String!) {
		messageBoardThreads(siteKey: $siteKey) {
			actions
		}
	}
`;

export const getSectionThreadsQuery = `
	query messageBoardSectionMessageBoardThreads(
		$filter: String
		$messageBoardSectionId: Long!
		$page: Int!
		$pageSize: Int!
		$search: String
		$sort: String
	) {
		messageBoardSectionMessageBoardThreads(
			filter: $filter
			messageBoardSectionId: $messageBoardSectionId
			page: $page
			pageSize: $pageSize
			search: $search
			sort: $sort
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				dateCreated
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				locked
				messageBoardSection {
					friendlyUrlPath
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				seen
				status
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getThreadsQuery = `
	query messageBoardThreads(
		$filter: String
		$page: Int!
		$pageSize: Int!
		$search: String
		$siteKey: String!
		$sort: String
	) {
		messageBoardThreads(
			filter: $filter
			flatten: true
			page: $page
			pageSize: $pageSize
			search: $search
			siteKey: $siteKey
			sort: $sort
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				dateCreated
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				locked
				messageBoardSection {
					friendlyUrlPath
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				seen
				status
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getRankedThreadsQuery = `
	query messageBoardThreadsRanked(
		$dateModified: Date
		$messageBoardSectionId: Long
		$page: Int!
		$pageSize: Int!
		$sort: String!
	) {
		messageBoardThreadsRanked(
			dateModified: $dateModified
			messageBoardSectionId: $messageBoardSectionId
			page: $page
			pageSize: $pageSize
			sort: $sort
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				locked
				messageBoardSection {
					friendlyUrlPath
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				seen
				status
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getSectionsQuery = `
	query messageBoardSections($siteKey: String!) {
		messageBoardSections(siteKey: $siteKey, sort: "title:asc") {
			actions
			items {
				description
				friendlyUrlPath
				id
				numberOfMessageBoardThreads
				parentMessageBoardSectionId
				subscribed
				title
			}
		}
	}
`;

export const getMessageBoardSectionByFriendlyUrlPathQuery = `
	query messageBoardSectionByFriendlyUrlPath($friendlyUrlPath: String!, $siteKey: String!) {
		messageBoardSectionByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			actions
			friendlyUrlPath
			id
			messageBoardSections(sort: "title:asc") {
				actions
				items {
					id
					description
					friendlyUrlPath
					numberOfMessageBoardSections
					numberOfMessageBoardThreads
					parentMessageBoardSectionId
					subscribed
					title
				}
			}
			numberOfMessageBoardSections
			parentMessageBoardSection {
				friendlyUrlPath
				id
				messageBoardSections {
					items {
						id
						friendlyUrlPath
						numberOfMessageBoardSections
						parentMessageBoardSectionId
						subscribed
						title
					}
				}
				numberOfMessageBoardSections
				parentMessageBoardSectionId
				subscribed
				title
			}
			parentMessageBoardSectionId
			subscribed
			title
		}
	}
`;

export const getSectionBySectionTitleQuery = `
	query messageBoardSections($filter: String!, $siteKey: String!) {
		messageBoardSections(
			filter: $filter
			flatten: true
			pageSize: 1
			siteKey: $siteKey
			sort: "title:asc"
		) {
			actions
			items {
				actions
				id
				messageBoardSections(sort: "title:asc") {
					actions
					items {
						id
						description
						numberOfMessageBoardSections
						numberOfMessageBoardThreads
						parentMessageBoardSectionId
						subscribed
						title
					}
				}
				numberOfMessageBoardSections
				parentMessageBoardSection {
					friendlyUrlPath
					id
					messageBoardSections {
						items {
							friendlyUrlPath
							id
							numberOfMessageBoardSections
							parentMessageBoardSectionId
							subscribed
							title
						}
					}
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					subscribed
					title
				}
				parentMessageBoardSectionId
				subscribed
				title
			}
		}
	}
`;

export const getRelatedThreadsQuery = `
	query messageBoardThreads($search: String!, $siteKey: String!) {
		messageBoardThreads(
			page: 1
			pageSize: 4
			flatten: true
			search: $search
			siteKey: $siteKey
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				creator {
					id
					image
					name
				}
				dateModified
				friendlyUrlPath
				headline
				id
				locked
				messageBoardSection {
					friendlyUrlPath
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				seen
				status
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getSectionQuery = `
	query messageBoardSection($messageBoardSectionId: Long!) {
		messageBoardSection(messageBoardSectionId: $messageBoardSectionId) {
			actions
			friendlyUrlPath
			id
			messageBoardSections(sort: "title:asc") {
				items {
					friendlyUrlPath
					id
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					subscribed
					title
				}
			}
			numberOfMessageBoardThreads
			parentMessageBoardSectionId
			subscribed
			title
		}
	}
`;

export function getThread(friendlyUrlPath, siteKey) {
	return clientNestedFields.request({
		query: getThreadQuery,
		variables: {
			friendlyUrlPath,
			siteKey,
		},
	});
}

export function getMessages(
	messageBoardThreadId,
	page,
	pageSize,
	sortBy = 'dateCreated:asc'
) {
	return clientNestedFields.request({
		query: getMessagesQuery,
		variables: {
			messageBoardThreadId,
			page,
			pageSize,
			sort: sortBy,
		},
	});
}

export const getUserActivityQuery = `
	query messageBoardMessages(
		$filter: String
		$page: Int!
		$pageSize: Int!
		$siteKey: String!
	) {
		messageBoardMessages(
			filter: $filter
			flatten: true
			page: $page
			pageSize: $pageSize
			siteKey: $siteKey
			sort: "dateCreated:desc"
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				creatorStatistics {
					postsNumber
					rank
				}
				dateCreated
				dateModified
				friendlyUrlPath
				headline
				id
				keywords
				messageBoardThread {
					messageBoardSection {
						friendlyUrlPath
						id
						title
					}
				}
				numberOfMessageBoardMessages
				parentMessageBoardMessage {
					articleBody
					headline
				}
				showAsAnswer
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const markAsAnswerMessageBoardMessageQuery = `
	mutation patchMessageBoardMessage(
		$messageBoardMessageId: Long!
		$showAsAnswer: Boolean!
	) {
		patchMessageBoardMessage(
			messageBoardMessage: {showAsAnswer: $showAsAnswer}
			messageBoardMessageId: $messageBoardMessageId
		) {
			id
			showAsAnswer
		}
	}
`;

export const updateMessageQuery = `
	mutation patchMessageBoardMessage(
		$articleBody: String!
		$messageBoardMessageId: Long!
	) {
		patchMessageBoardMessage(
			messageBoardMessage: {
				articleBody: $articleBody
				encodingFormat: "html"
			}
			messageBoardMessageId: $messageBoardMessageId
		) {
			articleBody
		}
	}
`;

export const updateThreadQuery = `
	mutation patchMessageBoardThread(
		$articleBody: String!
		$headline: String!
		$keywords: [String]
		$messageBoardThreadId: Long!
	) {
		patchMessageBoardThread(
			messageBoardThread: {
				articleBody: $articleBody
				encodingFormat: "html"
				headline: $headline
				keywords: $keywords
			}
			messageBoardThreadId: $messageBoardThreadId
		) {
			articleBody
			headline
			keywords
		}
	}
`;

export const subscribeQuery = `
	mutation updateMessageBoardThreadSubscribe($messageBoardThreadId: Long!) {
		updateMessageBoardThreadSubscribe(
			messageBoardThreadId: $messageBoardThreadId
		)
	}
`;

export const unsubscribeQuery = `
	mutation updateMessageBoardThreadUnsubscribe($messageBoardThreadId: Long!) {
		updateMessageBoardThreadUnsubscribe(
			messageBoardThreadId: $messageBoardThreadId
		)
	}
`;

export const subscribeSectionQuery = `
	mutation updateMessageBoardSectionSubscribe($messageBoardSectionId: Long!) {
		updateMessageBoardSectionSubscribe(
			messageBoardSectionId: $messageBoardSectionId
		)
	}
`;

export const unsubscribeSectionQuery = `
	mutation updateMessageBoardSectionUnsubscribe(
		$messageBoardSectionId: Long!
	) {
		updateMessageBoardSectionUnsubscribe(
			messageBoardSectionId: $messageBoardSectionId
		)
	}
`;

export const subscribeTagQuery = `
	mutation updateKeywordSubscribe($keywordId: Long!) {
		updateKeywordSubscribe(
			keywordId: $keywordId
		)
	}
`;

export const unsubscribeTagQuery = `
	mutation updateKeywordUnsubscribe($keywordId: Long!) {
		updateKeywordUnsubscribe(
			keywordId: $keywordId
		)
	}
`;

export const getSubscriptionsQuery = `
	query myUserAccountSubscriptions($contentType: String!) {
		myUserAccountSubscriptions(contentType: $contentType) {
			items {
				id
				contentType
				graphQLNode {
					... on MessageBoardSection {
						id
						title
					}
					... on MessageBoardThread {
						actions
						aggregateRating {
							ratingAverage
							ratingCount
							ratingValue
						}
						articleBody
						creator {
							id
							image
							name
						}
						creatorStatistics {
							joinDate
							lastPostDate
							postsNumber
							rank
						}
						dateCreated
						dateModified
						encodingFormat
						friendlyUrlPath
						headline
						id
						keywords
						messageBoardSection {
							friendlyUrlPath
							id
							numberOfMessageBoardSections
							parentMessageBoardSectionId
							title
						}
						myRating {
							ratingValue
						}
						showAsQuestion
						subscribed
						viewCount
					}
				}
			}
		}
	}
`;

export const unsubscribeMyUserAccountQuery = `
	mutation deleteMyUserAccountSubscription($subscriptionId: Long!) {
		deleteMyUserAccountSubscription(subscriptionId: $subscriptionId)
	}
`;
