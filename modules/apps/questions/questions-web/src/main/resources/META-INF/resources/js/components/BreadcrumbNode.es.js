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
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import {AppContext} from '../AppContext.es';
import Link from './Link.es';

const getSectionURL = (context, section) => {
	return context.useTopicNamesInURL ? section.title : section.id;
};

const BreadcrumbItem = ({
	context,
	section,
	showDropdownSections,
	showTopicNavigation,
	ui,
}) => {
	const sections = context.sections;

	const redirectTo = showTopicNavigation
		? '/questions'
		: `/questions/${getSectionURL(context, section)}`;

	const Component = showDropdownSections
		? (props) => <div {...props} />
		: Link;

	const BreadcrumbElement = ({showCaret}) => (
		<Component
			className="breadcrumb-item questions-breadcrumb-unstyled"
			to={redirectTo}
		>
			{ui || section.title}

			{showCaret && <ClayIcon className="ml-2" symbol="caret-bottom" />}
		</Component>
	);

	if (showDropdownSections) {
		return (
			<ClayDropDown
				trigger={
					<div>
						<BreadcrumbElement showCaret />
					</div>
				}
			>
				<ClayDropDown.ItemList>
					{sections.map((_section, index) => (
						<Link
							className={classNames('dropdown-item', {
								'font-weight-bold text-dark':
									section.title === _section.title,
							})}
							key={index}
							to={`/questions/${getSectionURL(
								context,
								_section
							)}`}
						>
							{_section.title}
						</Link>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		);
	}

	return <BreadcrumbElement />;
};

export default function BreadcrumbNode({
	hasDropdown = false,
	isEllipsis = false,
	showDropdownSections,
	isFirstNode = false,
	section = {},
	ui,
}) {
	const context = useContext(AppContext);
	const [active, setActive] = useState(false);

	return (
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
									to={`/questions/${getSectionURL(
										context,
										section
									)}`}
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
									to={`/questions/${getSectionURL(
										context,
										section
									)}`}
								>
									<ClayDropDown.Item key={i}>
										{section.title}
									</ClayDropDown.Item>
								</Link>
							))}
						</ClayDropDown.Group>
					</ClayDropDown.ItemList>
				</ClayDropDown>
			) : (
				<BreadcrumbItem
					context={context}
					section={section}
					showDropdownSections={showDropdownSections}
					showTopicNavigation={
						context.showCardsForTopicNavigation && isFirstNode
					}
					ui={ui}
				/>
			)}
		</li>
	);
}
