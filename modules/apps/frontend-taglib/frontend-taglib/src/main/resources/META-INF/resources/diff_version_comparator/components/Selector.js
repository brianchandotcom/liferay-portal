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

export default function Selector({
	label,
	selectedVersion,
	uniqueVersionLabel,
	urlSelector,
	versions
}) {
	const [active, setActive] = useState(false);

	return (
		<>
			{!versions.length ? (
				label
			) : (
				<div>
					<ClayDropDown
						active={active}
						onActiveChange={newVal => setActive(newVal)}
						trigger={
							<ClayButton displayType="unstyled">
								<span className="management-bar-item-title">
									{label}
								</span>

								<ClayIcon symbol="caret-double-l" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{versions.map(version => (
								<ClayDropDown.Item
									href={version[urlSelector]}
									key={version.version}
								>
									{version.label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			)}

			{selectedVersion === '0.0' && (
				<h6 className="text-default">{uniqueVersionLabel}</h6>
			)}
		</>
	);
}
