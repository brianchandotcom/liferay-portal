/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import {getSectionTitle} from './SectionLabel.es';
import TagList from './TagList.es';
import UserIcon from './UserIcon.es';

const MESSAGE_TYPES = {
	answer: {
		prefix: 'RE:',
		type: 1,
	},
	bestAnswer: {type: 4},
	question: {type: 2},
	reply: {prefix: 'RE: RE:', type: 3},
};

const ActivityHeaderBadge = ({
	messageType: {label, symbol, type},
	question,
}) => (
	<div className="align-items-center d-flex flex-wrap justify-content-between">
		<ul className="align-items-center c-mb-2 d-flex flex-nowrap list-badges list-unstyled stretched-link-layer">
			<li>
				<QuestionBadge
					className={classNames(
						'badge-activity bg-light label-secondary text-uppercase',
						{
							'question-best-answer':
								type === MESSAGE_TYPES.bestAnswer.type,
							'questions-reply':
								type === MESSAGE_TYPES.reply.type,
						}
					)}
					isActivityBadge
					symbol={symbol}
					symbolClassName={classNames({
						'questions-reply-icon':
							MESSAGE_TYPES.reply.type === type,
					})}
					value={label}
				/>
			</li>

			<li>
				<QuestionBadge
					className="badge-activity bg-light label-secondary text-uppercase"
					isActivityBadge
					value={getSectionTitle(question.messageBoardSection)}
				/>
			</li>
		</ul>
	</div>
);

const ActivityHeader = ({
	context,
	messageType: {text, type},
	question: {id, locked, parentMessageBoardMessage, seen, status},
}) => (
	<span
		className={classNames(
			'activity-question-name',
			'questions-labels-limit',
			'stretched-link',
			'c-mb-0',
			'stretched-link-layer',
			'text-dark',
			{
				'large-text': MESSAGE_TYPES.question.type === type,
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
					aria-label={Liferay.Language.get(
						'this-question-is-closed-new-answers-and-comments-are-disabled'
					)}
					data-tooltip-align="top"
					symbol="lock"
					title={Liferay.Language.get(
						'this-question-is-closed-new-answers-and-comments-are-disabled'
					)}
				/>
			</span>
		)}
	</span>
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
						symbolClassName="mr-2 questions-comment-reply-icon"
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
}) => (
	<div className="align-items-sm-center align-items-start d-flex flex-column-reverse flex-sm-row justify-content-between">
		<div className="c-mt-sm-0 d-flex mt-2 stretched-link-layer">
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

export {
	MESSAGE_TYPES,
	ActivityBody,
	ActivityFooter,
	ActivityHeader,
	ActivityHeaderBadge,
};
