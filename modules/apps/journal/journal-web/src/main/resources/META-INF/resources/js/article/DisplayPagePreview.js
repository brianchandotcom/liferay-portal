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
import {openSelectionModal, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

export default function DisplayPagePreview({
	itemSelectorURL,
	namespace,
	sites,
	sitesCount,
}) {
	const [selectedSite, setSelectedSite] = useState();
	const [active, setActive] = useState(false);

	const items = useMemo(() => {
		return [{groupId: 0, name: Liferay.Language.get('none')}, ...sites].map(
			(site) => ({
				label: site.name,
				onClick: () => {
					setActive(false);
					setSelectedSite({groupId: site.groupId, name: site.name});
				},
			})
		);
	}, [sites]);

	return (
		<>
			<label className="control-label">
				{Liferay.Language.get('site')}
			</label>
			<ClayDropDown
				active={active}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="bg-light form-control-select text-left w-100"
						displayType="secondary"
						type="button"
					>
						<span>
							{selectedSite?.name || Liferay.Language.get('none')}
						</span>
					</ClayButton>
				}
			>
				{items.map((item) => (
					<ClayDropDown.Item key={item.label} onClick={item.onClick}>
						{item.label}
					</ClayDropDown.Item>
				))}

				{sitesCount > sites.length && (
					<>
						<ClayDropDown.Caption>
							{sub(
								Liferay.Language.get('showing-x-of-x-items'),
								sites.length,
								sitesCount
							)}
						</ClayDropDown.Caption>

						<ClayButton
							className="w-100"
							displayType="secondary"
							onClick={() => {
								openSelectionModal({
									containerProps: {
										className: 'cadmin',
									},
									onSelect(selectedItem) {
										setSelectedSite({
											groupId: selectedItem.groupid,
											name:
												selectedItem.groupdescriptivename,
										});
									},
									selectEventName: `${namespace}selectSite`,
									title: Liferay.Language.get('select-site'),
									url: itemSelectorURL,
								});
							}}
							small
							type="button"
						>
							{Liferay.Language.get('more')}
						</ClayButton>
					</>
				)}
			</ClayDropDown>
		</>
	);
}
