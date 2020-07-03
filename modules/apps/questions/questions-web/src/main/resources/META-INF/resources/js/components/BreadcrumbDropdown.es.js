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

export default ({createLink = true, section, ...props}) => {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="c-p-0 questions-breadcrumb-unstyled text-truncate"
					displayType="unstyled"
				>
					{createLink ? (
						<CreateLink />
					) : (
						<ClayIcon symbol="ellipsis-h" />
					)}
					<ClayIcon symbol="caret-bottom-l" />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Group>
					{section.subCategories.map((section, i) => (
						<Link
							className="text-decoration-none"
							key={i}
							onClick={() => {
								setActive(false);
							}}
							to={'/questions/' + section.title}
						>
							<ClayDropDown.Item key={i}>
								{section.title}
							</ClayDropDown.Item>
						</Link>
					))}
				</ClayDropDown.Group>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);

	function CreateLink() {
		return (
			<Link
				className={props.className}
				onClick={() => {
					setActive(false);
				}}
				to={'/questions/' + section.title}
			>
				{section.title}
			</Link>
		);
	}
};
