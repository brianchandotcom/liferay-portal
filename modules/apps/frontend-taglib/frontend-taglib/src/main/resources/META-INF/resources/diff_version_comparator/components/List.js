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

import ClayAlert from '@clayui/alert';
import ClayList from '@clayui/list';
import React from 'react';

export default function List({onChange, selected, versions}) {
	return (
		<>
			{versions.length ? (
				<ClayList>
					{versions.map(version => {
						return (
							<ClayList.Item
								active={
									selected &&
									selected.version === version.version
								}
								className="version-item"
								data-version={version.version}
								flex
								key={version.version}
								onClick={onChange}
							>
								<ClayList.ItemField>
									<div className="user-icon user-icon-color-1 user-icon-default">
										<span>{version.userInitials}</span>
									</div>
								</ClayList.ItemField>

								<ClayList.ItemField expand>
									<ClayList.ItemText>
										{version.displayDate}
									</ClayList.ItemText>
									<ClayList.ItemTitle>
										{version.label}
									</ClayList.ItemTitle>
									<ClayList.ItemText>
										{version.userName}
									</ClayList.ItemText>
								</ClayList.ItemField>
							</ClayList.Item>
						);
					})}
				</ClayList>
			) : null}

			{!versions.length && (
				<ClayAlert displayType="info">
					{Liferay.Language.get('there-are-no-results')}
				</ClayAlert>
			)}
		</>
	);
}
