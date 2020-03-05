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

import React, { useContext, useState, useEffect, useCallback } from 'react';
import {withRouter} from 'react-router-dom';

import ClayButton from '@clayui/button';

import {AppContext} from '../../AppContext.es';
import { getUserActivity } from '../../utils/client.es';
import UserIcon from '../../components/UserIcon.es';

import {ClayPaginationWithBasicItems} from '@clayui/pagination';

import {Link} from 'react-router-dom';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import Error from '../../components/Error.es';
import QuestionBadge from '../../components/QuestionsBadge.es';
import SectionLabel from '../../components/SectionLabel.es';
import TagList from '../../components/TagList.es';
import {getRankedThreads, getThreads} from '../../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../../utils/utils.es';

export default withRouter( () => {
    const [error, setError] = useState({});
	const [loading, setLoading] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize] = useState(20);
    const [questions, setQuestions] = useState([]);
    const sectionId = 36526;
    const sectionTitle= "test";

	useEffect(() => {
        renderQuestions(loadThreads());
	}, [
		creatorId,
		page,
        pageSize,
        loadThreads,
        sectionId
	]);

	const renderQuestions = questions => {
		questions
			.then(data => setQuestions(data || []))
			.then(() => setLoading(false))
			.catch(error => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
				setLoading(false);
				setError({message: 'Loading Questions', title: 'Error'});
			});
	};

	const loadThreads = useCallback(
		sort =>
			getThreads({
				creatorId,
				page,
				pageSize,
                sort,
                sectionId
			}),
		[creatorId, page, pageSize, sectionId]
	);

	const hasValidAnswer = question =>
		question.messageBoardMessages.items.filter(
			message => message.showAsAnswer
		).length > 0;

	const filterChange = type => {
		if (type === 'latest-edited') {
			renderQuestions(loadThreads('dateModified:desc'));
		}
		else if (type === 'week') {
			const date = new Date();
			date.setDate(date.getDate() - 7);

			renderQuestions(getRankedThreads(date, page, pageSize, sectionId));
		}
		else if (type === 'month') {
			const date = new Date();
			date.setDate(date.getDate() - 31);

			renderQuestions(getRankedThreads(date, page, pageSize, sectionId));
		}
		else {
			renderQuestions(loadThreads('dateCreated:desc'));
		}
	};

    // MARK: Mine -- Page Header
    const context = useContext(AppContext);
    const userId = context.userId;
    const creatorId = userId;
    const [name, setName] = useState('');
    const [image, setImage] = useState('');
    const [postsNumber, setPostsNumber] = useState(0);
    const [rank, setRank] = useState('Not specified');
    // const [questions, setQuestions] = useState([]);


    useEffect(() => {
        getUserActivity(context.siteKey,userId).then(data => {
            const userInfo = data.items[0].creator;
            const userStatistics = data.items[0].creatorStatistics;
            setName(userInfo.name);
            setImage(userInfo.image);
            setPostsNumber(userStatistics.postsNumber);
            setRank(userStatistics.rank);
        });
    }, []);

    return(
        <>
            <PageHeader />
            LATEST QUESTION ASKED
            <Questions />
        </>
    );

    function PageHeader() {
        return(
            <div className="d-flex flex-row justify-content-between">
                <div className="d-flex">
                    <UserIcon
                        fullName={name}
                        portraitURL={image}
                        userId={String(userId)}
                    />
                    <div className="flex-column c-ml-3">
                        <div>
                            <span class="h3">Rank: {rank}</span>
                        </div>
                        <div>
                            <span class="h3">{name}</span>
                        </div>
                        <div>
                            <span class="h3">Posts: {postsNumber}</span>
                        </div>
                    </div>
                </div>
                <div>
                    <ClayButton displayType="secondary">
                        Manage Subscriptions
                    </ClayButton>
                </div>
            </div>
        );
    }

    function Questions() {
        return(
            <>
                {loading ? (
                    <ClayLoadingIndicator />
                ) : (
                    questions.items &&
                    questions.items.map(question => (
                        <div
                            className="c-mt-4 c-p-3 position-relative question-row text-secondary"
                            key={question.id}
                        >
                            <div className="align-items-center d-flex flex-wrap justify-content-between">
                                <SectionLabel
                                    section={question.messageBoardSection}
                                />

                                <ul className="c-mb-0 d-flex flex-wrap list-unstyled stretched-link-layer">
                                    <li>
                                        <QuestionBadge
                                            symbol={
                                                normalizeRating(
                                                    question.aggregateRating
                                                ) < 0
                                                    ? 'caret-bottom'
                                                    : 'caret-top'
                                            }
                                            value={normalizeRating(
                                                question.aggregateRating
                                            )}
                                        />
                                    </li>

                                    <li>
                                        <QuestionBadge
                                            symbol="view"
                                            value={question.viewCount}
                                        />
                                    </li>

                                    <li>
                                        <QuestionBadge
                                            className={
                                                hasValidAnswer(question)
                                                    ? 'alert-success border-0'
                                                    : ''
                                            }
                                            symbol={
                                                hasValidAnswer(question)
                                                    ? 'check-circle-full'
                                                    : 'message'
                                            }
                                            value={
                                                question.messageBoardMessages.items
                                                    .length
                                            }
                                        />
                                    </li>
                                </ul>
                            </div>

                            <Link
                                className="question-title stretched-link"
                                to={`/questions/${sectionTitle}/${question.friendlyUrlPath}`}
                            >
                                <h2 className="c-mb-0 stretched-link-layer text-dark">
                                    {question.headline}
                                </h2>
                            </Link>

                            <div className="c-mb-0 c-mt-3 stretched-link-layer text-truncate">
                                <ArticleBodyRenderer {...question} />
                            </div>

                            <div className="align-items-sm-center align-items-start d-flex flex-column-reverse flex-sm-row justify-content-between">
                                <div className="c-mt-3 c-mt-sm-0 stretched-link-layer">
                                    <Link
                                        to={`/questions/${sectionTitle}/creator/${question.creator.id}`}
                                    >
                                        <UserIcon
                                            fullName={question.creator.name}
                                            portraitURL={question.creator.image}
                                            size="sm"
                                            userId={String(question.creator.id)}
                                        />

                                        <strong className="c-ml-2 text-dark">
                                            {question.creator.name}
                                        </strong>
                                    </Link>

                                    <span className="c-ml-2 small">
                                        {'- ' +
                                            dateToInternationalHuman(
                                                question.dateModified
                                            )}
                                    </span>
                                </div>

                                <TagList tags={question.taxonomyCategoryBriefs} />
                            </div>
                        </div>
                    ))
                )}

                {!!questions.totalCount &&
                    questions.totalCount > questions.pageSize && (
                        <ClayPaginationWithBasicItems
                            activePage={page}
                            ellipsisBuffer={2}
                            onPageChange={setPage}
                            totalPages={Math.ceil(
                                questions.totalCount / questions.pageSize
                            )}
                        />
                    )}
                <Error error={error} />
            </>
        );
    }
});