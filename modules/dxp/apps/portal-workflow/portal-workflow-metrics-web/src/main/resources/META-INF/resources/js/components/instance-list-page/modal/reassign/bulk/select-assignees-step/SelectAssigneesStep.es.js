/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	}, [tasks.length, retry]);

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
