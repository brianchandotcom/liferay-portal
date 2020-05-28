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

import {ApolloClient, HttpLink, InMemoryCache, gql as gq} from '@apollo/client';
import {fetch} from 'frontend-js-web';

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'text/plain; charset=utf-8',
};

export const createClient = () => {
	const client = new ApolloClient({
		cache: new InMemoryCache(),
		link: new HttpLink({
			fetch,
			headers: HEADERS,
			uri: '/o/graphql',
		}),
	});

	client.defaultOptions = {
		query: {
			errorPolicy: 'all',
		},
	};

	return client;
};

function escape(x) {
	return (
		x !== undefined &&
		JSON.stringify(x)
			.replace(/[\\]/g, '\\\\')
			.replace(/[/]/g, '\\/')
			.replace(/[\b]/g, '\\b')
			.replace(/[\f]/g, '\\f')
			.replace(/[\n]/g, '\\n')
			.replace(/[\r]/g, '\\r')
			.replace(/[\t]/g, '\\t')
	);
}

function gql(strings, ...values) {
	return strings
		.map((string, i) => string + (escape(values[i]) || ''))
		.join('')
		.replace(/\s+/g, ' ')
		.replace(/"/g, '\\"');
}

export const request = (query, params = {}) =>
	fetch(getURL(params), {
		body: `{"query": "${query}"}`,
		headers: HEADERS,
		method: 'POST',
	})
		.then((response) => response.json())
		.then((json) => {
			const data = json.data;

			if (!data) {
				return Promise.reject(json.errors);
			}

			return data[Object.keys(data)[0]];
		});

export const getURL = (params) => {
	params = {
		t: Date.now(),
		...params,
	};

	const uri = new URL(`${window.location.origin}/o/graphql`);
	const keys = Object.keys(params);

	keys.forEach((key) => uri.searchParams.set(key, params[key]));

	return uri.toString();
};

export const createAnswerQuery = gq`mutation createMessageBoardThreadMessageBoardMessage($articleBody: String!, $messageBoardThreadId: Long!) {
	createMessageBoardThreadMessageBoardMessage(messageBoardMessage: {articleBody: $articleBody, encodingFormat: "html", viewableBy: ANYONE}, messageBoardThreadId: $messageBoardThreadId) {
		viewableBy
	}
}`;

export const createCommentQuery = gq`mutation createMessageBoardMessageMessageBoardMessage($articleBody: String!, $parentMessageBoardMessageId: Long!) {
	createMessageBoardMessageMessageBoardMessage(messageBoardMessage: {articleBody: $articleBody, encodingFormat: "html", viewableBy: ANYONE}, parentMessageBoardMessageId: $parentMessageBoardMessageId) {
		actions
		articleBody
		creator {
			name
		}
		id
	}
}`;

export const createQuestionQuery = gq`mutation createMessageBoardSectionMessageBoardThread($messageBoardSectionId: Long!, $articleBody: String!, $headline: String!, $keywords: [String]) {
	createMessageBoardSectionMessageBoardThread(messageBoardSectionId: $messageBoardSectionId, messageBoardThread: {articleBody: $articleBody, encodingFormat: "html", headline: $headline, showAsQuestion: true, keywords: $keywords, viewableBy: ANYONE}) {
		articleBody
		headline
		keywords
		showAsQuestion
	}
}`;

export const createVoteMessageQuery = gq`mutation createMessageBoardMessageMyRating($messageBoardMessageId: Long!, $ratingValue: Float!) {
	createMessageBoardMessageMyRating(messageBoardMessageId: $messageBoardMessageId, rating: {ratingValue: $ratingValue}) {
		id
		ratingValue
  	}
}`;

export const createVoteThreadQuery = gq`mutation createMessageBoardThreadMyRating($messageBoardThreadId: Long!, $ratingValue: Float!) {
	createMessageBoardThreadMyRating(messageBoardThreadId: $messageBoardThreadId, rating: {ratingValue: $ratingValue}) {
		id
		ratingValue
  	}
}`;

export const deleteMessageQuery = gq`mutation deleteMessageBoardMessage($messageBoardMessageId: Long!) {
	deleteMessageBoardMessage(messageBoardMessageId: $messageBoardMessageId)
}`;

export const deleteMessageBoardThreadQuery = gq`mutation deleteMessageBoardThread($messageBoardThreadId: Long!) {
	deleteMessageBoardThread(messageBoardThreadId: $messageBoardThreadId)
}`;

export const getTags = gq`query keywordsRanked($page: Int!, $pageSize: Int!, $search: String, $siteKey: String!) {
	keywordsRanked(page: $page, pageSize: $pageSize, search: $search, siteKey: $siteKey){
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
}`;

export const getMessageQuery = gq`query messageBoardMessageByFriendlyUrlPath($friendlyUrlPath: String!, $siteKey: String!) {
	messageBoardMessageByFriendlyUrlPath(friendlyUrlPath: $friendlyUrlPath, siteKey: $siteKey) {
		articleBody
		headline
		id
	}
}`;

export const getThreadQuery = gq`query messageBoardThreadByFriendlyUrlPath($friendlyUrlPath: String!, $siteKey: String!) {
	messageBoardThreadByFriendlyUrlPath(friendlyUrlPath: $friendlyUrlPath, siteKey: $siteKey) {
		actions
		aggregateRating {
			ratingAverage
			ratingCount
			ratingValue
		}
		articleBody 
		creator {
			id
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
			title
		}
		myRating {
			ratingValue
		}
		subscribed
		viewCount
	}
}`;

export const getMessageBoardThreadByIdQuery = gq`query messageBoardThread($messageBoardThreadId: Long!){
	messageBoardThread(messageBoardThreadId: $messageBoardThreadId){
		friendlyUrlPath
		id
		messageBoardSection {
			title
		}
	}
}`;

export const getThreadContentQuery = gq`query messageBoardThreadByFriendlyUrlPath($friendlyUrlPath: String!, $siteKey: String!){
	messageBoardThreadByFriendlyUrlPath(friendlyUrlPath: $friendlyUrlPath, siteKey: $siteKey){
		articleBody
		headline
		id
		keywords
	}
}`;

export const getMessages = (
	parentMessageBoardMessageId,
	page = 1,
	pageSize = 20,
	sort
) => {
	if (sort === 'votes') {
		sort = 'dateModified:desc';
		page = 1;
		pageSize = 100;
	}
	else if (sort === 'active') {
		sort = 'showAsAnswer:desc,dateModified:desc';
	}
	else {
		sort = 'showAsAnswer:desc,dateCreated:desc';
	}

	return request(
		gql`
        query {
              messageBoardThreadMessageBoardMessages(messageBoardThreadId: ${parentMessageBoardMessageId}, page: ${page}, pageSize: ${pageSize}, sort: ${sort}){
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
        }`,
		{nestedFields: 'lastPostDate'}
	).then((answers) => {
		if (pageSize !== 100) {
			return answers;
		}

		return {
			...answers,
			items: answers.items.sort((answer1, answer2) => {
				if (answer2.showAsAnswer) {
					return 1;
				}
				if (answer1.showAsAnswer) {
					return -1;
				}

				const ratingValue1 =
					(answer1.aggregateRating &&
						answer1.aggregateRating.ratingValue) ||
					0;
				const ratingValue2 =
					(answer2.aggregateRating &&
						answer2.aggregateRating.ratingValue) ||
					0;

				return ratingValue2 - ratingValue1;
			}),
		};
	});
};

export const hasListPermissionsQuery = gq`query messageBoardThreads($siteKey: String!) {
	messageBoardThreads(siteKey: $siteKey) {
		actions
	}
}`;

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

	return getThreads(
		creatorId,
		keywords,
		page,
		pageSize,
		search,
		section,
		siteKey,
		'dateCreated:desc'
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
	sort = 'dateCreated:desc'
) => {
	let filter = `(messageBoardSectionId eq ${section.id} `;

	for (let i = 0; i < section.messageBoardSections.items.length; i++) {
		filter += `or messageBoardSectionId eq ${section.messageBoardSections.items[i].id} `;
	}

	filter += ')';

	if (keywords) {
		filter = `keywords/any(x:x eq '${keywords}')`;
	}
	else if (creatorId) {
		filter = `creator/id eq ${creatorId}`;
	}

	return request(gql`
        query {
			messageBoardThreads(filter: ${filter}, flatten:true, page: ${page}, pageSize: ${pageSize}, search: ${search}, siteKey: ${siteKey}, sort: ${sort}){
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
					messageBoardSection {
						title
					}
					numberOfMessageBoardMessages
					keywords
					viewCount
				}
				page
				pageSize
				totalCount
			}
        }`);
};

export const getSectionQuery = gq`query messageBoardSections($filter: String!, $siteKey: String!) {
	messageBoardSections(filter: $filter, flatten:true, pageSize: 1, siteKey: $siteKey, sort: "title:desc") {
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
			parentMessageBoardSectionId
			subscribed
			title
		}
	}
}`;

export const getRankedThreads = (
	dateModified,
	page = 1,
	pageSize = 20,
	section,
	sort = ''
) =>
	request(gql`
        query {
			messageBoardThreadsRanked(dateModified: ${dateModified.toISOString()}, messageBoardSectionId: ${
		section.id
	}, page: ${page}, pageSize: ${pageSize}, sort: ${sort}){
				items {
					aggregateRating {
						ratingAverage
						ratingCount
						ratingValue
					} 
					articleBody
					creator {
						id
						name
					} 
					dateModified
					hasValidAnswer
					headline
					id  
					messageBoardSection {
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
		}`);

export const getRelatedThreadsQuery = gq`query messageBoardThreads($search: String!, $siteKey: String!) {
	messageBoardThreads(page: 1, pageSize: 4, flatten: true, search: $search, siteKey: $siteKey) {
		items {
			aggregateRating {
				ratingAverage
				ratingCount
				ratingValue
			}
			creator {
				id
				name
			} 
			dateModified
			friendlyUrlPath
			headline
			id 
			messageBoardSection {
				title
			}
		} 
		page 
		pageSize 
		totalCount
	}
}`;

export const getSectionsQuery = gq`query messageBoardSections($siteKey: String!) {
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
}`;

export const getUserActivity = gq`query messageBoardThreads($filter: String, $page: Int!, $pageSize: Int!, $siteKey: String!) {
	messageBoardThreads(filter: $filter, flatten: true, page: $page, pageSize: $pageSize, siteKey: $siteKey, sort: "dateCreated:desc") {
		items {
			aggregateRating {
				ratingAverage
				ratingCount
				ratingValue
			}
			articleBody
			creator {
				id
				name
				image
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
				title
			}
			messageBoardSection {
				title
			}
			numberOfMessageBoardMessages
			taxonomyCategoryBriefs {
				taxonomyCategoryId
				taxonomyCategoryName
			}
		}
		page
		pageSize
		totalCount
	}
}`;

export const markAsAnswerMessageBoardMessageQuery = gq`mutation patchMessageBoardMessage($messageBoardMessageId: Long!, $showAsAnswer: Boolean!) {
	patchMessageBoardMessage(messageBoardMessage: {showAsAnswer: $showAsAnswer}, messageBoardMessageId: $messageBoardMessageId){
		id
		showAsAnswer
	}
}`;

export const updateMessageQuery = gq`mutation patchMessageBoardMessage($articleBody: String!, $messageBoardMessageId: Long!) {
	patchMessageBoardMessage(messageBoardMessage: {articleBody: $articleBody, encodingFormat: "html"}, messageBoardMessageId: $messageBoardMessageId) {
		articleBody
	}
}`;

export const updateThreadQuery = gq`mutation patchMessageBoardThread($articleBody: String!, $headline: String!, $keywords: [String], $messageBoardThreadId: Long!) {
	patchMessageBoardThread(messageBoardThread: {articleBody: $articleBody, encodingFormat: "html", headline: $headline, keywords: $keywords}, messageBoardThreadId: $messageBoardThreadId) {
		articleBody
		headline
		keywords
	}
}`;

export const subscribeQuery = gq`mutation updateMessageBoardThreadSubscribe($messageBoardThreadId: Long!) {
	updateMessageBoardThreadSubscribe(messageBoardThreadId: $messageBoardThreadId)
}`;

export const unsubscribeQuery = gq`mutation updateMessageBoardThreadUnsubscribe($messageBoardThreadId: Long!) {
	updateMessageBoardThreadUnsubscribe(messageBoardThreadId: $messageBoardThreadId)
}`;

export const subscribeSectionQuery = gq`mutation updateMessageBoardSectionSubscribe($messageBoardSectionId: Long!) {
	updateMessageBoardSectionSubscribe(messageBoardSectionId: $messageBoardSectionId)
}`;

export const unsubscribeSectionQuery = gq`mutation updateMessageBoardSectionUnsubscribe($messageBoardSectionId: Long!) {
	updateMessageBoardSectionUnsubscribe(messageBoardSectionId: $messageBoardSectionId)
}`;
