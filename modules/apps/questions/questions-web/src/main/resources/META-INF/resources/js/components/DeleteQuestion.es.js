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
import {useMutation} from '@apollo/client';
import React from 'react';
import {withRouter} from 'react-router-dom';

import {deleteMessageBoardThreadQuery} from '../utils/client.es';
import {historyPushWithSlug} from '../utils/utils.es';
import Modal from './Modal.es';

export default withRouter(
	({deleteModalVisibility, history, question, setDeleteModalVisibility}) => {
		const historyPushParser = historyPushWithSlug(history.push);

		const [deleteThread] = useMutation(deleteMessageBoardThreadQuery, {
			onCompleted() {
				historyPushParser(
					`/questions/${question.messageBoardSection.title}`
				);
			},
			update(proxy) {
				proxy.evict(`MessageBoardThread:${question.id}`);
				proxy.gc();
			},
		});

		return (
			<>
				{question.actions && question.actions.delete && (
					<Modal
						body={Liferay.Language.get(
							'do-you-want-to-delete–this-question'
						)}
						callback={() => {
							deleteThread({
								variables: {
									messageBoardThreadId: question.id,
								},
							});
						}}
						onClose={() => {
							setDeleteModalVisibility(false);
						}}
						status="warning"
						textPrimaryButton={Liferay.Language.get('delete')}
						title={Liferay.Language.get('delete-question')}
						visible={deleteModalVisibility}
					/>
				)}
			</>
		);
	}
);
