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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {createPortletURL, openSelectionModal, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

export default function DisplayPagePreview({
	namespace,
	selectDisplayPageEventName,
	selectDisplayPageURL,
	selectSiteEventName,
	siteItemSelectorURL,
	sites,
	sitesCount,
}) {
	const [selectedSite, setSelectedSite] = useState();
	const [active, setActive] = useState(false);
	const [displayPageSelected, setDisplayPageSelected] = useState();

	const siteInputId = `${namespace}siteInput`;

	const items = useMemo(() => {
		return [{groupId: 0, name: Liferay.Language.get('none')}, ...sites].map(
			(site) => ({
				label: site.name,
				onClick: () => {
					setActive(false);
					setSelectedSite({groupId: site.groupId, name: site.name});
				},
			})
		);
	}, [sites]);

	return (
		<>
			<label className="control-label" htmlFor={siteInputId}>
				{Liferay.Language.get('site')}
			</label>
			<ClayDropDown
				active={active}
				aria-labelledby={siteInputId}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="bg-light form-control-select text-left w-100"
						displayType="secondary"
						type="button"
					>
						<span>
							{selectedSite?.name || Liferay.Language.get('none')}
						</span>
					</ClayButton>
				}
			>
				{items.map((item) => (
					<ClayDropDown.Item key={item.label} onClick={item.onClick}>
						{item.label}
					</ClayDropDown.Item>
				))}

				{sitesCount > sites.length && (
					<>
						<ClayDropDown.Caption>
							{sub(
								Liferay.Language.get('showing-x-of-x-items'),
								sites.length,
								sitesCount
							)}
						</ClayDropDown.Caption>

						<ClayButton
							className="w-100"
							displayType="secondary"
							onClick={() => {
								openSelectionModal({
									containerProps: {
										className: 'cadmin',
									},
									onSelect(selectedItem) {
										setSelectedSite({
											groupId: selectedItem.groupid,
											name:
												selectedItem.groupdescriptivename,
										});
									},
									selectEventName: selectSiteEventName,
									title: Liferay.Language.get('select-site'),
									url: siteItemSelectorURL,
								});
							}}
							small
							type="button"
						>
							{Liferay.Language.get('more')}
						</ClayButton>
					</>
				)}
			</ClayDropDown>

			{selectedSite?.groupId && (
				<DisplayPageInput
					displayPageSelected={displayPageSelected}
					namespace={namespace}
					selectDisplayPageEventName={selectDisplayPageEventName}
					selectDisplayPageURL={selectDisplayPageURL}
					selectedSite={selectedSite}
					setDisplayPageSelected={setDisplayPageSelected}
				/>
			)}
		</>
	);
}

function DisplayPageInput({
	displayPageSelected,
	namespace,
	selectDisplayPageEventName,
	selectDisplayPageURL,
	selectedSite,
	setDisplayPageSelected,
}) {
	const displayPageId = `${namespace}displayPageId`;

	const openDisplayPageSelector = () => {
		const url = new URL(selectDisplayPageURL);

		url.searchParams.set(
			`${getPortletNamespace(Liferay.PortletKeys.ITEM_SELECTOR)}groupId`,
			selectedSite.groupId
		);

		openSelectionModal({
			containerProps: {
				className: 'cadmin',
			},
			onSelect(selectedItem) {
				setDisplayPageSelected({
					id: selectedItem.id,
					name: selectedItem.name,
				});
			},
			selectEventName: selectDisplayPageEventName,
			title: Liferay.Language.get('select-page'),
			url,
		});
	};

	return (
		<div className="mb-3 mt-2">
			<ClayForm.Group>
				<label className="sr-only" htmlFor={displayPageId}>
					{Liferay.Language.get('display-page')}
				</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							placeholder={Liferay.Language.get(
								'select-display-page'
							)}
							readOnly
							sizing="sm"
							value={displayPageSelected?.name ?? ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							displayType="secondary"
							monospaced
							onClick={() => openDisplayPageSelector()}
							small
							title={sub(
								displayPageSelected
									? Liferay.Language.get('change-x')
									: Liferay.Language.get('select-x'),
								Liferay.Language.get('image')
							)}
						>
							<ClayIcon
								className="mt-0"
								symbol={displayPageSelected ? 'change' : 'plus'}
							/>
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}
