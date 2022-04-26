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
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import {BasicInfo, Conditions} from './DataValidation/ObjectValidationTabs';
import {useObjectValidationForm} from './ObjectValidationFormBase';
import SidePanelContent from './SidePanelContent';

const TABS = [
	{
		Component: BasicInfo,
		label: Liferay.Language.get('basic-info'),
	},
	{
		Component: Conditions,
		label: Liferay.Language.get('conditions'),
	},
];

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId() as Locale;
const defaultSymbol = defaultLanguageId.replace('_', '-').toLocaleLowerCase();
const locales: {label: string; symbol: string}[] = [];
const languageLabels: string[] = [];
const languages = Liferay.Language.available as LocalizedValue<string>;

Object.entries(languages).forEach(([languageId, label]) => {
	locales.push({
		label: languageId,
		symbol: languageId.replace('_', '-').toLocaleLowerCase(),
	});

	languageLabels.push(label);
});

const defaultLocale = locales.find(({symbol}) => symbol === defaultSymbol);

function closeSidePanel() {
	const parentWindow = Liferay.Util.getOpener();
	parentWindow.Liferay.fire('close-side-panel');
}

export default function EditObjectValidation({
	objectValidationRule: initialValues,
	objectValidationRuleElements,
	readOnly,
}: IProps) {
	const [activeIndex, setActiveIndex] = useState<number>(0);

	const onSubmit = async (objectValidation: ObjectValidation) => {
		const response = await fetch(
			`/o/object-admin/v1.0/object-validation-rules/${objectValidation.id}`,
			{
				body: JSON.stringify(objectValidation),
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'PUT',
			}
		);

		const parentWindow = Liferay.Util.getOpener();

		if (response.ok) {
			closeSidePanel();
			parentWindow.Liferay.Util.openToast({
				message: Liferay.Language.get(
					'the-object-validation-was-updated-successfully'
				),
				type: 'success',
			});
		}
		else {
			const message = Liferay.Language.get('an-error-occurred');

			parentWindow.Liferay.Util.openToast({message, type: 'danger'});
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectValidationForm({initialValues, onSubmit});

	return (
		<>
			<ClayTabs className="side-panel-iframe__tabs">
				{TABS.map(({label}, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => setActiveIndex(index)}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<SidePanelContent className="side-panel-content--layout">
				<SidePanelContent.Body>
					<ClayTabs.Content activeIndex={activeIndex} fade>
						{TABS.map(({Component, label}, index) => (
							<ClayTabs.TabPane key={index}>
								<Component
									componentLabel={label}
									defaultLocale={defaultLocale!}
									disabled={readOnly}
									errors={errors}
									handleChange={handleChange}
									locales={locales}
									objectValidationRuleElements={
										objectValidationRuleElements
									}
									setValues={setValues}
									values={values}
								/>
							</ClayTabs.TabPane>
						))}
					</ClayTabs.Content>
				</SidePanelContent.Body>

				<SidePanelContent.Footer>
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeSidePanel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton disabled={readOnly} onClick={handleSubmit}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				</SidePanelContent.Footer>
			</SidePanelContent>
		</>
	);
}

interface IProps {
	objectValidationRule: ObjectValidation;
	objectValidationRuleElements: ObjectValidationRuleElement[];
	readOnly: boolean;
}
