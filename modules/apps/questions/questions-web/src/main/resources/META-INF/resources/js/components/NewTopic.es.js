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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

export default () => {
	const [visible, setVisible] = useState(false);

	return (
		<>
			<NewTopicModal
				onClose={() => setVisible(false)}
				visible={visible}
			/>
			<ClayButton
				displayType="secondary"
				onClick={() => setVisible(true)}
			>
				<ClayIcon symbol="plus" />
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
										type="text"
									/>
								</ClayForm.Group>
								<ClayForm.Group className="form-group-sm">
									<label htmlFor="basicInput">
										{Liferay.Language.get('description')}
									</label>
									<textarea
										className="form-control"
										placeholder={Liferay.Language.get(
											'description'
										)}
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
										onClick={close}
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
};
