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
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {openSelectionModal, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default function GroupLabels({
	itemSelectorURL,
	portletNamespace,
	target,
}) {
	const [groupIds, setGroupIds] = useState([]);
	const [groupNames, setGroupNames] = useState([]);

	useEffect(() => {
		setGroupIds(
			document[`${portletNamespace}fm`][
				`${portletNamespace}groupIds${target}`
			].value
				.split(',')
				.filter((groupId) => !!groupId)
		);
		setGroupNames(
			document[`${portletNamespace}fm`][
				`${portletNamespace}groupNames${target}`
			].value
				.split('@@')
				.filter((name) => !!name)
		);
	}, [portletNamespace, target]);

	useEffect(() => {
		document[`${portletNamespace}fm`][
			`${portletNamespace}groupIds${target}`
		].value = groupIds.join(',');
		document[`${portletNamespace}fm`][
			`${portletNamespace}groupNames${target}`
		].value = groupNames.join('@@');
	}, [groupIds, groupNames, portletNamespace, target]);

	return (
		<>
			<span className="permission-scopes">
				{groupNames.length === 0 ? (
					<span>
						{Liferay.Language.get('all-sites-and-asset-libraries')}
					</span>
				) : (
					groupNames.map((name, i) => (
						<ClayLabel
							closeButtonProps={{
								onClick: () => {
									setGroupNames(
										groupNames.filter(
											(name) => name !== groupNames[i]
										)
									);
									setGroupIds(
										groupIds.filter(
											(id) => id !== groupIds[i]
										)
									);
								},
							}}
							key={i}
							large
						>
							{name}
						</ClayLabel>
					))
				)}
			</span>

			<ClayButton
				displayType="unstyled"
				onClick={() => {
					openSelectionModal({
						onSelect: (event) => {
							if (event.grouptarget === target) {
								setGroupIds((groupIds) =>
									groupIds.indexOf(event.groupid) === -1
										? [...groupIds, event.groupid]
										: groupIds
								);
								setGroupNames((groupNames) =>
									groupNames.indexOf(
										event.groupdescriptivename
									) === -1
										? [
												...groupNames,
												event.groupdescriptivename,
										  ]
										: groupNames
								);
							}
						},
						selectEventName: `${portletNamespace}selectGroup${target}`,
						selectedData: groupIds,
						title: sub(
							Liferay.Language.get('select-x'),
							Liferay.Language.get('site')
						),
						url: itemSelectorURL,
					});
				}}
			>
				<ClayIcon symbol="pencil" /> {Liferay.Language.get('change')}
			</ClayButton>
		</>
	);
}
