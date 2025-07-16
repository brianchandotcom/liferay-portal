/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import List from '@clayui/list';
import Sticker from '@clayui/sticker';
import classNames from 'classnames';
import {
	CKEditor5BalloonEditor,
	LiferayEditorConfig,
	TEditor,
} from 'frontend-editor-ckeditor-web';
import {openToast} from 'frontend-js-components-web';
import React, {useRef, useState} from 'react';

import CommentService, {Comment} from '../services/CommentService';

export default function CommentsPanel({
	addCommentURL,
	comments: initialComments,
	editorConfig,
}: {
	addCommentURL: string;
	comments: Comment[];
	editorConfig: {configJSONObject: LiferayEditorConfig};
}) {
	const [comments, setComments] = useState<Comment[]>(initialComments);

	return (
		<>
			<div className="border-bottom pb-2 px-3">
				<label>{Liferay.Language.get('add-comment')}</label>

				<CommentEditor
					addCommentURL={addCommentURL}
					editorConfig={editorConfig.configJSONObject}
					onAddComment={(comment) =>
						setComments((comments) => [...comments, comment])
					}
				/>
			</div>

			{comments.length ? (
				<List>
					{comments.map((comment) => (
						<CommentNode
							addCommentURL={addCommentURL}
							comment={comment}
							editorConfig={editorConfig.configJSONObject}
							key={comment.commentId}
							onAddComment={(
								childComment: Comment,
								parentId: string
							) => {
								setComments((comments) =>
									comments.map((comment) =>
										comment.commentId === parentId
											? {
													...comment,
													children: [
														...(comment?.children ||
															[]),
														childComment,
													],
												}
											: comment
									)
								);
							}}
						/>
					))}
				</List>
			) : null}
		</>
	);
}

function CommentNode({
	addCommentURL,
	comment,
	editorConfig,
	onAddComment,
}: {
	addCommentURL?: string;
	comment: Comment;
	editorConfig?: LiferayEditorConfig;
	onAddComment?: (comment: Comment, parentId: string) => void;
}) {
	const [showEditor, setShowEditor] = useState<boolean>(false);

	return (
		<>
			<List.Item
				className={classNames('mb-0 flex-wrap', {
					'border-0 py-2': !comment.children,
					'border-left-0 border-right-0 border-top-0 py-4':
						comment.children,
				})}
				flex
			>
				<article className="d-flex flex-wrap">
					<List.ItemField>
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
					</List.ItemField>

					<header className="autofit-col autofit-col-expand">
						<span className="list-group-title">
							{comment.author.fullName}
						</span>

						<time className="list-group-text text-3">
							{comment.dateDescription}
						</time>
					</header>

					<List.ItemField
						className="mt-2 text-3 w-100"
						dangerouslySetInnerHTML={{__html: comment.body}}
					/>

					{comment.children?.length ? (
						<List className="border-left border-secondary mb-3 ml-2 pl-1 rounded-0">
							{comment.children.map((child: Comment) => (
								<CommentNode
									comment={child}
									key={child.commentId}
								/>
							))}
						</List>
					) : null}

					{showEditor ? (
						<CommentEditor
							addCommentURL={addCommentURL!}
							editorConfig={editorConfig!}
							onAddComment={(childComment) => {
								onAddComment?.(childComment, comment.commentId);
								setShowEditor(false);
							}}
							onCancel={() => setShowEditor(false)}
							parentCommentId={comment.commentId}
						/>
					) : (
						<div className="w-100">
							{comment.children ? (
								<ClayButton
									borderless
									displayType="secondary"
									onClick={() => setShowEditor(true)}
									size="xs"
								>
									{Liferay.Language.get('reply')}
								</ClayButton>
							) : null}
						</div>
					)}
				</article>
			</List.Item>
		</>
	);
}

function CommentEditor({
	addCommentURL,
	editorConfig,
	onAddComment,
	onCancel,
	parentCommentId = null,
}: {
	addCommentURL: string;
	editorConfig: LiferayEditorConfig;
	onAddComment: (comment: Comment, parentId?: string) => void;
	onCancel?: () => void;
	parentCommentId?: string | null;
}) {
	const [content, setContent] = useState<string>();
	const editorRef = useRef<TEditor | null>(null);

	return (
		<>
			<CKEditor5BalloonEditor
				className={classNames('form-control form-control-sm', {
					'mx-2': parentCommentId,
				})}
				config={{
					...editorConfig,
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
					onClick={async () => {
						if (!content) {
							return;
						}

						try {
							const comment = await CommentService.addComment({
								content,
								parentCommentId,
								url: addCommentURL,
							});

							onAddComment(comment);

							openToast({
								message: Liferay.Language.get(
									'your-comment-has-been-posted'
								),
								type: 'success',
							});

							editorRef.current?.setData('');
						}
						catch (error) {
							openToast({
								message: (error as Error).message,
								type: 'danger',
							});
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
