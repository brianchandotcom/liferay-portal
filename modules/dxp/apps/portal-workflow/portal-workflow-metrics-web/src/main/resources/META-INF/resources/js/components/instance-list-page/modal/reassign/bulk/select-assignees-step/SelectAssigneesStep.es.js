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

import React, {useContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {usePost} from '../../../../../../shared/hooks/usePost.es';
import {ModalContext} from '../../../ModalProvider.es';
import Body from './SelectAssigneesStepBody.es';
import Header from './SelectAssigneesStepHeader.es';

function SelectAssigneesStep({setErrorToast}) {
	const {
		selectTasks: {tasks},
	} = useContext(ModalContext);

	const [retry, setRetry] = useState(0);

	const {data, postData} = usePost({
		admin: true,
		body: {
			workflowTaskIds: tasks.map((task) => task.id),
		},
		url: '/workflow-tasks/assignable-users',
	});

	const promises = useMemo(() => {
		setErrorToast(false);

		if (tasks.length) {
			return [
				postData().catch((error) => {
					setErrorToast(
						Liferay.Language.get('your-request-has-failed')
					);

					return Promise.reject(error);
				}),
			];
		}

		return [];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [postData, retry]);

	return (
		<div className="fixed-height modal-metrics-content">
			<PromisesResolver promises={promises}>
				<PromisesResolver.Resolved>
					<SelectAssigneesStep.Header data={data} />
				</PromisesResolver.Resolved>

				<SelectAssigneesStep.Body
					data={data}
					setRetry={setRetry}
					tasks={tasks}
				/>
			</PromisesResolver>
		</div>
	);
}

SelectAssigneesStep.Body = Body;
SelectAssigneesStep.Header = Header;

export default SelectAssigneesStep;
