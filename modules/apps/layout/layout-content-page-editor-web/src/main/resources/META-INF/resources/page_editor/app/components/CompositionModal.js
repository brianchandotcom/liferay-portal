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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {ConfigContext} from '../../app/config/index';
import Button from '../../common/components/Button';

const CompositionModal = ({errorMessage, observer, onErrorDismiss}) => {
	const {portletNamespace} = useContext(ConfigContext);

	const [loading] = useState(false);

	const nameInputId = portletNamespace + 'fragmentCompositionName';
	const descriptionInputId =
		portletNamespace + 'fragmentCompositionDescription';

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('new-fragment')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm autoComplete="off" className="mb-3" noValidate>
					{errorMessage && (
						<ClayAlert
							displayType="danger"
							onClose={onErrorDismiss}
							title={errorMessage}
						/>
					)}
					<ClayForm.Group>
						<label htmlFor={nameInputId}>
							{Liferay.Language.get('name')}

							<ClayIcon
								className="ml-1 reference-mark"
								focusable="false"
								role="presentation"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							autoFocus
							id={nameInputId}
							placeholder={Liferay.Language.get('name')}
							required
							type="text"
							value={name}
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={descriptionInputId}>
							{Liferay.Language.get('description')}
						</label>

						<ClayInput
							autoFocus
							id={descriptionInputId}
							placeholder={Liferay.Language.get('description')}
							type="textarea"
							value={name}
						/>
					</ClayForm.Group>
				</ClayForm>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton disabled={loading} displayType="secondary">
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<Button
							disabled={loading}
							displayType="primary"
							loading={loading}
						>
							{Liferay.Language.get('save')}
						</Button>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
	);
};

CompositionModal.propTypes = {
	errorMessage: PropTypes.string,
	observer: PropTypes.object.isRequired,
	onErrorDismiss: PropTypes.func.isRequired
};

export default CompositionModal;
