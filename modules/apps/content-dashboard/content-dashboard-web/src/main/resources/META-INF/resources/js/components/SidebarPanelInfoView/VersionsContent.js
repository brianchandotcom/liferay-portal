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

import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

const VersionsContent = ({allVersions, formatDate, languageTag = 'en'}) => {
	return (
		<>
			<ul className="list-group sidebar-list-group">
				{allVersions.map((version) => (
					<li
						className="list-group-item list-group-item-flex"
						key={version.version}
					>
						<ClayLayout.ContentCol expand>
							<div className="list-group-title">
								{Liferay.Language.get('version') + ' '}

								{version.version}
							</div>

							<div className="list-group-subtitle">
								{sub(Liferay.Language.get('x-by-x'), [
									formatDate(version.createDate, languageTag),
									version.userName,
								])}
							</div>

							<div className="list-group-subtext">
								{version.changeLog
									? version.changeLog
									: Liferay.Language.get('no-change-log')}
							</div>
						</ClayLayout.ContentCol>
					</li>
				))}
			</ul>
		</>
	);
};

VersionsContent.defaultProps = {
	languageTag: 'en-US',
};

VersionsContent.propTypes = {
	allVersions: PropTypes.array.isRequired,
	formatDate: PropTypes.func.isRequired,
};

export default VersionsContent;
