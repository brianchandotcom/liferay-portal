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
import React from 'react';
import Button from '../../common/Button.es';
import InvisibleFieldset from '../../common/InvisibleFieldset.es';
import Editor from '../../common/Editor.es';

const CommentForm = props => (
	<form onFocus={props.onFormFocus}>
		<InvisibleFieldset disabled={props.loading}>
			<div className="form-group form-group-sm">
				<label className="sr-only" htmlFor={props.id}>
					{Liferay.Language.get('add-comment')}
				</label>

				<Editor
					autoFocus={props.autoFocus}
					id={props.id}
					initialValue={props.textareaContent}
					onChange={props.onTextareaChange}
					placeholder={Liferay.Language.get('type-your-comment-here')}
				/>
			</div>

			{props.showButtons && (
				<ClayButton.Group className="mb-3" spaced>
					<Button
						disabled={!props.textareaContent}
						displayType="primary"
						loading={props.loading}
						onClick={props.onSubmitButtonClick}
						small
					>
						{props.submitButtonLabel}
					</Button>

					<Button
						displayType="secondary"
						onClick={props.onCancelButtonClick}
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

CommentForm.defaultProps = {
	autoFocus: false,
	loading: false,
	onFormFocus: () => {},
	showButtons: false
};

CommentForm.propTypes = {
	autoFocus: PropTypes.bool,
	id: PropTypes.string,
	loading: PropTypes.bool,
	onCancelButtonClick: PropTypes.func.isRequired,
	onFormFocus: PropTypes.func,
	onSubmitButtonClick: PropTypes.func.isRequired,
	onTextareaChange: PropTypes.func.isRequired,
	showButtons: PropTypes.bool,
	submitButtonLabel: PropTypes.string.isRequired,
	textareaContent: PropTypes.string.isRequired
};

export {CommentForm};
export default CommentForm;
