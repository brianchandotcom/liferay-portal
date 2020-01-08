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

import ClayForm from '@clayui/form';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import React, {useCallback, useEffect, useState} from 'react';

import Answer from '../../components/Answer.es';
import KeywordList from '../../components/KeywordList.es';
import Rating from '../../components/Rating.es';
import {createAnswer, getThread} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {dateToBriefInternationalHuman} from '../../utils/utils.es';

export default ({
	match: {
		params: {questionId}
	}
}) => {
	const [answers, setAnswers] = useState([]);
	const [articleBody, setArticleBody] = useState();
	const [page, setPage] = useState(1);
	const [question, setQuestion] = useState();

	const loadThread = useCallback(
		() =>
			getThread(questionId, page).then(data => {
				setQuestion(data);
				setAnswers(data.messageBoardMessages.items);
			}),
		[page, questionId]
	);

	useEffect(() => {
		loadThread();
	}, [loadThread]);

	const postAnswer = () => {
		createAnswer(articleBody, question.id).then(() => {
			setArticleBody('');
			return loadThread();
		});
	};

	return (
		<section>
			{question && (
				<div className="autofit-padded autofit-row">
					<div className="autofit-col">
						<Rating
							aggregateRating={question.aggregateRating}
							entityId={question.id}
							myRating={
								question.myRating &&
								question.myRating.ratingValue
							}
							type={'Thread'}
						/>
					</div>

					<div className="autofit-col autofit-col-expand">
						<div className="autofit-section">
							<h1 className="question-headline">
								{question.headline}
							</h1>
							<p>
								<small>
									{Liferay.Language.get('asked')}{' '}
									{dateToBriefInternationalHuman(
										question.dateCreated
									)}
									{' - '}
									{Liferay.Language.get('active')}{' '}
									{dateToBriefInternationalHuman(
										question.dateModified
									)}
									{' - '}
									{lang.sub(
										Liferay.Language.get('viewed-x-times'),
										[question.viewCount]
									)}
								</small>
							</p>
							<div>
								<p>{question.articleBody}</p>
							</div>

							<KeywordList keywords={question.keywords} />

							<hr />
						</div>

						{/*<Subscription*/}
						{/*	onSubscription={subscribed =>*/}
						{/*		setQuestion({...question, subscribed})*/}
						{/*	}*/}
						{/*	question={question}*/}
						{/*/>*/}
						{/*<UserRow*/}
						{/*	answer={false}*/}
						{/*	creator={question.creator}*/}
						{/*/>*/}

						<h3 className="subtitle">
							{answers.length} {Liferay.Language.get('answers')}
						</h3>

						{answers.map(answer => (
							<Answer answer={answer} key={answer.id} />
						))}

						{!!answers.totalCount &&
							answers.totalCount > answers.pageSize && (
								<ClayPaginationWithBasicItems
									activePage={page}
									ellipsisBuffer={2}
									onPageChange={setPage}
									totalPages={Math.ceil(
										answers.totalCount / answers.pageSize
									)}
								/>
							)}

						<ClayForm>
							<ClayForm.Group className="form-group-sm">
								<label htmlFor="basicInput">
									{Liferay.Language.get('your-answer')}
								</label>
								<textarea
									className="form-control"
									onChange={event =>
										setArticleBody(event.target.value)
									}
									value={articleBody}
								/>
							</ClayForm.Group>
						</ClayForm>

						<div className="sheet-footer">
							<div className="btn-group-item">
								<div className="btn-group-item">
									<button
										className="btn btn-primary"
										disabled={!articleBody}
										onClick={postAnswer}
									>
										{Liferay.Language.get(
											'post-your-answer'
										)}
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			)}
		</section>
	);
};
