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

export const request = query =>
	fetch(getURL(), {
		body: `{"query": "${query}"}`,
		headers: HEADERS,
		method: 'POST',
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
		...params,
	};

	const uri = new URL(`${window.location.origin}/o/graphql`);
	const keys = Object.keys(params);

	keys.forEach(key => uri.searchParams.set(key, params[key]));

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
            createMessageBoardSectionMessageBoardThread(messageBoardSectionId: ${messageBoardSectionId}, messageBoardThread: {articleBody: ${articleBody}, encodingFormat: "html", headline: ${headline}, showAsQuestion: true, taxonomyCategoryIds: ${tags}, viewableBy: ANYONE}){
                articleBody
                headline
                taxonomyCategoryBriefs {
                	taxonomyCategoryId
					taxonomyCategoryName 
                }
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

export const getTags = (page = 1, siteKey) =>
	request(gql`
        query {
            taxonomyCategoryRanked(page: ${page}, pageSize: 20, siteKey: ${siteKey}){
                items {
                    name
                    dateCreated
                    id
                    taxonomyCategoryUsageCount
                }
                lastPage
                page
                pageSize
                totalCount
            }
        }`);

export const getAllTags = siteKey =>
	request(gql`   
		query {
			taxonomyVocabularies(siteKey: ${siteKey}){
				items {
					taxonomyCategories {
						items {
							id
							name
						}
					}
				}
			}
		}`);

export const getMessage = (friendlyUrlPath, siteKey) =>
	request(gql`
        query {
            messageBoardMessageByFriendlyUrlPath(friendlyUrlPath: ${friendlyUrlPath}, siteKey: ${siteKey}){
                articleBody 
                headline
                id 
				taxonomyCategoryBriefs {
                	taxonomyCategoryId
					taxonomyCategoryName 
                } 
            }
        }`);

export const getThread = (
	friendlyUrlPath,
	siteKey,
	page = 1,
	sort = 'showAsAnswer:desc,dateModified:desc'
) =>
	request(gql`
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
                messageBoardSection {
                	title
                }
                myRating {
                    ratingValue
                }
                subscribed
				taxonomyCategoryBriefs {
                	taxonomyCategoryId
					taxonomyCategoryName 
                }
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
        }`).then(x => x.items);

export const getThreadContent = (friendlyUrlPath, siteKey) =>
	request(gql`
        query {
            messageBoardThreadByFriendlyUrlPath(friendlyUrlPath: ${friendlyUrlPath}, siteKey: ${siteKey}){
                articleBody 
                headline
                id 
				taxonomyCategoryBriefs {
                	taxonomyCategoryId
					taxonomyCategoryName 
                } 
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
	page = 1,
	pageSize = 30,
	search = '',
	sectionId,
	sort = 'dateCreated:desc',
	taxonomyCategoryId = '',
}) => {
	let filter = '';

	if (taxonomyCategoryId) {
		filter = `taxonomyCategoryId/any(x:x eq ${taxonomyCategoryId})`;
	}
	else if (creatorId) {
		filter = `creator/id eq ${creatorId}`;
	}

	return request(gql`
        query {
			messageBoardSectionMessageBoardThreads(filter: ${filter}, messageBoardSectionId: ${sectionId}, page: ${page}, pageSize: ${pageSize}, search: ${search}, sort: ${sort}){
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
					headline
					id 
					messageBoardMessages {
						items {
							showAsAnswer
						}
					}
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
};

export const getSection = (title, siteKey) => {
	const filter = `title eq '${title}' or id eq '${title}'`;

	return request(gql`
		query {
			messageBoardSections(filter: ${filter}, flatten:true, pageSize: 1, siteKey: ${siteKey}, sort: "title:desc") {
				items {
					id
					messageBoardSections(sort: "title:asc") {
						items {
							id
							parentMessageBoardSectionId
							subscribed
							title
						}
					}
					parentMessageBoardSectionId
					subscribed
					title
				}
			}
		}
	`).then(data => data.items[0]);
};

export const getRankedThreads = (
	dateModified,
	page = 1,
	pageSize = 20,
	sectionId,
	sort = ''
) =>
	request(gql`
        query {
			messageBoardThreadsRanked(dateModified: ${dateModified.toISOString()}, messageBoardSectionId: ${sectionId}, page: ${page}, pageSize: ${pageSize}, sort: ${sort}){
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
					messageBoardMessages {
						items {
							showAsAnswer
						}
					}
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

export const getSections = siteKey =>
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
	taxonomyCategoryIds
) =>
	request(gql`
        mutation {
            patchMessageBoardThread(messageBoardThread: {articleBody: ${articleBody}, encodingFormat: "html", headline: ${headline}, taxonomyCategoryIds: ${taxonomyCategoryIds}}, messageBoardThreadId: ${messageBoardThreadId}){
                articleBody
                headline
				taxonomyCategoryBriefs {
					taxonomyCategoryId
					taxonomyCategoryName
				} 
            }
        }`);

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

export const subscribeSection = messageBoardSectionId =>
	request(gql`
        mutation {
            updateMessageBoardSectionSubscribe(messageBoardSectionId: ${messageBoardSectionId})
        }
    `);

export const unsubscribeSection = messageBoardSectionId =>
	request(gql`
        mutation {
            updateMessageBoardSectionUnsubscribe(messageBoardSectionId: ${messageBoardSectionId})
        }
    `);
