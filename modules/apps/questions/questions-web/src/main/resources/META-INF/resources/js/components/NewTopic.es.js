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
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useRef, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {createTopicQuery} from '../utils/client.es';
import {
	deleteCacheVariables,
	historyPushWithSlug,
	stringToSlug,
} from '../utils/utils.es';

export default withRouter(({currentSectionId, history}) => {
	const historyPushParser = historyPushWithSlug(history.push);
	const [visible, setVisible] = useState(false);

	const topicName = useRef(null);
	const topicDescription = useRef(null);

	const [createNewTopic] = useMutation(createTopicQuery, {
		onCompleted(data) {
			historyPushParser(
				`/questions/${stringToSlug(
					data.createMessageBoardSectionMessageBoardSection.title
				)}`
			);
		},
		update(proxy) {
			deleteCacheVariables(proxy, 'MessageBoardSection');
			proxy.gc();
		},
	});

	return (
		<>
			<NewTopicModal
				onClose={() => setVisible(false)}
				visible={visible}
			/>
			<ClayButton
				className="breadcrumb-button c-ml-3 c-p-2"
				displayType="unstyled"
				onClick={() => setVisible(true)}
			>
				<ClayIcon className="c-mr-2" symbol="plus" />
				{Liferay.Language.get('new-topic')}
			</ClayButton>
		</>
	);

	function NewTopicModal({onClose, visible}) {
		const {observer, onClose: close} = useModal({
			onClose,
		});

		return (
			<>
				{visible && (
					<ClayModal observer={observer} size="lg" status="info">
						<ClayModal.Header>
							{Liferay.Language.get('new-topic')}
						</ClayModal.Header>
						<ClayModal.Body>
							<ClayForm>
								<ClayForm.Group className="form-group-sm">
									<label htmlFor="basicInput">
										{Liferay.Language.get('topic-name')}
									</label>
									<ClayInput
										placeholder={Liferay.Language.get(
											'please-enter-a-valid-topic-name'
										)}
										ref={topicName}
										type="text"
									/>
								</ClayForm.Group>
								<ClayForm.Group className="form-group-sm">
									<label htmlFor="basicInput">
										{Liferay.Language.get('description')}
									</label>
									<ClayInput
										className="form-control"
										component="textarea"
										placeholder={Liferay.Language.get(
											'description'
										)}
										ref={topicDescription}
									/>
								</ClayForm.Group>
							</ClayForm>
						</ClayModal.Body>
						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={close}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>
									<ClayButton
										displayType="primary"
										onClick={() => {
											createNewTopic({
												variables: {
													description:
														topicDescription.current
															.value,
													parentMessageBoardSectionId: currentSectionId,
													title:
														topicName.current.value,
												},
											});
											setVisible(false);
											close();
										}}
									>
										{Liferay.Language.get('create')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</ClayModal>
				)}
			</>
		);
	}
});
