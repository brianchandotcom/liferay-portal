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

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {getConnectedReactComponent} from '../../../store/ConnectedComponent.es';
import Textarea from '../../common/Textarea.es';

const AddCommentForm = props => {
	const newCommentId = `${props.portletNamespace}newComment`;
	const [addingComment, setAddingComment] = useState(false);

	const _handleSubmit = () => {
		setAddingComment(true);
		setTimeout(() => setAddingComment(false), 1000);
	};

	return (
		<form disabled={addingComment} onSubmit={_handleSubmit}>
			<Textarea
				id={newCommentId}
				label={Liferay.Language.get('new-comment')}
				placeholder={Liferay.Language.get('type-your-comment-here')}
				showLabel={false}
			/>
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
