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

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React from 'react';

import {FragmentPanel, WidgetPanel} from './FragmentsSidebar';

export default function SearchResultsPanel({filteredElements}) {
	const {fragments, widgets} = filteredElements;

	return (
		<>
			{fragments.fragmentEntries.length === 0 &&
			widgets.portlets.length === 0 ? (
				<ClayAlert
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get(
						'there-are-no-fragments-or-widgets-on-this-page'
					)}
				</ClayAlert>
			) : (
				<>
					{fragments.fragmentEntries.length > 0 && (
						<>
							<div className="filter-subtitle">
								{Liferay.Language.get('fragments')}
							</div>
							<FragmentPanel collection={fragments} />
						</>
					)}
					{widgets.portlets.length > 0 && (
						<>
							<div className="filter-subtitle">
								{Liferay.Language.get('widgets')}
							</div>
							<WidgetPanel category={widgets} />
						</>
					)}
				</>
			)}
		</>
	);
}

SearchResultsPanel.proptypes = {
	filteredElements: PropTypes.object.isRequired,
};
