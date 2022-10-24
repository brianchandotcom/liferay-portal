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

export default function AssetDisplayPagePreview({
	newArticle,
	portletNamespace: namespace,
	previewURL,
	saveAsDraftURL,
	selectAssetDisplayPageEventName,
	selectAssetDisplayPageURL,
	selectSiteEventName,
	siteItemSelectorURL,
	sites,
	sitesCount,
}) {
	const [selectedSite, setSelectedSite] = useState();
	const [active, setActive] = useState(false);
	const [assetDisplayPageSelected, setAssetDisplayPageSelected] = useState();

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

			<AssetDisplayPageSelector
				assetDisplayPageSelected={assetDisplayPageSelected}
				namespace={namespace}
				newArticle={newArticle}
				previewURL={previewURL}
				saveAsDraftURL={saveAsDraftURL}
				selectAssetDisplayPageEventName={
					selectAssetDisplayPageEventName
				}
				selectAssetDisplayPageURL={selectAssetDisplayPageURL}
				selectedSite={selectedSite}
				setAssetDisplayPageSelected={setAssetDisplayPageSelected}
			/>
		</>
	);
}

function AssetDisplayPageSelector({
	assetDisplayPageSelected,
	namespace,
	newArticle,
	previewURL,
	saveAsDraftURL,
	selectAssetDisplayPageEventName,
	selectAssetDisplayPageURL,
	selectedSite,
	setAssetDisplayPageSelected,
}) {
	const assetDisplayPageId = `${namespace}assetDisplayPageId`;

	const openAssetDisplayPageSelector = () => {
		const url = new URL(selectAssetDisplayPageURL);

		url.searchParams.set(
			`${getPortletNamespace(Liferay.PortletKeys.ITEM_SELECTOR)}groupId`,
			selectedSite.groupId
		);

		openSelectionModal({
			containerProps: {
				className: 'cadmin',
			},
			onSelect(selectedItem) {
				setAssetDisplayPageSelected({
					name: selectedItem.name,
					plid: selectedItem.plid,
				});
			},
			selectEventName: selectAssetDisplayPageEventName,
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
				<label className="sr-only" htmlFor={assetDisplayPageId}>
					{Liferay.Language.get('display-page')}
				</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							disabled={!selectedSite?.groupId}
							onClick={() => openAssetDisplayPageSelector()}
							placeholder={sub(
								Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
							)}
							readOnly
							sizing="sm"
							value={assetDisplayPageSelected?.name ?? ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							disabled={!selectedSite?.groupId}
							displayType="secondary"
							monospaced
							onClick={() => openAssetDisplayPageSelector()}
							small
							title={sub(
								assetDisplayPageSelected
									? Liferay.Language.get('change-x')
									: Liferay.Language.get('select-x'),
								Liferay.Language.get('display-page')
							)}
						>
							<ClayIcon
								className="mt-0"
								symbol={
									assetDisplayPageSelected ? 'change' : 'plus'
								}
							/>
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayButton
				disabled={!assetDisplayPageSelected}
				displayType="secondary"
				onClick={() => {
					updateJournalInput({
						name: 'formDate',
						namespace,
						value: Date.now().toString(),
					});

					const form = document.getElementById(`${namespace}fm1`);

					const formData = new FormData(form);

					const articleId = document.getElementById(
						`${namespace}articleId`
					);

					formData.append(
						`${namespace}cmd`,
						newArticle && !articleId.value ? 'add' : 'update'
					);

					return Liferay.Util.fetch(saveAsDraftURL, {
						body: formData,
						method: form.method,
					})
						.then((response) => response.json())
						.then(
							({
								articleId,
								classPK,
								error,
								friendlyUrlMap,
								version,
							}) => {
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
									updateJournalInput({
										name: 'formDate',
										namespace,
										value: Date.now().toString(),
									});

									updateJournalInput({
										name: 'articleId',
										namespace,
										value: articleId,
									});

									updateJournalInput({
										name: 'version',
										namespace,
										value: version,
									});

									Object.entries(friendlyUrlMap).forEach(
										([languageId, value]) => {
											updateJournalInput({
												name: `friendlyURL_${languageId}`,
												namespace,
												value,
											});
										}
									);

									openModal({
										title: Liferay.Language.get('preview'),
										url: createPortletURL(previewURL, {
											classPK,

											selPlid:
												assetDisplayPageSelected?.plid,
											version,
										}).toString(),
									});
								}
							}
						)
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

function updateJournalInput({name, namespace, value}) {
	const input = document.getElementById(`${namespace}${name}`);

	if (input) {
		input.value = value;
	}
}
