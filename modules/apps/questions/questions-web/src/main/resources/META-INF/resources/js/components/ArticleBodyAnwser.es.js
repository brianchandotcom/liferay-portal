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
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {MESSAGE_TYPES} from './ActivityUI.es';
import Highlight from './Highlight.es';

export default function ArticleBodyAnwser({
	articleBody,
	compactMode = false,
	companyName,
	encodingFormat,
	hasCompanyMx,
	id,
	signature,
	type,
}) {
	const [
		articleBodyContainsParagraph,
		setArticleBodyContainsParagraph,
	] = useState(true);

	useEffect(() => {
		setArticleBodyContainsParagraph(articleBody.includes('<p>'));
	}, [articleBody]);

	const _companyName = hasCompanyMx && companyName ? `(${companyName})` : '';

	return (
		<div
			className={classNames(
				'h-100 p-3 position-relative rectangle-comment w-100',
				{
					'questions-answer questions-answer-success':
						type === MESSAGE_TYPES.bestAnswer.type,
				}
			)}
		>
			<div className="icon-quote-left position-absolute">
				<ClayIcon symbol="quote-left" />
			</div>

			{encodingFormat !== 'bbcode' && compactMode && (
				<div
					className={`questions-article-body-${id} questions-labels-limit`}
					dangerouslySetInnerHTML={{__html: articleBody}}
				/>
			)}

			{encodingFormat !== 'bbcode' && !compactMode && (
				<div className={`cke_readonly questions-article-body-${id}`}>
					<Highlight innerHTML={true}>{articleBody}</Highlight>
				</div>
			)}

			{signature && (
				<style
					dangerouslySetInnerHTML={{
						__html: `.questions-article-body-${id} ${
							articleBodyContainsParagraph ? 'p' : 'div'
						}:last-child:after {content: " - ${signature} ${_companyName}"; font-weight: bold;}`,
					}}
				/>
			)}

			<div className="icon-quote-right position-absolute">
				<ClayIcon symbol="quote-right" />
			</div>
		</div>
	);
}
