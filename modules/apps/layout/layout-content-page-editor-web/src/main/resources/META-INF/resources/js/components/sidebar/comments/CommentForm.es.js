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
import React, {useEffect, useRef} from 'react';
import {EventHandler} from 'metal-events';
import Button from '../../common/Button.es';
import InvisibleFieldset from '../../common/InvisibleFieldset.es';
import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';

const NEW_COMMENT_ID = 'pageEditorNewCommentId';

const Editor = getConnectedReactComponent(
	state => ({
		defaultEditorConfiguration:
			state.defaultEditorConfigurations.text.editorConfig
	}),
	() => {}
)(props => {
	const wrapper = useRef(null);
	useEffect(() => {
		const editor = AlloyEditor.editable(wrapper.current, {
			...props.defaultEditorConfiguration,
			enterMode: 1
		});

		const editorEventHandler = new EventHandler();
		const nativeEditor = editor.get('nativeEditor');

		nativeEditor.setData(props.value);

		editorEventHandler.add(
			nativeEditor.on('change', () =>
				props.onChange(nativeEditor.getData())
			)
		);

		editorEventHandler.add(
			nativeEditor.on('actionPerformed', () =>
				props.onChange(nativeEditor.getData())
			)
		);

		return () => {
			editorEventHandler.removeAllListeners();
			editorEventHandler.dispose();
			editor.destroy();
		};
	}, []);

	return (
		<div className="alloy-editor-container" id={props.id}>
			<div
				className="alloy-editor alloy-editor-placeholder form-control"
				contentEditable={false}
				data-placeholder={props.placeholder}
				data-required={false}
				id={props.id}
				name={props.id}
				ref={wrapper}
			/>
		</div>
	);
});

const CommentForm = props => (
	<form onFocus={props.onFormFocus}>
		<InvisibleFieldset disabled={props.loading}>
			<div className="form-group form-group-sm">
				<label className="sr-only" htmlFor={NEW_COMMENT_ID}>
					{Liferay.Language.get('add-comment')}
				</label>

				<Editor
					id={NEW_COMMENT_ID}
					onChange={props.onTextareaChange}
					placeholder={Liferay.Language.get('type-your-comment-here')}
					value={props.textareaContent}
				/>
			</div>

			{props.showButtons && (
				<ClayButton.Group spaced>
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
