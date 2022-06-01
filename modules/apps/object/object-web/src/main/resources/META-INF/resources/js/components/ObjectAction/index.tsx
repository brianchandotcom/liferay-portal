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

import 'codemirror/mode/groovy/groovy';
import ClayTabs from '@clayui/tabs';
import {
	CustomItem,
	FormError,
	SidePanelForm,
	closeSidePanel,
	invalidateRequired,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import ActionBuilder from './tabs/ActionBuilder';
import BasicInfo from './tabs/BasicInfo';

const REQUIRED_MSG = Liferay.Language.get('required');

const TABS = [
	Liferay.Language.get('basic-info'),
	Liferay.Language.get('action-builder'),
];

export default function Action({
	ffNotificationTemplates,
	objectAction: initialValues,
	objectActionExecutors,
	objectActionTriggers,
	readOnly,
	requestParams: {method, url},
	successMessage,
}: IProps) {
	const onSubmit = async (objectAction: ObjectAction) => {
		const response = await fetch(url, {
			body: JSON.stringify(objectAction),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method,
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			closeSidePanel();
			openToast({message: successMessage});

			return;
		}

		const {
			title = Liferay.Language.get('an-error-occurred'),
		} = (await response.json()) as {title?: string};

		openToast({message: title, type: 'danger'});
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectActionForm({initialValues, onSubmit});

	const [activeIndex, setActiveIndex] = useState(0);

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={Liferay.Language.get('new-action')}
		>
			<ClayTabs className="side-panel-iframe__tabs">
				{TABS.map((label, index) => (
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
				<ClayTabs.TabPane>
					<BasicInfo
						errors={errors}
						handleChange={handleChange}
						readOnly={readOnly}
						setValues={setValues}
						values={values}
					/>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane>
					<ActionBuilder
						errors={errors}
						ffNotificationTemplates={ffNotificationTemplates}
						objectActionExecutors={objectActionExecutors}
						objectActionTriggers={objectActionTriggers}
						setValues={setValues}
						values={values}
					/>
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</SidePanelForm>
	);
}

function useObjectActionForm({initialValues, onSubmit}: IUseObjectActionForm) {
	const validate = (values: Partial<ObjectAction>) => {
		const errors: FormError<ObjectAction & ObjectActionParameters> = {};
		if (invalidateRequired(values.name)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(values.objectActionTriggerKey)) {
			errors.objectActionTriggerKey = REQUIRED_MSG;
		}

		if (invalidateRequired(values.objectActionExecutorKey)) {
			errors.objectActionExecutorKey = REQUIRED_MSG;
		}
		else if (
			values.objectActionExecutorKey === 'webhook' &&
			invalidateRequired(values.parameters?.url)
		) {
			errors.url = REQUIRED_MSG;
		}

		if (
			typeof values.conditionExpression === 'string' &&
			invalidateRequired(values.conditionExpression)
		) {
			errors.conditionExpression = REQUIRED_MSG;
		}

		if (Object.keys(errors).length) {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
				type: 'danger',
			});
		}

		return errors;
	};

	return useForm<ObjectAction, ObjectActionParameters>({
		initialValues,
		onSubmit,
		validate,
	});
}

interface IProps {
	ffNotificationTemplates: boolean;
	objectAction: Partial<ObjectAction>;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	readOnly?: boolean;
	requestParams: {
		method: HTTPMethods;
		url: string;
	};
	successMessage: string;
	title: string;
}

interface IUseObjectActionForm {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}
