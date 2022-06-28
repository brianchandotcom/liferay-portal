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

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext} from 'react';

import {AppContext} from '../AppContext.es';
import Link from './Link.es';

const getSectionURL = (context, section) => {
	if (section.href) {
		return section.href;
	}

	return context.useTopicNamesInURL ? section.title : section.id;
};

export default function BreadcrumbNode({
	hasDropdown = false,
	isEllipsis = false,
	isFirstNode = false,
	section = {},
	ui,
}) {
	const context = useContext(AppContext);

	return (
		<li className="breadcrumb-item breadcrumb-text-truncate mr-0">
			{hasDropdown &&
			section.subSections &&
			!!section.subSections.length ? (
				<ClayDropDown
					trigger={
						<span className="c-p-0 questions-breadcrumb-unstyled text-truncate">
							{isEllipsis ? (
								<ClayIcon symbol="ellipsis-h" />
							) : (
								<span className="breadcrumb-item breadcrumb-text-truncate questions-breadcrumb-item">
									{ui || section.title}
								</span>
							)}

							<ClayIcon symbol="caret-bottom" />
						</span>
					}
				>
					<ClayDropDown.ItemList>
						<ClayDropDown.Group>
							{section.subSections.map((subSection, index) => (
								<Link
									className={classNames(
										'dropdown-item text-decoration-none',
										{
											'font-weight-bold text-dark':
												subSection.title ===
												section.title,
										}
									)}
									key={index}
									to={`/questions/${getSectionURL(
										context,
										subSection
									)}`}
								>
									{subSection.title}
								</Link>
							))}
						</ClayDropDown.Group>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			) : context.showCardsForTopicNavigation && isFirstNode ? (
				<Link
					className="breadcrumb-item questions-breadcrumb-unstyled"
					to="/questions"
				>
					{ui || section.title}
				</Link>
			) : (
				<Link
					className="breadcrumb-item questions-breadcrumb-unstyled"
					to={`/questions/${getSectionURL(context, section)}`}
				>
					{ui || section.title}
				</Link>
			)}
		</li>
	);
}
