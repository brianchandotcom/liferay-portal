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
import {
	createPortletURL,
	getPortletNamespace,
	openModal,
	openSelectionModal,
	openToast,
	sub,
} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

export default function DisplayPagePreview({
	newArticle,
	portletNamespace: namespace,
	previewURL,
	saveAsDraftURL,
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
		return [
			{groupId: 0, name: `- ${Liferay.Language.get('not-selected')} -`},
			...sites,
		].map((site) => ({
			label: site.name,
			onClick: () => {
				setActive(false);
				setSelectedSite({groupId: site.groupId, name: site.name});
			},
		}));
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
							{selectedSite?.name ||
								`- ${Liferay.Language.get('not-selected')} -`}
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

						<div className="d-flex w-100">
							<ClayButton
								className="flex-grow-1 mx-3"
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
										title: Liferay.Language.get(
											'select-site'
										),
										url: siteItemSelectorURL,
									});
								}}
								small
								type="button"
							>
								{Liferay.Language.get('more')}
							</ClayButton>
						</div>
					</>
				)}
			</ClayDropDown>

			<DisplayPageSelector
				displayPageSelected={displayPageSelected}
				namespace={namespace}
				newArticle={newArticle}
				previewURL={previewURL}
				saveAsDraftURL={saveAsDraftURL}
				selectDisplayPageEventName={selectDisplayPageEventName}
				selectDisplayPageURL={selectDisplayPageURL}
				selectedSite={selectedSite}
				setDisplayPageSelected={setDisplayPageSelected}
			/>
		</>
	);
}

function DisplayPageSelector({
	displayPageSelected,
	namespace,
	newArticle,
	previewURL,
	saveAsDraftURL,
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
					name: selectedItem.name,
					plid: selectedItem.plid,
				});
			},
			selectEventName: selectDisplayPageEventName,
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('display-page')
			),
			url,
		});
	};

	return (
		<div className="mb-3 mt-2">
			<ClayForm.Group className="mb-2">
				<label className="sr-only" htmlFor={displayPageId}>
					{Liferay.Language.get('display-page')}
				</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							disabled={!selectedSite?.groupId}
							onClick={() => openDisplayPageSelector()}
							placeholder={sub(
								Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
							)}
							readOnly
							sizing="sm"
							value={displayPageSelected?.name ?? ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							disabled={!selectedSite?.groupId}
							displayType="secondary"
							monospaced
							onClick={() => openDisplayPageSelector()}
							small
							title={sub(
								displayPageSelected
									? Liferay.Language.get('change-x')
									: Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
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

			<ClayButton
				disabled={!displayPageSelected}
				displayType="secondary"
				onClick={() => {
					const formDateInput = document.getElementById(
						`${namespace}formDate`
					);

					formDateInput.value = Date.now().toString();

					const form = document.getElementById(`${namespace}fm1`);

					const formData = new FormData(form);

					formData.append(
						`${namespace}cmd`,
						newArticle ? 'add' : 'update'
					);

					return Liferay.Util.fetch(saveAsDraftURL, {
						body: formData,
						method: form.method,
					})
						.then((response) => response.json())
						.then(({classPK, error, version}) => {
							if (error) {
								openToast({
									message: Liferay.Language.get(
										'web-content-could-not-be-previewed-due-to-an-unexpected-error-while-generating-the-draft'
									),
									title: Liferay.Language.get('error'),
									type: 'danger',
								});
							}
							else {
								const formDateInput = document.getElementById(
									`${namespace}formDate`
								);

								formDateInput.value = Date.now().toString();

								openModal({
									title: Liferay.Language.get('preview'),
									url: createPortletURL(previewURL, {
										classPK,
										selPlid: displayPageSelected?.plid,
										version,
									}).toString(),
								});
							}
						})
						.catch(() => {
							openToast({
								message: Liferay.Language.get(
									'web-content-could-not-be-previewed-due-to-an-unexpected-error-while-generating-the-draft'
								),
								title: Liferay.Language.get('error'),
								type: 'danger',
							});
						});
				}}
				title={Liferay.Language.get(
					'a-draft-will-be-saved-before-displaying-the-preview'
				)}
			>
				{Liferay.Language.get('preview')}
			</ClayButton>
		</div>
	);
}
