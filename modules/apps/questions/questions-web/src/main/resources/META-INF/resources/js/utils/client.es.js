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
	'Content-Type': 'text/plain; charset=utf-8'
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

export const request = query =>
	fetch(getURL(), {
		body: `{"query": "${query}"}`,
		headers: HEADERS,
		method: 'POST'
	})
		.then(response => response.json())
		.then(json => {
			const data = json.data;

			if (!data) {
				return Promise.reject(json.errors);
			}

			return data[Object.keys(data)[0]];
		});

export const getURL = params => {
	params = {
		['p_auth']: Liferay.authToken,
		t: Date.now(),
		...params
	};

	const uri = new URL(`${window.location.origin}/o/graphql`);
	const keys = Object.keys(params);

	keys.forEach(key => uri.searchParams.set(key, params[key]));

	return uri.toString();
};

export const createAnswer = (articleBody, messageBoardThreadId) =>
	request(gql`
        mutation {
            createMessageBoardThreadMessageBoardMessage(messageBoardMessage: {articleBody: ${articleBody}, viewableBy: ANYONE}, messageBoardThreadId: ${messageBoardThreadId}){
                viewableBy
            }
        }`);

export const createComment = (articleBody, messageBoardMessageId) =>
	request(gql`
        mutation {
            createMessageBoardMessageMessageBoardMessage(messageBoardMessage: {articleBody: ${articleBody}, viewableBy: ANYONE}, parentMessageBoardMessageId: ${messageBoardMessageId}){
                articleBody
                creator {
                	name
                }
                id
            }
        }`);

export const createQuestion = (articleBody, headline, keywords, siteKey) => {
	keywords = keywords.length ? keywords.split(',').filter(x => x) : null;

	return request(gql`
        mutation {
            createSiteMessageBoardThread(messageBoardThread: {articleBody: ${articleBody}, headline: ${headline}, keywords: ${keywords}, showAsQuestion: true, viewableBy: ANYONE}, siteKey: ${siteKey}){
                articleBody
                headline
                keywords
                showAsQuestion
            }
        }`);
};

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

export const deleteMessage = messageBoardMessage =>
	request(gql`
        mutation {
            deleteMessageBoardMessage(messageBoardMessageId: ${messageBoardMessage.id})
        }`).then(data => {
		if (messageBoardMessage.messageBoardMessages) {
			return Promise.all(
				messageBoardMessage.messageBoardMessages.items.map(x =>
					deleteMessage(x)
				)
			);
		}
		return data;
	});

export const getKeywords = (page = 1, siteKey) =>
	request(gql`
        query {
            keywords(page: ${page}, pageSize: 20, siteKey: ${siteKey} sort: "dateModified:desc"){
                items {
                    name
                    dateCreated
                    id
                    keywordUsageCount
                }
                lastPage
                page
                pageSize
                totalCount
            }
        }`);

export const getMessage = messageBoardMessageId =>
	request(gql`
        query {
            messageBoardMessage(messageBoardMessageId: ${messageBoardMessageId}){
                articleBody 
                headline
                id 
                keywords 
            }
        }`);

export const getThread = (
	messageBoardThreadId,
	page = 1,
	sort = 'showAsAnswer:desc,dateModified:desc'
) =>
	request(gql`
        query {
            messageBoardThread(messageBoardThreadId: ${messageBoardThreadId}){
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
                dateCreated
                dateModified
                headline
                id 
                keywords 
                messageBoardMessages(page: ${page}, pageSize: 20, sort: ${sort}) {
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
                        id
                        messageBoardMessages {
                            items {
                            	actions
                                articleBody
                                creator {
                                    id
                                    name
                                }
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
                myRating {
                    ratingValue
                }
                subscribed
                viewCount
            }
        }`);

export const getMessages = (
	parentMessageBoardMessageId,
	sort = '',
	page = 1,
	pageSize = 20
) =>
	request(gql`
        query {
              messageBoardThreadMessageBoardMessages(messageBoardThreadId: ${parentMessageBoardMessageId}, page: ${page}, pageSize: ${pageSize}, sort: ${'showAsAnswer:desc,' +
		sort}){
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
                    id
                    messageBoardMessages {
                        items {
                            articleBody
                            creator {
                                id
                                name
                            }
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
        }`).then(x => x.items);

export const getThreadContent = messageBoardThreadId =>
	request(gql`
        query {
            messageBoardThread(messageBoardThreadId: ${messageBoardThreadId}){
                articleBody 
                headline
                id 
                keywords 
            }
        }`);

export const hasListPermissions = (permission, siteKey) =>
	request(gql`
			query {
				messageBoardThreads(siteKey: ${siteKey}) {
					actions
				}
			}`).then(data => Boolean(data.actions[permission]));

export const getThreads = ({
	creatorId = '',
	keyword = '',
	page = 1,
	pageSize = 30,
	search = '',
	siteKey,
	sort = 'dateModified:desc'
}) => {
	let filter = '';
	if (keyword) {
		filter = `keywords/any(x:x eq '${keyword}')`;
	}
	else if (creatorId) {
		filter = `creator/id eq ${creatorId}`;
	}

	return request(gql`
        query {
            messageBoardThreads(filter: ${filter}, page: ${page}, pageSize: ${pageSize}, search: ${search}, siteKey: ${siteKey}, sort: ${sort}){
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
                    headline
                    id 
                    keywords 
                    messageBoardMessages {
                        items {
                            showAsAnswer
                        }
                    }
                    viewCount
                } 
                page 
                pageSize 
                totalCount
            }
        }`);
};

export const getRankedThreads = (dateModified, page = 1, pageSize = 30, sort) =>
	request(gql`
        query {
          messageBoardThreadsRanked(dateModified: ${dateModified.toISOString()}, page: ${page}, pageSize: ${pageSize}, sort: ${sort}){
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
                headline
                id 
                keywords 
                messageBoardMessages {
                    items {
                        showAsAnswer
                    }
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
            messageBoardThreads(page: 1, pageSize: 10, flatten: true, search: ${search}, siteKey: ${siteKey}){
                items {
                    aggregateRating {
                        ratingAverage
                        ratingCount
                        ratingValue
                    }
                    headline
                    id 
                } 
                page 
                pageSize 
                totalCount
            }
        }`);

export const getUserAccount = userAccountId =>
	request(gql`
        query {
            userAccount(userAccountId: ${userAccountId}) {
                emailAddress
                id
                name
            }
        }`);

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
            patchMessageBoardMessage(messageBoardMessage: {articleBody: ${articleBody}}, messageBoardMessageId: ${messageBoardMessageId}){
                articleBody
            }
        }`);

export const updateThread = (
	articleBody,
	headline,
	keywords,
	messageBoardThreadId
) => {
	keywords = keywords.length ? keywords.split(',').filter(x => x) : null;

	return request(gql`
        mutation {
            patchMessageBoardThread(messageBoardThread: {articleBody: ${articleBody}, headline: ${headline}, keywords: ${keywords}}, messageBoardThreadId: ${messageBoardThreadId}){
                articleBody
                headline
                keywords
            }
        }`);
};

export const getMyUserAccount = () =>
	request(gql`
		query {
			myUserAccount {
				id
				name
				roleBriefs {
					name
				}
			}
		}
	`);

export const subscribe = messageBoardThreadId =>
	request(gql`
        mutation {
            updateMessageBoardThreadSubscribe(messageBoardThreadId: ${messageBoardThreadId})
        }
    `);

export const unsubscribe = messageBoardThreadId =>
	request(gql`
        mutation {
            updateMessageBoardThreadUnsubscribe(messageBoardThreadId: ${messageBoardThreadId})
        }
    `);
