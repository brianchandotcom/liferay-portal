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
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React from 'react';

import {stripHTML} from '../utils/utils.es';
import ArticleBodyAnwser from './ArticleBodyAnwser.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import EditedTimestamp from './EditedTimestamp.es';
import Link from './Link.es';
import QuestionBadge from './QuestionsBadge.es';
import TagList from './TagList.es';
import UserIcon from './UserIcon.es';

const DAYS_UNTIL_SHOW_LABEL = 3;
const TIME_IN_DAYS = 1000 * 60 * 60 * 24;

const MESSAGE_TYPES = {
	answer: {
		prefix: 'RE:',
		type: 1,
	},
	bestAnswer: {type: 4},
	question: {type: 2},
	reply: {prefix: 'RE: RE:', type: 3},
};

const getQuestionCreatedInDays = (dateCreated) => {
	const givenDate = new Date(dateCreated);
	const now = new Date();
	const timeDifference = now.getTime() - givenDate.getTime();
	const diffDays = (timeDifference / TIME_IN_DAYS).toFixed(0);

	return diffDays;
};

const ActivityHeaderBadge = ({
	messageType: {label, symbol, type},
	question,
}) => {
	const DAYS_SINCE_CREATED = getQuestionCreatedInDays(question.dateCreated);

	return (
		<div className="align-items-center d-flex flex-wrap justify-content-between">
			<ul className="align-items-center c-mb-2 d-flex flex-nowrap list-badges list-unstyled stretched-link-layer">
				{DAYS_SINCE_CREATED <= DAYS_UNTIL_SHOW_LABEL &&
					type !== MESSAGE_TYPES.reply.type &&
					type !== MESSAGE_TYPES.bestAnswer.type && (
						<li>
							<span className="new-question-badge text-uppercase">
								{Liferay.Language.get('new')}
							</span>
						</li>
					)}

				<li>
					<QuestionBadge
						className={classNames(
							'bg-light label-secondary text-uppercase',
							{
								'questions-reply':
									type === MESSAGE_TYPES.reply.type,
								'text-success border border-success':
									type === MESSAGE_TYPES.bestAnswer.type,
							}
						)}
						isActivityBadge
						symbol={symbol}
						symbolClassName={classNames({
							'questions-reply-icon': symbol === 'reply',
						})}
						value={label}
					/>
				</li>

				<li>
					<QuestionBadge
						className="bg-light label-secondary text-uppercase"
						isActivityBadge
						value={question.messageBoardSection.title}
					/>
				</li>
			</ul>
		</div>
	);
};

const ActivityHeader = ({
	context,
	messageType: {text, type},
	question: {id, locked, parentMessageBoardMessage, seen, status},
}) => (
	<h5
		className={classNames(
			'questions-labels-limit',
			'stretched-link',
			'c-mb-0',
			'stretched-link-layer',
			'text-dark',
			{
				'question-seen': seen || context?.Visited?.includes(id),
			}
		)}
	>
		{type === MESSAGE_TYPES.bestAnswer.type
			? parentMessageBoardMessage.headline
			: text}

		{status && status !== 'approved' && (
			<span className="c-ml-2">
				<ClayLabel displayType="info">{status}</ClayLabel>
			</span>
		)}

		{!!locked && (
			<span className="c-ml-2">
				<ClayIcon
					data-tooltip-align="top"
					symbol="lock"
					title={Liferay.Language.get(
						'this-question-is-closed-new-answers-and-comments-are-disabled'
					)}
				/>
			</span>
		)}
	</h5>
);

const ActivityBody = ({messageType: {symbol, type}, question}) => {
	if (type === MESSAGE_TYPES.bestAnswer.type) {
		return (
			<ArticleBodyAnwser
				{...question}
				articleBody={stripHTML(question.articleBody)}
				compactMode
				type={type}
			/>
		);
	}

	if (type === MESSAGE_TYPES.reply.type) {
		return (
			<>
				<ArticleBodyAnwser
					{...question}
					articleBody={stripHTML(
						question.parentMessageBoardMessage.articleBody
					)}
					compactMode
				/>

				{type !== MESSAGE_TYPES.bestAnswer.type && (
					<QuestionBadge
						className="questions-reply"
						isActivityBadge
						symbol={symbol}
						symbolClassName="questions-comment-reply-icon"
						value={stripHTML(question.articleBody)}
					/>
				)}
			</>
		);
	}

	if (type === MESSAGE_TYPES.answer.type) {
		return (
			<ArticleBodyRenderer
				{...question}
				articleBody={stripHTML(question.articleBody)}
				compactMode
			/>
		);
	}

	return null;
};

const ActivityFooter = ({
	creatorId,
	creatorInformation,
	messageType: {type},
	question,
	sectionTitle,
}) => {
	return (
		<div className="align-items-sm-center align-items-start d-flex flex-column-reverse flex-sm-row justify-content-between">
			<div className="c-mt-3 c-mt-sm-0 stretched-link-layer">
				<Link
					className={classNames({
						'disabled-link': !!creatorId,
					})}
					to={creatorInformation.link}
				>
					{creatorInformation.portraitURL && (
						<UserIcon
							fullName={creatorInformation.name}
							portraitURL={creatorInformation.portraitURL}
							size="sm"
							userId={creatorInformation.userId}
						/>
					)}

					<strong
						className={classNames('text-dark', {
							'c-ml-2': creatorInformation.portraitURL,
						})}
					>
						{creatorInformation.name ||
							Liferay.Language.get(
								'anonymous-user-configuration-name'
							)}
					</strong>
				</Link>

				<EditedTimestamp
					dateCreated={question.dateCreated}
					dateModified={question.dateModified}
					operationText={
						type === MESSAGE_TYPES.question.type
							? Liferay.Language.get('asked')
							: ''
					}
				/>
			</div>

			{question.keywords && (
				<TagList
					sectionTitle={
						sectionTitle?.title ? sectionTitle.title : sectionTitle
					}
					tags={question.keywords}
				/>
			)}
		</div>
	);
};

export {
	MESSAGE_TYPES,
	ActivityBody,
	ActivityFooter,
	ActivityHeader,
	ActivityHeaderBadge,
};
