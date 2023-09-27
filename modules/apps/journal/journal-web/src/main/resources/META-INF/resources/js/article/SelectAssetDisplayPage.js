/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

import AssetDisplayPageSelector from './AssetDisplayPageSelector';
import PreviewButton from './PreviewButton';

function getLabel(displayPageType) {
	if (displayPageType === DISPLAY_PAGE_TYPE.default) {
		return Liferay.Language.get('default');
	}
	else if (displayPageType === DISPLAY_PAGE_TYPE.specific) {
		return Liferay.Language.get('specific');
	}
	else {
		return Liferay.Language.get('none');
	}
}

const DISPLAY_PAGE_TYPE = {
	default: 1,
	none: 0,
	specific: 2,
};

export default function SelectAssetDisplayPage({
	assetDisplayPageId,
	assetDisplayPageType,
	defaultDisplayPageName,
	layoutUuid,
	newArticle,
	portletNamespace: namespace,
	saveAsDraftURL,
	selectAssetDisplayPageEventName,
	selectAssetDisplayPageURL,
	specificAssetDisplayPageName,
}) {
	const [selectedDisplayPageType, setSelectedDisplayPageType] = useState(
		assetDisplayPageType
	);

	const [assetDisplayPageSelected, setAssetDisplayPageSelected] = useState(
		() => {
			if (assetDisplayPageType === DISPLAY_PAGE_TYPE.specific) {
				return {
					assetDisplayPageId,
					layoutUuid,
					name: specificAssetDisplayPageName,
				};
			}

			return null;
		}
	);

	const ITEMS = [
		{
			label: getLabel(DISPLAY_PAGE_TYPE.default),
			onClick: () =>
				setSelectedDisplayPageType(DISPLAY_PAGE_TYPE.default),
			value: DISPLAY_PAGE_TYPE.default,
		},
		{
			label: getLabel(DISPLAY_PAGE_TYPE.specific),
			onClick: () =>
				setSelectedDisplayPageType(DISPLAY_PAGE_TYPE.specific),
			value: DISPLAY_PAGE_TYPE.specific,
		},
		{
			label: getLabel(DISPLAY_PAGE_TYPE.none),
			onClick: () => setSelectedDisplayPageType(DISPLAY_PAGE_TYPE.none),
			value: DISPLAY_PAGE_TYPE.none,
		},
	];

	return (
		<>
			<ClayForm.Group>
				<ClayDropDownWithItems
					items={ITEMS}
					trigger={
						<ClayButton
							aria-label={Liferay.Language.get(
								'select-display-page-type'
							)}
							className="bg-light form-control-select text-left w-100"
							displayType="secondary"
							type="button"
						>
							<span>{getLabel(selectedDisplayPageType)}</span>
						</ClayButton>
					}
				/>
			</ClayForm.Group>

			<ClayInput
				hidden
				name={`${namespace}layoutUuid`}
				value={assetDisplayPageSelected?.layoutUuid ?? ''}
			/>

			<ClayInput
				hidden
				name={`${namespace}assetDisplayPageId`}
				value={assetDisplayPageSelected?.assetDisplayPageId ?? ''}
			/>

			<ClayInput
				hidden
				name={`${namespace}displayPageType`}
				value={selectedDisplayPageType ?? ''}
			/>

			{selectedDisplayPageType === DISPLAY_PAGE_TYPE.default && (
				<ClayForm.Group className="mb-2">
					<ClayInput
						aria-label={Liferay.Language.get('display-page-name')}
						readOnly
						value={
							defaultDisplayPageName ||
							Liferay.Language.get(
								'no-default-display-page-template'
							)
						}
					/>
				</ClayForm.Group>
			)}

			{selectedDisplayPageType === DISPLAY_PAGE_TYPE.specific && (
				<AssetDisplayPageSelector
					assetDisplayPageSelected={assetDisplayPageSelected}
					disabled={false}
					namespace={namespace}
					selectAssetDisplayPageEventName={
						selectAssetDisplayPageEventName
					}
					selectAssetDisplayPageURL={selectAssetDisplayPageURL}
					setAssetDisplayPageSelected={setAssetDisplayPageSelected}
				/>
			)}

			{selectedDisplayPageType !== DISPLAY_PAGE_TYPE.none && (
				<PreviewButton
					disabled={
						(selectedDisplayPageType ===
							DISPLAY_PAGE_TYPE.specific &&
							!assetDisplayPageSelected) ||
						(selectedDisplayPageType ===
							DISPLAY_PAGE_TYPE.default &&
							!defaultDisplayPageName)
					}
					getPreviewURL={({previewURL}) => previewURL}
					namespace={namespace}
					newArticle={newArticle}
					saveAsDraftURL={saveAsDraftURL}
				/>
			)}
		</>
	);
}
