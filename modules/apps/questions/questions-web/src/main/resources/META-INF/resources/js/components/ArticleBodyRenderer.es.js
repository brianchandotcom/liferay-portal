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

import React, {useEffect, useState} from 'react';

import Highlight from './Highlight.es';

export default function ArticleBodyRenderer({
	articleBody,
	compactMode = false,
	companyName,
	elapsedTime,
	encodingFormat,
	hasCompanyMx,
	id,
	showSignature = true,
	signature,
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
		<>
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

			{showSignature && signature && (
				<style
					dangerouslySetInnerHTML={{
						__html: `.questions-article-body-${id} ${
							articleBodyContainsParagraph ? 'p' : 'div'
						}:last-child:after {content: " - ${signature} ${_companyName} - ${elapsedTime}"; font-weight: bold;}`,
					}}
				/>
			)}
		</>
	);
}
