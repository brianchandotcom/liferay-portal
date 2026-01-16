/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Sticker from '@clayui/sticker';
import classNames from 'classnames';
import {openToast} from 'frontend-js-components-web';
import {escapeHTML} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import DigitalSalesRoomService, {
	TCommentDTO,
} from '../commons/DigitalSalesRoomService';

function formatDate(date: string, languageTag: string): string {
	return (
		date &&
		languageTag &&
		Intl.DateTimeFormat(languageTag.replace(/_.*/, ''), {
			day: 'numeric',
			hour: 'numeric',
			hour12: true,
			minute: 'numeric',
			month: 'short',
			year: 'numeric',
		}).format(new Date(date))
	);
}

function DSRCommentsPanel({roomId}: {roomId: number}) {
	const [comments, setComments] = useState<Array<TCommentDTO>>([]);
	const [page, setPage] = useState(1);
	const [showLoadMore, setShowLoadMore] = useState(false);

	useEffect(() => {
		DigitalSalesRoomService.getComments(roomId, page)
			.then((data) => {
				setComments((prevState) =>
					page === 1 ? data.items : prevState.concat(data.items)
				);
				setShowLoadMore(page < data.lastPage);
			})
			.catch((error) => {
				openToast({
					message: (error as Error).message,
					type: 'danger',
				});
			});
	}, [page, roomId]);

	return (
		<>
			{comments.length ? (
				<ul className="p-0">
					{comments.map((comment) => (
						<DSRCommentNode comment={comment} key={comment.id} />
					))}
				</ul>
			) : null}

			{showLoadMore && (
				<ClayButton
					className="btn-block"
					data-qa-id="loadMoreButton"
					displayType="secondary"
					onClick={() => {
						setPage((prev) => prev + 1);
					}}
					size="sm"
				>
					{Liferay.Language.get('load-more')}
				</ClayButton>
			)}
		</>
	);
}

function DSRCommentNode({comment}: {comment: TCommentDTO}) {
	return (
		<>
			<li className={classNames('list-unstyled border-bottom pb-3')}>
				<article>
					<div className="autofit-padded autofit-row mb-1 pt-2">
						<div className="pl-0 pt-1">
							<Sticker shape="user-icon">
								{comment.creator.image ? (
									<Sticker.Image
										alt={comment.creator.name}
										src={comment.creator.image}
									/>
								) : (
									<ClayIcon symbol="user" />
								)}
							</Sticker>
						</div>

						<header className="autofit-col autofit-col-expand">
							<span className="list-group-title">
								{comment.creator.name}
							</span>

							<time className="list-group-text text-3">
								{formatDate(
									comment.dateCreated,
									Liferay.ThemeDisplay.getLanguageId()
								)}
							</time>
						</header>
					</div>

					<div
						className="my-3 text-3"
						dangerouslySetInnerHTML={{
							__html: escapeHTML(comment.text),
						}}
					/>
				</article>
			</li>
		</>
	);
}

export default DSRCommentsPanel;
