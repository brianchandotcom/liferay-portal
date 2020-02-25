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
import {Link} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getRelatedThreads} from '../utils/client.es';
import {dateToInternationalHuman, normalizeRating} from '../utils/utils.es';
import QuestionBadge from './QuestionsBadge.es';
import UserIcon from './UserIcon.es';

export default ({question}) => {
	const [relatedQuestions, setRelatedQuestions] = useState([]);
	const context = useContext(AppContext);

	useEffect(() => {
		if (question) {
			getRelatedThreads(question.headline, context.siteKey).then(data =>
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
					<h3 className="c-mt-5">Related Questions</h3>
					<hr />
					<div className="autofit-padded autofit-row">
						{relatedQuestions.map(relatedQuestion => (
							<div className="autofit-col"
								 key={relatedQuestion.id}>
								<div className="autofit-row">
									<div
										className="autofit-col autofit-col-expand">
										{relatedQuestion.messageBoardSection &&
										 relatedQuestion.messageBoardSection
											 .title}
									</div>
									<div>
										<QuestionBadge
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
								</div>
								<h2 className="question-headline">
									<Link
										to={'/questions/' + relatedQuestion.id}>
										{relatedQuestion.headline}
									</Link>
								</h2>
								<div>
									<UserIcon
										fullName={relatedQuestion.creator.name}
										portraitURL={relatedQuestion.creator.image}
										size="sm"
										userId={String(
											relatedQuestion.creator.id)}
									/>
									<span>
									<strong>
										{relatedQuestion.creator.name}
									</strong>
								</span>
									<span>
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
};
