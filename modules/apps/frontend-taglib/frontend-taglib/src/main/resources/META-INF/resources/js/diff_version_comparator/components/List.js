/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayList from '@clayui/list';
import React from 'react';

export default function List({onChange, selected, versions}) {
	return (
		<>
			{versions.length ? (
				<ClayList>
					{versions.map((version) => {
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
									<div className="sticker sticker-secondary sticker-user-icon user-icon-color-0">
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
