/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import React, {useEffect, useState} from 'react';

import VocabularyMultiSelect, {Category} from '../VocabularyMultiSelect';

import './Categorization.scss';

import {AssetTags, IAssetObjectEntry} from '@liferay/site-cms-site-initializer';

interface CategorizationProps {
	cmsGroupId: number;
	funnelStagesVocabularyERC: string;
	hasUpdatePermission: boolean;
	objectEntryKeywords: string[];
	personasVocabularyERC: string;
	selectedFunnelStageCategories: Category[];
	selectedPersonaCategories: Category[];
}

export default function Categorization({
	cmsGroupId,
	funnelStagesVocabularyERC,
	hasUpdatePermission,
	objectEntryKeywords,
	personasVocabularyERC,
	selectedFunnelStageCategories,
	selectedPersonaCategories,
}: CategorizationProps) {
	const [formId, setFormId] = useState<string | undefined>();
	const [funnelStageIds, setFunnelStageIds] = useState<number[]>(
		selectedFunnelStageCategories.map((category) => category.id)
	);
	const [keywords, setKeywords] = useState<string[]>(objectEntryKeywords);
	const [personaIds, setPersonaIds] = useState<number[]>(
		selectedPersonaCategories.map((category) => category.id)
	);

	const allIds = [...personaIds, ...funnelStageIds];

	useEffect(() => {
		let form = document.querySelector('.lfr-main-form-container');

		if (!form) {
			form = document.querySelector('.lfr-layout-structure-item-form');
		}

		if (form) {
			setFormId(form.id);
		}
	}, []);

	return (
		<ClayPanel
			className="lfr-cmp__categorization"
			collapsable={true}
			defaultExpanded={true}
			displayTitle={Liferay.Language.get('categorization')}
			displayType="default"
			showCollapseIcon={true}
		>
			<div className="lfr-cmp__categorization-grid">
				<VocabularyMultiSelect
					hasUpdatePermission={hasUpdatePermission}
					initialSelectedCategories={selectedPersonaCategories}
					label={Liferay.Language.get('personas')}
					onSelectionChange={(categories) =>
						setPersonaIds(categories.map((category) => category.id))
					}
					placeholder={Liferay.Language.get('add-personas')}
					vocabularyERC={personasVocabularyERC}
				/>

				<VocabularyMultiSelect
					hasUpdatePermission={hasUpdatePermission}
					initialSelectedCategories={selectedFunnelStageCategories}
					label={Liferay.Language.get('funnel-stages')}
					onSelectionChange={(categories) =>
						setFunnelStageIds(
							categories.map((category) => category.id)
						)
					}
					placeholder={Liferay.Language.get('add-stages')}
					vocabularyERC={funnelStagesVocabularyERC}
				/>
			</div>

			{allIds.map((id) => (
				<input
					form={formId}
					key={id}
					name="assetCategoryIds"
					type="hidden"
					value={id}
				/>
			))}

			<div className="lfr-cmp__tags-container">
				<AssetTags
					cmsGroupId={cmsGroupId}
					collapsable={false}
					hasUpdatePermission={hasUpdatePermission}
					inputSize="regular"
					key={keywords.join(',') || 'tags'}
					objectEntry={
						{
							keywords,
						} as IAssetObjectEntry
					}
					titleClassName="text-default"
					updateObjectEntry={async ({
						keywords: updatedKeywords,
					}: Partial<IAssetObjectEntry>): Promise<void> => {
						setKeywords(
							updatedKeywords ? [...updatedKeywords] : []
						);
					}}
				/>

				<input
					form={formId}
					name="assetTagNames"
					type="hidden"
					value={keywords.join(',')}
				/>
			</div>
		</ClayPanel>
	);
}
