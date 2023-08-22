/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayPopover from '@clayui/popover';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {Fragment} from '../../constants/Fragment';
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

	const highlightFragment = ({
		hierarchy,
		itemId,
		name,
	}: {
		hierarchy?: string;
		itemId: string;
		name: string;
	}) => {
		const fragment = document.querySelector(
			`.lfr-layout-structure-item-${itemId}`
		);

		if (!fragment) {
			return;
		}

		fragment?.classList.add('page-audit__fragment--highlight');

		const rect = fragment?.getBoundingClientRect();

		setHighlightedFragment({
			fragment,
			hierarchy,
			name,
			style: {left: rect.x, top: rect.y + rect.height + window.scrollY},
		});

		fragment?.scrollIntoView?.({
			behavior: 'smooth',
			block: 'center',
			inline: 'nearest',
		});
	};

	const removeHighlightFromFragment = () => {
		highlightedFragment?.fragment?.classList.remove(
			'page-audit__fragment--highlight'
		);

		setHighlightedFragment(null);
	};

	return (
		<div className="page-audit__fragmentList">
			{fragments
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
					} = fragment;

					return (
						<div
							className="c-p-2 page-audit__fragment position-relative"
							key={itemId}
							onMouseLeave={removeHighlightFromFragment}
							onMouseOver={() =>
								highlightFragment({
									itemId,
									name,
								})
							}
						>
							<div className="align-items-center d-flex justify-content-between">
								<span className="font-weight-bold">
									{name}

									{warnings.length ? (
										<ClayBadge
											className="ml-2"
											displayType="warning"
											label={warnings.length}
										/>
									) : null}
								</span>

								<div className="page-audit__fragment__buttons">
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

			<span className="sr-only" role="status">
				{highlightedFragment?.hierarchy}
			</span>
		</div>
	);
}
