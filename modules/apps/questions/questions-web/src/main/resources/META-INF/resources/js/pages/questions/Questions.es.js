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

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import Error from '../../components/Error.es';
import QuestionBadge from '../../components/QuestionsBadge.es';
import TagList from '../../components/TagList.es';
import UserIcon from '../../components/UserIcon.es';
import {getRankedThreads, getThreads} from '../../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../../utils/utils.es';

export default ({
	match: {
		params: {creatorId, tag}
	},
	search
}) => {
	const context = useContext(AppContext);

	const [error, setError] = useState({});
	const [loading, setLoading] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize] = useState(20);
	const [questions, setQuestions] = useState([]);
	const [activeFilter, setActiveFilter] = useState('modified');

	useEffect(() => {
		renderQuestions(loadThreads());
	}, [creatorId, page, pageSize, search, context.siteKey, tag, loadThreads]);

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
				siteKey: context.siteKey,
				sort,
				tag
			}),
		[context.siteKey, creatorId, page, pageSize, search, tag]
	);

	const hasValidAnswer = question =>
		question.messageBoardMessages.items.filter(
			message => message.showAsAnswer
		).length > 0;

	const filterBy = type => {
		if (type === 'modified') {
			setActiveFilter('modified');
			renderQuestions(loadThreads());
		}
		else if (type === 'week') {
			setActiveFilter('week');
			const date = new Date();
			date.setDate(date.getDate() - 7);

			renderQuestions(getRankedThreads(date, page, pageSize));
		}
		else if (type === 'month') {
			setActiveFilter('month');
			const date = new Date();
			date.setDate(date.getDate() - 31);

			renderQuestions(getRankedThreads(date, page, pageSize));
		}
		else {
			setActiveFilter('created');
			renderQuestions(loadThreads('dateCreated:desc'));
		}
	};

	return (
		<section className="c-mt-5 c-mx-auto col-xl-10">
			<ClayButton.Group>
				<ClayButton
					displayType={
						activeFilter === 'created' ? 'primary' : 'secondary'
					}
					onClick={() => filterBy('created')}
				>
					{Liferay.Language.get('latest-created')}
				</ClayButton>

				<ClayButton
					displayType={
						activeFilter === 'modified' ? 'primary' : 'secondary'
					}
					onClick={() => filterBy('modified')}
				>
					{Liferay.Language.get('latest-edited')}
				</ClayButton>

				<ClayButton
					displayType={
						activeFilter === 'week' ? 'primary' : 'secondary'
					}
					onClick={() => filterBy('week')}
				>
					{Liferay.Language.get('week')}
				</ClayButton>

				<ClayButton
					displayType={
						activeFilter === 'month' ? 'primary' : 'secondary'
					}
					onClick={() => filterBy('month')}
				>
					{Liferay.Language.get('month')}
				</ClayButton>
			</ClayButton.Group>

			{loading ? (
				<ClayLoadingIndicator />
			) : (
				questions.items &&
				questions.items.map(question => (
					<div
						className={'c-mt-4 c-p-3 question-row'}
						key={question.id}
					>
						<div className="autofit-padded-no-gutter autofit-row">
							<div className="autofit-col autofit-col-expand">
								{/* {question.category} */}
							</div>

							<div className="autofit-col">
								<ul className="question-list">
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
													? 'question-badge-success'
													: ''
											}
											symbol={
												hasValidAnswer(question)
													? 'check-circle-full'
													: 'message'
											}
											value={
												question.messageBoardMessages
													.items.length
											}
										/>
									</li>
								</ul>
							</div>
						</div>

						<Link
							className="question-title stretched-link"
							to={'/questions/' + question.id}
						>
							<h2 className="c-mb-0 stretched-link-layer">
								{question.headline}
							</h2>
						</Link>

						<div className="c-mb-0 c-mt-3 stretched-link-layer text-truncate">
							<ArticleBodyRenderer {...question} />
						</div>

						<div className="autofit-padded-no-gutters autofit-row autofit-row-center c-mt-3">
							<div className="autofit-col autofit-col-expand">
								<div className="autofit-row autofit-row-center">
									<Link
										className="question-user stretched-link-layer"
										to={
											'/questions/creator/' +
											question.creator.id
										}
									>
										<UserIcon
											fullName={question.creator.name}
											portraitURL={question.creator.image}
											size="sm"
											userId={String(question.creator.id)}
										/>

										<strong className="c-ml-2">
											{question.creator.name}
										</strong>
									</Link>

									<span className="c-ml-2 stretched-link-layer">
										{'- ' +
											dateToInternationalHuman(
												question.dateModified
											)}
									</span>
								</div>
							</div>

							<div className="autofit-col">
								<TagList tags={question.keywords} />
							</div>
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
