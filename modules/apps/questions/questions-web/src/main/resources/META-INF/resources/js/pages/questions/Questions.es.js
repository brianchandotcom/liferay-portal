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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import Error from '../../components/Error.es';
import QuestionBadge from '../../components/QuestionsBadge.es';
import SectionLabel from '../../components/SectionLabel.es';
import TagList from '../../components/TagList.es';
import UserIcon from '../../components/UserIcon.es';
import {getRankedThreads, getThreads} from '../../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../../utils/utils.es';
import QuestionsNavigationBar from '../QuestionsNavigationBar.es';

export default ({
	match: {
		params: {creatorId, sectionId, tag},
	},
}) => {
	const [error, setError] = useState({});
	const [loading, setLoading] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize] = useState(20);
	const [questions, setQuestions] = useState([]);
	const [search, setSearch] = useState('');

	const context = useContext(AppContext);

	useEffect(() => {
		renderQuestions(loadThreads());
	}, [
		creatorId,
		context.siteKey,
		page,
		pageSize,
		sectionId,
		search,
		tag,
		loadThreads,
	]);

	const renderQuestions = questions => {
		questions
			.then(data => setQuestions(data || []))
			.then(() => setLoading(false))
			.catch(_ => {
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
				search,
				sectionId,
				siteKey: context.siteKey,
				sort,
				tag,
			}),
		[context.siteKey, creatorId, page, pageSize, search, sectionId, tag]
	);

	const hasValidAnswer = question =>
		question.messageBoardMessages.items.filter(
			message => message.showAsAnswer
		).length > 0;

	const filterChange = type => {
		if (type === 'modified') {
			renderQuestions(loadThreads());
		}
		else if (type === 'week') {
			const date = new Date();
			date.setDate(date.getDate() - 7);

			renderQuestions(
				getRankedThreads(
					date,
					page,
					pageSize,
					sectionId,
					context.siteKey
				)
			);
		}
		else if (type === 'month') {
			const date = new Date();
			date.setDate(date.getDate() - 31);

			renderQuestions(
				getRankedThreads(
					date,
					page,
					pageSize,
					sectionId,
					context.siteKey
				)
			);
		}
		else {
			renderQuestions(loadThreads('dateCreated:desc'));
		}
	};

	return (
		<section className="c-mt-5 c-mx-auto c-px-0 col-xl-10">
			<QuestionsNavigationBar
				filterChange={filterChange}
				searchChange={search => {
					setSearch(search);
				}}
			/>

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
							to={`/questions/${sectionId}/${question.friendlyUrlPath}`}
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
									to={`/questions/${sectionId}/creator/${question.creator.id}`}
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

							<TagList tags={question.keywords} />
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
		</section>
	);
};
