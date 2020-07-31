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

import PagesTree from './PagesTree';
import {StyleBookContext} from './StyleBookContext';

export default function PageSelector() {
	const [active, setActive] = useState(false);
	const [showPrivatePages, setShowPrivatePages] = useState(false);
	const {previewPage} = useContext(StyleBookContext);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomRight}
			menuElementAttrs={{
				className: 'style-book-editor__page-selector',
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="ml-1 px-2"
					displayType="unstyled"
					small
					symbol="time"
				>
					{previewPage?.pageName}
					<ClayIcon className="mt-0" symbol={'caret-bottom-l'} />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<div className="style-book-editor__page-type-selector">
					<ClayForm.Group small>
						<ClaySelectWithOption
							onChange={(event) =>
								setShowPrivatePages(
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

				<PagesTree showPrivatePages={showPrivatePages} />
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
