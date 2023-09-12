/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayPopover from '@clayui/popover';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';
import React, {useContext, useMemo, useState} from 'react';

import {Fragment} from '../../constants/Fragment';
import {SET_SELECTED_ITEM} from '../../constants/actionTypes';
import {StoreDispatchContext} from '../../context/StoreContext';
import getComponentType from '../../utils/getComponentType';

interface HighlightedFragment {
	fragment: Element | null;
	hierarchy: string | undefined;
	name: string;
	style: {left: number; top: number};
}

const PopoverLabel = ({label, name}: {label: string; name: string}) => {
	const newLabel = label.replace(name, '');

	return (
		<>
			{newLabel} <span className="font-weight-bold">{name}</span>
		</>
	);
};

export default function FragmentList({
	ascendingSort,
	fragments,
}: {
	ascendingSort: boolean;
	fragments: Fragment[];
}) {
	const [
		highlightedFragment,
		setHighlightedFragment,
	] = useState<HighlightedFragment | null>(null);

	const dispatch = useContext(StoreDispatchContext);

	const fragmentsWithPosition = useMemo(
		() =>
			fragments.map((fragment, index) => ({
				...fragment,
				position: index,
			})),
		[fragments]
	);

	const highlightFragment = ({
		enableScroll = true,
		hierarchy,
		itemId,
		name,
		position,
	}: {
		enableScroll?: boolean;
		hierarchy?: string;
		itemId: string;
		name: string;
		position: number;
	}) => {
		const renderedFragments = document.querySelectorAll(
			`.lfr-layout-structure-item-${itemId}`
		);

		if (!renderedFragments.length) {
			return;
		}

		let [fragment] = renderedFragments;

		if (renderedFragments.length > 1) {
			const fragmentPosition = fragmentsWithPosition
				.filter((fragment) => fragment.itemId === itemId)
				.findIndex((fragment) => fragment.position === position);

			fragment = renderedFragments[fragmentPosition];
		}

		fragment?.classList.add('page-audit__fragment--highlight');

		const rect = fragment?.getBoundingClientRect();

		setHighlightedFragment({
			fragment,
			hierarchy,
			name,
			style: {left: rect.x, top: rect.y + rect.height + window.scrollY},
		});

		if (enableScroll) {
			fragment?.scrollIntoView?.({
				behavior: 'smooth',
				block: 'center',
				inline: 'nearest',
			});
		}
	};

	const removeHighlightFromFragment = () => {
		highlightedFragment?.fragment?.classList.remove(
			'page-audit__fragment--highlight'
		);

		setHighlightedFragment(null);
	};

	const selectFragment = (fragment: Fragment) =>
		dispatch({
			item: {
				...fragment,
				title: fragment.name,
				type: 'fragment',
			},
			type: SET_SELECTED_ITEM,
		});

	return (
		<div className="page-audit__fragmentList">
			{fragmentsWithPosition
				.sort((a: Fragment, b: Fragment) =>
					ascendingSort
						? a.renderTime - b.renderTime
						: b.renderTime - a.renderTime
				)
				.map((fragment) => {
					const {
						cached,
						fragmentCollectionURL,
						fromMaster,
						hierarchy,
						itemId,
						name,
						renderTime,
						warnings = [],
						position,
					} = fragment;

					return (
						<div
							className="c-p-2 page-audit__fragment position-relative"
							key={`${itemId}${position}`}
							onMouseLeave={removeHighlightFromFragment}
							onMouseOver={() =>
								highlightFragment({
									enableScroll: false,
									itemId,
									name,
									position,
								})
							}
						>
							<span className="sr-only" role="status">
								{highlightedFragment?.hierarchy}
							</span>

							<div className="align-items-center d-flex justify-content-between">
								<span className="font-weight-bold">
									{name}

									{warnings.length ? (
										<ClayBadge
											className="ml-2 warning-badge"
											displayType="warning"
											label={warnings.length}
											title={sub(
												Liferay.Language.get(
													'x-issues'
												),
												warnings.length
											)}
										/>
									) : null}
								</span>

								<div className="page-audit__fragment__buttons">
									<ClayButton
										aria-label={sub(
											Liferay.Language.get('select-x'),
											name
										)}
										className="select-fragment-button"
										displayType="unstyled"
										onBlur={removeHighlightFromFragment}
										onClick={() => {
											selectFragment(fragment);

											removeHighlightFromFragment();
										}}
										onFocus={() =>
											highlightFragment({
												itemId,
												name,
												position,
											})
										}
									/>

									<ClayButtonWithIcon
										aria-label={sub(
											Liferay.Language.get(
												'locate-x-in-page'
											),
											name
										)}
										borderless
										className="position-relative"
										displayType="secondary"
										onBlur={removeHighlightFromFragment}
										onClick={() =>
											highlightFragment({
												hierarchy,
												itemId,
												name,
												position,
											})
										}
										size="sm"
										symbol="search"
										title={sub(
											Liferay.Language.get(
												'locate-x-in-page'
											),
											name
										)}
									/>

									{fragmentCollectionURL ? (
										<ClayButtonWithIcon
											aria-label={sub(
												Liferay.Language.get(
													'open-x-in-fragment-library'
												),
												name
											)}
											borderless
											className="c-ml-2 position-relative"
											displayType="secondary"
											onClick={() =>
												window.open(
													fragmentCollectionURL,
													'_blank'
												)
											}
											size="sm"
											symbol="shortcut"
											title={sub(
												Liferay.Language.get(
													'open-x-in-fragment-library'
												),
												name
											)}
										/>
									) : null}
								</div>
							</div>

							<p className="mb-0">
								{sub(Liferay.Language.get('x-ms'), renderTime)}
							</p>

							<p className="mb-0">
								<ClayLabel displayType="secondary">
									{getComponentType(fragment)}
								</ClayLabel>

								{fromMaster && (
									<ClayLabel displayType="secondary">
										{Liferay.Language.get('from-master')}
									</ClayLabel>
								)}

								{cached && (
									<ClayLabel displayType="info">
										{Liferay.Language.get('cached')}
									</ClayLabel>
								)}
							</p>
						</div>
					);
				})}

			{highlightedFragment ? (
				<ReactPortal container={document.body}>
					<div
						className="page-audit__fragment__popover"
						style={highlightedFragment.style}
					>
						{highlightedFragment.hierarchy ? (
							<ClayPopover alignPosition="bottom-left">
								<PopoverLabel
									label={highlightedFragment.hierarchy}
									name={highlightedFragment.name}
								/>
							</ClayPopover>
						) : null}
					</div>
				</ReactPortal>
			) : null}
		</div>
	);
}
