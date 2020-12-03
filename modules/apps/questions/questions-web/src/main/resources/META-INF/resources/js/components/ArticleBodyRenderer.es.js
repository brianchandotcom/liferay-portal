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

import parser from 'bbcode-to-react';
import React, {useEffect, useState} from 'react';

import Highlight from './Highlight.es';

export default ({
	articleBody,
	compactMode = false,
	encodingFormat,
	id,
	signature,
}) => {
	const [
		articleBodyContainsParagraph,
		setArticleBodyContainsParagraph,
	] = useState(true);

	useEffect(() => {
		setArticleBodyContainsParagraph(articleBody.includes('<p>'));
	}, [articleBody]);

	return (
		<>
			{encodingFormat === 'bbcode' && (
				<div className={`questions-article-body-${id}`}>
					<div>{parser.toReact(articleBody)}</div>
				</div>
			)}
			{encodingFormat !== 'bbcode' && compactMode && (
				<div
					className={`questions-article-body-${id}`}
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
						}:last-child:after {content: " - ${signature}"; font-weight: bold;}`,
					}}
				/>
			)}
		</>
	);
};
