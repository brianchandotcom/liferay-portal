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

import React, {useContext} from 'react';

import {AppContext} from '../AppContext.es';
import {dateToInternationalHuman, normalizeRating} from '../utils/utils.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import Link from './Link.es';
import QuestionBadge from './QuestionsBadge.es';
import SectionLabel from './SectionLabel.es';
import TagList from './TagList.es';
import UserIcon from './UserIcon.es';

export default ({question}) => {
	const context = useContext(AppContext);

	const sectionTitle = context.section;

	return (
		<div className="c-mt-4 c-p-3 position-relative question-row text-secondary">
			<div className="align-items-center d-flex flex-wrap justify-content-between">
				<SectionLabel section={question.messageBoardSection} />

				<ul className="c-mb-0 d-flex flex-wrap list-unstyled stretched-link-layer">
					<li>
						<QuestionBadge
							symbol={
								normalizeRating(question.aggregateRating) < 0
									? 'caret-bottom'
									: 'caret-top'
							}
							value={normalizeRating(question.aggregateRating)}
						/>
					</li>

					<li>
						<QuestionBadge
							symbol="view"
							value={question.viewCount}
						/>
					</li>

					<li>
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
							value={question.numberOfMessageBoardMessages}
						/>
					</li>
				</ul>
			</div>

			<Link
				className="questions-title stretched-link"
				to={`/questions/${sectionTitle}/${question.friendlyUrlPath}`}
			>
				<h2 className="c-mb-0 stretched-link-layer text-dark">
					{question.headline}
				</h2>
			</Link>

			<div className="c-mb-0 c-mt-3 question-row-article-body stretched-link-layer text-truncate">
				<ArticleBodyRenderer {...question} />
			</div>

			<div className="align-items-sm-center align-items-start d-flex flex-column-reverse flex-sm-row justify-content-between">
				<div className="c-mt-3 c-mt-sm-0 stretched-link-layer">
					<Link
						to={`/questions/${sectionTitle}/creator/${question.creator.id}`}
					>
						<UserIcon
							fullName={question.creator.name}
							portraitURL={question.creator.image}
							size="sm"
							userId={String(question.creator.id)}
						/>

						<strong className="c-ml-2 text-dark">
							{question.creator.name}
						</strong>
					</Link>

					<span className="c-ml-2 small">
						{'- ' + dateToInternationalHuman(question.dateModified)}
					</span>
				</div>

				<TagList tags={question.keywords} />
			</div>
		</div>
	);
};
