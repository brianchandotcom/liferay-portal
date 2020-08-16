/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ApolloClient, HttpLink, InMemoryCache, gql} from '@apollo/client';
import {fetch} from 'frontend-js-web';

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'text/plain; charset=utf-8',
};

export const client = new ApolloClient({
	cache: new InMemoryCache(),
	defaultOptions: {
		query: {
			errorPolicy: 'all',
		},
	},
	link: new HttpLink({
		credentials: 'include',
		fetch,
		headers: HEADERS,
		uri: '/o/graphql',
	}),
});

export const createAnswerQuery = gql`
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

export const createCommentQuery = gql`
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
			id
		}
	}
`;

export const createQuestionQuery = gql`
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

export const createTopicQuery = gql`
	mutation createMessageBoardSectionMessageBoardSection(
		$description: String
		$parentMessageBoardSectionId: Long!
		$title: String!
	) {
		createMessageBoardSectionMessageBoardSection(
			parentMessageBoardSectionId: $parentMessageBoardSectionId
			messageBoardSection: {description: $description, title: $title}
		) {
			id
			title
		}
	}
`;

export const createVoteMessageQuery = gql`
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

export const createVoteThreadQuery = gql`
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

export const deleteMessageQuery = gql`
	mutation deleteMessageBoardMessage($messageBoardMessageId: Long!) {
		deleteMessageBoardMessage(messageBoardMessageId: $messageBoardMessageId)
	}
`;

export const deleteMessageBoardThreadQuery = gql`
	mutation deleteMessageBoardThread($messageBoardThreadId: Long!) {
		deleteMessageBoardThread(messageBoardThreadId: $messageBoardThreadId)
	}
`;

export const getTags = (
	orderBy,
	page = 1,
	pageSize = 30,
	search = '',
	siteKey
) => {
	if (orderBy === 'latest-created') {
		return client
			.query({
				query: getTagsOrderByDateCreatedQuery,
				variables: {
					page,
					pageSize,
					search,
					siteKey,
				},
			})
			.then((result) => ({
				...result,
				data: result.data.keywords,
			}));
	}

	return client
		.query({
			query: getTagsOrderByNumberOfUsagesQuery,
			variables: {
				page,
				pageSize,
				search,
				siteKey,
			},
		})
		.then((result) => ({
			...result,
			data: result.data.keywordsRanked,
		}));
};

export const getTagsOrderByNumberOfUsagesQuery = gql`
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
				id
				keywordUsageCount
				name
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getTagsOrderByDateCreatedQuery = gql`
	query keywords(
		$page: Int!
		$pageSize: Int!
		$search: String
		$siteKey: String!
	) {
		keywords(
			page: $page
			pageSize: $pageSize
			search: $search
			siteKey: $siteKey
			sort: "dateCreated:desc"
		) {
			items {
				id
				dateCreated
				name
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getMessageQuery = gql`
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

export const getThreadQuery = gql`
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
			keywords
			messageBoardSection {
				numberOfMessageBoardSections
				parentMessageBoardSectionId
				title
			}
			myRating {
				ratingValue
			}
			seen
			subscribed
			viewCount
		}
	}
`;

export const getMessageBoardThreadByIdQuery = gql`
	query messageBoardThreads($filter: String!, $siteKey: String!) {
		messageBoardThreads(filter: $filter, flatten: true, siteKey: $siteKey) {
			items {
				friendlyUrlPath
				id
				messageBoardSection {
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
			}
		}
	}
`;

export const getThreadContentQuery = gql`
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

export const getMessagesQuery = gql`
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
				encodingFormat
				friendlyUrlPath
				id
				messageBoardMessages {
					items {
						actions
						articleBody
						creator {
							id
							image
							name
						}
						encodingFormat
						id
						showAsAnswer
					}
				}
				myRating {
					ratingValue
				}
				showAsAnswer
			}
			pageSize
			totalCount
		}
	}
`;

export const hasListPermissionsQuery = gql`
	query messageBoardThreads($siteKey: String!) {
		messageBoardThreads(siteKey: $siteKey) {
			actions
		}
	}
`;

export const getQuestionThreads = (
	creatorId = '',
	filter,
	keywords = '',
	page = 1,
	pageSize = 30,
	search = '',
	section,
	siteKey
) => {
	if (filter === 'latest-edited') {
		return getThreads(
			creatorId,
			keywords,
			page,
			pageSize,
			search,
			section,
			siteKey,
			'dateModified:desc'
		);
	}
	else if (filter === 'week') {
		const date = new Date();
		date.setDate(date.getDate() - 7);

		return getRankedThreads(date, page, pageSize, section);
	}
	else if (filter === 'month') {
		const date = new Date();
		date.setDate(date.getDate() - 31);

		return getRankedThreads(date, page, pageSize, section);
	}
	else if (filter === 'most-voted') {
		return getRankedThreads(null, page, pageSize, section);
	}

	return getThreads(
		creatorId,
		keywords,
		page,
		pageSize,
		search,
		section,
		siteKey
	);
};

export const getThreads = (
	creatorId = '',
	keywords = '',
	page = 1,
	pageSize = 30,
	search = '',
	section,
	siteKey,
	sort
) => {
	if (
		!search &&
		!keywords &&
		!creatorId &&
		!sort &&
		!section.messageBoardSections.items.length
	) {
		return client
			.query({
				context: {
					uri: '/o/graphql?restrictFields=actions',
				},
				query: getSectionThreadsQuery,
				variables: {
					messageBoardSectionId: section.id,
					page,
					pageSize,
				},
			})
			.then((result) => ({
				...result,
				data: result.data.messageBoardSectionMessageBoardThreads,
			}));
	}

	let filter = '';

	if (section && section.id) {
		filter = `(messageBoardSectionId eq ${section.id} `;

		for (let i = 0; i < section.messageBoardSections.items.length; i++) {
			filter += `or messageBoardSectionId eq ${section.messageBoardSections.items[i].id} `;
		}

		filter += ')';
	}

	if (keywords) {
		filter += `${
			(section && section.id && ' and ') || ''
		}keywords/any(x:x eq '${keywords}')`;
	}
	else if (creatorId) {
		filter += ` and creator/id eq ${creatorId}`;
	}

	sort = sort || 'dateCreated:desc';

	return client
		.query({
			context: {
				uri: '/o/graphql?restrictFields=actions',
			},
			query: getThreadsQuery,
			variables: {
				filter,
				page,
				pageSize,
				search,
				siteKey,
				sort,
			},
		})
		.then((result) => ({...result, data: result.data.messageBoardThreads}));
};

export const getSections = (sectionTitle, siteKey) =>
	client
		.query({
			query: getSectionQuery,
			variables: {
				filter: `title eq '${sectionTitle}' or id eq '${sectionTitle}'`,
				siteKey,
			},
		})
		.then(({data}) => data.messageBoardSections.items[0]);

export const getThreadsQuery = gql`
	query messageBoardThreads(
		$filter: String!
		$page: Int!
		$pageSize: Int!
		$search: String!
		$siteKey: String!
		$sort: String!
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
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				messageBoardSection {
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				seen
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getSectionThreadsQuery = gql`
	query messageBoardSectionMessageBoardThreads(
		$messageBoardSectionId: Long!
		$page: Int!
		$pageSize: Int!
	) {
		messageBoardSectionMessageBoardThreads(
			messageBoardSectionId: $messageBoardSectionId
			page: $page
			pageSize: $pageSize
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
				messageBoardSection {
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				seen
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getRankedThreads = (
	dateModified,
	page = 1,
	pageSize = 20,
	section,
	sort = ''
) => {
	return client
		.query({
			query: getRankedThreadsQuery,
			variables: {
				dateModified: dateModified && dateModified.toISOString(),
				messageBoardSectionId: section.id,
				page,
				pageSize,
				sort,
			},
		})
		.then((result) => ({
			...result,
			data: result.data.messageBoardThreadsRanked,
		}));
};

export const getRankedThreadsQuery = gql`
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
				messageBoardSection {
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				seen
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getRelatedThreadsQuery = gql`
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
				messageBoardSection {
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				seen
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getSectionQuery = gql`
	query messageBoardSections($filter: String!, $siteKey: String!) {
		messageBoardSections(
			filter: $filter
			flatten: true
			pageSize: 1
			siteKey: $siteKey
			sort: "title:desc"
		) {
			items {
				actions
				id
				messageBoardSections(sort: "title:asc") {
					items {
						id
						numberOfMessageBoardSections
						parentMessageBoardSectionId
						subscribed
						title
					}
				}
				numberOfMessageBoardSections
				parentMessageBoardSection {
					id
					messageBoardSections {
						items {
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

export const getSectionsByIdQuery = gql`
	query messageBoardSection($messageBoardSectionId: Long!) {
		messageBoardSection(messageBoardSectionId: $messageBoardSectionId) {
			actions
			id
			messageBoardSections(sort: "title:asc") {
				items {
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

export const getSectionsQuery = gql`
	query messageBoardSections($siteKey: String!) {
		messageBoardSections(siteKey: $siteKey, sort: "title:desc") {
			items {
				description
				id
				numberOfMessageBoardThreads
				parentMessageBoardSectionId
				subscribed
				title
			}
		}
	}
`;

export const getUserActivityQuery = gql`
	query messageBoardThreads(
		$filter: String
		$page: Int!
		$pageSize: Int!
		$siteKey: String!
	) {
		messageBoardThreads(
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
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				messageBoardSection {
					numberOfMessageBoardSections
					parentMessageBoardSectionId
					title
				}
				numberOfMessageBoardMessages
				taxonomyCategoryBriefs {
					taxonomyCategoryId
					taxonomyCategoryName
				}
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const markAsAnswerMessageBoardMessageQuery = gql`
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

export const updateMessageQuery = gql`
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

export const updateThreadQuery = gql`
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

export const subscribeQuery = gql`
	mutation updateMessageBoardThreadSubscribe($messageBoardThreadId: Long!) {
		updateMessageBoardThreadSubscribe(
			messageBoardThreadId: $messageBoardThreadId
		)
	}
`;

export const unsubscribeQuery = gql`
	mutation updateMessageBoardThreadUnsubscribe($messageBoardThreadId: Long!) {
		updateMessageBoardThreadUnsubscribe(
			messageBoardThreadId: $messageBoardThreadId
		)
	}
`;

export const subscribeSectionQuery = gql`
	mutation updateMessageBoardSectionSubscribe($messageBoardSectionId: Long!) {
		updateMessageBoardSectionSubscribe(
			messageBoardSectionId: $messageBoardSectionId
		)
	}
`;

export const unsubscribeSectionQuery = gql`
	mutation updateMessageBoardSectionUnsubscribe(
		$messageBoardSectionId: Long!
	) {
		updateMessageBoardSectionUnsubscribe(
			messageBoardSectionId: $messageBoardSectionId
		)
	}
`;

export const getSubscriptionsQuery = gql`
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
							numberOfMessageBoardSections
							parentMessageBoardSectionId
							title
						}
						myRating {
							ratingValue
						}
						subscribed
						viewCount
					}
				}
			}
		}
	}
`;

export const unsubscribeMyUserAccountQuery = gql`
	mutation deleteMyUserAccountSubscription($subscriptionId: Long!) {
		deleteMyUserAccountSubscription(subscriptionId: $subscriptionId)
	}
`;
