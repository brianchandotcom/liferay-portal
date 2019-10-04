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
import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';
import ReactDOM from 'react-dom';

import {discard, publish} from '../actions/index';
import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';

const {useContext} = React;

function ToolbarBody() {
	const dispatch = useContext(DispatchContext);

	const {singleSegmentsExperienceMode} = useContext(ConfigContext);

	return (
		<div className="container-fluid container-fluid-max-xl">
			<ul className="navbar-nav">Experiences</ul>

			<ul className="navbar-nav">
				<li className="nav-item">
					<ClayButton
						className="nav-btn"
						disabled
						displayType="secondary"
						onClick={() => dispatch(discard())}
						small
					>
						{singleSegmentsExperienceMode
							? Liferay.Language.get('discard-variant')
							: Liferay.Language.get('discard-draft')}
					</ClayButton>
				</li>
				<li className="nav-item">
					<ClayButton
						className="nav-btn"
						disabled
						displayType="primary"
						onClick={() => dispatch(publish())}
						small
					>
						{singleSegmentsExperienceMode
							? Liferay.Language.get('save-variant')
							: Liferay.Language.get('publish')}
					</ClayButton>
				</li>
			</ul>
		</div>
	);
}

export default function Toolbar() {
	const {toolbarId} = useContext(ConfigContext);

	const isMounted = useIsMounted();

	const container = document.getElementById(toolbarId);

	if (!isMounted()) {
		// First time here, must empty JSP-rendered markup from container.
		while (container.firstChild) {
			container.removeChild(container.firstChild);
		}
	}

	return ReactDOM.createPortal(<ToolbarBody />, container);
}
