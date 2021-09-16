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

import SearchForm from '../../../../../common/components/SearchForm';

export default function ContentFilter({
	contentTypes,
	onChangeInput,
	onChangeSelect,
	selectedType,
}) {
	const [active, setActive] = useState(false);

	return (
		<div className="page-editor__page-contents__content-filter">
			<p className="page-editor__page-contents__content-filter__help">
				{Liferay.Language.get('content-filtering-help')}
			</p>

			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomLeft}
				className="mb-2"
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				role="listbox"
				trigger={
					<ClayButton
						className="form-control form-control-select form-control-sm text-left"
						displayType="unstyled"
						small
						type="button"
					>
						<span>{selectedType}</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{contentTypes?.map((type) => (
						<React.Fragment key={type}>
							<ClayDropDown.Item
								onClick={() => {
									onChangeSelect(type);
									setActive(false);
								}}
								symbolRight={
									selectedType === type ? 'check' : undefined
								}
							>
								{type}
							</ClayDropDown.Item>
						</React.Fragment>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			<SearchForm onChange={onChangeInput} />
		</div>
	);
}
