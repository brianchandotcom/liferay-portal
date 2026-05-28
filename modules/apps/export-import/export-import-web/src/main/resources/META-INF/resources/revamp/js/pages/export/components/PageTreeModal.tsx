/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';
import {PagesTree} from 'staging-taglib';

const NAMESPACE = '_exportPageTreeModal_';

const PATH_MAIN = Liferay.ThemeDisplay.getPathMain();
const GET_LAYOUTS_TREE_URL = `${PATH_MAIN}/portal/get_layouts_tree`;
const SESSION_TREE_JS_CLICK_URL = `${PATH_MAIN}/portal/session_tree_js_click`;

export interface PageTreeModalConfiguration {
	liveGroupId: number;
	pageSize: number;
	privateLayoutsEnabled: boolean;
}

interface Props extends PageTreeModalConfiguration {
	initialSelectedIds: number[];
	onClose: () => void;
	onSubmit: (
		result: {layoutIds: number[]; privateLayout: boolean} | null
	) => void;
}

export default function PageTreeModal({
	initialSelectedIds,
	liveGroupId,
	onClose,
	onSubmit,
	pageSize,
	privateLayoutsEnabled,
}: Props) {
	const {observer} = useModal({onClose});

	const [privateLayout, setPrivateLayout] = useState(false);
	const [dropdownActive, setDropdownActive] = useState(false);
	const [items, setItems] = useState<unknown[] | null>(null);
	const [error, setError] = useState(false);
	const treeRef = useRef<HTMLDivElement>(null);
	const initialSelectedIdsRef = useRef(initialSelectedIds);
	const treeId = `exportPageTreeModal_${privateLayout ? 'private' : 'public'}`;

	useEffect(() => {
		let cancelled = false;

		setItems(null);
		setError(false);

		const sessionTreeId = `${treeId}SelectedNode`;
		const initialIds = initialSelectedIdsRef.current;

		const sync = async () => {
			await fetch(SESSION_TREE_JS_CLICK_URL, {
				body: new URLSearchParams({
					cmd: 'collapse',
					treeId: sessionTreeId,
				}),
				method: 'post',
			});

			if (initialIds.length) {
				await fetch(SESSION_TREE_JS_CLICK_URL, {
					body: new URLSearchParams({
						cmd: 'expand',
						nodeIds: initialIds.join(','),
						treeId: sessionTreeId,
					}),
					method: 'post',
				});
			}

			const response = await fetch(
				`${GET_LAYOUTS_TREE_URL}?${new URLSearchParams({
					end: String(pageSize),
					groupId: String(liveGroupId),
					incomplete: 'true',
					parentLayoutId: '0',
					privateLayout: String(privateLayout),
					start: '0',
				})}`
			);

			const data = await response.json();

			if (cancelled) {
				return;
			}

			setItems([
				{
					children: data.items,
					hasChildren: true,
					hasGuestViewPermission: true,
					id: '0',
					layoutId: 0,
					name: privateLayout
						? Liferay.Language.get('private-pages')
						: Liferay.Language.get('public-pages'),
					paginated: data.hasMoreElements,
				},
			]);
		};

		sync().catch(() => !cancelled && setError(true));

		return () => {
			cancelled = true;
		};
	}, [liveGroupId, pageSize, privateLayout, treeId]);

	const select = () => {
		const input = treeRef.current?.querySelector<HTMLInputElement>(
			`input[name="${NAMESPACE}layoutIds"]`
		);

		const layoutIds = input
			? (JSON.parse(input.value || '[]') as (number | string)[])
					.map(Number)
					.filter((id) => !Number.isNaN(id) && id !== 0)
			: [];

		onSubmit(layoutIds.length ? {layoutIds, privateLayout} : null);
	};

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('pages-to-export')}
			</ClayModal.Header>

			<ClayModal.Body>
				{privateLayoutsEnabled ? (
					<ClayDropDown
						active={dropdownActive}
						onActiveChange={setDropdownActive}
						trigger={
							<ClayButton
								className="border-bottom border-bottom-primary px-2 py-1 rounded-0"
								displayType="unstyled"
							>
								{privateLayout
									? Liferay.Language.get('private-pages')
									: Liferay.Language.get('public-pages')}

								<ClayIcon
									className="ml-2"
									symbol="caret-bottom"
								/>
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								onClick={() => {
									setPrivateLayout(false);
									setDropdownActive(false);
								}}
							>
								{Liferay.Language.get('public-pages')}
							</ClayDropDown.Item>

							<ClayDropDown.Item
								onClick={() => {
									setPrivateLayout(true);
									setDropdownActive(false);
								}}
							>
								{Liferay.Language.get('private-pages')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				) : (
					<div className="border-bottom border-bottom-primary px-2 py-1">
						{Liferay.Language.get('public-pages')}
					</div>
				)}

				{error && (
					<ClayAlert className="mt-3" displayType="danger">
						{Liferay.Language.get('an-unexpected-error-occurred')}
					</ClayAlert>
				)}

				{!items && !error && <ClayLoadingIndicator className="mt-3" />}

				{items && (
					<div className="mt-3" ref={treeRef}>
						<PagesTree
							config={{
								changeItemSelectionURL:
									SESSION_TREE_JS_CLICK_URL,
								loadMoreItemsURL: GET_LAYOUTS_TREE_URL,
								maxPageSize: pageSize,
								namespace: NAMESPACE,
							}}
							groupId={String(liveGroupId)}
							items={items}
							key={treeId}
							portletNamespace={NAMESPACE}
							privateLayout={privateLayout}
							selectedLayoutIds={initialSelectedIds.map(String)}
							treeId={treeId}
						/>
					</div>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={select}>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
