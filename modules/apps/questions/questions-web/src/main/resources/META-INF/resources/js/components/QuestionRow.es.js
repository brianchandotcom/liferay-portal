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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React from 'react';

import {
	dateToInternationalHuman,
	normalizeRating,
	stripHTML,
} from '../utils/utils.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import Link from './Link.es';
import QuestionBadge from './QuestionsBadge.es';
import SectionLabel from './SectionLabel.es';
import TagList from './TagList.es';
import UserIcon from './UserIcon.es';

export default ({currentSection, items, question, showSectionLabel}) => {
	const sectionTitle =
		currentSection || currentSection === '0'
			? currentSection
			: question.messageBoardSection &&
			  question.messageBoardSection.title;

	const creatorInformation = question.creator
		? {
				link: `/questions/${sectionTitle}/creator/${question.creator.id}`,
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

	return (
		<div className="c-mt-4 c-p-3 position-relative question-row text-secondary">
			<div className="align-items-center d-flex flex-wrap justify-content-between">
				<span>
					{showSectionLabel && (
						<SectionLabel section={question.messageBoardSection} />
					)}
				</span>

				<ul className="c-mb-0 d-flex flex-wrap list-unstyled stretched-link-layer">
					<li>
						<QuestionBadge
							symbol={
								normalizeRating(question.aggregateRating) < 0
									? 'caret-bottom'
									: 'caret-top'
							}
							tooltip={Liferay.Language.get('votes')}
							value={normalizeRating(question.aggregateRating)}
						/>
					</li>

					<li>
						<QuestionBadge
							symbol="view"
							tooltip={Liferay.Language.get('view-count')}
							value={question.viewCount}
						/>
					</li>

					<li data-testid="has-valid-answer-badge">
						<QuestionBadge
							className={
								question.hasValidAnswer
									? 'alert-success border-0'
									: ''
							}
							symbol={
								question.hasValidAnswer
									? 'check-circle-full'
									: 'message'
							}
							tooltip={Liferay.Language.get('number-of-replies')}
							value={question.numberOfMessageBoardMessages}
						/>
					</li>

					{items && !!items.length && (
						<li>
							<ClayDropDownWithItems
								className="c-py-1"
								items={items}
								trigger={
									<ClayButtonWithIcon
										displayType="unstyled"
										small
										symbol="ellipsis-v"
									/>
								}
							/>
						</li>
					)}
				</ul>
			</div>

			<Link
				className="questions-title stretched-link"
				to={`/questions/${sectionTitle}/${question.friendlyUrlPath}`}
			>
				<h2
					className={classNames(
						'c-mb-0',
						'stretched-link-layer',
						'text-dark',
						{
							'question-seen': question.seen,
						}
					)}
				>
					{question.headline}
					{question.status && question.status !== 'approved' && (
						<span className="c-ml-2">
							<ClayLabel displayType="info">
								{question.status}
							</ClayLabel>
						</span>
					)}

					{!!question.locked && (
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
				</h2>
			</Link>

			<div className="c-mb-0 c-mt-3 question-row-article-body stretched-link-layer text-truncate">
				<ArticleBodyRenderer
					{...question}
					articleBody={stripHTML(question.articleBody)}
					compactMode={true}
				/>
			</div>

			<div className="align-items-sm-center align-items-start d-flex flex-column-reverse flex-sm-row justify-content-between">
				<div className="c-mt-3 c-mt-sm-0 stretched-link-layer">
					<Link to={creatorInformation.link}>
						<UserIcon
							fullName={creatorInformation.name}
							portraitURL={creatorInformation.portraitURL}
							size="sm"
							userId={creatorInformation.userId}
						/>

						<strong className="c-ml-2 text-dark">
							{creatorInformation.name ||
								Liferay.Language.get(
									'anonymous-user-configuration-name'
								)}
						</strong>
					</Link>

					<span className="c-ml-2 small">
						{'- ' + dateToInternationalHuman(question.dateModified)}
					</span>
				</div>

				<TagList sectionTitle={sectionTitle} tags={question.keywords} />
			</div>
		</div>
	);
};
