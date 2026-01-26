/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import Sticker from '@clayui/sticker';
import classNames from 'classnames';
import {openToast} from 'frontend-js-components-web';
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
				setComments((prevState) => {
					if (page === 1) {
						return data.items;
					}

					const existingIds = new Set(
						prevState.map((comment) => comment.id)
					);

					return prevState.concat(
						data.items.filter(
							(comment) => !existingIds.has(comment.id)
						)
					);
				});
				setShowLoadMore(page < data.lastPage);
			})
			.catch((error) => {
				openToast({
					message: (error as Error).message,
					type: 'danger',
				});
			});
	}, [page, roomId]);

	const handleSaveComment = async (comment: string, roomId: number) => {
		try {
			const data =
				await DigitalSalesRoomService.postDigitalSalesRoomComment(
					roomId,
					comment
				);

			setComments((prevState) => [data, ...prevState]);

			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
		}
		catch (error) {
			openToast({
				message: (error as Error).message,
				type: 'danger',
			});
		}
	};

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
			<DSRCommentEditor
				onSave={(comment) => {
					return handleSaveComment(comment, roomId);
				}}
			></DSRCommentEditor>
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

					<div className="my-3 text-3">{comment.text}</div>
				</article>
			</li>
		</>
	);
}

function DSRCommentEditor({
	onSave,
}: {
	onSave: (comment: string) => Promise<void>;
}) {
	const [comment, setComment] = useState('');
	const [disabled, setDisabled] = useState<boolean>(false);

	return (
		<>
			<div className="py-2">
				<strong>{Liferay.Language.get('add-comment')}</strong>
			</div>
			<ClayInput
				className="form-control form-control-sm"
				component="textarea"
				data-qa-id="commentTextarea"
				onChange={(event) => {
					setComment(event.target.value);
				}}
				placeholder={Liferay.Language.get('type-your-comment-here')}
				value={comment}
			></ClayInput>
			<div className="my-3">
				<ClayButton
					disabled={disabled || !comment.trim()}
					onClick={async () => {
						setDisabled(true);
						try {
							await onSave(comment.trim());
							setComment('');
						}
						finally {
							setDisabled(false);
						}
					}}
					size="sm"
				>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton
					borderless
					className="ml-1"
					displayType="secondary"
					onClick={() => {
						setComment('');
					}}
					size="sm"
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</div>
		</>
	);
}

export default DSRCommentsPanel;
