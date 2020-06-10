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
	exportTranslationURL,
	observer,
	onModalClose = noop,
}) => {
	const {namespace} = useContext(ExportTranslationContext);

	const [sourceLanguageId, setSourceLanguageId] = useState(
		availableSourceLocales[0].languageId
	);

	const [selectedTargetLocales, setSelectedTargetLocales] = useState([]);

	const exportTranslationPortletURL = Liferay.Util.PortletURL.createPortletURL(
		exportTranslationURL,
		{
			articleId: articleIds[0],
			sourceLanguageId,
			targetLanguageIds: selectedTargetLocales.join(','),
		}
	);

	const onChangeTarget = (checked, selectedLocaleId) => {
		if (checked) {
			setSelectedTargetLocales(
				selectedTargetLocales.concat(selectedLocaleId)
			);
		}
		else {
			setSelectedTargetLocales(
				selectedTargetLocales.filter(
					(localeId) => localeId != selectedLocaleId
				)
			);
		}
	};

	const SourceLocales = () => {
		if (availableSourceLocales.length == 1) {
			return (
				<ClayInput
					readOnly
					value={availableSourceLocales[0].displayName}
				/>
			);
		}
		else {
			return (
				<ClaySelect
					name={`_${namespace}_sourceLanguageId`}
					onChange={(e) => {
						setSourceLanguageId(e.currentTarget.value);
					}}
					value={sourceLanguageId}
				>
					{availableSourceLocales.map((locale) => (
						<ClaySelect.Option
							key={locale.languageId}
							label={locale.displayName}
							value={locale.languageId}
						/>
					))}
				</ClaySelect>
			);
		}
	};

	const TargetLocale = ({locale}) => {
		const languageId = locale.languageId;
		const checked = selectedTargetLocales.indexOf(languageId) != -1;

		return (
			<ClayCheckbox
				checked={checked}
				disabled={languageId === sourceLanguageId}
				label={locale.displayName}
				onChange={() => {
					onChangeTarget(!checked, languageId);
				}}
			/>
		);
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('export-for-translation')}
			</ClayModal.Header>

			<ClayForm
				className="export-modal-content"
				onSubmit={(e) => {
					e.preventDefault();
					onModalClose();
					location.href = Liferay.Util.addParams(
						'download=true',
						exportTranslationPortletURL.toString()
					);
				}}
			>
				<ClayModal.Body scrollable>
					<h5>{Liferay.Language.get('origin-language')}</h5>

					<ClayForm.Group>
						<SourceLocales />
					</ClayForm.Group>

					<h5>
						<p>
							{Liferay.Language.get('languages-to-translate-to')}
						</p>
					</h5>

					<ClayForm.Group>
						{availableTargetLocales.map((locale) => (
							<TargetLocale
								key={locale.languageId}
								locale={locale}
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

							<ClayButton
								disabled={selectedTargetLocales.length === 0}
								displayType="primary"
								type="submit"
							>
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
