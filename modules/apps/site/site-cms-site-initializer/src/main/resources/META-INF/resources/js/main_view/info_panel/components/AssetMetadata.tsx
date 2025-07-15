/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {dateUtils, sub} from 'frontend-js-web';
import React, {useCallback, useContext} from 'react';

import {ISearchAssetObjectEntry} from '../../../structure_builder/types/AssetType';
import {
	AssetTypeInfoPanelContext,
	IAssetTypeInfoPanelContext,
} from '../context';
import {ASSET_TYPE} from '../util/constants';

const getAssetLanguages = (title_i18n: {[key: string]: string} = {}) => {
	const assetLanguages = Object.keys(title_i18n);

	if (assetLanguages.length) {
		return Object.keys(title_i18n).map((key) => key.replace('_', '-'));
	}

	return [];
};

const formatDate = (date: string) => {
	return dateUtils.format(new Date(date), 'P p');
};

const AssetMetadata = () => {
	const {
		objectEntries = [],
		type,
		title_i18n = {},
	}: IAssetTypeInfoPanelContext = useContext(AssetTypeInfoPanelContext);

	const [{embedded: objectEntry}]: ISearchAssetObjectEntry[] = objectEntries;

	const copyText = useCallback(
		(event: any) => {
			event.preventDefault();

			if (window?.navigator?.clipboard) {
				window.navigator.clipboard.writeText(
					objectEntry.file?.link?.href as string
				);
			}
		},
		[objectEntry]
	);

	return (
		<>
			<ClayPanel
				className="asset-metadata"
				collapsable={false}
				displayTitle={Liferay.Language.get('metadata').toUpperCase()}
				displayType="unstyled"
				showCollapseIcon={true}
			>
				<ClayPanel.Body>
					{type === ASSET_TYPE.FILES && (
						<>
							<div className="asset-metadata-block mt-3">
								<h3>{Liferay.Language.get('url')}</h3>

								<ClayInput.Group className="mt-1">
									<ClayInput.GroupItem prepend>
										<ClayInput
											disabled={true}
											placeholder={
												objectEntry.file?.link?.href
											}
											type="text"
										/>
									</ClayInput.GroupItem>

									<ClayInput.GroupItem append shrink>
										<ClayButtonWithIcon
											data-clipboard-text={
												objectEntry.file?.link?.href
											}
											displayType="secondary"
											onClick={copyText}
											symbol="copy"
										></ClayButtonWithIcon>
									</ClayInput.GroupItem>
								</ClayInput.Group>
							</div>
						</>
					)}

					<div className="asset-metadata-block mt-3">
						<h3>{Liferay.Language.get('author')}</h3>

						<p className="mt-1">{objectEntry?.creator?.name}</p>
					</div>

					<div className="asset-metadata-block mt-3">
						<h3>{Liferay.Language.get('created')}</h3>

						<p className="mt-1">
							{sub(Liferay.Language.get('x-by-x'), [
								formatDate(objectEntry.dateCreated as string),
								objectEntry.creator?.name,
							])}
						</p>
					</div>

					<div className="asset-metadata-block mt-3">
						<h3>{Liferay.Language.get('modified')}</h3>

						<p className="mt-1">
							{formatDate(objectEntry.dateModified as string)}
						</p>
					</div>

					{type !== ASSET_TYPE.FOLDER && (
						<>
							<div className="asset-metadata-block mt-3">
								<h3>
									{Liferay.Language.get('expiration-date')}
								</h3>

								<p className="mt-1">
									{objectEntry?.expirationDate
										? formatDate(
												objectEntry?.expirationDate
											)
										: Liferay.Language.get('never-expire')}
								</p>
							</div>
							<div className="asset-metadata-block mt-3">
								<h3>{Liferay.Language.get('review-date')}</h3>

								<p className="mt-1">
									{objectEntry?.reviewDate
										? formatDate(objectEntry?.reviewDate)
										: Liferay.Language.get('never-review')}
								</p>
							</div>
							<div className="asset-metadata-block mt-3">
								<h3>
									{Liferay.Language.get(
										'languages-translated-into'
									)}
								</h3>

								{getAssetLanguages(title_i18n)?.map(
									(locale) => (
										<>
											<div className="d-flex mt-1">
												<ClayIcon
													className="mr-2 mt-1"
													symbol={locale.toLowerCase()}
												/>

												<p>{locale}</p>
											</div>
										</>
									)
								)}
							</div>
						</>
					)}
				</ClayPanel.Body>
			</ClayPanel>
		</>
	);
};

export default AssetMetadata;
