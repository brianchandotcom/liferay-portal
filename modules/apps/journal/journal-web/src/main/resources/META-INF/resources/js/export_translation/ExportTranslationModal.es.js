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
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import ExportTranslationContext from './ExportTranslationContext.es';

const noop = () => {};

const ExportTranslationModal = ({
	articleIds,
	availableSourceLocales,
	availableTargetLocales,
	observer,
	onModalClose = noop,
}) => {
	const {namespace} = useContext(ExportTranslationContext);

	const [originLanguageId, setOriginLanguageId] = useState(
		availableSourceLocales[0].languageId
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
							{availableSourceLocales.map((locale) => (
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
						{availableTargetLocales.map((locale) => (
							<ClayCheckbox
								checked={true}
								key={locale.languageId}
								label={locale.displayName}
								onChange={() => {}}
							/>
						))}
					</ClayForm.Group>

					<ClayInput
						name={`_${namespace}_articleIdsIds`}
						type="hidden"
						value={articleIds}
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
	articleIds: PropTypes.array,
	availableSourceLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			languageId: PropTypes.string,
		})
	).isRequired,
	availableTargetLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			languageId: PropTypes.string,
		})
	).isRequired,
};

export default ExportTranslationModal;
