/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {config} from './config';
import {LAYOUT_TYPES} from './constants/layoutTypes';
import {
	usePreviewLayout,
	usePreviewLayoutType,
	useSetLoading,
	useSetPreviewLayout,
	useSetPreviewLayoutType,
} from './contexts/LayoutContext';
import {itemSelectorValueFromFragmentCollection} from './item_selector_value/itemSelectorValueFromFragmentCollection';
import {itemSelectorValueFromLayout} from './item_selector_value/itemSelectorValueFromLayout';
import openItemSelector from './openItemSelector';

const LAYOUT_TYPES_OPTIONS = [
	{
		label: Liferay.Language.get('display-page-templates'),
		type: LAYOUT_TYPES.displayPageTemplate,
	},
	{
		label: Liferay.Language.get('fragments'),
		type: LAYOUT_TYPES.fragmentCollection,
	},
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
];

export default function PreviewSelector() {
	const previewLayoutType = usePreviewLayoutType();

	return (
		<>
			<LayoutTypeSelector layoutType={previewLayoutType} />

			<LayoutSelector layoutType={previewLayoutType} />
		</>
	);
}

export function LayoutTypeSelector({layoutType}) {
	const [active, setActive] = useState(false);
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
					className="form-control-select ml-3 style-book-editor__preview-selector text-left"
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
					const previewData = config.previewOptions.find(
						(option) => option.type === type
					).data;

					const {totalLayouts} = previewData;

					return totalLayouts ? (
						<ClayDropDown.Item
							key={type}
							onClick={() => {
								setActive(false);
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
	layoutType: PropTypes.string.isRequired,
};

export function LayoutSelector({layoutType}) {
	const [active, setActive] = useState(false);
	const previewLayout = usePreviewLayout();
	const setLoading = useSetLoading();
	const setPreviewLayout = useSetPreviewLayout();

	const previewData = config.previewOptions.find(
		(option) => option.type === layoutType
	).data;

	const [recentLayouts, setRecentLayouts] = useState(
		previewData.recentLayouts
	);

	const {itemSelectorURL, totalLayouts} = previewData;

	useEffect(() => {
		setLoading(true);
		setPreviewLayout(previewData.recentLayouts[0]);
		setRecentLayouts(previewData.recentLayouts);
	}, [previewData, setLoading, setPreviewLayout]);

	const selectPreviewLayout = (layout) => {
		if (
			layout.name === previewLayout.name &&
			layout.url === previewLayout.url
		) {
			return;
		}

		setLoading(true);
		setPreviewLayout(layout);
		setRecentLayouts(getNextRecentLayouts(recentLayouts, layout));
	};

	const handleMoreButtonClick = () => {
		openItemSelector({
			callback: (item) => {
				const value = JSON.parse(item.value);

				if (layoutType === LAYOUT_TYPES.fragmentCollection) {
					selectPreviewLayout(
						itemSelectorValueFromFragmentCollection(value)
					);
				}
				else {
					selectPreviewLayout(itemSelectorValueFromLayout(value));
				}
			},
			itemSelectorURL,
		});
	};

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
					size="sm"
					type="button"
				>
					<span>{previewLayout?.name}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Group header={Liferay.Language.get('recent')}>
					{recentLayouts.map((layout) => (
						<ClayDropDown.Item
							key={layout.url}
							onClick={() => {
								setActive(false);

								selectPreviewLayout(layout);
							}}
						>
							{layout.name}

							{layout.private && (
								<ClayIcon
									className="ml-3"
									symbol="low-vision"
								/>
							)}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.Group>

				{totalLayouts > recentLayouts.length && (
					<>
						<ClayDropDown.Caption>
							{sub(
								Liferay.Language.get('showing-x-of-x-items'),
								recentLayouts.length,
								totalLayouts
							)}
						</ClayDropDown.Caption>
						<ClayDropDown.Section>
							<ClayButton
								displayType="secondary w-100"
								onClick={() => {
									setActive(false);

									handleMoreButtonClick();
								}}
							>
								{Liferay.Language.get('more')}
							</ClayButton>
						</ClayDropDown.Section>
					</>
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

LayoutSelector.propTypes = {
	layoutType: PropTypes.string.isRequired,
};

/**
 * Calculates new recent layouts. Inserts the selected layout in first position.
 * If it is already present in the array, removes it from current position.
 * If not, removes the last item instead.
 *
 * @param {Array} recentLayouts
 * @param {object} selectedLayout
 */
function getNextRecentLayouts(recentLayouts, selectedLayout) {
	const selectedLayoutIndex = recentLayouts.findIndex(
		(layout) =>
			layout.url === selectedLayout.url &&
			layout.name === selectedLayout.name
	);

	const deletedLayoutIndex =
		selectedLayoutIndex > -1
			? selectedLayoutIndex
			: recentLayouts.length - 1;

	const nextRecentLayouts = [
		selectedLayout,
		...recentLayouts.slice(0, deletedLayoutIndex),
		...recentLayouts.slice(deletedLayoutIndex + 1, recentLayouts.length),
	];

	return nextRecentLayouts;
}
