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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useQuery} from 'graphql-hooks';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getRelatedThreadsQuery} from '../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../utils/utils.es';
import Link from './Link.es';
import QuestionBadge from './QuestionsBadge.es';
import SectionLabel from './SectionLabel.es';
import UserIcon from './UserIcon.es';

export default withRouter(
	({
		match: {
			params: {sectionTitle},
		},
		question,
	}) => {
		const context = useContext(AppContext);

		const [relatedQuestions, setRelatedQuestions] = useState([]);

		const {data} = useQuery(getRelatedThreadsQuery, {
			variables: {
				search: question && question.headline,
				siteKey: context.siteKey,
			},
		});

		useEffect(() => {
			setRelatedQuestions(
				data?.messageBoardThreads.items.filter(
					(otherQuestion) => otherQuestion.id !== question.id
				) ?? []
			);
		}, [data, question.id]);

		return (
			<>
				<h2 className="c-mt-5 font-weight-light h3 text-secondary">
					{Liferay.Language.get('related-questions')}
				</h2>

				<hr />

				{relatedQuestions.length ? (
					<div className="questions-container row">
						{relatedQuestions.map((relatedQuestion) => (
							<div
								className="col-lg-3 col-md-4 col-sm-6 p-3 position-relative"
								key={relatedQuestion.id}
							>
								<div className="align-items-center d-flex justify-content-between stretched-link-layer">
									<SectionLabel
										section={
											relatedQuestion.messageBoardSection
										}
									/>

									<QuestionBadge
										className="text-secondary"
										symbol={
											normalizeRating(
												question.aggregateRating
											) < 0
												? 'caret-bottom'
												: 'caret-top'
										}
										value={normalizeRating(
											relatedQuestion.aggregateRating
										)}
									/>
								</div>

								<Link
									className="c-mt-2 d-block questions-title stretched-link text-reset"
									to={`/questions/${sectionTitle}/${relatedQuestion.friendlyUrlPath}`}
								>
									<h3
										className={classNames(
											'h2',
											'stretched-link-layer',
											{
												'question-seen':
													relatedQuestion.seen,
											}
										)}
									>
										{relatedQuestion.headline}

										{!!relatedQuestion.locked && (
											<span className="c-ml-2">
												<ClayIcon symbol="lock" />
											</span>
										)}
									</h3>
								</Link>

								<div className="c-mt-3 small stretched-link-layer">
									<UserIcon
										fullName={
											relatedQuestion.creator
												? relatedQuestion.creator.name
												: ''
										}
										portraitURL={
											relatedQuestion.creator
												? relatedQuestion.creator.image
												: ''
										}
										size="sm"
										userId={
											relatedQuestion.creator
												? String(
														relatedQuestion.creator
															.id
												  )
												: '0'
										}
									/>

									<span className="c-ml-2 font-weight-bold">
										{relatedQuestion.creator
											? relatedQuestion.creator.name
											: Liferay.Language.get(
													'anonymous-user-configuration-name'
											  )}
									</span>

									<span className="text-secondary">
										{' - ' +
											dateToInternationalHuman(
												relatedQuestion.dateModified
											)}
									</span>
								</div>
							</div>
						))}
					</div>
				) : (
					<div className="d-flex justify-content-center">
						<h2 className="c-my-5 font-weight-light h3 text-secondary">
							{Liferay.Language.get(
								'no-related-questions-were-found'
							)}
						</h2>
					</div>
				)}
			</>
		);
	}
);
