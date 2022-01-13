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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React, {useMemo, useState} from 'react';

export default function IconConfiguration({icons}) {
	const [searchQuery, setSearchQuery] = useState('');

	const iconPackNames = Object.keys(icons);

	const filteredIcons = useMemo(() => {
		return iconPackNames.reduce((acc, packName) => {
			return {
				...acc,
				[packName]: {
					...icons[packName],
					icons: icons[packName].icons.filter((icon) =>
						icon.name
							.toLowerCase()
							.includes(searchQuery.toLocaleLowerCase())
					),
				},
			};
		}, {});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [iconPackNames, icons, searchQuery]);

	const referenceTime = useMemo(
		() => new Date().getTime(),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[icons]
	);

	return (
		<ClayLayout.Sheet>
			<ClayLayout.ContentRow className="mb-5" containerElement="h2">
				<ClayLayout.ContentCol containerElement="span" expand>
					{Liferay.Language.get('frontend-icons-configuration-name')}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<h4>{Liferay.Language.get('icon-packs')}</h4>

			<label className="form-control-label">
				<span className="form-control-label-text">
					{Liferay.Language.get('search-icons')}
				</span>

				<ClayInput
					onChange={(event) => setSearchQuery(event.target.value)}
					placeholder={Liferay.Language.get('search-icons')}
					type="text"
					value={searchQuery}
				/>
			</label>

			<ClayPanel.Group className="mt-4">
				{iconPackNames.map((iconPackName) => (
					<div className="d-flex" key={iconPackName}>
						<ClayPanel
							collapsable
							displayTitle={`${iconPackName} (${filteredIcons[iconPackName]?.icons.length})`}
							displayType="secondary"
							showCollapseIcon={true}
							style={{flex: 1}}
						>
							<ClayPanel.Body className="list-group-card">
								{filteredIcons[iconPackName].editable && (
									<ClayDropDownWithItems
										items={[
											{
												label:
													'Add Icons Pack from spritemap',
												onClick: () => {},
											},
											{
												label:
													'Add Icons Pack from existing icons',
												onClick: () => {},
											},
										]}
										trigger={
											<ClayButton
												className="mt-2"
												displayType="secondary"
												small
											>
												Add Icons
											</ClayButton>
										}
									/>
								)}

								<ul className="list-group">
									{filteredIcons[iconPackName].icons
										.sort()
										.map((icon) => (
											<li
												className="list-group-card-item w-25"
												key={icon.name}
											>
												<ClayButton
													displayType={null}
													onClick={() => {}}
												>
													<ClayIcon
														spritemap={`/o/icons/${iconPackName}.svg?${referenceTime}`}
														symbol={icon.name}
													/>

													<span className="list-group-card-item-text">
														{icon.name}
													</span>
												</ClayButton>
											</li>
										))}

									{!filteredIcons[iconPackName].icons
										.length && (
										<li className="list-group-card-item w-100">
											{Liferay.Language.get(
												'no-results-found'
											)}
										</li>
									)}
								</ul>
							</ClayPanel.Body>
						</ClayPanel>

						<ClayButtonWithIcon
							borderless
							disabled={!filteredIcons[iconPackName].editable}
							displayType="secondary ml-2 mt-1"
							onClick={() => {}}
							small
							symbol="times-circle"
						/>
					</div>
				))}
			</ClayPanel.Group>
		</ClayLayout.Sheet>
	);
}
