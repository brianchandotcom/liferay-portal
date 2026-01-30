/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {FieldFeedback, useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {ObjectDefinitions} from '../../../common/types/ObjectDefinition';
import getLocalizedValue from '../../../common/utils/getLocalizedValue';
import {useCache} from '../../contexts/CacheContext';
import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectErrors from '../../selectors/selectErrors';
import selectPublishedChildren from '../../selectors/selectPublishedChildren';
import selectStructureERC from '../../selectors/selectStructureERC';
import {RelatedContent, Structure} from '../../types/Structure';
import Breadcrumb from '../Breadcrumb';
import ERCInput from '../ERCInput';
import {LocalizedInput} from '../LocalizedInput';

export default function RelatedContentSettings({
	disabled,
	relatedContent,
}: {
	disabled: boolean;
	relatedContent: RelatedContent;
}) {
	return (
		<ClayLayout.ContainerFluid className="px-4" size="md" view>
			<Breadcrumb uuid={relatedContent.uuid} />

			<ClayTabs>
				<ClayTabs.List>
					<ClayTabs.Item>
						{Liferay.Language.get('general')}
					</ClayTabs.Item>
				</ClayTabs.List>

				<ClayTabs.Panels fade>
					<ClayTabs.TabPane className="px-0">
						<GeneralTab
							disabled={disabled}
							relatedContent={relatedContent}
						/>
					</ClayTabs.TabPane>
				</ClayTabs.Panels>
			</ClayTabs>
		</ClayLayout.ContainerFluid>
	);
}

function GeneralTab({
	disabled,
	relatedContent,
}: {
	disabled: boolean;
	relatedContent: RelatedContent;
}) {
	const errors = useSelector(selectErrors(relatedContent.uuid));
	const mainStructureERC = useSelector(selectStructureERC);
	const publishedChildren = useSelector(selectPublishedChildren);

	const isPublished = publishedChildren.has(relatedContent.uuid);

	const {erc} = relatedContent;

	const dispatch = useStateDispatch();

	const feedbackId = useId();
	const labelInputId = useId();
	const pickerId = useId();

	const [selectedKey, setSelectedKey] = useState<string | undefined>(
		relatedContent.relatedStructureERC
	);

	const {data: objectDefinitions} = useCache('object-definitions');

	const error = errors.get('related-content');

	return (
		<div>
			<div className="mb-4">
				<p className="font-weight-semi-bold mb-0 text-3">
					{Liferay.Language.get('field-type')}
				</p>

				<ClayLabel displayType="warning">
					{Liferay.Language.get('select-related-content')}
				</ClayLabel>
			</div>

			<LocalizedInput
				disabled={disabled}
				error={errors.get('label')}
				formGroupClassName="mt-4"
				id={labelInputId}
				label={Liferay.Language.get('label')}
				onSave={(translations) => {
					dispatch({
						label: translations,
						type: 'update-related-content',
						uuid: relatedContent.uuid,
					});
				}}
				required
				translations={relatedContent.label}
			/>

			<ClayForm.Group
				className={classNames('mb-2', {'has-error': error})}
			>
				<label htmlFor={pickerId}>
					{Liferay.Language.get('related-content')}

					<ClayIcon
						className="ml-1 reference-mark"
						symbol="asterisk"
					/>
				</label>

				<Picker
					aria-describedby={feedbackId}
					disabled={disabled}
					id={pickerId}
					items={getItems(objectDefinitions, mainStructureERC)}
					messages={{
						itemDescribedby: Liferay.Language.get(
							'you-are-currently-on-a-text-element,-inside-of-a-list-box'
						),
						itemSelected: Liferay.Language.get('x-selected'),
						scrollToBottomAriaLabel:
							Liferay.Language.get('scroll-to-bottom'),
						scrollToTopAriaLabel:
							Liferay.Language.get('scroll-to-top'),
					}}
					onSelectionChange={(selectedKey: React.Key) => {
						setSelectedKey(String(selectedKey));

						dispatch({
							relatedStructureERC: String(selectedKey),
							type: 'update-related-content',
							uuid: relatedContent.uuid,
						});
					}}
					placeholder={Liferay.Language.get('select-content')}
					selectedKey={selectedKey ? String(selectedKey) : ''}
				>
					{(item) => <Option key={item.value}>{item.label}</Option>}
				</Picker>

				{error ? (
					<FieldFeedback errorMessage={error} id={feedbackId} />
				) : null}
			</ClayForm.Group>

			<ClayForm.Group className="mb-3">
				<ClayCheckbox
					checked={relatedContent.multiselection}
					disabled={disabled || isPublished}
					label={Liferay.Language.get('multiselection')}
					onChange={(event) => {
						dispatch({
							multiselection: event.target.checked,
							type: 'update-related-content',
							uuid: relatedContent.uuid,
						});
					}}
				/>
			</ClayForm.Group>

			<ERCInput
				disabled={disabled || isPublished}
				error={errors.get('erc')}
				onValueChange={(value) => {
					dispatch({
						erc: value,
						type: 'update-related-content',
						uuid: relatedContent.uuid,
					});
				}}
				value={erc}
			/>
		</div>
	);
}

function getItems(
	objectDefinitions: ObjectDefinitions,
	mainStructureERC: Structure['erc']
) {
	if (!objectDefinitions) {
		return [];
	}

	const items = [];

	for (const objectDefinition of Object.values(objectDefinitions)) {
		if (
			objectDefinition.externalReferenceCode === mainStructureERC ||
			objectDefinition.objectFolderExternalReferenceCode ===
				'L_CMS_STRUCTURE_REPEATABLE_GROUPS'
		) {
			continue;
		}

		items.push({
			label: getLocalizedValue(objectDefinition.label),
			value: objectDefinition.externalReferenceCode,
		});
	}

	return items;
}
