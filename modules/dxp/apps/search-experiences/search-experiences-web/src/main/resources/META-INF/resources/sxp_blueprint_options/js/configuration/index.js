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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import {getLocalizedLearnMessageObject} from '../../../sxp_blueprint_admin/js/utils/language';
import SelectSXPBlueprintModal from './SelectSXPBlueprintModal';

const Configuration = ({
	initialFederatedSearchKey = '',
	initialSXPBlueprintId = '',
	initialSXPBlueprintTitle = '',
	learnMessages,
	portletNamespace,
	preferenceKeyFederatedSearchKey,
	preferenceKeySXPBlueprintId,
}) => {
	const [federatedSearchKey, setFederatedSearchKey] = useState(
		initialFederatedSearchKey
	);
	const [sxpBlueprintId, setSXPBlueprintId] = useState(initialSXPBlueprintId);
	const [sxpBlueprintTitle, setSXPBlueprintTitle] = useState(
		initialSXPBlueprintTitle
	);
	const [visibleModal, setVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const learnMessageObject = getLocalizedLearnMessageObject(
		'search-blueprint-on-search-page',
		learnMessages
	);

	const _handleChangeFederatedSearchKey = (event) => {
		setFederatedSearchKey(event.target.value);
	};

	const _handleClickRemove = () => {
		setSXPBlueprintId('');
		setSXPBlueprintTitle('');
	};

	const _handleClickSelect = () => {
		setVisibleModal(true);
	};

	const _handleSubmitModal = (id, title) => {
		setSXPBlueprintId(id);
		setSXPBlueprintTitle(title);
	};

	return (
		<>
			{visibleModal && (
				<SelectSXPBlueprintModal
					observer={observer}
					onClose={onClose}
					onSubmit={_handleSubmitModal}
					selectedId={sxpBlueprintId}
				/>
			)}

			<ClayInput
				name={`${portletNamespace}${preferenceKeySXPBlueprintId}`}
				type="hidden"
				value={sxpBlueprintId}
			/>

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}sxpBlueprintTitle`}>
					{Liferay.Language.get('blueprint')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							name={`${portletNamespace}sxpBlueprintTitle`}
							readOnly
							type="text"
							value={sxpBlueprintTitle}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							displayType="secondary"
							onClick={_handleClickSelect}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							displayType="secondary"
							onClick={_handleClickRemove}
						>
							{Liferay.Language.get('remove')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				{learnMessageObject.url && (
					<div className="form-text">
						<ClayLink
							className="learn-message"
							href={learnMessageObject.url}
							key="learn-how"
							rel="noopener noreferrer"
							target="_blank"
						>
							{learnMessageObject.message}
						</ClayLink>
					</div>
				)}
			</ClayForm.Group>

			<ClayForm.Group>
				<label
					htmlFor={`${portletNamespace}${preferenceKeyFederatedSearchKey}`}
				>
					{Liferay.Language.get('federated-search-key')}

					<ClayTooltipProvider>
						<ClaySticker
							displayType="unstyled"
							size="sm"
							title={Liferay.Language.get(
								'enter-the-key-of-an-alternate-search-this-widget-is-participating-on-if-not-set-widget-participates-on-default-search'
							)}
						>
							<ClayIcon
								data-tooltip-align="top"
								symbol="info-circle"
							/>
						</ClaySticker>
					</ClayTooltipProvider>
				</label>

				<ClayInput
					name={`${portletNamespace}${preferenceKeyFederatedSearchKey}`}
					onChange={_handleChangeFederatedSearchKey}
					value={federatedSearchKey}
				/>
			</ClayForm.Group>
		</>
	);
};

export default Configuration;
