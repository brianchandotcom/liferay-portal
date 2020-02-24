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
					<h3>Related Questions</h3>
					<hr />
					{relatedQuestions.map(relatedQuestion => (
						<ul key={relatedQuestion.id}>
							<li>
								<Link to={'/questions/' + relatedQuestion.id}>
									[
									{(relatedQuestion.aggregateRating &&
										relatedQuestion.aggregateRating
											.ratingCount *
											(relatedQuestion.aggregateRating
												.ratingAverage *
												2 -
												1)) ||
										0}
									] {relatedQuestion.headline}
								</Link>
							</li>
						</ul>
					))}
				</>
			)}
		</>
	);
};
