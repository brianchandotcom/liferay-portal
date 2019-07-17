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

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {addFragmentEntryLinkComment} from '../../../utils/FragmentsEditorFetchUtils.es';
import {addFragmentEntryLinkCommentAction} from '../../../actions/addFragmentEntryLinkComment.es';
import Button from '../../common/Button.es';
import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';
import InvisibleFieldset from '../../common/InvisibleFieldset.es';
import Textarea from '../../common/Textarea.es';

const AddCommentForm = props => {
	const newCommentId = `${props.portletNamespace}newComment`;

	const [addingComment, setAddingComment] = useState(false);
	const [showButtons, setShowButtons] = useState(false);
	const [textareaContent, setTextareaContent] = useState('');

	const _handleCancelButtonClick = () => {
		setShowButtons(false);
		setTextareaContent('');
	};

	const _handleFormFocus = () => {
		setShowButtons(true);
	};

	const _handleCommentButtonClick = () => {
		setAddingComment(true);

		addFragmentEntryLinkComment(props.fragmentEntryLinkId, textareaContent)
			.then(response => response.json())
			.then(comment => {
				props.addComment(props.fragmentEntryLinkId, comment);

				setAddingComment(false);
				setShowButtons(false);
				setTextareaContent('');
			});
	};

	const _handleTextareaChange = event => {
		if (event.target) {
			setTextareaContent(event.target.value);
		}
	};

	return (
		<form onFocus={_handleFormFocus}>
			<InvisibleFieldset disabled={addingComment}>
				<div className="form-group form-group-sm">
					<label className="sr-only" htmlFor={newCommentId}>
						{Liferay.Language.get('add-comment')}
					</label>

					<Textarea
						id={newCommentId}
						onChange={_handleTextareaChange}
						placeholder={Liferay.Language.get(
							'type-your-comment-here'
						)}
						value={textareaContent}
					/>
				</div>

				{showButtons && (
					<ClayButton.Group spaced>
						<Button
							disabled={!textareaContent}
							displayType="primary"
							loading={addingComment}
							onClick={_handleCommentButtonClick}
							small
						>
							{Liferay.Language.get('comment')}
						</Button>

						<Button
							displayType="secondary"
							onClick={_handleCancelButtonClick}
							small
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</Button>
					</ClayButton.Group>
				)}
			</InvisibleFieldset>
		</form>
	);
};

AddCommentForm.propTypes = {
	addComment: PropTypes.func,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string
};

const ConnectedAddCommentForm = getConnectedReactComponent(
	state => ({
		portletNamespace: state.portletNamespace
	}),
	dispatch => ({
		addComment: (fragmentEntryLinkId, comment) =>
			dispatch(
				addFragmentEntryLinkCommentAction(fragmentEntryLinkId, comment)
			)
	})
)(AddCommentForm);

export {ConnectedAddCommentForm, AddCommentForm};
export default ConnectedAddCommentForm;
