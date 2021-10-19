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
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import {LAYOUT_TYPES} from './constants/layoutTypes';

const LAYOUT_TYPES_OPTIONS = [
	{
		label: Liferay.Language.get('masters'),
		type: LAYOUT_TYPES.master,
	},
	{
		label: Liferay.Language.get('pages'),
		type: LAYOUT_TYPES.page,
	},
	{
		label: Liferay.Language.get('page-templates'),
		type: LAYOUT_TYPES.pageTemplate,
	},
	{
		label: Liferay.Language.get('display-page-templates'),
		type: LAYOUT_TYPES.displayPageTemplate,
	},
];

export default function PreviewSelector() {
	const [layoutType, setLayoutType] = useState(LAYOUT_TYPES.page);

	return (
		<>
			<LayoutTypeSelector
				layoutType={layoutType}
				setLayoutType={setLayoutType}
			/>

			<LayoutSelector />
		</>
	);
}

function LayoutTypeSelector({layoutType, setLayoutType}) {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomLeft}
			menuElementAttrs={{
				containerProps: {
					className: 'cadmin',
				},
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="form-control-select ml-3 style-book-editor__preview-selector text-left"
					displayType="secondary"
					small
					type="button"
				>
					<span>
						{
							LAYOUT_TYPES_OPTIONS.find(
								(option) => option.type === layoutType
							).label
						}
					</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{LAYOUT_TYPES_OPTIONS.map(({label, type}) => (
					<ClayDropDown.Item
						key={type}
						onClick={() => {
							setActive(false);
							setLayoutType(type);
						}}
					>
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

function LayoutSelector() {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomLeft}
			menuElementAttrs={{
				containerProps: {
					className: 'cadmin',
				},
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="form-control-select ml-3 style-book-editor__preview-selector text-left"
					displayType="secondary"
					small
					type="button"
				>
					<span>Page name</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList></ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
