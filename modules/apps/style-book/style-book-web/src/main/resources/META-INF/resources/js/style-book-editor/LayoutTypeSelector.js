/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {config} from './config';
import {LAYOUT_TYPES_OPTIONS} from './constants/layoutTypes';
import {
	useSetLoading,
	useSetPreviewLayout,
	useSetPreviewLayoutType,
} from './contexts/LayoutContext';

export function LayoutTypeSelector({className = 'ml-3', layoutType}) {
	const [active, setActive] = useState(false);
	const setLoading = useSetLoading();
	const setPreviewLayout = useSetPreviewLayout();
	const setPreviewLayoutType = useSetPreviewLayoutType();

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
					className={classNames(
						'form-control-select style-book-editor__preview-selector text-left',
						className
					)}
					displayType="secondary"
					size="sm"
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
				{LAYOUT_TYPES_OPTIONS.map(({label, type}) => {
					const previewOption = config.previewOptions.find(
						(option) => option.type === type
					);

					const {totalLayouts} = previewOption.data;

					return totalLayouts ? (
						<ClayDropDown.Item
							key={type}
							onClick={() => {
								setActive(false);

								setLoading(true);

								setPreviewLayout(
									previewOption.data.recentLayouts[0]
								);

								setPreviewLayoutType(type);
							}}
						>
							{label}
						</ClayDropDown.Item>
					) : null;
				})}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

LayoutTypeSelector.propTypes = {
	className: PropTypes.string,
	layoutType: PropTypes.string.isRequired,
};
