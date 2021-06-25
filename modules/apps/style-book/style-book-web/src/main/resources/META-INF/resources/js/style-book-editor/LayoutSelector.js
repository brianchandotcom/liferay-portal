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
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import LayoutsTree from './LayoutsTree';
import {StyleBookContext} from './StyleBookContext';
import {useId} from './useId';

export default function LayoutSelector() {
	const [active, setActive] = useState(false);
	const [showPrivateLayouts, setShowPrivateLayouts] = useState(false);
	const {previewLayout} = useContext(StyleBookContext);

	const id = useId();

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomRight}
			menuElementAttrs={{
				className: 'style-book-editor__page-selector',
				containerProps: {
					className: 'cadmin',
				},
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="ml-2 pl-2 pr-1"
					displayType="unstyled"
					small
					symbol="time"
				>
					{previewLayout?.layoutName}
					<ClayIcon className="mt-0" symbol="caret-bottom-l" />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<div className="style-book-editor__page-type-selector">
					<ClayForm.Group small>
						<label className="sr-only" htmlFor={id}>
							{Liferay.Language.get('page-type-selector')}
						</label>
						<ClaySelectWithOption
							id={id}
							onChange={(event) =>
								setShowPrivateLayouts(
									event.target.value === 'private-pages'
								)
							}
							options={[
								{
									label: Liferay.Language.get('public-pages'),
									value: 'public-pages',
								},
								{
									label: Liferay.Language.get(
										'private-pages'
									),
									value: 'private-pages',
								},
							]}
						/>
					</ClayForm.Group>
				</div>

				<LayoutsTree showPrivateLayouts={showPrivateLayouts} />
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
