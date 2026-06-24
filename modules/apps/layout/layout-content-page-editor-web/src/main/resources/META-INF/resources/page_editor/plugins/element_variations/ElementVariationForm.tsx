/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {useId} from 'frontend-js-components-web';
import React from 'react';

import {ElementVariation} from './elementVariationsReducer';

type ElementVariationFormData = Pick<
	ElementVariation,
	'audienceEntryERC' | 'hide' | 'html' | 'js' | 'name' | 'targetElement'
>;

interface Props {
	audiences: Array<{label: string; value: string}>;
	elementVariation: ElementVariationFormData;
	onCancel: () => void;
	onChange: (properties: Partial<ElementVariationFormData>) => void;
	onSave: () => void;
}

export default function ElementVariationForm({
	audiences,
	elementVariation,
	onCancel,
	onChange,
	onSave,
}: Props) {
	const audienceId = useId();
	const htmlId = useId();
	const jsId = useId();
	const nameId = useId();
	const targetElementId = useId();

	return (
		<>
			<div className="align-items-center border-bottom d-flex flex-shrink-0 px-3 py-3">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('back')}
					borderless
					className="mr-2"
					displayType="secondary"
					onClick={onCancel}
					size="sm"
					symbol="angle-left"
				/>

				<span className="font-weight-bold">
					{Liferay.Language.get('element-variation')}
				</span>
			</div>

			<div className="flex-grow-1 overflow-auto p-3">
				<ClayForm.Group small>
					<label htmlFor={nameId}>
						{Liferay.Language.get('name')}
					</label>

					<ClayInput
						defaultValue={elementVariation.name}
						id={nameId}
						onBlur={(event) => onChange({name: event.target.value})}
						type="text"
					/>
				</ClayForm.Group>

				<ClayForm.Group small>
					<label htmlFor={targetElementId}>
						{Liferay.Language.get('page-element')}
					</label>

					<ClayInput
						defaultValue={elementVariation.targetElement}
						id={targetElementId}
						onBlur={(event) =>
							onChange({targetElement: event.target.value})
						}
						type="text"
					/>
				</ClayForm.Group>

				<ClayForm.Group small>
					<label htmlFor={audienceId}>
						{Liferay.Language.get('audience')}
					</label>

					<ClayMultiSelect
						id={audienceId}
						items={audiences.filter(
							(audience) =>
								audience.value ===
								elementVariation.audienceEntryERC
						)}
						onItemsChange={(
							items: Array<{label: string; value: string}>
						) => {
							const existingAudiences = items
								.map((item) =>
									audiences.find(
										(audience) =>
											audience.value === item.value
									)
								)
								.filter(
									(
										audience
									): audience is {
										label: string;
										value: string;
									} => Boolean(audience)
								);

							onChange({
								audienceEntryERC: existingAudiences.length
									? existingAudiences[
											existingAudiences.length - 1
										].value
									: '',
							});
						}}
						sourceItems={audiences}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayToggle
						label={Liferay.Language.get(
							'hide-element-for-this-audience'
						)}
						onToggle={(hide) => onChange({hide})}
						toggled={elementVariation.hide}
					/>
				</ClayForm.Group>

				<ClayForm.Group small>
					<label htmlFor={htmlId}>
						{Liferay.Language.get('html')}
					</label>

					<ClayInput
						component="textarea"
						defaultValue={elementVariation.html}
						id={htmlId}
						onBlur={(event) => onChange({html: event.target.value})}
					/>
				</ClayForm.Group>

				<ClayForm.Group small>
					<label htmlFor={jsId}>
						{Liferay.Language.get('javascript')}
					</label>

					<ClayInput
						component="textarea"
						defaultValue={elementVariation.js}
						id={jsId}
						onBlur={(event) => onChange({js: event.target.value})}
					/>
				</ClayForm.Group>
			</div>

			<div className="border-top d-flex flex-shrink-0 p-3">
				<ClayButton
					borderless
					displayType="secondary"
					onClick={onCancel}
					size="sm"
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className="ml-2"
					displayType="primary"
					onClick={onSave}
					size="sm"
				>
					{Liferay.Language.get('save')}
				</ClayButton>
			</div>
		</>
	);
}
