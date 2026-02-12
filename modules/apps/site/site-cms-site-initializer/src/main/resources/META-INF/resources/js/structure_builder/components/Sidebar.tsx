/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {FocusTrap} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {useIsMobileDevice} from '@clayui/shared';
import {SearchForm} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectPublishedChildren from '../selectors/selectPublishedChildren';
import selectSelection from '../selectors/selectSelection';
import selectStructure from '../selectors/selectStructure';
import handleAddRepeatableGroup from '../utils/handleAddRepeatableGroup';
import handleDeleteChildren from '../utils/handleDeleteChildren';
import AddChildDropdown from './AddChildDropdown';
import StructureTree from './StructureTree';

export default function () {
	const [open, setOpen] = useState<boolean>(false);

	const openButtonRef = useRef<HTMLButtonElement>(null);
	const panelRef = useRef<HTMLDivElement>(null);

	const isMobile = useIsMobileDevice();

	return (
		<>
			<ClayButtonWithIcon
				className="d-md-none sidebar-toggler"
				displayType="secondary"
				onClick={() => {
					setOpen(true);

					requestAnimationFrame(() => {
						panelRef.current?.focus();
					});
				}}
				ref={openButtonRef}
				size="sm"
				symbol="angle-double-right-small"
				title={sub(
					Liferay.Language.get('open-x'),
					sub(
						Liferay.Language.get('x-panel'),
						Liferay.Language.get('content-structure-fields')
					)
				)}
			/>

			<FocusTrap active={isMobile && open}>
				<div
					aria-label={sub(
						Liferay.Language.get('x-panel'),
						Liferay.Language.get('content-structure-fields')
					)}
					className={classNames(
						'border rounded-lg structure-builder__sidebar',
						{'hide-xs': !open}
					)}
					ref={panelRef}
					tabIndex={-1}
				>
					<div className="autofit-row">
						<div className="autofit-col autofit-col-expand">
							<h3 className="font-weight-semi-bold pt-4 px-4 text-4">
								{Liferay.Language.get(
									'content-structure-fields'
								)}
							</h3>
						</div>

						<div className="autofit-col d-md-none mr-2 mt-3">
							<ClayButtonWithIcon
								borderless
								displayType="secondary"
								onClick={() => {
									setOpen(false);

									requestAnimationFrame(() => {
										openButtonRef.current?.focus();
									});
								}}
								size="sm"
								symbol="times"
								title={Liferay.Language.get('close')}
							/>
						</div>
					</div>

					<Content />
				</div>
			</FocusTrap>
		</>
	);
}

function Content() {
	const [search, setSearch] = useState('');

	return (
		<>
			<Toolbar setSearch={setSearch} />

			<StructureTree search={search} />
		</>
	);
}

function Toolbar({
	setSearch,
}: {
	setSearch: React.Dispatch<React.SetStateAction<string>>;
}) {
	const dispatch = useStateDispatch();
	const selection = useSelector(selectSelection);
	const structure = useSelector(selectStructure);
	const publishedChildren = useSelector(selectPublishedChildren);

	if (selection.length <= 1) {
		return (
			<div className="align-items-center c-gap-2 d-flex px-4">
				<SearchForm
					className="flex-grow-1 my-3"
					label={Liferay.Language.get('search-fields')}
					onChange={setSearch}
					size="sm"
					variant="white"
				/>

				<AddChildDropdown />
			</div>
		);
	}

	return (
		<ManagementToolbar.Container
			active
			className="mb-2 mt-3"
			onClick={(event) => event.stopPropagation()}
		>
			{sub(Liferay.Language.get('x-items-selected'), selection.length)}

			<ClayDropDownWithItems
				items={[
					{
						label: Liferay.Language.get('create-repeatable-group'),
						onClick: () =>
							handleAddRepeatableGroup({
								dispatch,
								publishedChildren,
								uuids: selection,
							}),
						symbolLeft: 'repeat',
					},
					{type: 'divider'},
					{
						label: Liferay.Language.get('delete'),
						onClick: () =>
							handleDeleteChildren({
								dispatch,
								publishedChildren,
								structure,
								uuids: selection,
							}),
						symbolLeft: 'trash',
					},
				]}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('selection-options')}
						borderless
						displayType="unstyled"
						size="sm"
						symbol="ellipsis-v"
					/>
				}
			/>
		</ManagementToolbar.Container>
	);
}
