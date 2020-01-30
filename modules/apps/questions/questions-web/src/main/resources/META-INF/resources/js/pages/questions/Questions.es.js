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
import parser from 'bbcode-to-react';
import React, {useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import KeywordsList from '../../components/KeywordList.es';
import QuestionBadge from '../../components/QuestionsBadge.es';
import UserIcon from '../../components/UserIcon.es';
import {getThreads} from '../../utils/client.es';
import {dateToInternationalHuman} from '../../utils/utils.es';

export default ({
	match: {
		params: {keyword}
	}
}) => {
	const context = useContext(AppContext);

	const [loading, setLoading] = useState(true);
	const [page, setPage] = useState(1);
	const [pageSize] = useState(5);
	const [questions, setQuestions] = useState([]);

	useEffect(() => {
		getThreads({keyword, page, pageSize, siteKey: context.siteKey})
			.then(data => setQuestions(data))
			.then(() => setLoading(false));
	}, [keyword, page, pageSize, context.siteKey]);

	const hasValidAnswer = question =>
		question.messageBoardMessages.items.filter(
			message => message.showAsAnswer
		).length > 0;

	return (
		<section>
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				questions.items &&
				questions.items.map(question => (
					<div className={'question-row'} key={question.id}>
						<div className="autofit-padded autofit-row">
							<div className="autofit-col autofit-col-expand">
								<div className="autofit-section">
									<h2>
										<Link to={'/questions/' + question.id}>
											{question.headline}
										</Link>
									</h2>
								</div>
							</div>
							<div className="autofit-col">
								<p>
									<QuestionBadge
										symbol="caret-top"
										value={
											question.aggregateRating &&
											question.aggregateRating.ratingCount
										}
									/>

									<QuestionBadge
										symbol="view"
										value={question.viewCount}
									/>

									<QuestionBadge
										className={
											hasValidAnswer(question)
												? 'question-accepted-badge'
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
								</p>
							</div>
						</div>
						<div className="autofit-padded autofit-row">
							<div className="autofit-col autofit-col-expand">
								<p className="text-truncate">
									{parser.toReact(question.articleBody)}
								</p>
							</div>
						</div>

						<div className="autofit-padded autofit-row autofit-row-center">
							<div className="autofit-col autofit-col-expand">
								<div>
									<UserIcon
										fullName={question.creator.name}
										portraitURL={question.creator.image}
										size="sm"
										userId={String(question.creator.id)}
									/>
									<span>
										<strong>
											{' ' + question.creator.name}
										</strong>
									</span>
									<span>
										{' - ' +
											dateToInternationalHuman(
												question.dateModified
											)}
									</span>
								</div>
							</div>
							<div>
								<KeywordsList keywords={question.keywords} />
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
		</section>
	);
};
