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

import React, {useContext} from 'react';

import lang from '../../../utils/lang.es';
import Button from '../../button/Button.es';
import SearchContext from './SearchContext.es';

export default ({isLoading, totalCount}) => {
	const [{keywords}, dispatch] = useContext(SearchContext);
	const invisible = isLoading ? 'invisible' : '';

	return (
		<>
			{keywords && (
				<nav
					className={`tbar tbar-inline-xs-down subnav-tbar subnav-tbar-primary ${invisible}`}
				>
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
									<Button
										className="component-link tbar-link"
										displayType="unstyled"
										onClick={() =>
											dispatch({
												keywords: '',
												type: 'SEARCH'
											})
										}
									>
										Clear
									</Button>
								</div>
							</li>
						</ul>
					</div>
				</nav>
			)}
		</>
	);
};
