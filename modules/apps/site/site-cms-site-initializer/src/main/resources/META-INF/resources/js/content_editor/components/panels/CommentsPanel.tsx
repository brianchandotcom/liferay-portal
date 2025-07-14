/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import {
	CKEditor5BalloonEditor,
	LiferayEditorConfig,
	TEditor,
} from 'frontend-editor-ckeditor-web';
import {openToast} from 'frontend-js-components-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

export type Comment = {
	author: {
		fullName: string;
		portraitURL: string;
		userId: string;
	};
	body: string;
	children: Comment[];
	className: string;
	commentId: string;
	dateDescription: string;
	edited: boolean;
	negativeVotes: number;
	positiveVotes: number;
};

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
		</>
	);
}

function CommentEditor({
	addCommentURL,
	editorConfig,
	onAddComment,
	parentCommentId = null,
}: {
	addCommentURL: string;
	editorConfig: LiferayEditorConfig;
	onAddComment: (comment: Comment, parentId?: string) => void;
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
				}}
			/>

			<div className="my-3">
				<ClayButton
					onClick={async () => {
						if (!content) {
							return;
						}

						const response = await fetch(addCommentURL, {
							body: objectToFormData({
								body: content,
								parentCommentId,
							}),
							method: 'POST',
						});

						const comment = await response.json();

						if (comment.error) {
							openToast({
								message:
									comment.error ||
									Liferay.Language.get(
										'an-unexpected-system-error-occurred'
									),
								type: 'danger',
							});
						}
						else {
							onAddComment(comment);

							openToast({
								message: Liferay.Language.get(
									'your-comment-has-been-posted'
								),
								type: 'success',
							});

							editorRef.current?.setData('');
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
					}}
					size="sm"
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</div>
		</>
	);
}
