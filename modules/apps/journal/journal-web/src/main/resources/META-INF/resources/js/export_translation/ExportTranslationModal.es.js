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
import ClayForm, {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import ExportTranslationContext from './ExportTranslationContext.es';

const noop = () => {};

const mockOriginLocales = [
	{
		languageId: 1,
		displayName: 'English - United States',
	},
	{
		languageId: 2,
		displayName: 'Chinese - China',
	},
];

const ExportTranslationModal = ({
	fileEntries,
	observer,
	onModalClose = noop,
}) => {
	const {namespace} = useContext(ExportTranslationContext);

	const [originLocales, setOriginLocales] = useState(mockOriginLocales);
	const [originLanguageId, setOriginLanguageId] = useState(
		originLocales[0].languageId
	);

	const handleSubmit = noop;

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('export-for-translation')}
			</ClayModal.Header>

			<ClayForm className="export-modal-content" onSubmit={handleSubmit}>
				<ClayModal.Body>
					<h5>{Liferay.Language.get('origin-language')}</h5>

					<ClayForm.Group>
						<ClaySelect
							name={`_${namespace}_originLanguageId`}
							onChange={(e) => {
								setOriginLanguageId(e.currentTarget.value);
							}}
							value={originLanguageId}
						>
							{originLocales.map((locale) => (
								<ClaySelect.Option
									key={locale.languageId}
									label={locale.displayName}
									value={locale.languageId}
								/>
							))}
						</ClaySelect>
					</ClayForm.Group>

					<h5>{Liferay.Language.get('languages-to-translate-to')}</h5>

					<ClayForm.Group>
						<ClayCheckbox
							checked={true}
							label="German - Austria"
							onChange={() => {}}
						/>
						<ClayCheckbox
							checked={true}
							label="Italian - Italy"
							onChange={() => {}}
						/>
						<ClayCheckbox
							checked={false}
							label="Spanish - Spain"
							onChange={() => {}}
						/>
					</ClayForm.Group>

					<ClayInput
						name={`_${namespace}_fileEntriesIds`}
						value={fileEntries}
						type="hidden"
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onModalClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('export')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

ExportTranslationModal.propTypes = {
	fileEntries: PropTypes.array,
};

export default ExportTranslationModal;