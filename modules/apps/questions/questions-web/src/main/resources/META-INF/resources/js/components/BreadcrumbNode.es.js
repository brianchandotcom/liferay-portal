/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	return context.useTopicNamesInURL ? section.friendlyUrlPath : section.id;
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
								<ClayIcon
									aria-label={Liferay.Language.get('options')}
									symbol="ellipsis-h"
								/>
							) : (
								<span className="breadcrumb-item breadcrumb-text-truncate questions-breadcrumb-item">
									{ui || section.title}
								</span>
							)}

							<ClayIcon aria-label="Icon" symbol="caret-bottom" />
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
