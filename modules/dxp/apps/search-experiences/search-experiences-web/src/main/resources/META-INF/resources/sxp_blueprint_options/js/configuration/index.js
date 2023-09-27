/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import getLocalizedLearnMessageObject from '../../../sxp_blueprint_admin/js/utils/language/get_localized_learn_message_object';
import SelectSXPBlueprintModal from './SelectSXPBlueprintModal';

const Configuration = ({
	initialFederatedSearchKey = '',
	initialSXPBlueprintExternalReferenceCode = '',
	initialSXPBlueprintTitle = '',
	learnMessages,
	portletNamespace,
	preferenceKeyFederatedSearchKey,
	preferenceKeySXPBlueprintExternalReferenceCode,
}) => {
	const [federatedSearchKey, setFederatedSearchKey] = useState(
		initialFederatedSearchKey
	);
	const [
		sxpBlueprintExternalReferenceCode,
		setSXPBlueprintExternalReferenceCode,
	] = useState(initialSXPBlueprintExternalReferenceCode);
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
		setSXPBlueprintExternalReferenceCode('');
		setSXPBlueprintTitle('');
	};

	const _handleClickSelect = () => {
		setVisibleModal(true);
	};

	const _handleSubmitModal = (externalReferenceCode, title) => {
		setSXPBlueprintExternalReferenceCode(externalReferenceCode);
		setSXPBlueprintTitle(title);
	};

	return (
		<>
			{visibleModal && (
				<SelectSXPBlueprintModal
					observer={observer}
					onClose={onClose}
					onSubmit={_handleSubmitModal}
					selectedExternalReferenceCode={
						sxpBlueprintExternalReferenceCode
					}
				/>
			)}

			<ClayInput
				name={`${portletNamespace}${preferenceKeySXPBlueprintExternalReferenceCode}`}
				type="hidden"
				value={sxpBlueprintExternalReferenceCode}
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
