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
import {createPortletURL, openSelectionModal, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import AssetDisplayPageSelector from './AssetDisplayPageSelector';
import PreviewButton from './PreviewButton';

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

				if (site.groupId !== selectedSite?.groupId) {
					setAssetDisplayPageSelected(null);
				}
			},
		}));
	}, [sites, selectedSite?.groupId]);

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
								size="sm"
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
				disabled={!selectedSite}
				namespace={namespace}
				selectAssetDisplayPageEventName={
					selectAssetDisplayPageEventName
				}
				selectAssetDisplayPageURL={selectAssetDisplayPageURL}
				selectedSite={selectedSite}
				setAssetDisplayPageSelected={setAssetDisplayPageSelected}
			/>

			<PreviewButton
				disabled={!assetDisplayPageSelected}
				getPreviewURL={({classPK, version}) =>
					createPortletURL(previewURL, {
						classPK,

						selPlid: assetDisplayPageSelected?.plid,
						version,
					}).toString()
				}
				namespace={namespace}
				newArticle={newArticle}
				saveAsDraftURL={saveAsDraftURL}
			/>
		</>
	);
}
