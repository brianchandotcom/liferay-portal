/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useCallback, useContext, useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {RAYLIFE_PAGES} from '../../../common/utils/constants';
import {redirectTo} from '../../../common/utils/liferay';
import {smoothScroll} from '../../../common/utils/scroll';
import {AppContext} from '../context/AppContextProvider';
import {createOrUpdateRaylifeApplication} from '../services/RaylifeApplication';
import {
	APPLICATION_STATUS,
	AVAILABLE_STEPS,
	CONTACT_INFORMATION_STEP,
	OBJECT_MESSAGE,
} from '../utils/constants';
import {verifyInputAgentPage} from '../utils/contact-agent';
import {useStepWizard} from './useStepWizard';

/**
 *
 * @param {String} form <useWatch>
 * @param {String?} previousSection
 * @param {String?} nextSection
 * @param {String?} errorMessage
 * @returns
 */

const useFormActions = ({
	errorMessage = 'Unable to save your information. Please try again.',
	form,
	nextSection,
	previousSection,
	saveData = false,
}) => {
	const [applicationId, setApplicationId] = useState();
	const {setError, setValue} = useFormContext();
	const {setSection} = useStepWizard();
	const {
		state: {selectedStep},
	} = useContext(AppContext);

	/**
	 * @description When the application is created, we set the value to Form Context
	 * We tried to use setValue directly on goToPrevious and goToNextForm
	 * and for reasons unknowns, the section is not called.
	 */

	useEffect(() => {
		if (applicationId) {
			setValue('basics.applicationId', applicationId);

			const createDate = new Date().toISOString().split('T')[0];
			setValue('basics.applicationCreateDate', createDate);

			Storage.setItem(STORAGE_KEYS.APPLICATION_ID, applicationId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [applicationId]);

	useEffect(() => {
		Storage.setItem(STORAGE_KEYS.APPLICATION_FORM, JSON.stringify(form));
	}, [form]);

	const _onValidation = useCallback(() => {
		const phraseAgentPage = verifyInputAgentPage(form, nextSection);
		let validated = true;

		if (phraseAgentPage) {
			Storage.setItem(STORAGE_KEYS.CONTEXTUAL_MESSAGE, phraseAgentPage);
			redirectTo(RAYLIFE_PAGES.GET_IN_TOUCH);
			validated = false;
		}
		else {
			Storage.removeItem(STORAGE_KEYS.CONTEXTUAL_MESSAGE);
		}

		return validated;
	}, [form, nextSection]);

	const onSave = useCallback(
		async (status = {}) => {
			if (!saveData) {
				return;
			}

			setError('continueButton', {});

			status = APPLICATION_STATUS.OPEN;

			if (selectedStep.index !== CONTACT_INFORMATION_STEP) {
				status = APPLICATION_STATUS.INCOMPLETE;
			}

			try {
				const response = await createOrUpdateRaylifeApplication(
					form,
					status
				);

				setApplicationId(response.data.id);

				return response;
			}
			catch (error) {
				setError('continueButton', {
					message:
						errorMessage ||
						'There was an error processing your request. Please try again.',
					type: 'manual',
				});

				throw error;
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[errorMessage, form, saveData, setError]
	);

	const onPrevious = useCallback(async () => {
		await onSave();

		if (previousSection) {
			setSection(previousSection);
		}

		smoothScroll();
	}, [onSave, previousSection, setSection]);

	/**
	 * @state disabled for now
	 * @param {*} data
	 */
	const onNext = useCallback(async () => {
		let status = APPLICATION_STATUS.OPEN;

		if (AVAILABLE_STEPS.PROPERTY.index === selectedStep.index) {
			status = APPLICATION_STATUS.QUOTED;
		}

		const response = await createOrUpdateRaylifeApplication(form, status);

		if (response) {
			setApplicationId(response.data.id);

			const validated = _onValidation();

			if (validated) {
				if (nextSection) {
					setSection(nextSection);

					return smoothScroll();
				}

				redirectTo(RAYLIFE_PAGES.HANG_TIGHT);
			}

			return response;
		}

		setError('applicationObject', {
			message: OBJECT_MESSAGE.APPLICATION.DISABLED,
		});
	}, [
		selectedStep.index,
		form,
		setError,
		_onValidation,
		nextSection,
		setSection,
	]);

	return {onNext, onPrevious, onSave};
};

export default useFormActions;
