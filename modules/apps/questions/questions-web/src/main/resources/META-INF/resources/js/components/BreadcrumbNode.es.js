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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import {AppContext} from '../AppContext.es';
import Link from './Link.es';

export default ({
	hasDropdown = false,
	isEllipsis = false,
	isFirstNode = false,
	section = {},
	ui,
}) => {
	const context = useContext(AppContext);
	const [active, setActive] = useState(false);

	return (
		<>
			<li className="breadcrumb-item breadcrumb-text-truncate mr-0">
				{hasDropdown &&
				section.subSections &&
				section.subSections.length > 0 ? (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="c-p-0 questions-breadcrumb-unstyled text-truncate"
								displayType="unstyled"
							>
								{isEllipsis ? (
									<ClayIcon symbol="ellipsis-h" />
								) : (
									<Link
										className="breadcrumb-item breadcrumb-text-truncate questions-breadcrumb-item"
										onClick={() => {
											setActive(false);
										}}
										to={`/questions/${
											context.useTopicNamesInURL
												? section.title
												: section.id
										}`}
									>
										{ui || section.title}
									</Link>
								)}
								<ClayIcon symbol="caret-bottom-l" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Group>
								{section.subSections.map((section, i) => (
									<Link
										className="text-decoration-none"
										key={i}
										onClick={() => {
											setActive(false);
										}}
										to={`/questions/${
											context.useTopicNamesInURL
												? section.title
												: section.id
										}`}
									>
										<ClayDropDown.Item key={i}>
											{section.title}
										</ClayDropDown.Item>
									</Link>
								))}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				) : context.showCardsForTopicNavigation && isFirstNode ? (
					<Link
						className="breadcrumb-item questions-breadcrumb-unstyled"
						to={'/questions'}
					>
						{ui || section.title}
					</Link>
				) : (
					ui || section.title
				)}
			</li>
		</>
	);
};
