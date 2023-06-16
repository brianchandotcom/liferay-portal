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

import classNames from 'classnames';
import React, {useMemo} from 'react';
import {Link} from 'react-router-dom';

import {stripHTML} from '../utils/utils.es';
import {
	ActivityBody,
	ActivityFooter,
	ActivityHeader,
	ActivityHeaderBadge,
	MESSAGE_TYPES,
} from './ActivityUI.es';

export default function ActivityQuestionRow({
	context,
	creatorId,
	currentSection,
	linkProps,
	question,
	rowSelected,
}) {
	const {headline} = question;

	const sectionTitle =
		currentSection || currentSection === '0'
			? currentSection
			: question.messageBoardSection &&
			  question.messageBoardSection.title;

	const creatorInformation = question.creator
		? {
				link: `/questions/all/creator/${question.creator.id}`,
				name: question.creator.name,
				portraitURL: question.creator.image,
				userId: String(question.creator.id),
		  }
		: {
				link: `/questions/${sectionTitle}`,
				name: '',
				portraitURL: '',
				userId: '0',
		  };

	const isRowSelected = question.friendlyUrlPath === rowSelected;

	const messageType = useMemo(() => {
		if (question.showAsAnswer) {
			return {
				label: Liferay.Language.get('best-answer'),
				symbol: 'check-circle-full',
				text: stripHTML(question.articleBody).replace(
					MESSAGE_TYPES.answer.prefix,
					''
				),
				type: MESSAGE_TYPES.bestAnswer.type,
			};
		}

		if (headline.startsWith(MESSAGE_TYPES.reply.prefix)) {
			return {
				label: Liferay.Language.get('comment-reply'),
				symbol: 'reply',
				text: headline.replace(MESSAGE_TYPES.reply.prefix, ''),
				type: MESSAGE_TYPES.reply.type,
			};
		}

		if (headline.startsWith(MESSAGE_TYPES.answer.prefix)) {
			return {
				label: Liferay.Language.get('answer[noun]'),
				symbol: 'message',
				text: headline.replace(MESSAGE_TYPES.answer.prefix, ''),
				type: MESSAGE_TYPES.answer.type,
			};
		}

		return {
			label: Liferay.Language.get('question'),
			symbol: 'question-circle-full',
			text: headline,
			type: MESSAGE_TYPES.question.type,
		};
	}, [headline, question.articleBody, question.showAsAnswer]);

	return (
		<div
			className={classNames(
				'c-mt-3 c-p-3 position-relative question-row text-secondary',
				{
					'question-row-selected': isRowSelected,
					'question-row-unselected': !isRowSelected,
				}
			)}
		>
			<ActivityHeaderBadge
				messageType={messageType}
				question={question}
			/>

			<Link
				className="questions-title stretched-link"
				to={`/questions/${sectionTitle}/${question.friendlyUrlPath}`}
				{...linkProps}
			>
				<ActivityHeader
					context={context}
					messageType={messageType}
					question={question}
				/>
			</Link>

			<div
				className={classNames('c-mb-1 c-mt-2 stretched-link-layer', {
					'questions-answer questions-answer-success':
						messageType.type === MESSAGE_TYPES.bestAnswer.type,
				})}
			>
				<ActivityBody messageType={messageType} question={question} />
			</div>

			{messageType.type !== MESSAGE_TYPES.bestAnswer.type && (
				<ActivityFooter
					creatorId={creatorId}
					creatorInformation={creatorInformation}
					messageType={messageType}
					question={question}
					sectionTitle={sectionTitle}
				/>
			)}
		</div>
	);
}
