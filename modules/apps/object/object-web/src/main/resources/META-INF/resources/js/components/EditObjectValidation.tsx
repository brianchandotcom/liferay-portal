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

import ClayTabs from '@clayui/tabs';
import {
	SideBarCategory,
	SidePanelForm,
	closeSidePanel,
	openToast,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	availableLocales,
	defaultLanguageId,
	defaultLocale,
} from '../utils/locale';
import {BasicInfo, Conditions} from './DataValidation/ObjectValidationTabs';
import {useObjectValidationForm} from './ObjectValidationFormBase';

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

		if (response.ok) {
			closeSidePanel();
			openToast({
				message: Liferay.Language.get(
					'the-object-validation-was-updated-successfully'
				),
			});
		}
		else {
			const message = Liferay.Language.get('an-error-occurred');

			openToast({message, type: 'danger'});
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectValidationForm({initialValues, onSubmit});

	useEffect(() => {
		if (initialValues.script === 'script_placeholder') {
			initialValues.script = '';
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={initialValues.name?.[defaultLanguageId] as string}
		>
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

			<ClayTabs.Content activeIndex={activeIndex} fade>
				{TABS.map(({Component, label}, index) =>
					activeIndex === index ? (
						<ClayTabs.TabPane key={index}>
							<Component
								componentLabel={label}
								defaultLocale={defaultLocale!}
								disabled={readOnly}
								errors={errors}
								handleChange={handleChange}
								locales={availableLocales}
								objectValidationRuleElements={
									objectValidationRuleElements
								}
								setValues={setValues}
								values={values}
							/>
						</ClayTabs.TabPane>
					) : (
						<React.Fragment key={index} />
					)
				)}
			</ClayTabs.Content>
		</SidePanelForm>
	);
}

interface IProps {
	objectValidationRule: ObjectValidation;
	objectValidationRuleElements: SideBarCategory[];
	readOnly: boolean;
}
