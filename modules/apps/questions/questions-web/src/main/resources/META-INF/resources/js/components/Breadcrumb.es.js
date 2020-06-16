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
import React, {useState} from 'react';

import Link from './Link.es';

export default ({section}) => {
	const [active, setActive] = useState(false);

	const getParentSubSections = () =>
		(section &&
			section.parentSection &&
			section.parentSection.messageBoardSections.items) ||
		[];

	return (
		<>
			{section && section.parentSection && (
				<ol className="breadcrumb c-m-0 c-p-0">
					<li className="breadcrumb-item breadcrumb-text-truncate">
						<strong className="breadcrumb-text-truncate text-secondary">
							{section.parentSection.title}
						</strong>
					</li>
					{!!getParentSubSections().length && (
						<li className="breadcrumb-item breadcrumb-text-truncate">
							<ClayDropDown
								active={active}
								onActiveChange={setActive}
								trigger={
									<ClayButton
										className="c-p-0 questions-breadcrumb-unstyled text-truncate"
										displayType="unstyled"
									>
										{section.id === section.parentSection.id
											? Liferay.Language.get('all')
											: section.title}
										<ClayIcon symbol="caret-bottom-l" />
									</ClayButton>
								}
							>
								<Link
									className="text-decoration-none"
									onClick={() => {
										setActive(false);
									}}
									to={`/questions/${section.parentSection.title}`}
								>
									<ClayDropDown.Item>
										{Liferay.Language.get('all')}
									</ClayDropDown.Item>
								</Link>
								<ClayDropDown.ItemList>
									<ClayDropDown.Group>
										{getParentSubSections().map(
											(item, i) => (
												<Link
													className="text-decoration-none"
													key={i}
													onClick={() => {
														setActive(false);
													}}
													to={
														'/questions/' +
														item.title
													}
												>
													<ClayDropDown.Item>
														{item.title}
													</ClayDropDown.Item>
												</Link>
											)
										)}
									</ClayDropDown.Group>
								</ClayDropDown.ItemList>
							</ClayDropDown>
						</li>
					)}
				</ol>
			)}
		</>
	);
};
