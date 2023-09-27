/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from 'graphql-hooks';
import React, {useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {deleteMessageBoardThreadQuery} from '../utils/client.es';
import {deleteCache, historyPushWithSlug} from '../utils/utils.es';
import Modal from './Modal.es';

export default withRouter(
	({deleteModalVisibility, history, question, setDeleteModalVisibility}) => {
		const historyPushParser = historyPushWithSlug(history.push);
		const context = useContext(AppContext);

		const [deleteThread] = useMutation(deleteMessageBoardThreadQuery);

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
							}).then(() => {
								deleteCache();
								historyPushParser(
									`/questions/${
										question.messageBoardSection
											? context.useTopicNamesInURL
												? question.messageBoardSection
														.title
												: question.messageBoardSection
														.id
											: context.rootTopicId
									}`
								);
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
