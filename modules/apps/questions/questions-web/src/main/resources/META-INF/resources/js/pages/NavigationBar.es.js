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
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import React, {useContext, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';

export default withRouter(({history}) => {
	const navigate = href => {
		history.push('/' + href);
		setActive(href);
	};

	const context = useContext(AppContext);

	const [active, setActive] = useState('questions');

	return (
		<div className="autofit-padded autofit-row navigation-bar">
			<div className="autofit-col autofit-col-expand">
				<ClayNavigationBar triggerLabel="Questions">
					<ClayNavigationBar.Item
						active={active === 'questions'}
						onClick={() => navigate('questions')}
					>
						<ClayLink className="nav-link" displayType="unstyled">
							{Liferay.Language.get('questions')}
						</ClayLink>
					</ClayNavigationBar.Item>
					<ClayNavigationBar.Item
						active={active === 'tags'}
						onClick={() => navigate('tags')}
					>
						<ClayLink className="nav-link" displayType="unstyled">
							{Liferay.Language.get('tags')}
						</ClayLink>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>
			</div>
			<div className="autofit-col">
				{context.canCreateThread && (
					<ClayButton
						displayType="primary"
						onClick={() => navigate('questions/new')}
					>
						{Liferay.Language.get('ask-question')}
					</ClayButton>
				)}
			</div>
		</div>
	);
});
