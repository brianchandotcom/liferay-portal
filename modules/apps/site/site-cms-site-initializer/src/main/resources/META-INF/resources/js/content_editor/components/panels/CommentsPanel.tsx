/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import Sticker from '@clayui/sticker';
import classNames from 'classnames';
import {
	CKEditor5BalloonEditor,
	LiferayEditorConfig,
	TEditor,
} from 'frontend-editor-ckeditor-web';
import {openToast} from 'frontend-js-components-web';
import {Ratings} from 'ratings-taglib';
import React, {useRef, useState} from 'react';

import CommentService, {Comment} from '../services/CommentService';

type Status = 'edit' | 'reply' | null;

export default function CommentsPanel({
	addCommentURL,
	comments: initialComments,
	deleteCommentURL,
	editCommentURL,
	editorConfig,
}: {
	addCommentURL: string;
	comments: Comment[];
	deleteCommentURL: string;
	editCommentURL: string;
	editorConfig: LiferayEditorConfig;
}) {
	const [comments, setComments] = useState<Comment[]>(initialComments);

	const deleteComment = async (
		commentId: string,
		parentCommentId?: string
	) => {
		try {
			await CommentService.deleteComment({
				commentId,
				url: deleteCommentURL,
			});

			const filterComments = (comments: Comment[]) =>
				comments.filter((comment) => comment.commentId !== commentId);

			setComments((comments) =>
				parentCommentId
					? comments.map((comment) =>
							comment.commentId === parentCommentId
								? {
										...comment,
										children: filterComments(
											comment.children
										),
									}
								: comment
						)
					: filterComments(comments)
			);

			openToast({
				message: Liferay.Language.get('your-comment-has-been-deleted'),
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

	const saveComment = async ({
		commentId = null,
		content,
		editor,
		parentCommentId = null,
		status = null,
	}: {
		commentId?: string | null;
		content: string;
		editor: TEditor;
		parentCommentId?: string | null;
		status?: Status;
	}) => {
		try {
			let message = Liferay.Language.get('your-comment-has-been-posted');

			if (status !== 'edit') {
				const newComment = await CommentService.addComment({
					content,
					parentCommentId,
					url: addCommentURL,
				});

				setComments((comments) =>
					parentCommentId
						? comments.map((comment) =>
								comment.commentId === parentCommentId
									? {
											...comment,
											children: [
												...(comment?.children || []),
												newComment,
											],
										}
									: comment
							)
						: [...comments, newComment]
				);
			}
			else if (commentId) {
				const newComment = await CommentService.editComment({
					commentId,
					content,
					url: editCommentURL,
				});

				const updateComments = (comments: Comment[]) =>
					comments.map((comment) =>
						comment.commentId === commentId
							? {
									...newComment,
									children: comment.children,
								}
							: comment
					);

				setComments((comments) =>
					parentCommentId
						? comments.map((comment) =>
								comment.commentId === parentCommentId
									? {
											...comment,
											children: updateComments(
												comment.children
											),
										}
									: comment
							)
						: updateComments(comments)
				);

				message = Liferay.Language.get('your-comment-has-been-edited');
			}

			openToast({message, type: 'success'});

			editor.setData('');
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
			<div className="border-bottom pb-2 px-3">
				<label>{Liferay.Language.get('add-comment')}</label>

				<CommentEditor
					editorConfig={editorConfig}
					onSave={(content, editor) => saveComment({content, editor})}
				/>
			</div>

			{comments.length ? (
				<ul className="p-0">
					{comments.map((comment) => (
						<CommentNode
							comment={comment}
							editorConfig={editorConfig}
							key={comment.commentId}
							onDeleteComment={deleteComment}
							onSaveComment={saveComment}
						/>
					))}
				</ul>
			) : null}
		</>
	);
}

function CommentNode({
	comment,
	editorConfig,
	onDeleteComment,
	onSaveComment,
	parentCommentId,
}: {
	comment: Comment;
	editorConfig: LiferayEditorConfig;
	onDeleteComment: (
		commentId: string,
		parentCommentId?: string
	) => Promise<void>;
	onSaveComment: ({
		commentId,
		content,
		editor,
		parentCommentId,
		status,
	}: {
		commentId?: string;
		content: string;
		editor: TEditor;
		parentCommentId?: string;
		status: Status;
	}) => Promise<void>;
	parentCommentId?: string;
}) {
	const [status, setStatus] = useState<Status>(null);

	return (
		<>
			<li
				className={classNames('list-unstyled pl-3', {
					'border-bottom pr-3 py-3': comment.rootComment,
				})}
			>
				<article>
					<div className="autofit-padded autofit-row mb-1 pt-2">
						<div className="autofit-col pl-0">
							<Sticker shape="user-icon">
								{comment.author.portraitURL ? (
									<Sticker.Image
										alt=""
										src={comment.author.portraitURL}
									/>
								) : (
									<ClayIcon symbol="user" />
								)}
							</Sticker>
						</div>

						<header className="autofit-col autofit-col-expand">
							<span className="list-group-title">
								{comment.author.fullName}
							</span>

							<time className="list-group-text text-3">
								{comment.dateDescription}
							</time>
						</header>

						<ClayDropDownWithItems
							items={[
								{
									label: Liferay.Language.get('edit'),
									onClick: () => setStatus('edit'),
									symbolLeft: 'pencil',
								},
								{
									label: Liferay.Language.get('delete'),
									onClick: () =>
										onDeleteComment(
											comment.commentId,
											parentCommentId
										),
									symbolLeft: 'trash',
								},
							]}
							menuWidth="shrink"
							trigger={
								<ClayButtonWithIcon
									borderless
									displayType="secondary"
									monospaced
									size="xs"
									symbol="ellipsis-v"
									title={Liferay.Language.get('actions')}
								/>
							}
						/>
					</div>

					{status === 'edit' ? (
						<CommentEditor
							editorConfig={editorConfig!}
							initialData={comment.body}
							onCancel={() => setStatus(null)}
							onSave={async (content, editor) => {
								await onSaveComment({
									commentId: comment.commentId,
									content,
									editor,
									parentCommentId,
									status,
								});

								setStatus(null);
							}}
							status={status}
						/>
					) : (
						<div
							className="text-3"
							dangerouslySetInnerHTML={{__html: comment.body}}
						/>
					)}

					{comment.children?.length ? (
						<ul className="border-left border-secondary pl-0">
							{comment.children.map((child: Comment) => (
								<CommentNode
									comment={child}
									editorConfig={editorConfig}
									key={child.commentId}
									onDeleteComment={onDeleteComment}
									onSaveComment={onSaveComment}
									parentCommentId={comment.commentId}
								/>
							))}
						</ul>
					) : null}

					{status === 'reply' ? (
						<CommentEditor
							editorConfig={editorConfig}
							onCancel={() => setStatus(null)}
							onSave={async (content, editor) => {
								await onSaveComment({
									commentId: comment.commentId,
									content,
									editor,
									parentCommentId: comment.commentId,
									status,
								});

								setStatus(null);
							}}
							parentCommentId={comment.commentId}
							status={status}
						/>
					) : (
						<div
							className={classNames('d-flex ratings', {
								'mt-3': comment.children,
								'pb-2': !comment.rootComment,
							})}
						>
							{comment.rootComment ? (
								<ClayButton
									borderless
									displayType="secondary"
									onClick={() => setStatus('reply')}
									size="xs"
								>
									{Liferay.Language.get('reply')}
								</ClayButton>
							) : null}

							<Ratings
								className={comment.className}
								classPK={comment.commentId}
								enabled
								initialNegativeVotes={comment.negativeVotes}
								initialPositiveVotes={comment.positiveVotes}
								signedIn
								size="xs"
								thumbDown={comment.negativeVotes > 0}
								thumbUp={comment.positiveVotes > 0}
								type="thumbs"
							/>
						</div>
					)}
				</article>
			</li>
		</>
	);
}

function CommentEditor({
	editorConfig,
	initialData = '',
	onCancel,
	onSave,
	parentCommentId = null,
	status = null,
}: {
	editorConfig: LiferayEditorConfig;
	initialData?: string;
	onCancel?: () => void;
	onSave: (content: string, editor: TEditor, status: Status) => Promise<void>;
	parentCommentId?: string | null;
	status?: Status;
}) {
	const [content, setContent] = useState<string>();
	const [disabled, setDisabled] = useState<boolean>(false);
	const editorRef = useRef<TEditor | null>(null);

	return (
		<>
			<CKEditor5BalloonEditor
				className="form-control form-control-sm"
				config={{
					...editorConfig,
					initialData,
					label: Liferay.Language.get('add-comment'),
					placeholder: Liferay.Language.get('type-your-comment-here'),
				}}
				onChange={(_, editor) => {
					setContent(editor.getData());
				}}
				onReady={(editor) => {
					editorRef.current = editor;

					if (parentCommentId) {
						editor.focus();
					}
				}}
			/>

			<div className="my-3">
				<ClayButton
					disabled={disabled}
					onClick={async () => {
						if (!content) {
							return;
						}

						setDisabled(true);

						await onSave(content, editorRef.current!, status);

						setDisabled(false);
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
						editorRef.current?.setData('');

						onCancel?.();
					}}
					size="sm"
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</div>
		</>
	);
}
