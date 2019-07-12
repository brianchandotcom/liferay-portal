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

/* eslint no-unused-vars: "warn" */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';

const AddCommentForm = props => {
	const newCommentId = `${props.portletNamespace}newComment`;

	const [addingComment, setAddingComment] = useState(false);
	const [showButtons, setShowButtons] = useState(false);
	const textareaElement = useRef(null);

	const _handleCancelButtonClick = () => {
		setShowButtons(false);
		textareaElement.current.value = '';
	};

	const _handleFormFocus = () => {
		setShowButtons(true);
	};

	const _handleCommentButtonClick = () => {
		setAddingComment(true);

		setTimeout(() => {
			setAddingComment(false);
			setShowButtons(false);
			textareaElement.current.value = '';
		}, 1000);
	};

	return (
		<form onFocus={_handleFormFocus}>
			<div className='form-group'>
				<label className='sr-only' htmlFor={newCommentId}>
					{Liferay.Language.get('add-comment')}
				</label>

				<textarea
					className='form-control'
					disabled={addingComment}
					id={newCommentId}
					placeholder={Liferay.Language.get('type-your-comment-here')}
					ref={textareaElement}
				/>
			</div>

			{showButtons && (
				<>
					<ClayButton
						disabled={addingComment}
						displayType='primary'
						onClick={_handleCommentButtonClick}
						small
					>
						{Liferay.Language.get('comment')}
					</ClayButton>{' '}
					<ClayButton
						disabled={addingComment}
						displayType='secondary'
						onClick={_handleCancelButtonClick}
						small
						type='button'
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</>
			)}
		</form>
	);
};

AddCommentForm.propTypes = {
	portletNamespace: PropTypes.string
};

const ConnectedAddCommentForm = getConnectedReactComponent(
	state => ({
		portletNamespace: state.portletNamespace
	}),
	() => ({})
)(AddCommentForm);

export {ConnectedAddCommentForm, AddCommentForm};
export default ConnectedAddCommentForm;
