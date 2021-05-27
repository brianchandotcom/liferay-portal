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

import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {StoreStateContext} from '../context/StoreContext';

export default function IssueDetail() {
	const {selectedIssue} = useContext(StoreStateContext);

	return (
		<div className="c-p-3">
			<ClayPanel.Group className="panel-group-flush panel-group-sm">
				<HtmlPanel
					content={selectedIssue.description}
					title={Liferay.Language.get('description')}
				/>
				<HtmlPanel
					content={selectedIssue.tips}
					title={Liferay.Language.get('tips')}
				/>
			</ClayPanel.Group>
		</div>
	);
}

const HtmlPanel = ({content, title}) => (
	<ClayPanel
		collapsable
		collapseClassNames="c-mb-4 c-mt-3"
		displayTitle={title}
		displayType="unstyled"
		showCollapseIcon={true}
	>
		<ClayPanel.Body>
			<div
				className="text-secondary"
				dangerouslySetInnerHTML={{
					__html: content,
				}}
			></div>
		</ClayPanel.Body>
	</ClayPanel>
);

HtmlPanel.propTypes = {
	content: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};
