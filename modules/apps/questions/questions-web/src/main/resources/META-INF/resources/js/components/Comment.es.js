/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classnames from 'classnames';
import {useMutation} from 'graphql-hooks';
import React, {useContext, useState} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import FlagsContainer from '../pages/questions/components/FlagsContainer';
import {deleteMessageQuery} from '../utils/client.es';
import {fromNow} from '../utils/time.es';
import ArticleBodyRenderer from './ArticleBodyRenderer.es';
import EditedTimestamp from './EditedTimestamp.es';
import Modal from './Modal.es';

export default withRouter(
	({
		comment,
		commentChange,
		display,
		editable = true,
		match: {url},
		showSignature,
		styledItems = false,
	}) => {
		const context = useContext(AppContext);
		const [showDeleteCommentModal, setShowDeleteCommentModal] = useState(
			false
		);

		const [deleteMessage] = useMutation(deleteMessageQuery);

		const elapsedTime = fromNow(comment.dateCreated);

		return (
			<div className="c-my-3 pl-3 questions-reply row">
				<div
					className={classnames({
						'align-items-md-center col-2 col-md-1 d-flex justify-content-end justify-content-md-center': !styledItems,
						'pt-1 d-flex justify-content-end justify-content-md-center': styledItems,
					})}
				>
					<ClayIcon
						aria-label={Liferay.Language.get('reply')}
						className="c-mt-3 c-mt-md-0 questions-reply-icon text-secondary"
						symbol="reply"
					/>
				</div>

				<div className="col-10 col-lg-11">
					<div
						className={classnames({
							'd-flex flex-column': styledItems,
						})}
					>
						<span className="d-flex text-secondary">
							<EditedTimestamp
								creator={comment.creator.name}
								dateCreated={comment.dateCreated}
								dateModified={comment.dateModified}
								operationText={Liferay.Language.get('replied')}
								showSignature={showSignature}
							/>

							{comment.modified && (
								<span className="question-edited">
									({Liferay.Language.get('edited')})
								</span>
							)}
						</span>

						{comment.status && comment.status !== 'approved' && (
							<span className="c-ml-2 text-secondary">
								<ClayLabel displayType="info">
									{comment.status}
								</ClayLabel>
							</span>
						)}

						<ArticleBodyRenderer
							{...comment}
							companyName={context.companyName}
							elapsedTime={elapsedTime}
							showSignature={showSignature}
							signature={comment.creator && comment.creator.name}
						/>
					</div>

					{editable && comment.actions.delete && (
						<>
							<div className="font-weight-bold text-secondary">
								<ClayButton
									aria-label={Liferay.Language.get('delete')}
									className="btn-sm c-mr-2 c-px-2 c-py-1"
									displayType="secondary"
									onClick={() => {
										setShowDeleteCommentModal(true);
									}}
								>
									{Liferay.Language.get('delete')}
								</ClayButton>

								{display?.flags && (
									<FlagsContainer
										btnProps={{
											className:
												'c-mr-2 c-px-2 c-py-1 btn btn-secondary',
											small: true,
										}}
										content={comment}
										context={context}
										onlyIcon={false}
										showIcon={false}
									/>
								)}

								<ClayButton
									aria-label={Liferay.Language.get('edit')}
									className="btn-sm c-px-2 c-py-1"
									displayType="secondary"
								>
									<Link
										className="text-reset"
										to={`${url}/answers/${comment.friendlyUrlPath}/edit`}
									>
										{Liferay.Language.get('edit')}
									</Link>
								</ClayButton>
							</div>

							<Modal
								body={Liferay.Language.get(
									'do-you-want-to-delete–this-comment'
								)}
								callback={() => {
									deleteMessage({
										variables: {
											messageBoardMessageId: comment.id,
										},
									}).then(() => {
										if (commentChange) {
											commentChange(comment);
										}
									});
								}}
								onClose={() => {
									setShowDeleteCommentModal(false);
								}}
								status="warning"
								textPrimaryButton={Liferay.Language.get(
									'delete'
								)}
								title={Liferay.Language.get('delete-comment')}
								visible={showDeleteCommentModal}
							/>
						</>
					)}
				</div>
			</div>
		);
	}
);
