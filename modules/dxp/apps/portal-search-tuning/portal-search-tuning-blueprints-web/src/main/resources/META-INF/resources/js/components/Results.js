/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClaySticker from '@clayui/sticker';
import {PropTypes} from 'prop-types';
import React from 'react';

import {sub} from '../utils/language';

const ICON_MAP = {
	['Blogs Entry']: 'blogs',
	DEFAULT: 'web-content',
	['Document']: 'document',
	['Documents Folder']: 'folder',
	['Knowledge Base Article']: 'info-book',
	['Message Boards Message']: 'message-boards',
	['Page']: 'page',
	['Web Content Article']: 'web-content',
	['Web Content Folder']: 'folder',
	['Wiki Page']: 'wiki-page',
	['com.liferay.bookmarks.model.bookmarksentry']: 'bookmarks',
};

export default function Results({
	activePage,
	hits,
	onPageChange,
	query,
	totalHits,
	totalPages,
}) {
	return (
		<>
			<p>
				{sub(
					Liferay.Language.get('x-results-for-x'),
					[totalHits, <strong key={1}>{query}</strong>],
					false
				)}
			</p>

			<ClayList className="search-results-list">
				{hits.map((item, index) => (
					<ClayList.Item flex key={index}>
						<ClayList.ItemField>
							<ClaySticker displayType="secondary" size="md">
								<ClayIcon
									symbol={
										item.b_type && ICON_MAP[item.b_type]
											? ICON_MAP[item.b_type]
											: ICON_MAP.DEFAULT
									}
								/>
							</ClaySticker>
						</ClayList.ItemField>
						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{item.b_viewURL ? (
									<a
										dangerouslySetInnerHTML={{
											__html: item.title_highlight
												? item.title_highlight
												: item.b_title,
										}}
										href={item.b_viewURL}
										rel="noopener noreferrer"
										target="_blank"
									/>
								) : (
									<span
										dangerouslySetInnerHTML={{
											__html: item.title_highlight
												? item.title_highlight
												: item.b_title,
										}}
									/>
								)}
							</ClayList.ItemTitle>

							{(item.b_type ||
								item.b_author ||
								item.b_created) && (
								<ClayList.ItemText
									className="result-subtext"
									subtext
								>
									{item.b_type && (
										<span>
											<strong>{item.b_type}</strong>
										</span>
									)}

									{item.b_author && (
										<span>
											{Liferay.Language.get('by')}{' '}
											{item.b_author}
										</span>
									)}

									{item.b_created && (
										<span>
											{Liferay.Language.get('on')}{' '}
											{item.b_created}
										</span>
									)}
								</ClayList.ItemText>
							)}

							{item.b_summary && (
								<ClayList.ItemText>
									{item.b_summary}
								</ClayList.ItemText>
							)}
						</ClayList.ItemField>
					</ClayList.Item>
				))}
			</ClayList>

			<ClayPaginationWithBasicItems
				activePage={activePage}
				className="result-pagination"
				onPageChange={onPageChange}
				totalPages={totalPages}
			/>
		</>
	);
}

Results.propTypes = {
	activePage: PropTypes.number,
	hits: PropTypes.arrayOf(PropTypes.object),
	onPageChange: PropTypes.func,
	query: PropTypes.string,
	totalHits: PropTypes.number,
	totalPages: PropTypes.number,
};
