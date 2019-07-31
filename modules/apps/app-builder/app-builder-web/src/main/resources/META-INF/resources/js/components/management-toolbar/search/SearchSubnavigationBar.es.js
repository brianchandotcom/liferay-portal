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

import React, {Fragment} from 'react';
import lang from '../../../utils/lang.es';

export default function SearchSubnavigationBar({
	keywords,
	onSearch,
	totalCount
}) {
	const clear = () => {
		onSearch('');
	};

	return (
		<Fragment>
			{keywords !== '' && (
				<nav className="tbar tbar-inline-xs-down subnav-tbar subnav-tbar-primary">
					<div className="container-fluid container-fluid-max-xl">
						<ul className="tbar-nav tbar-nav-wrap">
							<li className="tbar-item tbar-item-expand">
								<div className="tbar-section">
									<span className="component-text text-truncate-inline">
										<span className="text-truncate">
											{lang.sub(
												Liferay.Language.get(
													'x-results-for-x'
												),
												[totalCount, keywords]
											)}
										</span>
									</span>
								</div>
							</li>
							<li className="tbar-item">
								<div className="tbar-section">
									<a
										className=" component-link tbar-link"
										href="javascript:;"
										onClick={clear}
									>
										Clear
									</a>
								</div>
							</li>
						</ul>
					</div>
				</nav>
			)}
		</Fragment>
	);
}
