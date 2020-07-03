/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import {AppContext} from 'app-builder-web/js/AppContext.es';
import Button from 'app-builder-web/js/components/button/Button.es';
import {ControlMenuBase} from 'app-builder-web/js/components/control-menu/ControlMenu.es';
import withDDMForm, {
	useDDMFormSubmit,
	useDDMFormValidation,
} from 'app-builder-web/js/hooks/withDDMForm.es';
import {updateItem} from 'app-builder-web/js/utils/client.es';
import {successToast} from 'app-builder-web/js/utils/toast.es';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useCallback, useContext} from 'react';

import useAppWorkflow from '../../hooks/useAppWorkflow.es';

export function EditEntry({dataRecordId, ddmForm, redirect}) {
	const {appId, basePortletURL, baseResourceURL, namespace} = useContext(
		AppContext
	);

	const {appWorkflowStates: [initialState = {}] = []} = useAppWorkflow(appId);
	const {appWorkflowTransitions: [transition = {}] = []} = initialState;

	const onCancel = useCallback(() => {
		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
		else {
			Liferay.Util.navigate(basePortletURL);
		}
	}, [basePortletURL, redirect]);

	const onSubmit = useDDMFormValidation(
		ddmForm,
		useCallback(
			(dataRecord) => {
				if (dataRecordId !== '0') {
					updateItem(
						`/o/data-engine/v2.0/data-records/${dataRecordId}`,
						dataRecord
					).then(() => {
						successToast(
							Liferay.Language.get('an-entry-was-updated')
						);
						onCancel();
					});
				}
				else {
					fetch(
						createResourceURL(baseResourceURL, {
							p_p_resource_id: '/app_builder/add_data_record',
						}),
						{
							body: new URLSearchParams(
								Liferay.Util.ns(namespace, {
									appBuilderAppId: appId,
									dataRecord: JSON.stringify(dataRecord),
								})
							),
							method: 'POST',
						}
					).then(() => {
						successToast(
							Liferay.Language.get('an-entry-was-added')
						);
						onCancel();
					});
				}
			},
			[appId, baseResourceURL, dataRecordId, namespace, onCancel]
		)
	);

	useDDMFormSubmit(ddmForm, onSubmit);

	return (
		<>
			<ControlMenuBase
				backURL={redirect ? redirect : `${basePortletURL}/#/`}
				title={
					dataRecordId !== '0'
						? Liferay.Language.get('edit-entry')
						: Liferay.Language.get('add-entry')
				}
				url={location.href}
			/>

			<ClayButton.Group className="app-builder-form-buttons" spaced>
				<Button onClick={onSubmit}>
					{transition.name || Liferay.Language.get('submit')}
				</Button>

				<Button displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</Button>
			</ClayButton.Group>
		</>
	);
}

export default withDDMForm(EditEntry);
