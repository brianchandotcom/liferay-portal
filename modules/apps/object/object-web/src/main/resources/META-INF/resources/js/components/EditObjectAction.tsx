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

import ClayForm, {ClayToggle} from '@clayui/form';
import React from 'react';

import Card from './Card/Card';
import ObjectActionFormBase, {
	IObjectActionFormBaseProps,
	useObjectActionForm,
} from './ObjectActionFormBase';
import {SidePanelForm, closeSidePanel, openToast} from './SidePanelContent';

export default function EditObjectAction({
	objectAction,
	objectActionExecutors,
	objectActionTriggers,
	readOnly,
}: IProps) {
	const onSubmit = async ({id, ...objectAction}: ObjectAction) => {
		const response = await Liferay.Util.fetch(
			`/o/object-admin/v1.0/object-actions/${id}`,
			{
				body: JSON.stringify(objectAction),
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'PUT',
			}
		);

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			closeSidePanel();
			openToast({
				message: Liferay.Language.get(
					'the-object-action-was-updated-successfully'
				),
			});

			return;
		}

		const responseJSON = await response.json();
		if (responseJSON?.title) {
			openToast({
				message: responseJSON.title,
				type: 'danger',
			});
		}
	};
	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectActionForm({initialValues: objectAction, onSubmit});

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={Liferay.Language.get('action')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				<ObjectActionFormBase
					errors={errors}
					handleChange={handleChange}
					objectAction={values}
					objectActionExecutors={objectActionExecutors}
					objectActionTriggers={objectActionTriggers}
					setValues={setValues}
				>
					<ClayForm.Group>
						<ClayToggle
							disabled={readOnly}
							label={Liferay.Language.get('active')}
							name="indexed"
							onToggle={(active) => setValues({active})}
							toggled={values.active}
						/>
					</ClayForm.Group>
				</ObjectActionFormBase>
			</Card>
		</SidePanelForm>
	);
}

interface IProps extends IObjectActionFormBaseProps {
	objectAction: ObjectAction;
	readOnly?: boolean;
}
