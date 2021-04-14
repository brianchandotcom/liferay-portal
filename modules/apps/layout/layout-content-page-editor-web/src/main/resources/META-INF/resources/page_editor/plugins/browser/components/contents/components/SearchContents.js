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

const ALL_CONTENT_LABEL = Liferay.Language.get('all-content');

export default function SearchContents() {
	const [active, setActive] = useState(false);
	const [selectedOption, setSelectedOption] = useState(ALL_CONTENT_LABEL);

	return (
		<div className="page-editor__page-contents__content-filter">
			<p className="page-editor__page-contents__content-filter__help">
				{Liferay.Language.get('content-filtering-help')}
			</p>

			<SearchForm className="mb-2" />

			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomLeft}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="form-control form-control-select form-control-sm text-left"
						displayType="unstyled"
						small
						type="button"
					>
						<span>{selectedOption}</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Item
						onClick={() => setSelectedOption(ALL_CONTENT_LABEL)}
						symbolRight={
							selectedOption === ALL_CONTENT_LABEL
								? 'check'
								: undefined
						}
					>
						{ALL_CONTENT_LABEL}
					</ClayDropDown.Item>
					<ClayDropDown.Divider />
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</div>
	);
}
