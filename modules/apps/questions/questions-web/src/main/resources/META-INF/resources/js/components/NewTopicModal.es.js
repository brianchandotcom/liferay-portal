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
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext, useRef} from 'react';

import {AppContext} from '../AppContext.es';
import {createSubTopicQuery, createTopicQuery} from '../utils/client.es';
import {deleteCacheVariables} from '../utils/utils.es';

export default ({currentSectionId, onClose, onCreateNavigateTo, visible}) => {
	const context = useContext(AppContext);
	const topicName = useRef(null);
	const topicDescription = useRef(null);

	const [createNewSubTopic] = useMutation(createSubTopicQuery, {
		onCompleted(data) {
			onCreateNavigateTo(
				data.createMessageBoardSectionMessageBoardSection.title
			);
		},
		update(proxy) {
			deleteCacheVariables(proxy, 'MessageBoardSection');
			proxy.gc();
		},
	});

	const [createNewTopic] = useMutation(createTopicQuery, {
		onCompleted(data) {
			onCreateNavigateTo(data.createSiteMessageBoardSection.title);
		},
		update(proxy) {
			deleteCacheVariables(proxy, 'MessageBoardSection');
			deleteCacheVariables(proxy, 'ROOT_QUERY');
			proxy.gc();
		},
	});

	const createTopic = () => {
		if (currentSectionId) {
			createNewSubTopic({
				variables: {
					description: topicDescription.current.value,
					parentMessageBoardSectionId: currentSectionId,
					title: topicName.current.value,
				},
			});
		}
		else {
			createNewTopic({
				variables: {
					description: topicDescription.current.value,
					siteKey: context.siteKey,
					title: topicName.current.value,
				},
			});
		}
	};

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
										createTopic();
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
};
