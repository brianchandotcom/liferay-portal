/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

import {getLocalizedText} from '../utils/utils';

export default function PaneOrgInfo(props) {
	const {childrenNo, colorIdentifier, orgName} = props;

	return (
		<div className="pane-org-info">
			<div
				className="org-color-identifier"
				style={{backgroundColor: colorIdentifier}}
			></div>
			<div className="org-data">
				<p>{orgName}</p>
				<p>
					{childrenNo
						? `${childrenNo} ${getLocalizedText(
								'suborganizations'
						  )}`
						: `${getLocalizedText('suborganization')}`}
				</p>
			</div>
			<div className="org-actions" role="button" tabIndex="1">
				<p style={{display: 'none'}}>&sdot;&sdot;&sdot;</p>
			</div>
		</div>
	);
}
