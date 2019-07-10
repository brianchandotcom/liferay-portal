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

import React from 'react';
import PropTypes from 'prop-types';

import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import Textarea from '../../common/Textarea.es';

const FragmentComments = props => {
	const newCommentId = `${props.portletNamespace}newCommentId`;

	return (
		<div
			data-fragments-editor-item-id={props.fragmentEntryLinkId}
			data-fragments-editor-item-type={
				FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			}
		>
			<h2 className='mb-2 sidebar-dt text-secondary'>
				{props.fragmentEntryLinkName}
			</h2>

			<form>
				<Textarea
					id={newCommentId}
					label={Liferay.Language.get('new-comment')}
					placeholder={Liferay.Language.get('type-your-comment-here')}
					showLabel={false}
				/>
			</form>
		</div>
	);
};

FragmentComments.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
	fragmentEntryLinkName: PropTypes.string,
	portletNamespace: PropTypes.string
};

export {FragmentComments};
export default FragmentComments;
