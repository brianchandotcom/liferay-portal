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

import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getRelatedThreads} from '../utils/client.es';
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
		const [relatedQuestions, setRelatedQuestions] = useState([]);
		const context = useContext(AppContext);

		useEffect(() => {
			if (question) {
				getRelatedThreads(
					question.headline,
					context.siteKey
				).then(data =>
					setRelatedQuestions(
						data.items.filter(
							otherQuestion => otherQuestion.id !== question.id
						)
					)
				);
			}
		}, [question, context.siteKey]);

		return (
			<>
				{!!relatedQuestions.length && (
					<>
						<h2 className="c-mt-5 font-weight-light h3 text-secondary">
							Related Questions
						</h2>

						<hr />

						<div className="row">
							{relatedQuestions.map(relatedQuestion => (
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
										<h3 className="h2 stretched-link-layer">
											{relatedQuestion.headline}
										</h3>
									</Link>

									<div className="c-mt-3 small stretched-link-layer">
										<UserIcon
											fullName={
												relatedQuestion.creator.name
											}
											portraitURL={
												relatedQuestion.creator.image
											}
											size="sm"
											userId={String(
												relatedQuestion.creator.id
											)}
										/>

										<span className="c-ml-2 font-weight-bold">
											{relatedQuestion.creator.name}
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
					</>
				)}
			</>
		);
	}
);
