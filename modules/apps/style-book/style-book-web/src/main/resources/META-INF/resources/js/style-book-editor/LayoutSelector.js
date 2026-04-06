/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {config} from './config';
import {LAYOUT_TYPES} from './constants/layoutTypes';
import {
	usePreviewLayout,
	useSetLoading,
	useSetPreviewLayout,
} from './contexts/LayoutContext';
import {itemSelectorValueFromFragmentCollection} from './item_selector_value/itemSelectorValueFromFragmentCollection';
import {itemSelectorValueFromLayout} from './item_selector_value/itemSelectorValueFromLayout';
import openItemSelector from './openItemSelector';

export function LayoutSelector({className = 'ml-3', layoutType}) {
	const [active, setActive] = useState(false);
	const previewLayout = usePreviewLayout();
	const setLoading = useSetLoading();
	const setPreviewLayout = useSetPreviewLayout();

	const {
		itemSelectorURL,
		recentLayouts: initialRecentLayouts,
		totalLayouts,
	} = useMemo(
		() => config.previewOptions.find((opt) => opt.type === layoutType).data,
		[layoutType]
	);

	const [recentLayouts, setRecentLayouts] = useState(initialRecentLayouts);

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
					className={classNames(
						'form-control-select style-book-editor__preview-selector text-left',
						className
					)}
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
							className="align-items-center d-flex"
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

							{!layout.hasGuestViewPermission && (
								<span
									aria-label={Liferay.Language.get(
										'restricted-page'
									)}
									className="c-ml-2 lfr-portal-tooltip"
									title={Liferay.Language.get(
										'restricted-page'
									)}
								>
									<ClayIcon
										className="c-mt-0 text-4 text-dark"
										symbol="password-policies"
									/>
								</span>
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
	className: PropTypes.string,
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
