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

import {fetch} from 'frontend-js-web';

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'text/plain; charset=utf-8',
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

export const createAnswer = (articleBody, messageBoardThreadId) =>
	request(gql`
        mutation {
            createMessageBoardThreadMessageBoardMessage(messageBoardMessage: {articleBody: ${articleBody}, encodingFormat: "html", viewableBy: ANYONE}, messageBoardThreadId: ${messageBoardThreadId}){
                viewableBy
            }
        }`);

export const createComment = (articleBody, messageBoardMessageId) =>
	request(gql`
        mutation {
            createMessageBoardMessageMessageBoardMessage(messageBoardMessage: {articleBody: ${articleBody}, encodingFormat: "html", viewableBy: ANYONE}, parentMessageBoardMessageId: ${messageBoardMessageId}){
            	actions
                articleBody
                creator {
                	name
                }
                id
            }
        }`);

export const createQuestion = (
	articleBody,
	headline,
	messageBoardSectionId,
	tags
) =>
	request(gql`
        mutation {
            createMessageBoardSectionMessageBoardThread(messageBoardSectionId: ${messageBoardSectionId}, messageBoardThread: {articleBody: ${articleBody}, encodingFormat: "html", headline: ${headline}, showAsQuestion: true, keywords: ${tags}, viewableBy: ANYONE}){
                articleBody
                headline
                keywords
                showAsQuestion
            }
        }`);

export const createVoteMessage = (id, rating) =>
	request(gql`
        mutation {
            createMessageBoardMessageMyRating(messageBoardMessageId: ${id}, rating: {ratingValue: ${rating}}){
                id
                ratingValue
          }
        }`);

export const createVoteThread = (id, rating) =>
	request(gql`
        mutation {
            createMessageBoardThreadMyRating(messageBoardThreadId: ${id}, rating: {ratingValue: ${rating}}){
                id
                ratingValue
          }
        }`);

export const deleteMessage = (messageBoardMessage) =>
	request(gql`
        mutation {
            deleteMessageBoardMessage(messageBoardMessageId: ${messageBoardMessage.id})
        }`).then((data) => {
		if (messageBoardMessage.messageBoardMessages) {
			return Promise.all(
				messageBoardMessage.messageBoardMessages.items.map((x) =>
					deleteMessage(x)
				)
			);
		}

		return data;
	});

export const deleteMessageBoardThread = (messageBoardThreadId) =>
	request(gql`
		mutation {
			deleteMessageBoardThread(messageBoardThreadId: ${messageBoardThreadId})
		}
	`);

export const getTags = (page = 1, pageSize = 20, siteKey, search = '') =>
	request(gql`
        query {
            keywordsRanked(page: ${page}, pageSize: ${pageSize}, siteKey: ${siteKey}, search: ${search}){
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
        }`);

export const getMessage = (friendlyUrlPath, siteKey) =>
	request(gql`
        query {
            messageBoardMessageByFriendlyUrlPath(friendlyUrlPath: ${friendlyUrlPath}, siteKey: ${siteKey}){
                articleBody
                headline
                id
            }
        }`);

export const getThread = (friendlyUrlPath, siteKey) =>
	request(
		gql`
        query {
            messageBoardThreadByFriendlyUrlPath(friendlyUrlPath: ${friendlyUrlPath}, siteKey: ${siteKey}){
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
        }`,
		{nestedFields: 'lastPostDate'}
	);

export const getMessageBoardThreadById = (messageBoardThreadId) =>
	request(
		gql`
        query {
            messageBoardThread(messageBoardThreadId: ${messageBoardThreadId}){
                friendlyUrlPath
				id
                messageBoardSection {
                	title
                }
            }
        }`
	);

export const getThreadContent = (friendlyUrlPath, siteKey) =>
	request(gql`
        query {
            messageBoardThreadByFriendlyUrlPath(friendlyUrlPath: ${friendlyUrlPath}, siteKey: ${siteKey}){
                articleBody
                headline
                id
				keywords
            }
        }`);

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

export const hasListPermissions = (permission, siteKey) =>
	request(gql`
			query {
				messageBoardThreads(siteKey: ${siteKey}) {
					actions
				}
			}`).then((data) => Boolean(data.actions[permission]));

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

export const getSection = (title, siteKey) => {
	const filter = `title eq '${title}' or id eq '${title}'`;

	return request(gql`
		query {
			messageBoardSections(filter: ${filter}, flatten:true, pageSize: 1, siteKey: ${siteKey}, sort: "title:desc") {
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
		}
	`).then((data) => data.items[0]);
};

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

export const getRelatedThreads = (search = '', siteKey) =>
	request(gql`
        query {
            messageBoardThreads(page: 1, pageSize: 4, flatten: true, search: ${search}, siteKey: ${siteKey}){
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
        }`);

export const getSections = (siteKey) =>
	request(gql`
		query {
			messageBoardSections(siteKey: ${siteKey}, sort: "title:desc") {
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
`);

export const getUserActivity = (page = 1, pageSize, siteKey, userId = '') => {
	const filter = `creatorId eq ${userId}`;

	return request(gql`
		query {
			messageBoardThreads(filter: ${filter}, flatten: true, page: ${page}, pageSize: ${pageSize}, siteKey: ${siteKey}, sort: "dateCreated:desc") {
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
					taxonomyCategoryBriefs{
						taxonomyCategoryId
						taxonomyCategoryName
					}
				}
			page
			pageSize
			totalCount
			}
		}`);
};

export const markAsAnswerMessageBoardMessage = (
	messageBoardMessageId,
	showAsAnswer
) =>
	request(gql`
        mutation {
            patchMessageBoardMessage(messageBoardMessage: {showAsAnswer: ${showAsAnswer}}, messageBoardMessageId: ${messageBoardMessageId}){
                id
            }
        }`);

export const updateMessage = (articleBody, messageBoardMessageId) =>
	request(gql`
        mutation {
            patchMessageBoardMessage(messageBoardMessage: {articleBody: ${articleBody},  encodingFormat: "html"}, messageBoardMessageId: ${messageBoardMessageId}){
                articleBody
            }
        }`);

export const updateThread = (
	articleBody,
	headline,
	messageBoardThreadId,
	tags
) =>
	request(gql`
        mutation {
            patchMessageBoardThread(messageBoardThread: {articleBody: ${articleBody}, encodingFormat: "html", headline: ${headline}, keywords: ${tags}}, messageBoardThreadId: ${messageBoardThreadId}){
                articleBody
                headline
				keywords
            }
        }`);

export const subscribe = (messageBoardThreadId) =>
	request(gql`
        mutation {
            updateMessageBoardThreadSubscribe(messageBoardThreadId: ${messageBoardThreadId})
        }
    `);

export const unsubscribe = (messageBoardThreadId) =>
	request(gql`
        mutation {
            updateMessageBoardThreadUnsubscribe(messageBoardThreadId: ${messageBoardThreadId})
        }
    `);

export const subscribeSection = (messageBoardSectionId) =>
	request(gql`
        mutation {
            updateMessageBoardSectionSubscribe(messageBoardSectionId: ${messageBoardSectionId})
        }
    `);

export const unsubscribeSection = (messageBoardSectionId) =>
	request(gql`
        mutation {
            updateMessageBoardSectionUnsubscribe(messageBoardSectionId: ${messageBoardSectionId})
        }
    `);
