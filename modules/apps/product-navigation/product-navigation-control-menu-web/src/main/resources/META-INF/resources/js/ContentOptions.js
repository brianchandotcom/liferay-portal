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
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {AddPanelContext} from './AddPanel';

const OPTIONS = [
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-items'), 4),
		value: 4,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-items'), 8),
		value: 8,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-items'), 10),
		value: 10,
	},
];

const ContentOptions = ({onChangeSelect}) => {
	const {
		addContentsURLs,
		displayGrid,
		portletNamespace,
		setDisplayGrid,
	} = useContext(AddPanelContext);

	const [active, setActive] = useState(false);

	return (
		<div className="sidebar-body__add-panel__content-options">
			<ClayForm.Group small>
				<ClaySelectWithOption
					aria-label="Select Label"
					className="btn-monospaced sidebar-body__add-panel__content-options-select"
					defaultValue={10}
					id={`${portletNamespace}_contentDropdown`}
					onChange={(event) => onChangeSelect(event.target.value)}
					options={OPTIONS}
					sizing="sm"
				/>
			</ClayForm.Group>
			<ClayButton
				className="btn-monospaced sidebar-body__add-panel__content-options-list"
				displayType="unstyled"
				onClick={() => setDisplayGrid(!displayGrid)}
				small
				title={Liferay.Language.get('display-style')}
			>
				<ClayIcon symbol={displayGrid ? 'cards2' : 'list'} />
				<span className="sr-only">
					{Liferay.Language.get('display-style')}
				</span>
			</ClayButton>

			<ClayDropDown
				active={active}
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="btn-monospaced sidebar-body__add-panel__content-options-add"
						displayType="unstyled"
						small
						title={Liferay.Language.get('add-new')}
					>
						<ClayIcon symbol="plus" />
						<span className="sr-only">
							{Liferay.Language.get('add-new')}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{addContentsURLs.map((content, index) => (
						<ClayDropDown.Item
							key={index}
							onClick={() => {
								setActive(false);
								Liferay.Util.navigate(content.url);
							}}
						>
							{content.label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</div>
	);
};

ContentOptions.propTypes = {
	onChangeSelect: PropTypes.func,
};

export default ContentOptions;
